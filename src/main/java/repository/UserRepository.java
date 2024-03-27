package repository;


import model.Role;
import model.User;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {

    private final ArrayList<User> users;
    private User authorizedUser = null;

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    private final AtomicInteger currentId = new AtomicInteger(1);

    public UserRepository() {
        this.users = new ArrayList<>();
    }





    public ArrayList<User> getAllUsers() {
        return users;
    }

    public boolean isUserEmailExist(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) return true;
        }
        return false;
    }

    public User createUser(String email, String password) {
        User user = null;
        if (isEmailValid(email) && isPasswordValid(password)) {
            user = new User(currentId.getAndIncrement(), email, password);
            if (user.getEmail() == null || user.getPassword() == null) return null;
            user.setRole(Role.USER);
            users.add(user);
        }
        return user;
    }

    public User createAdmin(String email, String password) {
        User user = null;
        if (isEmailValid(email) && isPasswordValid(password)) {
            user = new User(0, email, password);
            if (user.getEmail() == null || user.getPassword() == null) return null;
            user.setRole(Role.ADMIN);
            users.add(user);
        }
        return user;
    }

    public User isRegistered(String email, String password) {
        if (email == null || password == null) {
            //Пустой email  или пароль
            return null;
        }
        if (isUserEmailExist(email)) {
            User user = createUser(email, password);
            return user;
        }
        return null;
    }

    public User getUserById(long userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }


    public void removeUserById(long userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userId){
                users.remove(i);
            }

        }

    }

    public User isAuthorized(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                authorizedUser = user;
                return authorizedUser;
            }
        }
        return null;
    }

    private boolean isEmailValid(String email) {
        return true;
    }


    private boolean isPasswordValid(String password) {
        return true;
    }

    public void blockUserById(Integer blockedUserId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == blockedUserId) {
                users.get(i).setRole(Role.BLOCKED);
            }
            }
        }

    @Override
    public String toString() {
        return "UserRepository{" +
                "users=" + users +
                '}';
    }

    public void falseActiveUser() {
        authorizedUser = null;
    }
}
