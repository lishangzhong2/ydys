package com.tphy.hospitaldoctor.bjca;
//
//import android.util.Base64;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
//import android.util.Base64;


public class StringUtils {

   // @RequiresApi(api = Build.VERSION_CODES.O)
    public static String base64Encode(byte [] data)
    {

       return Base64.encodeToString(data, Base64.DEFAULT).trim();
//        return new String(Base64.getEncoder().encode(data));
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] base64Decode(String base64)
    {

        //return Base64.getDecoder().decode(base64);
        return Base64.decode(base64, Base64.DEFAULT);
    }

    /**
     * 字符Base64加密
     * @param str
     * @return
     */
    public static String encodeToString(String str){
        try {
          //  String sstr = str.toString().replaceAll("[\\s*\t\n\r]","");
            return Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT).replaceAll("[\\s*\t\n\r]","");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
	
}
