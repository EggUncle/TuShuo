package com.egguncle.imagetohtml.ui.activity;


import android.support.v7.app.ActionBar;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.util.SPUtil;

/**
 * 实验性功能
 * 用于实验图片的各种尺寸，文字大小等
 */
public class LaboratoryActivity extends BaseActivity {

    private Switch swhLaboratory;




    @Override
    void initView() {
        swhLaboratory = (Switch) findViewById(R.id.swh_laboratory);
        swhLaboratory.setChecked(SPUtil.getInstance(this).isLaboratory());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    void initAction() {
        swhLaboratory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //开启 关闭，实验性功能
                if (b){
                    SPUtil.getInstance(LaboratoryActivity.this).openLaboratoryMode();
                }else{
                    SPUtil.getInstance(LaboratoryActivity.this).closeLaboratoryMode();
                }
            }
        });
    }

    @Override
    void initVar() {

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_laboratory;
    }
}
