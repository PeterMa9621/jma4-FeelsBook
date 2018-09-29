package ualberta.jma4.feelsbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditRecordActivity extends AppCompatActivity {

    private Feeling changedEmotion = null;
    private Feeling prev_emotion = null;
    private String prev_comment = null;
    private Emotion current_emotion = null;

    private String date;
    //private TextView edit_text_date = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = getIntent();
        int emotion_id = intent.getIntExtra("id", 0);

        if(emotion_id==0)
        {
            System.out.print("Emotion id cannot be 0!");
            throw new NullPointerException();
        }

        current_emotion = DataController.getEmotionList().getEmotion(emotion_id);

        // Init the Data
        TextView text_date = findViewById(R.id.edit_text_date);

        init_dateSelector((Button) findViewById(R.id.button_change_date), text_date);

        TextView text_current_emotion = findViewById(R.id.edit_text_current_emotion);


        date = current_emotion.getDateString();
        updateDate(text_date);

        String str = "Current emotion is: " + current_emotion.getFeeling().toString();

        text_current_emotion.setText(str);

        EditText editText = findViewById(R.id.edit_text_comment);

        prev_emotion = current_emotion.getFeeling();
        prev_comment = current_emotion.getComment();
        editText.setText(prev_comment);

        Spinner spinner = findViewById(R.id.spinner);

        final ArrayList<String> list = new ArrayList<>();
        for(Feeling f:Feeling.values())
        {
            list.add(f.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(list.indexOf(current_emotion.getFeeling().toString()), true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changedEmotion = Feeling.valueOf(list.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Used to update the string that shows current date
    private void updateDate(TextView textView)
    {
        textView.setText("Current Date: " + date);
    }

    // Used to init the date selector
    public void init_dateSelector(Button btn, final TextView textView)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current_emotion.getDate());
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new myTimePickerDialog(EditRecordActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        date += "T" + hourOfDay + ":" + minute + ":00";
                        updateDate(textView);
                    }
                }, hour, minute,true).show();

                new myDatePickerDialog(EditRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = year+"-"+(month+1)+"-"+dayOfMonth;
                    }
                }, year, month, day).show();
            }
        });
    }

    // Used to apply all changes of this record.
    // The method is in the listener of apply button.
    public void apply(View view)
    {
        boolean changed = false;
        EditText editText = findViewById(R.id.edit_text_comment);
        String comment = editText.getText().toString();
        System.out.print(comment);
        if(!comment.equals("") && !comment.equals(prev_comment))
        {
            DataController.getEmotionList().setComment(current_emotion, comment);
            changed = true;
        }

        if(changedEmotion!=null && !changedEmotion.equals(prev_emotion))
        {
            DataController.getEmotionList().setEmotion(current_emotion, changedEmotion);
            changedEmotion = null;
            changed = true;
        }

        if(!date.equals(current_emotion.getDateString()))
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date d = simpleDateFormat.parse(date);
                DataController.getEmotionList().setDate(current_emotion, d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            changed = true;
        }

        if(changed)
        {
            Toast.makeText(this, "Your record has updated!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else
            Toast.makeText(this, "Nothing new to update!", Toast.LENGTH_SHORT).show();

    }
}
