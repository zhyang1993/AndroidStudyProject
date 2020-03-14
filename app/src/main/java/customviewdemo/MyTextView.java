package customviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyTextView extends View {
    private TextView textView;
    //定义画笔
    private Paint mPaint = new Paint();
    //圆心横坐标
    private float circleX = 0;
    //圆心纵坐标
    private float circleY = 0;
    //圆半径
    private float radius = 0;
    //手机屏幕宽高
    private int mWidth, mHeight;
    //减去状态栏和导航栏之后的高度
    private int maxHeight;
    private int statusBarHeight;
    private int navigationBarHeight;
    //区别是否已经发起了ontouch事件，处理postInvalidate回调onDraw()然后view在宽高都在wrap_content的测量模式下，宽高默认设置为300，
    // 发生view跟随手指移动的时候只在300 X 300的区域内移动
    private boolean isMove = false;

    /*代码中实例化调用*/
    public MyTextView(Context context) {
        this(context, null);
        init(context);
    }

    /*在布局文件中使用时调用*/
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    /*在布局文件中且使用style属性*/
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //AT_MOST ----> wrap_content,会造成当宽高设置wrap_content时充满整个view，最好给一个默认的大小
        //EXACTLY ----> match_parent 或 具体的数字
        //UNSPECIFIED ---->这说明parent没有对child强加任何限制，child可以是它想要的任何尺寸
        maxHeight = mHeight - statusBarHeight - navigationBarHeight;
        Log.e("zhyang", "剩余高度 ：" + maxHeight);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        //获取测量模式
        int widthMode = MeasureSpec.getMode(measureSpec);
        //获取测量的大小
        int widthSize = MeasureSpec.getSize(measureSpec);
        //如果没有指定大小限制，则使用默认大小（以像素为单位）
        int result = 0;
        if (widthMode == MeasureSpec.AT_MOST) {
            result = 200;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            result = widthSize;
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        //获取测量模式
        int heightMode = MeasureSpec.getMode(measureSpec);
        //获取测量的大小
        int heightSize = MeasureSpec.getSize(measureSpec);
        //如果没有指定大小限制，则使用默认大小（以像素为单位）
        int result = 0;
        if (heightMode == MeasureSpec.AT_MOST) {
            result = 200;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            result = heightSize;
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init(Context context) {
        mPaint.setColor(Color.BLUE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mHeight = dm.heightPixels;
        mWidth = dm.widthPixels;
        statusBarHeight = getStatusBarHeight();
        navigationBarHeight = getNavigationBarHeight(context);
        Log.e("zhyang", "屏幕宽度：" + mWidth);
        Log.e("zhyang", "屏幕高度 ：" + mHeight);
        Log.e("zhyang", "状态栏度：" + statusBarHeight);
        Log.e("zhyang", "导航栏高度 ：" + navigationBarHeight);
    }

    /**
     * 重写draw方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius = getHeight() / 2;
        this.circleX = getWidth() / 2;
        this.circleY = getHeight() / 2;
        canvas.drawCircle(this.circleX, this.circleY, radius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int lastX = 0;
        int lastY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
//                int offsetX = x - lastX;
//                int offsetY = y - lastY;
//                // 在当前left、top、right、bottom的基础上加上偏移量
//                //应该添加限制条件 比如防止拖动到屏幕外侧
//
//                int l = getLeft();
//                int r = getRight();
//                int t = getTop();
//                int b = getBottom();
//                layout(l + offsetX, t + offsetY, getRight() + offsetX, getBottom() + offsetY);
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                int l,r,t,b; // 上下左右四点移动后的偏移量
                //计算偏移量 设置偏移量 = 3 时 为判断点击事件和滑动事件的峰值
                if (Math.abs(offsetX) > 3 ||Math.abs(offsetY) > 3) { // 偏移量的绝对值大于 3 为 滑动时间 并根据偏移量计算四点移动后的位置
                    l = (int) (getLeft() + offsetX);
                    r = l+getWidth();
                    t = (int) (getTop() + offsetY);
                    b = t+getHeight();
                    //不划出边界判断,最大值为边界值
                    // 如果你的需求是可以划出边界 此时你要计算可以划出边界的偏移量 最大不能超过自身宽度或者是高度  如果超过自身的宽度和高度 view 划出边界后 就无法再拖动到界面内了 注意
                    if(l<0){ // left 小于 0 就是滑出边界 赋值为 0 ; right 右边的坐标就是自身宽度 如果可以划出边界 left right top bottom 最小值的绝对值 不能大于自身的宽高
                        l=0;
                        r=l+getWidth();
                    }else if(r> mWidth){ // 判断 right 并赋值
                        r= mWidth;
                        l=r-getWidth();
                    }
                    if(t<0){ // top
                        t=0;
                        b=t+getHeight();
                    }else if(b> maxHeight){ // bottom
                        b= maxHeight;
                        t=b-getHeight();
                    }
                    this.layout(l, t, r, b); // 重置view在layout 中位置
                // 重置 拖动为 true
                }else {
                 // 小于峰值3时 为点击事件
                }
                break;
        }
        return true;
    }

    // 获取状态栏高度
    public int getStatusBarHeight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }
    // 获取导航栏高度
    public int getNavigationBarHeight(Context context) {
        int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid!=0){
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        }else
            return 0;

    }

    /*
    *  使用偏移量和onlayout方法来实现view跟随手指移动
    * @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int lastX = 0;
        int lastY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                // 在当前left、top、right、bottom的基础上加上偏移量
                layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);

                break;
        }
        return true;
    }
    *
    *
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取各个编剧的padding值
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //获取绘制的View的宽度
        int width = getWidth() - paddingLeft - paddingRight;
        //获取绘制的View的高度
        int height = getHeight() - paddingTop - paddingBottom;
        //绘制矩形View，左上角坐标（0+paddingLeft,0+paddingTop），右下角坐标（width+paddingLeft,height+paddingTop）
        canvas.drawRect(0 + paddingLeft, 0 + paddingTop, width + paddingLeft, height + paddingTop, mPaint);

    }
     * */


}
