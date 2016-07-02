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
    private Context context;
    private TextView resultTextView;
    private DownloadListener downloadListener;

    public YodaTranslateRequest(Context context, TextView resultTextView, DownloadListener downloadListener) {
        this.context = context;
        this.resultTextView = resultTextView;
        this.downloadListener = downloadListener;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";

        try {
            String encodedParams = URLEncoder.encode(params[0], "UTF-8");
            response = new Request(YODA_URL + encodedParams)
                    .header("X-Mashape-Key", "AmMQsOsdhImshGQIpVG1YdjpsjQNp1AX4cBjsnn3mITt00gVe9")
                    .header("Accept", "text/plain")
                    .execute();
        }
        catch (Exception e) {
            Log.d("TAG", "Exception caught");
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
