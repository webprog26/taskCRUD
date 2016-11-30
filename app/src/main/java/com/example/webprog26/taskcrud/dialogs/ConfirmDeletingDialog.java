package com.example.webprog26.taskcrud.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

import com.example.webprog26.taskcrud.R;

/**
 * Created by webpr on 30.11.2016.
 */

public class ConfirmDeletingDialog extends DialogFragment implements View.OnClickListener{

    private static final String TAG = "ConfirmDeletingDialog";

    private long mUserToDeleteId;
    private String mUserNameToDelete;

    public static final String USER_ID_TO_DELETE = "com.example.webprog26.taskcrud.user_id_to_delete";
    public static final String USER_NAME_TO_DELETE = "com.example.webprog26.taskcrud.user_name_to_delete";

    public static ConfirmDeletingDialog newInstance(long userId, String userName){
        Bundle bundle = new Bundle();
        bundle.putLong(USER_ID_TO_DELETE, userId);
        bundle.putString(USER_NAME_TO_DELETE, userName);

        ConfirmDeletingDialog deletingDialog = new ConfirmDeletingDialog();
        deletingDialog.setArguments(bundle);

        return deletingDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            mUserToDeleteId = getArguments().getLong(USER_ID_TO_DELETE);
            mUserNameToDelete = getArguments().getString(USER_NAME_TO_DELETE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.confirm_deleting_dialog, null);

        Button btnDeleteUserOk = (Button) view.findViewById(R.id.btnDeleteUserOk);
        Button btnDeleteUserCancel = (Button) view.findViewById(R.id.btnDeleteUserCancel);

        btnDeleteUserOk.setOnClickListener(this);
        btnDeleteUserCancel.setOnClickListener(this);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getResources().getString(R.string.confirm_delete, mUserNameToDelete))
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDeleteUserOk:
                sendResult(Activity.RESULT_OK, mUserToDeleteId);
                ConfirmDeletingDialog.this.dismiss();
                break;
            case R.id.btnDeleteUserCancel:
                sendResult(Activity.RESULT_CANCELED, 0);
                ConfirmDeletingDialog.this.dismiss();
                break;
        }
    }

    private void sendResult(int resultCode, long userToDeleteId){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(USER_ID_TO_DELETE, userToDeleteId);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
