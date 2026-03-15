package com.tphy.hospitaldoctor.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.ui.adapter.AudioAdapter;
import com.tphy.hospitaldoctor.ui.bean.AudioInfor;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.hospitaldoctor.utils.audio.MediaUtils;
import com.tphy.hospitaldoctor.widget.MyItemDecoration;
import com.tphy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 废弃不在使用
 */
public class BeiWangFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

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

    private MediaUtils mediaUtils;
    private boolean isCancel;
    private List<AudioInfor> audioInfors;
    private AudioAdapter audioAdapter;
    private String czydm;
    private String zyh;
    private String yysj;
    private ActivityUtils activityUtils;
    private String photoLocation;
    private String noteType = "tt";
    private View audioView;
    private ImageView dismiss;
    private ImageView record;
    private AlertDialog audioDialog;
    private AlertDialog jiluDialog;
    private Chronometer chronometer;
    private ImageView iv_photo;
    private PopupWindow popupWindow;
    //    private View audioView;
//    private AlertDialog audioDialog;

    @Override
    protected int getLayoutID() {
        return R.layout.frag_bw;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        activityUtils = new ActivityUtils(this);
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
                String yymc = audioInfor.getYYMC();
                if (yylx.equals("yy")) {
                    String yydz = audioInfor.getYYDZ();
                    if (null != yydz) {
                        Uri content_url = Uri.parse(yydz);
                        DownloadManager.Request request = new DownloadManager.Request(content_url);
                        request.setAllowedOverRoaming(false);/*漫游网络是否可以下载*/
                        //        设置文件类型，可以在下载结束后自动打开该文件
                        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(yydz));
                        request.setMimeType(mimeString);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setVisibleInDownloadsUi(true);

                        request.setDestinationInExternalPublicDir("/download/", yymc + ".m4a");
                        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
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
        initDialogView();
        initRecorder();
    }
    private void initDialogView() {
        audioView = LayoutInflater.from(context).inflate(R.layout.dialog_audio_layout, null);
        dismiss = audioView.findViewById(R.id.audio_tv_dismiss);
//        TextView confirm = audioView.findViewById(R.id.audio_tv_confirm);
        chronometer = audioView.findViewById(R.id.time_display);
        chronometer.setOnChronometerTickListener(tickListener);
        record = audioView.findViewById(R.id.audio_iv_record);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioDialog.dismiss();
            }
        });
    }

    private void initRecorder() {
        mediaUtils = new MediaUtils(getHoldingActivity());
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        File file = new File("/sdcard/移动医生/audio/");
        file.mkdirs();
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        /*UUID.randomUUID()*/
        mediaUtils.setTargetName(System.currentTimeMillis() + ".m4a");
        record.setOnTouchListener(touchListener);

    }

    @OnClick({R.id.bw_iv_twbw, R.id.bw_iv_yybw, R.id.bw_iv_ftjl})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bw_iv_twbw:
