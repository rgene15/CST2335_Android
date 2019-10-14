package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity2 extends AppCompatActivity {
     EditText mail;

     public static final String D = "try";

     SharedPreferences sharedPreferences;
     private Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       mail=(EditText) findViewById(R.id.mail);





        sharedPreferences=getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        String email =sharedPreferences.getString("data","");
        if(email.equals(D)){

        }
        else{
            mail.setText(email);
        }



    }

    protected void onPause(){

        sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data",mail.getText().toString());
        editor.commit();
        super.onPause();


}


   public void login(View v){

       button =(Button) findViewById(R.id.Login);
       button.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity2.this, ProfileActivity.class );
               EditText mail=(EditText)findViewById(R.id.mail);
               String emailReal=mail.getText().toString();
               intent.putExtra("EMAIL", emailReal);
               startActivity(intent);
           }


       });
    }







}
