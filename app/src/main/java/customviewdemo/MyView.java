package customviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float currentX = 70;
    public float currentY = 70;
    //定义。创建画笔
    Paint p = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.RED);
        //绘制一个小球
        //参数分别是：圆心坐标，半径 ，所使用的画笔
        canvas.drawCircle(currentX, currentY, 30, p);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//修改当前的坐标
        this.currentX = event.getX();
        this.currentY = event.getY();
//重绘小球
        this.invalidate();

        return true;
    }
}
