package com.example.project_cnpm.Controller;

import android.util.Log;

import com.example.project_cnpm.Login.LoginDAO;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.example.project_cnpm.View.ILoginView;

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
        } else {
            if(loginModel.checkLogin(email,password)){
                loginView.showLoginSuccess("*Đăng nhập thành công!");
                return true;
            } else {
                loginView.showLoginFail("*Không tồn tại tài khoản!");
                return false;
            }
        }
    }
}
