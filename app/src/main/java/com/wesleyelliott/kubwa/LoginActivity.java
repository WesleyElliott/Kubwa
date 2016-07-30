package com.wesleyelliott.kubwa;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wesleyelliott.kubwa.databinding.ActivityLoginBinding;

/**
 * Created by wesley on 2016/07/28.
 */

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new LoginViewModel(this);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(loginViewModel);
    }
}
