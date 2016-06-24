package wuinc.wuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import wuinc.wuapp.R;
import wuinc.wuapp.User;
import wuinc.wuapp.base.BaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText mPseudoView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mBirthDateView;
    private EditText mEmailAddressView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmationView;

    private Button register = null;

    private String mPseudo= "";
    private String mFirstName= "";
    private String mLastName= "";
    private String mBirthDate= "";
    private String mEmailAddress= "";
    private String mPassword= "";
    private String mPasswordConfirmation= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPseudoView = (EditText) findViewById (R.id.pseudo);
        mFirstNameView = (EditText) findViewById (R.id.firstName);
        mLastNameView = (EditText) findViewById (R.id.lastName);
        mBirthDateView = (EditText) findViewById (R.id.birthDate);
        mEmailAddressView = (EditText) findViewById (R.id.email);
        mPasswordView = (EditText) findViewById (R.id.password);
        mPasswordConfirmationView = (EditText) findViewById (R.id.password_2);

        register=(Button)findViewById(R.id.button_create_account);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                onClick_create_account(v);    }
        });
    }

    public void onClick_create_account(View v) {
        mPseudo = mPseudoView.getText().toString();
        mFirstName = mFirstNameView.getText().toString();
        mLastName = mLastNameView.getText().toString();
        mBirthDate = mBirthDateView.getText().toString();
        mEmailAddress = mEmailAddressView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        mPasswordConfirmation = mPasswordConfirmationView.getText().toString();

        BaseUser create_user = new BaseUser();
        User new_user;
        if((mPseudo.equals(""))&&(mEmailAddress.equals(""))&&(mPassword.equals(""))&&(mPasswordConfirmation.equals(""))){
            Toast toast = Toast.makeText(getApplicationContext(), "Please check your email and fill all required fields.", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            if(!mPassword.equals(mPasswordConfirmation)){
                Toast toast = Toast.makeText(getApplicationContext(), "Both passwords don't match", Toast.LENGTH_SHORT);
                toast.show();
                mPasswordView.setText(null);
                mPasswordConfirmationView.setText(null);
            }else {

                new_user = new User(mPseudo, mEmailAddress, mPassword);
                new_user.setFirstName(mFirstName);
                new_user.setLastName(mLastName);
                new_user.setBirthDate(mBirthDate);

                try{
                    create_user.add(new_user);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "InterruptedException while attempting to create an account", Toast.LENGTH_SHORT);
                    toast.show();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "ExecutionException while attempting to create an account", Toast.LENGTH_SHORT);
                    toast.show();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "ParseException while attempting to create an account", Toast.LENGTH_SHORT);
                    toast.show();
                }

                Intent newsfeed = new Intent(getApplicationContext(), NewsFeedActivity.class);

                newsfeed.putExtra("new_account", true);

                startActivity(newsfeed);
            }
        }

    }

}
