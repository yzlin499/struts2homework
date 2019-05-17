package top.yzlin.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yzlin.homework.dao.UserDAO;
import top.yzlin.homework.database.annotation.Save;
import top.yzlin.homework.database.annotation.SaveOrUpdate;
import top.yzlin.homework.database.annotation.Select;
import top.yzlin.homework.entity.RegisterUser;
import top.yzlin.homework.entity.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User userLogin(String userName, String password) {
        return userDAO.userLogin(userName, password);
    }

    public boolean isExistUser(String userName) {
        return userDAO.isExistUser(userName);
    }

    public boolean registerUser(User user) {
        if (user instanceof RegisterUser){
            RegisterUser temp= (RegisterUser) user;
            user=new User();
            user.setUserName(temp.getUserName());
            user.setPassword(temp.getPassword());
        }
        return userDAO.registerUser(user);
    }

    public void updatePassword(User user) {
        userDAO.updatePassword(user);
    }
}
