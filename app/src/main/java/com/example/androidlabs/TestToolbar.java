package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;



import android.content.DialogInterface;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;


public class TestToolbar extends AppCompatActivity {
    Toolbar toolbar;
    String inputText = "You clicked on the overflow menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setContentView(R.layout.activity_test_toolbar);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String s = "This is the initial message";

        switch (item.getItemId()) {
            //what to do when the menu item is selected:

            case R.id.zombie:
                alertExample();
                break;
            case R.id.back:
                Snackbar sb = Snackbar.make(findViewById(android.R.id.content), "Are you sure you want to exit?", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                sb.show();
                break;
            case R.id.item1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                break;

            case R.id.overFlow:
                Toast.makeText(this, inputText, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.view, null);
          final EditText et = (EditText) middle.findViewById(R.id.view_edit_text);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Positive", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                inputText = et.getText().toString();
            }
        })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel

                    }
                }).setView(middle);

        builder.create().show();
    }

}