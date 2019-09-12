# Kubwa
An Annotation based validation library for use with Android's DataBinding library in an MVVM architecture.

 - Quick and easily add validation rules to ViewModels by annotating the class
 - Annotation Processing to eliminate reflection and generate boilerplate validation classes

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Kubwa-green.svg?style=true)](https://android-arsenal.com/details/1/4428)
[![Download](https://jitpack.io/v/WesleyElliott/Kubwa.svg)](https://jitpack.io/#WesleyElliott/Kubwa)

## !Important!
The new `v2.0.0` has some breaking changes related to Gradle. This library is no longer hosted on jCenter, and is now available via JitPack. Please see [Download](#download) for more info on using the new version.
Code wise, this release has nothing new - so existing apps looking to target AndroidX can continue to use the library

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
Make sure your project level gradle config (`build.gradle`) has the jitpack repository:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add the `kubwa-compiler` and `kubwa-annotations` and `kubwa` dependancies and make sure Android DataBinding is setup:
```gradle
android {
  ...

  dataBinding {
      enabled = true
  }
}

dependencies {
  implementation 'com.github.WesleyElliott.Kubwa:kubwa:v2.0.0'
  implementation 'com.github.WesleyElliott.Kubwa:kubwa-annotations:v2.0.0'
  annotationProcessor 'com.github.WesleyElliott.Kubwa:kubwa-compiler:v2.0.0'
  // Or if you're using Kotlin, kapt:
  kapt 'com.github.WesleyElliott.Kubwa:kubwa-compiler:v2.0.0'
}
```
## License

```
Copyright 2019 Wesley Elliott

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
