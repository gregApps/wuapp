package wuinc.wuapp.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import wuinc.wuapp.R;

public class ReadVideoActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("CHEMIN");
        setContentView(R.layout.activity_video);
        VideoView video = (VideoView) findViewById(R.id.videoView);
        video.setMediaController(new MediaController(this));
        video.setVideoURI(Uri.parse(path));
        video.start();
    }

}
