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

public class EditEventDialog extends DialogFragment {
    private TimePicker timePicker;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewError;
    private EditEventDialogListener listener;
    private Event event;

    private boolean error;
    private String errorString;

    public EditEventDialog(boolean error, String errorString, Event event) {
        this.error = error;
        this.errorString = errorString != null ? errorString : "";
        this.event = event;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_new_event, null);

        builder.setView(view).setTitle("Edit Event")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(event.getTimestamp());
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        System.out.println(month);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.set(year, month, day, hour, minute);
                        Date timestamp = calendar.getTime();
                        String title = editTextTitle.getText().toString();
                        String description = editTextDescription.getText().toString();

                        event.setTitle(title);
                        event.setDescription(description);
                        event.setTimestamp(timestamp);

                        listener.confirmEditEvent(event);
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.confirmDeleteEvent(event);
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

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getTimestamp());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePicker.setHour(hour);
        timePicker.setMinute(minute);

        editTextTitle.setText(event.getTitle());
        editTextDescription.setText(event.getDescription());

        if (error) {
            textViewError.setText(errorString);
        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EditEventDialogListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "NewEventDialogListener not implemented");
        }
    }

    public interface EditEventDialogListener {
        void confirmEditEvent(Event event);
        void confirmDeleteEvent(Event event);
    }
}