//                luru_tw.setVisibility(View.VISIBLE);
//                Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_up);
//                luru_tw.startAnimation(animation);

                View tuwenView = LayoutInflater.from(context).inflate(R.layout.bw_tuwen_layout, null);
                final EditText et_topic1 = tuwenView.findViewById(R.id.note_title);
                final EditText et_content = tuwenView.findViewById(R.id.note_content);
                ImageView iv_addphoto = tuwenView.findViewById(R.id.bw_iv_addPhoto);
                iv_photo = tuwenView.findViewById(R.id.note_iv_phote);
                iv_addphoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
                });
                ImageView iv_save = tuwenView.findViewById(R.id.bw_iv_save);
                iv_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = Common.stringToUnicode(et_topic1.getText().toString());
                        String content = Common.stringToUnicode(et_content.getText().toString());
                        if (title.equals("")) {
                            activityUtils.showToast("请填写主题");
                        } else if (content.equals("")) {
                            activityUtils.showToast("请填写内容");
                        } else {
                            String time = Common.stringToUnicode(Common.getTime());
//                    String fomattedTime = getFomattedTime();
                            switch (noteType) {
                                case "tt":
                                    recorderUpload(czydm, zyh, time + title, content, "", "tt");
                                    break;
                                case "tp":
                                    recorderUpload(czydm, zyh, time + title, content, Common.imageToBase64(photoLocation), "tp");
                                    break;
                            }
                        }
                    }
                });
                popupWindow = new PopupWindow(tuwenView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAtLocation(tuwenView, Gravity.RIGHT, 0, 400);
                break;
            case R.id.bw_iv_yybw:
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
                //访谈记录
            case R.id.bw_iv_ftjl:
//                if (null == jiluDialog) {
                View audioViewOrange = LayoutInflater.from(context).inflate(R.layout.dialog_audio_orange, null);
                final ImageView iv_record = audioViewOrange.findViewById(R.id.audio_iv_record);
                final ImageView iv_wave = audioViewOrange.findViewById(R.id.audio_wave);
                final ImageView iv_close = audioViewOrange.findViewById(R.id.audio_iv_close);
                final TextView tv_confirm = audioViewOrange.findViewById(R.id.audio_tv_confirm);
                final Chronometer chronometer = audioViewOrange.findViewById(R.id.time_display);
                final TextView tv_topic = audioViewOrange.findViewById(R.id.audio_tv_topic);
                final EditText et_topic = audioViewOrange.findViewById(R.id.audio_et_topic);
                final View view = audioViewOrange.findViewById(R.id.audio_view);
                final View view2 = audioViewOrange.findViewById(R.id.audio_v);
//                final ImageView iv_dismiss = audioViewOrange.findViewById(R.id.audio_iv_dismiss);
//                iv_dismiss.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        jiluDialog.dismiss();
//                    }
//                });
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
//                                Log.e("WQ", "哈哈哈==" + pathRaw);
                            File file = new File(pathRaw);
                            String yuyin = Common.imageToBase64(file.getPath());
                            String time = Common.getTime();
                            recorderUpload(czydm, zyh, Common.stringToUnicode(time + titile), "", yuyin, "yy");
                        }
                    }
                });
                final boolean[] isOn = {false};
                iv_record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isOn[0]) {
                            isOn[0] = true;
//                                Log.e("WQ", "开始");
//                            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_stop_orange);
//                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                            iv_record.setBackgroundDrawable(drawable);
                            iv_record.setSelected(true);
                            mediaUtils.setTargetName(System.currentTimeMillis() + ".m4a");
//                                Log.e("WQ", "哈哈哈==" + System.currentTimeMillis() + ".m4a");
//                                startAnim(true);
                            chronometer.setBase(SystemClock.elapsedRealtime());
                            chronometer.setFormat("%S");
                            chronometer.start();
                            mediaUtils.record();
                        } else {
//                                Log.e("WQ", "停止");
                            mediaUtils.stopRecordSave();
//                                stopAnim();
                            chronometer.stop();
                            iv_wave.setVisibility(View.GONE);
//                            tv_dismiss.setVisibility(View.GONE);
                            tv_confirm.setVisibility(View.GONE);
                            view2.setVisibility(View.VISIBLE);
                            tv_topic.setVisibility(View.VISIBLE);
                            String ymd = getYMD();
                            et_topic.setVisibility(View.VISIBLE);
                            et_topic.setText(ymd);
                            view.setVisibility(View.VISIBLE);
//                            iv_dismiss.setVisibility(View.VISIBLE);
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
        }
    }

    //语音备忘保存事件
    private final View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            float downY = 0;
            int action = event.getAction();
            switch (v.getId()) {
                case R.id.audio_iv_record:
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            mediaUtils.setTargetName(System.currentTimeMillis() + ".m4a");
                            startAnim(true);
                            mediaUtils.record();
                            ret = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            stopAnim();
                            if (isCancel) {
                                isCancel = false;
                                mediaUtils.stopRecordUnSave();
                                Toast.makeText(context, "取消保存", Toast.LENGTH_SHORT).show();
                            } else {
                                int duration = getDuration(chronometer.getText().toString());
                                switch (duration) {
                                    case -1:
                                        break;
                                    case -2:
                                        mediaUtils.stopRecordUnSave();
                                        Toast.makeText(context, "时间太短", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        mediaUtils.stopRecordSave();
                                        String pathRaw = mediaUtils.getTargetFilePath();
                                        File file = new File(pathRaw);
                                        String yuyin = Common.imageToBase64(file.getPath());
//                                        Log.e("WQ", "文件转字符==" + yuyin);
//                                        Log.e("WQ", "操作员代码" + czydm + " 住院号==" + zyh);
                                        String time = Common.getTime();
                                        String fomattedTime = getFomattedTime();
                                        recorderUpload(czydm, zyh, time, fomattedTime + "a", yuyin, "yy");
                                        break;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
//                                moveAnim();
                                isCancel = true;
                            } else {
                                isCancel = false;
                                startAnim(false);
                            }
                            break;
                    }
                    break;
            }
            return ret;
        }
    };
    final Chronometer.OnChronometerTickListener tickListener = new Chronometer.OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() > 60 * 1000) {
                stopAnim();
                mediaUtils.stopRecordSave();
                Toast.makeText(context, "录音超时", Toast.LENGTH_SHORT).show();
                String path = mediaUtils.getTargetFilePath();
                Toast.makeText(context, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int getDuration(String str) {
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

    private void startAnim(boolean isStart) {
//        audioLayout.setVisibility(View.VISIBLE);
//        info.setText("上滑取消");
//        micIcon.setBackgroundDrawable(null);
//        micIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_mic_white_24dp));
        if (isStart) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setFormat("%S");
            chronometer.start();
        }
    }

    private void stopAnim() {
//        audioLayout.setVisibility(View.GONE);
//        bw_iv_record.setBackgroundDrawable(getResources().getDrawable(R.drawable.mybg));
        chronometer.stop();
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        audioInfors = new ArrayList<>();
    }

    public void getYyyzList(String czydm, String zyh, String yysj) {
        if (!bwRefresh.isRefreshing()) {
            bwRefresh.setRefreshing(true);
        }
        Log.e("WQ", "获取备忘列表" + "操作员代码" + czydm + "  住院号==" + zyh + "  时间==" + yysj);
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
                        if (bwRefresh.isRefreshing()) {
                            bwRefresh.setRefreshing(false);
                        }
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (bwRefresh.isRefreshing()) {
                            bwRefresh.setRefreshing(false);
                        }
                        audioInfors.clear();
                        Gson gson = new Gson();
                        audioInfors = gson.fromJson(response, new TypeToken<List<AudioInfor>>() {
                        }.getType());

                        if (null != audioAdapter) {
                            audioAdapter.refreshData(audioInfors);
                        }
                    }
                });
    }

    private void recorderUpload(final String czydm, final String zyh, String filename, String yymc, String yynr, final String yylx) {
//        Log.e("WQ", "上传的参数==" + czydm + " " + zyh + " " + filename + " " + yymc + " " + yynr + " " + yylx);
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
                    }

                    @Override
                    public void onResponse(String response, int id) {
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
    public void onRefresh() {
        getYyyzList(czydm, zyh, yysj);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                noteType = "tp";
                final Bitmap photo = data.getParcelableExtra("data");
                Drawable drawable = new BitmapDrawable(photo);
                iv_photo.setBackgroundDrawable(drawable);
                String name = "note.jpg";
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                FileOutputStream b;
                @SuppressLint("SdCardPath") File file = new File("/sdcard/移动医生/img/");
                file.mkdirs();// 创建文件夹
                @SuppressLint("SdCardPath") String fileName = "/sdcard/移动医生/img/" + name;
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
                    b.flush();
                    b.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                photoLocation = fileName;
            } else {
                activityUtils.showToast("拍照未成功！");
            }
        }
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
}
