package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;
public class ProfileActivity extends AppCompatActivity {

    ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView userEmail;
    Button but;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle intent = getIntent().getExtras();
        String mailing=intent.getString("EMAIL");
        TextView editText=(TextView) findViewById(R.id.email);
        editText.setText(mailing);


     /* userEmail= findViewById(R.id.email);
      Intent intent = getIntent();
      String email = intent.getStringExtra("EMAIL");
      userEmail.setText(email);*/


        Log.e(ACTIVITY_NAME,"onCreate()");
        mImageButton=(ImageButton) findViewById(R.id.imagePic);


    }

    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    protected void onStart(){
        Log.e(ACTIVITY_NAME,"onStart()");
        super.onStart();
    }

    protected void onResume(){
        Log.e(ACTIVITY_NAME,"onResume()");
        super.onResume();
    }
    protected void onPause(){
        Log.e(ACTIVITY_NAME,"onPause()");
        super.onPause();
    }
    protected void onStop(){
        Log.e(ACTIVITY_NAME,"onStop()");
        super.onStop();
    }
    protected void onDestroy(){
        Log.e(ACTIVITY_NAME,"onDestroy()");
        super.onDestroy();
    }
    public void chat(View v) {
        but = (Button) findViewById(R.id.chat);
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(intent);
            }


        });
    }
        public void weather (View view){
            Button weather = (Button) findViewById(R.id.Weather);
            weather.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileActivity.this, WeatherForecast.class);
                    startActivity(intent);
                }


            });

        }


    }