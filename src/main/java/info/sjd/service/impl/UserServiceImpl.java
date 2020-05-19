package info.sjd.service.impl;

import info.sjd.dao.UserDAO;
import info.sjd.model.User;
import info.sjd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User save(User user) {
        if (user.getId() == null && userDAO.findByLogin(user.getLogin()) == null) {
            log.info("User was created");
            return userDAO.save(user);
        }
        return null;
    }

    @Override
    public User findById(Integer id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        if (user.getId() != null && userDAO.findById(user.getId()).isPresent()) {
            return userDAO.save(user);
        }
        return null;
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        return userDAO.findByLoginAndPassword(login, password);
    }
}
