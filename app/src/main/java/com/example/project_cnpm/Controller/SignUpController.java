package com.example.project_cnpm.Controller;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.project_cnpm.DAO.SignUpDAO;
import com.example.project_cnpm.View.Login.LoginActivity;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.View.SignUp.SignUpView;

import java.security.NoSuchAlgorithmException;

import static java.lang.Thread.sleep;

public class SignUpController implements ISignUpController{
     SignUpView signUpView;
     SignUpDAO signUpModel;

    public SignUpController(SignUpView signUpView, SignUpDAO signUpModel) {
        this.signUpView = signUpView;
        this.signUpModel = signUpModel;
    }

    @Override
    public void signup(String email, String password, String repass) {
        User user = new User(email,password,1);
        int signUpCode = user.isValid();
        if (signUpCode == 0){
            signUpView.showSignUpFail("*Vui lòng nhập email!");
          // return false;
        }
        else if(signUpCode == 1){
            signUpView.showSignUpFail("*Vui lòng nhập đúng email!");
         //  return false;
        }
        else if (signUpCode == 2){
            signUpView.showSignUpFail("*Vui lòng nhập password!");
         //  return false;
        }
        else if (signUpCode == 3){
            signUpView.showSignUpFail("*Mật khẩu phải có tối thiểu 8 ký tự");
         //   return false;
        }
        else if(repass.equals("")){
            signUpView.showSignUpFail("*Vui lòng xác nhận mật khẩu!");
        }
        else if(!repass.equals(password)){
            signUpView.showSignUpFail("*Mật khẩu không khớp!");
        }
        else{
            // input hợp lệ
            if (signUpModel.checkEmail(email)){
                // tồn tại username
                signUpView.showSignUpFail("*Email đã tồn tại!");
              //  return false;
            }
            else {
                try {
                    MD5 md5 = new MD5();
                    String pass = md5.enryptPassword(password);

                       Handler handler = new Handler(){
                           @Override
                           public void handleMessage(@NonNull Message msg) {
                               super.handleMessage(msg);
                               if (msg.what == 1){
                                   signUpView.showSignUpSuccess("Đăng ký thành công!");
                                   signUpModel.sendMail(email);

                                   Intent intent = new Intent(signUpView, LoginActivity.class);
                                   intent.putExtra("email_signup",email);
                                   signUpView.startActivity(intent);
                               }
                               else{
                                   signUpView.showSignUpFail("Lỗi kết nối! Vui lòng kiểm tra lại!");
                               }
                           }
                       };
                       signUpModel.signUp(email,pass);
                       signUpModel.setHandler(handler);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }
      // return false;
    }
}
