package club.ok328.wnz.srv.config;

import club.ok328.wnz.srv.framework.StatLogger;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties({DruidProp.class, DataSourceProp.class})
public class DruidDataSourceConfig {
    @Autowired
    private Environment env;

    @Autowired
    private DruidProp druidProp;

    @Autowired
    private DataSourceProp dataSourceProp;

//    @Primary
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid")
//    public DataSource dataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }
//    @Bean
//    @ConfigurationProperties(prefix = "mybatis")
//    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
//        return sqlSessionFactoryBean;
//    }

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
//    @Bean(name = "dataSource")
    @Primary // 在同样的DataSource中，首先使用被标注的DataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(dataSourceProp.getUrl());
//        dataSource.setDriverClassName(dataSourceProp.getDriverClassName());
//        dataSource.setUsername(dataSourceProp.getUsername());
//        dataSource.setPassword(dataSourceProp.getPassword());
//
//        dataSource.setInitialSize(druidProp.getInitialSize());
//        dataSource.setMaxActive(druidProp.getMaxActive());
//        dataSource.setMinIdle(druidProp.getMinIdle());
//        dataSource.setMaxWait(druidProp.getMaxWait());
//        dataSource.setFilters("config");
//
//        String connectPropStr = druidProp.getConnectionProperties();
//        dataSource.setConnectionProperties(connectPropStr);


        dataSource.setStatLogger(new StatLogger());
        return dataSource;
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public MyConfig myConfig() throws SQLException {
        MyConfig config = new MyConfig();
        System.out.println(druidProp.getInitialSize());
        return config;
    }
}
