package wuinc.wuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import wuinc.wuapp.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent next_intent = new Intent(getApplicationContext(), LoginActivity.class);
                overridePendingTransition(R.anim.slide_fadein,R.anim.slide_fadeout);
                startActivity(next_intent);
            }
        }, 3000);

    }
}

