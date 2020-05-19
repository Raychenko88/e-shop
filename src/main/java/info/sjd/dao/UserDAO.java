package info.sjd.dao;

import info.sjd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);

}
