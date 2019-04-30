package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import top.yzlin.homework.Context;
import top.yzlin.homework.dao.UserDAO;
import top.yzlin.homework.entity.User;

import java.util.Map;
import java.util.Objects;

public class ChangePasswordAction extends ActionSupport implements SessionAware {
    private String oldPassword;
    private String newPassword;
    private String newPasswordAgain;
    private Map<String, Object> session;
    private UserDAO userDAO;

    public ChangePasswordAction() {
        userDAO= Context.getInstance().getComponent(UserDAO.class);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }


    public String page() throws Exception {
        return SUCCESS;
    }

    public String changePassword(){
        User user= (User) session.get("user");
        if(user!=null){
            if(!Objects.equals(user.getPassword(), oldPassword)){
                addFieldError("oldPassword","老密码错误");
                return INPUT;
            }else if(!Objects.equals(newPassword, newPasswordAgain)){
                addFieldError("newPasswordAgain","两次密码不统一");
                return INPUT;
            }else{
                user.setPassword(newPassword);
                userDAO.updatePassword(user);
                session.put("user" ,null);
                session.clear();
                return SUCCESS;
            }
        }else{
            addFieldError("error","没有登录");
            return INPUT;
        }
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session=session;
    }
}
