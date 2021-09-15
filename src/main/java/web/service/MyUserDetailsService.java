package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import web.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userRepository.findByUsername(username);
        if (user == null) {
           throw new UsernameNotFoundException(username);
        }
        return user;
    }

}
