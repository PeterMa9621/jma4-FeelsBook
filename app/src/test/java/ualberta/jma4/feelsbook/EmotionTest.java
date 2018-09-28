package ualberta.jma4.feelsbook;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
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
        emotion.setEmotion(Feeling.Joy);
        assertTrue("Feeling is not Joy!", emotion.getFeeling().equals(Feeling.Joy));
    }
}