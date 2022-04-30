package com.csci_4211_mocal.mocal.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.csci_4211_mocal.mocal.R;

public class LoginDialog extends DialogFragment {
    private TextView textViewError;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private LoginDialogListener listener;

    private boolean error;
    private String errorString;

    public LoginDialog(boolean error, String errorString) {
        this.error = error;
        this.errorString = errorString != null ? errorString : "";
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_login, null);
        builder.setView(view).setTitle("Login").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                listener.confirmLogin(username, password);
            }
        });

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        textViewError = view.findViewById(R.id.textViewError);

        if (error) {
            textViewError.setText(errorString);
        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (LoginDialogListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "LoginDialogListener not implemented");
        }
    }

    public interface LoginDialogListener {
        void confirmLogin(String username, String password);
    }
}
