package com.tphy.hospitaldoctor.bjca;

/**
 * Copyright (C) 2021 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2021 TPHY.Co.,Ltd.  All rights reserved
 * 文件名：(com.tphy.hospitaldoctor.bjca)
 * 文件功能描述：xxx
 * author：tphy_ydys
 * time：2021/7/13 21:04
 */

public class CAContant {

//    public static String Sign_APPID = "APP_0157E810F6894BEEB4DEC70331720557";
//    public static String Sign_ServerUrl = "https://coss-dev.isignet.cn:18759/coss/";
//    public static String Sign_sourceCode = "XTka8lO2NMH9PCffiFWLE1HV4UPYCWMu";
    //正式
    public static String Sign_APPID = "APP_9E19989E097A40768A5100F5D79026D4";
    public static String Sign_ServerUrl = "http://10.10.0.111:8759/coss/";
    public static String Sign_sourceCode = "1rAxB7bQIdLTkpXhjSzObHU9mYI7sSDf";


    public static String Sign_signAlgo = "HMAC";
    public static String Sign_version = "1.0";
    public static String Sign_algorithm = "SM3withSM2";

    /*北京CA接口方法*/

    /**
     * 功能说明;查询用户详细信息，业务系统调用本接口即可完成通过用户uniqueld或证件类型、证件号查询用户的userld（即MSSPID)、姓名等信息。
     */
    public static String CA_QueryUserInfo = "service/v1/queryUserInfo";
    /**
     * 功能说明:业务系统服务端为在协同签名服务器中已经添加的用户申请激活码（最终用户在移动端调用相应输入授权码激活接口完成用户激活)
     */
    public static String CA_getAuthCode = "service/v1/getAuthCode";

    /**
     * 功能说明:业务端查询证书接口
     */
    public static String CA_QueryCert = "service/v1/queryCert";
    /**
     * 功能说明:业务端添加签名任务接口调用示例
     */
    public static String CA_addSignJobUrl = "service/v1/addSignJob";

    public static String  successCode = "0x00000000";
}
