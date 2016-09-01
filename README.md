# Kubwa
An Annotation based validation library for use with Android's DataBinding library in an MVVM architecture.

 - Quick and easily add validation rules to ViewModels by annotating the class
 - Annotation Processing to elimate reflection and generate boilerplate validation classes
 
## Usage
1. Simply annotate your ViewModel class with the validation rules need:
  ```java
  @Email(errorMessage = R.string.email_error)
  @Password(errorMessage = R.string.password_error, scheme = PasswordRule.Scheme.ALPHA_NUMERIC_SYMBOLS)
  public class LoginViewModel extends BaseObservable {
    
    LoginViewModelValidator validator; // Generated when project is built
    
    public LoginViewModel(Context contex) {
      validator = new LoginViewModelValidator(context); // Instantiate the validator with a Context
    }
  }
  ```
  **Note: Each validation rule (`@Email`, `@Password`, etc) requires an `errorMessage` String resource parameter**
  
  *For a list of annotation rules, check out the [Annotations](https://github.com/WesleyElliott/Kubwa/tree/master/kubwa-annotations/src/main/java/com/wesleyelliott/kubwa/annotation)*

2. Make sure your layout and ViewModel are setup for DataBinding
  Layout.xml:
  ```xml
  <layout>
      <data>
          <variable name="viewModel" type="com.wesleyelliott.kubwa.LoginViewModel" />
      </data>
      <LinearLayout>
          <EditText
              ...
              android:text="@={viewModel.email}"
              app:error="@{viewModel.emailError}"/>
  
          <EditText
              ...
              android:text="@={viewModel.password}"
              app:error="@{viewModel.passwordError}"/>
  ```
  
  ViewModel:
  ```java
  public String getEmailError() {
      return validator.getEmailErrorMessage();
  }
  
  public String getPasswordError() {
      return validator.getPasswordErrorMessage();
  }
  ```
3. Validate each rule independantly, or all together (in the same order as the annotations):
  ```java
  private void login() {
      // Either
      validator.validateEmail(getEmail());
      validator.validatePassword(getPassword());
      
      // Or
      validator.validateAll(getEmail(), getPassword());
      notifyChange();
  
      if (validator.isValid()) {
         // Todo: Log user in
      }
  }
  ```
  And thats it! `notifyChange()` will ensure the errors (if any) are shown on the correct EditText's

## Download
Your project level gradle config (`build.gradle`) needs to have the `android-apt` plugin setup, as well as the jcenter repository:

```gradle
buildscript {
  repositories {
    mavenCentral()
    jcenter()
   }
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}
```

Apply the `android-apt` plugin to your module's build file, add the `kubwa-compiler` and `kubwa-annotations` dependancies and make sure Android DataBinding is setup:
```gradle
apply plugin: 'android-apt'

android {
  ...
  
  dataBinding {
        enabled = true
    }
}

dependencies {
  compile 'com.wesleyelliott:kubwa-annotations:1.0.1'
  apt 'com.wesleyelliott:kubwa-compiler:1.0.1'
}
```
## License

```
Copyright 2016 Wesley Elliott

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
