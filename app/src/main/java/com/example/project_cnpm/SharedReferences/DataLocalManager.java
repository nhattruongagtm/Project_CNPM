package com.example.project_cnpm.SharedReferences;

import android.content.Context;

import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.Model.User;
import com.google.gson.Gson;

import java.util.HashMap;

public class DataLocalManager {
    private static final String REF_FIRST = "REF_FIRST";
    private static final String REF_VALID = "REF_VALID";
    private static final String REF_ACCOUNT = "REF_ACCOUNT";
    private static final String EMAIL_LOGIN = "EMAIL_LOGIN";
    private static final String PASSWORD_LOGIN = "PASSWORD_LOGIN";
    private static final String RESULT_LOGIN = "RESULT_LOGIN";
    private static final String RESULT_SIGNUP = "RESULT_SIGNUP";
    private static DataLocalManager instance;
    private MyShareReferences myShareReferences;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.myShareReferences = new MyShareReferences(context);
    }
    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setFirstInstalled(boolean isFirst){
        DataLocalManager.getInstance().myShareReferences.putBooleanValue(REF_FIRST,isFirst);
    }
    public static boolean getFirstInstalled(){
        return DataLocalManager.getInstance().myShareReferences.getBooleanValue(REF_FIRST);
    }
    public static void setAccount(Customer account){
        Gson gson = new Gson();
        String stringJSONAccount = gson.toJson(account);
        DataLocalManager.getInstance().myShareReferences.putStringValue(REF_ACCOUNT,stringJSONAccount);
    }
    public static void setSignUp(User user){
        Gson gson = new Gson();
        String stringJSONAccount = gson.toJson(user);
        DataLocalManager.getInstance().myShareReferences.putStringValue(RESULT_SIGNUP,stringJSONAccount);
    }
    public static User getUser(){
        Gson gson = new Gson();
        String stringJSONAccount = DataLocalManager.getInstance().myShareReferences.getStringValue(RESULT_SIGNUP);
        User user = gson.fromJson(stringJSONAccount,User.class);
        return user;
    }
    public static Customer getAccount(){
        Gson gson = new Gson();
        String stringJSONAccount = DataLocalManager.getInstance().myShareReferences.getStringValue(REF_ACCOUNT);
        Customer account = gson.fromJson(stringJSONAccount,Customer.class);
        return account;
    }
    public static void setValid(boolean input){
        DataLocalManager.getInstance().myShareReferences.putBooleanValue(REF_VALID,input);
    }
    public static boolean isValid(){
        return DataLocalManager.getInstance().myShareReferences.getBooleanValue(REF_VALID);
    }
    public static void setSaveAccount(String email, String password){
        DataLocalManager.getInstance().myShareReferences.putStringValue(EMAIL_LOGIN, email);
        DataLocalManager.getInstance().myShareReferences.putStringValue(PASSWORD_LOGIN,password);
    }
    public static HashMap<String,String> getLoginInput(){
        String email = DataLocalManager.getInstance().myShareReferences.getStringValue(EMAIL_LOGIN);
        String password = DataLocalManager.getInstance().myShareReferences.getStringValue(PASSWORD_LOGIN);

        HashMap<String,String> user = new HashMap<>();
        user.put(email,password);

        return user;
    }
    public static void setResultLogin(String result){
        DataLocalManager.getInstance().myShareReferences.putStringValue(RESULT_LOGIN,result);
    }
    public static String getResultLogin(){
        return DataLocalManager.getInstance().myShareReferences.getStringValue(RESULT_LOGIN);
    }

}
