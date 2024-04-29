package com.myapp.area;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;
import com.myapp.area.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public static boolean draw=false;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private ActivityMainBinding binding;

    private GraffitiView mgraffitiview;
    private Button button;
    private Button button_chose;
    public static Button button_draw;
    private ImageView imageView;
    private TextView textView;
    private Myview myview;

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private int REQUEST_CODE_PERMISSIONS = 1001;

    PreviewView pvCameraPreview;
    private Yolov5n Yolov5Net=new Yolov5n();


//    public static List<String> chosed_aims;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        boolean init=Yolov5Net.Init(getAssets());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mgraffitiview=binding.draw;
        button=binding.btn1;
        pvCameraPreview=binding.preview;
        imageView=binding.image;
        textView=binding.txt;
        myview=binding.myview;
        button_draw=binding.btn2;
        button_chose=binding.btn3;

//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getRealMetrics(metric);
//        int width = metric.widthPixels; // 宽度（PX）
//        int height = metric.heightPixels; // 高度（PX）
//
//        Log.i("aa","width="+width+"  "+height);



        button.setOnClickListener(v -> mgraffitiview.clearAllPath());
        button_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw=true;
                //button_draw.setBackgroundColor(Color.rgb(124,252,0));
                button_draw.setText("绘制中...");
            }
        });

        button_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogUtils dialogUtils =AlertDialogUtils.getInstance();
                AlertDialogUtils.showConfirmDialog(MainActivity.this,"","请选择类别！");

                dialogUtils.setMonDialogButtonClickListener(new AlertDialogUtils.OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(AlertDialog dialog) {

                        boolean ch1=AlertDialogUtils.checkBox1.isChecked();
                        boolean ch2=AlertDialogUtils.checkBox2.isChecked();
                        boolean ch3=AlertDialogUtils.checkBox3.isChecked();
                        boolean ch4=AlertDialogUtils.checkBox4.isChecked();
                        boolean ch5=AlertDialogUtils.checkBox5.isChecked();
                        boolean ch6=AlertDialogUtils.checkBox6.isChecked();
                        boolean ch7=AlertDialogUtils.checkBox7.isChecked();
                        boolean ch8=AlertDialogUtils.checkBox8.isChecked();
                        boolean ch9=AlertDialogUtils.checkBox9.isChecked();
                        boolean ch10=AlertDialogUtils.checkBox10.isChecked();
                        boolean ch11=AlertDialogUtils.checkBox11.isChecked();
                        boolean ch12=AlertDialogUtils.checkBox12.isChecked();
                        boolean ch13=AlertDialogUtils.checkBox13.isChecked();
                        boolean ch14=AlertDialogUtils.checkBox14.isChecked();
                        boolean ch15=AlertDialogUtils.checkBox15.isChecked();
                        boolean ch16=AlertDialogUtils.checkBox16.isChecked();
                        boolean ch17=AlertDialogUtils.checkBox17.isChecked();
                        boolean ch18=AlertDialogUtils.checkBox18.isChecked();
                        boolean ch19=AlertDialogUtils.checkBox19.isChecked();
                        boolean ch20=AlertDialogUtils.checkBox20.isChecked();
                        boolean ch21=AlertDialogUtils.checkBox21.isChecked();
                        boolean ch22=AlertDialogUtils.checkBox22.isChecked();
                        boolean ch23=AlertDialogUtils.checkBox23.isChecked();
                        boolean ch24=AlertDialogUtils.checkBox24.isChecked();
                        boolean ch25=AlertDialogUtils.checkBox25.isChecked();

                        boolean[] buttons=new boolean[]{ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9,ch10,ch11,ch12,ch13,ch14,
                        ch15,ch16,ch17,ch18,ch19,ch20,ch21,ch22,ch23,ch24,ch25};

                        String[] class_names=new String[]{
                                "person", "bicycle", "car", "motorcycle", "airplane", "bus", "train", "truck", "boat", "traffic light",
                                "fire hydrant", "stop sign", "parking meter", "bench", "bird", "cat", "dog", "horse", "sheep", "cow",
                                "elephant", "bear", "zebra", "giraffe", "backpack", "umbrella", "handbag", "tie", "suitcase", "frisbee",
                                "skis", "snowboard", "sports ball", "kite", "baseball bat", "baseball glove", "skateboard", "surfboard",
                                "tennis racket", "bottle", "wine glass", "cup", "fork", "knife", "spoon", "bowl", "banana", "apple",
                                "sandwich", "orange", "broccoli", "carrot", "hot dog", "pizza", "donut", "cake", "chair", "couch",
                                "potted plant", "bed", "dining table", "toilet", "tv", "laptop", "mouse", "remote", "keyboard", "cell phone",
                                "microwave", "oven", "toaster", "sink", "refrigerator", "book", "clock", "vase", "scissors", "teddy bear",
                                "hair drier", "toothbrush"
                        };



                        Myview.chosed_aims.clear();

                        for (int i=0;i<buttons.length;i++){
                            if (buttons[i]){
                                Myview.chosed_aims.add(class_names[i]);
                            }
                        }
                        Log.i("aa",Myview.chosed_aims.size()+"");

                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClick(AlertDialog dialog) {


                    }
                });
            }
        });


        //获取权限
        if(allPermissionsGranted()){
            startCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }



    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


    //获取权限函数
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 开始预览
     */
    private CameraControl cameraControl;
    private Executor executor = Executors.newSingleThreadExecutor();
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        ((ListenableFuture<?>) cameraProviderFuture).addListener(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                try {

                    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                            //.setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)//阻塞模式
                            //.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                            //.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                            .build();
                    imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
                        @Override
                        public void analyze(@NonNull @NotNull ImageProxy image) {

                            //yuv图像数据转bitmap
                            ImageProxy.PlaneProxy[] planes = image.getPlanes();
                            //cameraX 获取yuv
                            ByteBuffer yBuffer = planes[0].getBuffer();
                            ByteBuffer uBuffer = planes[1].getBuffer();
                            ByteBuffer vBuffer = planes[2].getBuffer();

                            int ySize = yBuffer.remaining();
                            int uSize = uBuffer.remaining();
                            int vSize = vBuffer.remaining();

                            byte[] nv21 = new byte[ySize + uSize + vSize];

                            yBuffer.get(nv21, 0, ySize);
                            vBuffer.get(nv21, ySize, vSize);
                            uBuffer.get(nv21, ySize + vSize, uSize);
                            //获取yuvImage
                            YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
                            //输出流
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            //压缩写入out
                            yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 50, out);
                            //转数组
                            byte[] imageBytes = out.toByteArray();
                            //生成bitmap
                            Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

//                            Bitmap rotateBitmap=null;

                            long t1=System.currentTimeMillis();
                            Dector(bmp);

                            long t2=System.currentTimeMillis();

                            String FPS=1000f/(t2-t1)+"";
                            //Log.i("aa",draw+"");
                            Message message=new Message();
                            message.what=2;
                            message.obj=FPS;
                            handler.sendMessage(message);


                            image.close();
                        }
                    });



                    //将相机的生命周期和activity的生命周期绑定，camerax 会自己释放
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    Preview preview = new Preview.Builder().build();
                    //创建图片的 capture
                    ImageCapture mImageCapture = new ImageCapture.Builder()
                            .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                            .build();
                    //选择前置摄像头
                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll();

                    // Bind use cases to camera
                    //参数中如果有mImageCapture才能拍照，否则会报下错
                    //Not bound to a valid Camera [ImageCapture:androidx.camera.core.ImageCapture-bce6e930-b637-40ee-b9b9-
                    Camera camera = cameraProvider.bindToLifecycle(MainActivity.this, cameraSelector, preview, imageAnalysis,mImageCapture);

                    cameraControl = camera.getCameraControl();
                    cameraControl.setLinearZoom(0.1f);
                    preview.setSurfaceProvider(pvCameraPreview.getSurfaceProvider());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }


    private Handler handler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Bitmap bitmap=(Bitmap) msg.obj;
                imageView.setImageBitmap(bitmap);
            }else if (msg.what==2){
                String s=(String)msg.obj;
                textView.setText("FPS: "+s);

            }
        }
    };


    /**
     * 触摸事件,缩放摄像头画面
     */
    public float rate=0f;
    public float rate1=0f;
    public float rate2=0f;
    private int state=0;
    private float distance;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                int x=(int)event.getRawX();
                int y=(int)event.getRawY();
                Log.e("aa","按下"+x+","+y);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("aa","抬起");
                break;
            case MotionEvent.ACTION_MOVE:
                if(state==2){
                    //计算缩放倍数,放大
                    if(getDistance(event)/distance>=1) {


                        rate2 = (getDistance(event) / distance-1f)*0.4f;

                        rate1=rate;
                        rate1+=rate2;
                        rate1= Math.min(rate1, 1f);
                        cameraControl.setLinearZoom(rate1);

                        //Log.e("aa", "双点移动放大" + rate1);
                    }else {
                        rate2=(1f-getDistance(event) / distance)*0.4f;

                        rate1=rate;
                        rate1-=rate2;
                        rate1= Math.max(rate1, 0f);

                        cameraControl.setLinearZoom(rate1);
                        //Log.e("aa", "双点移动缩小" + rate1);
                    }


                }else {
                    Log.e("aa","单点移动");
                }
                //Log.i("aa","滑动");

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(event.getPointerCount()==2){
                    //Log.i("aa","22222222");
                    distance = getDistance(event);
                    state=2;
                }
                Log.e("aa","多点");
                break;
            case MotionEvent.ACTION_POINTER_UP://双指离开
                //手指离开后，重置状态
                rate=rate1;

                state=0;
                break;
        }

        return super.dispatchTouchEvent(event);
    }
    //获取距离
    private static float getDistance(MotionEvent event) {//获取两点间距离
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


    /**
     * 旋转bitmap图像
     */
    private int camea_id=1;
    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        if (camea_id==0){
            matrix.postScale(-1,1);
        }
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


    /**
     * 检测函数
     */
    private void  Dector(Bitmap bitmap) {
        // draw objects on bitmap
        Bitmap rgba = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Yolov5n.Obj[] objects = Yolov5Net.Detect(rgba, false, 0.25f);


        float width = rgba.getWidth();
        float height = rgba.getHeight();

        myview.draws(objects,width,height,true,false);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                myview.draws(objects,width,height,true,false);
//            }
//        }).start();


//        final int[] colors = new int[]{
//                Color.rgb(54, 67, 244),
//                Color.rgb(99, 30, 233),
//                Color.rgb(176, 39, 156),
//                Color.rgb(183, 58, 103),
//                Color.rgb(181, 81, 63),
//                Color.rgb(243, 150, 33),
//                Color.rgb(244, 169, 3),
//                Color.rgb(212, 188, 0),
//                Color.rgb(136, 150, 0),
//                Color.rgb(80, 175, 76),
//                Color.rgb(74, 195, 139),
//                Color.rgb(57, 220, 205),
//                Color.rgb(59, 235, 255),
//                Color.rgb(7, 193, 255),
//                Color.rgb(0, 152, 255),
//                Color.rgb(34, 87, 255),
//                Color.rgb(72, 85, 121),
//                Color.rgb(158, 158, 158),
//                Color.rgb(139, 125, 96)
//        };
//
//        Canvas canvas = new Canvas(rgba);
//
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4);
//
//        Paint textbgpaint = new Paint();
//        textbgpaint.setColor(Color.WHITE);
//        textbgpaint.setStyle(Paint.Style.FILL);
//
//
//        Paint textpaint = new Paint();
//        textpaint.setColor(Color.BLACK);
//        textpaint.setTextSize(26);
//        textpaint.setTextAlign(Paint.Align.LEFT);
//
//        //List<Yolov5n.Obj> chosed_objects=new ArrayList<>();
//
//        int index = 0;
//
//        for (int i = 0; i < objects.length; i++) {
//
//            paint.setColor(colors[i % 19]);
//
//            canvas.drawRect(objects[i].x , objects[i].y , (objects[i].x + objects[i].w), (objects[i].y + objects[i].h) , paint);
//
//            // draw filled text inside image
//            {
//                String text = objects[i].label + " = " + String.format("%.1f", objects[i].prob * 100) + "%";
//
//                float text_width = textpaint.measureText(text);
//                float text_height = -textpaint.ascent() + textpaint.descent();
//
//                float x = objects[i].x ;
//                float y = objects[i].y  - text_height;
//                if (y < 0)
//                    y = 0;
//                if (x + text_width > rgba.getWidth())
//                    x = rgba.getWidth() - text_width;
//
//                canvas.drawRect(x, y, x + text_width, y + text_height, textbgpaint);
//
//                canvas.drawText(text, x, y - textpaint.ascent(), textpaint);
//            }
//        }

//        Message message=new Message();
//        message.what=1;
//        message.obj=rgba;
//        handler.sendMessage(message);

    }




}