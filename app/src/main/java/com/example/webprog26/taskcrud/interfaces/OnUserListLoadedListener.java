package com.example.webprog26.taskcrud.interfaces;

/**
 * Created by webprog26 on 29.11.2016.
 */

public interface OnUserListLoadedListener {

    /**
     * Downloads completely all user data from remote database
     * to update existing data in {@link com.example.webprog26.taskcrud.adapters.UserListAdapter}
     */
    public void onUserListLoaded();
}
