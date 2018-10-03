package ualberta.jma4.feelsbook;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/*
    Used to test if the class EmotionList works well
 */
public class EmotionListTest
{
    @Test
    public void emotionListAddTest()
    {
        EmotionList emotionList = new EmotionList();
        Emotion emotionA = new Emotion(Feeling.Anger);

        emotionList.addEmotion(emotionA);
        assertTrue("Emotion from emotionList is not equal to emotionA", emotionList.getEmotion(1).equals(emotionA));
    }
    @Test
    public void emotionListGetTest()
    {
        EmotionList emotionList = new EmotionList();
        Emotion emotionA = new Emotion(Feeling.Anger);

        emotionList.addEmotion(emotionA);
        assertTrue("Emotion from emotionList is not equal to emotionA", emotionList.getEmotion(1).equals(emotionA));
    }

    @Test
    public void emotionListRemoveTest()
    {
        EmotionList emotionList = new EmotionList();
        Emotion emotionA = new Emotion(Feeling.Anger);

        emotionList.addEmotion(emotionA);
        emotionList.removeEmotion(1);
        assertTrue("EmotionList'size should be zero!", emotionList.getSize()==0);
    }

    @Test
    public void emotionListCheckID()
    {
        EmotionList emotionList = new EmotionList();
        Emotion emotionA = new Emotion(Feeling.Anger);
        Emotion emotionB = new Emotion(Feeling.Joy);
        Emotion emotionC = new Emotion(Feeling.Fear);
        emotionList.addEmotion(emotionA);
        emotionList.addEmotion(emotionB);
        emotionList.addEmotion(emotionC);
        assertTrue("EmotionList'size should be 3!", emotionList.getSize()==3);
        emotionList.removeEmotion(1);
        assertTrue("EmotionList'size should be 2!", emotionList.getSize()==2);
        assertTrue("emotionB's ID should be 1!", emotionB.getID()==1);
        assertTrue("emotionA's ID should be 2!", emotionA.getID()==2);
    }

    @Test
    public void emotionListChangeDate()
    {
        EmotionList emotionList = new EmotionList();
        Emotion emotionA = new Emotion(Feeling.Anger);
        Emotion emotionB = new Emotion(Feeling.Joy);
        Emotion emotionC = new Emotion(Feeling.Fear);
        Emotion emotionD = new Emotion(Feeling.Love);
        Emotion emotionE = new Emotion(Feeling.Surprise);
        emotionList.addEmotion(emotionA);
        emotionList.addEmotion(emotionB);
        emotionList.addEmotion(emotionC);
        emotionList.addEmotion(emotionD);
        emotionList.addEmotion(emotionE);

        Date newDate = new Date(2018-1900, 10-1, 15);
        emotionList.setDate(emotionB, newDate);

        newDate = new Date(2018-1900, 9-1, 14);
        emotionList.setDate(emotionC, newDate);

        newDate = new Date(2018-1900, 9-1, 15);
        emotionList.setDate(emotionD, newDate);

        assertTrue("emotionC's ID should be 5!", emotionC.getID()==5);
        assertTrue("emotionD's ID should be 4!", emotionD.getID()==4);
        assertTrue("emotionB's ID should be 1!", emotionB.getID()==1);
    }
}
