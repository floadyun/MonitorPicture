package com.iwinad.uploadpicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.base.lib.baseui.AppBaseActivity;
import com.iwinad.uploadpicture.service.MonitorService;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @copyright : yixf
 *
 * @author : yixf
 *
 * @version :1.0
 *
 * @creation date: 2019/7/19
 *
 * @description:个人中心
 */
public class MoniorActivity extends AppBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_layout);
        ButterKnife.bind(this);

        startMonitorService();
    }
    private void startMonitorService(){
        Intent intent = new Intent(this,MonitorService.class);
        startService(intent);
    }
    /**
     * 退出
     */
    @OnClick(R.id.exit_btn)void exitApp(){
        finishSelf();
    }
    /**
     * 打开系统设置
     */
    @OnClick(R.id.system_setting_btn)void openSetting(){
        SettingDialogFragment settingFragment = new SettingDialogFragment();
        settingFragment.show(getSupportFragmentManager(),"SettingDialogFragment");
    }
}
