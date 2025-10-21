package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * OPC UA configuration for servers and clients.
 *
 * @author Michael Bishara
 */
public class OpcUaConfig {

    @JsonProperty("molding-machine")
    private ServerConfig moldingMachine;

    @JsonProperty("robot")
    private ServerConfig robot;

    @JsonProperty("client")
    private ClientConfig client;

    public ServerConfig getMoldingMachine() {
        return moldingMachine;
    }

    public void setMoldingMachine(ServerConfig moldingMachine) {
        this.moldingMachine = moldingMachine;
    }

    public ServerConfig getRobot() {
        return robot;
    }

    public void setRobot(ServerConfig robot) {
        this.robot = robot;
    }

    public ClientConfig getClient() {
        return client;
    }

    public void setClient(ClientConfig client) {
        this.client = client;
    }

    public void validate() {
        Objects.requireNonNull(moldingMachine, "Molding machine configuration is required");
        Objects.requireNonNull(robot, "Robot configuration is required");
        Objects.requireNonNull(client, "Client configuration is required");

        moldingMachine.validate();
        robot.validate();
        client.validate();
    }

    /**
     * Server configuration
     */
    public static class ServerConfig {
        private boolean enabled;
        private String host;
        private int port;
        private String endpoint;
        private Namespace namespace;
        private NodeIds nodes;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public Namespace getNamespace() {
            return namespace;
        }

        public void setNamespace(Namespace namespace) {
            this.namespace = namespace;
        }

        public NodeIds getNodes() {
            return nodes;
        }

        public void setNodes(NodeIds nodes) {
            this.nodes = nodes;
        }

        public void validate() {
            if (enabled) {
                Objects.requireNonNull(host, "Host is required");
                if (port <= 0 || port > 65535) {
                    throw new IllegalStateException("Port must be between 1 and 65535");
                }
                Objects.requireNonNull(endpoint, "Endpoint is required");
                Objects.requireNonNull(namespace, "Namespace is required");
            }
        }
    }

    /**
     * Namespace configuration
     */
    public static class Namespace {
        private int index;
        private String uri;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    /**
     * Node IDs configuration
     */
    public static class NodeIds {
        @JsonProperty("base-object-id")
        private Integer baseObjectId;

        @JsonProperty("status-node-id")
        private Integer statusNodeId;

        @JsonProperty("hello-variable-id")
        private Integer helloVariableId;

        @JsonProperty("string-variable-id")
        private Integer stringVariableId;

        @JsonProperty("hello-method-id")
        private Integer helloMethodId;

        @JsonProperty("open-mold-method-id")
        private Integer openMoldMethodId;

        public Integer getBaseObjectId() {
            return baseObjectId;
        }

        public void setBaseObjectId(Integer baseObjectId) {
            this.baseObjectId = baseObjectId;
        }

        public Integer getStatusNodeId() {
            return statusNodeId;
        }

        public void setStatusNodeId(Integer statusNodeId) {
            this.statusNodeId = statusNodeId;
        }

        public Integer getHelloVariableId() {
            return helloVariableId;
        }

        public void setHelloVariableId(Integer helloVariableId) {
            this.helloVariableId = helloVariableId;
        }

        public Integer getStringVariableId() {
            return stringVariableId;
        }

        public void setStringVariableId(Integer stringVariableId) {
            this.stringVariableId = stringVariableId;
        }

        public Integer getHelloMethodId() {
            return helloMethodId;
        }

        public void setHelloMethodId(Integer helloMethodId) {
            this.helloMethodId = helloMethodId;
        }

        public Integer getOpenMoldMethodId() {
            return openMoldMethodId;
        }

        public void setOpenMoldMethodId(Integer openMoldMethodId) {
            this.openMoldMethodId = openMoldMethodId;
        }
    }

    /**
     * Client configuration
     */
    public static class ClientConfig {
        @JsonProperty("connection-timeout-ms")
        private int connectionTimeoutMs;

        @JsonProperty("reconnect-attempts")
        private int reconnectAttempts;

        @JsonProperty("reconnect-delay-ms")
        private int reconnectDelayMs;

        @JsonProperty("subscription-interval-ms")
        private int subscriptionIntervalMs;

        @JsonProperty("request-timeout-ms")
        private int requestTimeoutMs;

        public int getConnectionTimeoutMs() {
            return connectionTimeoutMs;
        }

        public void setConnectionTimeoutMs(int connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
        }

        public int getReconnectAttempts() {
            return reconnectAttempts;
        }

        public void setReconnectAttempts(int reconnectAttempts) {
            this.reconnectAttempts = reconnectAttempts;
        }

        public int getReconnectDelayMs() {
            return reconnectDelayMs;
        }

        public void setReconnectDelayMs(int reconnectDelayMs) {
            this.reconnectDelayMs = reconnectDelayMs;
        }

        public int getSubscriptionIntervalMs() {
            return subscriptionIntervalMs;
        }

        public void setSubscriptionIntervalMs(int subscriptionIntervalMs) {
            this.subscriptionIntervalMs = subscriptionIntervalMs;
        }

        public int getRequestTimeoutMs() {
            return requestTimeoutMs;
        }

        public void setRequestTimeoutMs(int requestTimeoutMs) {
            this.requestTimeoutMs = requestTimeoutMs;
        }

        public void validate() {
            if (connectionTimeoutMs <= 0) {
                throw new IllegalStateException("Connection timeout must be positive");
            }
            if (reconnectAttempts < 0) {
                throw new IllegalStateException("Reconnect attempts cannot be negative");
            }
            if (reconnectDelayMs <= 0) {
                throw new IllegalStateException("Reconnect delay must be positive");
            }
        }
    }
}
