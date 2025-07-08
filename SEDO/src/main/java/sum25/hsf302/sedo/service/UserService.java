package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.User;

public interface UserService {
    User validateUser(String email, String password);
    boolean rememberUser(String email);
    void logout();
    User findByEmail(String email);
    User save(User user);
    long count();
    User findByUsername(String username);
}