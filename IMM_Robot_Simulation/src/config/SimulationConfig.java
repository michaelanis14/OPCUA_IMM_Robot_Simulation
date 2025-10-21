package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Simulation-specific configuration.
 *
 * @author Michael Bishara
 */
public class SimulationConfig {

    @JsonProperty("auto-start")
    private boolean autoStart;

    @JsonProperty("continuous-mode")
    private boolean continuousMode;

    @JsonProperty("max-cycles")
    private int maxCycles;

    @JsonProperty("cycle-delay-ms")
    private int cycleDelayMs;

    @JsonProperty("failure-injection")
    private FailureInjection failureInjection;

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public boolean isContinuousMode() {
        return continuousMode;
    }

    public void setContinuousMode(boolean continuousMode) {
        this.continuousMode = continuousMode;
    }

    public int getMaxCycles() {
        return maxCycles;
    }

    public void setMaxCycles(int maxCycles) {
        this.maxCycles = maxCycles;
    }

    public int getCycleDelayMs() {
        return cycleDelayMs;
    }

    public void setCycleDelayMs(int cycleDelayMs) {
        this.cycleDelayMs = cycleDelayMs;
    }

    public FailureInjection getFailureInjection() {
        return failureInjection;
    }

    public void setFailureInjection(FailureInjection failureInjection) {
        this.failureInjection = failureInjection;
    }

    /**
     * Failure injection configuration for testing resilience
     */
    public static class FailureInjection {
        private boolean enabled;

        @JsonProperty("failure-rate")
        private double failureRate;

        private List<String> types;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public double getFailureRate() {
            return failureRate;
        }

        public void setFailureRate(double failureRate) {
            this.failureRate = failureRate;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
}
