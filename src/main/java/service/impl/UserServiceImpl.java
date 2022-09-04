package service.impl;

import dao.UserDao;
import exception.AuthException;
import exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import model.User;
import service.UserService;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public User login(String login, String password) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new UserNotFoundException("Login " + login + " not found"));
        if (user.getPassword().equals(password)) {
            System.out.println("User with login: " + login + " is authenticated.");
            return user;
        } else {
            throw new AuthException("Invalid password for user with login: " + login);
        }
    }
}
