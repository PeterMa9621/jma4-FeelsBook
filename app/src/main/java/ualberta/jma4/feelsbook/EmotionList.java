package ualberta.jma4.feelsbook;

import android.content.Context;
import android.webkit.DateSorter;

import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/*
    Class EmotionList is used to store all emotions that the user recorded.
    There are listeners to control to update views if the data is changed.
 */
public class EmotionList implements Serializable
{
    protected ArrayList<Emotion> emotionList;
    protected transient HashMap<Integer, Listener> listeners;
    protected int largest_id;

    public EmotionList()
    {
        emotionList = new ArrayList<Emotion>();
        listeners = new HashMap<Integer, Listener>();
        largest_id = 0;
    }

    public void addEmotion(Emotion emotion)
    {
        largest_id ++;
        emotion.setID(largest_id);
        emotionList.add(emotion);
        sortEmotionList();
        notifyListener();
    }

    public Emotion getEmotion(int id)
    {
        for (Emotion e:emotionList)
        {
            if(e.getID()==id)
                return e;
        }
        return null;
    }

    public void removeEmotion(int id)
    {
        Emotion emotion = getEmotion(id);
        if(emotion==null)
            throw new NullPointerException();
        emotionList.remove(emotion);
        sortEmotionList();
        notifyListener();
    }

    public int getSize()
    {
        return emotionList.size();
    }

    public ArrayList<Emotion> getList()
    {
        return emotionList;
    }

    public void addListener(int id, Listener listener)
    {
        if(listeners==null)
            listeners=new HashMap<Integer, Listener>();

        listeners.put(id, listener);

    }

    public void setEmotion(Emotion emotion, Feeling feeling)
    {
        if(emotionList.contains(emotion))
        {
            emotion.setFeeling(feeling);
            notifyListener();
        }
    }

    public void setComment(Emotion emotion, String comment)
    {
        if(emotionList.contains(emotion))
        {
            emotion.setComment(comment);
            notifyListener();
        }
    }

    public void setDate(Emotion emotion, Date newDate)
    {
        if(emotionList.contains(emotion))
        {
            emotion.setDate(newDate);
            sortEmotionList();
            notifyListener();
        }
    }

    public void sortEmotionList()
    {
        Collections.sort(emotionList, new Comparator<Emotion>() {
            @Override
            public int compare(Emotion o1, Emotion o2) {
                if(o1.getDate().before(o2.getDate()))
                    return 1;
                return -1;
            }
        });
        int id = 1;
        for(Emotion e:emotionList)
        {
            e.setID(id);
            id++;
        }
    }

    public void notifyListener()
    {
        if(listeners==null)
            listeners=new HashMap<Integer, Listener>();
        for(Listener l:listeners.values())
        {
            l.update();
        }
    }

    // Used to clear all stored emotions
    public void clearData()
    {
        emotionList.clear();
        largest_id = 0;
        notifyListener();
    }
}
