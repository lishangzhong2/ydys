package com.tphy.hospitaldoctor.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseAppCompatActivity;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.pool.BaseAsyncRefreshPool2;
import com.tphy.hospitaldoctor.ui.adapter.JianChaListAdapter;
import com.tphy.hospitaldoctor.ui.adapter.MainCardAdapter;
import com.tphy.hospitaldoctor.ui.adapter.SimpleExpandableListAdapter;
import com.tphy.hospitaldoctor.ui.bean.JianChaInfo;
import com.tphy.hospitaldoctor.ui.bean.JianYanFile;
import com.tphy.hospitaldoctor.ui.bean.JianYanReport;
import com.tphy.hospitaldoctor.ui.bean.KeShi;
import com.tphy.hospitaldoctor.ui.bean.MessageInfo;
import com.tphy.hospitaldoctor.ui.bean.PatientInfo;
import com.tphy.hospitaldoctor.ui.bean.WenShuFile;
import com.tphy.hospitaldoctor.ui.fragment.BaoGaoFragment;
import com.tphy.hospitaldoctor.ui.fragment.HomeFragment;
import com.tphy.hospitaldoctor.ui.fragment.MsgFragment;
import com.tphy.hospitaldoctor.ui.fragment.WenShuFragment;
import com.tphy.hospitaldoctor.ui.fragment.YiZhuFragment;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.hospitaldoctor.utils.audio.MediaUtils;
import com.tphy.hospitaldoctor.views.top_snackbar.BaseTransientBottomBar;
import com.tphy.hospitaldoctor.views.top_snackbar.TopSnackBar;
import com.tphy.http.okhttp.OkHttpUtils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends BaseAppCompatActivity {

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.frag_container)
    FrameLayout frag_container;
    @BindView(R.id.nav_iv_home)
    ImageView nav_iv_home;
    @BindView(R.id.nav_iv_lj)
    ImageView nav_iv_lj;
    @BindView(R.id.nav_iv_yizhu)
    ImageView nav_iv_yizhu;
    @BindView(R.id.nav_iv_wenshu)
    ImageView nav_iv_wenshu;
    @BindView(R.id.nav_iv_baogao)
    ImageView nav_iv_baogao;
    @BindView(R.id.nav_iv_my)
    ImageView nav_iv_my;
    @BindView(R.id.iv_logout)
    ImageView iv_logout;
    @BindView(R.id.iv_changePassword)
    ImageView iv_changPassword;
    @BindView(R.id.texttemp)
    TextView tempText;

    /*卡片*/
    @BindView(R.id.nav_card)
    RelativeLayout card_layout;
    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.tv_cwh)
    TextView tv_cwh;
    @BindView(R.id.tv_hzxm)
    TextView tv_hzxm;
    @BindView(R.id.tv_zyh)
    TextView tv_zyh;
    @BindView(R.id.tv_hznl)
    TextView tv_hznl;
    @BindView(R.id.iv_gender)
    ImageView iv_gender;
    @BindView(R.id.tv_yllx)
    TextView tv_yllx;
    @BindView(R.id.tv_gchs)
    TextView tv_gchs;
    @BindView(R.id.tv_ksmc)
    TextView tv_ksmc;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_ryzd)
    TextView tv_ryzd;
    @BindView(R.id.tv_zzys)
    TextView tv_zzys;
    @BindView(R.id.iv_zzys)
    ImageView iv_zzys;
    @BindView(R.id.iv_level)
    ImageView iv_level;
    @BindView(R.id.main_cardList)
    RecyclerView cardRecycler;
    @BindView(R.id.navlist)
    LinearLayout navlist;
    @BindView(R.id.main_navEdit)
    EditText main_navEdit;
    @BindView(R.id.scanLayout)
    LinearLayout scanLayout;
    @BindView(R.id.card_iv_scan)
    ImageView iv_scan;
    /*卡片*/

    /*首页menu*/
    @BindView(R.id.menu_et)
    EditText menu_et;
    @BindView(R.id.menu_cb_my)
    CheckBox menu_cb_my;
    @BindView(R.id.menu_cb_in)
    CheckBox menu_cb_in;
    @BindView(R.id.home_rg_keshi)
    RadioGroup home_rg_keshi;
    @BindView(R.id.menu_rb_1)
    RadioButton menu_rb_1;
    @BindView(R.id.menu_rg_hljb)
    RadioGroup home_rg_hljb;
    @BindView(R.id.radio_level_all)
    RadioButton menu_rb_levelall;
    @BindView(R.id.menu_tv_notmy)
    TextView menu_tv_notmy;
    @BindView(R.id.menu_tv_my)
    TextView menu_tv_my;
    @BindView(R.id.menu_tv_in)
    TextView menu_tv_in;
    @BindView(R.id.menu_tv_out)
    TextView menu_tv_out;

    @BindView(R.id.nav_home_menu)
    LinearLayout nav_home_menu;
    @BindView(R.id.nav_lj_menu)
    LinearLayout nav_lj_menu;
    @BindView(R.id.nav_yizhu_menu)
    RelativeLayout nav_yizhu_menu;
    @BindView(R.id.nav_wenshu_menu)
    RelativeLayout nav_wenshu_menu;
    @BindView(R.id.nav_baogao_menu)
    LinearLayout nav_baogao_menu;
    @BindView(R.id.nav_beiwang_menu)
    LinearLayout nav_beiwang_menu;
    @BindView(R.id.nav_msg_menu)
    RelativeLayout nav_msg_menu;

    /*报告*/
    @BindView(R.id.baogao_rg)
    RadioGroup baogao_rg;
    @BindView(R.id.baogao_rb_jiancha)
    RadioButton baogao_rb_jiancha;
    @BindView(R.id.baogao_rb_jianyan)
    RadioButton baogao_rb_jianyan;
    @BindView(R.id.baogao_list)
    ExpandableListView jianyanListView;
    @BindView(R.id.jiancha_recycler)
    RecyclerView jiancha_recycler;
    @BindView(R.id.rl_jy_pxfs)
    RelativeLayout rl_jy_pxfs;
    @BindView(R.id.menu_cb_jy_sj)
    CheckBox cb_jy_sjpx;
    @BindView(R.id.menu_cb_jy_mc)
    CheckBox cb_jy_mcpx;



    /*医嘱*/
    @BindView(R.id.menu_rb_longyizhu)
    RadioButton menu_rb_longyizhu;
    @BindView(R.id.menu_rb_shortyizhu)
    RadioButton menu_rb_shortyizhu;
    @BindView(R.id.yizhu_rg_types)
    RadioGroup yizhu_rg_types;
    @BindView(R.id.yizhu_rg_states)
    RadioGroup yizhu_rg_states;
    @BindView(R.id.stateClear)
    RadioButton stateClear;
    @BindView(R.id.yizhu_rb_ting)
    RadioButton rb_ting;
    @BindDrawable(R.drawable.state_chexiao_selector)
    Drawable state_chexiao_selector;
    @BindDrawable(R.drawable.state_yiting)
    Drawable state_yiting_selector;

    /*文书*/
    @BindView(R.id.wenshu_menu_list)
    ExpandableListView expandableListView;
    @BindView(R.id.menu_rb_twd)
    RadioButton rb_twd;
    @BindView(R.id.menu_rb_xtd)
    RadioButton rb_xtjcb;

    /*备忘*/
    @BindView(R.id.bwTv)
    TextView bwTv;
    @BindView(R.id.bw_tv_date)
    TextView bw_tv_date;

    /*公告消息*/
    @BindView(R.id.menu_rb_unread)
    RadioButton rb_unread;
    @BindView(R.id.menu_rb_readed)
    RadioButton rb_readed;
    @BindView(R.id.menu_rb_msg)
    RadioButton rb_clear;
    @BindView(R.id.message_rg)
    RadioGroup message_rg;
    //    @BindView(R.id.new_msg)
//    CardView new_msg;
    @BindView(R.id.menu_cb_sign)
    TextView cbSign;
