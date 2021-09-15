package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import web.repository.UserRepository;
import java.util.List;


@Service
@Transactional
public class UserServiceImp implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Long getById(String name) {
        return userRepository.getById(name);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
