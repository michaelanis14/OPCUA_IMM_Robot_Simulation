package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Logging configuration.
 *
 * @author Michael Bishara
 */
public class LoggingConfig {

    private Map<String, String> level;

    private FileConfig file;

    public Map<String, String> getLevel() {
        return level;
    }

    public void setLevel(Map<String, String> level) {
        this.level = level;
    }

    public FileConfig getFile() {
        return file;
    }

    public void setFile(FileConfig file) {
        this.file = file;
    }

    /**
     * File logging configuration
     */
    public static class FileConfig {
        private boolean enabled;
        private String path;
        private String name;

        @JsonProperty("max-size")
        private String maxSize;

        @JsonProperty("max-history")
        private int maxHistory;

        @JsonProperty("total-size-cap")
        private String totalSizeCap;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(String maxSize) {
            this.maxSize = maxSize;
        }

        public int getMaxHistory() {
            return maxHistory;
        }

        public void setMaxHistory(int maxHistory) {
            this.maxHistory = maxHistory;
        }

        public String getTotalSizeCap() {
            return totalSizeCap;
        }

        public void setTotalSizeCap(String totalSizeCap) {
            this.totalSizeCap = totalSizeCap;
        }
    }
}
