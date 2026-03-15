package com.tphy.hospitaldoctor.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.ui.adapter.JianYanAdapter;
import com.tphy.hospitaldoctor.ui.adapter.XiJunAdapter;
import com.tphy.hospitaldoctor.ui.bean.JianYanResult;
import com.tphy.hospitaldoctor.ui.bean.XiJunResult;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.FileUtils;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.hospitaldoctor.widget.MyItemDecoration;
import com.tphy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.org.bjca.signet.coss.component.core.i.G;
import okhttp3.Call;

public class BaoGaoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.baogao_recycler)
    RecyclerView recycler;
    @BindView(R.id.baogao_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.header_jianyan)
    LinearLayout header_jianyan;
    @BindView(R.id.header_xijun)
    LinearLayout header_xijun;
    @BindView(R.id.jiancha_relative)
    RelativeLayout jiancha_relative;
    @BindView(R.id.jianyan_linear)
    LinearLayout jianyan_linear;
//    @BindView(R.id.jiancha_image)
//    ImageView iv_yingxinag;
    @BindView(R.id.jiancha_webview)
    WebView jiancha_webview;
    @BindView(R.id.baogao_tv_title)
    TextView tv_title;
//    @BindView(R.id.jiacha_listview)
//    ListView jiancha_listview;
    @BindView(R.id.jiancha_resultTv)
    TextView jiancha_resultTv;
    @BindView(R.id.tv_zbzcck)
    TextView tv_zbzcck ;
    @BindView(R.id.sv_yingxiang)
    ScrollView sv_yingxiang;
    @BindView(R.id.iv_pacs_next)
    ImageView iv_pacs_next;
    @BindView(R.id.iv_pacs_last)
    ImageView iv_pacs_last;
    @BindView(R.id.ll_imagetop)
    LinearLayout imagetop;
    @BindView(R.id.jiancha_checkbutton)
    Button checkbutton;


    private List<JianYanResult> jianYanResults;
    private List<XiJunResult> xiJunResults;
    //    private JianYanAdapter adapter;
    private String sqdh;
    private ActivityUtils activityUtils;
    private  int currentfileindex = 0;
    private ProgressDialog progressDialog;
    private String pacsWebviewUrl;
    private HashMap<String, PacsBean> patientFileidsMap;
    private String zhuYuanHao;
    private String examNo;
    private String examClass;
    private String urlList;


    @Override
    protected int getLayoutID() {
        return R.layout.frag_jiancha;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new MyItemDecoration(context, MyItemDecoration.VERTICAL_LIST));

