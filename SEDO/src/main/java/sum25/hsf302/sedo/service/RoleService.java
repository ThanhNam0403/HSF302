package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Role;

public interface RoleService {
    Role save(Role role);
    Role findById(Long id);
}