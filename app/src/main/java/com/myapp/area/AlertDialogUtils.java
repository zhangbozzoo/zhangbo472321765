package com.myapp.area;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

// 自定义dialog对话框
public class AlertDialogUtils {
    private static View view_custom;
    public static AlertDialog.Builder builder;
    public  static AlertDialog alert ;
    public static TextView tv_dialog_title, tv_dialog_content;
    public static Button dialog_cancelBtn,dialog_confirmBtn;


    public static CheckBox checkBox1;
    public static CheckBox checkBox2;
    public static CheckBox checkBox3;
    public static CheckBox checkBox4;
    public static CheckBox checkBox5;
    public static CheckBox checkBox6;
    public static CheckBox checkBox7;
    public static CheckBox checkBox8;
    public static CheckBox checkBox9;
    public static CheckBox checkBox10;
    public static CheckBox checkBox11;
    public static CheckBox checkBox12;
    public static CheckBox checkBox13;
    public static CheckBox checkBox14;
    public static CheckBox checkBox15;
    public static CheckBox checkBox16;
    public static CheckBox checkBox17;
    public static CheckBox checkBox18;
    public static CheckBox checkBox19;
    public static CheckBox checkBox20;
    public static CheckBox checkBox21;
    public static CheckBox checkBox22;
    public static CheckBox checkBox23;
    public static CheckBox checkBox24;
    public static CheckBox checkBox25;

    public static AlertDialogUtils getInstance() {
        return new AlertDialogUtils();
    }

    /**
     * todo 带有确认取消按钮的自定义dialog
     * @param context 上下文对象
     * @param title 标题
     * @param content 内容
     */
    public static void showConfirmDialog(Context context, String title, String content){
        builder = new AlertDialog.Builder(context);
        alert = builder.create();
        alert.show();

        //引入布局
        view_custom = LayoutInflater.from(context).inflate(R.layout.alert_dialog_defaut,null,false);
        tv_dialog_title = view_custom.findViewById(R.id.title);
        tv_dialog_title.setText(title);
        tv_dialog_content =  view_custom.findViewById(R.id.content);
        tv_dialog_content.setText(content);
        alert.setCancelable(false); //点击空白处不关闭弹窗



        //多选框
        checkBox1=view_custom.findViewById(R.id.check1);
        checkBox2=view_custom.findViewById(R.id.check2);
        checkBox3=view_custom.findViewById(R.id.check3);
        checkBox4=view_custom.findViewById(R.id.check4);
        checkBox5=view_custom.findViewById(R.id.check5);
        checkBox6=view_custom.findViewById(R.id.check6);
        checkBox7=view_custom.findViewById(R.id.check7);
        checkBox8=view_custom.findViewById(R.id.check8);
        checkBox9=view_custom.findViewById(R.id.check9);
        checkBox10=view_custom.findViewById(R.id.check10);
        checkBox11=view_custom.findViewById(R.id.check11);
        checkBox12=view_custom.findViewById(R.id.check12);
        checkBox13=view_custom.findViewById(R.id.check13);
        checkBox14=view_custom.findViewById(R.id.check14);
        checkBox15=view_custom.findViewById(R.id.check15);
        checkBox16=view_custom.findViewById(R.id.check16);
        checkBox17=view_custom.findViewById(R.id.check17);
        checkBox18=view_custom.findViewById(R.id.check18);
        checkBox19=view_custom.findViewById(R.id.check19);
        checkBox20=view_custom.findViewById(R.id.check20);
        checkBox21=view_custom.findViewById(R.id.check21);
        checkBox22=view_custom.findViewById(R.id.check22);
        checkBox23=view_custom.findViewById(R.id.check23);
        checkBox24=view_custom.findViewById(R.id.check24);
        checkBox25=view_custom.findViewById(R.id.check25);



        //为取消按钮设置点击监听
//        view_custom.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mOnDialogButtonClickListener !=null){
//                    mOnDialogButtonClickListener.onNegativeButtonClick(alert);
//                }
//            }
//        });
        //为确认按钮设置点击监听
        view_custom.findViewById(R.id.comfirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnDialogButtonClickListener !=null){
                    mOnDialogButtonClickListener.onPositiveButtonClick(alert);
                }
            }
        });
        //使用布局
        alert.getWindow().setContentView(view_custom);
    }


    //todo 按钮点击回调接口
    public static OnDialogButtonClickListener mOnDialogButtonClickListener;

    public void setMonDialogButtonClickListener(OnDialogButtonClickListener listener){
        this.mOnDialogButtonClickListener = listener;
    }
    public interface OnDialogButtonClickListener{
        void onPositiveButtonClick(AlertDialog dialog); //确认
        void onNegativeButtonClick(AlertDialog dialog); //取消
    }

}

