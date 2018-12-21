package com.example.stripclothes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {
    private ImageView ivBottom;
    private ImageView ivTop;
    private Bitmap imgBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        init();
    }

    /**
     * 初始化控件内容
     */
    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        //获取显示在上面的图片，BitmapFactory.decodeResource()方法获取到的图片编码是RGB格式的，需要转换成ARGB格式的。
        Bitmap imgTop = BitmapFactory.decodeResource(getResources(), R.drawable.up, options);
        //获取显示在下面的图片。
        Bitmap imgBottom = BitmapFactory.decodeResource(getResources(), R.drawable.down, options);
        //创建一个显示在上面的图片imgTop大小的空白Bitmap图片，图片格式设置成ARGB格式的。
        imgBlank = Bitmap.createBitmap(imgTop.getWidth(), imgTop.getHeight(), Bitmap.Config.ARGB_4444);
        //将imgBlank创建为画布。
        Canvas canvas = new Canvas(imgBlank);
        //将imgTop画在画布上
        canvas.drawBitmap(imgTop, 0, 0, null);

        ivTop.setImageBitmap(imgBlank);
        ivBottom.setImageBitmap(imgBottom);

        ivTop.setOnTouchListener(new MyOnTouchListener());
    }

    private void findView() {
        ivTop = (ImageView) findViewById(R.id.iv_top);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);
    }

    private class MyOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {

                int x = (int) event.getX();
                int y = (int) event.getY();
                Log.i("location", "当前位置 x:" + x + ",y:" + y);
                for (int i = x - 20; i < x + 20; i++) {
                    for (int j = y - 20; j < y + 20; j++) {
                        //处理图片边界问题
                        if (i >= 0 && i < imgBlank.getWidth() && j >= 0 && j < imgBlank.getHeight()) {
                            //设置当前点为透明
                            imgBlank.setPixel(i, j, Color.TRANSPARENT);
                        }
                    }
                }
                //显示图片
                ivTop.setImageBitmap(imgBlank);
            }
            return true;
        }
    }
}