//    @BindView(R.id.msg_title)
//    TextView msg_title;

    @BindView(R.id.main_beiwang)
    ImageView iv_beiwang;
    @BindView(R.id.menu_cb_zhibiao)
    TextView tv_zhibiao;

    /*工具栏*/
    @BindView(R.id.iv_callTools)
    ImageView iv_callTools;
    @BindView(R.id.menu_tools)
    RelativeLayout menu_toolsLayout;
    @BindView(R.id.tool_iv_scan)
    ImageView tool_iv_scan;
    @BindView(R.id.tool_iv_note)
    ImageView tool_iv_note;
    @BindView(R.id.tool_iv_nav)
    ImageView tool_iv_nav;
    @BindView(R.id.tool_iv_signin)
    ImageView tool_iv_signin;

    /**/
    private BaseFragment[] fragments;
    private int index;
    private int currentTabIndex;
    private List<ImageView> navIcons;
    private BaoGaoFragment baoGaoFragment;
    private YiZhuFragment yiZhuFragment;
    private WenShuFragment wenShuFragment;
    private String zhuYuanHao = "";
    private List<ViewGroup> menus;
    private boolean isCardSelected;
    private AlertDialog alertDialog;
    private List<KeShi> keShiList;
    private List<PatientInfo> mainPatientInfors;
    private int cardPosition = -1;
    private boolean isCardTrans;
    private long firstTime = 0;
    private List<MessageInfo> messageInfos;
    //    private boolean isWantToRead = true;
    private boolean isReading;

    /*首页*/
    private String ksdmFromPatient = "";
    private String myCZYDM = "";
    private String password;
    private String mKSDM = "";

    public String mHLJB = "";
    public boolean isMine = true;
    public boolean isIn = true;
    //private final String[] hljbArray = new String[]{"一级护理", "二级护理", "三级护理", "特级护理", ""};
    private final String[] hljbArray = new String[]{"","特级护理", "一级护理", "二级护理", "三级护理"};
    private boolean homeCardChanged;

    /*医嘱*/
    private String yizhutype = "全部医嘱";
    private String[] stateCode = new String[]{"1", "2", "3", "7", "6", "10", ""};
