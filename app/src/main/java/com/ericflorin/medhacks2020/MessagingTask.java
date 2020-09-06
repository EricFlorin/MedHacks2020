package com.ericflorin.medhacks2020;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.lexrts.model.PostTextRequest;
import com.amazonaws.services.lexrts.model.PostTextResult;

public class MessagingTask extends AsyncTask<PostTextRequest, Integer, PostTextResult> {


    @Override
    protected PostTextResult doInBackground(PostTextRequest... postTextRequests) {

        if (postTextRequests.length == 1)
        {
            PostTextResult textResult = MainActivity.client.postText(postTextRequests[0]);
            Log.d("MainActivity", textResult.getMessage());

            return textResult;
        }

        return null;
    }

    @Override
    protected  void onPostExecute(PostTextResult result)
    {

    }

}
