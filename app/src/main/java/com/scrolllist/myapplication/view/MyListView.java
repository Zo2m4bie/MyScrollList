package com.scrolllist.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Zo2m4bie com.scrolllist.myapplication.view.MyListView
 */
public class MyListView extends ListView {

    public static final int DURATION = 500;
    public static final int PART_OF_SCREEN = 3;

    float y;
    boolean startMove = false;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            y = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float distance = y - event.getY();
            if ((getChildAt(0).getTop() == 0 && distance < 0) // start move
                    || startMove) { // or we already move
                startMove = true;
                float newY = getY() - distance;

                if(newY > 0) { // list to new position
                    setY(newY);
                }else if(getY() != 0){ // if we out of range and our view isn't at 0 y position, list will set y to 0
                    setY(0);
                }
            } else { // save new start y
                y = event.getY();
            }

        }
        //if we move ListView always show 0 item in top
        if(startMove){
            setSelection(0);
        }
        //animate autoscroll to end or to start
        if(event.getAction() == MotionEvent.ACTION_UP){
            startMove = false;
            int height = getMeasuredHeight();
            int translateTo = (getY() < (height / PART_OF_SCREEN)) ? 0 : height;
            animate().translationY(translateTo).setDuration(DURATION).start();
        }
        return result;
    }

}
