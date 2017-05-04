package com.example.alw.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    ImageView img_target;
    ImageView img_target2;
    private BitmapTask mBitmapTask;
    private BitmapTask mBitmapTask2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_target = (ImageView) findViewById(R.id.img_target);
        img_target2 = (ImageView) findViewById(R.id.img_target2);
        // Example of a call to a native method
        //获取Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aaa);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.bbb);
        img_target.setImageBitmap(bitmap);
        img_target2.setImageBitmap(bitmap2);
        //初始化任务
        mBitmapTask = new BitmapTask(bitmap, img_target);
        mBitmapTask2 = new BitmapTask(bitmap2, img_target2);
        //高斯模糊
        AsyncTaskCompat.executeParallel(mBitmapTask2);
        AsyncTaskCompat.executeParallel(mBitmapTask);


    }

    /**
     * 静态内部类
     */
    private static class BitmapTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Bitmap> mBitmap;

        private WeakReference<ImageView> mImageLogo;

        public BitmapTask(Bitmap bitmap, ImageView imageView) {
            mBitmap = new WeakReference<>(bitmap);
            mImageLogo = new WeakReference<>(imageView);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Bitmap bitmap = mBitmap.get();
            if (bitmap != null) {
                gaussBlur(bitmap, 50);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ImageView imageView = mImageLogo.get();
            if (imageView != null) {
                imageView.postInvalidate();
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    public static native int gaussBlur(Object bitmap, int radius);

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
