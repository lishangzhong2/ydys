package com.tphy.hospitaldoctor.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Common {

    private Toast toast;

    public static float density;

    public Common(Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
    }

    public Common(Fragment fragment) {
        WeakReference<Fragment> fragmentWeakReference = new WeakReference<>(fragment);
    }

    private PopupWindow popupWindow;
    private NumberPicker yearicker, monthicker, dayicker;
    private String year, month, day;
    private Activity activity;
    private SharedPreferences sp;

    public static String getCurrentDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public static String formatDateToYMD(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }

    public static String getDate(Date Date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(Date);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 安装APK程序代码
     */
    public static void installFile(Context context, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(context, "com.tphy.zhyy.fileprovider", file);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }
    }

    public static String getNetTime() throws Exception {
        URL url = new URL("http://www.baidu.com");
        URLConnection uc = url.openConnection();// 生成连接对象
        uc.connect(); // 发出连接
        long ld = uc.getDate(); // 取得网站日期时间
        Date date = new Date(ld); // 转换为标准时间对象
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public static String getDbPath(Context context) {
        return "/data/data/" + context.getPackageName() + "/databases/";
    }

    public static String getDbName() {
        return "cdss.db";
    }


    /**
     * 判断sdcard卡上的文件是否存在 存在，若不完整，则删除之
     */
    public static boolean isExistForSdcard(Context context, String filePath, String fileSize) {
        boolean b = false;
        File f = new File(filePath);
        if (f.exists()) {
            if (fileSize.equals(f.length() + "")) {
                b = true;
            } else {
                f.delete();
            }
        }
        return b;
    }

    /**
     * @param DATE1 起止时间
     * @param DATE2 终止时间
     * @return 判断时间的大小
     */
    public static boolean compare_date(String DATE1, String DATE2) {

        String dates[] = DATE2.split("-");
        String olddates[] = DATE1.split("-");

        if (Long.parseLong(dates[0]) == Long.parseLong(olddates[0])) {

            if (Integer.valueOf(dates[1]).intValue() == Integer.valueOf(olddates[1]).intValue()) {
                if (Integer.valueOf(dates[2]).intValue() >= Integer.valueOf(olddates[2]).intValue()) {
                    return true;
                }
            } else if (Integer.valueOf(dates[1]) > Integer.valueOf(olddates[1])) {
                return true;
            }
        } else if (Long.parseLong(dates[0]) > Long.parseLong(olddates[0])) {
            return true;
        }
        return false;
    }

    public static boolean isMobileNum(String mobiles) {

        Pattern p = Pattern.compile("^[1][3578][0-9]{9}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }


    /**
     * 获取本地图片
     *
     * @param pathString
     * @return
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File("/sdcard/bunner/" + pathString);
            Log.e("获取本地的图片", "file");
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile("/sdcard/bunner/" + pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
    }

    /**
     * @param urlpath
     * @return Bitmap 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 读取html源码
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) throws Exception {

        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }


    public static String testGetHtml(String urlpath) throws Exception {

        //版本4.0后需加这个，不然就报错android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        URL url = new URL(urlpath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            String html = new String(data, "gb2312");
            int st = html.indexOf("名称:");
            String s = html.split("名称")[1];
            String ss = s.split("规格型号")[0];
            String sss = ss.split("<dd>")[1];
            String ssss = sss.split("</dd>")[0];

            return ssss;
        }

        return null;

    }


    /**
     * 图片本地存储
     *
     * @param bitmap
     * @param url
     */
    public static void savePicture(Bitmap bitmap, String url) {
        File f = new File("/sdcard/bunner/", url);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 判断本地文件是否存在   false 存在
     *
     * @param path
     * @return
     */
    public static boolean initDownPath(String path) {
        File file = new File("/sdcard/bunner/" + path);
        Log.e("本地的图片路径", "file");
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }


    /**
     * 弹出时间选择
     */
    public static void showDateDialog(Activity activity, final TextView tv) {
        String date = "";
        Calendar d = Calendar.getInstance(Locale.CHINA);
        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
        Date myDate = new Date();
        // 创建一个Date实例
        d.setTime(myDate);
        // 设置日历的时间，把一个新建Date实例myDate传入
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        //初始化默认日期year, month, day
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            /**
             * 点击确定后，在这个方法中获取年月日
             */
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.setMessage("请选择日期");
        datePickerDialog.show();
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            backgroundAlpha(1f);
        }
    }

    public static String params(String key, String value) {
        String strInput = "";
        try {
            // 输入的JSON字符串(键值对的形式)
            strInput = new JSONStringer().object().key(key).value(value).endObject().toString();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return strInput;
    }

    public static String params(String value, String key2, String value2) {
        String strInput = "";
        try {
            // 输入的JSON字符串(键值对的形式)
            strInput = new JSONStringer().object().key("LoginUserCode").value(value).key(key2).value(value2).endObject().toString();
            Log.e("strInput", strInput);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return strInput;
    }

    public static String params(String key, String value, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5) {
        String strInput = "";
        try {
            // 输入的JSON字符串(键值对的形式)
            strInput = new JSONStringer().object().key(key).value(value).key(key2).value(value2)
                    .key(key3).value(value3).key(key4).value(value4).key(key5).value(value5).endObject().toString();
            Log.e("strInput", strInput);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return strInput;
    }

    /**
     * Map 数组转化成json
     *
     * @param map
     * @return
     */
    public static String MaptoJson(Map<String, String> map) {
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
            key = (String) it.next();
            value = map.get(key);
            if (value.contains("{")) {
                jsonBuffer.append("\"" + key + "\":" + value + "");
            } else {
                jsonBuffer.append("\"" + key + "\":\"" + value + "\"");
            }
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

    /**
     * @param path 图片路径
     * @return
     * @将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @author QQ986945193
     * @Date 2015-01-26
     */
    public static String imageToBase64(String path) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        // 返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    public static void CompresBitmap(Activity at, int img) {
        // png图片
        Bitmap bitmap = BitmapFactory.decodeResource(at.getResources(), img);
        try {
            // 保存压缩图片到本地
            File file = new File(Environment.getExternalStorageDirectory(), "aaa.jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fs);
            fs.flush();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Uri获取文件的绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param fileUri
     */
    public static String getFileAbsolutePath(Activity context, Uri fileUri) {
        if (context == null || fileUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, fileUri)) {
            if (isExternalStorageDocument(fileUri)) {
                String docId = DocumentsContract.getDocumentId(fileUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(fileUri)) {
                String id = DocumentsContract.getDocumentId(fileUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(fileUri)) {
                String docId = DocumentsContract.getDocumentId(fileUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(fileUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(fileUri))
                return fileUri.getLastPathSegment();
            return getDataColumn(context, fileUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(fileUri.getScheme())) {
            return fileUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"
            }, 1);

            return false;
        }

        return true;
    }

    /**
     * @param astring 字符串
     * @return 字符串对应的 unicode编码
     */
    public static String stringToUnicode(String astring) {
        StringBuffer sbuffer = null;
        try {
            sbuffer = new StringBuffer();
            for (int i = 0; i < astring.length(); i++) {
                char cc = astring.charAt(i);
                sbuffer.append("\\u" + Integer.toHexString(cc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbuffer.toString();
    }


//    public static String formatDuring(long mss, int i) {
//        long days = mss / (1000 * 60 * 60 * 24);
//        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
//        long seconds = (mss % (1000 * 60)) / 1000;
//        if (days != 0 && hours != 0) {
//            return String.format("%.1f", (float) days + (float) hours / 24);
//        }
//        if (days != 0 && hours == 0) {
//            return String.valueOf(days);
//        }
//        if (days == 0 && hours != 0) {
//            return String.valueOf(String.format("%.1f", (float) hours / 24));
//        }
//        if (days == 0 && hours == 0 && minutes != 0 && i == 0) {
//            return minutes + "分钟";
//        }
//        if (days == 0 && hours == 0 && i == 1) {
//            return String.format("%.1f", (float) days + (float) hours / 24);
//        }
//        return "不足一分钟";
//    }

    public static int formatDuring(long mss, int i) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
//        if (days != 0 && hours != 0) {
//            return String.format("%.1f", (float) days + (float) hours / 24);
//        }
        return (int) days;
    }

    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    @SuppressLint("SimpleDateFormat")
    public static int formatDuring(String begin, String end) {
        SimpleDateFormat simpleDateFormat = null;
        if (end.contains("-")) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (end.contains("/")) {
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format2.parse(begin);
            endDate = simpleDateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDuring(endDate.getTime() - startDate.getTime(), 0);
    }

    public static int formatDuringyMd(String begin, String end) {
//        Log.e("WQ", "入院时间===" + begin);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(begin);
            endDate = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDuring(endDate.getTime() - startDate.getTime(), 0);
    }

    public static String getSystemTimeyMyHm() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }

    public static String formatMdHm(String time) {
        String formattedTime = "";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat origin = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            formattedTime = simpleDateFormat.format(origin.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;

    }

    public static String getSpecificString(String s, String spot, int beginPosition) {
        String string;
        if (s.contains(spot)) {
            string = s.substring(beginPosition, s.indexOf(spot));
        } else {
            string = s;
        }
        return string;
    }

    public static String[] initQueryDate(String rysj) {
//            Log.e("WQ", "入院时间==" + rysj);
        String[] beginAndEnd = new String[2];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedRYSJ = format.format(new Date(rysj));
        String formattedToday = format.format(new Date());
        int days = Common.formatDuringyMd(formattedRYSJ, formattedToday) + 1;
        int leftDay = 0 - days % 7 + 1;
        Date startDate = getDateAfterDays(new Date(), leftDay);
        Date weekAfter = getWeekAfter(startDate);
        beginAndEnd[0] = format.format(startDate);
        beginAndEnd[1] = format.format(weekAfter);
        Log.e("WQ", "开始日期" + format.format(startDate) + "  结束日期=" + format.format(weekAfter));
        return beginAndEnd;
    }

    public static Date getDateAfterDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date getWeekAfter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 6);
        return calendar.getTime();
    }

    public static Date getWeekAfterDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date getWeekDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -6);
        return calendar.getTime();
    }

    public static String getTime() {
        long l = System.currentTimeMillis();
        return String.valueOf(l);
    }

    public static int getDuration(String str) {
        String a = str.substring(0, 1);
        String b = str.substring(1, 2);
        String c = str.substring(3, 4);
        String d = str.substring(4);
        String duration;
        if (a.equals("0") && b.equals("0")) {
            if (c.equals("0") && Integer.valueOf(d) < 1) {
                return -2;
            } else if (c.equals("0") && Integer.valueOf(d) > 1) {
                duration = d;
                return Integer.valueOf(d);
            } else {
                duration = c + d;
                return Integer.valueOf(c + d);
            }
        } else {
            duration = "60";
            return -1;
        }

    }

    public static String getFomattedTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getYMD() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String formatHomeCardTime(String time) {
        String format = "";
        if (time.contains("-")) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat origin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date parse = origin.parse(time);
                format = simpleDateFormat.format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (time.contains("/")) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date(time);
            format = simpleDateFormat.format(date);
        }
        return format;
    }

    public static String formatHomeCardTimeCommon(String time) {
        String format = "";
        if (time.contains("-")) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat origin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                Date parse = origin.parse(time);
                format = simpleDateFormat.format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (time.contains("/")) {
            format = time;
        }
        return format;
    }

    public static String getException(Exception e) {
        String exception;
        if (e.getClass() == java.net.SocketTimeoutException.class) {
            exception = "请求超时，请重试";
        } else if (e.getClass() == java.net.ConnectException.class) {
            exception = "网络连接异常";
        } else if (e.getClass() == JSONException.class) {
            exception = "json解析异常";
        } else if (e.getClass() == InterruptedException.class) {
            exception = "网络已断开";
        } else {
            exception = "网络错误";
        }
        return exception;
    }


    public static String timeToTimestamp(String time) {
        long timestamp = 0;
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timestamp = date.getTime();
        return String.valueOf(timestamp);
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void compressImageToFile(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ifDuplicated(List<String> list) {
        Set set = new HashSet(list);
        return list.size() == set.size();
    }

    public static String arrayToString(String[] a) {
        String s = "";
        for (int i = 0; i < a.length; i++) {
            if (i < a.length - 1) {
                s += a[i] + ",";
            } else if (i == a.length - 1) {
                s += a[i];
            }
        }
        return s;
    }

    /*设置IP*/
    public static String getIP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        int ipAddress = connectionInfo.getIpAddress();
        String ip = intToIP(ipAddress);
        return ip;

    }

    public static String intToIP(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /*获取当前版本号*/
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }


    public static String formatStringDateYDM(String sjstr){
        String ssdate = formatHomeCardTime(sjstr);
        return ssdate.split(" ")[0];

    }

}
