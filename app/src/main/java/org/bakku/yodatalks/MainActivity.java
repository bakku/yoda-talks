package org.bakku.yodatalks;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.bakku.yodatalks.network.YodaDownloadListener;
import org.bakku.yodatalks.network.YodaTranslateRequest;
import org.bakku.yodatalks.utility.KeyboardHider;

public class MainActivity extends AppCompatActivity implements YodaDownloadListener {

    private EditText editText;
    private TextView resultText;
    private Button translateButton;
    private LinearLayout baseLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseLayout = (LinearLayout) findViewById(R.id.baseLayout);
        editText = (EditText) findViewById(R.id.editText);
        resultText = (TextView) findViewById(R.id.resultText);
        translateButton = (Button) findViewById(R.id.translateButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_GO) {
                    translate(null);
                    handled = true;
                    KeyboardHider.hideKeyboard(MainActivity.this);
                }

                return handled;
            }
        });
    }

    public void translate(View view) {
        new YodaTranslateRequest(this).execute(this.editText.getText().toString());
        translateButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(String response) {
        resultText.setText(response);
        resultText.setVisibility(View.VISIBLE);
        translateButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        Snackbar.make(baseLayout, R.string.error_snackbar, Snackbar.LENGTH_LONG).
                setAction(R.string.retry_snackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.translate(null);
                    }
                })
                .show();

        translateButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
