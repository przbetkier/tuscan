package pro.tuscan.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties("mongo-client-properties")
public class MongoClientProperties {

    @NotEmpty
    private Integer socketTimeout;

    @NotEmpty
    private Integer connectionsPerHost;

    @NotEmpty
    private Integer maxConnectionIdleTime;

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(Integer connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public Integer getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }
}