//        recycler.setAdapter(adapter);
        iv_pacs_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String skey = zhuYuanHao + "_" + examNo;
                PacsBean pacsBean = patientFileidsMap.get(skey);

                if (pacsBean!=null&& pacsBean.getFileids().size()>1){
                    if (currentfileindex==0){
                        activityUtils.showToast("已经是第一张了");
                    }else {
                        currentfileindex = currentfileindex-1;
                        TransportRemoteToLocal(czydm,pacsBean.getFileids().get(currentfileindex),pacsBean.getPacsPath());
                    }
                }

            }
        });
        iv_pacs_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skey = zhuYuanHao + "_" + examNo;
                PacsBean pacsBean = patientFileidsMap.get(skey);

                if (pacsBean!=null&& pacsBean.getFileids().size()>1){
                    if (currentfileindex<pacsBean.getFileids().size()-1){
                        currentfileindex = currentfileindex+1;
                        TransportRemoteToLocal(czydm,pacsBean.getFileids().get(currentfileindex),pacsBean.getPacsPath());
                      }else {
                        activityUtils.showToast("已经是最后一张了");

                    }
                }

            }
        });
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String skey = zhuYuanHao + "_" + examNo;
//                PacsBean pacsBean = patientFileidsMap.get(skey);
//                showWebViewByStudyid(pacsBean.getFileids().get(currentfileindex));
                String msg= Base64.getEncoder().encodeToString(urlList.getBytes());
                Uri uri = Uri.parse("http://10.10.4.200:8093/client/view/exam-info.html?DJ="+msg);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        initWebview();
    }

    private  void  getUrl(String urlList){
        this.urlList = urlList;
    }
    private void initWebview() {
        WebSettings webSettings = jiancha_webview .getSettings();

        jiancha_webview.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码或其他

        webSettings.setJavaScriptEnabled(true); //支持js

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小

        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。

        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.supportMultipleWindows(); //多窗口

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存

        webSettings.setAllowFileAccess(true); //设置可以访问文件

        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片

        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        jianYanResults = new ArrayList<>();
        xiJunResults = new ArrayList<>();
        activityUtils = new ActivityUtils(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(true);
        patientFileidsMap = new HashMap<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        getLisItemInfoDAL(sqdh);

    }

    @Override
    protected void setLandScapeView() {
        super.setLandScapeView();
    }

    @Override
    protected void setPortraitView() {
        super.setPortraitView();
    }

    @Override
    protected void initLviewsAndEvents() {
        super.initLviewsAndEvents();
    }

    @Override
    protected void initPviewsAndEvents() {
        super.initPviewsAndEvents();
    }

    /*根据申请单号获取检验结果*/
    public void getLisItemInfoDAL(String sqdh) {
        tv_title.setText("检验项目");
        showHeaderJianyan();
        this.sqdh = sqdh;
        if (null != swipeRefreshLayout && !swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Map<String, String> params = new HashMap<>();
//        Log.e("WQ", "检查" + sqdh);
        params.put("sqdh", sqdh);
        String url = Constant.BaseURL + "/GetLisItemInfoDAL";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        jianYanResults.clear();
                        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JianYanResult jianYanResult = new JianYanResult();
                                JSONObject jsonObject = array.getJSONObject(i);
                                String zwmc = jsonObject.getString("ZWMC");
                                String jg = jsonObject.getString("JG");
                                String dw = jsonObject.getString("DW");
                                String sx = jsonObject.getString("SX");
                                String xx = jsonObject.getString("XX");
                                String range = "";
                                if ((sx.equals("0.000") || sx.equals("0.0")) && (xx.equals("0.000") || xx.equals("0.0"))){
                                    range = "";
                                }else{
                                    range = xx +"-"+ sx;
                                }
                                FileUtils.recordToFile("lsz","range","range="+range);
//                                String range = jsonObject.getString("RANGE");
                                String zt = jsonObject.getString("ZT");
                                String ywmc = jsonObject.getString("YWMC");
                                jianYanResult.setZWMC(zwmc);
                                jianYanResult.setJG(jg);
                                jianYanResult.setDW(dw);
                                jianYanResult.setRANGE(range);
                                jianYanResult.setZT(zt);
                                jianYanResult.setYWMC(ywmc);
                                jianYanResults.add(jianYanResult);
                                JianYanAdapter adapter = new JianYanAdapter(context, jianYanResults);
                                recycler.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Gson gson = new Gson();
//                        jianYanResults = gson.fromJson(response, new TypeToken<List<JianYanResult>>() {
//                        }.getType());
                    }
                });
    }

    /*根据检查申请单获取彩超结果*/
    public void getCheckDescriptionDAL(final String examNo,final String examClass, String zhuYuanHao) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();

        this.examNo = examNo;
        this.examClass = examClass;
        String skey = zhuYuanHao + "_" + examNo;
//        if (patientFileidsMap.containsKey(skey)){//已经获取过 文件名 集合 直接去 调生成的接口
//            PacsBean pacsBean = patientFileidsMap.get(skey);
//            TransportRemoteToLocal(czydm,pacsBean.getFileids().get(0),pacsBean.getPacsPath());
//            return;
//        }
        this.zhuYuanHao = zhuYuanHao;
        Map<String, String> params = new HashMap<>();
        params.put("examNo", examNo);
        final String url = Constant.BaseURL+ "/GetCheckDescriptionDAL";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            response = response.replace("nbsp;","");
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String exam_report = jsonObject.getString("exam_report");
                            //这个pacsPth是pacs 提供的影像路径
                            String pacsPth = jsonObject.getString("URLLIST");
                            if (pacsPth!="" && !pacsPth.equals("")){
                            String[] allUrlList = pacsPth.split("\\$");
                           String urlList = allUrlList[5];
                            getUrl(urlList);
                            pacsPth = pacsPth.replace("$","\\");
                            if (!pacsPth.equals("")){
                                getAllPacsName(pacsPth,examNo);
                                jiancha_resultTv.setText(exam_report);
                            }else {
                                activityUtils.showToast("未找到影像数据！");
                            }
                            }else{
                                jiancha_resultTv.setText(exam_report);
                                progressDialog.dismiss();

                            }

