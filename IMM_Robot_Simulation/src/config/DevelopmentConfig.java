package config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Development-specific configuration.
 *
 * @author Michael Bishara
 */
public class DevelopmentConfig {

    @JsonProperty("hot-reload")
    private boolean hotReload;

    @JsonProperty("debug-mode")
    private boolean debugMode;

    @JsonProperty("verbose-logging")
    private boolean verboseLogging;

    @JsonProperty("mock-opcua")
    private boolean mockOpcua;

    public boolean isHotReload() {
        return hotReload;
    }

    public void setHotReload(boolean hotReload) {
        this.hotReload = hotReload;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    public boolean isMockOpcua() {
        return mockOpcua;
    }

    public void setMockOpcua(boolean mockOpcua) {
        this.mockOpcua = mockOpcua;
    }
}
