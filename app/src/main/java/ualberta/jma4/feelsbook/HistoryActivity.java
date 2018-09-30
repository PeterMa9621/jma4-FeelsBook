package ualberta.jma4.feelsbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *  Student Name: Jingyuan Ma
 *  CCID: jma4
 *  This software is created by Jingyuan Ma individually. No Collaborators.
 */
public class HistoryActivity extends AppCompatActivity {
    private EmotionAdapter emotionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //DataController.loadEmotionList(getApplicationContext());

        EmotionList emotionList = DataController.getEmotionList();

        final ArrayList<Emotion> list = emotionList.getList();

        final ListView listView = findViewById(R.id.emotion_list);

        Intent intent = getIntent();
        int id = intent.getIntExtra("newEmotionID", -1);
        if(id==-1)
            emotionAdapter = new EmotionAdapter(this, R.layout.emotion_list, list);
        else
            emotionAdapter = new EmotionAdapter(this, R.layout.emotion_list, list, id);

        final TextView text_count1 = findViewById(R.id.text_count1);
        final TextView text_count2 = findViewById(R.id.text_count2);

        listView.setAdapter(emotionAdapter);

        update_text_count(text_count1, text_count2);

        emotionList.addListener(2, new Listener()
        {
            @Override
            public void update()
            {
                emotionAdapter.notifyDataSetChanged();
                update_text_count(text_count1, text_count2);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder adb = new AlertDialog.Builder(HistoryActivity.this);
                adb.setMessage("Do you want to delete this record?");

                adb.setNegativeButton("Delete", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        DataController.removeEmotion(list.get(position).getID());
                    }
                });

                adb.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Do nothing
                    }
                });

                adb.show();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryActivity.this, EditRecordActivity.class);
                intent.putExtra("id", list.get(position).getID());
                startActivity(intent);
            }
        });

    }

    // Used to update the emotion counts
    private void update_text_count(TextView text_count1, TextView text_count2)
    {
        HashMap<Feeling, Integer> num = MainActivity.get_num_emotion(DataController.getEmotionList());
        text_count1.setText(MainActivity.transform_to_string(num, 0, 2));
        text_count2.setText(MainActivity.transform_to_string(num, 3, 5));
    }

    // Used to clear all emotions
    public void delete_all(View view)
    {
        if(DataController.getEmotionList().getSize()==0)
            Toast.makeText(getApplicationContext(), "Nothing to delete!", Toast.LENGTH_SHORT).show();
        else
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(HistoryActivity.this);
            adb.setMessage("Are you sure to delete all records?");

            adb.setNegativeButton("Delete", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    DataController.getEmotionList().clearData();
                    Toast.makeText(getApplicationContext(), "Clear all records!", Toast.LENGTH_SHORT).show();
                }
            });

            adb.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // Do nothing
                }
            });

            adb.show();
        }
    }
}
