package ualberta.jma4.feelsbook;

import android.app.TimePickerDialog;
import android.content.Context;

/*
    The class is only used to remove the method onStop which is in TimePickerDialog.
 */
public class myTimePickerDialog extends TimePickerDialog
{
    public myTimePickerDialog(Context context, OnTimeSetListener callBack,
                              int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
    }

    public myTimePickerDialog(Context context, int theme,
                              OnTimeSetListener callBack, int hourOfDay, int minute,
                              boolean is24HourView) {
        super(context, theme, callBack, hourOfDay, minute, is24HourView);
    }

    @Override
    protected void onStop() {
        // Do nothing
    }
}
