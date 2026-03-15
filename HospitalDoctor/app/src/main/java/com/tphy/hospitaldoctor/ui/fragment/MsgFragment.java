package com.tphy.hospitaldoctor.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.ui.activity.MainActivity;
import com.tphy.hospitaldoctor.ui.adapter.AttendAdapter;
import com.tphy.hospitaldoctor.ui.adapter.AudioAdapter;
import com.tphy.hospitaldoctor.ui.adapter.JixiaoAdapter;
import com.tphy.hospitaldoctor.ui.adapter.MessageAdapter;
import com.tphy.hospitaldoctor.ui.bean.AttendInfor;
import com.tphy.hospitaldoctor.ui.bean.AttendenceAdvance;
import com.tphy.hospitaldoctor.ui.bean.AudioInfor;
import com.tphy.hospitaldoctor.ui.bean.JiXiaoBean;
import com.tphy.hospitaldoctor.ui.bean.KeShi;
import com.tphy.hospitaldoctor.ui.bean.MessageInfo;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.FileUtils;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.hospitaldoctor.utils.audio.MediaUtils;
import com.tphy.hospitaldoctor.widget.MyItemDecoration;
import com.tphy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MsgFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    //    @BindView(R.id.msg_swipe)
//    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.msg_recycler)
    RecyclerView recycler;
    @BindView(R.id.message_layout)
    RelativeLayout message_layout;

    private MessageAdapter adapter;
    private List<MessageInfo> messageInfoList;
    private ActivityUtils activityUtils;
    private String czydm;
    private String ydbz;
    /*考勤*/
    private List<AttendInfor> attendInfors;

    /*备忘*/
    @BindView(R.id.beiwang_layout)
    RelativeLayout beiwang_layout;
    @BindView(R.id.bw_recycler)
    RecyclerView bwRecycler;
    @BindView(R.id.bw_swipe)
    SwipeRefreshLayout bwRefresh;
    @BindView(R.id.bw_iv_twbw)
    ImageView iv_twbw;
    @BindView(R.id.bw_iv_yybw)
    ImageView yybw;
    @BindView(R.id.bw_iv_ftjl)
    ImageView iv_ftjl;
    @BindView(R.id.attendLayout)
    RelativeLayout attendLayout;
//    @BindView(R.id.audio_item_state)
//    TextView state;

    /*考勤*/
    @BindView(R.id.attend_iv_gender)
    ImageView mAttendIvGender;
    @BindView(R.id.attend_tv_name)
    TextView mAttendTvName;
    @BindView(R.id.attend_tv_keshi)
    TextView mAttendTvKeshi;
    @BindView(R.id.attend_iv_daka)
    TextView mAttendIvDaka;
    @BindView(R.id.attend_tv_state)
    TextView mAttendTvState;
    @BindView(R.id.attend_iv_last)
    ImageView mAttendIvLast;
    @BindView(R.id.attend_iv_next)
    ImageView mAttendIvNext;
    @BindView(R.id.attend_tv_date)
    TextView mAttendTvDate;
    @BindView(R.id.attend_list)
    RecyclerView mAttendList;

    /*绩效*/
    @BindView(R.id.jixiaoLayout)
    LinearLayout jixiaoLayout;
    @BindView(R.id.jx_recycler)
    RecyclerView jxRecycler;

    private MediaUtils mediaUtils;
    private boolean isCancel;
    private List<AudioInfor> audioInfors;
    private AudioAdapter audioAdapter;
    private String zyh;
    private String yysj;
    private String photoLocation;
    private String noteType = "tt";
    private View audioView;
    private ImageView record;
    private AlertDialog audioDialog;
    private AlertDialog jiluDialog;
    private ImageView iv_photo;
    private PopupWindow popupWindow;
    private DownloadManager downloadManager;
    private long mTaskID;
    private ProgressDialog progressDialog;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus(context);
        }
    };
    private String timestamp;
    private File mTmpFile;
    private int REQUEST_CAMERA = 0;
    private Date mDate;
    private AttendAdapter attendAdapter;
    private List<AttendenceAdvance> attendenceAdvances;
    private List<JiXiaoBean> jiXiaoBeanList;
    private JixiaoAdapter jixiaoAdapter;