//    private String[] stateCode = new String[]{"0", "1", "2", "4", "3", "5", "6", "7", "10", ""};

    /*文书*/
    private List<List<Map<String, String>>> childData;
    private List<Map<String, String>> groupData;
    private SimpleExpandableListAdapter mAdapter;
    private ProgressDialog progressDialog;
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";
    private List<WenShuFile> wenShuFileList;
    private ActivityUtils activityUtils;
    private int wenshuGroupPosition;
    private int wenshuChildPosition = -1;
    private long lastScrolledTime;
    /*报告*/
    private List<JianYanReport> jianYanReports;
    private List<JianYanFile> jianYanFiles;
    private SimpleExpandableListAdapter baogaoAdapter;
    private String rysj;
    private List<List<Map<String, String>>> BGchildData;
    private List<Map<String, String>> BGgroupData;
    private String beiwangDate;
    private List<JianChaInfo> jianChaInfos;
    private JianChaListAdapter adapter;
    private int keshiCheckedPosition;
    private BaseAsyncRefreshPool2 refreshPool;

    /*备忘功能*/
    private PopupWindow popupWindow;
    private String noteType = "tt";
    private String photoLocation;
    private ImageView bwImage;
    private AlertDialog jiluDialog;
    private MediaUtils mediaUtils;
    private MainCardAdapter mainCardAdapter;
    private int mHiddenViewMeasuredHeight;
    //是否修改过备忘录
    private boolean isChangeBeiWang = false;

    /*考勤*/
    private String swbz = "0";
    private String xwbz = "0";
    private String wsbz = "0";
    private String blh;
    //    private int[] toolsLoaction;
    private String cybz = "";

    /*消息*/
    private float BEEP_VOLUME = 0.10f;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;

    public String getCybz() {
        return cybz;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    private void initData() {
        this.activityUtils = new ActivityUtils(this);
        SharedPreferences preferences = getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        //changeUrl(preferences);
        myCZYDM = preferences.getString("czydm", "");
        password = preferences.getString("jmkl", "");
        keShiList = new ArrayList<>();
        groupData = new ArrayList<>();
        childData = new ArrayList<>();
        BGgroupData = new ArrayList<>();
        BGchildData = new ArrayList<>();
        wenShuFileList = new ArrayList<>();
        Intent intent = getIntent();
        //noinspection unchecked
        keShiList = (List<KeShi>) intent.getSerializableExtra("keshi");
        mKSDM = keShiList.get(0).getDEPT_ID();


        jianYanReports = new ArrayList<>();
        jianYanFiles = new ArrayList<>();
        jianChaInfos = new ArrayList<>();
        messageInfos = new ArrayList<>();
        mainPatientInfors = new ArrayList<>();
        Common.density = mScreenDensity;
    }

    @Override
    protected void setPviewsAndEvents() {
    }

    @Override
    protected void setLviewsAndEvents() {
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initData();
        isNeedPermission = true;
        iv_photo.setBackground(getResources().getDrawable(R.mipmap.ic_donno));
        navIcons = new ArrayList<>();
        menus = new ArrayList<>();
        menus.add(nav_home_menu);
        menus.add(nav_yizhu_menu);
        menus.add(nav_wenshu_menu);
        menus.add(nav_baogao_menu);
        menus.add(nav_msg_menu);
//        icon
        navIcons.add(nav_iv_home);
        navIcons.add(nav_iv_yizhu);
        navIcons.add(nav_iv_wenshu);
        navIcons.add(nav_iv_baogao);
        navIcons.add(nav_iv_my);
        //fragment
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ksdm", keShiList.get(0).getDEPT_ID());
        homeFragment.setArguments(bundle);
        baoGaoFragment = new BaoGaoFragment();
        wenShuFragment = new WenShuFragment();
        yiZhuFragment = new YiZhuFragment();
        MsgFragment msgFragment = new MsgFragment();
        setNavTrans(0);
        fragments = new BaseFragment[]{homeFragment, yiZhuFragment, wenShuFragment, baoGaoFragment, msgFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, homeFragment)                                              /*.add(R.id.frag_container,wenShuFragment).hide(wenShuFragment)*/
                .add(R.id.frag_container, yiZhuFragment).add(R.id.frag_container, baoGaoFragment).add(R.id.frag_container, wenShuFragment)
                .add(R.id.frag_container, msgFragment).hide(wenShuFragment).hide(baoGaoFragment).hide(yiZhuFragment).hide(msgFragment).show(homeFragment)
                .commit();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请选择床位");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        initHomeMenu();
        initYiZhuMenu();
        initWenShuMenu();
        initBaoGao();
       // initLunXun();
        initMessage();
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(myCZYDM, true);
            }
        });
        iv_changPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setMessage("修改此密码后，his密码也将同步修改，确定要修改吗？");
                mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initPasswordDialog();
                        dialog.dismiss();
                    }
                });
                mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.create().show();
            }
        });
        initRecorder();
        initCardList();
    }

    /*修改密码的dialog*/
    private void initPasswordDialog() {
        View passwordView = LayoutInflater.from(this).inflate(R.layout.dialog_change_passowrd, null);
        final TextView tv_change = passwordView.findViewById(R.id.dialog_tv_change);
        final EditText et_passowrd = passwordView.findViewById(R.id.dialog_et_password);
        final TextView tv_confirm = passwordView.findViewById(R.id.dialog_tv_confirm);
        final EditText et_confirm = passwordView.findViewById(R.id.dialog_et_confirm);
        final ImageView iv_commit = passwordView.findViewById(R.id.dilog_change_commit);
        final AlertDialog passwordDialog = new AlertDialog.Builder(MainActivity.this).create();
        passwordDialog.setView(new EditText(this));
        passwordDialog.show();
        passwordDialog.setContentView(passwordView);
        iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!iv_commit.isSelected()) {
                    String mPassword = et_passowrd.getText().toString();
                    String s = null;
                    try {
                        s = new String(Hex.encodeHex(DigestUtils.md5(mPassword)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (password.equals(s)) {
                        tv_change.setText("请输入新密码：");
                        et_passowrd.setText("");
                        tv_confirm.setVisibility(View.VISIBLE);
                        et_confirm.setVisibility(View.VISIBLE);
                        iv_commit.setSelected(true);
                    } else {
                        activityUtils.showToast("密码不符！");
                    }
                } else {
                    String pwd1 = et_passowrd.getText().toString();
                    String pwd2 = et_confirm.getText().toString();
                    if (pwd1.equals(pwd2)) {
                        String finalPwd = new String(Hex.encodeHex(DigestUtils.md5(pwd2)));
                        updateUserPwd(myCZYDM, finalPwd, passwordDialog);
                    } else {
                        activityUtils.showToast("两次输入不一致");
                        et_passowrd.setText("");
                        et_confirm.setText("");
                    }
                }

            }
        });

    }

    /*快捷栏切换患者列表*/
    private void initCardList() {
        mainCardAdapter = new MainCardAdapter(this, mainPatientInfors);
        cardRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cardRecycler.setAdapter(mainCardAdapter);
        mainCardAdapter.setOnItemClickListener(new MainCardAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final YiZhuFragment fragment = (YiZhuFragment) fragments[1];
                String zyh = mainPatientInfors.get(position).getZyh();
                if (!zyh.equals(zhuYuanHao)) {
                    /*判断医嘱fragment中是否有未保存的医嘱*/
                    if (fragment.canTransPatient()) {
                        cardPosition = position;
                        updateCardInfor(mainPatientInfors.get(position), position);
                        reloadInfors();
                        animateClose(navlist);
                        for (int i = 0; i < mainPatientInfors.size(); i++) {
                            PatientInfo info = mainPatientInfors.get(i);
                            if (i == position) {
                                info.setSelected(true);
                            } else {
                                info.setSelected(false);
                            }
                        }
                        mainCardAdapter.refreshDate(mainPatientInfors);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setPositiveButton("删除并切换", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteUnSavedYizhu();
                                cardPosition = position;
                                updateCardInfor(mainPatientInfors.get(position), position);
                                reloadInfors();
                                animateClose(navlist);
                                for (int i = 0; i < mainPatientInfors.size(); i++) {
                                    PatientInfo info = mainPatientInfors.get(i);
                                    if (i == position) {
                                        info.setSelected(true);
                                    } else {
                                        info.setSelected(false);
                                    }
                                }
                                Constant.isSavedYizhuEmpty = true;
                                mainCardAdapter.refreshDate(mainPatientInfors);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setMessage("还有未保存的医嘱！是否删除未保存医嘱并切换患者？");
                        alertDialog.show();
                    }
                }
            }
        });
        float density = getResources().getDisplayMetrics().density;
        mHiddenViewMeasuredHeight = (int) (density * 403 + 0.5);
        main_navEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charquense = editable.toString();
                List<PatientInfo> tempList = new ArrayList<>();
                for (int i = 0; i < mainPatientInfors.size(); i++) {
                    PatientInfo patientInfo = mainPatientInfors.get(i);
                    String cwh = patientInfo.getCwh();
                    String hzxm = patientInfo.getHzxm();
                    if (cwh.contains(charquense) || hzxm.contains(charquense)) {
                        tempList.add(patientInfo);
                    }
                }
                mainCardAdapter.refreshDate(tempList);
            }
        });
    }

    /*初始化录音功能*/
    @SuppressLint("ClickableViewAccessibility")
    private void initRecorder() {
        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        @SuppressLint("SdCardPath") File file = new File("/sdcard/移动医生/audio/");
        file.mkdirs();
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        /*UUID.randomUUID()*/
        mediaUtils.setTargetName(System.currentTimeMillis() + ".m4a");
    }

    /*初始化轮询*/
    private void initLunXun() {
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if ((audioService != null ? audioService.getRingerMode() : 0) != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        refreshPool = new BaseAsyncRefreshPool2<String>(tempText, 0, 5000) {
            @Override
            protected String getData() {
                getMessageList(myCZYDM);
                return "a";
            }

            @Override
            protected void setData(String data) {
                tempText.setText(data);
            }
        }
        ;
        refreshPool.startRefresh();
    }

    public void setCardList(List<PatientInfo> list) {
        this.mainPatientInfors = list;
        mainCardAdapter.refreshDate(list);
    }

    /*从mainActivity患者列表选择患者*/
    private void updateCardInfor(PatientInfo patientInfo, int selectedPosition) {
        this.cardPosition = selectedPosition;
        updateCardInfor(patientInfo);
        isCardTrans = true;
    }

    /*从HomeFragment患者列表选择患者,传过来所选患者信息*/
    public void updateCardInfor(PatientInfo patientInfo) {
        if (scanLayout.getVisibility() == View.VISIBLE) {
            scanLayout.setVisibility(View.GONE);
        }
        isCardSelected = true;
        homeCardChanged = true;
        isCardTrans = false;
        String cwh = patientInfo.getCwh();
        String hljb = patientInfo.getHljb();
        String hzxm = patientInfo.getHzxm();
        String ksmc = patientInfo.getKsmc();
        String nl = patientInfo.getHznl();
        String nldw = patientInfo.getNldw();
        String ryzd = patientInfo.getRyzd();
        String yllx = patientInfo.getYllx();
        String zyh = patientInfo.getZyh();
        String zzys = patientInfo.getZzys();
        String hzxb = patientInfo.getHzxb();
        String hlks = patientInfo.getHlks();
        String gchs = patientInfo.getGchs();
        //lis报告用住院号查 还是用病历号查
        blh = patientInfo.getZyh();
        cybz = patientInfo.getCybz();
        String finalGCHS = Common.getSpecificString(gchs, ",", 0);
        /*切换卡片，清空报告处的二级菜单*/
        if (!zyh.equals(zhuYuanHao)) {
            if (null != BGchildData) {
                BGchildData.clear();
            }
            if (null != BGgroupData) {
                BGgroupData.clear();
            }
            if (null != baogaoAdapter) {
                baogaoAdapter.refreshData(BGgroupData, BGchildData);
            }
            /*切换卡片，清空文书处的二级菜单*/
            if (null != childData) {
                childData.clear();
            }
            if (null != groupData) {
                for (int i = 0; i < groupData.size(); i++) {
                    expandableListView.collapseGroup(i);
                }
                groupData.clear();
            }
            if (null != mAdapter) {
                mAdapter.refreshData(groupData, childData);
            }
        }
        rysj = Common.formatHomeCardTimeCommon(patientInfo.getRysj());
        String format = Common.formatHomeCardTime(rysj);
//        ksdmFromPatient = patientInfo.getKsdm();
        ksdmFromPatient = patientInfo.getHlks();
        this.zhuYuanHao = zyh;
        tv_cwh.setText(cwh);
        tv_ksmc.setText(ksmc);
        tv_zzys.setText(zzys);
        tv_zyh.setText(zyh);
        tv_hzxm.setText(hzxm);
        tv_gchs.setText(finalGCHS);
        String s = nl + nldw;
        tv_hznl.setText(s);
        tv_ryzd.setText(ryzd);
        tv_yllx.setText(yllx);
        tv_time.setText(format);
        switch (hzxb) {
            case "男":
                iv_gender.setBackground(getResources().getDrawable(R.mipmap.ic_male));
                iv_photo.setBackground(getResources().getDrawable(R.mipmap.ic_boy));
                break;
            case "女":
                iv_gender.setBackground(getResources().getDrawable(R.mipmap.ic_female));
                iv_photo.setBackground(getResources().getDrawable(R.mipmap.ic_girl));
                break;
        }
        switch (hljb) {
            case "一级护理":
                iv_level.setBackground(getResources().getDrawable(R.mipmap.ic_level_one));
                break;
            case "二级护理":
                iv_level.setBackground(getResources().getDrawable(R.mipmap.ic_level_two));
                break;
            case "三级护理":
                iv_level.setBackground(getResources().getDrawable(R.mipmap.ic_level_three));
                break;
            case "四级护理":
                iv_level.setBackground(getResources().getDrawable(R.mipmap.ic_level_special));
                break;
            case "":
                iv_level.setBackground(null);
                break;
        }
    }

    /* R.id.card_iv_more*/
    @OnClick({R.id.tv_hljb, R.id.stateTV, R.id.mainLayout, R.id.bwTv, R.id.iv_callTools, R.id.tool_iv_scan, R.id.tool_iv_note, R.id.tool_iv_nav, R.id.tool_iv_signin, R.id.card_iv_scan})
    public void onCardClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hljb:
                menu_rb_levelall.setChecked(true);
                break;
            case R.id.stateTV:
                stateClear.setChecked(true);
                break;
            case R.id.mainLayout:
                if (menu_toolsLayout.getVisibility() == View.VISIBLE) {
                    Animation buttonAnime = AnimationUtils.loadAnimation(this, R.anim.button_tools_rotateback);
                    buttonAnime.setFillAfter(true);
                    iv_callTools.setAnimation(buttonAnime);
                    menu_toolsLayout.setVisibility(View.GONE);
                    tool_iv_scan.setVisibility(View.INVISIBLE);
                    tool_iv_note.setVisibility(View.INVISIBLE);
                    tool_iv_nav.setVisibility(View.INVISIBLE);
                    tool_iv_signin.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.bwTv:
                if (null != beiwangDate && !TextUtils.isEmpty(beiwangDate)) {
                    MsgFragment fragment = (MsgFragment) fragments[4];
                    fragment.getYyyzList(myCZYDM, zhuYuanHao, beiwangDate);
                }
                break;
            case R.id.iv_callTools:
                if (menu_toolsLayout.getVisibility() == View.GONE) {
                    Animation buttonAnime = AnimationUtils.loadAnimation(this, R.anim.button_tools_rotate);
                    buttonAnime.setFillAfter(true);
                    iv_callTools.setAnimation(buttonAnime);
                    menu_toolsLayout.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.menu_tools_rotate);
                    menu_toolsLayout.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tool_iv_scan.setVisibility(View.VISIBLE);
                            tool_iv_note.setVisibility(View.VISIBLE);
                            tool_iv_nav.setVisibility(View.VISIBLE);
                            tool_iv_signin.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    Animation buttonAnime = AnimationUtils.loadAnimation(this, R.anim.button_tools_rotateback);
                    buttonAnime.setFillAfter(true);
                    iv_callTools.setAnimation(buttonAnime);
                    menu_toolsLayout.setVisibility(View.GONE);
                    tool_iv_scan.setVisibility(View.INVISIBLE);
                    tool_iv_note.setVisibility(View.INVISIBLE);
                    tool_iv_nav.setVisibility(View.INVISIBLE);
                    tool_iv_signin.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tool_iv_scan:
                Intent qrCodeIntent = new Intent(this, CaptureActivity.class);
                startActivity(qrCodeIntent);
                break;
            case R.id.tool_iv_note:
                if (isCardSelected) {
                    if (getCybz().equals("已出院")) {
                        activityUtils.showToast("该病人已出院");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        View bwTypeView = LayoutInflater.from(this).inflate(R.layout.dialog_bwtype, null, false);
                        ImageView iv_tw = bwTypeView.findViewById(R.id.dialog_iv_twbw);
                        ImageView iv_yy = bwTypeView.findViewById(R.id.dialog_iv_yybw);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.setContentView(bwTypeView);
                        iv_tw.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                if(!isNeedPermission){
//                                    showMissingPermissionDialog();
//                                }

                                showTWBeiWang();
                                alertDialog.dismiss();
                            }
                        });
                        iv_yy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                setFTJL();
                                alertDialog.dismiss();
                            }
                        });
                    }
                } else {
                    if (null != alertDialog && !alertDialog.isShowing()) {
                        alertDialog.show();
                    }
                }
                break;
            case R.id.tool_iv_nav:
                if (index == 0) {
                    activityUtils.showToast("首页无需导航");
                    return;
                }
                if (navlist.getVisibility() == View.GONE) {
                    openCardList();
                    Animation buttonAnime = AnimationUtils.loadAnimation(this, R.anim.button_tools_rotateback);
                    buttonAnime.setFillAfter(true);
                    iv_callTools.setAnimation(buttonAnime);
                    menu_toolsLayout.setVisibility(View.GONE);
                    tool_iv_scan.setVisibility(View.INVISIBLE);
                    tool_iv_note.setVisibility(View.INVISIBLE);
                    tool_iv_nav.setVisibility(View.INVISIBLE);
                    tool_iv_signin.setVisibility(View.INVISIBLE);
                } else {
                    animateClose(navlist);
                }
                break;
            case R.id.tool_iv_signin:
                Date dateAfterDays = Common.getDateAfterDays(new Date(System.currentTimeMillis()), -1);
                getAttendsList(myCZYDM, Common.formatDateToYMD(dateAfterDays), Common.getCurrentDate());
                break;
            case R.id.card_iv_scan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    /*动画显示患者列表，并滑倒已选位置*/
    private void openCardList() {
        animeOpen(navlist);
        cardRecycler.scrollToPosition(cardPosition);
    }
   /* 更新病人列表*/
