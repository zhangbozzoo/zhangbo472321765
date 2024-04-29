package com.myapp.area;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GraffitiView extends View {

    private final Context mContext;
    private Canvas mCanvas;//
    private Bitmap mBitmap;// 用于保存绘制过的路径的 bitmap
    private Paint mPaint;// 画笔
    private Path mPath;// 触摸时的路径

    private int width,height;



    public GraffitiView(Context context) {
        this(context,null);
    }

    public GraffitiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        // 初始化 画笔
        mPaint = new Paint();
        mPaint.setColor(mContext.getColor(R.color.teal_200));//画笔颜色
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setDither(true);// 抖动处理
        mPaint.setStrokeJoin(Paint.Join.ROUND);//画笔连接处 圆弧
        mPaint.setStrokeCap(Paint.Cap.ROUND);//画笔拐弯处风格 圆弧
        mPaint.setStyle(Paint.Style.STROKE);//画笔格式
        mPaint.setStrokeWidth(10f);//画笔宽度

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        if(mBitmap == null){
            // 初始化 bitmap
            mBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_4444);
        }
        if(mCanvas == null){
            mCanvas = new Canvas(mBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制路径
        // 因为每次触摸都会生成一条新的路径，直接绘制会使原路径消失，因此
        mCanvas.drawPath(mPath,mPaint);// 先将路径绘制到 bitmap 上，再绘制到当前画布中
        canvas.drawBitmap(mBitmap, 0,0,mPaint);// 将bitmap绘制到当前画布中
    }

    /**
     * 清除之前所有路径
     */
    public void clearAllPath(){
        mBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_4444);
        mCanvas = new Canvas(mBitmap);
        mPath.reset();
        Myview.path=mPath;
        //Log.i("aa",mPath+"");
        invalidate();
    }

    /**
     * 设置画笔颜色
     * @param resource id
     */
    public void setPaintColor(int resource){
        mPaint.setColor(mContext.getColor(resource));
    }

    /**
     * 设置画笔大小
     * @param size size
     */
    public void setPaintSize(int size){
        mPaint.setStrokeWidth(size);
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!MainActivity.draw){
            return true;
        }

        //Log.i("aa","wwww");
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();// 每次触摸 生成一条新的路径
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("aa",mPath+"");
                mPath.lineTo(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mPath.close();
                invalidate();
                MainActivity.draw=false;
                Myview.path=mPath;

                //MainActivity.button_draw.setBackgroundColor(Color.rgb(211,211,211));
                MainActivity.button_draw.setText("绘制区域");

                //boolean conn=iscontain(mPath,500,500);
                break;

        }
        return true;
    }

    private boolean iscontain(Path path,int x,int y){
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(path, new Region((int)bounds.left, (int)bounds.top,(int)bounds.right, (int)bounds.bottom));
        if (region.contains(x,y)){
//            Log.i("aa","包含");
            return true;
        }else {
//            Log.i("aa","不包含");
            return false;
        }
    }
}