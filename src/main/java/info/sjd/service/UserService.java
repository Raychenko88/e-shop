package info.sjd.service;

import info.sjd.model.User;

import java.util.List;

public interface UserService {

    User save(User user);
    User findById(Integer id);
    User update(User user);
    void delete(User user);
    List<User> findAll();
    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);

}
