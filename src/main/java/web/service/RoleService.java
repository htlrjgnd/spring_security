package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    Role getByName(String name);
    List<Role> getAll();
    Role getById(Long id);
}
