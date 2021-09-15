package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAll();
    User getByName(String name);
    void deleteById(Long id);
    User findById(Long id);
    Long getById(String name);
    void save(User user);
}
