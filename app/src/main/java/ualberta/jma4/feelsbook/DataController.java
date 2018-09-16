package ualberta.jma4.feelsbook;

import android.content.Context;
import android.content.SharedPreferences;

public class DataController
{
    private static EmotionList emotionList = null;
    static String preFile = "emotionList";

    static public EmotionList getEmotionList()
    {
        if(emotionList==null)
            emotionList = new EmotionList();
        return emotionList;
    }

    static void addEmotion(Emotion emotion)
    {
        getEmotionList().addEmotion(emotion);
    }

    static void removeEmotion(int id)
    {
        getEmotionList().removeEmotion(id);
    }

    static void saveEmotionList(Context context)
    {
        SharedPreferences setting = context.getSharedPreferences(preFile, Context.MODE_PRIVATE);

        Util.saveObjectToSharepreference(setting, emotionList, preFile);
    }

    static void loadEmotionList(Context context)
    {
        SharedPreferences setting = context.getSharedPreferences(preFile, Context.MODE_PRIVATE);
        EmotionList list = (EmotionList) Util.loadObjectFromSharepreference(setting, preFile);
        if(list==null)
            emotionList = new EmotionList();
        else
            emotionList = list;
    }
}
