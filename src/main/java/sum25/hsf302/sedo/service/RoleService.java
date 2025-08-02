package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role);
    Role findById(Long id);
    List<Role> findAll();
}