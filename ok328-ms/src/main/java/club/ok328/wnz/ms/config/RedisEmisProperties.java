package club.ok328.wnz.ms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.emis.redis")
public class RedisEmisProperties {
    private boolean enable;

    public RedisEmisProperties() {
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
