package ualberta.jma4.feelsbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emotion implements Serializable
{
    private Feeling emotion;
    private String comment;
    private Date date;
    private int id;

    public Emotion(Feeling emotion)
    {
        this.emotion = emotion;
        date = new Date();
        comment = null;
    }

    public Feeling getEmotion()
    {
        return emotion;
    }

    public String getComment()
    {
        if(comment==null)
            return "";
        return comment;
    }

    public String getDateString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return simpleDateFormat.format(date);
    }

    public Date getDate()
    {
        return date;
    }

    public void setComment(String newComment)
    {
        comment = newComment;
    }

    public void setEmotion(Feeling newEmotion)
    {
        emotion = newEmotion;
    }

    public int getID()
    {
        return id;
    }

    public void setID(int newID)
    {
        id = newID;
    }

    public void setDate(Date newDate)
    {
        date = newDate;
    }

    public Emotion clone()
    {
        Emotion newEmotion = new Emotion(emotion);
        newEmotion.setComment(comment);
        newEmotion.setID(id);
        newEmotion.setDate(date);
        return newEmotion;
    }
}
