package ualberta.jma4.feelsbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    Class Emotion is used to save one record that includes the
    user's feeling, comment, the date of the record and an unique id of the record.
 */
public class Emotion implements Serializable
{
    private Feeling feeling;
    private String comment;
    private Date date;
    private int id;

    public Emotion(Feeling feeling)
    {
        this.feeling = feeling;
        date = new Date();
        comment = null;
    }

    public Feeling getFeeling()
    {
        return feeling;
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

    public void setFeeling(Feeling newFeeling)
    {
        feeling = newFeeling;
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

    @Override
    public Emotion clone()
    {
        Emotion newEmotion = new Emotion(feeling);
        newEmotion.setComment(comment);
        newEmotion.setID(id);
        newEmotion.setDate(date);
        return newEmotion;
    }
}
