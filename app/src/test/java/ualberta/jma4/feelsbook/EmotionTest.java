package ualberta.jma4.feelsbook;

import org.junit.Test;

import static org.junit.Assert.*;

/*
    Used to test if the class Emotion works well
 */
public class EmotionTest {
    @Test
    public void EmotionTest()
    {
        Emotion emotion = new Emotion(Feeling.Anger);
        assertTrue("Feeling is not anger!", emotion.getFeeling().equals(Feeling.Anger));
        String comment = "I was very Anger!";
        emotion.setComment(comment);
        assertTrue("Comment is not correct!", emotion.getComment().equals(comment));
        System.out.print(emotion.getDateString() + "\n");
        System.out.print(emotion.getFeeling().toString());
        emotion.setFeeling(Feeling.Joy);
        assertTrue("Feeling is not Joy!", emotion.getFeeling().equals(Feeling.Joy));
    }
}