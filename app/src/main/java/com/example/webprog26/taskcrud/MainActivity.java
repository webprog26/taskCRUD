package com.example.webprog26.taskcrud;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.webprog26.taskcrud.fragments.FragmentUsersList;
import com.example.webprog26.taskcrud.interfaces.UserApiInterface;
import com.example.webprog26.taskcrud.managers.ApiClient;
import com.example.webprog26.taskcrud.models.User;
import com.example.webprog26.taskcrud.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserApiInterface apiInterface = ApiClient.getClient().create(UserApiInterface.class);

        Call<UserResponse> call = apiInterface.getUsers();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.i(TAG, "Response code: " + response.code());
                UserResponse userResponse = new UserResponse();
                userResponse.setUserList(response.body().getUserList());
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.fragmentContainer, FragmentUsersList.newInstance(userResponse)).commit();
//                for(User user: response.body().getUserList()){
//                    Log.i(TAG, user.toString());
//                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });



//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragmentContainer, FragmentUsersList.newInstance(userResponse)).commit();
    }
}
