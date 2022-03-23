package business.interfaces;

import model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User addUser(User user);
    List<User> getUsers();
    Optional<User> findByUsername(String username);
    void deleteUser(User user);

}
