package srv.service;

import srv.vo.UserInfo;

public interface TestService {
    UserInfo getUser(String id);

    UserInfo updateUser(UserInfo user);

    void evictUser(String id);
}