//    private List<KeShi> keShiList;
//    private String mKSDM = "";
//    private int keshiCheckedPosition;
//    private BaseFragment[] fragments;



    @Override
    protected int getLayoutID() {
        return R.layout.frag_message;
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        messageInfoList = new ArrayList<>();
        activityUtils = new ActivityUtils(this);
        audioInfors = new ArrayList<>();
        attendInfors = new ArrayList<>();
        attendenceAdvances = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("备忘上传中...");
        progressDialog.setCanceledOnTouchOutside(true);
        jiXiaoBeanList = new ArrayList<>();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new MyItemDecoration(context, MyItemDecoration.VERTICAL_LIST));
        adapter = new MessageAdapter(context, messageInfoList);

        recycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MessageInfo messageInfo = messageInfoList.get(position);
                String msg_url = messageInfo.getMSG_URL();
                @SuppressLint("InflateParams") View webLayout = LayoutInflater.from(context).inflate(R.layout.activity_msg_details, null);
                WebView webView = webLayout.findViewById(R.id.detail_webview);
                webView.loadUrl(msg_url);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog dialog = builder.create();
                if (ydbz.equals("0")) {
                    String msg_id = messageInfo.getMSG_ID();
                    updateMsgStatus(czydm, msg_id);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            onRefresh();
                        }
                    });
                }
                dialog.show();
                dialog.getWindow().setContentView(webLayout);
            }
        });
        initBeiWang();
        initAttendence();
        initJiXiao();
    }

    private void initJiXiao() {
        jixiaoAdapter = new JixiaoAdapter(context, jiXiaoBeanList);
        jxRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        jxRecycler.setAdapter(jixiaoAdapter);
    }

    private void initAttendence() {
        mAttendTvName.setText(czymc);
        mAttendTvKeshi.setText(ksmc);
        mDate = new Date();
        String date = Common.formatDateToYMD(mDate);
        mAttendTvDate.setText(date);
        mAttendList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        attendAdapter = new AttendAdapter(context, attendenceAdvances);
        mAttendList.setAdapter(attendAdapter);

    }
    /*初始化备忘录功能*/
    private void initBeiWang() {
        bwRefresh.setOnRefreshListener(this);
        bwRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        audioAdapter = new AudioAdapter(context, audioInfors);
        bwRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bwRecycler.addItemDecoration(new MyItemDecoration(context, MyItemDecoration.VERTICAL_LIST));
        bwRecycler.setAdapter(audioAdapter);
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                AudioInfor audioInfor = audioInfors.get(position);
                String yylx = audioInfor.getYYLX();
                if (yylx.equals("yy")) {
                    String yydz = audioInfor.getYYDZ();
                    if (null == yydz) return;
                    String lrsj = audioInfor.getLRSJ();
                    timestamp = Common.timeToTimestamp(lrsj);
                    String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + timestamp + ".mp3";
                    if (Common.fileIsExists(filePath)) {
                        playAudio(filePath);
                    } else {
                        Uri content_url = Uri.parse(yydz);
                        DownloadManager.Request request = new DownloadManager.Request(content_url);
                        request.setAllowedOverRoaming(false);/*漫游网络是否可以下载*/
                        //        设置文件类型，可以在下载结束后自动打开该文件
                        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(yydz));
                        request.setMimeType(mimeString);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setVisibleInDownloadsUi(true);
//                        new MainActivity().updatePatientList(position);
                        request.setDestinationInExternalPublicDir("/download/", timestamp + ".mp3");
                        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        mTaskID = downloadManager.enqueue(request);
                        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    }
                } else if (yylx.equals("tt")) {
                    String bz = audioInfor.getBZ();
                    if (null != bz) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("备忘详情");
                        builder.setMessage(bz);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                } else if (yylx.equals("tp")) {
                    String yydz = audioInfor.getYYDZ();
                    if (null != yydz) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(yydz);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }
            }
        });
        activityUtils = new ActivityUtils(this);
