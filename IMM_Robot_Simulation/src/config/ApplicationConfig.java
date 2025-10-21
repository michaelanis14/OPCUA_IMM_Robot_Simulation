package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Root configuration class for the OPC UA IMM Robot Simulation application.
 * This class is automatically populated from application.yaml using Jackson.
 *
 * @author Michael Bishara
 */
public class ApplicationConfig {

    @JsonProperty("application")
    private AppInfo application;

    @JsonProperty("opcua")
    private OpcUaConfig opcua;

    @JsonProperty("timing")
    private TimingConfig timing;

    @JsonProperty("threading")
    private ThreadingConfig threading;

    @JsonProperty("logging")
    private LoggingConfig logging;

    @JsonProperty("monitoring")
    private MonitoringConfig monitoring;

    @JsonProperty("security")
    private SecurityConfig security;

    @JsonProperty("simulation")
    private SimulationConfig simulation;

    @JsonProperty("development")
    private DevelopmentConfig development;

    // Getters and Setters

    public AppInfo getApplication() {
        return application;
    }

    public void setApplication(AppInfo application) {
        this.application = application;
    }

    public OpcUaConfig getOpcua() {
        return opcua;
    }

    public void setOpcua(OpcUaConfig opcua) {
        this.opcua = opcua;
    }

    public TimingConfig getTiming() {
        return timing;
    }

    public void setTiming(TimingConfig timing) {
        this.timing = timing;
    }

    public ThreadingConfig getThreading() {
        return threading;
    }

    public void setThreading(ThreadingConfig threading) {
        this.threading = threading;
    }

    public LoggingConfig getLogging() {
        return logging;
    }

    public void setLogging(LoggingConfig logging) {
        this.logging = logging;
    }

    public MonitoringConfig getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(MonitoringConfig monitoring) {
        this.monitoring = monitoring;
    }

    public SecurityConfig getSecurity() {
        return security;
    }

    public void setSecurity(SecurityConfig security) {
        this.security = security;
    }

    public SimulationConfig getSimulation() {
        return simulation;
    }

    public void setSimulation(SimulationConfig simulation) {
        this.simulation = simulation;
    }

    public DevelopmentConfig getDevelopment() {
        return development;
    }

    public void setDevelopment(DevelopmentConfig development) {
        this.development = development;
    }

    /**
     * Validates the configuration for consistency and required fields.
     *
     * @throws IllegalStateException if configuration is invalid
     */
    public void validate() {
        Objects.requireNonNull(application, "Application configuration is required");
        Objects.requireNonNull(opcua, "OPC UA configuration is required");
        Objects.requireNonNull(timing, "Timing configuration is required");
        Objects.requireNonNull(threading, "Threading configuration is required");

        opcua.validate();
        timing.validate();
        threading.validate();
    }

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "application=" + application +
                ", opcua=" + opcua +
                ", timing=" + timing +
                ", threading=" + threading +
                '}';
    }

    /**
     * Application information
     */
    public static class AppInfo {
        private String name;
        private String version;
        private String mode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        @Override
        public String toString() {
            return "AppInfo{" +
                    "name='" + name + '\'' +
                    ", version='" + version + '\'' +
                    ", mode='" + mode + '\'' +
                    '}';
        }
    }
}
