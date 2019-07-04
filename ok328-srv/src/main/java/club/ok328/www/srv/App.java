package club.ok328.www.srv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableCaching
@MapperScan("club.ok328.www.srv.mapper")// 项目中的mapper扫描路径
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
