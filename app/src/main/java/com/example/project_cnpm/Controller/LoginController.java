package com.example.project_cnpm.Controller;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

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
    public void login(String email, String password) {
        User user = new User(email, password, 1);
        int loginCode = user.isValid();
        if (loginCode == 0) {
            loginView.showLoginFail("*Vui lòng nhập email!");
        } else if (loginCode == 1) {
            loginView.showLoginFail("*Vui lòng nhập đúng email!");

        } else if (loginCode == 2) {
            loginView.showLoginFail("*Vui lòng nhập password!");

        } else {


//            if(loginModel.checkLogin(email,password)){
//                loginView.showLoginSuccess("*Đăng nhập thành công!");
//                return true;
//            } else {
//                loginView.showLoginFail("*Không tồn tại tài khoản!");
//                return false;
//            }

            Handler handler = new Handler(){

                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    if(msg.what == 1){
                        switch (msg.arg1){
                            case 0:
                                loginView.showLoginSuccess("*Đăng nhập thành công!");
                                break;
                            case 1:
                                loginView.showLoginFail("Tài khoản không tồn tại!");
                                break;
                            case 2:
                                loginView.showLoginFail("Lỗi hệ thống");
                                break;
                        }
                    }

                }
            };

            loginModel.setHandler(handler);

            loginModel.checkLogin(email,password);

        }
    }
}
