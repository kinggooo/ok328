package srv.service;

import srv.vo.UserInfo;

public interface TestService {
    UserInfo getUser(String id);

    UserInfo updateUser();

    void evictUser(String id);
}
