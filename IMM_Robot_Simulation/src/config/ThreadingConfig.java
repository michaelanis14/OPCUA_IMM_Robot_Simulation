package config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Threading configuration for executor services.
 *
 * @author Michael Bishara
 */
public class ThreadingConfig {

    @JsonProperty("core-pool-size")
    private int corePoolSize;

    @JsonProperty("max-pool-size")
    private int maxPoolSize;

    @JsonProperty("keep-alive-seconds")
    private int keepAliveSeconds;

    @JsonProperty("queue-capacity")
    private int queueCapacity;

    @JsonProperty("shutdown-timeout-seconds")
    private int shutdownTimeoutSeconds;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getShutdownTimeoutSeconds() {
        return shutdownTimeoutSeconds;
    }

    public void setShutdownTimeoutSeconds(int shutdownTimeoutSeconds) {
        this.shutdownTimeoutSeconds = shutdownTimeoutSeconds;
    }

    public void validate() {
        if (corePoolSize <= 0) {
            throw new IllegalStateException("Core pool size must be positive");
        }
        if (maxPoolSize < corePoolSize) {
            throw new IllegalStateException("Max pool size must be >= core pool size");
        }
        if (keepAliveSeconds < 0) {
            throw new IllegalStateException("Keep alive seconds cannot be negative");
        }
        if (queueCapacity < 0) {
            throw new IllegalStateException("Queue capacity cannot be negative");
        }
        if (shutdownTimeoutSeconds < 0) {
            throw new IllegalStateException("Shutdown timeout cannot be negative");
        }
    }
}
