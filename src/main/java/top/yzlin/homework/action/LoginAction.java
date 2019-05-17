package top.yzlin.homework.action;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yzlin.homework.dao.UserDAO;
import top.yzlin.homework.entity.RegisterUser;
import top.yzlin.homework.entity.User;
import top.yzlin.homework.service.UserService;

import java.util.Map;

@Component
public class LoginAction extends ActionSupport implements SessionAware {

    private User user;
    private RegisterUser registerUser;
    private Map<String, Object> session;
    private final UserService userService;

    public LoginAction(UserService userService) {
        this.userService = userService;
    }


    public RegisterUser getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session=session;
    }

    public String index(){
        return SUCCESS;
    }

    public String login(){
        User dbUser=userService.userLogin(user.getUserName(),user.getPassword());
        if(dbUser!=null){
            session.put("user",dbUser);
            return "buyTicket";
        }else{
            session.put("user",null);
            addFieldError("login.error",getText("login.error.loginError"));
            return INPUT;
        }
    }

    public String register(){
        if(userService.registerUser(registerUser)){
            addFieldError("register.error",getText("register.error.registerSuccess"));
            return SUCCESS;
        }else{
            addFieldError("register.error",getText("register.error.registerError"));
            return INPUT;
        }
    }

    public String loginOut(){
        session.put("user",null);
        session.clear();
        return SUCCESS;
    }

}
