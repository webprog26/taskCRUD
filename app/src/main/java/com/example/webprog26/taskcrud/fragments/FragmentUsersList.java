package com.example.webprog26.taskcrud.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.webprog26.taskcrud.R;
import com.example.webprog26.taskcrud.models.User;
import com.example.webprog26.taskcrud.models.UserResponse;

import java.util.List;


/**
 * Created by webprog26 on 28.11.2016.
 */

public class FragmentUsersList extends Fragment{

    private static final String TAG = "FragmentUsersList";

    public static final String LOADED_USERS_LIST = "com.example.webprog26.taskcrud.loaded_users_list";

    public static FragmentUsersList newInstance(UserResponse userResponse){
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOADED_USERS_LIST, userResponse);
        FragmentUsersList fragmentUsersList = new FragmentUsersList();
        fragmentUsersList.setArguments(bundle);

        return fragmentUsersList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<User> users = ((UserResponse)getArguments().getSerializable(LOADED_USERS_LIST)).getUserList();
        for(User user: users){
            Log.i(TAG, user.toString());
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

    }
}
