package com.edubox.admin.user;

import com.edubox.admin.User;

public interface UserCallback {
    void onUserRetrieved(User user);
    void onUserNotFound();
}
