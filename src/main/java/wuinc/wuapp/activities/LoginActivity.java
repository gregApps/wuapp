package wuinc.wuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import wuinc.wuapp.R;
import wuinc.wuapp.User;
import wuinc.wuapp.base.BaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button sign_in;
    private Button sign_up;

    private EditText mPseudoView;
    private EditText mPasswordView;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText mPseudoView = (EditText) findViewById (R.id.pseudo);
        EditText mPasswordView = (EditText) findViewById (R.id.password);

        sign_in = (Button) findViewById(R.id.button_sign_in);
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
        String mPseudo = mPseudoView.getText().toString();
        String mPassword = mPasswordView.getText().toString();
        BaseUser load_user = new BaseUser();
        User user_logging;
        if ((mPseudo.equals(""))&&(mPassword.equals(""))){
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a user name/password to attempt to log in", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            try {
                user_logging = load_user.getUserByPseudo(mPseudo);

                if (mPassword.equals(user_logging.getPassword())){
                    Intent login = new Intent(getApplicationContext(), NewsFeedActivity.class);
                    login.putExtra("pseudo_user", mPseudo);
                    startActivity(login);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "The username and password that you entered do not match", Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), "InterruptedException while attempting to access to the data base", Toast.LENGTH_SHORT);
                toast.show();

            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), "ExecutionException while attempting to access to the data base", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    public void onClick_signUp(View v) {
        Intent login = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(login);
    }
}

