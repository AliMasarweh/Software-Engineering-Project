package com.alimasarweh.traingleuniversity;

import com.alimasarweh.traingleuniversity.Users.Student;
import com.alimasarweh.traingleuniversity.Users.User;

public class UserDataHolder {
    private User appUser = null;

    private UserDataHolder() { }

    public static final UserDataHolder instance = new UserDataHolder();

    public User getAppUser() { return this.appUser; }

    public void setAppUser(User user) { this.appUser = user; }
}
