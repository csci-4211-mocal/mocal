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
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.Event;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class NewEventDialog extends DialogFragment {
    private TimePicker timePicker;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewError;
    private NewEventDialogListener listener;

    private boolean error;
    private String errorString;

    public NewEventDialog(boolean error, String errorString) {
        this.error = error;
        this.errorString = errorString != null ? errorString : "";
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_new_event, null);

        builder.setView(view).setTitle("Login")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        LocalDate localDate = LocalDate.now();
                        int year = localDate.getYear();
                        int month = localDate.getMonthValue();
                        int day = localDate.getDayOfMonth();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hour, minute);
                        Date timestamp = calendar.getTime();
                        String title = editTextTitle.getText().toString();
                        String description = editTextDescription.getText().toString();

                        listener.confirmNewEvent(title, description, timestamp);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        timePicker = view.findViewById(R.id.timePicker);
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
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
            listener = (NewEventDialogListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "NewEventDialogListener not implemented");
        }
    }

    public interface NewEventDialogListener {
        void confirmNewEvent(String title, String description, Date timestamp);
    }
}
