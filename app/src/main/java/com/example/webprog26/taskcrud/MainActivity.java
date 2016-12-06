package com.example.webprog26.taskcrud;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.webprog26.taskcrud.adapters.UserListAdapter;
import com.example.webprog26.taskcrud.fragments.FragmentUsersList;
import com.example.webprog26.taskcrud.interfaces.OnUserActionListener;
import com.example.webprog26.taskcrud.interfaces.OnUserListLoadedListener;
import com.example.webprog26.taskcrud.interfaces.UserApiInterface;
import com.example.webprog26.taskcrud.clients.ApiClient;
import com.example.webprog26.taskcrud.models.User;
import com.example.webprog26.taskcrud.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnUserListLoadedListener, OnUserActionListener {

    private static final String TAG = "MainActivity_TAG";

    private UserApiInterface mUserApiInterface;
    private FragmentUsersList mFragmentUsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserApiInterface = ApiClient.getClient().create(UserApiInterface.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentUsersList = new FragmentUsersList();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, mFragmentUsersList).commit();

        onUserListLoaded();
    }

    @Override
    public void onUserListItemClicked(User user, int action) {
        switch (action){
            case UserListAdapter.UserListViewHolder.EDIT_USER:
                mFragmentUsersList.showAddOrEditUserDialog(user, action);
                break;
            case UserListAdapter.UserListViewHolder.DELETE_USER:
                mFragmentUsersList.showConfirmDeleteUserDialog(user.getUserId(), user.getUserName());
                break;
        }
    }

    @Override
    public void onUserListLoaded() {

        Call<UserResponse> readUsersCall = mUserApiInterface.getUsers();
        readUsersCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.i(TAG, "Response code: " + response.code());
                UserResponse userResponse = new UserResponse();
                List<User> userList = response.body().getUserList();
                if(userList != null){
                    userResponse.setUserList(userList);
                    mFragmentUsersList.updateList(userResponse);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void addUser(User user) {
        Log.i(TAG, "Add request for " + user.toString());
        Call<UserResponse> addUserCall = mUserApiInterface.addUser(user.getUserName(),
                                                                   user.getUserSecondName(),
                                                                   user.getUserCity());
        addUserCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int responseCode = response.body().getResponseAnswer();
                Log.i(TAG, "Responce code addUser " + responseCode);
                onUserListLoaded();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void editUser(User user) {
        Log.i(TAG, "Edit request " + user.toString() + ", with id " + user.getUserId());
        Call<UserResponse> editUserCall = mUserApiInterface.editUser(user.getUserId(),
                                                                     user.getUserName(),
                                                                     user.getUserSecondName(),
                                                                     user.getUserCity());
        editUserCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int responseCode = response.body().getResponseAnswer();
                Log.i(TAG, "Responce code editUser " + responseCode);
                onUserListLoaded();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void deleteUser(long userId) {
        if(userId == 0){
            return;
        }

        Call<UserResponse> deleteUserCall = mUserApiInterface.deleteUser(userId);
        deleteUserCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int responseCode = response.body().getResponseAnswer();
                Log.i(TAG, "Responce code deleteUser " + responseCode);
                onUserListLoaded();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
