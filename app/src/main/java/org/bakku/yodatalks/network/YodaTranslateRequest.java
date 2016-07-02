package org.bakku.yodatalks.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.bakku.yodatalks.network.request.Request;
import org.bakku.yodatalks.network.request.RequestException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by christian on 02/07/16.
 */
public class YodaTranslateRequest extends AsyncTask<String, Void, String> {

    private static String YODA_URL = "https://yoda.p.mashape.com/yoda?sentence=";
    private static String API_KEY = "AmMQsOsdhImshGQIpVG1YdjpsjQNp1AX4cBjsnn3mITt00gVe9";

    private TextView resultTextView;
    private DownloadListener downloadListener;

    public YodaTranslateRequest(TextView resultTextView, DownloadListener downloadListener) {
        this.resultTextView = resultTextView;
        this.downloadListener = downloadListener;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";

        try {
            String encodedParams = URLEncoder.encode(params[0], "UTF-8");
            response = new Request(YODA_URL + encodedParams)
                    .header("X-Mashape-Key", API_KEY)
                    .header("Accept", "text/plain")
                    .execute();
        }
        catch (Exception e) {
            cancel(true);
        }

        return response;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        downloadListener.onError();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        resultTextView.setText(s);
        resultTextView.setVisibility(View.VISIBLE);
        downloadListener.onSuccess();
    }
}
