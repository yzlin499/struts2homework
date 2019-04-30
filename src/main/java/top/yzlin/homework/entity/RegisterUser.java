package top.yzlin.homework.entity;

public class RegisterUser extends User {

    private String passwordAgain;

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    @Override
    public String toString() {
        return super.toString()+"RegisterUser{" +
                "passwordAgain='" + passwordAgain + '\'' +
                '}';
    }
}
