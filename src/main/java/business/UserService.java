package business;

import business.interfaces.IUserService;
import model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements IUserService, Serializable  {

    private ArrayList<User> users;
    private static int lastUserId;

    public UserService() {
        users = new ArrayList<>();
        lastUserId = 0;

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        this.addUser(user);
    }

    @Override
    public User addUser(User user) {
        lastUserId += 1;
        user.setId(lastUserId);
        users.add(user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }
}
