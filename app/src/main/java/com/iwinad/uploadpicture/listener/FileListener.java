package com.iwinad.uploadpicture.listener;

import android.os.FileObserver;

import com.iwinad.uploadpicture.service.MonitorService;

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
public class FileListener extends FileObserver {

    private String filePath;

    //mask:指定要监听的事件类型，默认为FileObserver.ALL_EVENTS
    public FileListener(String path, int mask) {
        super(path, mask);
        this.filePath = path;
    }

    public FileListener(String path) {
        super(path);
    }

    @Override
    public void onEvent(int event, String path) {
        final int action = event & FileObserver.ALL_EVENTS;
        switch (action) {
            case FileObserver.ACCESS:
                System.out.println("event: 文件或目录被访问, path: " + path);
                break;

            case FileObserver.DELETE:
                System.out.println("event: 文件或目录被删除, path: " + path);
                break;

            case FileObserver.OPEN:
                System.out.println("event: 文件或目录被打开, path: " + path);
                break;

            case FileObserver.MODIFY:
                System.out.println("event: 文件或目录被修改, path: " + path);
                break;

            case FileObserver.CREATE:
                System.out.println("event: 文件或目录被创建, path: " + path);
                MonitorService.getMonitorService().uploadImage(filePath+"/"+path.replace(".tmp",""));
                break;
        }
    }
}
