package com.example.androidlabs;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private List<Message> sMessage = new ArrayList<>();
    private Button Send;
    private Button Receive;
    private MyDBHandler helper;
    private ChatAdapter messageAdapter;
    private static final String ACTIVITY_NAME = "Chat Window Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        listView = (ListView)findViewById(R.id.listitems);
        editText = (EditText)findViewById(R.id.editChat);
        Send = (Button)findViewById(R.id.btnsend);
        Receive = (Button)findViewById(R.id.btnreceive);

        helper = new MyDBHandler(this);
        Cursor cursor = helper.viewData();

        while(cursor.moveToNext()){
            String s = cursor.getString( cursor.getColumnIndex(helper.COL_MESSAGE));
            Message allow = new Message(s, true);
            sMessage.add( allow);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString( cursor.getColumnIndex(helper.COL_MESSAGE) ) );
        }

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMsg = editText.getText().toString();
                Message messaa = new Message(newMsg, true);
                sMessage.add( messaa );
                helper.insertData(newMsg);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
                getMessage();
            }
        });

        Receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    public void getMessage(){
        String sendMessage = editText.getText().toString();
        if ( !sendMessage.equals("")){
            Message model = new Message(sendMessage, true);
            sMessage.add(model);

            ChatAdapter adt = new ChatAdapter(sMessage, getApplicationContext());
            listView.setAdapter(adt);
            editText.setText("");
        }
    };

    public void sendMessage(){
        String sendMessage = editText.getText().toString();
        if ( !sendMessage.equals("")){
            Message model = new Message(sendMessage, false);
            sMessage.add(model);

            ChatAdapter adt = new ChatAdapter(sMessage, getApplicationContext());
            listView.setAdapter(adt);
            editText.setText("");
        }
    }
}

class ChatAdapter extends BaseAdapter{

    private List<Message> smessageModels;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<Message> messageModels, Context context) {
        this.smessageModels = messageModels;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return smessageModels.size();
    }

    @Override
    public Object getItem(int position) {
        return smessageModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newview = convertView;

        if (newview == null){
            if (smessageModels.get(position).isSend()){

                newview = inflater.inflate(R.layout.receive, null);
            }else {

                newview = inflater.inflate(R.layout.send, null);
            }
            TextView messageText = (TextView)newview.findViewById(R.id.messageText);
            messageText.setText(smessageModels.get(position).getMsg());
        }
        return newview;
    }
}