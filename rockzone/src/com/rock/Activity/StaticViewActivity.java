package com.rock.Activity;

import android.os.Bundle;
import android.widget.TextView;
;

/**
 *关于我们
 */
public class StaticViewActivity extends BaseActivity {
    private String title;
    private int layoutId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutId = getIntent().getIntExtra("layoutId", R.layout.rock_about_us);
        setContentView(layoutId);
        title = getIntent().getStringExtra("title");
        initialHeader(title);
    }
    public void initialHeader(String title){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText(title);
    }
}