//    public  void updatePatientList(){
//        keshiCheckedPosition = a.indexOfChild(a.findViewById(position));
//        mKSDM = keShiList.get(keshiCheckedPosition).getDEPT_ID();
//        final HomeFragment homeFragment = (HomeFragment) fragments[0];
////        if (menu_cb_my.isChecked() || (!menu_cb_in.isChecked()) || (!mHLJB.equals(""))) {
//            homeFragment.getPatientList("", "", "", mKSDM, "", "", true, false, "1", "50");
////        } else {
////            homeFragment.getPatientList("", "", "", mKSDM, "", "", false, false, "1", "50");
////        }
//    }
    /*切换患者后，fragment重新加载数据*/
    private void reloadInfors() {
        switch (index) {
            case 0:

                break;
            case 1:
                YiZhuFragment yizhu = (YiZhuFragment) fragments[index];
                int flag = menu_rb_longyizhu.isChecked() ? 1 : 0;
                yizhu.gettDocOrderInfoByZyh(zhuYuanHao, flag, yizhutype, false);
                break;
            case 2:
                WenShuFragment wenshu = (WenShuFragment) fragments[index];
                wenshu.clearText();
                if (rb_twd.isChecked()) {
                    String[] strings = Common.initQueryDate(rysj);
                        wenshu.getTwdInfo(zhuYuanHao, strings[0], strings[1], rysj);

                }
                if (rb_xtjcb.isChecked()){
                    wenshu.getXtjcb(zhuYuanHao,rysj);
                }
                if(!rb_xtjcb.isChecked()&&!rb_twd.isChecked()) {
                    getFileList(zhuYuanHao);
                }
                break;
            case 3:
                BaoGaoFragment baogao = (BaoGaoFragment) fragments[index];
                baogao.clearConttent();
                if (baogao_rb_jianyan.isChecked()) {
                    jianyanListView.setVisibility(View.VISIBLE);
                    jiancha_recycler.setVisibility(View.GONE);
                    baogao.showJianyan();
                    rl_jy_pxfs.setVisibility(View.VISIBLE);
                    getLisInfoDAL(blh, rysj);
                }
                if (baogao_rb_jiancha.isChecked()) {
                    jianyanListView.setVisibility(View.GONE);
                    jiancha_recycler.setVisibility(View.VISIBLE);
                    rl_jy_pxfs.setVisibility(View.GONE);
                    baogao.showJianCha();
                    getCheckInfoDAL(blh );
                }
                break;
            case 4:
//                LuJingFragment lujing = (LuJingFragment) fragments[index];
                break;
            case 5:
//            BeiWangFragment beiwang = (BeiWangFragment) fragments[index];
//                beiwang.getYyyzList(myCZYDM, zhuYuanHao, beiwangDate);
                break;
        }
    }

    /*主页面的左侧菜单栏*/
    private void initHomeMenu() {
        menu_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                HomeFragment fragment = (HomeFragment) fragments[0];
                fragment.selectList(isMine, isIn, mHLJB, s1);
            }
        });
        final HomeFragment homeFragment = (HomeFragment) fragments[0];
        //动态添加科室的radioButton，并设置样式
        for (int i = 0; i < keShiList.size(); i++) {
            String dept_name = keShiList.get(i).getDEPT_NAME();
            String time_limit = keShiList.get(i).getTIME_LIMIT();
            String ksrs = keShiList.get(i).getKSRS();
            if (!time_limit.equals("")) {
                RadioButton radioButton = new RadioButton(this);
                RadioGroup.LayoutParams radioParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (30 * Common.density));
                radioButton.setTextColor(getResources().getColor(android.R.color.white));
                radioButton.setTextSize(12);
                radioButton.setButtonDrawable(new BitmapDrawable((Bitmap) null));
                Drawable drawable = getResources().getDrawable(R.drawable.menu_radio_button);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                radioButton.setCompoundDrawables(drawable, null, null, null);
                radioButton.setCompoundDrawablePadding(8);
                radioButton.setGravity(Gravity.CENTER_VERTICAL);
                radioButton.setPadding((int) (20 * Common.density), 0, 0, 0);
                radioButton.setBackground(getResources().getDrawable(R.drawable.menu_item_bg));
                String department = dept_name + "(" + ksrs + ")";
                radioButton.setText(department);
                radioButton.setLayoutParams(radioParams);
                home_rg_keshi.addView(radioButton);
            } else {
                String s = dept_name + "(" + ksrs + ")";
                menu_rb_1.setText(s);
            }
        }
        home_rg_keshi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                keshiCheckedPosition = group.indexOfChild(group.findViewById(checkedId));
                mKSDM = keShiList.get(keshiCheckedPosition).getDEPT_ID();
                if (menu_cb_my.isChecked() || (!menu_cb_in.isChecked()) || (!mHLJB.equals(""))) {
                    homeFragment.getPatientList("", "", "", mKSDM, "", "", true, false, "1", "50");
                } else {
                    homeFragment.getPatientList("", "", "", mKSDM, "", "", false, false, "1", "50");
                }
            }
        });

        menu_cb_my.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isMine = true;
                    homeFragment.selectList(true, isIn, mHLJB, false, null);
                } else {
                    isMine = false;
                    homeFragment.selectList(false, isIn, mHLJB, false, null);
                }
            }
        });

        menu_cb_in.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isIn = true;
                    homeFragment.selectList(isMine, true, mHLJB, false, null);
                } else {
                    isIn = false;
                    homeFragment.selectList(isMine, false, mHLJB, false, null);
                }
            }
        });
        home_rg_hljb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = group.indexOfChild(group.findViewById(checkedId));
                mHLJB = hljbArray[i];
                homeFragment.selectList(menu_cb_my.isChecked(), menu_cb_in.isChecked(), mHLJB, false, null);
            }
        });
    }



    /*设置这页面菜单栏的人数*/
    public void initHomeRenShu(String allPatient, String myPatient, String inHospital, String outHospita, String levelone, String leveltwo, String levelthree, String levelspecial) {
        RadioButton radioButton = (RadioButton) home_rg_keshi.getChildAt(keshiCheckedPosition);
        String s = radioButton.getText().toString();
        Pattern pattern = Pattern.compile(".*?(?=\\()");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            String m = matcher.group() + "（" + allPatient + "）";
            radioButton.setText(m);
        }
        menu_tv_my.setText(String.format(getResources().getString(R.string.myPatient), myPatient));
        menu_tv_notmy.setText(String.format(getResources().getString(R.string.myPatient), allPatient));
        menu_tv_in.setText(String.format(getResources().getString(R.string.myPatient), inHospital));
        menu_tv_out.setText(String.format(getResources().getString(R.string.myPatient), outHospita));
    }

    /*医嘱的左侧菜单栏*/
    private void initYiZhuMenu() {
        final String[] yizhuFenLei = new String[]{"当前医嘱", "今日医嘱", "昨日医嘱", "近一周医嘱", "全部医嘱"};
        /*长期医嘱按钮*/
        menu_rb_longyizhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RadioButton lastButton = (RadioButton) yizhu_rg_states.getChildAt(5);
                    lastButton.setChecked(true);
                    /*撤销图标换成停止图标*/
                    rb_ting.setBackground(state_yiting_selector);
                    yiZhuFragment.gettDocOrderInfoByZyh(zhuYuanHao, 1, yizhutype, false);
                }
            }
        });
        /*临时医嘱按钮*/
        menu_rb_shortyizhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RadioButton lastButton = (RadioButton) yizhu_rg_states.getChildAt(5);
                    lastButton.setChecked(true);
                    rb_ting.setBackground(state_chexiao_selector);
                    yiZhuFragment.gettDocOrderInfoByZyh(zhuYuanHao, 0, yizhutype, false);
                }
            }
        });
        /*医嘱分类筛选*/
        yizhu_rg_types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = group.indexOfChild(group.findViewById(checkedId));
                stateClear.setChecked(true);
                yizhutype = yizhuFenLei[i];
                int flag = menu_rb_longyizhu.isChecked() ? 1 : 0;
                yiZhuFragment.gettDocOrderInfoByZyh(zhuYuanHao, flag, yizhutype, false);
            }
        });
        /*医嘱状态筛选*/
        yizhu_rg_states.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int position = radioGroup.indexOfChild(radioGroup.findViewById(i));
                yiZhuFragment.sortYizhu("", stateCode[position]);
            }
        });
    }

    /*删除未保存的医嘱，显然是*/
    public void deleteUnSavedYizhu() {
        YiZhuFragment fragment = (YiZhuFragment) fragments[1];
        fragment.deleteNotSavedYizhu();
    }

    /*文书的左侧菜单栏*/
    private void initWenShuMenu() {
        final WenShuFragment wenShuFragment = (WenShuFragment) fragments[2];
        /*文书中体温单的查看*/
//        rb_twd.setChecked(true);
        rb_twd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String[] strings = Common.initQueryDate(rysj);
                if (isChecked) {
                    if (mAdapter != null) {
                        mAdapter.clearCheckState();
                    }
                    homeCardChanged = false;
                    rb_xtjcb.setChecked(false);
                    wenShuFragment.getTwdInfo(zhuYuanHao, strings[0], strings[1], rysj);
                }
            }
        });
        rb_xtjcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mAdapter != null) {
                        mAdapter.clearCheckState();
                    }
                    homeCardChanged = false;

                }
                rb_twd.setChecked(false);
                wenShuFragment.getXtjcb(zhuYuanHao,rysj);
            }
        });
