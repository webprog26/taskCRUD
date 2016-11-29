package com.example.webprog26.taskcrud.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.webprog26.taskcrud.R;
import com.example.webprog26.taskcrud.interfaces.OnUserActionListener;
import com.example.webprog26.taskcrud.models.User;

import java.util.List;

/**
 * Created by webprog26 on 29.11.2016.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    public class UserListViewHolder extends RecyclerView.ViewHolder{

        public static final int EDIT_USER = 100;
        public static final int DELETE_USER = 101;

        private TextView mTvUserName;
        private TextView mTvUserSecondName;
        private TextView mTvUserCity;
        private ImageButton mIbEditUser;
        private ImageButton mIbDeleteUser;

        public UserListViewHolder(View itemView) {
            super(itemView);

            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvUserSecondName = (TextView) itemView.findViewById(R.id.tvUserSecondName);
            mTvUserCity = (TextView) itemView.findViewById(R.id.tvUserCity);
            mIbEditUser = (ImageButton) itemView.findViewById(R.id.iBEditUser);
            mIbDeleteUser = (ImageButton) itemView.findViewById(R.id.iBDeleteUser);

        }

        public void bind(final User user, final OnUserActionListener onUserActionListener){
            mTvUserName.setText(user.getUserName());
            mTvUserSecondName.setText(user.getUserSecondName());
            mTvUserCity.setText(mContext.getResources().getString(R.string.lives_in, user.getUserCity()));

            mIbEditUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserActionListener.onUserListItemClicked(user, EDIT_USER);
                }
            });

            mIbDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserActionListener.onUserListItemClicked(user, DELETE_USER);
                }
            });
        }

    }

    private Context mContext;
    private List<User> mUserList;
    private OnUserActionListener mOnUserActionListener;

    public UserListAdapter(Context context, List<User> userList, OnUserActionListener onUserActionListener) {
        this.mContext = context;
        this.mUserList = userList;
        this.mOnUserActionListener = onUserActionListener;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        holder.bind(mUserList.get(position), mOnUserActionListener);
    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void updateAdapterData(List<User> data){
        this.mUserList = data;
        notifyDataSetChanged();
    }
}
