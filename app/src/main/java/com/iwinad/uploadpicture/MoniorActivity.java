package com.iwinad.uploadpicture;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.WindowManager;

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

    public static MoniorActivity moniorActivity;

    private PowerManager powerManager;
    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_layout);
        ButterKnife.bind(this);
        moniorActivity = this;

        startMonitorService();

        powerManager = (PowerManager)getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");
            mWakeLock.acquire(); // in onPause() call
        }
    }
    private void startMonitorService(){
        Intent intent = new Intent(this,MonitorService.class);
        startService(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(MonitorService.getMonitorService()!=null){
            MonitorService.getMonitorService().removeFloatView();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(MonitorService.getMonitorService()!=null){
            MonitorService.getMonitorService().addFloatView();
        }
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
