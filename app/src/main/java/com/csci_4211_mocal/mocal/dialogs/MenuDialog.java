package com.csci_4211_mocal.mocal.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.csci_4211_mocal.mocal.R;

public class MenuDialog extends DialogFragment {
    private Button buttonReceived;
    private Button buttonLogout;
    private MenuListener listener;

    public MenuDialog(MenuListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_menu, null);
        builder.setView(view).setTitle("Menu");

        buttonReceived = view.findViewById(R.id.buttonReceived);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        buttonReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.receivedPressed();
                dismiss();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.logoutPressed();
                dismiss();
            }
        });

        return builder.create();
    }

    public interface MenuListener {
        void receivedPressed();
        void logoutPressed();
    }
}
