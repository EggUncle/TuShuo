package com.egguncle.imagetohtml.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.ui.adapter.SectionsPagerAdapter;
import com.egguncle.imagetohtml.ui.dialog.BaseDialog;
import com.egguncle.imagetohtml.ui.dialog.DialogFactory;
import com.egguncle.imagetohtml.ui.dialog.HomeDialog;
import com.egguncle.imagetohtml.ui.dialog.LaboratoryDialog;
import com.egguncle.imagetohtml.ui.view.MyViewPager;
import com.egguncle.imagetohtml.util.ImageUtil;
import com.egguncle.imagetohtml.util.SPUtil;

import java.lang.reflect.Method;

public class HomeActivity extends BaseActivity {

    private TabLayout tabLayout;
    private FloatingActionButton fab;

    private final static String TAG = "HomeActivity";
    private static final int SELECT_PHOTO = 0;//调用相册照片

    // private String htmlStr;

    @Override
    void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
      The {@link ViewPager} that will host the section contents.
     */
        MyViewPager mViewPager = (MyViewPager) findViewById(R.id.container);

        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    void initAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //申请读取SD卡权限
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ) {
                    Log.i(TAG, "onClick: 申请权限");
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                } else {
                    getImage();
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabSelected: ");
                switch (tab.getPosition()) {
                    case 0:
                        if (!fab.isShown()) {
                            fab.show();
                        }
                        break;
                    case 1:
                        if (fab.isShown()) {
                            fab.hide();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    void initVar() {

        //记录下APP打开过，以后不启动开场动画
        SPUtil spUtil=SPUtil.getInstance(this);
        spUtil.recordingLaunch();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onClick: 申请权限成功");
                    getImage();
                } else {
                    //权限申请未通过
                    Log.i(TAG, "onClick: 申请权限失败");
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
            //若获取到了图片的路径，则生成对应html文件并保存
            //  fileUtil.saveFile(imagePath);
            //弹出对话框
        //    HomeDialog dialog = new HomeDialog(this);
            Boolean isLaboratory=SPUtil.getInstance(this).isLaboratory();
            BaseDialog dialog= DialogFactory.getDialog(this,isLaboratory);
  //          LaboratoryDialog dialog=new LaboratoryDialog(this);
            dialog.setTvImgpath(imagePath);
            dialog.setIvDialogImg(imagePath);

            Log.i(TAG, "onActivityResult: " + imagePath);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this,AboutActivity.class));
            return true;
        }else if (id ==R.id.action_laboratory){
            startActivity(new Intent(HomeActivity.this,LaboratoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 调用系统相册获取图片
     */
    private void getImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);//调用相册照片
    }

}
