package top.yzlin.homework.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

public class LoginInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if(session.getAttribute("user")!=null){
            return actionInvocation.invoke();
        }else{
            return "login";
        }
    }
}
