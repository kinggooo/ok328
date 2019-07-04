package club.ok328.www.srv.service;

import club.ok328.www.srv.vo.UserInfo;

public interface CacheService {
    UserInfo getUser(String id);

    UserInfo updateUser(UserInfo user);

    void evictUser(String id);
}
