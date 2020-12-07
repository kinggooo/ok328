package club.ok328.wnz.srv.service;

import club.ok328.wnz.srv.vo.UserInfo;

public interface CacheService {
    UserInfo getUser(String id);

    UserInfo updateUser(UserInfo user);

    void evictUser(String id);
}
