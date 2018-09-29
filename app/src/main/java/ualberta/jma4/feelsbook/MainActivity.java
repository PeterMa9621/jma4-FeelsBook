package ualberta.jma4.feelsbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Feeling current_feeling = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear_data(getApplicationContext(), "emotionList");

        DataController.loadEmotionList(getApplicationContext());

        final TextView latest_emotion_text = findViewById(R.id.latest_emotion);

        if(DataController.getEmotionList().getSize()==0)
            latest_emotion_text.setText("I haven't recorded any feeling yet!");
        else
            latest_emotion_text.setText("My latest feeling is " + DataController.getEmotionList().getEmotion(DataController.getEmotionList().getSize()).getFeeling().toString());

        DataController.getEmotionList().addListener(1, new Listener() {
            @Override
            public void update()
            {
                if(DataController.getEmotionList().getSize()==0)
                    latest_emotion_text.setText("I haven't recorded any feeling yet!");
                else
                    latest_emotion_text.setText("My latest feeling is " + DataController.getEmotionList().getEmotion(DataController.getEmotionList().getSize()).getFeeling().toString());
                DataController.saveEmotionList(getApplicationContext());
            }
        });
    }

    // The method is in the listener of the add button
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_add:
                if(current_feeling==null)
                    Toast.makeText(MainActivity.this, "You haven't set your feeling yet!", Toast.LENGTH_SHORT).show();
                else
                {
                    String feeling = current_feeling.toString();
                    int id = CreateEmotion(current_feeling);
                    Toast.makeText(MainActivity.this, "Recorded!", Toast.LENGTH_SHORT).show();
                    TextView latest_emotion_text = findViewById(R.id.latest_emotion);
                    latest_emotion_text.setText("My latest emotion is " + feeling);

                    TextView feeling_record = findViewById(R.id.feeling_record);
                    feeling_record.setText("");

                    EditText comment_text = findViewById(R.id.comment_text);
                    comment_text.setText("");

                    // pass the new emotion's id to the history activity to allow user to add an optional comment
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    intent.putExtra("newEmotionID", id);
                    startActivity(intent);
                }
        }
    }

    // The method is used to delete the data stored in SharedPreferences.
    public static void clear_data(Context context, String preFile)
    {
        SharedPreferences setting = context.getSharedPreferences(preFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.clear();
        editor.commit();
    }

    // The method is used to count emotions
    public static HashMap<Feeling, Integer> get_num_emotion(EmotionList emotionList)
    {
        HashMap<Feeling, Integer> num = new HashMap<>();
        for(Feeling f:Feeling.values())
        {
            num.put(f, 0);
        }

        for(Emotion e:emotionList.getList())
        {
            num.put(e.getFeeling(), num.get(e.getFeeling())+1);
        }

        return num;
    }

    // Used to transform the count emotions to string
    public static String transform_to_string(HashMap<Feeling, Integer> num, int start, int end)
    {
        String str = "";
        int index = 0;
        for(Feeling f:num.keySet())
        {
            if(index >= start && index <= end)
            {
                str += "%-10s:%d";
                //str += f.toString() + ":" + num.get(f);
                if(index != end)
                    str += ", ";
                str = String.format(str, f.toString(), num.get(f));
            }
            index ++;
        }
        return str;
    }

    // Used to show the menu of feelings.
    // The method is in the listener of Feeling button
    public void ShowEmotionMenu(View view)
    {
        final PopupMenu popupMenu = new PopupMenu(this, view);
        final TextView feeling_record = findViewById(R.id.feeling_record);

        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                Toast.makeText(MainActivity.this, "Record emotion " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                feeling_record.setText("My feeling for now is " + item.getTitle().toString());
                current_feeling = Feeling.valueOf(item.getTitle().toString());
                //CreateEmotion(Feeling.valueOf(item.getTitle().toString()));
                return false;
            }
        });

        popupMenu.show();
    }

    // Used to create a new emotion
    public int CreateEmotion(Feeling feeling)
    {
        Emotion emotion = new Emotion(feeling);
        EditText comment_text = findViewById(R.id.comment_text);
        String comment = comment_text.getText().toString();
        if(!comment.equals(""))
            emotion.setComment(comment);

        DataController.addEmotion(emotion);

        current_feeling = null;

        return emotion.getID();
    }


    // Used to jump to the activity of history
    public void ViewHistory(View view)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