//        initDialogView();
        initRecorder();

        audioAdapter.setOnItemDeleteClickListener(new AudioAdapter.OnItemDeleteClickListener() {
            @Override
            public void onClick1(View view, int position) {
                AudioInfor audioInfor = audioInfors.get(position);
                String state = "2";
                finishBeiWangRecord(audioInfor.getLSH(),state);
            }
        });
        audioAdapter.setOnItemFinishClickListener(new AudioAdapter.OnItemFinishClickListener() {
            @Override
            public void onClick2(View view, int position) {
                AudioInfor audioInfor = audioInfors.get(position);
                String state ="1";
                finishBeiWangRecord(audioInfor.getLSH(),state);
            }
        });
    }


    /*录音计时器*/
    private void initRecorder() {
        mediaUtils = new MediaUtils(getHoldingActivity());
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        @SuppressLint("SdCardPath") File file = new File("/sdcard/移动医生/audio/");
        file.mkdirs();
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        /*UUID.randomUUID()*/
        mediaUtils.setTargetName(System.currentTimeMillis() + ".m4a");
    }

    @Override
    public void onRefresh() {
//        Log.e("WQ", czydm + "  " + ydbz);
        getYyyzList(czydm, zyh, yysj);
    }

    /*获取消息列表*/
    public void getMessageList(final String czydm, final String ydbz) {
        showMsgLayout(true);
        this.czydm = czydm;
        this.ydbz = ydbz;
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("ydbz", ydbz);
        String url = Constant.BaseURL + "/GetMessageList";
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
                        messageInfoList.clear();
                        try {
                            Gson gson = new Gson();
                            messageInfoList = gson.fromJson(response, new TypeToken<List<MessageInfo>>() {
                            }.getType());
                            List<MessageInfo> list = new ArrayList<>();
                            if (ydbz.equals("1")) {
                                for (int i = 0; i < messageInfoList.size(); i++) {
                                    MessageInfo messageInfo = messageInfoList.get(i);
                                    String msg_yydr = messageInfo.getMSG_YYDR();
                                    if (msg_yydr.contains(czydm)) {
                                        list.add(messageInfo);
                                    }
                                }
                            } else {
                                list = messageInfoList;
                            }
                            if (null != adapter) {
                                adapter.refreshData(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*更新消息状态*/
    public void updateMsgStatus(String czydm, String msgid) {
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("msgid", msgid);
        String url = Constant.BaseURL + "/UpdateMsgStatus";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("WQ", "标记已读成功");

                    }
                });
    }

    @OnClick({R.id.bw_iv_twbw, R.id.bw_iv_yybw, R.id.bw_iv_ftjl, R.id.attend_iv_daka, R.id.attend_iv_last, R.id.attend_iv_next})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bw_iv_twbw:
                @SuppressLint("InflateParams") View tuwenView = LayoutInflater.from(context).inflate(R.layout.bw_tuwen_layout, null);
                final EditText et_topic1 = tuwenView.findViewById(R.id.note_title);
                final EditText et_content = tuwenView.findViewById(R.id.note_content);
                ImageView iv_addphoto = tuwenView.findViewById(R.id.bw_iv_addPhoto);
                iv_photo = tuwenView.findViewById(R.id.note_iv_phote);
                iv_addphoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCameraAction();
                    }
                });
                ImageView iv_save = tuwenView.findViewById(R.id.bw_iv_save);
                iv_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消了stringToUnicode
                        String title = et_topic1.getText().toString();
                        String content = et_content.getText().toString();
                        if (title.equals("")) {
                            activityUtils.showToast("请填写主题");
                        } else if (content.equals("")) {
                            activityUtils.showToast("请填写内容");
                        } else {
                            String time = getTime();
                            switch (noteType) {
                                case "tt":
                                    recorderUpload(czydm, zyh, time + title, content, "", "tt");
                                    break;
                                case "tp":
                                    recorderUpload(czydm, zyh, time + title, content, Common.imageToBase64(photoLocation), "tp");
                                    break;
                            }
                        }
                        MainActivity activity = (MainActivity) getHoldingActivity();
                        //如果改变了备忘,设置备忘为true
                        activity.setChangeBeiWang(true);
                    }
                });/*(new_msg.getVisibility()!=View.VISIBLE)*/
                popupWindow = new PopupWindow(tuwenView, (int) (260 * Common.density), (int) (460 * Common.density));
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAtLocation(tuwenView, Gravity.END, 0, 400);

                break;
            case R.id.bw_iv_ftjl:
                if (null == audioDialog) {
                    audioDialog = new AlertDialog.Builder(context).create();
                    audioDialog.show();
                    audioDialog.getWindow().setContentView(audioView);
//                    WindowManager.LayoutParams attributes = audioDialog.getWindow().getAttributes();
//                    attributes.width = 150;
//                    attributes.height = 292;
//                    audioDialog.getWindow().setAttributes(attributes);
                    audioDialog.setCanceledOnTouchOutside(false);
                } else {
                    audioDialog.show();
                }
                break;
            case R.id.bw_iv_yybw:
                @SuppressLint("InflateParams") View audioViewOrange = LayoutInflater.from(context).inflate(R.layout.dialog_audio_orange, null);
                final ImageView iv_record = audioViewOrange.findViewById(R.id.audio_iv_record);
                final ImageView iv_wave = audioViewOrange.findViewById(R.id.audio_wave);
                final ImageView iv_close = audioViewOrange.findViewById(R.id.audio_iv_close);
                final TextView tv_confirm = audioViewOrange.findViewById(R.id.audio_tv_confirm);
                final Chronometer chronometer = audioViewOrange.findViewById(R.id.time_display);
                final TextView tv_topic = audioViewOrange.findViewById(R.id.audio_tv_topic);
                final EditText et_topic = audioViewOrange.findViewById(R.id.audio_et_topic);
                final View view = audioViewOrange.findViewById(R.id.audio_view);
                final View view2 = audioViewOrange.findViewById(R.id.audio_v);
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jiluDialog.dismiss();
                    }
                });
                final ImageView iv_confirm = audioViewOrange.findViewById(R.id.audio_iv_confirm);
                iv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String titile = et_topic.getText().toString();
                        if (titile.equals("")) {
                            activityUtils.showToast("请添加主题");
                        } else {
                            String pathRaw = mediaUtils.getTargetFilePath();
                            File file = new File(pathRaw);
                            String yuyin = Common.imageToBase64(file.getPath());
                            String time = getTime();
                            recorderUpload(czydm, zyh, time + titile, "", yuyin, "yy");
                        }
                    }
                });
                final boolean[] isOn = {false};
                iv_record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isOn[0]) {
                            isOn[0] = true;
                            iv_record.setSelected(true);
                            mediaUtils.setTargetName(System.currentTimeMillis() + ".m4a");
                            chronometer.setBase(SystemClock.elapsedRealtime());
                            chronometer.setFormat("%S");
                            chronometer.start();
                            mediaUtils.record();
                        } else {
                            mediaUtils.stopRecordSave();
                            chronometer.stop();
                            iv_wave.setVisibility(View.GONE);
                            tv_confirm.setVisibility(View.GONE);
                            view2.setVisibility(View.VISIBLE);
                            tv_topic.setVisibility(View.VISIBLE);
                            String ymd = getYMD();
                            et_topic.setVisibility(View.VISIBLE);
                            et_topic.setText(ymd);
                            view.setVisibility(View.VISIBLE);
                            iv_confirm.setVisibility(View.VISIBLE);
                            iv_record.setVisibility(View.GONE);
                        }
                    }
                });
                jiluDialog = new AlertDialog.Builder(context).create();
                jiluDialog.setView(new EditText(context));
                jiluDialog.show();
                jiluDialog.getWindow().setContentView(audioViewOrange);
