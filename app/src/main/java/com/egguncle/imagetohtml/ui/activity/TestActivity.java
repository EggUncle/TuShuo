package com.egguncle.imagetohtml.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.util.FileUtil;
import com.egguncle.imagetohtml.util.Image2Html;
import com.egguncle.imagetohtml.util.ImageUtil;


public class TestActivity extends BaseActivity {

    private final static String TAG = "TestActivity";

    private Button activityBtnTest;
    private ImageView activityImg;
    private Button activityBtnLoad;
    private Button activityBtnSelectImg;

    private static final int SELECT_PHOTO = 0;//调用相册照片


    private String str;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        initView();
//        initAction();
//    }

    @Override
    void initVar() {

    }

    @Override
    void initView() {
        activityBtnTest = (Button) findViewById(R.id.activity_btn_test);
        activityImg = (ImageView) findViewById(R.id.activity_img);
        activityBtnLoad = (Button) findViewById(R.id.activity_btn_load_html);
        activityBtnSelectImg = (Button) findViewById(R.id.activity_btn_select_img);

    }

    @Override
    void initAction() {

        activityBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //申请读取SD卡和写文件的权限
                if (ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED
                        ) {
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);

                } else {
                    //saveFile();
                }
            }
        });

        activityBtnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, WebViewActivity.class);
                intent.putExtra("url", FileUtil.getFileUrl("test10.html"));
                startActivity(intent);
            }
        });

        activityBtnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }


    @Override
    int getLayoutId() {
        return R.layout.activity_test;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // saveFile();
                } else {
                    //权限申请未通过

                }
                break;
            default:

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: ");
        String imagePath = "";
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "onActivityResult: SELECT_PHOTO");
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统用这个方法处理图片
                        imagePath = ImageUtil.handleImageOnKitKat(data);

                    } else {
                        //4.4以下系统使用这个方法处理图片
                        imagePath = ImageUtil.handleImageBeforeKitKat(data);

                    }

                }

                break;
            default:
                break;

        }
        if (!imagePath.isEmpty()) {
            saveFile(imagePath);
            Log.i(TAG, "onActivityResult: " + imagePath);
        }
    }

    /**
     * 将图片转换为html文件，并保存
     *
     * @param filePath
     */
    public void saveFile(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Image2Html image2Html = new Image2Html();
            //    str = image2Html.imageToHtml(filePath, 10, "测试");

                FileUtil fileUtil = new FileUtil();
                long time = System.currentTimeMillis();
                fileUtil.creatFile(time + ".html", str);
            }
        }).start();
    }


    private void getImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);//调用相册照片
    }

}
