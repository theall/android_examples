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
     * ��ʼ���ؼ�����
     */
    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        //��ȡ��ʾ�������ͼƬ��BitmapFactory.decodeResource()������ȡ����ͼƬ������RGB��ʽ�ģ���Ҫת����ARGB��ʽ�ġ�
        Bitmap imgTop = BitmapFactory.decodeResource(getResources(), R.drawable.up, options);
        //��ȡ��ʾ�������ͼƬ��
        Bitmap imgBottom = BitmapFactory.decodeResource(getResources(), R.drawable.down, options);
        //����һ����ʾ�������ͼƬimgTop��С�Ŀհ�BitmapͼƬ��ͼƬ��ʽ���ó�ARGB��ʽ�ġ�
        imgBlank = Bitmap.createBitmap(imgTop.getWidth(), imgTop.getHeight(), Bitmap.Config.ARGB_4444);
        //��imgBlank����Ϊ������
        Canvas canvas = new Canvas(imgBlank);
        //��imgTop���ڻ�����
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
                Log.i("location", "��ǰλ�� x:" + x + ",y:" + y);
                for (int i = x - 20; i < x + 20; i++) {
                    for (int j = y - 20; j < y + 20; j++) {
                        //����ͼƬ�߽�����
                        if (i >= 0 && i < imgBlank.getWidth() && j >= 0 && j < imgBlank.getHeight()) {
                            //���õ�ǰ��Ϊ͸��
                            imgBlank.setPixel(i, j, Color.TRANSPARENT);
                        }
                    }
                }
                //��ʾͼƬ
                ivTop.setImageBitmap(imgBlank);
            }
            return true;
        }
    }
}