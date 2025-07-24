package sum25.hsf302.sedo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sum25.hsf302.sedo.pojo.User;

public interface UserService {
    User validateUser(String email, String password);
    boolean rememberUser(String email);
    void logout();
    User findByEmail(String email);
    User save(User user);
    long count();
    User findByUsername(String username);
    int countActiveUsers();
    Page<User> findAll(Pageable pageable);
    User findById(Long id);
    void deleteById(Long id);
    User update(User user);
    Page<User> searchByFullName(String keyword, Pageable pageable);
}