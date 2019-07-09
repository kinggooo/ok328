package club.ok328.www.srv.config;

import club.ok328.www.srv.framework.StatLogger;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//@Primary // 在同样的DataSource中，首先使用被标注的DataSource
public class DruidDataSourceConfig {

    private String dbUrl;

    private String username;

    private String password;

    private String driverClassName;

    private int initialSize;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int timeBetweenEvictionRunsMillis;

    private int minEvictableIdleTimeMillis;

    private String validationQuery;

    private boolean testWhileIdle;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean poolPreparedStatements;

    private int maxPoolPreparedStatementPerConnectionSize;

    private String filters;

    private String connectionProperties;

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setStatLogger(new StatLogger());
        return dataSource;
    }
}
