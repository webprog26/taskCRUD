package com.example.webprog26.taskcrud.interfaces;

import com.example.webprog26.taskcrud.models.User;

/**
 * Created by webprog26 on 29.11.2016.
 */

public interface OnUserActionListener {

    public void onUserListItemClicked(User user, int action);
    public void addUser(User user);
    public void editUser(User user);
    public void deleteUser(User user);
}
