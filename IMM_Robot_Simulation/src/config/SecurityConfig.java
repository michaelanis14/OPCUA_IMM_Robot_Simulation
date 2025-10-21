package config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Security configuration for OPC UA.
 *
 * @author Michael Bishara
 */
public class SecurityConfig {

    private boolean enabled;

    @JsonProperty("certificate-path")
    private String certificatePath;

    @JsonProperty("private-key-path")
    private String privateKeyPath;

    @JsonProperty("trust-list-path")
    private String trustListPath;

    @JsonProperty("security-policy")
    private String securityPolicy;

    @JsonProperty("security-mode")
    private String securityMode;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getTrustListPath() {
        return trustListPath;
    }

    public void setTrustListPath(String trustListPath) {
        this.trustListPath = trustListPath;
    }

    public String getSecurityPolicy() {
        return securityPolicy;
    }

    public void setSecurityPolicy(String securityPolicy) {
        this.securityPolicy = securityPolicy;
    }

    public String getSecurityMode() {
        return securityMode;
    }

    public void setSecurityMode(String securityMode) {
        this.securityMode = securityMode;
    }
}