//        rb_twd.setChecked(true);
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.simple_expandable_list_item_1,
                new String[]{NAME, IS_EVEN},
                new int[]{android.R.id.text1, android.R.id.text2},
                childData,
                R.layout.simple_expandable_list_item_2,
                new String[]{NAME, IS_EVEN},
                new int[]{R.id.childBox}
        );
        expandableListView.setDivider(new BitmapDrawable((Bitmap) null));
        expandableListView.setChildDivider(new BitmapDrawable((Bitmap) null));
        expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.group_indicator));
        expandableListView.setAdapter(mAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        /*病历的item点击*/
        mAdapter.setOnItemCheckedListener(new SimpleExpandableListAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(View view, int groupPosition, int childPosition) {

                rb_twd.setChecked(false);
                rb_xtjcb.setChecked(false);
                wenshuGroupPosition = groupPosition;
                wenshuChildPosition = childPosition;
                WenShuFile wenShuFile = wenShuFileList.get(groupPosition);
                List<WenShuFile.WS> wslist = wenShuFile.getWslist();
                WenShuFile.WS ws = wslist.get(childPosition);
                String file_no = ws.getFILE_NO();
                String creator_id = ws.getCREATOR_ID();
                String mr_code = ws.getMR_CODE();
                wenShuFragment.getFileContent(zhuYuanHao, file_no, creator_id, mr_code,ws.getTOPIC(),ws.getMR_CLASS());
                mAdapter.setCheckState(groupPosition, childPosition);
            }
        });

    }

    /*文书的病历的接口及加载数据*/
    private void getFileList(String zyh) {
        if (null != progressDialog && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        String url = Constant.BaseURL + "/GetFileList";
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
                        groupData.clear();
                        childData.clear();
                        wenShuFileList.clear();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                WenShuFile wenShuFile = new WenShuFile();
                                JSONObject jsonObject = array.getJSONObject(i);
                                String cname = jsonObject.getString("cname");
                                String ccode = jsonObject.getString("ccode");
                                wenShuFile.setCname(cname);
                                wenShuFile.setCcode(ccode);
                                String wslist = jsonObject.getString("wslist");
                                if(!wslist.equals("")&&!wslist.equals("[]")) {
                                    JSONArray wslistArray = jsonObject.getJSONArray("wslist");
                                    Map<String, String> curGroupMap = new HashMap<>();
                                    groupData.add(curGroupMap);
                                    curGroupMap.put(NAME, cname);
                                    curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
                                    List<Map<String, String>> children = new ArrayList<>();
                                    List<WenShuFile.WS> wsList = new ArrayList<>();
                                    for (int j = 0; j < wslistArray.length(); j++) {
                                        WenShuFile.WS ws = new WenShuFile.WS();
                                        JSONObject innerObject = wslistArray.getJSONObject(j);
                                        String topic = innerObject.getString("TOPIC");
                                        String inp_no = innerObject.getString("INP_NO");
                                        String file_no = innerObject.getString("FILE_NO");
                                        String creator_id = innerObject.getString("CREATOR_ID");
                                        String creator_name = innerObject.getString("CREATOR_NAME");
                                        String create_date_time = innerObject.getString("CREATE_DATE_TIME");
                                        String mr_code = innerObject.getString("MR_CODE");
                                        String file_flag = innerObject.getString("FILE_FLAG");
                                        String last_modify_time = innerObject.getString("LAST_MODIFY_TIME");
                                        String mr_class = innerObject.getString("MR_CLASS");
                                        Map<String, String> curChildMap = new HashMap<>();
                                        children.add(curChildMap);
                                        curChildMap.put(NAME, topic + "\t" + create_date_time);
                                        curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
                                        ws.setTOPIC(topic);
                                        ws.setFILE_NO(file_no);
                                        ws.setINP_NO(inp_no);
                                        ws.setCREATOR_ID(creator_id);
                                        ws.setCREATOR_NAME(creator_name);
                                        ws.setCREATE_DATE_TIME(create_date_time);
                                        ws.setMR_CODE(mr_code);
                                        ws.setFILE_FLAG(file_flag);
                                        ws.setLAST_MODIFY_TIME(last_modify_time);
                                        ws.setMR_CLASS(mr_class);
                                        wsList.add(ws);
                                    }
                                    wenShuFile.setWslist(wsList);
                                    childData.add(children);
                                    wenShuFileList.add(wenShuFile);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.refreshData(groupData, childData);
                    }
                });
    }

    /*病历滑动到底部自动切换下一项*/
    public void wenshuAutoScrollToNext() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastScrolledTime > 1000) {
            lastScrolledTime = currentTime;
            WenShuFile wenShuFile = wenShuFileList.get(wenshuGroupPosition);
            List<WenShuFile.WS> wslist = wenShuFile.getWslist();

            WenShuFile.WS ws = wenShuFileList.get(wenshuGroupPosition).getWslist().get(wenshuChildPosition);
            String file_no = ws.getFILE_NO();
            String creator_id = ws.getCREATOR_ID();
            String mr_code = ws.getMR_CODE();
            if (wenshuChildPosition + 1 >= wslist.size()) {
                if (wenshuGroupPosition + 1 < wenShuFileList.size()) {
                    wenshuGroupPosition = wenshuGroupPosition + 1;
                    wenshuChildPosition = 0;
                    wenShuFragment.getFileContent(zhuYuanHao, file_no, creator_id, mr_code,ws.getTOPIC(),ws.getMR_CLASS());
                    mAdapter.setCheckState(wenshuGroupPosition, wenshuChildPosition);
                }
            } else {
                wenshuChildPosition = wenshuChildPosition + 1;
                wenShuFragment.getFileContent(zhuYuanHao, file_no, creator_id, mr_code,ws.getTOPIC(),ws.getMR_CLASS());
                mAdapter.setCheckState(wenshuGroupPosition, wenshuChildPosition);
            }
        }
    }

    /*报告的左侧菜单栏*/
    private void initBaoGao() {
        final BaoGaoFragment fragment = (BaoGaoFragment) fragments[3];
        /*这里*/
        baogaoAdapter = new SimpleExpandableListAdapter(
                this,
                BGgroupData,
                R.layout.simple_expandable_list_item_1,
                new String[]{NAME, IS_EVEN},
                new int[]{android.R.id.text1, android.R.id.text2},
                BGchildData,
                R.layout.jianyan_child_item,
                new String[]{NAME, IS_EVEN},
                new int[]{R.id.childBox}
        );
        jianyanListView.setGroupIndicator(getResources().getDrawable(R.drawable.group_indicator));
        jianyanListView.setAdapter(baogaoAdapter);
        baogaoAdapter.setOnItemCheckedListener(new SimpleExpandableListAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(View view, int groupPosition, int childPosition) {
                JianYanFile jianYanFile = jianYanFiles.get(groupPosition);
                List<JianYanReport> list = jianYanFile.getList();
                JianYanReport jianYanReport = list.get(childPosition);
                String sqdh = jianYanReport.getSqdh();
                if (groupPosition == 0) {
                    fragment.getLisItemInfoDAL(sqdh);
                } else {
                    fragment.getGermItemInfoDAL();
                }
                baogaoAdapter.setCheckState(groupPosition, childPosition);
            }
        });

       // baogaoAdapter.refreshData();

        adapter = new JianChaListAdapter(this, jianChaInfos);
        jiancha_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        jiancha_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new JianChaListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String exam_no = jianChaInfos.get(position).getEXAM_NO();
                String exam_class = jianChaInfos.get(position).getEXAM_CLASS();
                fragment.getCheckDescriptionDAL(exam_no,exam_class,zhuYuanHao);
                for (int i = 0; i < jianChaInfos.size(); i++) {
                    if (i == position) {
                        jianChaInfos.get(i).setChecked(true);
                    } else {
                        jianChaInfos.get(i).setChecked(false);
                    }
                }
                adapter.refreshData(jianChaInfos);
            }
        });
        cb_jy_mcpx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLisInfoDAL(blh,rysj);
            }
        });
        cb_jy_sjpx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getLisInfoDAL(blh,rysj);
            }
        });

        baogao_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.baogao_rb_jiancha:
                        jianyanListView.setVisibility(View.GONE);
                        jiancha_recycler.setVisibility(View.VISIBLE);
                        fragment.showJianCha();
                        rl_jy_pxfs.setVisibility(View.GONE);
                        getCheckInfoDAL(blh );
                        break;
                    case R.id.baogao_rb_jianyan:
                        jianyanListView.setVisibility(View.VISIBLE);
                        jiancha_recycler.setVisibility(View.GONE);
                        fragment.showJianyan();
                        rl_jy_pxfs.setVisibility(View.VISIBLE);
                        getLisInfoDAL(blh, rysj);
                        break;
                }
            }
        });
    }

    /*我的的左侧菜单栏*/
    private void initMessage() {
        beiwangDate = activityUtils.getYMD();
        bw_tv_date.setText(beiwangDate);
        bw_tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_clear.setChecked(true);
                showDateDialog(MainActivity.this, bw_tv_date);
            }
        });
        final MsgFragment fragment = (MsgFragment) fragments[4];
        bwTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_clear.setChecked(true);
                fragment.getYyyzList(myCZYDM, zhuYuanHao, beiwangDate);
            }
        });
        message_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.menu_rb_unread:
                     //   fragment.getMessageList(myCZYDM, "0");
                        break;
                    case R.id.menu_rb_readed:
                       // fragment.getMessageList(myCZYDM, "1");
                        break;
                }
            }
        });
        cbSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_clear.setChecked(true);
                Date before = Common.getDateAfterDays(new Date(System.currentTimeMillis()), -1);
                Date after = Common.getDateAfterDays(new Date(System.currentTimeMillis()), 1);
                fragment.getAttendsList(myCZYDM, Common.formatDateToYMD(before), Common.formatDateToYMD(after), false);

            }
        });
        tv_zhibiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_clear.setChecked(true);
                fragment.getJxzbList(myCZYDM);
            }
        });
    }

    /*功能导航栏切换fragment*/
    public void onTabClick(View view) {
        if (null == refreshPool) {
           // initPool();
        } else {
            if (!refreshPool.isRefreshing) {
                refreshPool.startRefresh();
            }
        }
        switch (view.getId()) {

            case R.id.nav_iv_home:
                if (isCardSelected) {
                    if (isCardTrans) {
                        HomeFragment homeFragment = (HomeFragment) fragments[0];
                        homeFragment.resetHomeSelectedItem(mainPatientInfors);
                        homeFragment.scrollToSelectedPosition(cardPosition);
                    }
                    //如果备忘改变了,重新刷新首页
                    if(isChangeBeiWang) {
                        HomeFragment homeFragment = (HomeFragment) fragments[0];
                        homeFragment.getPatientList("", "", "", mKSDM, "", "", true, false, "1", "50");
                        isChangeBeiWang = false;
                    }
                    index = 0;
                    setNavTrans(index);
                } else {
                    if (null != alertDialog && !alertDialog.isShowing()) {
                        alertDialog.show();
                    }
                }
                break;
            case R.id.nav_iv_yizhu:
                SimplefyTrans(1);
                break;
            case R.id.nav_iv_wenshu:
                SimplefyTrans(2);
                break;
            case R.id.nav_iv_baogao:
                SimplefyTrans(3);
                break;
            case R.id.nav_iv_my:
                SimplefyTrans(4);
                break;
        }
        if (isCardSelected) {
            if (currentTabIndex != index) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(fragments[currentTabIndex]);
                if (!fragments[index].isAdded()) {
                    trx.add(R.id.frag_container, fragments[index]);
                }
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        yiZhuFragment = (YiZhuFragment) fragments[index];
                        int flag = menu_rb_longyizhu.isChecked() ? 1 : 0;
                        yiZhuFragment.gettDocOrderInfoByZyh(zhuYuanHao, flag, yizhutype, false);
                        yiZhuFragment.get2KSDM(mKSDM, ksdmFromPatient);
                        break;
                    case 2:
                        wenShuFragment = (WenShuFragment) fragments[index];
                        wenShuFragment.clearText();
                        getFileList(zhuYuanHao);
                        String[] strings = Common.initQueryDate(rysj);
                        if (homeCardChanged) {
                            rb_twd.setChecked(true);
                            wenShuFragment.getTwdInfo(zhuYuanHao, strings[0], strings[1], rysj);
                        }
                        break;
                    case 3:
                        baoGaoFragment = (BaoGaoFragment) fragments[index];
                        baoGaoFragment.clearConttent();
                        if (baogao_rb_jianyan.isChecked()) {
                            jianyanListView.setVisibility(View.VISIBLE);
                            jiancha_recycler.setVisibility(View.GONE);
                            baoGaoFragment.showJianyan();
                            rl_jy_pxfs.setVisibility(View.VISIBLE);
                            getLisInfoDAL(blh, rysj);
                        }
                        if (baogao_rb_jiancha.isChecked()) {
                            jianyanListView.setVisibility(View.GONE);
                            jiancha_recycler.setVisibility(View.VISIBLE);
                            baoGaoFragment.showJianCha();
                            rl_jy_pxfs.setVisibility(View.GONE);
                            getCheckInfoDAL(blh);
                        }
                        break;
                    case 4:
                        MsgFragment message = (MsgFragment) fragments[index];
                        message.getYyyzList(myCZYDM, zhuYuanHao, beiwangDate);
                        break;
                }
                trx.show(fragments[index]).commit();
            }
            currentTabIndex = index;
        }
    }

    /*切换时的重复代码*/
    private void SimplefyTrans(int position) {
        if (isCardSelected) {
            index = position;
            setNavTrans(index);
        } else {
            if (null != alertDialog && !alertDialog.isShowing()) {
                alertDialog.show();
            }
        }
    }

    /*导航栏的图标切换*/
    private void setNavTrans(int index) {
        for (int i = 0; i < menus.size(); i++) {
            ViewGroup linearLayout = menus.get(i);
            ImageView imageView = navIcons.get(i);
            if (i == index) {
                imageView.setSelected(true);
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                imageView.setSelected(false);
                linearLayout.setVisibility(View.GONE);
            }
        }
        if (null != fragments && index == fragments.length - 1) {
            iv_beiwang.setVisibility(View.GONE);
        } else {
            iv_beiwang.setVisibility(View.GONE);
        }
    }

    /*检验二级菜单的接口及加载数据*/
    private void getLisInfoDAL(String zyh, String rysj) {
        if (null != progressDialog && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("rysj", rysj);
        params.put("sjpx",cb_jy_sjpx.isChecked()?"1":"0");
        params.put("mcpx",cb_jy_mcpx.isChecked()?"1":"0");
        Log.e(TAG, "getLisInfoDAL:111 "+zyh+rysj );
        String url = Constant.BaseURL + "/GetLisInfoDAL";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        activityUtils.showToast(Common.getException(e));
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        jianYanFiles.clear();
                        BGchildData.clear();
                        BGgroupData.clear();
                        jianYanReports.clear();
                        try {
                            JianYanFile jianyan = new JianYanFile();
                            jianyan.setName("检验项目");
//                            JianYanFile xijun = new JianYanFile();
//                            xijun.setName("细菌项目");
                            JSONArray array = new JSONArray(response);
                            List<JianYanReport> jianyanlist = new ArrayList<>();
                            List<JianYanReport> xijunlist = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String zhuyuanhao = jsonObject.getString("zyh");
                                String sqdh = jsonObject.getString("sqdh");
                                String bbh = jsonObject.getString("bbh");
                                String mc = jsonObject.getString("mc");
                                String jcxmmc = jsonObject.getString("jcxmmc");
                                String jgsj = jsonObject.getString("jgsj");
                                String sqks = jsonObject.getString("sqks");
                                String sbh = jsonObject.getString("sbh");
                                String xjbz = jsonObject.getString("xjbz");

//                                String zhuyuanhao = jsonObject.getString("ZYH");
//                                String sqdh = jsonObject.getString("SQDH");
//                                String bbh = jsonObject.getString("BBH");
//                                String mc = jsonObject.getString("MC");
//                                String jcxmmc = jsonObject.getString("JCXMMC");
//                                String jgsj = jsonObject.getString("JGSJ");
//                                String sqks = jsonObject.getString("SQKS");
//                                String sbh = jsonObject.getString("SBH");
//                                String xjbz = jsonObject.getString("XJBZ");

                                JianYanReport jianYanReport = new JianYanReport();
                                jianYanReport.setZyh(zhuyuanhao);
                                jianYanReport.setSqdh(sqdh);
                                jianYanReport.setBbh(bbh);
                                jianYanReport.setMc(mc);
                                jianYanReport.setJcxmmc(jcxmmc);
                                jianYanReport.setJgsj(jgsj);
                                jianYanReport.setSqks(sqks);
                                jianYanReport.setSbh(sbh);
                                jianYanReport.setXjbz(xjbz);
                                if (xjbz.equals("检验") || xjbz.equals("0")) {
                                    jianyanlist.add(jianYanReport);
                                } else if (xjbz.equals("细菌")) {
                                    xijunlist.add(jianYanReport);
                                }
                            }
                            jianyan.setList(jianyanlist);
//                            xijun.setList(xijunlist);
                            jianYanFiles.add(jianyan);
//                            jianYanFiles.add(xijun);

                            /*二级列表*/
                            for (int i = 0; i < jianYanFiles.size(); i++) {
                                Log.e(TAG, "onResponse: "+jianYanFiles.size() );
                                JianYanFile jianYanFile = jianYanFiles.get(i);
                                String name = jianYanFile.getName();
                                List<JianYanReport> list = jianYanFile.getList();
                                Map<String, String> curGroupMap = new HashMap<>();
                                BGgroupData.add(curGroupMap);
                                curGroupMap.put(NAME, name);
                                curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
                                List<Map<String, String>> children = new ArrayList<>();
                                for (int j = 0; j < list.size(); j++) {
                                    JianYanReport jianYanReport = list.get(j);
                                    String jcxmmc = jianYanReport.getJcxmmc();
                                    String mc = jianYanReport.getMc();
                                    String jgsj = jianYanReport.getJgsj();
                                    Map<String, String> curChildMap = new HashMap<>();
                                    children.add(curChildMap);
                                    curChildMap.put(NAME, jcxmmc + "(" + mc.trim() + ")");
                                    curChildMap.put(IS_EVEN, jgsj);
                                }
                                BGchildData.add(children);
                            }
                            baogaoAdapter.refreshData(BGgroupData, BGchildData);
                            jianyanListView.expandGroup(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /*检查的接口及加载数据*/
    private void getCheckInfoDAL(String zyh) {
        Log.e(TAG, "reloadInfors: "+blh );
        if (null != progressDialog && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        String url = Constant.BaseURL + "/GetCheckInfoDAL";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Log.e("wcx", "onResponse: "+response );
                        try {
                            jianChaInfos.clear();
                            Gson gson = new Gson();
                            jianChaInfos = gson.fromJson(response, new TypeToken<List<JianChaInfo>>() {
                            }.getType());
                            Log.e("wcx", "onResponse: "+jianChaInfos.size() );
                            adapter.refreshData(jianChaInfos);
                        }catch (Exception e){
                            activityUtils.showToast("数据解析错误");
                        }
                    }
                });
    }

    /*登出*/
    private void logout(final String czydm, final boolean tologin) {
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("pwd", "");
        params.put("padip", "");
        params.put("dcbz", "1");
        String url = Constant.BaseURL + "/LoginHandle";
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
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject o = (JSONObject) jsonArray.get(0);
                            String response1 = o.getString("response");
                            if (response1.equals("true")) {
                                if (tologin) {
                                    Intent intent = new Intent(MainActivity.this, NewLoginActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                String bz = o.getString("bz");
                                activityUtils.showToast(bz);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*时间选择器*/
    private void showDateDialog(Activity activity, final TextView tv) {
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                beiwangDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                MsgFragment fragment = (MsgFragment) fragments[index];
                fragment.getYyyzList(myCZYDM, zhuYuanHao, beiwangDate);
            }
        }, year, month, day);
        datePickerDialog.setMessage("请选择日期");
        datePickerDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*获取打卡状态*/
    private void getMessageList(String czydm) {
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("ydbz", "0");
        String url = Constant.BaseURL + "/GetMessageList";
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
                        messageInfos.clear();
                        Gson gson = new Gson();
                        messageInfos = gson.fromJson(response, new TypeToken<List<MessageInfo>>() {
                        }.getType());
                        if (!messageInfos.isEmpty()) {
                            /*!newMsgDialog.isShowing()*/
                            if (!isReading) {
                                if (playBeep && mediaPlayer != null) {
                                    mediaPlayer.start();
                                }
                                String msg_mc = messageInfos.get(0).getMSG_MC();
                                TopSnackBar.make(mainLayout, msg_mc, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("查看", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MessageInfo messageInfo = messageInfos.get(0);
                                        String msg_url = messageInfo.getMSG_URL();
                                        @SuppressLint("InflateParams") View webLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_msg_details, null);
                                        WebView webView = webLayout.findViewById(R.id.detail_webview);
                                        if (null == msg_url || TextUtils.isEmpty(msg_url))
                                            return;
                                        webView.loadUrl(msg_url);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        AlertDialog a = builder.create();
                                        String msg_id = messageInfo.getMSG_ID();
                                        updateMsgStatus(myCZYDM, msg_id);
                                        a.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                isReading = true;
                                            }
                                        });
                                        a.show();
                                        //noinspection ConstantConditions
                                        a.getWindow().setContentView(webLayout);
                                    }
                                }).show();
                            }
                        }
                    }
                });
    }

    /*更新消息阅读状态*/
    private void updateMsgStatus(String czydm, String msgid) {
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

    /*初始化轮询*/
    private void initPool() {
        if (null == refreshPool) {
            refreshPool = new BaseAsyncRefreshPool2<String>(tempText, 0, 5000) {
                @Override
                protected String getData() {
                    getMessageList(myCZYDM);
                    return "a";
                }

                @Override
                protected void setData(String data) {
                    tempText.setText(data);
                }

            };
        }
    }

    /*图文备忘弹出框*/
    private void showTWBeiWang() {
        @SuppressLint("InflateParams") View tuwenView = LayoutInflater.from(this).inflate(R.layout.bw_tuwen_layout, null);
        final EditText et_topic1 = tuwenView.findViewById(R.id.note_title);
        final EditText et_content = tuwenView.findViewById(R.id.note_content);
        ImageView iv_addphoto = tuwenView.findViewById(R.id.bw_iv_addPhoto);
        bwImage = tuwenView.findViewById(R.id.note_iv_phote);
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
                    switch (noteType) {
                        case "tt":
                            recorderUpload(myCZYDM, zhuYuanHao, time + title, content, "", "tt");
                            break;
                        case "tp":
                            recorderUpload(myCZYDM, zhuYuanHao, time + title, content, Common.imageToBase64(photoLocation), "tp");
                            break;
                    }
                }
               setChangeBeiWang(true);
            }
        });
        popupWindow = new PopupWindow(tuwenView, (int) (260 * Common.density), (int) (460 * Common.density));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(tuwenView, Gravity.END, 0, 400);
    }

    /*图片返回*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                noteType = "tp";
                final Bitmap photo = data.getParcelableExtra("data");
                Drawable drawable = new BitmapDrawable(photo);
                bwImage.setBackground(drawable);
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
            } else if (resultCode == 100) {
                String qrMsg = data.getStringExtra("QRMSG");
                HomeFragment fragment = (HomeFragment) fragments[0];
                PatientInfo patientInfo = fragment.pickPatientFromQRCode(qrMsg);
                if (null != patientInfo) {
                    updateCardInfor(patientInfo);
                    reloadInfors();
                } else {
                    activityUtils.showToast("该科室未找到此患者");
                }
            } else {
                activityUtils.showToast("拍照未成功！");
            }
        }
    }

    /*上传备忘*/
    private void recorderUpload(final String czydm, final String zyh, String filename, String yymc, String yynr, final String yylx) {
//        Log.e("WQ", "上传的参数==" + czydm + " " + zyh + " " + filename + " " + yymc + " " + yynr + " " + yylx);
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("zyh", zyh);
        params.put("filename", filename);
        params.put("yymc", yymc);
        params.put("yynr", yynr);
        params.put("yylx", yylx);
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
                                if (null != jiluDialog && jiluDialog.isShowing()) {
                                    jiluDialog.dismiss();
                                }
                                if (null != popupWindow && popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                }
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //展开和关闭动画
    private void animeOpen(View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mHiddenViewMeasuredHeight);
        animator.start();
    }

    /*访谈记录弹出框*/
    @SuppressWarnings("ConstantConditions")
    private void setFTJL() {
        @SuppressLint("InflateParams") View audioViewOrange = LayoutInflater.from(this).inflate(R.layout.dialog_audio_orange, null);
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
                    String time = Common.getTime();
                    recorderUpload(myCZYDM, zhuYuanHao, Common.stringToUnicode(time + titile), "", yuyin, "yy");
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
                    String ymd = Common.getYMD();
                    et_topic.setVisibility(View.VISIBLE);
                    et_topic.setText(ymd);
                    view.setVisibility(View.VISIBLE);
                    iv_confirm.setVisibility(View.VISIBLE);
                    iv_record.setVisibility(View.GONE);
                }
            }
        });
        jiluDialog = new AlertDialog.Builder(this).create();
        jiluDialog.setView(new EditText(this));
        jiluDialog.show();
        jiluDialog.getWindow().setContentView(audioViewOrange);
        jiluDialog.setCanceledOnTouchOutside(false);
    }

    /*动画关闭患者列表*/
    private void animateClose(final View view) {
        int height = view.getHeight();
        ValueAnimator dropAnimator = createDropAnimator(view, height, 0);
        dropAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        dropAnimator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        //创建一个数值发生器
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /*打卡弹出框*/
    public void initDakaDialog(final boolean enable) {
        View dakaView = LayoutInflater.from(this).inflate(R.layout.daka_dialog, null);
        TextView dakaTv = dakaView.findViewById(R.id.dakaTv);
        CheckBox cb_morning = dakaView.findViewById(R.id.daka_cb_morning);
        CheckBox cb_noon = dakaView.findViewById(R.id.daka_cb_noon);
        CheckBox cb_afternoon = dakaView.findViewById(R.id.daka_cb_afternoon);
        cb_morning.setEnabled(enable);
        cb_noon.setEnabled(enable);
        cb_afternoon.setEnabled(enable);
        cb_morning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                swbz = isChecked ? "1" : "0";
            }
        });
        cb_noon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                xwbz = isChecked ? "1" : "0";
            }
        });
        cb_afternoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wsbz = isChecked ? "1" : "0";
            }
        });
        final AlertDialog dakaDialog = new AlertDialog.Builder(this).create();
        dakaDialog.setView(new EditText(this));
        dakaDialog.show();
        dakaDialog.getWindow().setContentView(dakaView);
        WindowManager.LayoutParams attributes = dakaDialog.getWindow().getAttributes();
        attributes.width = (int) (280 * Common.density);
        attributes.height = (int) (160 * Common.density);
        dakaDialog.getWindow().setAttributes(attributes);
        dakaDialog.setCanceledOnTouchOutside(true);
        dakaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enable) {
                    activityUtils.showToast("当日已签到！");
                } else {
                    attendenceSubmit(myCZYDM, Common.getCurrentTime(), swbz, xwbz, wsbz, dakaDialog);
                }
            }
        });
    }

    /*考勤提交*/
    private void attendenceSubmit(String ysdm, String kqsj, String swbz, String xwbz, String wsbz, final Dialog dialog) {
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
//                            Log.e("WQ", "提交" + response1 + " " + bz);
                            if (response1.equals("true")) {
                                activityUtils.showToast(bz);
                                dialog.dismiss();
                            } else {
                                activityUtils.showToast(bz);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*获取考勤列表*/
    private void getAttendsList(String czydm, String qssj, String zzsj) {
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
                        try {
                            JSONArray array = new JSONArray(response);
                            if (array.length() > 0) {
                                initDakaDialog(false);
                            } else {
                                initDakaDialog(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*操作员密码修改*/
    private void updateUserPwd(String czydm, String newpwd, final AlertDialog dialog) {
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("newpwd", newpwd);
        String url = Constant.BaseURL + "/UpdateUserPwd";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                dialog.dismiss();
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /*通知音*/
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.message);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }

    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else { // 两次按键小于2秒时，退出应用
                logout(myCZYDM, false);
                appManager.clear();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
//是否改变了备忘
    public void setChangeBeiWang(boolean changeBeiWang) {
        isChangeBeiWang = changeBeiWang;

    }
    //button使用的点击去除软键盘事件
    public static void closeKeybord(Button mButton, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mButton.getWindowToken(), 0);
    }
    //recyclerView使用的点击除去软键盘事件
    public static void closeKeybord(RecyclerView recyclerView, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
    }
    //GridView使用的点击除去软键盘事件
    public static void closeKeybord(GridView GridView, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(GridView.getWindowToken(), 0);
    }
}
