package com.example.webprog26.taskcrud;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.webprog26.taskcrud.adapters.UserListAdapter;
import com.example.webprog26.taskcrud.fragments.FragmentUsersList;
import com.example.webprog26.taskcrud.interfaces.OnUserListItemClickListener;
import com.example.webprog26.taskcrud.interfaces.OnUserListLoadedListener;
import com.example.webprog26.taskcrud.interfaces.UserApiInterface;
import com.example.webprog26.taskcrud.managers.ApiClient;
import com.example.webprog26.taskcrud.models.User;
import com.example.webprog26.taskcrud.models.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnUserListLoadedListener, OnUserListItemClickListener{

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
                Log.i(TAG, "user " + user.getUserName() + ", with id " + user.getUserId() + " is ready to edit");
                break;
            case UserListAdapter.UserListViewHolder.DELETE_USER:
                Log.i(TAG, "user " + user.getUserName() + ", with id " + user.getUserId() + " is ready to delete");
                break;
        }
    }

    @Override
    public void onUserListLoaded() {

        Call<UserResponse> call = mUserApiInterface.getUsers();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.i(TAG, "Response code: " + response.code());
                UserResponse userResponse = new UserResponse();
                userResponse.setUserList(response.body().getUserList());
                mFragmentUsersList.updateList(userResponse);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
