package com.ericflorin.medhacks2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.UserMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // For chat message UI
    private RecyclerView rvMessages;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> messageList;
    private Button btnSendMessage;
    private EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initalize our message list
        messageList = new ArrayList<BaseMessage>();

        etMessage = findViewById(R.id.edittext_chatbox);
        btnSendMessage = findViewById(R.id.button_chatbox_send);
        rvMessages = findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(mMessageAdapter);

        try {
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMessage.getText().toString().isEmpty()) {
                    //save the message and clear editText
                    Toast.makeText(MainActivity.this, "Message sent: " + etMessage.getText().toString(), Toast.LENGTH_SHORT).show();
                    etMessage.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}