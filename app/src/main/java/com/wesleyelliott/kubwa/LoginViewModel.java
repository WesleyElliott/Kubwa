package com.wesleyelliott.kubwa;

import android.content.Context;
import androidx.databinding.BaseObservable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.wesleyelliott.kubwa.annotation.Checked;
import com.wesleyelliott.kubwa.annotation.ConfirmEmail;
import com.wesleyelliott.kubwa.annotation.ConfirmPassword;
import com.wesleyelliott.kubwa.annotation.Email;
import com.wesleyelliott.kubwa.annotation.Regex;
import com.wesleyelliott.kubwa.annotation.ZAIdNumber;
import com.wesleyelliott.kubwa.annotation.Max;
import com.wesleyelliott.kubwa.annotation.Min;
import com.wesleyelliott.kubwa.annotation.Password;
import com.wesleyelliott.kubwa.annotation.Range;
import com.wesleyelliott.kubwa.annotation.Select;
import com.wesleyelliott.kubwa.rule.PasswordRule;


/**
 * Created by wesley on 2016/07/28.
 */
@Email(errorMessage = R.string.email_error)
@ConfirmEmail(errorMessage = R.string.confirm_email_error)
@Password(errorMessage = R.string.password_error, scheme = PasswordRule.Scheme.ALPHA_NUMERIC_SYMBOLS)
@ConfirmPassword(errorMessage = R.string.confirm_password_error)
@ZAIdNumber(errorMessage = R.string.id_error)
@Checked(errorMessage = R.string.checked_error)
@Min(errorMessage = R.string.min_error, value = 10)
@Max(errorMessage = R.string.max_error, value = 50)
@Range(errorMessage = R.string.range_error, min = 10, max = 20, includeBounds = true)
@Select(errorMessage = R.string.spinner_error, value = 0)
@Regex(errorMessage = R.string.app_name, regex = "*")
public class LoginViewModel extends BaseObservable {

    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;
    private String idNumber;
    private boolean checked;
    private int min;
    private int max;
    private int range;
    private int spinnerIndex;
    private SpinnerAdapter adapter;

    LoginViewModelValidator validator;

    public LoginViewModel(Context context) {
        validator = new LoginViewModelValidator(context);
        adapter = new SpinnerAdapter(context);
        adapter.addAll("Android", "iOS", "Windows Mobile", "Blackberry");
    }


    // Field Getters / Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getMin() {
        return Integer.toString(min);
    }

    public void setMin(String min) {
        this.min = TextUtils.isEmpty(min) ? 0 : Integer.parseInt(min);
    }

    public String getMax() {
        return Integer.toString(max);
    }

    public void setMax(String max) {
        this.max = TextUtils.isEmpty(max) ? 0 : Integer.parseInt(max);
    }

    public String getRange() {
        return Integer.toString(range);
    }

    public void setRange(String range) {
        this.range = TextUtils.isEmpty(range) ? 0 : Integer.parseInt(range);
    }

    public void setSpinnerIndex(int spinnerIndex) {
        this.spinnerIndex = spinnerIndex;
    }

    public int getSpinnerIndex() {
        return spinnerIndex;
    }

    // Custom Methods

    public SpinnerAdapter getSpinnerAdapter() {
        return adapter;
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

    public Spinner.OnItemSelectedListener onSelectionChanged() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSpinnerIndex(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    private void login() {
        validator.validateAll(getEmail(),
                getPassword(),
                getIdNumber(),
                "123",
                isChecked(),
                getConfirmEmail(),
                getConfirmPassword(),
                min, max,
                getSpinnerIndex(),
                range);

        notifyChange();

        if (validator.isValid()) {
            Log.d("TEST", "NO ERRORS!");
        } else {
            Log.d("TEST", "ERRORS!");
        }
    }

    // Errors

    public String getEmailError() {
        return validator.getEmailErrorMessage();
    }

    public String getConfirmEmailError() {
        return validator.getConfirmEmailErrorMessage();
    }

    public String getPasswordError() {
        return validator.getPasswordErrorMessage();
    }

    public String getConfirmPasswordError() {
        return validator.getConfirmPasswordErrorMessage();
    }

    public String getIdError() {
        return validator.getIdNumberErrorMessage();
    }

    public String getCheckedError() {
        return validator.getCheckedErrorMessage();
    }

    public String getMinError() {
        return validator.getMinErrorMessage();
    }

    public String getMaxError() {
        return validator.getMaxErrorMessage();
    }

    public String getSpinnerError() {
        return validator.getSpinnerErrorMessage();
    }

    public String getRangeError() {
        return validator.getRangeErrorMessage();
    }

    public String getRegexError() {
        return validator.getRegexErrorMessage();
    }
}
