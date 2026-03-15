package com.tphy.hospitaldoctor.utils.ftp;

import android.os.AsyncTask;

/**
 * Created by ZhangHao on 2017/5/19.
 * ftp下载单个文件
 */

public class FtpDownLoadFileTask extends AsyncTask<String, Integer, Boolean> {
    //ftp工具类
    private FtpHelper ftpHelper;
    //回调
    private ftpNetCallBack callBack;
    //ftp目录路径
    private String ftpFolder;
    //本地文件夹
    private String localFilePath;
    //需要下载的文件
    private String fileName;
    private String folderName;

    public FtpDownLoadFileTask(FtpHelper ftpHelper, ftpNetCallBack callBack,
                               String ftpFolder, String fileName, String localFilePath, String folerName) {
        this.ftpHelper = ftpHelper;
        this.callBack = callBack;
        this.ftpFolder = ftpFolder;
        this.localFilePath = localFilePath;
        this.fileName = fileName;
        this.folderName = folerName;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean result = false;
        try {
            if (ftpHelper != null && ftpHelper.isConnect()) {
//                result = ftpHelper.downloadFile(ftpFolder, folderName,fileName, localFilePath);
//                Log.e("WQ", "ftpfolder==" + ftpFolder + "   localFilePath==" + localFilePath + "  fileName==" + fileName);
                result = ftpHelper.downloadFolder(ftpFolder, localFilePath, folderName, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        callBack.downLoadFinish(result);
    }
}
