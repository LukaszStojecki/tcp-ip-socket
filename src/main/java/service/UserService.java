package service;

import exception.AuthException;
import model.User;

public interface UserService {
    User login(String login, String password) throws AuthException;
}
