package com.tphy.hospitaldoctor.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by tphy on 2018/3/15.
 */

public class FileUtils {
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private static final String TAG = FileUtils.class.getSimpleName();//类名
    private Context context;
    //日志是否需要读写开关
    public static final boolean DEBUG_FLAG = true;
    //private static String TAG = "LogToFile";

    private static String logPath = null;//log日志存放路径

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);//日期格式;

    private static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

    public FileUtils(Context context) {
        this.context = context;
    }

    public static File createTmpFile(Context context) throws IOException {
        File dir = null;
        if(TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            if (!dir.exists()) {
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
                if (!dir.exists()) {
                    dir = getCacheDirectory(context, true);
                }
            }
        }else{
            dir = getCacheDirectory(context, true);
        }
        return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir);
    }


    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";


    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }


    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue #989)
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }


    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }


    //flie：要删除的文件夹的所在位置
    public  static   void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            // file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context) {
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFilePath(Context context) {


        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            Log.e("WCX", "getFilePath: "+context.getExternalFilesDir(null).getPath() );
            return context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/Logs/log_2016-03-14_16-15-09.log
        } else {
            //Log.e("WCX", "getFilePath: "+context.getFilesDir().getPath() );
            return context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }


    public static String getErrorpath(){
        if (null == logPath) {
            Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return "";
        }

        String filepath = logPath + "/logerror/";//log日志名，使用时间命名，保证不重复
        return filepath;
    }

    /**
     * 将record手持机的提交记录保存到手机本地文件中
     *
     * @param czymc
     * @param gzltag
     * @param msg
     */
    public static void recordToFile(String  czymc, String gzltag, String msg)  {

        try {
            //时间判断
            SimpleDateFormat dateFormatYM = new SimpleDateFormat("yyyyMM", Locale.US);
            SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyy_MM_dd", Locale.US);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTime(date);
            String  currentYM = dateFormatYM.format(date);
            String currentYMD = dateFormatYMD.format(date);
            calendar.add(Calendar.MONTH,-3);
            String beforeYM = calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)<=9?"0"+calendar.get(Calendar.MONTH):""+calendar.get(Calendar.MONTH));

            if (null == logPath) {
                Log.e(TAG, "logPath == null ，未初始化LogToFile");
                return;
            }
            //判断之前上上个月的记录是否存在 ，存在的话删除
            File  beforfile  = new File(logPath+"/"+beforeYM);
            if (beforfile.exists()){
                deleteFile(beforfile);
            }

            String fileName = logPath + "/" +currentYM+ "/record"+currentYMD + ".txt";//log日志名，使用时间命名，保证不重复
            String log ="-------------"+gzltag+"---------------------------------------------------" +"\n"+
                    "时间："+dateFormat.format(FileUtils.date) + " " + "操作员名称："+czymc+"     类型："+gzltag+ "\n"
                    + "内容：" + " " + msg + "\n";//log日志内容，可以自行定制

            //如果父路径不存在
            File file = new File(logPath+"/"+currentYM);
            if (!file.exists()) {
                //创建父路径
                file.mkdirs();
            }

            FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
            BufferedWriter bw = null;
            try {

                fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
                bw = new BufferedWriter(new OutputStreamWriter(fos));
                bw.write(log);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();//关闭缓冲流
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
