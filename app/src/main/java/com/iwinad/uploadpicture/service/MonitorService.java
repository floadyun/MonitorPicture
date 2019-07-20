package com.iwinad.uploadpicture.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.base.lib.util.ToastUtil;
import com.iwinad.uploadpicture.FtpUtil;
import com.iwinad.uploadpicture.MoniorActivity;
import com.iwinad.uploadpicture.PreferenceUtil;
import com.iwinad.uploadpicture.TakePhotoActivity;
import com.iwinad.uploadpicture.listener.FileListener;
import com.orhanobut.logger.Logger;

/**
 * VR播放服务
 */
public class MonitorService extends Service {

	private static MonitorService mScoketService;

	private static final String channelID = "UpdateCheck_channel_id";
	private static final String channelName = "UpdateCheck_channelname";

	private FileListener fileListener;
	private String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera";

	private Handler mHandler;

	public static MonitorService getMonitorService(){
		return mScoketService;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mScoketService = this;
		mHandler = new Handler();
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			createNotificationChannel();
			Notification notification = new Notification.Builder(getApplicationContext(), channelID).build();
			startForeground(1, notification);
		}
		initFileListner();
	}

	private void initFileListner(){
		fileListener  = new FileListener(FilePath, FileObserver.CREATE);
		fileListener.startWatching();
	}
	private void createNotificationChannel(){
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
		notificationManager.createNotificationChannel(notificationChannel);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	private int picIndex;
	/**
	 * 上传图片
	 */
	public void uploadImage(final String imagePath){
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String serverIp = PreferenceUtil.getPreference(getApplicationContext()).getStringPreference(PreferenceUtil.DEFAULT_IP,"192.168.1.10");
						String serverPort = PreferenceUtil.getPreference(getApplicationContext()).getStringPreference(PreferenceUtil.DEFAULT_PORT,"21");
						String userName = PreferenceUtil.getPreference(getApplicationContext()).getStringPreference(PreferenceUtil.REMOTE_USER,"");
						String password = PreferenceUtil.getPreference(getApplicationContext()).getStringPreference(PreferenceUtil.REMOTE_PASSWORD,"");
						String remotePath = PreferenceUtil.getPreference(getApplicationContext()).getStringPreference(PreferenceUtil.REMOTE_FILE_PAHT,"C:/ftpRoot");
						FtpUtil.connectFtp(picIndex,serverIp, serverPort, userName, password,remotePath,imagePath);
						picIndex++;
					}
				}).start();
			}
		},200);
	}
}
