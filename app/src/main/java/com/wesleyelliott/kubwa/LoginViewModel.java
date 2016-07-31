package com.wesleyelliott.kubwa;

import android.content.Context;
import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.wesleyelliott.kubwa.annotation.Checked;
import com.wesleyelliott.kubwa.annotation.Email;
import com.wesleyelliott.kubwa.annotation.Password;
import com.wesleyelliott.kubwa.rule.PasswordRule;


/**
 * Created by wesley on 2016/07/28.
 */
@Email(errorMessage = R.string.email_error)
@Password(errorMessage = R.string.password_error, scheme = PasswordRule.Scheme.ALPHA_NUMERIC_SYMBOLS)
@Checked(value = true, errorMessage = R.string.checked_error)
public class LoginViewModel extends BaseObservable {

    private String email;
    private String password;
    private boolean checked;
    LoginViewModelValidator validator;

    public LoginViewModel(Context context) {
        validator = new LoginViewModelValidator(context);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void onCheckedChanged(View v) {
        setChecked((((CheckBox) v).isChecked()));
    }

    public View.OnClickListener onLoginClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        };
    }

    private void login() {
        validator.validateAll(getEmail(), getPassword(), isChecked());
        notifyChange();

        if (validator.isValid()) {
            Log.d("TEST", "NO ERRORS!");
        } else {
            Log.d("TEST", "ERRORS!");
        }
    }

    public String getEmailError() {
        return validator.getEmailErrorMessage();
    }

    public String getPasswordError() {
        return validator.getPasswordErrorMessage();
    }

    public String getCheckedError() {
        return validator.getCheckedErrorMessage();
    }
}
