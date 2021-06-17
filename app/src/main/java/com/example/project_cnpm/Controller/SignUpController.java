package com.example.project_cnpm.Controller;


import com.example.project_cnpm.DAO.SignUpDAO;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.View.ISignUpView;

import java.security.NoSuchAlgorithmException;

public class SignUpController implements ISignUpController{
     ISignUpView signUpView;
     SignUpDAO signUpModel;

    public SignUpController(ISignUpView signUpView, SignUpDAO signUpModel) {
        this.signUpView = signUpView;
        this.signUpModel = signUpModel;
    }

    @Override
    public boolean signup(String email, String password) {
        User user = new User(email,password,1);
        int signUpCode = user.isValid();
        if (signUpCode == 0){
            signUpView.showSignUpFail("*Vui lòng nhập email!");
            return false;
        }
        else if(signUpCode == 1){
            signUpView.showSignUpFail("*Vui lòng nhập đúng email!");
            return false;
        }
        else if (signUpCode == 2){
            signUpView.showSignUpFail("*Vui lòng nhập password");
            return false;
        }
        else if (signUpCode == 3){
            signUpView.showSignUpFail("*Mật khẩu phải có tối thiểu 8 ký tự");
            return false;
        }
        else{
            // input hợp lệ
            if (signUpModel.checkEmail(email)){
                // tồn tại username
                signUpView.showSignUpFail("*Email đã tồn tại!");
                return false;
            }
            else {
                try {
                    MD5 md5 = new MD5();
                    String pass = md5.enryptPassword(password);
                    if (signUpModel.signUp(email,pass)){
                        signUpModel.sendMail(email);
                        signUpView.showSignUpSuccess("*Đăng ký thành công!");
                        return true;
                    }
                    else{
                        signUpView.showSignUpFail("*Đã xảy ra lỗi, vui lòng thử lại!");
                        return false;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
