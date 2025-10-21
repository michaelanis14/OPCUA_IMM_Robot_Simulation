package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Configuration manager for loading and accessing application configuration.
 * This class implements the Singleton pattern to ensure only one configuration
 * instance exists throughout the application lifecycle.
 *
 * Configuration is loaded from application.yaml in the classpath.
 * Environment-specific overrides can be provided via system properties or
 * environment variables.
 *
 * Usage:
 * <pre>
 * ApplicationConfig config = ConfigurationManager.getInstance().getConfig();
 * int port = config.getOpcua().getMoldingMachine().getPort();
 * </pre>
 *
 * @author Michael Bishara
 */
public class ConfigurationManager {

    private static final String DEFAULT_CONFIG_FILE = "application.yaml";
    private static final String CONFIG_FILE_PROPERTY = "config.file";

    private static volatile ConfigurationManager instance;
    private final ApplicationConfig config;

    /**
     * Private constructor to prevent external instantiation.
     *
     * @throws ConfigurationException if configuration cannot be loaded
     */
    private ConfigurationManager() {
        this.config = loadConfiguration();
        this.config.validate();
    }

    /**
     * Gets the singleton instance of ConfigurationManager.
     * Uses double-checked locking for thread-safe lazy initialization.
     *
     * @return the ConfigurationManager instance
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the application configuration.
     *
     * @return the ApplicationConfig instance
     */
    public ApplicationConfig getConfig() {
        return config;
    }

    /**
     * Resets the singleton instance (primarily for testing).
     * Should not be used in production code.
     */
    public static synchronized void reset() {
        instance = null;
    }

    /**
     * Loads configuration from YAML file.
     *
     * @return the loaded ApplicationConfig
     * @throws ConfigurationException if configuration cannot be loaded
     */
    private ApplicationConfig loadConfiguration() {
        String configFile = System.getProperty(CONFIG_FILE_PROPERTY, DEFAULT_CONFIG_FILE);

        try (InputStream inputStream = getConfigInputStream(configFile)) {
            if (inputStream == null) {
                throw new ConfigurationException(
                        "Configuration file not found: " + configFile +
                        ". Please ensure " + configFile + " is in the classpath."
                );
            }

            ObjectMapper mapper = createObjectMapper();
            ApplicationConfig loadedConfig = mapper.readValue(inputStream, ApplicationConfig.class);

            // Apply environment variable overrides
            applyEnvironmentOverrides(loadedConfig);

            return loadedConfig;

        } catch (IOException e) {
            throw new ConfigurationException(
                    "Failed to load configuration from " + configFile, e
            );
        }
    }

    /**
     * Gets input stream for configuration file.
     *
     * @param configFile the configuration file name
     * @return InputStream for the configuration file
     */
    private InputStream getConfigInputStream(String configFile) {
        // Try to load from classpath
        InputStream stream = getClass().getClassLoader().getResourceAsStream(configFile);
        if (stream != null) {
            return stream;
        }

        // Try to load from root of classpath
        return getClass().getResourceAsStream("/" + configFile);
    }

    /**
     * Creates and configures the Jackson ObjectMapper for YAML parsing.
     *
     * @return configured ObjectMapper
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        return mapper;
    }

    /**
     * Applies environment variable and system property overrides to configuration.
     * This allows runtime configuration without modifying YAML files.
     *
     * Environment variable format: OPCUA_MOLDING_MACHINE_PORT=4840
     * System property format: opcua.molding-machine.port=4840
     *
     * @param config the configuration to apply overrides to
     */
    private void applyEnvironmentOverrides(ApplicationConfig config) {
        // OPC UA Molding Machine Port
        String moldingPort = getEnvironmentOrProperty(
                "OPCUA_MOLDING_MACHINE_PORT",
                "opcua.molding-machine.port"
        );
        if (moldingPort != null) {
            config.getOpcua().getMoldingMachine().setPort(Integer.parseInt(moldingPort));
        }

        // OPC UA Robot Port
        String robotPort = getEnvironmentOrProperty(
                "OPCUA_ROBOT_PORT",
                "opcua.robot.port"
        );
        if (robotPort != null) {
            config.getOpcua().getRobot().setPort(Integer.parseInt(robotPort));
        }

        // OPC UA Molding Machine Host
        String moldingHost = getEnvironmentOrProperty(
                "OPCUA_MOLDING_MACHINE_HOST",
                "opcua.molding-machine.host"
        );
        if (moldingHost != null) {
            config.getOpcua().getMoldingMachine().setHost(moldingHost);
        }

        // OPC UA Robot Host
        String robotHost = getEnvironmentOrProperty(
                "OPCUA_ROBOT_HOST",
                "opcua.robot.host"
        );
        if (robotHost != null) {
            config.getOpcua().getRobot().setHost(robotHost);
        }

        // Application Mode
        String appMode = getEnvironmentOrProperty(
                "APP_MODE",
                "application.mode"
        );
        if (appMode != null) {
            config.getApplication().setMode(appMode);
        }

        // Debug Mode
        String debugMode = getEnvironmentOrProperty(
                "DEBUG_MODE",
                "development.debug-mode"
        );
        if (debugMode != null) {
            config.getDevelopment().setDebugMode(Boolean.parseBoolean(debugMode));
        }
    }

    /**
     * Gets value from environment variable or system property.
     * Environment variable takes precedence over system property.
     *
     * @param envVar the environment variable name
     * @param sysProp the system property name
     * @return the value, or null if not found
     */
    private String getEnvironmentOrProperty(String envVar, String sysProp) {
        String value = System.getenv(envVar);
        if (value != null) {
            return value;
        }
        return System.getProperty(sysProp);
    }

    /**
     * Exception thrown when configuration cannot be loaded or is invalid.
     */
    public static class ConfigurationException extends RuntimeException {
        public ConfigurationException(String message) {
            super(message);
        }

        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Convenience method to get OPC UA configuration.
     *
     * @return OPC UA configuration
     */
    public OpcUaConfig getOpcUaConfig() {
        return config.getOpcua();
    }

    /**
     * Convenience method to get timing configuration.
     *
     * @return timing configuration
     */
    public TimingConfig getTimingConfig() {
        return config.getTiming();
    }

    /**
     * Convenience method to get threading configuration.
     *
     * @return threading configuration
     */
    public ThreadingConfig getThreadingConfig() {
        return config.getThreading();
    }

    /**
     * Convenience method to check if running in debug mode.
     *
     * @return true if debug mode is enabled
     */
    public boolean isDebugMode() {
        return config.getDevelopment() != null && config.getDevelopment().isDebugMode();
    }

    /**
     * Convenience method to check if simulation should auto-start.
     *
     * @return true if auto-start is enabled
     */
    public boolean isAutoStart() {
        return config.getSimulation() != null && config.getSimulation().isAutoStart();
    }

    @Override
    public String toString() {
        return "ConfigurationManager{" +
                "config=" + config +
                '}';
    }
}
