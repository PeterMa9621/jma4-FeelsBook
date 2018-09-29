package ualberta.jma4.feelsbook;

import android.app.DatePickerDialog;
import android.content.Context;

/*
    The class is only used to remove the method onStop which is in DatePickerDialog.
 */
public class myDatePickerDialog extends DatePickerDialog
{
    public myDatePickerDialog(Context context, OnDateSetListener callBack,
                            int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    public myDatePickerDialog(Context context, int theme,
                            OnDateSetListener callBack, int year, int monthOfYear,
                            int dayOfMonth) {
        super(context, theme, callBack, year, monthOfYear, dayOfMonth);
    }

    @Override
    protected void onStop() {
        // Do nothing
    }

}
