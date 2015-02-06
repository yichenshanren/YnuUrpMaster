package edu.ynu.sl.ui.widget;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.util.Util;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/29.
 */
public class SelectorView extends SurfaceView implements SurfaceHolder.Callback {
    private static final int top = 0;
    private static final int center = 1;
    private static final int buttom = 2;
    private static final int left = 0;
    private static final int right = 2;
    private static final int temp = 3;

    private SurfaceHolder holder; //surface holer
    private GestureDetector detector; //手势

    private ArrayList<ScheduleClass> classifyName; //分类名称
    private ArrayList<ArrayList<ScheduleClass>> classifyContent;//分类内容
    private MyThread myThread;

    private int selectedItem; //当前选中的item
    private int selectedClassifyText; //当前选中的分类
    private int widht; //视图高
    private int height;//驶入宽
    private int upSpeed; //列表上滚的速度
    private int leftSpeed;
    private int speed;
    private int times = 0;

    private Boolean moveAble = false;
    //    private RectF[] bgRect;//画背景
    private RectF bgRect;
    private RectF centerRect;//画选中
    private RectF selectedRect;
    /*画分类内容列表*/

    private RectF[] classifyContentRects;
    private RectF tempRect;
    private RectF tempRectTop;
    private Paint mainPaint; //主画笔
    private Paint classifyPaint;//分类画笔
    private Paint centerTextPaint;
    /*画分类名称列表*/
    private RectF[] classifyNameRects;
    private RectF tempRectLeft;
    private RectF tempRectRight;
    private RectF centerClassify;

    public SelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.holder = this.getHolder();
        holder.addCallback(this);
        detector = new GestureDetector(new MyGestureDetector());

