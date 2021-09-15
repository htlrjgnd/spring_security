package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.repository.RoleRepository;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
