package com.example.project_cnpm.Controller;

import com.example.project_cnpm.DAO.LoginDAO;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.View.ILoginView;

import java.security.NoSuchAlgorithmException;

public class LoginController implements ILoginController {
    ILoginView loginView;
    LoginDAO loginModel;


    public LoginController(ILoginView loginView, LoginDAO loginModel) {
        this.loginView = loginView;
        this.loginModel = loginModel;
    }

    @Override
    public boolean login(String email, String password) {
        User user = new User(email, password, 1);
        int loginCode = user.isValid();
        if (loginCode == 0) {
            loginView.showLoginFail("*Vui lòng nhập email!");
            return false;
        } else if (loginCode == 1) {
            loginView.showLoginFail("*Vui lòng nhập đúng email!");
            return false;
        } else if (loginCode == 2) {
            loginView.showLoginFail("*Vui lòng nhập password!");
            return false;

        } else if (loginCode == 3) {
            loginView.showLoginFail("*Mật khẩu phải tối thiểu 8 ký tự!");
            return false;
        }
        else {
            MD5 md5 = new MD5();
            try {
                String pass = md5.enryptPassword(password.trim());
                if (loginModel.checkLogin(email, pass)==1) {
                    // lưu vào session
                    loginModel.getAccount(email,pass);

                    loginView.showLoginSuccess("*Đăng nhập thành công!");
                    return true;
                } else if(loginModel.checkLogin(email, pass)==0){
                    loginView.showLoginFail("*Không tồn tại tài khoản!");
                    return false;
                }
                else {
                    loginView.showLoginFail("*Sai mật khẩu!");
                    return false;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
