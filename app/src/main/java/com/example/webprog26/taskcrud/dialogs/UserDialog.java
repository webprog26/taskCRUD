package com.example.webprog26.taskcrud.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.webprog26.taskcrud.R;
import com.example.webprog26.taskcrud.models.User;

/**
 * Created by webprog26 on 29.11.2016.
 */

public class UserDialog extends DialogFragment implements View.OnClickListener{

    private static final String TAG = "UserDialog";

    public static final String USER_DATA = "com.example.webprog26.taskcrud.user_data";
    public static final String USER_DIALOG_MODE = "com.example.webprog26.taskcrud.user_dialog_mode";
    public static final int MODE_EDIT_USER = 300;
    public static final int MODE_ADD_USER = 301;

    private EditText mEtUserName;
    private EditText mEtUserSecondName;
    private EditText mEtUserCity;

    private int mUserDialogMode;
    private long mUserId;
    private String mUserName;

    public static UserDialog newInstance(User user, int mode){
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_DATA, user);
        bundle.putInt(USER_DIALOG_MODE, mode);
        UserDialog userDialog = new UserDialog();
        userDialog.setArguments(bundle);

        return userDialog;
    }

    public static UserDialog newInstance(int mode){
        Bundle bundle = new Bundle();
        bundle.putInt(USER_DIALOG_MODE, mode);
        UserDialog userDialog = new UserDialog();
        userDialog.setArguments(bundle);

        return userDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mUserDialogMode = getArguments().getInt(USER_DIALOG_MODE);
            Log.i(TAG, "USER_DIALOG_MODE " + mUserDialogMode);
        }
    }

    private boolean isInEditMode(){
        return MODE_EDIT_USER == mUserDialogMode;
    }

    private boolean isInAdditionMode(){
        return MODE_ADD_USER == mUserDialogMode;
    }


    private boolean areEditTextFieldsNotEmpty(EditText userName, EditText userSecondName, EditText userCity){
        return userName.length() > 0 && userSecondName.length() > 0 && userCity.length() > 0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.user_dialog, null);

        mEtUserName = (EditText) view.findViewById(R.id.etUserName);
        mEtUserSecondName = (EditText) view.findViewById(R.id.etUserSecondName);
        mEtUserCity = (EditText) view.findViewById(R.id.etUserCity);

        if(isInEditMode()){

            User user = (User) getArguments().getSerializable(USER_DATA);

            mUserId = user.getUserId();
            mUserName = user.getUserName();

            mEtUserName.setText(user.getUserName());
            mEtUserSecondName.setText(user.getUserSecondName());
            mEtUserCity.setText(user.getUserCity());
        }

        Button btnUserOk = (Button) view.findViewById(R.id.btnUserOk);
        Button btnUserCancel = (Button) view.findViewById(R.id.btnUserCancel);

        btnUserOk.setOnClickListener(this);
        btnUserCancel.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        if(isInEditMode()){
            builder.setTitle(getResources().getString(R.string.edit_user, mUserName));
        } else if(isInAdditionMode()){
            builder.setTitle(getResources().getString(R.string.add_user));
        } else {
            new Exception("Unexpected mode").printStackTrace();
        }

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUserOk:
                if(!areEditTextFieldsNotEmpty(mEtUserName, mEtUserSecondName, mEtUserCity)){
                    return;
                }

                User.Builder builder = User.newBuilder();

                if(isInEditMode()){
                    builder.setUserId(mUserId);
                }

                builder.setUserName(mEtUserName.getText().toString());
                builder.setUserSecondName(mEtUserSecondName.getText().toString());
                builder.setUserCity(mEtUserCity.getText().toString());

                sendResult(Activity.RESULT_OK, builder.build());
                UserDialog.this.dismiss();
                break;
            case R.id.btnUserCancel:
                sendResult(Activity.RESULT_CANCELED, null);
                UserDialog.this.dismiss();
                break;
        }
    }

    private void sendResult(int resultCode, User user){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(USER_DATA, user);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
