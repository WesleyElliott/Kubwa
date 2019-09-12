package com.wesleyelliott.kubwa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

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
        binding.setSpinnerAdapter(loginViewModel.getSpinnerAdapter());
    }
}
