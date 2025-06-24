package sum25.hsf302.sedo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.hsf302.sedo.pojo.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}