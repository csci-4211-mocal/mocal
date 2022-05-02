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
import com.csci_4211_mocal.mocal.models.Event;

public class ShareDialog extends DialogFragment {
    private TextView textViewError;
    private EditText editTextUsername;
    private ShareDialogListener listener;
    private Event event;

    private boolean error;
    private String errorString;

    public ShareDialog(boolean error, String errorString, ShareDialogListener listener, Event event) {
        this.error = error;
        this.errorString = errorString != null ? errorString : "";
        this.listener = listener;
        this.event = event;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_share, null);
        builder.setView(view).setTitle("Share").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = editTextUsername.getText().toString();
                listener.confirmShare(username, event);
            }
        });

        editTextUsername = view.findViewById(R.id.editTextUsername);
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
            listener = (ShareDialogListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "LoginDialogListener not implemented");
        }
    }

    public interface ShareDialogListener {
        void confirmShare(String username, Event event);
    }
}
