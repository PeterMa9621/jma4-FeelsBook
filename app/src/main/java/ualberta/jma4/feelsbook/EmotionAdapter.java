package ualberta.jma4.feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.UpdateAppearance;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/*
    This class is used to rewrite the ArrayAdapter.
 */
public class EmotionAdapter extends ArrayAdapter
{
    final private int resourceId;
    private int newEmotionId = -1;

    public EmotionAdapter(@NonNull Context context, int resource, List<Emotion> emotionList) {
        super(context, resource, emotionList);
        resourceId = resource;
    }

    public EmotionAdapter(@NonNull Context context, int resource, List<Emotion> emotionList, int newEmotionId) {
        super(context, resource, emotionList);
        resourceId = resource;
        this.newEmotionId = newEmotionId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        final Emotion emotion = (Emotion) getItem(position);
        final View view = LayoutInflater.from(getContext()).inflate(resourceId, null);


        TextView text_emotion = view.findViewById(R.id.text_emotion);
        final TextView text_comment = view.findViewById(R.id.text_comment);
        TextView text_date = view.findViewById(R.id.edit_text_date);
        final EditText editText = view.findViewById(R.id.add_optional_comment);

        final Button button = view.findViewById(R.id.add_comment_button);

        text_emotion.setText(emotion.getFeeling().toString());
        if(emotion.getComment().equals(""))
            //text_comment.setVisibility(View.INVISIBLE);
            text_comment.setText("No Comments");
        else
            text_comment.setText(emotion.getComment());
        text_date.setText(emotion.getID() + ". " + emotion.getDateString());

        if(emotion.getID()==newEmotionId)
        {
            text_comment.setText("Add your comment here if you want.");
            editText.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            newEmotionId=-1;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataController.getEmotionList().setComment(emotion, editText.getText().toString());
                    editText.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                }
            });
        }

        return view;
    }
}
