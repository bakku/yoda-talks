package org.bakku.yodatalks;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bakku.yodatalks.network.DownloadListener;
import org.bakku.yodatalks.network.YodaTranslateRequest;

public class MainActivity extends AppCompatActivity implements DownloadListener {

    private EditText editText;
    private TextView resultText;
    private Button translateButton;
    private LinearLayout baseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseLayout = (LinearLayout) findViewById(R.id.baseLayout);
        editText = (EditText) findViewById(R.id.editText);
        resultText = (TextView) findViewById(R.id.resultText);
        translateButton = (Button) findViewById(R.id.translateButton);
    }

    public void translate(View view) {
        new YodaTranslateRequest(this, resultText, this).execute(this.editText.getText().toString());
        translateButton.setText(R.string.button_loading);
        translateButton.setEnabled(false);
    }

    @Override
    public void onSuccess() {
        translateButton.setText(R.string.button_label);
        translateButton.setEnabled(true);
    }

    @Override
    public void onError() {
        Snackbar.make(baseLayout, R.string.error_snackbar, Snackbar.LENGTH_INDEFINITE).
                setAction(R.string.retry_snackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.translate(null);
                    }
                })
                .show();

        translateButton.setText(R.string.button_label);
        translateButton.setEnabled(true);
    }
}
