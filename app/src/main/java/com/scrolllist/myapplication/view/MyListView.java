package com.scrolllist.myapplication.view;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.widget.ListView;

/**
 * Created by Zo2m4bie com.scrolllist.myapplication.view.MyListView
 */
public class MyListView extends ListView {

    public static final int DURATION = 500;
    public static final int PART_OF_SCREEN = 3;

    private float mY;
    private boolean mStartMove = false;
    private boolean mShowList = true;
    private boolean mLoadFirstY = false;
    private OnListHideListener mListHideListener = new StubOnListHideListener();
    private ViewPropertyAnimator mAnimator;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        stopAnimator();

        Log.d("SctollList", "Data event = " + event.getAction());
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mY = event.getY();
            mLoadFirstY = true;
            Log.d("SctollList", "Data ACTION_DOWN = " + mY);
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            float distance = mY - event.getY();
            if (mLoadFirstY && ((getChildAt(0).getTop() == 0 && distance < 0) // start move
                    || mStartMove)) { // or we already move
                mStartMove = true;
                float newY = getY() - distance;
                Log.d("SctollList", "Data distance = " + distance + " newY = " + newY);
                if(newY > 0) { // list to new position
                    setY(newY);
                }else if(getY() != 0){ // if we out of range and our view isn't at 0 mY position, list will set mY to 0
                    setY(0);
                }
            } else { // save new start mY
                Log.d("SctollList", "Data ACTION_MOVE = " + mY);
                mY = event.getY();
                mLoadFirstY = true;
            }

        }
        //if we move ListView always show 0 item in top
        if(mStartMove){
            setSelection(0);
        }
        //animate autoscroll to end or to start
        if(event.getAction() == MotionEvent.ACTION_UP){
            mStartMove = false;
            int height = getMeasuredHeight();
            int translateTo = 0;
            mLoadFirstY = false;
            if(!(getY() < (height / PART_OF_SCREEN)))  {
                mShowList = false;
                translateTo = height;
            }

            moveList(translateTo);
        }
        return result;
    }

    private void stopAnimator() {
        if(mAnimator != null){
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private void moveList(int translateTo){
        mAnimator = animate();
        mAnimator.translationY(translateTo).setDuration(DURATION).setListener(new AnimatorListener());
        mAnimator.start();
    }

    public void setOnListHideListener(OnListHideListener listener){
        if(listener != null) {
            mListHideListener = listener;
        }
    }

    public void showList(){
        mShowList = true;
        moveList(0);
    }

    public boolean isListShow() {
        return mShowList;
    }

    public void hideWithoutAnimation(){
        mShowList = false;
        setY(getMeasuredHeight());
    }

    private class AnimatorListener implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if(!mShowList) {
                mListHideListener.onHileList();
            }
            mAnimator = null;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private class StubOnListHideListener implements OnListHideListener {
        @Override
        public void onHileList() {}
    }

    public interface OnListHideListener {
        void onHileList();
    }

}
