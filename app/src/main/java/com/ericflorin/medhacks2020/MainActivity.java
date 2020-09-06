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

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexrts.AmazonLexRuntimeClient;
import com.amazonaws.services.lexrts.model.PostTextRequest;
import com.amazonaws.services.lexrts.model.PostTextResult;
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
    public static List<Message> messageList;
    private Button btnSendMessage;
    private EditText etMessage;

    // Communicating with AWS
    public static AmazonLexRuntimeClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initalize our message list
        messageList = new ArrayList<Message>();

        etMessage = findViewById(R.id.edittext_chatbox);
        btnSendMessage = findViewById(R.id.button_chatbox_send);

        // Setup the messaging UI
        rvMessages = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(mMessageAdapter);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMessage.getText().toString().isEmpty()) {

                    User sender = new User(getResources().getString(R.string.userid), "User");
                    Message temp = new Message(etMessage.getText().toString(), sender, 0);

                    messageList.add(messageList.size(), temp);
                    mMessageAdapter.notifyDataSetChanged();

                    //save the message and clear editText
                    Toast.makeText(MainActivity.this, "Message sent: " + etMessage.getText().toString(), Toast.LENGTH_SHORT).show();
                    etMessage.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Initialize AWS Credientials
        BasicAWSCredentials creds = new BasicAWSCredentials(getResources().getString(R.string.accessKey), getResources().getString(R.string.secretKey));
        client = new AmazonLexRuntimeClient(creds);
        client.setRegion(Region.getRegion(Regions.US_EAST_1));


        PostTextRequest textRequest = new PostTextRequest();
        textRequest.setBotName("ScheduleAppointment");
        textRequest.setBotAlias("Prod");
        textRequest.setUserId("testuser");
        textRequest.setInputText("Hi!");
        new MessagingTask().execute(textRequest);

    }
}