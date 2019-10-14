package com.example.androidlabs;


import android.widget.EditText;

public class Message {
    private String msges;
    private boolean send;

    public Message(String msges, boolean send) {
        this.msges = msges;
        this.send = send;
    }

    public String getMsg() {
        return msges;
    }

    public void setMsg(String msges) {
        this.msges = msges;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        send = send;
    }
}