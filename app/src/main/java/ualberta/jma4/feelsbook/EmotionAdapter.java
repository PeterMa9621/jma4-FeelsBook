package ualberta.jma4.feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EmotionAdapter extends ArrayAdapter
{
    final private int resourceId;
    public EmotionAdapter(@NonNull Context context, int resource, List<Emotion> emotionList) {
        super(context, resource, emotionList);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Emotion emotion = (Emotion) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView text_emotion = view.findViewById(R.id.text_emotion);
        TextView text_comment = view.findViewById(R.id.text_comment);
        TextView text_date = view.findViewById(R.id.edit_text_date);

        text_emotion.setText(emotion.getFeeling().toString());
        if(emotion.getComment().equals(""))
            //text_comment.setVisibility(View.INVISIBLE);
            text_comment.setText("No Comments");
        else
            text_comment.setText(emotion.getComment());
        text_date.setText(emotion.getID() + ". " + emotion.getDateString());

        return view;
    }
}
