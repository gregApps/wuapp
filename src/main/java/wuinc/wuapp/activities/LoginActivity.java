package wuinc.wuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import wuinc.wuapp.R;


public class LoginActivity extends AppCompatActivity {
    private Button sign_in = null;
    private Button sign_up = null;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_in=(Button) findViewById(R.id.button_sign_in);
        sign_in.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v){
                onClick_signIn(v); }
        });

        sign_up=(Button)findViewById(R.id.button_sign_up);
        sign_up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v){
                onClick_signUp(v);    }
        });
    }

    public void onClick_signIn(View v) {
        Intent login = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(login);
    }

    public void onClick_signUp(View v) {
        Intent login = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(login);
    }
}