//                    WindowManager.LayoutParams attributes = audioDialog.getWindow().getAttributes();
//                    attributes.width = 150;
//                    attributes.height = 292;
//                    audioDialog.getWindow().setAttributes(attributes);
                jiluDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.bw_iv_save:

                break;
            case R.id.attend_iv_daka:
                Date dateAfterDays = Common.getDateAfterDays(new Date(System.currentTimeMillis()), -1);
                Date afterDate = Common.getDateAfterDays(new Date(System.currentTimeMillis()), 1);
                getAttendsList(czydm, Common.formatDateToYMD(dateAfterDays), Common.formatDateToYMD(afterDate), true);
                break;
            case R.id.attend_iv_last:
                /*mDate*/
                Date before = Common.getDateAfterDays(mDate, -1);
                mDate = before;
                String s = Common.formatDateToYMD(mDate);
                mAttendTvDate.setText(s);
                Date lastBefore = Common.getDateAfterDays(mDate, -1);
                Date lastAfter = Common.getDateAfterDays(mDate, 1);
                getAttendsList(czydm, Common.formatDateToYMD(lastBefore), Common.formatDateToYMD(lastAfter), false);
                break;
            case R.id.attend_iv_next:
                Date after = Common.getDateAfterDays(mDate, 1);
                mDate = after;
                String s1 = Common.formatDateToYMD(mDate);
                mAttendTvDate.setText(s1);
                Date nextBefore = Common.getDateAfterDays(mDate, -1);
                Date nextAfter = Common.getDateAfterDays(mDate, 1);
                getAttendsList(czydm, Common.formatDateToYMD(nextBefore), Common.formatDateToYMD(nextAfter), false);
                break;
        }
    }

    //备忘列表
    public void getYyyzList(String czydm, String zyh, String yysj) {
        showMsgLayout(false);
        attendLayout.setVisibility(View.GONE);
        beiwang_layout.setVisibility(View.VISIBLE);
        if (!bwRefresh.isRefreshing()) {
            bwRefresh.setRefreshing(true);
        }
//        Log.e("WQ", "获取备忘列表" + "操作员代码" + czydm + "  住院号==" + zyh + "  时间==" + yysj);
        this.czydm = czydm;
        this.zyh = zyh;
        this.yysj = yysj;
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("czydm", czydm);
        params.put("YYSJ", yysj);
        String url = Constant.BaseURL + "/GetYyyzList";
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
                        if (bwRefresh.isRefreshing()) {
                            bwRefresh.setRefreshing(false);
                        }
                        try {
                            audioInfors.clear();
                            Gson gson = new Gson();
                            audioInfors = gson.fromJson(response, new TypeToken<List<AudioInfor>>() {
                            }.getType());

                            if (null != audioAdapter) {
                                audioAdapter.refreshData(audioInfors);
                            }
                        } catch (JsonSyntaxException e) {
                            activityUtils.showToast("数据解析异常");
                            e.printStackTrace();
                        }
                    }
                });
    }

    //保存备忘录
    private void recorderUpload(final String czydm, final String zyh, String filename, String yymc, String yynr, final String yylx) {
//        Log.e("WQ", "上传的参数==" + czydm + " " + zyh + " " + filename + " " + yymc + " " + yynr + " " + yylx);
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("zyh", zyh);
        params.put("filename", filename);
        params.put("yymc", yymc);
        params.put("yynr", yynr);
        params.put("yylx", yylx);
//        Log.e("WQ", "参数是==" + params.toString());
        String url = Constant.BaseURL + "/RecorderUpload";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        activityUtils.showToast(Common.getException(e));
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String response1 = jsonObject.getString("response");
                            String bz = jsonObject.getString("bz");
                            if (response1.equals("true")) {
                                Toast.makeText(context, bz, Toast.LENGTH_SHORT).show();
                                getYyyzList(czydm, zyh, yysj);
                                if (null != audioDialog && audioDialog.isShowing()) {
                                    audioDialog.dismiss();
                                }
                                if (null != jiluDialog && jiluDialog.isShowing()) {
                                    jiluDialog.dismiss();
                                }
                                if (null != popupWindow && popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                }
                            } else {
                                Toast.makeText(context, bz, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                noteType = "tp";
                Drawable drawable = Drawable.createFromPath(photoLocation);
                if (null != drawable) {
                    iv_photo.setBackgroundDrawable(drawable);
                }
            } else {
                activityUtils.showToast("拍照未成功！");
            }
        }
    }

    private String getTime() {
        long l = System.currentTimeMillis();
        return String.valueOf(l);
    }

    private String getFomattedTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private String getYMD() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private void showMsgLayout(boolean show) {
        if (show) {
            message_layout.setVisibility(View.VISIBLE);
            beiwang_layout.setVisibility(View.GONE);
            attendLayout.setVisibility(View.GONE);
            jixiaoLayout.setVisibility(View.GONE);
        } else {
            beiwang_layout.setVisibility(View.VISIBLE);
            jixiaoLayout.setVisibility(View.GONE);
            message_layout.setVisibility(View.GONE);
            attendLayout.setVisibility(View.GONE);
        }
    }

    public void showAttendLayout() {
        attendLayout.setVisibility(View.VISIBLE);
    }

    private void attendenceSubmit(String ysdm, String kqsj, String swbz, String xwbz, String wsbz) {
        Map<String, String> params = new HashMap<>();
        params.put("ysdm", ysdm);
        params.put("kqsj", kqsj);
        params.put("swbz", swbz);
        params.put("xwbz", xwbz);
        params.put("wsbz", wsbz);
        String url = Constant.BaseURL + "/AttendenceSubmit";
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
                            String response1 = jsonObject.getString("response");
                            String bz = jsonObject.getString("bz");
                            if (response1.equals("true")) {
                                activityUtils.showToast(bz);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 考勤记录
     * @param czydm
     * @param qssj
     * @param zzsj
     * @param daka
     */


    public void getAttendsList(String czydm, String qssj, String zzsj, final boolean daka) {
//        Log.e("WQ", "czydm==" + czydm + "  其实渐渐==" + qssj + " 截止时间==" + zzsj);
        attendenceAdvances.clear();
        beiwang_layout.setVisibility(View.GONE);
        jixiaoLayout.setVisibility(View.GONE);
        message_layout.setVisibility(View.GONE);
        attendLayout.setVisibility(View.VISIBLE);
        attendInfors.clear();
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("qssj", qssj);
        params.put("zzsj", zzsj);
        String url = Constant.BaseURL + "/GetAttendsList";
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
//                        Log.e("WQ", "daxiao==" + response);
                        Gson gson = new Gson();
                        attendInfors = gson.fromJson(response, new TypeToken<List<AttendInfor>>() {
                        }.getType());
                        if (daka) {
                            boolean enable = true;
                            MainActivity activity = (MainActivity) getHoldingActivity();
                            for (int i = 0; i < attendInfors.size(); i++) {
                                AttendInfor attendInfor = attendInfors.get(i);
                                String status = attendInfor.getSTATUS();
                                if (status.equals("上班")) {
                                    enable = false;
                                }
                            }
                            activity.initDakaDialog(enable);
                        } else {
                            boolean mStatus = false;
                            String xms = "";
                            for (int i = 0; i < attendInfors.size(); i++) {
                                AttendInfor attendInfor = attendInfors.get(i);
                                String status = attendInfor.getSTATUS();
                                String xm = attendInfor.getXM();
                                if (status.equals("上班")) {
                                    mStatus = true;
                                    xms = xm + " ";
                                }
                            }
                            if (mStatus) {
                                mAttendTvState.setText("今日已签到,班次：" + xms);
                            } else {
                                mAttendTvState.setText("今日未签到");
                            }
                            if (!attendInfors.isEmpty()) {
                                String czsj = attendInfors.get(0).getCZSJ();
                                String ysdm = attendInfors.get(0).getYSDM();
                                String swxm = "", swStatus = "", xwxm = "", xwStatus = "", wsxm = "", wsStatus = "";
                                for (int i = 0; i < attendInfors.size(); i++) {
                                    AttendInfor attendInfor = attendInfors.get(i);
                                    String xm = attendInfor.getXM();
                                    String status = attendInfor.getSTATUS();
                                    if (xm.equals("上午")) {
                                        swxm = xm;
                                        swStatus = status;
                                    } else if (xm.equals("下午")) {
                                        xwxm = xm;
                                        xwStatus = status;
                                    } else if (xm.equals("夜班")) {
                                        wsxm = xm;
                                        wsStatus = status;
                                    }
                                }
                                AttendenceAdvance advance = new AttendenceAdvance(ksdm, ysdm, swxm, swStatus, xwxm, xwStatus, wsxm, wsStatus, czsj);
                                attendenceAdvances.add(advance);
                            }
                            attendAdapter.refreshData(attendenceAdvances);
                        }
                    }
                });
    }


    private void checkDownloadStatus(Context context) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskID);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.e("WQ", ">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.e("WQ", ">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.e("WQ", ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    activityUtils.showToast("下载语音完成");
                    //下载完成安装apk                                                                         /*"hospitalDoctor"+*/
                    String downlodaPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + timestamp + ".mp3";
                    playAudio(downlodaPath);
                    break;
                case DownloadManager.STATUS_FAILED:
                    activityUtils.showToast("下载语音失败");
                    break;
            }
        }
    }

    public void playAudio(String audioPath) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();//新建一个的实例
            mediaPlayer.setDataSource(audioPath);//设置要播放文件的路径
            mediaPlayer.prepare();//播放 准备完成，开始播放前要调用
            mediaPlayer.start();//播放
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open camera
     */
    private void showCameraAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            try {
                mTmpFile = FileUtils.createTmpFile(getActivity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mTmpFile != null && mTmpFile.exists()) {
                /*获取当前系统的android版本号*/
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion < 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
                    Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    photoLocation = Common.getRealPathFromUri(context, uri);
//                    Log.e("currentapiVersion", "uri)====>" + uri + "\n" + "realUri==" + realPathFromUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
            } else {
                Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "bbb", Toast.LENGTH_SHORT).show();
        }
    }

    /*获取当月绩效指标列表*/
    public void getJxzbList(String czydm) {
        message_layout.setVisibility(View.GONE);
        beiwang_layout.setVisibility(View.GONE);
        attendLayout.setVisibility(View.GONE);
        jixiaoLayout.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        String url = Constant.BaseURL + "/GetJxzbList";
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
                        jiXiaoBeanList.clear();
                        try {
                            Gson gson = new Gson();
                            jiXiaoBeanList = gson.fromJson(response, new TypeToken<List<JiXiaoBean>>() {
                            }.getType());
                            jixiaoAdapter.updateData(jiXiaoBeanList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void finishBeiWangRecord(String lsh,String state){
        //逻辑删除和改变状态
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在执行完成操作...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String lshjson = new String("[{\"lsh\":\""
                +lsh
                +"\",\"state\":\""
                + state + "\"}]");

        //String json = "[{\"lsh\":\""+lsh+"\",\"state\":\""+state+"\"}]";
        Log.e(TAG, "finishBeiWangRecord: "+lshjson );
        Map<String, String> params = new HashMap<>();
        params.put("json", lshjson);
        String url = Constant.BaseURL + "/StateRecorder";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    // [{"lsh":"0000000046","state":"2"}]
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            String response1 = jsonObject.getString("response");
                            String bz = jsonObject.getString("bz");
                            if (response1.equals("true")) {
                                Toast.makeText(context, bz, Toast.LENGTH_SHORT).show();
                                getYyyzList(czydm, zyh, yysj);

                                if (null != audioDialog && audioDialog.isShowing()) {
                                    audioDialog.dismiss();
                                }
                                if (null != jiluDialog && jiluDialog.isShowing()) {
                                    jiluDialog.dismiss();
                                }
                                if (null != popupWindow && popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                }
                                MainActivity activity = (MainActivity) getHoldingActivity();
                                //如果改变了备忘,设置备忘为true
                                activity.setChangeBeiWang(true);
                            } else {
                                Toast.makeText(context, bz, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
//    private void deleteBeiWangRecord(String lsh) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("正在执行删除操作...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        String lshjson = new String("[{\"lsh\":\""+lsh+"\"}]");
//        Log.e(TAG, "deleteBeiWangRecord: "+lshjson );
//        Map<String, String> params = new HashMap<>();
//        params.put("json", lshjson);
//        String url = Constant.BaseURL + "/DeleteRecorder";
//        OkHttpUtils.post()
//                .url(url)
//                .params(params)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
//                        activityUtils.showToast(Common.getException(e));
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            if (null!=progressDialog&&progressDialog.isShowing()) progressDialog.dismiss();
//                            JSONArray array = new JSONArray(response);
//                            JSONObject jsonObject = array.getJSONObject(0);
//                            String response1 = jsonObject.getString("response");
//                            String bz = jsonObject.getString("bz");
//                            if (response1.equals("true")) {
//                                Toast.makeText(context, bz, Toast.LENGTH_SHORT).show();
//                                getYyyzList(czydm, zyh, yysj);
//                                if (null != audioDialog && audioDialog.isShowing()) {
//                                    audioDialog.dismiss();
//                                }
//                                if (null != jiluDialog && jiluDialog.isShowing()) {
//                                    jiluDialog.dismiss();
//                                }
//                                if (null != popupWindow && popupWindow.isShowing()) {
//                                    popupWindow.dismiss();
//                                }
//                            } else {
//                                Toast.makeText(context, bz, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }

}
