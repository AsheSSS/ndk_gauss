package com.example.alw.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.lang.ref.WeakReference;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ImageView img_target;
    ImageView img_target2;
    ImageView img_center;
    private BitmapTask mBitmapTask;
    private BitmapTask mBitmapTask2;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("advanced_test");
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_target = (ImageView) findViewById(R.id.img_target);
        img_target2 = (ImageView) findViewById(R.id.img_target2);
        img_center = (ImageView) findViewById(R.id.img_center);
        // Example of a call to a native method
        //获取Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aaa);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.bbb);
        img_target.setImageBitmap(bitmap);
        img_target2.setImageBitmap(bitmap2);

        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();

        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Bitmap grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.RGB_565);

        Utils.bitmapToMat(srcBitmap, rgbMat);//convert original bitmap to Mat, R G B.

        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat

        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap

        img_center.setImageBitmap(grayBitmap);

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        Log.e("eeee", "aaa: " + externalStorageDirectory);


        /*beginning 下面代码用rxJava替换*/


       /*ending 下面代码用rxJava替换*/


        initPicOne(bitmap, img_target);
        initPicTwo(bitmap2, img_target2);

        //初始化任务
//        mBitmapTask = new BitmapTask(bitmap, img_target);
//        mBitmapTask2 = new BitmapTask(bitmap2, img_target2);
        //高斯模糊
//        AsyncTaskCompat.executeParallel(mBitmapTask2);
//        AsyncTaskCompat.executeParallel(mBitmapTask);


        int main = JNIUtils.aaa();
//        Toast.makeText(this, "main=" + main, Toast.LENGTH_SHORT).show();
    }

    private void initPicTwo(Bitmap bitmap2, final ImageView img_target2) {
        // map变换将url转换成bitmap
        Observable<Bitmap> observable = Observable.just(bitmap2).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).map(new Func1<Bitmap, Bitmap>() {
            @Override
            public Bitmap call(Bitmap bitmap) {
//                gaussBlur(bitmap, 50);
                return null;
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object ob) {
                if (img_target2 != null) {
                    img_target2.postInvalidate();
                }
            }
        });
    }


    private void initPicOne(Bitmap bitmap, final ImageView imageView) {
        // map变换将url转换成bitmap
        Observable<Bitmap> observable = Observable.just(bitmap).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).map(new Func1<Bitmap, Bitmap>() {
            @Override
            public Bitmap call(Bitmap bitmap) {
//                gaussBlur(bitmap, 50);
                return null;
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object ob) {
                if (imageView != null) {
                    imageView.postInvalidate();
                }
            }
        });


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
//                gaussBlur(bitmap, 50);
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

}
