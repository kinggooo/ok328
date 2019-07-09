package club.ok328.www.srv.framework;

import com.alibaba.druid.pool.DruidDataSourceStatLoggerAdapter;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatLogger extends DruidDataSourceStatLoggerAdapter {
    private static Logger log = LoggerFactory.getLogger(StatLogger.class);

    @Override
    public void log(DruidDataSourceStatValue statValue) {
        log.info("***************************************************");
        log.info("                  监控数据持久化                    ");
        log.info("***************************************************");
    }
}
