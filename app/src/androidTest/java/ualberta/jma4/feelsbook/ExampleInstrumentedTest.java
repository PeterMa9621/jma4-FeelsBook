package ualberta.jma4.feelsbook;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ualberta.jma4.feelsbook", appContext.getPackageName());
    }

    @Test
    public void readLoadTest()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        EmotionList emotionList = DataController.getEmotionList();
        Emotion emotionA = new Emotion(Feeling.Fear);
        emotionA.setComment("I am scared now!");

        emotionList.addEmotion(emotionA);

        Emotion emotionB = new Emotion(Feeling.Joy);
        emotionB.setComment("I am happy now!");

        emotionList.addEmotion(emotionB);

        DataController.saveEmotionList(appContext);

        emotionList.removeEmotion(1);

        assertTrue("EmotionList size is not 1", DataController.getEmotionList().getSize()==1);

        DataController.loadEmotionList(appContext);

        assertTrue("EmotionList size is not 2", DataController.getEmotionList().getSize()==2);

        assertTrue("testEmotion is not equal to EmotionB", DataController.getEmotionList().getEmotion(2).getComment().equals("I am happy now!"));
    }
}
