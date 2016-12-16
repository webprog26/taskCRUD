package com.example.webprog26.taskcrud.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.webprog26.taskcrud.R;
import com.example.webprog26.taskcrud.adapters.UserListAdapter;
import com.example.webprog26.taskcrud.dialogs.ConfirmDeletingDialog;
import com.example.webprog26.taskcrud.dialogs.UserDialog;
import com.example.webprog26.taskcrud.interfaces.OnUserActionListener;
import com.example.webprog26.taskcrud.interfaces.OnUserListLoadedListener;
import com.example.webprog26.taskcrud.models.User;
import com.example.webprog26.taskcrud.models.UserResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by webprog26 on 28.11.2016.
 */

public class FragmentUsersList extends Fragment{

    private static final String TAG = "FragmentUsersList";

    private List<User> mUsers;
    private UserListAdapter mListAdapter;
    private OnUserListLoadedListener mOnUserListLoadedListener;
    private OnUserActionListener mOnUserActionListener;

    private static final String USER_DIALOG = "user_dialog";
    private static final String CONFIRM_USER_DELETE_DIALOG = "confirm_user_delete_dialog";
    private static final int USER_EDIT_REQUEST = 400;
    private static final int USER_ADD_REQUEST = 401;
    private static final int CONFIRM_DELETE_USER_REQUEST = 402;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsers = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnUserListLoadedListener){
            mOnUserListLoadedListener = (OnUserListLoadedListener) context;
        }

        if(context instanceof OnUserActionListener){
            mOnUserActionListener = (OnUserActionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView userListRecyclerView = (RecyclerView) view.findViewById(R.id.userListRecyclerView);
        userListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListAdapter = new UserListAdapter(getActivity(), mUsers, mOnUserActionListener);
        userListRecyclerView.setAdapter(mListAdapter);
        mOnUserListLoadedListener.onUserListLoaded();

        final FloatingActionButton btnAddUser = (FloatingActionButton) view.findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddOrEditUserDialog(null, UserDialog.MODE_ADD_USER);
            }
        });
    }

    /**
     * Updates data with the new one, downloaded from remote database
     * @param userResponse {@link UserResponse}
     */
    public void updateList(UserResponse userResponse){
        mListAdapter.updateAdapterData(userResponse.getUserList());
    }

    public void showConfirmDeleteUserDialog(long userToDeleteId, String userToDeleteName, int position){
        FragmentManager fragmentManager = getChildFragmentManager();

        ConfirmDeletingDialog deletingDialog = ConfirmDeletingDialog.newInstance(userToDeleteId, userToDeleteName, position);
        deletingDialog.setTargetFragment(FragmentUsersList.this, CONFIRM_DELETE_USER_REQUEST);

        deletingDialog.show(fragmentManager, CONFIRM_USER_DELETE_DIALOG);
    }

    /**
     * Displays dialog with {@link android.widget.EditText} fields
     * that contains current user data if it has to be edited
     * or empty fields if new user should be created
     * @param user {@link User}
     * @param action int
     */
    public void showAddOrEditUserDialog(User user, int action){
        UserDialog userDialog = null;

        FragmentManager fragmentManager = getChildFragmentManager();

        switch (action){
            case UserListAdapter.UserListViewHolder.EDIT_USER:
                userDialog = UserDialog.newInstance(user, UserDialog.MODE_EDIT_USER);
                userDialog.setTargetFragment(FragmentUsersList.this, USER_EDIT_REQUEST);
                break;
            case UserDialog.MODE_ADD_USER:
                userDialog = UserDialog.newInstance(UserDialog.MODE_ADD_USER);
                userDialog.setTargetFragment(FragmentUsersList.this, USER_ADD_REQUEST);
                break;
        }

        if(userDialog != null){
            if(fragmentManager.findFragmentByTag(USER_DIALOG) == null){//prevents opening new dialog while it's previous instance was'nt closed
                userDialog.show(fragmentManager, USER_DIALOG);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        User user;
        switch (requestCode){
            case USER_EDIT_REQUEST:
                user = (User) data.getSerializableExtra(UserDialog.USER_DATA);
                mOnUserActionListener.editUser(user);
                break;
            case USER_ADD_REQUEST:
                user = (User) data.getSerializableExtra(UserDialog.USER_DATA);
                mOnUserActionListener.addUser(user);
                break;
            case CONFIRM_DELETE_USER_REQUEST:
                mOnUserActionListener.deleteUser(data.getLongExtra(ConfirmDeletingDialog.USER_ID_TO_DELETE, 0));
                removeDeletedUser(data.getIntExtra(ConfirmDeletingDialog.USER_POSITION_IN_LIST_TO_DELETE, -1));
                break;
            default:
                new Exception("Unexpected request code").printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnUserListLoadedListener = null;
        mOnUserActionListener = null;
    }

    /**
     * Calls adapter's method removeUserFromList(int position)
     * @param position int
     */
    private void removeDeletedUser(int position){
        if(position < 0 || mListAdapter == null){
            return;
        }
        mListAdapter.removeUserFromList(position);
    }
}
