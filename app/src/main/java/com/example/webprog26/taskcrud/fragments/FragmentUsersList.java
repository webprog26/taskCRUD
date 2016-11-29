package com.example.webprog26.taskcrud.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.webprog26.taskcrud.R;
import com.example.webprog26.taskcrud.adapters.UserListAdapter;
import com.example.webprog26.taskcrud.interfaces.OnUserListItemClickListener;
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
    private OnUserListItemClickListener mOnUserListItemClickListener;

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

        if(context instanceof OnUserListItemClickListener){
            mOnUserListItemClickListener = (OnUserListItemClickListener) context;
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
        mListAdapter = new UserListAdapter(getActivity(), mUsers, mOnUserListItemClickListener);
        userListRecyclerView.setAdapter(mListAdapter);
        mOnUserListLoadedListener.onUserListLoaded();
    }

    public void updateList(UserResponse userResponse){
        mListAdapter.updateAdapterData(userResponse.getUserList());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnUserListLoadedListener = null;
        mOnUserListItemClickListener = null;
    }
}
