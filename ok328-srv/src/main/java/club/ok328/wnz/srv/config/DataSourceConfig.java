package club.ok328.wnz.srv.config;

//@Configuration
//@MapperScan(basePackages = "com.em.loan.bh.mapper")
//@MapperScan(basePackages = "com.em.loan.bh.mapper", sqlSessionFactoryRef = "DBDataSqlSessionFactory")
public class DataSourceConfig {
//    @Bean(name = "DBDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource") //告诉自动加载配置的属性
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "DBDataSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("DBDataSource") DataSource dataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "DBDataTransactionManager")
//    public DataSourceTransactionManager transactionManager(@Qualifier("DBDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "DBDataSqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("DBDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
