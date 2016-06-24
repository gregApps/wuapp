package wuinc.wuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import wuinc.wuapp.R;

public class RegisterActivity extends AppCompatActivity {
    private Button register = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=(Button)findViewById(R.id.button_create_account);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                onClick_create_account(v);    }
        });
    }

    public void onClick_create_account(View v) {
        Intent login = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(login);
    }

}
