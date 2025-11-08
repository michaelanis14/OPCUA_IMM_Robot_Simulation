package util;

import config.ConfigurationManager;
import config.ThreadingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Centralized manager for ExecutorService instances to prevent memory leaks.
 * This singleton manages a shared ScheduledExecutorService that is properly
 * configured and cleaned up on shutdown.
 *
 * PROBLEM SOLVED:
 * The original code created a new ScheduledThreadPoolExecutor for EVERY state
 * transition, causing severe memory leaks. This manager provides a single,
 * reusable executor service with proper lifecycle management.
 *
 * Usage:
 * <pre>
 * ExecutorServiceManager.getInstance().getScheduledExecutor()
 *     .schedule(() -> doSomething(), 5, TimeUnit.SECONDS);
 * </pre>
 *
 * @author Michael Bishara
 */
public class ExecutorServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceManager.class);

    private static volatile ExecutorServiceManager instance;
    private final ScheduledExecutorService scheduledExecutor;
    private final ThreadingConfig config;
    private volatile boolean isShutdown = false;

    /**
     * Private constructor to prevent external instantiation.
     */
    private ExecutorServiceManager() {
        logger.info("Initializing ExecutorServiceManager");

        this.config = ConfigurationManager.getInstance().getThreadingConfig();
        this.scheduledExecutor = createScheduledExecutor();

        // Register shutdown hook for graceful cleanup
        registerShutdownHook();

        logger.info("ExecutorServiceManager initialized with corePoolSize={}, maxPoolSize={}",
                config.getCorePoolSize(), config.getMaxPoolSize());
    }

    /**
     * Gets the singleton instance of ExecutorServiceManager.
     * Uses double-checked locking for thread-safe lazy initialization.
     *
     * @return the ExecutorServiceManager instance
     */
    public static ExecutorServiceManager getInstance() {
        if (instance == null) {
            synchronized (ExecutorServiceManager.class) {
                if (instance == null) {
                    instance = new ExecutorServiceManager();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the shared ScheduledExecutorService.
     * This executor should be used for all scheduled tasks in the application.
     *
     * @return the ScheduledExecutorService
     * @throws IllegalStateException if the executor has been shut down
     */
    public ScheduledExecutorService getScheduledExecutor() {
        if (isShutdown) {
            throw new IllegalStateException("ExecutorServiceManager has been shut down");
        }
        return scheduledExecutor;
    }

    /**
     * Creates and configures the ScheduledExecutorService.
     *
     * @return configured ScheduledExecutorService
     */
    private ScheduledExecutorService createScheduledExecutor() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                config.getCorePoolSize(),
                new OpcUaThreadFactory("opcua-scheduled")
        );

        // Configure executor behavior
        executor.setMaximumPoolSize(config.getMaxPoolSize());
        executor.setKeepAliveTime(config.getKeepAliveSeconds(), TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);

        // Remove tasks from queue on cancellation to prevent memory leaks
        executor.setRemoveOnCancelPolicy(true);

        // Continue running other tasks even if one throws an exception
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

        return executor;
    }

    /**
     * Registers a JVM shutdown hook for graceful cleanup.
     */
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutdown hook triggered - cleaning up ExecutorService");
            shutdown();
        }, "executor-shutdown-hook"));
    }

    /**
     * Initiates an orderly shutdown of the executor service.
     * Previously submitted tasks are executed, but no new tasks will be accepted.
     */
    public void shutdown() {
        if (isShutdown) {
            logger.warn("ExecutorServiceManager already shut down");
            return;
        }

        logger.info("Shutting down ExecutorServiceManager");
        isShutdown = true;

        try {
            // Disable new tasks from being submitted
            scheduledExecutor.shutdown();

            // Wait for existing tasks to terminate
            if (!scheduledExecutor.awaitTermination(
                    config.getShutdownTimeoutSeconds(), TimeUnit.SECONDS)) {

                logger.warn("Executor did not terminate gracefully, forcing shutdown");

                // Force shutdown
                scheduledExecutor.shutdownNow();

                // Wait a bit for tasks to respond to being cancelled
                if (!scheduledExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.error("Executor did not terminate after forced shutdown");
                }
            }

            logger.info("ExecutorServiceManager shut down successfully");

        } catch (InterruptedException e) {
            logger.error("Interrupted while shutting down ExecutorService", e);

            // Force shutdown on interrupt
            scheduledExecutor.shutdownNow();

            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Attempts to stop all actively executing tasks and halts the processing
     * of waiting tasks.
     *
     * Use this only in emergency situations as it may leave tasks in inconsistent states.
     */
    public void shutdownNow() {
        if (isShutdown) {
            logger.warn("ExecutorServiceManager already shut down");
            return;
        }

        logger.warn("Forcing immediate shutdown of ExecutorServiceManager");
        isShutdown = true;
        scheduledExecutor.shutdownNow();
    }

    /**
     * Checks if the executor service has been shut down.
     *
     * @return true if shut down
     */
    public boolean isShutdown() {
        return isShutdown || scheduledExecutor.isShutdown();
    }

    /**
     * Checks if all tasks have completed following shut down.
     *
     * @return true if all tasks have completed
     */
    public boolean isTerminated() {
        return scheduledExecutor.isTerminated();
    }

    /**
     * Resets the singleton instance (primarily for testing).
     * Should not be used in production code.
     */
    public static synchronized void reset() {
        if (instance != null) {
            instance.shutdown();
            instance = null;
        }
    }

    /**
     * Custom ThreadFactory for creating named threads with appropriate priorities.
     */
    private static class OpcUaThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        OpcUaThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, namePrefix + "-" + threadNumber.getAndIncrement());
            thread.setDaemon(false); // Keep JVM alive while tasks are running
            thread.setPriority(Thread.NORM_PRIORITY);

            // Set uncaught exception handler
            thread.setUncaughtExceptionHandler((t, e) -> {
                logger.error("Uncaught exception in thread {}", t.getName(), e);
            });

            return thread;
        }
    }

    /**
     * Gets statistics about the executor service for monitoring.
     *
     * @return executor statistics as a string
     */
    public String getStatistics() {
        if (!(scheduledExecutor instanceof ScheduledThreadPoolExecutor)) {
            return "Statistics not available";
        }

        ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) scheduledExecutor;

        return String.format(
            "ExecutorService Stats: active=%d, poolSize=%d, corePoolSize=%d, " +
            "queueSize=%d, completedTasks=%d, isShutdown=%s, isTerminated=%s",
            exec.getActiveCount(),
            exec.getPoolSize(),
            exec.getCorePoolSize(),
            exec.getQueue().size(),
            exec.getCompletedTaskCount(),
            exec.isShutdown(),
            exec.isTerminated()
        );
    }
}
