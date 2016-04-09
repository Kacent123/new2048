package com.example.kacent.my2048;

import android.content.Context;
import android.graphics.Interpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kacent on 2016/4/7.
 */
public class AimaLayout extends FrameLayout {
    public AimaLayout(Context context) {
        super(context);
    }

    public AimaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AimaLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //数字 滑动的 动画
    public void createMoveAnim(final Card from,final Card to,int fromX,int toX,int fromY,int toY){

        final Card c = getCard(from.getNum());

        LayoutParams lp = new LayoutParams(4, 4);
        lp.leftMargin = fromX*4;
        lp.topMargin = fromY*4;
        c.setLayoutParams(lp);

        if (to.getNum()<=0) {
            to.getLable().setVisibility(View.INVISIBLE);
        }
        TranslateAnimation ta = new TranslateAnimation(0,4*(toX-fromX), 0,4*(toY-fromY));
        ta.setDuration(200);
        c.setAnimation(null);
        ta.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                to.getLable().setVisibility(View.VISIBLE);
                recycleCard(c);
            }
        });
        c.startAnimation(ta);
    }

    public void createCardAima(Card card) {
        ScaleAnimation animation = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        card.setAnimation(null);
        card.startAnimation(animation);
    }


    private Card getCard(int num){
        Card c;
        if (cards.size()>0) {
            c = cards.remove(0);
        }else{
            c = new Card(getContext());
            addView(c);
        }
        c.setVisibility(View.VISIBLE);
        c.setNum(num);
        return c;
    }

    private void recycleCard(Card c){
        c.setVisibility(View.INVISIBLE);
        c.setAnimation(null);
        cards.add(c);
    }


    //缓存用的
    private List<Card> cards = new ArrayList<Card>();
}
