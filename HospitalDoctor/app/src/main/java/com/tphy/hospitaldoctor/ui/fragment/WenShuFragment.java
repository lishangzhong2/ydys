package com.tphy.hospitaldoctor.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.ui.activity.MainActivity;
import com.tphy.hospitaldoctor.ui.adapter.XueTangAdapter;
import com.tphy.hospitaldoctor.ui.bean.PDASetting;
import com.tphy.hospitaldoctor.ui.bean.TWDInfor;
import com.tphy.hospitaldoctor.ui.bean.XueTangBean;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.hospitaldoctor.views.BodyTemperatureChart;
import com.tphy.hospitaldoctor.widget.MyItemDecoration;
import com.tphy.hospitaldoctor.widget.SmartScrollView;
import com.tphy.hospitaldoctor.widget.ZoomImageView;
import com.tphy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class WenShuFragment extends BaseFragment {
    @BindView(R.id.wenshu_tv_content)
    EditText tv_content;
    @BindView(R.id.wenshu_tv_title)
    TextView tv_title;
    @BindView(R.id.wenshu_iv_backTop)
    ImageView iv_backTop;
    @BindView(R.id.wenshu_iv_update)
    ImageView iv_updateWS;
    @BindView(R.id.wenshu_scroll)
    SmartScrollView scrollView;
    @BindView(R.id.iv_ws)
    ZoomImageView imageView;
    @BindView(R.id.iv_before)
    ImageView iv_before;
    @BindView(R.id.iv_next)
    ImageView iv_next;



    //    体温单
    @BindView(R.id.twd_layout)
    ScrollView twdLayout;
    @BindView(R.id.xtjcb_layout)
    LinearLayout xtjcbLayout;
    @BindView(R.id.twd_tv_title)
    TextView twd_tv_title;
    @BindView(R.id.tv_qssj)
    TextView tv_qssj;
    @BindView(R.id.tv_zzsj)
    TextView tv_zzsj;
    @BindView(R.id.frag_xtd_list)
    RecyclerView frag_xtd_list;
    @BindView(R.id.tv_xtsjlx)
    TextView tv_xtsjlx;
    @BindView(R.id.item_title_2am)
    TextView item_title_2am;
    @BindView(R.id.item_title_xt)
    TextView item_title_xt;

    @BindView(R.id.tv_gzlsqsx)
    Button tv_gzlsqsx;
    @BindView(R.id.tiwendanView)
    BodyTemperatureChart bodyTemperatureChart;
    private List<TWDInfor> twdInforList;
    private List<XueTangBean> xuetangInforList;
    private ProgressDialog progressDialog;
    private Date currentDate;
    private Date startDate;
    private Date endDate;
    private String zhuyuanhao;
    private ActivityUtils activityUtils;
    private String qssj;
    private String ruyuanshijian;
    private float downPosition;/*屏幕按下的位置*/
    private float upPosition;/*松开的位置*/
    private boolean bottomFlag;
    private String creatorID = "";
    private String mrCode = "";
    private String fileNo = "";
    private List<String> wenshuUrls;
    private String wenshuTitle;
    private int currentIn = 0;
    XueTangAdapter xueTangAdapter;
    private String xtrysj;
    private boolean xtxtlx = false;

    @Override
    protected int getLayoutID() {
        return R.layout.frag_wenshu;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(true);
        currentDate = new Date();
        startDate = Common.getWeekDate(currentDate);
        activityUtils = new ActivityUtils(this);
        scrollView.setScrollViewBottomListener(new SmartScrollView.ScrollViewBottomListener() {
            @Override
            public void onScrollViewBottomListener(boolean isBottom) {
//                Log.e("WQ", "偏移量==" + (downPosition - upPosition));
                if (isBottom && (downPosition - upPosition >= 380.0)) {
                    bottomFlag = isBottom;
                    MainActivity holdingActivity = (MainActivity) getHoldingActivity();
                    holdingActivity.wenshuAutoScrollToNext();
                }
            }
        });
        scrollView.setOnTouchListener(touchListener);
        tv_qssj.setText(ruyuanshijian);
        tv_zzsj.setText(Common.getDate());

        frag_xtd_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        frag_xtd_list.addItemDecoration(new MyItemDecoration(context, MyItemDecoration.VERTICAL_LIST));

        xueTangAdapter = new XueTangAdapter(context, xuetangInforList,xtxtlx);
        frag_xtd_list.setAdapter(xueTangAdapter);

        getPDAConfig();
    }

    private void getPDAConfig() {
        HashMap<String, String> hMap = new HashMap<>();
        String urls = Constant.BaseURL+"/GetPDASetting";
        OkHttpUtils.post().params(null).url(urls).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {}

            @Override
            public void onResponse(String response, int id) {

                List<PDASetting> listpdas= new Gson().fromJson(response,new TypeToken<List<PDASetting>>(){}.getType());
                if (null!=listpdas&&listpdas.size()>0){
                    for (PDASetting dBean:listpdas) {
                        if ("40".equals(dBean.getDM().trim())){
                            if ((dBean.getBZ()+",").trim().contains(ksdm+",")){
                                xtxtlx = true;
                                tv_xtsjlx.setText("随机血糖");
                                item_title_xt.setVisibility(View.VISIBLE);
                                item_title_2am.setVisibility(View.VISIBLE);
                            }else {
                                xtxtlx = false;
                                tv_xtsjlx.setText("随机血糖");
                                item_title_xt.setVisibility(View.GONE);
                                item_title_2am.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });
    }

    private final View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downPosition = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    upPosition = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        twdInforList = new ArrayList<>();
        xuetangInforList = new ArrayList<>();
        wenshuUrls = new ArrayList<>();
    }

    @OnClick({R.id.tv_lastweek, R.id.tv_thisweek, R.id.tv_nextweek, R.id.wenshu_iv_backTop, R.id.wenshu_iv_update,R.id.iv_next,R.id.iv_before,R.id.tv_qssj,R.id.tv_zzsj,R.id.tv_gzlsqsx})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_lastweek:
                @SuppressLint("SimpleDateFormat") SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd");
                if (qssj.equals(before.format(new Date(ruyuanshijian)))) {
                    activityUtils.showToast("没有上周体征信息");
                } else {
                    try {
                        Date parse = before.parse(qssj);
                        endDate = Common.getWeekAfterDays(parse, -1);
                        startDate = Common.getWeekDate(endDate);
                        String startTime0 = before.format(startDate);
                        String endTime0 = before.format(endDate);
                        getTwdInfo(zhuyuanhao, startTime0, endTime0, ruyuanshijian);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_thisweek:
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String[] strings = Common.initQueryDate(ruyuanshijian);
                try {
                    startDate = format.parse(strings[0]);
                    endDate = format.parse(strings[1]);
                    getTwdInfo(zhuyuanhao, strings[0], strings[1], ruyuanshijian);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_nextweek:
                if (null != endDate) {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat after = new SimpleDateFormat("yyyy-MM-dd");
                    if (Common.formatDuringyMd(after.format(currentDate), after.format(Common.getWeekAfterDays(startDate, 6))) > 0) {
                        activityUtils.showToast("暂无下周信息");
                    } else {
                        startDate = Common.getWeekAfterDays(endDate, 1);
                        endDate = Common.getWeekAfter(startDate);
                        String afterTime = after.format(endDate);
                        String beforeTime = after.format(startDate);
                        getTwdInfo(zhuyuanhao, beforeTime, afterTime, ruyuanshijian);
                    }
                } else {
                    activityUtils.showToast("暂无下周信息");
                }

                break;
            case R.id.wenshu_iv_backTop:
                if (czydm.equals(creatorID)) {
                if (tv_content.isEnabled()) {
                    tv_content.setEnabled(false);
                    activityUtils.showToast("此文书已退出编辑模式");
                    iv_updateWS.setVisibility(View.GONE);
                } else {
                    tv_content.setEnabled(true);
                    activityUtils.showToast("此文书已进入编辑模式");
                    iv_updateWS.setVisibility(View.GONE);
                }
                } else {
                    activityUtils.showToast("非本病历创建者，不能编辑");
                }
                break;
            case R.id.wenshu_iv_update:
                activityUtils.showToast("该功能暂时无法使用");
//                if (tv_content.isEnabled()) {
//                    String fileString = tv_content.getText().toString();
//                    submitBlsxData(zhuyuanhao, fileNo, mrCode, czydm, Common.stringToUnicode(fileString));
//                }
                break;

            case R.id.iv_before:
                if (currentIn == 0) {
                    Toast.makeText(context, "已经是最前一张了", Toast.LENGTH_SHORT).show();
                    return;
                }
                //  imageView.setImageBitmap(wenshus.get(currentIn - 1));
                Glide.with(context).load(wenshuUrls.get(currentIn - 1))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
                currentIn = currentIn - 1;
                tv_title.setText(wenshuTitle + "(" + (currentIn + 1) + "/" + wenshuUrls.size() + ")");
                break;
            case R.id.iv_next:
                if (currentIn == wenshuUrls.size() - 1) {
                    Toast.makeText(context, "已经是最后一张了", Toast.LENGTH_SHORT).show();
                    return;
                }
                Glide.with(context).load(wenshuUrls.get(currentIn + 1))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView);
                currentIn = currentIn + 1;
                tv_title.setText(wenshuTitle + "(" + (currentIn + 1) + "/" + wenshuUrls.size() + ")");
                break;
            case R.id.tv_qssj:
                showTimePicker(tv_qssj);
                break;
            case R.id.tv_zzsj:
                showTimePicker(tv_zzsj);
                break;
            case R.id.tv_gzlsqsx:
                getXtjcb(zhuyuanhao, xtrysj);
                break;
        }
    }

    /*获取文书*/
    public void getFileContent(String zyh, String fileno, String creator_id, String mrCode,String wsTitle,String mrClass) {
        this.creatorID = creator_id;
        this.mrCode = mrCode;
        this.fileNo = fileno;
        this.zhuyuanhao = zyh;
        this.wenshuTitle = wsTitle;
        twdLayout.setVisibility(View.GONE);
        xtjcbLayout.setVisibility(View.GONE);
        currentIn = 0;
        iv_before.setVisibility(View.GONE);
        iv_next.setVisibility(View.GONE);

        if (mrClass.equals("AJ")){//护理文书
            imageView.setImageDrawable(null);
            iv_backTop.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            getEmrFileMrInfo(zyh,fileno);
        }else {//病历病程
            iv_backTop.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            getDocFileContent(zyh,fileno);
        }


    }

    private void getEmrFileMrInfo(String zyh, String fileno) {
        if (null != progressDialog) {
            progressDialog.setMessage("正在生成文书...");
            progressDialog.show();
        }
        wenshuUrls.clear();
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("file_no", fileno);
        params.put("czydm", czydm);
        Log.e(TAG, "getEmrFileMrInfo:"+params.toString() );
        String url = Constant.BaseURL + "/MrInfo";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .connTimeOut(20000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        tv_content.setEnabled(false);
                        iv_updateWS.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String emr_doc = jsonObject.getString("bz");
                            String success = jsonObject.getString("success");

                            if (success.equals("true")){
                                if (emr_doc.contains(";")){
                                    String wenshuImageUrlTemp = Constant.BaseURL.split("/")[0]+"/"+Constant.BaseURL.split("/")[1]+"/"+Constant.BaseURL.split("/")[2]+"/"+Constant.BaseURL.split("/")[3]+"/";
                                    String[] split = emr_doc.split(";");
                                    for (int i=0;i<split.length;i++){
                                        wenshuUrls.add(wenshuImageUrlTemp+"EmrFileTemp/"+czydm+"/"+split[i]+".png");


                                    }
                                    if (wenshuUrls.size()>0) {
                                        Log.e("wcx", "onPostExecute: "+wenshuUrls.get(0) );
                                        Glide. with(context)
                                                .load(wenshuUrls.get(0))
                                                .skipMemoryCache(true)
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .into(imageView);
                                    }
                                }
                                tv_title.setText(wenshuTitle);
                                //如果有多個文書
                                if (wenshuUrls.size()>1) {
                                    iv_before.setVisibility(View.VISIBLE);
                                    iv_next.setVisibility(View.VISIBLE);
                                    tv_title.setText(wenshuTitle+"("+1+"/"+wenshuUrls.size()+")");
                                }


                            }else {
                                activityUtils.showToast("文书接口异常");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getDocFileContent(String zyh, String fileno) {

        if (null != progressDialog) {
            progressDialog.show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("fileno", fileno); 
        String url = Constant.BaseURL + "/GetFileContent";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        tv_content.setEnabled(false);
                        iv_updateWS.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        if (bottomFlag) {
                            scrollView.fullScroll(View.FOCUS_UP);
                        }
                        downPosition = 0;
                        upPosition = 0;
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String emr_doc = jsonObject.getString("emr_doc");
                            int i = emr_doc.indexOf("\n");
                            String title = emr_doc.substring(0, i);
                            tv_title.setText(title);
                            String content = emr_doc.substring(i + 2);
                            tv_content.setText(content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void clearText() {
        tv_title.setText("");
        tv_content.setText("");
    }

    /*获取体温单信息*/
    public void getTwdInfo(String zyh, final String qssj, final String zzsj, String rysj) {
        this.qssj = qssj;
        this.ruyuanshijian = rysj;
        final List<String> dateList = initTableHeadList(qssj);
        final List<String> zytsList = setRYTS(dateList);
        zhuyuanhao = zyh;
        xtjcbLayout.setVisibility(View.GONE);
        twdLayout.setVisibility(View.VISIBLE);
        iv_backTop.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        if (null != progressDialog) {
            progressDialog.setMessage("正在获取体温单...");
            progressDialog.show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("qssj", qssj);
        params.put("zzsj", zzsj);
        String url = Constant.BaseURL + "/GetTwdInfo";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String s = qssj + "——" + zzsj;
                        twd_tv_title.setText(s);
                        twdInforList.clear();
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        twdInforList = gson.fromJson(response, new TypeToken<List<TWDInfor>>() {
                        }.getType());
                        setTwdData(dateList, zytsList);
                        sortUp(twdInforList);
                    }
                });
        Log.e(TAG, "getXtjcb: twd=="+twdLayout.getVisibility()+"xtd=="+xtjcbLayout.getVisibility() );
    }

    /*制作体温单*/
    private void setTwdData(List<String> strings, List<String> list) {
        List<TWDInfor> impulseList = new ArrayList<>();
        List<TWDInfor> bodyTempeList = new ArrayList<>();
        List<TWDInfor> breathList = new ArrayList<>();
        for (int i = 0; i < twdInforList.size(); i++) {
            TWDInfor twdInfor = twdInforList.get(i);
            String tzmc = twdInfor.getTZMC();
            switch (tzmc) {
                case "体温":
                    bodyTempeList.add(twdInfor);
                    break;
                case "脉搏":
                    impulseList.add(twdInfor);
                    break;
                case "呼吸":
                    breathList.add(twdInfor);
                    break;
            }
        }
        ArrayList<Integer> maibo = new ArrayList<>();
        ArrayList<Integer> tiwen = new ArrayList<>();
        List<Integer> maiboSpotPosition = new ArrayList<>();
        List<Integer> tiwenSpotPosition = new ArrayList<>();
        List<Integer> breathPosition = new ArrayList<>();
        List<String> tiwenBZ = new ArrayList<>();
        List<String> maiboBZ = new ArrayList<>();
        List<String> breathBZ = new ArrayList<>();
        for (int i = 0; i < impulseList.size(); i++) {
            TWDInfor twdInfor = impulseList.get(i);
            String value_t = twdInfor.getVALUE_T();
            String xssj = twdInfor.getXSSJ();
            String sjd = twdInfor.getSJD();
            maiboBZ.add(value_t);
            int maiboValue = Integer.parseInt(value_t);
            int duration = Common.formatDuring(qssj, xssj);
            int dataPosition = getDataPosition(duration, sjd);
            maiboSpotPosition.add(dataPosition);
            maibo.add(maiboValue);
        }
        for (int i = 0; i < bodyTempeList.size(); i++) {
            TWDInfor twdInfor = bodyTempeList.get(i);
            String value_t = twdInfor.getVALUE_T();
            String xssj = twdInfor.getXSSJ();
            String sjd = twdInfor.getSJD();
            tiwenBZ.add(value_t);
            int duration = Common.formatDuring(qssj, xssj);
            int dataPosition = getDataPosition(duration, sjd);
            tiwenSpotPosition.add(dataPosition);
            float v = (float) ((Float.parseFloat(value_t) - 34.0) * 10);
            int round = Math.round(v);
            tiwen.add(round);
        }
        for (int i = 0; i < breathList.size(); i++) {
            TWDInfor twdInfor = breathList.get(i);
            String value_t = twdInfor.getVALUE_T();
            String xssj = twdInfor.getXSSJ();
            String sjd = twdInfor.getSJD();
            breathBZ.add(value_t);
            int duration = Common.formatDuring(qssj, xssj);
            int dataPosition = getDataPosition(duration, sjd);
            breathPosition.add(dataPosition);
        }
        bodyTemperatureChart.setData(maibo, maiboSpotPosition, maiboBZ, tiwen, tiwenSpotPosition, tiwenBZ, strings, list);
        bodyTemperatureChart.setBreath(breathBZ, breathPosition);
    }

    /*处理体温单数据*/
    private int getDataPosition(int duration, String sjd) {
        int a = 0;
        switch (sjd) {
            case "2":
                a = 0;
                break;
            case "6":
                a = 1;
                break;
            case "10":
                a = 2;
                break;
            case "14":
                a = 3;
                break;
            case "18":
                a = 4;
                break;
            case "22":
                a = 5;
                break;
        }
        return 6 * duration + a;
    }

    /*处理体温单时间标题*/
    private List<String> initTableHeadList(String qssj) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        try {
            Date parse = format0.parse(qssj);
            for (int i = 6; i > -1; i--) {
                Date weekAfterDays = Common.getWeekAfterDays(parse, i);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String days = format.format(weekAfterDays);
                dates.add(days);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /*设置入院天数*/
    private List<String> setRYTS(List<String> dates) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String rysj = format.format(new Date(ruyuanshijian));
        List<String> rytsList = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            String s = dates.get(i);
            int duration = Common.formatDuringyMd(rysj, s) + 1;
            if (duration < 1) {
                rytsList.add("");
            } else {
                rytsList.add(String.valueOf(duration));
            }
        }
        return rytsList;
    }

    /*体温单数据分类*/
    private void sortUp(List<TWDInfor> list) {
        List<TWDInfor> bi = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<List<TWDInfor>> groupList = new ArrayList<>();
        List<List<Integer>> positions = new ArrayList<>();
        for (TWDInfor bill : list) {
            boolean state = false;
            for (TWDInfor bills : bi) {
                if (bills.getTZMC().equals(bill.getTZMC())) {
                    state = true;
                }
            }
            if (!state) {
                if (!bill.getTZMC().equals("体温") && !bill.getTZMC().equals("脉搏") && !bill.getTZMC().equals("呼吸")) {
                    titles.add(bill.getTZMC());
                }
                bi.add(bill);
            }
        }
        for (int i = 0; i < titles.size(); i++) {
            List<TWDInfor> inforList = new ArrayList<>();
            List<Integer> position = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getTZMC().equals(titles.get(i))) {
                    TWDInfor twdInfor = list.get(j);
                    String xssj = twdInfor.getXSSJ();
                    position.add(Common.formatDuring(qssj, xssj));
                    inforList.add(twdInfor);
                }
            }
            positions.add(position);
            groupList.add(inforList);
        }
        bodyTemperatureChart.setSubTable(titles, groupList, positions);
    }

    /*提交修改过的文书*/
    private void submitBlsxData(String zyh, String fileNo, String mrCode, String ysdm, String FileDoc) {
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("fileNo", fileNo);
        params.put("mrCode", mrCode);
        params.put("ysdm", ysdm);
        params.put("FileDoc", FileDoc);
        String url = Constant.BaseURL + "/SubmitBlsxData";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String response1 = jsonObject.getString("response");
                            String bz = jsonObject.getString("bz");
                            activityUtils.showLongToast(bz);
                            if (response1.equals("true")) {
                                tv_content.setEnabled(false);
                                iv_updateWS.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void getXtjcb(String zhuYuanHao,String xtrysj) {

        //tv_qssj.setText(Common.formatStringDateYDM(xtrysj));
        String stext = tv_qssj.getText().toString();
        if (stext.equals("")){
            tv_qssj.setText(Common.formatStringDateYDM(xtrysj));
        }
        this.xtrysj = xtrysj;
        this.zhuyuanhao = zhuYuanHao;
        twdLayout.setVisibility(View.GONE);
        xtjcbLayout.setVisibility(View.VISIBLE);
        iv_backTop.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        if (null != progressDialog) {
            progressDialog.setMessage("正在获取血糖数据");
            progressDialog.show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zhuYuanHao);
        params.put("qssj", tv_qssj.getText().toString());
        params.put("zzsj", tv_zzsj.getText().toString());
        Log.e(TAG, "getXtjcb: "+params.toString() );
        String url = Constant.BaseURL + "/GetBloodSugarInfo";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        progressDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        xuetangInforList.clear();
                        progressDialog.dismiss();
                        try {
                            Gson gson = new Gson();
                            xuetangInforList = gson.fromJson(response, new TypeToken<List<XueTangBean>>() {
                            }.getType());
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        if (xuetangInforList!=null&&xuetangInforList.size()>0){
                            xueTangAdapter.update(xuetangInforList,xtxtlx);
                        }

                    }
                });
        Log.e(TAG, "getXtjcb: twd=="+twdLayout.getVisibility()+"xtd=="+xtjcbLayout.getVisibility() );
    }

    private void showTimePicker(final TextView textView)  {

        String sinitdate = textView.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateParse = null;
        try {
            dateParse = simpleDateFormat.parse(sinitdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar d = Calendar.getInstance(Locale.CHINA);
        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例

        // 创建一个Date实例
        d.setTime(dateParse);
        // 设置日历的时间，把一个新建Date实例myDate传入
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        //初始化默认日期year, month, day
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            /**
             * 点击确定后，在这个方法中获取年月日
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.setMessage("请选择日期");
        datePickerDialog.show();
    }


}
