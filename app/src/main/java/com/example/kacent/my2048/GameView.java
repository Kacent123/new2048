package com.example.kacent.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kacent on 2016/3/30.
 */
public class GameView extends LinearLayout {
    private Card[][] cardMap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<Point>();


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiGameView();
    }

    public GameView(Context context) {
        super(context);
        intiGameView();
    }



    private void intiGameView() {
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(0xffbbada0);
        //监听滑动动作
        setOnTouchListener(new View.OnTouchListener() {
            private float fromX, fromY, doX, doY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.print("touch");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        fromX = event.getX();
                        fromY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        doX = event.getX() - fromX;
                        doY = event.getY() - fromY;

                        if (Math.abs(doX) > Math.abs(doY)) {
                            //左右滑动
                            if (doX < -5) {
                                doUp();
                            } else if (doX > 5) {

                                doDown();
                            }
                        } else {
                            //上下
                            if (doY < -5) {
                                doLeft();
                            } else if (doY > 5) {
                                doRight();
                            }
                        }
                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCard(cardWidth, cardWidth);
        startGame();
    }

    public void addCard(int cardWidth, int cardHeight) {
        Card c;

        LinearLayout line;
        LinearLayout.LayoutParams lineLp;

        for (int y = 0; y < 4; y++) {
            line = new LinearLayout(getContext());
            lineLp = new LinearLayout.LayoutParams(-1, cardHeight);
            addView(line, lineLp);

            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                line.addView(c, cardWidth, cardHeight);

                cardMap[x][y] = c;
            }
        }

    }

    public void startGame() {
        MainActivity.getmMainactivity().clearSore();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardMap[x][y].setNum(0);
            }
        }
        addRandom();
        addRandom();

    }

    public void addRandom() {
        emptyPoint.clear();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (cardMap[x][y].getNum() <= 0) {
                    emptyPoint.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoint.remove((int) ((Math.random()) * emptyPoint.size()));
        cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        MainActivity.getAnimaLayout().createCardAima(cardMap[p.x][p.y]);
    }

    /*
        *上下左右动作 方法执行
        *
        * */
    public void doLeft() {

        boolean check = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x][y1],cardMap[x][y],x,x,y1,y);

                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y--;

                            check = true;
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x][y1],cardMap[x][y],x,x,y1,y);

                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getmMainactivity().addSore(cardMap[x][y].getNum() * 2);

                            check = true;
                        }
                        break;
                    }
                }
            }
        }
        if (check) {
            addRandom();
            checkOver();
        }


    }

    public void doRight() {
        boolean check = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x][y1],cardMap[x][y],x,x,y1,y);

                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;
                            check = true;
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x][y1],cardMap[x][y],x,x,y1,y);

                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getmMainactivity().addSore(cardMap[x][y].getNum() * 2);
                            check = true;
                        }
                        break;
                    }
                }
            }
        }
        if (check) {
            addRandom();
            checkOver();
        }

    }

    public void doUp() {
        boolean check = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardMap[x1][y].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x1][y],cardMap[x][y],x1,x,y,y);
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x1--;
                            check = true;
                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x1][y],cardMap[x][y],x1,x,y,y);
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getmMainactivity().addSore(cardMap[x][y].getNum() * 2);

                            check = true;
                        }
                        break;

                    }
                }
            }
        }
        if (check) {
            addRandom();
            checkOver();
        }


    }

    public void doDown() {
        boolean check = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardMap[x1][y].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x1][y],cardMap[x][y],x1,x,y,y);

                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x1++;
                            check = true;
                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            MainActivity.getAnimaLayout().createMoveAnim(cardMap[x1][y],cardMap[x][y],x1,x,y,y);

                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getmMainactivity().addSore(cardMap[x][y].getNum() * 2);
                            check = true;
                        }
                    }
                }
            }

        }
        if (check) {
            addRandom();
            checkOver();
        }

    }


    //监测游戏是否结束了
    public void checkOver() {
        boolean overFlag = true;
        ALL:
        for (int y = 0; y < 4;y++) {
            for (int x= 0;x< 4; x++) {
                if (cardMap[x][y].getNum() == 0
                        || (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y]))
                        || (x < 3 && cardMap[x][y].equals(cardMap[x + 1][y]))
                        || (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1]))
                        || (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))
                        ) {
                    overFlag = false;
                    break ALL;
                }
            }
        }

        if (overFlag) {
            new AlertDialog.Builder(getContext()).setTitle("信息提示").setMessage("GAME OVER").setPositiveButton("重新再来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
}
