package ualberta.jma4.feelsbook;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class UtilTest
{
    @Test
    public void saveReadTest()
    {
        Emotion emotionA = new Emotion(Feeling.Fear);
        emotionA.setComment("I am scared now!");

        EmotionList emotionList = new EmotionList();
        emotionList.addEmotion(emotionA);

        Emotion emotionB = new Emotion(Feeling.Joy);
        emotionB.setComment("I am happy now!");

        emotionList.addEmotion(emotionB);

        Util.writeObjectToFile(emotionList, "test.txt");
        EmotionList testList = (EmotionList) Util.readObjectFromFile("test.txt");

        Emotion testEmotion = testList.getEmotion(1);
        assertTrue("testEmotion is not equal to EmotionA", testEmotion.getEmotion().equals(emotionA.getEmotion()));
        testEmotion = testList.getEmotion(2);
        assertTrue("testEmotion is not equal to EmotionB", testEmotion.getComment().equals("I am happy now!"));
    }

    @Test
    public void test()
    {
        String str = "%-10s:%d, %-10s:%d";
        str = String.format(str, "Joy",1, "Sadness", 5);
        System.out.print(str + "\n");
        str = "%-10s:%d, %-10s:%d";
        str = String.format(str, "Surprise", 3, "Fear", 2);
        System.out.print(str);

        String date = "2018-9-16T14:16:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try
        {
            Date d = simpleDateFormat.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
