package controller;

import dao.UserDAO;
import java.util.List;
import model.User;

public class UserManager {
    private final UserDAO dao = new UserDAO();

    public boolean register(String username, String password) {
        return dao.register(username, password);
    }

    public boolean login(String username, String password) {
        return dao.login(username, password);
    }

    public int getUserId(String username) {
        return dao.getUserId(username);
    }
    
     public List<User> getTopUsers(int limit) {
        return dao.getTopUsers(limit);
    }
}
