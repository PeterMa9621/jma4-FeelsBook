package ualberta.jma4.feelsbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 *  Student Name: Jingyuan Ma
 *  CCID: jma4
 *  This software is created by Jingyuan Ma individually. No Collaborators.
 */
public class MainActivity extends AppCompatActivity {
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

    // The method is in the listener of every feeling button
    public void onClick(View view)
    {
        Feeling current_feeling = null;
        if (view.getId()==R.id.button_love)
            current_feeling = Feeling.Love;
        else if(view.getId()==R.id.button_joy)
            current_feeling = Feeling.Joy;
        else if(view.getId()==R.id.button_surprise)
            current_feeling = Feeling.Surprise;
        else if(view.getId()==R.id.button_anger)
            current_feeling = Feeling.Anger;
        else if(view.getId()==R.id.button_sadness)
            current_feeling = Feeling.Sadness;
        else if(view.getId()==R.id.button_fear)
            current_feeling = Feeling.Fear;

        if(current_feeling==null)
            Toast.makeText(MainActivity.this, "You haven't set your feeling yet!", Toast.LENGTH_SHORT).show();
        else
        {
            String feeling = current_feeling.toString();
            int id = createEmotion(current_feeling);
            Toast.makeText(MainActivity.this, "Recorded!", Toast.LENGTH_SHORT).show();
            TextView latest_emotion_text = findViewById(R.id.latest_emotion);
            latest_emotion_text.setText("My latest emotion is " + feeling);

            // pass the new emotion's id to the history activity to allow user to add an optional comment
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("newEmotionID", id);
            startActivity(intent);
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
    public static HashMap<Feeling, Integer> getNumEmotion(EmotionList emotionList)
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
    public static String transformToString(HashMap<Feeling, Integer> num, int start, int end)
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

    // Used to create a new emotion
    public int createEmotion(Feeling feeling)
    {
        Emotion emotion = new Emotion(feeling);

        DataController.addEmotion(emotion);

        return emotion.getID();
    }


    // Used to jump to the activity of history
    public void viewHistory(View view)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
