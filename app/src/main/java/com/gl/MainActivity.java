package com.gl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tagtextview.lib.TagTextView;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity {


    private TagTextView tagTextView, tagTextView2,tagTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagTextView = (TagTextView) findViewById(R.id.tagText);
        tagTextView2 = (TagTextView) findViewById(R.id.tagText_2);
        tagTextView3 = (TagTextView) findViewById(R.id.tagText_3);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagTextView.render();
                tagTextView2.render();
                tagTextView3.render();
            }
        });
    }
}
