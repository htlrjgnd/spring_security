package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
