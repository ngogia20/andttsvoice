package voice.niksh.com.nikeshvoice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,TextToSpeech.OnInitListener {

    protected static final int REQUEST_OK = 1;
    TextToSpeech t1;
    private double pitch=1.0;
    private double speed=1.0;
    private SeekBar seekPitch;
    private SeekBar seekSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        findViewById(R.id.button1).setOnClickListener(this);
        t1 = new TextToSpeech(this, this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            speech(thingsYouSaid.get(0));
            t1.setLanguage(Locale.UK);
        }
    }


    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            speech("Hi Nikesh, Welcome, How can I help you?");
            t1.setLanguage(Locale.UK);
        }
    }

    private void speech(String stext) {
        t1.speak(stext, TextToSpeech.QUEUE_FLUSH, null, null);
    }

}