        setClassifyName(new ArrayList<ScheduleClass>());
        setClassifyContent(new ArrayList<ArrayList<ScheduleClass>>());
        ArrayList<ScheduleClass> classify = new ArrayList<ScheduleClass>();
        classify.add(new ScheduleClass("2015年"));
        classify.add(new ScheduleClass("2014年"));
        classify.add(new ScheduleClass("2013年"));
        classify.add(new ScheduleClass("2012年"));
        classifyContent.add(classify);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /*初始化必要数据*/
        initialize();
        /*开启绘图线程*/
        myThread = new MyThread(holder, this);
        myThread.start();
    }

    private void initialize() {
         /*获取view的高宽*/
        widht = getWidth(); //获取高
        height = getHeight();//获取宽
        setUpSpeed(0);
        setLeftSpeed(0);
        /*初始化主画笔*/
        mainPaint = new Paint();
        mainPaint.setColor(Color.RED);
        mainPaint.setAlpha(100);//设置透明
        /*c初始化分类画笔*/
        classifyPaint = new Paint();
       /*初始化text画笔*/
        centerTextPaint = new Paint();
        centerTextPaint.setColor(Color.WHITE);//设置画笔颜色
        centerTextPaint.setStrokeWidth(3);//间距
        centerTextPaint.setTextAlign(Paint.Align.CENTER);//居中
        centerTextPaint.setTextSize(height / 12);//字体大小

        /*构造画背景的矩形*/
//        bgRect = new RectF[4];
//        bgRect[0] = new RectF(0,0, widht, height/3);                           //////////////////////
//        bgRect[1] = new RectF(0, height/3, widht/4, height);                   ///////         //////
//        bgRect[2] = new RectF(widht*3/4, height/3, widht, height);             ///////         //////
//        bgRect[3] = new RectF(widht/4,height*2/3, widht*3/4, height);          //////////////////////
//        centerRect = new RectF(widht/4,height/3 , widht*3/4, height*2/3);

        centerRect = new RectF(0, height / 3, widht, height * 2 / 3);
        /**/
        setClassifyNameRects(new RectF[3]);//内容列表 四个矩形循环
        setClassifyContentRects(new RectF[4]); //名称列表 三个矩形循环

        setSelectedItem(0);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        /*在surfaceView 销毁时结束绘图线程*/
        myThread.setIsRun(false);
        Boolean retry = true;
        while (retry) {
            try {
                this.myThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.d("", "Surface destroy failure:", e);
            }
        }


    }

    /**
     * 画图
     */
    public void draw(Canvas c) {

        c.drawColor(Color.WHITE);

//        drawClassifyName(c);
        //   drawClassify(c);
        //   drawBg(c);

    }

    /**
     * 画背景
     * *
     */
    public void drawBg(Canvas c) {
        c.drawRect(bgRect, mainPaint);
    }

    /**
     * 画分类内容
     */

    public void drawClassify(Canvas c) {

        /**
         * 分类内容列表的滚动处理
         * 四个矩形循环滚动
         *
         * */
        classifyPaint.setColor(Color.BLUE);
        /*选中框*/

         /*先移动矩形*/
        moveClassifyRect(upSpeed); //做移动
         /*再在画布上画出矩形*/
        for (int i = 0; i < 4; i++) {
            c.drawRect(classifyContentRects[i], classifyPaint); //画出列表
        }
        //画一个bug背景，处理华东滚中的闪烁
        c.drawRect(centerRect.left, 0, centerRect.right, height, classifyPaint);

        //加速度以及停止的处理====================================================================================
        if ((upSpeed > 1 || upSpeed < -1)) {
            /*加速度的处理---------------------------------------------*/
            if (times == 5) { //每5帧加速一次
                upSpeed /= 2;//加速度2次递减
                times = 0;
            }
            times++;/*加速度处理结束--------------------------------------*/
        } else {
            /*停止的处理，使item最终都能停在选择框内--------------------------------------------------------------*/
            if (upSpeed > 0) {//向上滑动
                if (classifyContentRects[center].bottom != centerRect.bottom - 1) {//判断centerRect是否停在选择框内
                    if (classifyContentRects[center].bottom < centerRect.bottom) {
                        upSpeed = 1;
                    } else {
                        upSpeed = 0;
                    }
                }
            } else if (upSpeed < 0) { //向下滑动
                if (classifyContentRects[center].top != centerRect.top + 1) {
                    if (classifyContentRects[center].top > centerRect.top) {
                        upSpeed = -1;
                    } else {
                        upSpeed = 0;
                    }

                }
            }/*item停止处理结束-----------------------------------------------------------*/
        }
        //===============================================================================================

            /*文字的处理，画出对应的文字， 使文字随矩形滚动*/
        drawTextCenter(c, rectFToRect(classifyContentRects[0]), classifyContent.get(0).get(selectedItem > 0 ? selectedItem - 1 : 3).getValue());
        drawTextCenter(c, rectFToRect(classifyContentRects[1]), classifyContent.get(0).get(selectedItem).getValue());
        drawTextCenter(c, rectFToRect(classifyContentRects[2]), classifyContent.get(0).get(selectedItem < 3 ? selectedItem + 1 : 0).getValue());
        drawTextCenter(c, rectFToRect(classifyContentRects[3]), classifyContent.get(0).get(selectedItem < 2 ? selectedItem + 2 : selectedItem - 2).getValue());

           /*列表滚动处理*/
        if (upSpeed > 0) { //向上滚动-------------------------------------------------------
            if (classifyContentRects[center].bottom <= centerRect.top + 1) {//

                if (selectedItem < 3) {
                    selectedItem++;
                } else {
                    selectedItem = 0;
                }

                for (int i = 0; i < 4; i++) {
                    if (i < 3) {
                        classifyContentRects[i] = classifyContentRects[i + 1];

                    } else {

                        classifyContentRects[i] = tempRect;
                    }

                }
            }//----------------------向上滚动结束-------------------------------------------
        } else if (upSpeed < 0) {//向下滚动-------------------------------------------------

            if (classifyContentRects[center].top >= centerRect.bottom - 1) {

                if (selectedItem > 0) {
                    selectedItem--;
                } else {
                    selectedItem = 3;
                }

                for (int i = 3; i >= 0; i--) {
                    if (i > 0) {
                        classifyContentRects[i] = classifyContentRects[i - 1];

                    }
                    if (i == 0) {
                        classifyContentRects[top] = tempRectTop;
                    }
                }

            }
        }//向下滚动结束--------------------------------------------------------------------


    }


    /**
     * 把RectF转换为Rect
     */
    private Rect rectFToRect(RectF r) {
        return new Rect((int) r.left, (int) r.top, (int) r.right, (int) r.bottom);
    }

    public void addClassifyName(ScheduleClass name) {
        classifyName.add(name);
    }


    public void addClassifyContent(ArrayList<ScheduleClass> content) {
        classifyContent.add(content);
    }

    public ArrayList<ScheduleClass> getClassifyName() {
        return classifyName;
    }

    /**
     * 设置
     */
    public void setClassifyName(ArrayList<ScheduleClass> classifyName) {
        this.classifyName = classifyName;
        classifyName.add(new ScheduleClass("选择学期"));
        classifyName.add(new ScheduleClass("选择学院"));
        classifyName.add(new ScheduleClass("选择专业"));
        classifyName.add(new ScheduleClass("选择年级"));
        classifyName.add(new ScheduleClass("选择课程性质"));
    }

    public ArrayList<ArrayList<ScheduleClass>> getClassifyContent() {


        return classifyContent;
    }


    public void setClassifyContent(ArrayList<ArrayList<ScheduleClass>> classifyContent) {

        this.classifyContent = classifyContent;

    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    /*
    * 画出需要显示的文本，并使其居中显示
    * */
    public void drawTextCenter(Canvas canvas, Rect rect, String text) {

        Paint.FontMetricsInt fontMetrics = centerTextPaint.getFontMetricsInt();
        int baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()

        canvas.drawText(text, rect.centerX(), baseline, centerTextPaint);
    }

    public int getSelectedClassifyText() {
        return selectedClassifyText;
    }

    public void setSelectedClassifyText(int selectedClassifyText) {
        this.selectedClassifyText = selectedClassifyText;
    }

    public RectF getSelectedRect() {
        return selectedRect;
    }

    public void setSelectedRect(RectF selectedRect) {
        this.selectedRect = selectedRect;
    }

    public RectF[] getClassifyNameRects() {
        return classifyNameRects;
    }

    public void setClassifyNameRects(RectF[] classifyNameRects) {
        centerClassify = new RectF(widht / 4, 0, widht * 3 / 4, height);
        tempRectLeft = new RectF(
                centerClassify.left - widht / 4,
                centerClassify.top,
                centerClassify.left - widht * 3 / 4,
                centerClassify.bottom);
        tempRectRight = new RectF(centerClassify.right + widht / 4,
                centerClassify.top,
                centerClassify.right + widht * 3 / 4,
                centerClassify.bottom);
//        moveClassifyNameFect(0);
        this.classifyNameRects = classifyNameRects;
    }

    public RectF[] getClassifyContentRects() {

        return classifyContentRects;
    }

    /**
     * 初始分类名称列表的矩形
     */
//    public void moveClassifyNameFect(int speed){
//        classifyNameRects[center] = new RectF(
//                centerClassify.left - speed,
//                centerClassify.top,
//                centerClassify.left - speed,
//                centerClassify.bottom
//        );
//        classifyNameRects[left] = new RectF(
//                classifyNameRects[center].left - widht * 2 /4,
//                classifyNameRects[center].top,
//                classifyNameRects[center].left,
//                classifyNameRects[center].bottom
//        );
//        classifyNameRects[right] = new RectF(
//                classifyNameRects[center].right ,
//                classifyNameRects[center].top,
//                classifyNameRects[center].right - widht * 2 /4 ,
//                classifyNameRects[center].bottom
//        );
//    }
    public void setClassifyContentRects(RectF[] classifyContentRects) {
        this.classifyContentRects = classifyContentRects;
        tempRect = new RectF(centerRect.left,
                height,
                centerRect.right,
                height * 4 / 3);
        tempRectTop = new RectF(centerRect.left,
                -height / 3,
                centerRect.right,
                0);

        classifyContentRects[center] = new RectF(centerRect.left,
                centerRect.top,
                centerRect.right,
                centerRect.bottom);
        classifyContentRects[top] = new RectF(centerRect.left,
                centerRect.top - height / 3,
                centerRect.right,
                centerRect.bottom - height / 3);
        classifyContentRects[temp] = tempRect;
        classifyContentRects[buttom] = new RectF(centerRect.left,
                centerRect.top + height / 3,
                centerRect.right,
                centerRect.bottom + height / 3);

    }

    public void moveClassifyRect(int speed) {

        classifyContentRects[center] = new RectF(
                classifyContentRects[center].left,
                classifyContentRects[center].top - speed,
                classifyContentRects[center].right,
                classifyContentRects[center].bottom - speed);

        classifyContentRects[top] = new RectF(
                classifyContentRects[top].left,
                classifyContentRects[center].top - height / 3,
                classifyContentRects[top].right,
                classifyContentRects[center].top);

        classifyContentRects[buttom] = new RectF(
                classifyContentRects[buttom].left,
                classifyContentRects[center].bottom,
                classifyContentRects[buttom].right,
                classifyContentRects[center].bottom + height / 3);


        if (speed > 0) {
            classifyContentRects[temp] = new RectF(
                    classifyContentRects[temp].left,
                    classifyContentRects[center].bottom + height * 1 / 3,
                    classifyContentRects[temp].right,
                    classifyContentRects[center].bottom + height * 2 / 3);
        } else {
            classifyContentRects[temp] = new RectF(
                    classifyContentRects[temp].left,
                    classifyContentRects[center].top - height * 2 / 3,
                    classifyContentRects[temp].right,
                    classifyContentRects[center].top - height * 1 / 3);
        }

    }


    public int getUpSpeed() {
        return upSpeed;
    }

    public void setUpSpeed(int upSpeed) {
        this.upSpeed = upSpeed;
    }

    public int getLeftSpeed() {
        return leftSpeed;
    }

    public void setLeftSpeed(int leftSpeed) {
        this.leftSpeed = leftSpeed;
    }

    public class MyThread extends Thread {

        private SurfaceHolder holder;
        private SelectorView selectorView;
        private Boolean isRun;
        private Canvas render;
        private long mTimeLogger;
        private int sleepTime = 60;

        public MyThread(SurfaceHolder holder, SelectorView selectorView) {
            this.holder = holder;
            this.selectorView = selectorView;
            isRun = true;
        }

        public void run() {
            while (isRun) {

                try {
                    mTimeLogger = System.currentTimeMillis();
                    try {
                        synchronized (this.holder) {
                            render = this.holder.lockCanvas(null);
                            selectorView.draw(render);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    } finally {
                        if (render != null) {
                            holder.unlockCanvasAndPost(render);//结束锁定画图，并提交改变。

                        }
                    }
                    mTimeLogger = System.currentTimeMillis() - mTimeLogger;

                    if (mTimeLogger < sleepTime)
                        Thread.sleep(sleepTime - mTimeLogger);


                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }//睡眠时间

            }
        }

        public void setIsRun(Boolean isRun) {
            this.isRun = isRun;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            moveAble = true;

        }
        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            moveAble = false;
        }
        detector.onTouchEvent(event);
        return true;
    }

    public class MyGestureDetector implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //if(distanceY > 0){
            setUpSpeed((int) distanceY);
            // }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX() - e2.getX() < -120) {
                Util.print("又滑");
                return true;
            }

            return false;
        }
    }
}
