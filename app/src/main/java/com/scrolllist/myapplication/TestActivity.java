package com.scrolllist.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ArrayAdapter;

import com.scrolllist.myapplication.view.MyListView;

/**
 * Created by Zo2m4bie on 12/10/14.
 */
public class TestActivity extends Activity {

    private MyListView mListView;
    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (MyListView) findViewById(R.id.lv);

        int n = 50;
        String[] array = new String[n];
        for(int i =0; i< n; i++){
            array[i] = i + "";
        }
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item, array));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
