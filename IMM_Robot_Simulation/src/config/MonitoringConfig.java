package config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Monitoring and metrics configuration.
 *
 * @author Michael Bishara
 */
public class MonitoringConfig {

    private boolean enabled;

    @JsonProperty("metrics-interval-seconds")
    private int metricsIntervalSeconds;

    @JsonProperty("health-check-interval-seconds")
    private int healthCheckIntervalSeconds;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMetricsIntervalSeconds() {
        return metricsIntervalSeconds;
    }

    public void setMetricsIntervalSeconds(int metricsIntervalSeconds) {
        this.metricsIntervalSeconds = metricsIntervalSeconds;
    }

    public int getHealthCheckIntervalSeconds() {
        return healthCheckIntervalSeconds;
    }

    public void setHealthCheckIntervalSeconds(int healthCheckIntervalSeconds) {
        this.healthCheckIntervalSeconds = healthCheckIntervalSeconds;
    }
}
