package com.tphy.hospitaldoctor.utils.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.util.List;

/**
 * Created by ZhangHao on 2017/5/19.
 * 用于ftp操作回调
 */

public interface ftpNetCallBack {

    //获取文件夹下文件列表
    void getFtpFileList(List<FTPFile> ftpFileList);

    //下载
    void downLoadFinish(boolean result);

    //上传
    void uploadFinish(boolean result);
}
