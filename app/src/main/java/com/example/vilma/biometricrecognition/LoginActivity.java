package com.example.vilma.biometricrecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


/**
 * MAIN CLASS
 * Created by vilma on 1/20/2018.
 */

/*
    All classes should extend BaseActivity to be able to see the toolbar
 */
public class LoginActivity extends BaseActivity {

    TextView txtSignup;
    EditText txtUsernameBox;
    EditText txtPasswordBox;
    Button btnLogin;
    String[] userData = new String[2];
    String txtUsername;
    String txtFirstname;
    String txtLastname;
    String txtPassword;
    String txtRePassword;
    boolean requirSatisfied;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //This function hold all database connectivity initialization.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //so it doesnt show any text in the toolbar

        //Use frame layout instead of setContentView to include the menu and toolbar
        //FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        //getLayoutInflater().inflate(R.layout.activity_login, contentFrameLayout);

        initUI();
    }


    private void initUI() {
        txtUsernameBox = (EditText) findViewById(R.id.txtUsername);
        txtPasswordBox = (EditText) findViewById(R.id.txtPassWord);
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        btnLogin = (Button) findViewById(R.id.button2);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, Register.class);
                startActivity(registerIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //this method does like pretty much all the work....like seriously-_-
                tryToLogin(v);
            }
        });
    }

    /*
     * Method verifies that the username and password matches the user information stored in the
     * dynamo db.
     */
    private void tryToLogin(View v) {
            txtUsername = txtUsernameBox.getText().toString();
            txtPassword = txtPasswordBox.getText().toString();
            userData[0] = txtUsername;
            userData[1] = txtPassword;
            if (txtUsername.matches("\\w")  || txtUsername.toString().equals("Enter a Username")
                    || txtPassword.matches("\\w") || txtPassword.toString().equals("Enter Password")) {
                Toast.makeText(this, "Please enter a valid username or Password", Toast.LENGTH_LONG).show();
            }else{
                DbManager.checkTable checkTable = new DbManager.checkTable(this, userData,this);
                checkTable.execute();
                try {
                    checkTable.get();
                } catch (InterruptedException e) {
                    Toast.makeText(this, "shit we broke it", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Toast.makeText(this, "shit we broke it", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
    }

    public void getUserData(String[] userData){
        txtFirstname = userData[1];
        txtLastname = userData[2];
        txtRePassword = userData[3];
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Nice Pic!", Toast.LENGTH_LONG).show();
        }
    }
*/
    public void initializeResult(Boolean result){
        requirSatisfied = result;
        if(requirSatisfied){

            Intent intentBundle = new Intent(LoginActivity.this,HomeActivity.class);
       /*     Bundle bundle = new Bundle();
          
            bundle.putString("FirstName", txtFirstname);
            bundle.putString("LastName", txtLastname);
            bundle.putString("Username", txtUsername);
            intentBundle.putExtras(bundle);
         */   startActivity(intentBundle);
           // finish();

            //Stores first, last, and user name in the shared preferences
            SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("FirstName", txtFirstname);
            editor.putString("LastName", txtLastname);
            editor.putString("Username", txtUsername);
            editor.commit();

        }else{
            Toast.makeText(this, "Please enter a correct Username or Password!"
                    , Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please enter your username or register a new account!"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}