//                            //US为彩超，如果是彩超就显示在平板，否则显示查看影像按钮 聊城退役暂时不显示影像
//                            if(!examClass.equals("US")){
//                                iv_pacs_last.setVisibility(View.GONE);
//                                iv_pacs_next.setVisibility(View.GONE);
//                                imagetop.setVisibility(View.GONE);
//                                checkbutton.setVisibility(View.VISIBLE);
//
//                            }else{
//                                iv_pacs_last.setVisibility(View.VISIBLE);
//                                iv_pacs_next.setVisibility(View.VISIBLE);
//                                imagetop.setVisibility(View.VISIBLE);
//                                checkbutton.setVisibility(View.GONE);
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    private void getAllPacsName(final String pacsPath, final String examNo) {
        // 初始化显示页面
        jiancha_resultTv.setText("");
        jiancha_webview.setVisibility(View.GONE);
        currentfileindex=0;//初始化 当先显示的 pacs影像序号
        Map<String, String> params = new HashMap<>();
        params.put("path", pacsPath);
        String url = Constant.BaseURL+ "/getAllPacsName";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String emr_doc = jsonObject.getString("bz");
                            String success = jsonObject.getString("success");
                            PacsBean pacsBeans = new PacsBean();

                            if (success.equals("true")){
                                if (emr_doc.contains(";")){
                                    pacsWebviewUrl = jsonObject.getString("url");
                                        List<String> fileids = new ArrayList<>();
                                        String[] split = emr_doc.split(";");
                                        for (int i = 0; i < split.length; i++) {
                                            fileids.add(split[i]);
                                        }
                                    pacsBeans.setFileids(fileids);
                                    pacsBeans.setPacsPath(pacsPath);
                                  //  Log.e(TAG, "fileids "+fileids.size() );
                                    patientFileidsMap.put(zhuYuanHao+"_"+examNo,pacsBeans);
                                }
                            }else {
                                if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
                                activityUtils.showToast(emr_doc);
                                return;
                            }

                            if (null!=pacsBeans.getFileids()&&pacsBeans.getFileids().size()>0){
                                jiancha_webview.setVisibility(View.VISIBLE);
                                TransportRemoteToLocal(czydm, pacsBeans.getFileids().get(0) , pacsPath);

                                tv_zbzcck.setVisibility(View.GONE);
                                if (pacsBeans.getFileids().size()>1&&examClass.equals("US")){//如果不是一个pacs影像
                                    iv_pacs_last.setVisibility(View.VISIBLE);
                                    iv_pacs_next.setVisibility(View.VISIBLE);
                                }else {
                                    iv_pacs_last.setVisibility(View.GONE);
                                    iv_pacs_next.setVisibility(View.GONE);
                                }
                            }else {
                                tv_zbzcck.setVisibility(View.VISIBLE);
                                tv_zbzcck.setText("未找到pacs影像数据");
                                jiancha_webview.setVisibility(View.GONE);
                                iv_pacs_last.setVisibility(View.GONE);
                                iv_pacs_next.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
}

    private void TransportRemoteToLocal(String czydm, final String fileName, String pacsPath) {

        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("fileName", fileName);
        params.put("pacsPath", pacsPath);
        String url = Constant.BaseURL+ "/TransportRemoteToLocal";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        activityUtils.showToast(Common.getException(e));
                        if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        try {
                            if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
                            Log.e(TAG, "TransportRemoteToLocal: "+response);
//                            JSONArray array = new JSONArray(response);
//                            JSONObject jsonObject = array.getJSONObject(0);
//                            String bz = jsonObject.getString("bz");
//                            String success = jsonObject.getString("success");
                            jiancha_webview.setVisibility(View.VISIBLE);
                            showWebViewByStudyid(fileName);
//                            if (success.equals("true")){
//                            }else {
//                                activityUtils.showToast(bz);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                });


}


    private void showWebViewByStudyid(String name) {
        jiancha_webview.setVisibility(View.VISIBLE);
        String pacurl;
        if(examClass.equals("US")) {
            String[] spliturl = pacsWebviewUrl.split("？");
            pacurl = spliturl[0] + name + "&czydm=" + czydm;
        }else {
//        Log.e(TAG, "showWebViewByStudyid: "+pacurl );
            String msg= Base64.getEncoder().encodeToString(urlList.getBytes());
            pacurl = "http://10.10.4.200:8093/client/view/exam-info.html?DJ="+msg;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }
        WebStorage.getInstance().deleteAllData();
        jiancha_webview.clearHistory();
        jiancha_webview.loadUrl(pacurl);
    }



    /*根据申请单号和标本号获取细菌项目结果*/
    public void getGermItemInfoDAL() {
        tv_title.setText("细菌项目");
        showHeaderXiJun();
        Map<String, String> params = new HashMap<>();
        params.put("sqdh", "");
        params.put("test_no", "");
        String url = Constant.BaseURL + "/GetGermItemInfoDAL";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        xiJunResults.clear();
                        Gson gson = new Gson();
                        xiJunResults = gson.fromJson(response, new TypeToken<List<XiJunResult>>() {
                        }.getType());
                        XiJunAdapter xiJunAdapter = new XiJunAdapter(context, xiJunResults);
                        recycler.setAdapter(xiJunAdapter);
                    }
                });
    }


    public void showJianyan() {
        if (jianyan_linear.getVisibility() == View.GONE) {
            jianyan_linear.setVisibility(View.VISIBLE);
            jiancha_relative.setVisibility(View.GONE);
            sv_yingxiang.setVisibility(View.GONE);
        }
    }

    public void showJianCha() {
        if (jiancha_relative.getVisibility() == View.GONE) {
            jiancha_relative.setVisibility(View.VISIBLE);
            sv_yingxiang.setVisibility(View.GONE);
            jianyan_linear.setVisibility(View.GONE);
        }
    }

    private void showHeaderJianyan() {
        if (header_jianyan.getVisibility() == View.GONE) {
            header_jianyan.setVisibility(View.VISIBLE);
            header_xijun.setVisibility(View.GONE);
        }
    }

    private void showHeaderXiJun() {
        if (header_xijun.getVisibility() == View.GONE) {
            header_xijun.setVisibility(View.VISIBLE);
            header_jianyan.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            //tv_zbzcck.setText("请先选择检查项目");
            tv_zbzcck.setVisibility(View.VISIBLE);
            jiancha_webview.setVisibility(View.GONE);
            //ll_imagetop.setVisibility(View.GONE);
            iv_pacs_last.setVisibility(View.GONE);
            iv_pacs_next.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileUtils.deleteFile(new File(Environment.getExternalStorageDirectory().toString() + "/download/pacsimages/"));
    }

    private class PacsBean {
        private List<String> fileids ;
        private  String pacsPath;

        public PacsBean() {
        }

        public List<String> getFileids() {
            return fileids;
        }

        public void setFileids(List<String> fileids) {
            this.fileids = fileids;
        }

        public String getPacsPath() {
            return pacsPath;
        }

        public void setPacsPath(String pacsPath) {
            this.pacsPath = pacsPath;
        }
    }

    public void clearConttent() {
        Log.e(TAG, "clearConttent: " );
        jiancha_resultTv.setText("");
        jiancha_webview.loadUrl("about:blank");
        jianYanResults = new ArrayList<>();
        JianYanAdapter adapter = new JianYanAdapter(context, jianYanResults);
        recycler.setAdapter(adapter);
    }
}
