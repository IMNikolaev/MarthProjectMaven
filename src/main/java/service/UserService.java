package service;

import model.User;
import repository.UserRepository;

import java.util.ArrayList;

public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }


    public User getAuthorizeUser (){
        return userRepository.getAuthorizedUser();
    }
    public void activeUserFalse() {userRepository.falseActiveUser();}

    public User registerUser(String email, String password){
        return userRepository.createUser(email, password);
    }

    public User authorize(String email, String password){
        return userRepository.isAuthorized(email, password);
    }

    public void  blockUser(Integer blockedUserId){
        userRepository.blockUserById(blockedUserId);
    }
}
