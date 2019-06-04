package srv.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import srv.service.TestService;
import srv.vo.UserInfo;

import java.util.Date;

@Service
public class TestServiceImpl implements TestService {
    private static Logger log = LoggerFactory.getLogger(TestService.class);

    @Override
    @Cacheable(cacheNames = "user", key = "#id")
    public UserInfo getUser(String id) {
        log.info("获取用户：{}", id);
        UserInfo user = new UserInfo();
        user.setId(id);
        user.setName("aaa");
        user.setCreateTime(new Date());
        return user;
    }

    @Override
    @CachePut(cacheNames = "user", key = "#user.id")
    public UserInfo updateUser(UserInfo user) {
        log.info("更新用户：{}", user.getId());
        user.setCreateTime(new Date());
        return user;
    }

    @Override
    @CacheEvict(cacheNames = "user", key = "#id")
    public void evictUser(String id) {
    }
}
