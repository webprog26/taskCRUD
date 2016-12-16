package com.example.webprog26.taskcrud.interfaces;

import com.example.webprog26.taskcrud.models.User;

/**
 * Created by webprog26 on 29.11.2016.
 */

public interface OnUserActionListener {

    /**
     * Handles on list item click
     * @param user {@link User}
     * @param action int
     */

    public void onUserListItemClicked(User user, int action, int position);

    /**
     * Adds new user to remote database via API methods
     * @param user {@link User}
     */
    public void addUser(User user);

    /**
     * Edits user data stored in remote database via API methods
     * @param user {@link User}
     */
    public void editUser(User user);

    /**
     * Deletes user data stored in remote database via API methods
     * @param userId long
     */
    public void deleteUser(long userId);
}
