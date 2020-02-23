package com.example.distance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_LABEL = "REPLY_LABEL";
    public static final String EXTRA_REPLY_LOCATION = "REPLY_LOCATION";
    private EditText mEditLabel;
    private EditText mEditLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mEditLabel = findViewById(R.id.edit_label);
        mEditLocation = findViewById(R.id.edit_location);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditLabel.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    String input = mEditLabel.getText().toString();
                    String location = mEditLocation.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_LABEL, input);
                    replyIntent.putExtra(EXTRA_REPLY_LOCATION, location);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
