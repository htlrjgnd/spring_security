package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    private RoleService roleService;
    private UserService userService;

    @Autowired
    public SetupDataLoader(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        roleService.addRole(new Role("ROLE_ADMIN"));
        roleService.addRole(new Role("ROLE_USER"));

        User user = new User();
        user.setUsername("ADMIN");
        user.setPassword("ADMIN");
        User user2 = new User();
        user2.setUsername("USER");
        user2.setPassword("USER");
        user.addRole(roleService.getByName("ROLE_ADMIN"));
        user.addRole(roleService.getByName("ROLE_USER"));
        user2.addRole(roleService.getByName("ROLE_USER"));
        user.addRole(roleService.getByName("ROLE_USER"));
        userService.addUser(user);
        userService.addUser(user2);
        alreadySetup = true;
    }
}
