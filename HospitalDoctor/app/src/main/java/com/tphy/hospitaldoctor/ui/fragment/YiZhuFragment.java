package com.tphy.hospitaldoctor.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.bjca.CAContant;
import com.tphy.hospitaldoctor.bjca.CARequestUtils;
import com.tphy.hospitaldoctor.bjca.CAResAddSignJob;
import com.tphy.hospitaldoctor.bjca.StringUtils;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.base.MainApplication;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.ui.activity.MainActivity;
import com.tphy.hospitaldoctor.ui.adapter.DesageListAdapter;
import com.tphy.hospitaldoctor.ui.adapter.DrugAdapter;
import com.tphy.hospitaldoctor.ui.adapter.FrequencyListAdapter;
import com.tphy.hospitaldoctor.ui.adapter.HerbalDrugNameAdapter;
import com.tphy.hospitaldoctor.ui.adapter.NewYiZhuAdapter;
import com.tphy.hospitaldoctor.ui.adapter.PatientRecipleAdapter;
import com.tphy.hospitaldoctor.ui.adapter.RecipelChildAdapter;
import com.tphy.hospitaldoctor.ui.adapter.SavedDrugRecyclerAdapter;
import com.tphy.hospitaldoctor.ui.adapter.SimpleExpandableListAdapter;
import com.tphy.hospitaldoctor.ui.adapter.TaoYizhuAdapter;
import com.tphy.hospitaldoctor.ui.adapter.YzbgItemAdapter;
import com.tphy.hospitaldoctor.ui.adapter.UsageListAdapter;
import com.tphy.hospitaldoctor.ui.bean.Brewway;
import com.tphy.hospitaldoctor.ui.bean.CaDoBean;
import com.tphy.hospitaldoctor.ui.bean.CommonDrugChild;
import com.tphy.hospitaldoctor.ui.bean.CommonDrugFather;
import com.tphy.hospitaldoctor.ui.bean.DaiJianFangAn;
import com.tphy.hospitaldoctor.ui.bean.DesageDict;
import com.tphy.hospitaldoctor.ui.bean.DrugCodeInfor;
import com.tphy.hospitaldoctor.ui.bean.FreqencyDict;
import com.tphy.hospitaldoctor.ui.bean.HerbalChildJson;
import com.tphy.hospitaldoctor.ui.bean.HerbalDrugName;
import com.tphy.hospitaldoctor.ui.bean.HerbalDrugOthers;
import com.tphy.hospitaldoctor.ui.bean.HerbalFatherJson;
import com.tphy.hospitaldoctor.ui.bean.JianZhuJiLiang;
import com.tphy.hospitaldoctor.ui.bean.KuFang;
import com.tphy.hospitaldoctor.ui.bean.PatientRecipel;
import com.tphy.hospitaldoctor.ui.bean.PatientRecipelDetails;
import com.tphy.hospitaldoctor.ui.bean.RecipelDetailsAdapter;
import com.tphy.hospitaldoctor.ui.bean.Require;
import com.tphy.hospitaldoctor.ui.bean.SavedYizhu;
import com.tphy.hospitaldoctor.ui.bean.TaoYiZhuJson;
import com.tphy.hospitaldoctor.ui.bean.TaoyizhuBean;
import com.tphy.hospitaldoctor.ui.bean.TaoyizhuType;
import com.tphy.hospitaldoctor.ui.bean.UsageDict;
import com.tphy.hospitaldoctor.ui.bean.YiZhuBean;
import com.tphy.hospitaldoctor.ui.bean.YiZhuJson;
import com.tphy.hospitaldoctor.ui.bean.YiZhuSelected;
import com.tphy.hospitaldoctor.ui.dao.CASignUserStatus;
import com.tphy.hospitaldoctor.ui.dao.CASignUserStatusDao;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.DateTimePickerNew;
import com.tphy.hospitaldoctor.utils.FileUtils;
import com.tphy.hospitaldoctor.utils.GreenDaoManager;
import com.tphy.hospitaldoctor.utils.Mypopmenu;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.hospitaldoctor.views.top_snackbar.BaseTransientBottomBar;
import com.tphy.hospitaldoctor.views.top_snackbar.TopSnackBar;
import com.tphy.hospitaldoctor.widget.MyItemDecoration;
import com.tphy.http.okhttp.OkHttpUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.org.bjca.signet.coss.api.SignetCossApi;
import cn.org.bjca.signet.coss.bean.CossSignPinResult;
import cn.org.bjca.signet.coss.interfaces.CossSignPinCallBack;
import okhttp3.Call;

import static com.tphy.hospitaldoctor.common.config.Constant.isSavedYizhuEmpty;

public class YiZhuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

//    Context context;
    @BindView(R.id.yizhu_layout)
    RelativeLayout yizhu_layout;
    @BindView(R.id.yizhu_iv_new)
    ImageView iv_new;
    @BindView(R.id.yizhu_iv_bind)
    ImageView iv_bind;
    @BindView(R.id.yizhu_iv_unbind)
    ImageView iv_unbind;
    @BindView(R.id.yizhu_iv_chongzheng)
    ImageView iv_chongzheng;
    @BindView(R.id.yizhu_iv_shanchu)
    ImageView iv_shanchu;
    @BindView(R.id.yizhu_list)
    ListView yizhuListView;
    @BindView(R.id.yizhu_iv_commit)
    ImageView iv_commit;
    @BindView(R.id.yizhu_tv_title)
    TextView yizhu_tv_title;
    @BindView(R.id.yizhu_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.header_yizhu)
    LinearLayout yizhuHeader;
    @BindDrawable(R.mipmap.ic_commit)
    Drawable ic_commit;
    @BindView(R.id.yizhu_header_check)
    CheckBox header_check;
    @BindDrawable(R.mipmap.ic_reset_invalid)
    Drawable ic_reset_invalid;
    @BindDrawable(R.mipmap.ic_reset)
    Drawable ic_reset;
    @BindView(R.id.yizhu_iv_cado)
    TextView yizhu_iv_cado;
    @BindView(R.id.item_tv_nurCa)
    TextView item_tv_nurCa;

    //recycleViewList的频率适配器
    private FrequencyListAdapter frequencyListAdapter;
    //recycleViewList的方法适配器
    private UsageListAdapter usageListAdapter;
    //剂量的适配器
    private DesageListAdapter desageListAdapter;
    private List<YiZhuBean> yiZhuBeanList;
    private String zyh;
    private String sjbz;
    private int flag = -1;
    private List<DrugCodeInfor> drugCodeInfors;
    private List<SavedYizhu> newYizhus;
    private List<YiZhuJson> yiZhuJsons;
    /*录入的*/
    private List<String> types;
    private List<String> typeCodes;
    private List<UsageDict> usageDicts;
    private List<DesageDict> desageDicts;
    private List<FreqencyDict> freqencyDicts;
    private ActivityUtils activityUtils;
    private String dm = "";
    private String type;
    private String docKSDM = "";
    private String patientKSDM = "";
    private AlertDialog alertDialog;
    private String yzdm;
    private String dw;
    private String dw2;
    private String yfDM="";
    private String plDM="";
    private String zbybz = "0";
    private String YBBZ = "";
    private String psjg = "";
    private String isLong;
    private ProgressDialog saveDialog;
    private NewYiZhuAdapter newYiZhuAdapter;
    private ProgressDialog progressDialog;
    private boolean kucunEnable = true;
    //    private SavedDrugAdapter savedDrugAdapter;
    private SavedDrugRecyclerAdapter savedRecyclerAdapter;
    private PatientRecipleAdapter patientRecipleAdapter;
    private String[] plItems;
    private String cs ="1";
    private final String[] typeCode = new String[]{"1", "2", "3", "5", "6", "7", "8"};
    private String typeChosen = "";
    private String stateChosen = "";
    //    private boolean groupEnabled = false;
//    private boolean isAllLoad;
    private List<CommonDrugFather> commonDrugFathers;
    private List<CommonDrugChild> commonDrugChildList;

    /*中草药控件*/
    private ListView herbal_cfList;
    private String[] herbalRanges = new String[]{"0", "1", "2"};
    private RecipelChildAdapter recipelChildAdapter;
    private List<KuFang> kuFangs;
    private int recipelFatherPosition;
    private String yfdm;
    private String newRecipelNo = "";
    private List<Brewway> brewways;
    private List<Require> requires;
    private String takeUsageNo;
    private String brewayNo;
    private String herbalFrequencyCode;
    private String herbalWaysCode;
    private String[] yfItems;
    private EditText et_jl;
    private EditText et_bz;
    private Spinner sp_pl;
    private Spinner sp_gyfs;
    private Spinner sp_ayfs;
    private Spinner sp_fyyq;
    private Spinner sp_djfa;
    private Spinner sp_jzjl;
    private EditText et_sl;
    private EditText et_zhutuo;
    private List<PatientRecipelDetails> patientRecipelDetailsList;
    private List<PatientRecipel> patientRecipels;
    private TextView tv_ypmc;
    private TextView tv_jldw;
    private TextView tv_kcts;
    private String[] brewaysNames;
    private String[] requireNames;
    private TextView tv_qyyf;
    private TextView tv_sldw;
    private TextView herbal_tv_cfdh;
    //新增
    private HerbalDrugNameAdapter herbalDrugNameAdapter;
    private PatientRecipelDetails newPatientRecipelDetails;
    private List<HerbalDrugName> herbalDrugNameList;
    private List<HerbalDrugOthers> herbalDrugOthersList;
    private LinearLayout layout_djfa;
    private LinearLayout layout_jzjl;
    private List<DaiJianFangAn> daiJianFangAnList;
    private List<JianZhuJiLiang> jianZhuJiLiangList;
    private String[] daiJianFangAnNames;
    private String[] jianZhuJiLiangNames;


    private String kfmc;
    private int herbalFatherPosition;
    private int herbalChildPosition;
    private String savedHerbalDH;
    private RecipelDetailsAdapter recipelDetailsAdapter;
    private ListView herbal_pop_fatherList;
    private int herbalTypePosition=2;
//    private boolean isHerbalChecked = false;

    /*套医嘱*/
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";
    private List<List<Map<String, String>>> childData;
    private List<Map<String, String>> groupData;
    private List<TaoyizhuType> taoyizhuTypeList;
    private SimpleExpandableListAdapter mAdapter;
    private List<TaoyizhuBean> taoyizhuBeans;
    private TaoYizhuAdapter taoYizhuAdapter;
    private String groupMaCode = "";
    private String qyyfdm;
    private  SimpleDateFormat sdf ;
    private List<CaDoBean> ltemps;
    private   GridLayoutManager gridLayoutManager;
    @Override
    protected int getLayoutID() {
        return R.layout.frag_yizhu;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        /*医嘱列表时间*/
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        newYiZhuAdapter = new NewYiZhuAdapter(context, yiZhuBeanList);
        yizhuListView.setEmptyView(view.findViewById(R.id.iv_empty));
        yizhuListView.setAdapter(newYiZhuAdapter);
        newYiZhuAdapter.setOnItemClickListener(new NewYiZhuAdapter.OnItemClickListener() {
            @Override
            public void onclick(View view, int position) {
            }
        });

        newYiZhuAdapter.setEditListener(new NewYiZhuAdapter.EditListener() {
            @Override
            public void edit(View view, int position, int type) {
                if (type == 0) {
                    activityUtils.showToast("此条医嘱不可操作！");
                } else {
                    dealwithItem(position, type);
                }
            }
        });
        progressDialog = new ProgressDialog(context);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        getMaTypes();
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        yiZhuBeanList = new ArrayList<>();
        drugCodeInfors = new ArrayList<>();
        types = new ArrayList<>();
        typeCodes = new ArrayList<>();
        usageDicts = new ArrayList<>();
        freqencyDicts = new ArrayList<>();
        desageDicts = new ArrayList<>();
        activityUtils = new ActivityUtils(this);
        newYizhus = new ArrayList<>();
        yiZhuJsons = new ArrayList<>();
        /*中草药*/
        commonDrugFathers = new ArrayList<>();
        commonDrugChildList = new ArrayList<>();
        kuFangs = new ArrayList<>();
        brewways = new ArrayList<>();
        requires = new ArrayList<>();
        patientRecipelDetailsList = new ArrayList<>();
        herbalDrugNameList = new ArrayList<>();
        herbalDrugOthersList = new ArrayList<>();
        daiJianFangAnList  = new ArrayList<>();
        jianZhuJiLiangList = new ArrayList<>();
        patientRecipels = new ArrayList<>();
        /*套医嘱*/
        groupData = new ArrayList<>();
        childData = new ArrayList<>();
        taoyizhuTypeList = new ArrayList<>();
        taoyizhuBeans = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    @OnClick({R.id.yizhu_iv_commit, R.id.yizhu_iv_new, R.id.yizhu_iv_unbind, R.id.yizhu_iv_bind, R.id.yizhu_iv_chongzheng, R.id.yizhu_iv_herbal
            , R.id.yizhu_tv_yaopin, R.id.yizhu_tv_shoushu, R.id.yizhu_tv_jiancha, R.id.yizhu_tv_huli, R.id.yizhu_tv_fuzhu, R.id.yizhu_tv_qita,
            R.id.yizhu_tv_zhiliao, R.id.yizhu_tv_reset, R.id.yizhu_iv_tao,R.id.yizhu_iv_shanchu,R.id.yizhu_iv_cado})
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getHoldingActivity();
        if (activity.getCybz().equals("已出院")) {
            activityUtils.showToast("该病人已出院");
            return;
        }
        switch (v.getId()) {
            default:
                break;
            case R.id.yizhu_iv_commit:
                commitYiZhu();
                break;
            case R.id.yizhu_iv_new:
                /*判断当前患者是否可以下医嘱*/
                if (patientKSDM.equals(docKSDM) && (!docKSDM.equals(""))) {
                    String msg;
                    if (isLong.equals("true")) {
                        msg = "将开立长期医嘱";
                    } else {
                        msg = "将开立临时医嘱";
                    }
                    TopSnackBar.make(yizhu_layout, msg, BaseTransientBottomBar.LENGTH_SHORT).show();
                    initLuruPopWindow();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("不能为当前患者下医嘱");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    if(alertDialog != null ) {
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                break;
            case R.id.yizhu_iv_herbal:
                if (patientKSDM.equals(docKSDM) && (!docKSDM.equals(""))) {
                    initHerbalWindow();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("不能为当前患者下医嘱");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    if(alertDialog != null ){
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                break;
            case R.id.yizhu_iv_cado:
                prepareCaData();
                break;
            case R.id.yizhu_iv_bind:
                if (header_check.getVisibility() == View.GONE) {
                    header_check.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < yiZhuBeanList.size(); i++) {
                        yiZhuBeanList.get(i).setShow(true);
                    }
                    newYiZhuAdapter.refreshData(yiZhuBeanList);
                } else if (header_check.getVisibility() == View.INVISIBLE) {
                    List<YiZhuBean> checkedList = newYiZhuAdapter.getCheckedList();
                    dealWithBindingYizhu(checkedList);
                }
                break;
            case R.id.yizhu_iv_unbind:
                if (header_check.getVisibility() == View.GONE) {
                    header_check.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < yiZhuBeanList.size(); i++) {
                        yiZhuBeanList.get(i).setShow(true);
                    }
                    newYiZhuAdapter.refreshData(yiZhuBeanList);
                } else if (header_check.getVisibility() == View.INVISIBLE) {
                    List<YiZhuBean> checkedList = newYiZhuAdapter.getCheckedList();
                    dealwithUnbindingYizhu(checkedList);
                }
                break;
            case R.id.yizhu_iv_chongzheng:
                AlertDialog.Builder resetBuilder = new AlertDialog.Builder(context);
                resetBuilder.setMessage("此操作不可逆，重整后当前所有执行状态的医嘱，将重新生成新的医嘱。\n\n确定执行重整操作吗？");
                resetBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetMa(zyh, czydm, ksdm);
                        dialog.dismiss();
                    }
                });
                resetBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog resetDialog = resetBuilder.create();
                resetDialog.show();
                break;
            case R.id.yizhu_tv_yaopin:
                typeChosen = typeCode[0];
                sortYizhu(typeCode[0], stateChosen);
                break;
            case R.id.yizhu_tv_shoushu:
                typeChosen = typeCode[1];
                sortYizhu(typeCode[1], stateChosen);
                break;
            case R.id.yizhu_tv_jiancha:
                typeChosen = typeCode[2];
                sortYizhu(typeCode[2], stateChosen);
                break;
            case R.id.yizhu_tv_huli:
                typeChosen = typeCode[3];
                sortYizhu(typeCode[3], stateChosen);
                break;
            case R.id.yizhu_tv_fuzhu:
                typeChosen = typeCode[4];
                sortYizhu(typeCode[4], stateChosen);
                break;
            case R.id.yizhu_tv_qita:
                typeChosen = typeCode[5];
                sortYizhu(typeCode[5], stateChosen);
                break;
            case R.id.yizhu_tv_zhiliao:
                typeChosen = typeCode[6];
                sortYizhu(typeCode[6], stateChosen);
                break;
            case R.id.yizhu_tv_reset:
                typeChosen = "";
                sortYizhu("", stateChosen);
                break;
            case R.id.yizhu_iv_tao:
                showTaoYizhuDialog();
                break;
            case R.id.yizhu_iv_shanchu:
                if (header_check.getVisibility() == View.GONE) {
                    header_check.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < yiZhuBeanList.size(); i++) {
                        yiZhuBeanList.get(i).setShow(true);
                    }
                    newYiZhuAdapter.refreshData(yiZhuBeanList);
                } else if (header_check.getVisibility() == View.INVISIBLE) {
                    List<YiZhuBean> checkedList = newYiZhuAdapter.getCheckedList();
                   // dealWithBindingYizhu(checkedList);
                    deleteChooseYizhus(checkedList);
                }

                break;
        }
    }
private void commitYiZhu() {
    String a = "";
    if (flag == 0) {
        a = "false";
    } else if (flag == 1) {
        a = "true";
    }

    submitMa(zyh,czydm,a);
//    final String yzbztemp = a;
//    AlertDialog.Builder buildertj = new AlertDialog.Builder(context);
//    buildertj.setPositiveButton("确认提交", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
////            addSignjobgetSignResult(sjson, cadaoDialog, textctime, false, zyh);
//            ArrayList<YzSubmit> yzSubmit = new ArrayList<>();
//            for (YiZhuBean be : yiZhuBeanList) {
//                YzSubmit YzSubmit = new YzSubmit();
//                if (be.getYZZTMC().equals("保存")) {
//                    YzSubmit.setCzydm(czydm);
//                    YzSubmit.setYzh(be.getYZH());
//                    YzSubmit.setZyh(be.getZYH());
//                    yzSubmit.add(YzSubmit);
//                }
//            }
//            String sjson = new Gson().toJson(yzSubmit);
//            addSignjobgetSignResultBySubmit(sjson,zyh, czydm, yzbztemp);
//        }
//    });
//    buildertj.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            dialog.dismiss();
//        }
//    });
//    AlertDialog alertDialogtj = buildertj.create();
//    alertDialogtj.setMessage("确定要提交所有保存状态的医嘱吗？");
//    alertDialogtj.show();
}


    private void prepareCaData() {

        List<YiZhuBean> cList  = newYiZhuAdapter.getAllList();
        if (null==ltemps) ltemps = new ArrayList<>();
        ltemps.clear();
        for (YiZhuBean yiZhuBean : cList) {
            // 药品医嘱 执行过的医嘱不在这里执行
            if (!yiZhuBean.getLXMC().equals("药品医嘱")&&yiZhuBean.getCAZXSJ().equals("")) {
                ltemps.add(new CaDoBean(yiZhuBean.getYZMC(), "", yiZhuBean.getLXMC(), "", "", yiZhuBean.getZYH(), yiZhuBean.getYZH(), yiZhuBean.getZXHSMC(), "", yiZhuBean.getYZSJ(), false));
            }
        }
        initMaCaDoWindow();
    }

    /**
     * 临时医嘱CA签字执行
     */
    private void initMaCaDoWindow() {
        View herbalDrugView = LayoutInflater.from(context).inflate(R.layout.dialog_cado_drugs, null);
        final AlertDialog cadaoDialog = new AlertDialog.Builder(context).create();
        cadaoDialog.show();
        cadaoDialog.setContentView(herbalDrugView);
        WindowManager.LayoutParams attributes = cadaoDialog.getWindow().getAttributes();
        attributes.width = (int) (750 * Common.density);
        attributes.height = (int) (500 * Common.density);
        cadaoDialog.getWindow().setAttributes(attributes);
        cadaoDialog.setCanceledOnTouchOutside(true);
        Button tv_cancle = herbalDrugView.findViewById(R.id.bt_cancle);
        Button tv_submit = herbalDrugView.findViewById(R.id.bt_confirm);
        final TextView tv_xgsj = herbalDrugView.findViewById(R.id.tv_xgsj1);
        final RecyclerView sf_listview = herbalDrugView.findViewById(R.id.sfxm_recycler);
        final CheckBox cb_qx = herbalDrugView.findViewById(R.id.cb_cx);
        final YzbgItemAdapter yzbgItemAdapter = new YzbgItemAdapter(ltemps, context);
        sf_listview.addItemDecoration(new MyItemDecoration(context, MyItemDecoration.VERTICAL_LIST));
        sf_listview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        sf_listview.setAdapter(yzbgItemAdapter);

        try {
            tv_xgsj.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
            activityUtils.showToast("日期格式初始化异常！");
        }
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadaoDialog.dismiss();
            }
        });
        tv_xgsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerNew.showTimeText(tv_xgsj, getHoldingActivity());
            }
        });
        cb_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CaDoBean> tBeans = yzbgItemAdapter.getalllist();
                if (null == tBeans || tBeans.isEmpty())
                    return;
                yzbgItemAdapter.setAllChecked(cb_qx.isChecked());
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<YzbgSubmitBean> yzbgSubmitBeans = new ArrayList<>();
                Set<String> onlyKeys = new HashSet<>();
                String textctime = tv_xgsj.getText().toString();
                if (textctime.equals("重新选择") || textctime.equals("")) {
                    activityUtils.showLongToast("时间格式有误！");
                    return;
                }
//              //  if(haschoosetime){
//              //     textctime = textctime+":00";
//              //  }else {
//                    textctime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//              //  }

                // 说明选择了时间
                if (textctime.split(":").length <= 2) {
                    textctime = textctime + ":00";
                }
                List<CaDoBean> caDoBeans = yzbgItemAdapter.getallCheckedlist();
                if (null == caDoBeans || caDoBeans.size() <= 0) {
                    activityUtils.showLongToast("请先选择要执行的医嘱！");
                    return;
                }

                boolean blowMinYzsj = false;
                for (int i = 0; i < caDoBeans.size(); i++) {

                    try {
                        //yyyy-MM-dd HH:mi:ss
                        String tempyzsj = caDoBeans.get(i).getYzsj().substring(0, 16) + ":00";
                        Date tyzsj = sdf.parse(tempyzsj);
                        Date ctime = sdf.parse(textctime);

                        // 如果医嘱时间 大于 所选操作时间
//                            Log.e(TAG, "onClick: "+tempyzsj+"=="+tyzsj+"==="+ctime );
//                        Log.e(TAG, "onClick: "+tempyzsj+"=="+(tyzsj.before(ctime)) );
//                        if (tyzsj.getTime()>ctime.getTime()){
//                            blowMinYzsj = true;
//                        }
                        if (tyzsj.compareTo(ctime) > 0) {
                            blowMinYzsj = true;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (blowMinYzsj) {
                    activityUtils.showLongToast("选择时间不能小于 所选的最小医嘱时间！");
                    return;
                }
                //Log.e(TAG, "onClick: "+blowMinYzsj );
                for (CaDoBean be : caDoBeans) {

                    YzbgSubmitBean yzbgSubmitBean = new YzbgSubmitBean();
                    String keystr = be.getZyh() + "_" + be.getYzh();
                    if (!onlyKeys.contains(keystr)) {
                        yzbgSubmitBean.setCzydm(czydm);
                        yzbgSubmitBean.setYzh(be.getYzh());
                        yzbgSubmitBean.setZyh(be.getZyh());
                        yzbgSubmitBean.setZxsj(textctime);
                        yzbgSubmitBeans.add(yzbgSubmitBean);
                    }

                }
                String sjson = new Gson().toJson(yzbgSubmitBeans);
                addSignjobgetSignResult(sjson, cadaoDialog, textctime, false, zyh);
            }
        });

    }

    private void deleteChooseYizhus(List<YiZhuBean> bindingList) {
        if (bindingList.size() <=0) {
            activityUtils.showToast("请选择至少一条医嘱进行删除！");
            return;
        }
        List<YiZhuBean> list = new ArrayList<>();
        HashSet<String> yzhsets = new HashSet<>();
        for (int i = 0; i < bindingList.size(); i++) {
            YiZhuBean yiZhuBean = bindingList.get(i);
            String ysmc = yiZhuBean.getYSMC();
            if (!czymc.equals(ysmc)) {
                activityUtils.showToast("非本人医嘱不可删除");
                return;
            }
            String yzztmc = yiZhuBean.getYZZTMC();
            if (!yzztmc.equals("保存")) {
                activityUtils.showToast("所选医嘱包含非保存状态医嘱！");
                return;
            }

            if (!yzhsets.contains(yiZhuBean.getYZH())){
                yzhsets.add(yiZhuBean.getYZH());
                YiZhuBean yiZhuBean1 = new YiZhuBean();
                yiZhuBean1.setYZH(yiZhuBean.getYZH());
                yiZhuBean1.setZYH(yiZhuBean.getZYH());
                list.add(yiZhuBean1);
            }

        }
        String s = new Gson().toJson(list);
        String isLong = "";
        if (flag == 1) {
            isLong = "true";
        } else if (flag == 0) {
            isLong = "false";
        }
            deleteMa(s, isLong);
    }


    private void dealwithUnbindingYizhu(List<YiZhuBean> checkedList) {
        if (checkedList.size() < 2) {
            activityUtils.showToast("拆组医嘱数量至少两条！");
            return;
        }
        String yzh = checkedList.get(0).getYZH();
        for (int i = 0; i < checkedList.size(); i++) {
            YiZhuBean yiZhuBean = checkedList.get(i);
            String ysmc = yiZhuBean.getYSMC();
            if (!czymc.equals(ysmc)) {
                activityUtils.showToast("非本人医嘱不可拆组");
                return;
            }
            String yzztmc = yiZhuBean.getYZZTMC();
            if (!yzztmc.equals("保存")) {
                activityUtils.showToast("所选医嘱包含非保存状态医嘱！");
                return;
            }
            String yzh1 = yiZhuBean.getYZH();
            if (!yzh.equals(yzh1)) {
                activityUtils.showToast("所选医嘱并非同组！");
                return;
            }
        }
        getUnbindingYizhuInfos(checkedList);
    }

    private void getUnbindingYizhuInfos(List<YiZhuBean> checkedList) {
        String zyh = checkedList.get(0).getZYH();
        String yzh = checkedList.get(0).getYZH();
        String isLongMa = isLong.equals("true") ? "1" : "0";
        removeGroupMa(zyh, yzh, isLongMa);
    }

    private void dealWithBindingYizhu(List<YiZhuBean> bindingList) {
        if (bindingList.size() < 2) {
            activityUtils.showToast("结组医嘱数量至少两条！");
            return;
        }
        List<String> names = new ArrayList<>();
        String yfmc0 = bindingList.get(0).getYFMC();
        String plzwmc0 = bindingList.get(0).getPLZWMC();
        for (int i = 0; i < bindingList.size(); i++) {
            YiZhuBean yiZhuBean = bindingList.get(i);
            String ysmc = yiZhuBean.getYSMC();
            if (!czymc.equals(ysmc)) {
                activityUtils.showToast("非本人医嘱不可结组");
                return;
            }
            String yzztmc = yiZhuBean.getYZZTMC();
            if (!yzztmc.equals("保存")) {
                activityUtils.showToast("所选医嘱包含非保存状态医嘱！");
                return;
            }
            String yzmc = yiZhuBean.getYZMC();
            names.add(yzmc);
            String yfmc = yiZhuBean.getYFMC();
            if (!yfmc0.equals(yfmc)) {
                activityUtils.showToast("药品用法不同不可结组");
                return;
            }
            String plzwmc = yiZhuBean.getPLZWMC();
            if (!plzwmc0.equals(plzwmc)) {
                activityUtils.showToast("药品频率不同不可结组");
                return;
            }
        }
        if (!Common.ifDuplicated(names)) {
            activityUtils.showToast("包含相同药品不可结组");
            return;
        }
        /*条件均通过，开始取数据*/
        getBindYizhuInfos(bindingList);
    }

    private void getBindYizhuInfos(List<YiZhuBean> list) {
        String zyh = "";
        List<Integer> yizhuNumsList = new ArrayList<>();
        String[] yizhuNums = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            YiZhuBean yiZhuBean = list.get(i);
            zyh = yiZhuBean.getZYH();
            String yzh = yiZhuBean.getYZH();
            yizhuNums[i] = yzh;
            yizhuNumsList.add(Integer.parseInt(yzh));
        }
        String maxMaNo = String.valueOf(Collections.max(yizhuNumsList));
        String minMaNo = String.valueOf(Collections.min(yizhuNumsList));
        String maNoList;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            maNoList = String.join(",", yizhuNums);
        } else {
            maNoList = Common.arrayToString(yizhuNums);
        }
        String isLongMa = isLong.equals("true") ? "1" : "0";
        setGroupMa(zyh, minMaNo, maxMaNo, isLongMa, maNoList);
    }

    /*初始化医嘱录入选药页面*/
    private void initLuruPopWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.luru_popview, null);
        ImageView iv_save = view.findViewById(R.id.luru_iv_save);
        final ImageView iv_clear = view.findViewById(R.id.luru_iv_clear);
        final EditText et_search = view.findViewById(R.id.luru_et_search);
        final GridView luruGrid = view.findViewById(R.id.luruGrid);
        final RecyclerView savedList = view.findViewById(R.id.luru_savedList);
        final RadioGroup rg_types = view.findViewById(R.id.luru_rg_types);
        final ImageView iv_empty = view.findViewById(R.id.luru_iv_empty);
        luruGrid.setEmptyView(iv_empty);
        savedRecyclerAdapter = new SavedDrugRecyclerAdapter(newYizhus, context,freqencyDicts,isLong);
        savedList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        savedList.setAdapter(savedRecyclerAdapter);
        savedRecyclerAdapter.setOnItemClickListener(new SavedDrugRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SavedYizhu savedYizhu = newYizhus.get(position);
                String yizhuType = savedYizhu.getYizhuType();
                boolean enable = savedYizhu.isEnable();
                editLuru(null, enable, yizhuType, true, savedYizhu, true, position);
                selectDesage(savedYizhu.getYzdm(),null,czydm);
            }
        });
        savedRecyclerAdapter.setOnItemDeleteListener(new SavedDrugRecyclerAdapter.OnItemDeleteListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                yiZhuJsons.remove(position);
                newYizhus.remove(position);
                savedRecyclerAdapter.updateList(newYizhus,freqencyDicts,isLong);
                activityUtils.showToast("删除医嘱成功");
            }
        });
        luruGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrugCodeInfor drugCodeInfor = drugCodeInfors.get(position);
                int i = rg_types.indexOfChild(rg_types.findViewById(rg_types.getCheckedRadioButtonId()));
                YBBZ = drugCodeInfor.getYBBZ();
                yzdm = drugCodeInfor.getDM();
                selectDesage(yzdm,null,czydm);
                Log.e("WQ", "医保标志==" + YBBZ);

                if (i == 0) {
                    editLuru(drugCodeInfor, kucunEnable, types.get(i), false, null, false, 0);
                } else {
                    //判断重复选药
                    String mc = drugCodeInfor.getMC();
                    String gg = drugCodeInfor.getGG();
                    String kc = drugCodeInfor.getKC();
                    String dm = drugCodeInfor.getDM();
                    String dw2 = drugCodeInfor.getDW2();
                    if (dm.equals("3")) {
                        int duplicateP = isDuplicateForWenzi(mc);
                        if (duplicateP > -1) {//已经存在
                            addByClickItem(duplicateP);
                        } else {//不存在
                            SavedYizhu savedWenzi = new SavedYizhu(mc, Common.getSystemTimeyMyHm(), gg, kc, 0, "", 0, "1", false, false, "", "", "", false, dm, true, dm,YBBZ,"",dw,dw2);
                            YiZhuJson jsonWenzi = new YiZhuJson(zyh, "", czydm, docKSDM, typeCodes.get(i), dm, "", "剂量单位", "", "", "", mc, "1", dw2, "移动", "", YBBZ, "", Common.getSystemTimeyMyHm());
                            newYizhus.add(savedWenzi);
                            yiZhuJsons.add(jsonWenzi);
                        }
                    } else {
                        int duplicate = isDuplicate(dm);
                        if (duplicate > -1) {
                            addByClickItem(duplicate);
                        } else {

                            SavedYizhu savedYizhu = new SavedYizhu(mc, Common.getSystemTimeyMyHm(), gg, kc,0,"",  0,"1", false, false, "", "", types.get(i), false, dm, true, dm,YBBZ,"",dw,dw2);
                            YiZhuJson yiZhuJson = new YiZhuJson(zyh, "", czydm, docKSDM, typeCodes.get(i), dm, "", "剂量单位", "", "", "", "", "1", dw2, "移动", "", YBBZ, "", Common.getSystemTimeyMyHm());
                            newYizhus.add(savedYizhu);
                            yiZhuJsons.add(yiZhuJson);
                        }
                    }
                    savedRecyclerAdapter.updateList(newYizhus,freqencyDicts,isLong);

                }
                MainActivity activity = (MainActivity) getHoldingActivity();
                activity.closeKeybord(luruGrid,context);
            }
        });
        int checkedPosition = typeCodes.indexOf(dm);
        RadioButton radioButton0 = (RadioButton) rg_types.getChildAt(checkedPosition);
        if (null == radioButton0) return;
        radioButton0.setChecked(true);
        for (int i = 0; i < types.size(); i++) {
            RadioButton radioButton = (RadioButton) rg_types.getChildAt(i);
            String s = types.get(i);
            String substring = s.substring(0, 2);
            radioButton.setText(substring);
        }
        final DrugAdapter drugAdapter = new DrugAdapter(drugCodeInfors, context);
        luruGrid.setAdapter(drugAdapter);
        getDrugCodeInfo(dm, "", drugAdapter);
        rg_types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = group.indexOfChild(group.findViewById(checkedId));
                dm = typeCodes.get(i);
                et_search.setText("");
                getDrugCodeInfo(dm, "", drugAdapter);
                kucunEnable = types.get(i).contains("药品");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                getDrugCodeInfo(dm, s1, drugAdapter);
            }
        });
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final PopupWindow luruPopWindow = new PopupWindow(view, (int) (810 * Common.density), (int) (610 * Common.density));
        luruPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        luruPopWindow.setOutsideTouchable(true);
        luruPopWindow.setTouchable(true);
        luruPopWindow.setFocusable(true);
        luruPopWindow.setBackgroundDrawable(new BitmapDrawable());
        luruPopWindow.setAnimationStyle(android.R.anim.slide_in_left);
        luruPopWindow.showAtLocation(view, Gravity.END, (int) (0 * Common.density), (int) (45 * Common.density));
        luruPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                drugCodeInfors.clear();
                isSavedYizhuEmpty = newYizhus.isEmpty();
            }
        });
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditData();
                Gson gson = new Gson();
                String json = gson.toJson(yiZhuJsons);
                saveDialog = new ProgressDialog(context);
                saveDialog.setCanceledOnTouchOutside(false);
                saveDialog.setMessage("保存医嘱中...");
                Log.e("WQ", "json=" + json);
                saveMa(json, isLong, luruPopWindow);
                selectFreqency("",null,czydm);
                selectUsageDt("",null,czydm);
//                selectDesage(yzdm,null,czydm);
            }
        });
    }

    private int isDuplicate(String dm) {
        int position = -1;
        for (int i = 0; i < newYizhus.size(); i++) {
            if (newYizhus.get(i).getYzdm().equals(dm)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private int isDuplicateForWenzi(String mc) {
        int positon = -1;
        for (int i = 0; i < newYizhus.size(); i++) {
            if (newYizhus.get(i).getName().equals(mc)) {
                positon = i;
                break;
            }
        }
        return positon;
    }

    private void addByClickItem(int duplicateP) {
        SavedYizhu savedYizhu = newYizhus.get(duplicateP);
        String sl = savedYizhu.getSl();
        int origin = Integer.parseInt(sl);
        int finalCount = origin + 1;
        savedYizhu.setSl(String.valueOf(finalCount));
        YiZhuJson yiZhuJson = yiZhuJsons.get(duplicateP);
        String jsonCount = yiZhuJson.getSl();
        int finalNum = Integer.parseInt(jsonCount) + 1;
        yiZhuJson.setSl(String.valueOf(finalNum));
    }

    private void getEditData() {
        List<SavedYizhu> savedYizhus = savedRecyclerAdapter.getSavedYizhus();
        for (int i = 0; i < savedYizhus.size(); i++) {
            SavedYizhu savedYizhu = savedYizhus.get(i);
            YiZhuJson yiZhuJson = yiZhuJsons.get(i);
            String sl = savedYizhu.getSl();
            String name = savedYizhu.getName();
            String dm = savedYizhu.getDm();
            yiZhuJson.setSl(sl);
//            yiZhuJson.setPl(savedYizhu.getPldm());
            if (!dm.equals("3") || name.equals("文字医嘱")) {
                String bz = savedYizhu.getBz();
                yiZhuJson.setBz2(bz);
            }
        }
    }

    /*编辑医嘱界面*/
    private void editLuru(@Nullable DrugCodeInfor selectedBean, final boolean enable, final String type, boolean edit, @Nullable SavedYizhu savedYizhu, final boolean replace, final int position) {
        /*注册id*/
        View luruDialogView = LayoutInflater.from(context).inflate(R.layout.luruedit_dialog, null);
        final TextView tv_yzsj = luruDialogView.findViewById(R.id.luru_tv_yzsj);
        final TextView tv_gg = luruDialogView.findViewById(R.id.luru_tv_gg);
        final TextView tv_yznr = luruDialogView.findViewById(R.id.luru_tv_yznr);
        final TextView sp_yf = luruDialogView.findViewById(R.id.luru_sp_yf);
//        final EditText et_jl = luruDialogView.findViewById(R.id.luru_et_jl);
        TextView tv_jldw = luruDialogView.findViewById(R.id.luru_tv_jldw);
        final EditText et_sl = luruDialogView.findViewById(R.id.luru_et_sl);
        TextView tv_sldw = luruDialogView.findViewById(R.id.luru_tv_sldw);
        final TextView tv_kc = luruDialogView.findViewById(R.id.luru_tv_kc);
        final TextView sp_pl = luruDialogView.findViewById(R.id.luru_sp_pl);
        final CheckBox luru_rb1 = luruDialogView.findViewById(R.id.luru_rb1);
        final CheckBox luru_rb2 = luruDialogView.findViewById(R.id.luru_rb2);
        final EditText et_bz = luruDialogView.findViewById(R.id.luru_et_bz);
        ImageView count_decrease = luruDialogView.findViewById(R.id.sl_iv_decrease);
        final ImageView count_add = luruDialogView.findViewById(R.id.sl_iv_add);
        ImageView edit_iv_save = luruDialogView.findViewById(R.id.edit_iv_save);
        final LinearLayout right1 = luruDialogView.findViewById(R.id.Right1);
        final LinearLayout right2 = luruDialogView.findViewById(R.id.Right2);
        final LinearLayout right3 = luruDialogView.findViewById(R.id.Right3);
        //新增控件代码
        final EditText frequency_search = luruDialogView.findViewById(R.id.frequency_search);
        final EditText usage_search = luruDialogView.findViewById(R.id.usage_search);
        final EditText desage_search = luruDialogView.findViewById(R.id.desage_search);
        final RecyclerView frequency_list = luruDialogView.findViewById(R.id.frequencyUseList);
        final RecyclerView usage_list = luruDialogView.findViewById(R.id.usageList);
        final RecyclerView desage_list = luruDialogView.findViewById(R.id.desageList);
        final ImageView frequency_iv_empty = luruDialogView.findViewById(R.id.frequency_iv_empty);
        final ImageView usage_iv_empty = luruDialogView.findViewById(R.id.usage_iv_empty);
        final ImageView desage_iv_empty = luruDialogView.findViewById(R.id.desage_iv_empty);
        final ImageView frequency_clear = luruDialogView.findViewById(R.id.frequency_clear);
        final ImageView usage_clear = luruDialogView.findViewById(R.id.usage_clear);
        final ImageView desage_clear = luruDialogView.findViewById(R.id.desage_clear);
        final TextView sp_jl = luruDialogView.findViewById(R.id.luru_sp_jl);
        final Button jl_button = luruDialogView.findViewById(R.id.jl_button);
        final LinearLayout usageRow = luruDialogView.findViewById(R.id.usageRow);
        final LinearLayout desageRow = luruDialogView.findViewById(R.id.desageRow);
        final LinearLayout frequencyRow = luruDialogView.findViewById(R.id.frequencyRow);



        /*赋值*/
        final String jlbl;
        if (isLong.equals("true")) {
            luru_rb2.setVisibility(View.GONE);
            sp_pl.setVisibility(View.VISIBLE);
        } else {
            sp_pl.setVisibility(View.GONE);
        }
        if(freqencyDicts.size()>0) {
            plDM = freqencyDicts.get(0).getDM();
            sp_pl.setText(freqencyDicts.get(0).getMC1());
        }
        if(usageDicts.size()>0) {
            yfDM = usageDicts.get(0).getDM();
            sp_yf.setText(usageDicts.get(0).getMC());
        }
        if(usage_iv_empty != null && usageDicts.size()==0){
            usage_iv_empty.setVisibility(View.VISIBLE);
        }else if(usage_iv_empty != null && usageDicts.size()!=0){
            usage_iv_empty.setVisibility(View.GONE);
        }
        //frequency,usage,desage的recyclerList使用的manager
        FlexboxLayoutManager frequencyManager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        frequency_list.setLayoutManager(frequencyManager);
        FlexboxLayoutManager usageManager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        usage_list.setLayoutManager(usageManager);
        FlexboxLayoutManager desageManager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        desage_list.setLayoutManager(desageManager);
        //给频率添加适配器
        frequencyListAdapter = new FrequencyListAdapter(context,freqencyDicts);
        frequency_list.setAdapter(frequencyListAdapter);
        //给方法添加适配器
       usageListAdapter = new UsageListAdapter(context,usageDicts);
        usage_list.setAdapter(usageListAdapter);
        //给剂量添加适配器
        desageListAdapter = new DesageListAdapter(context,desageDicts);
        desage_list.setAdapter(desageListAdapter);
        //frequency清理图标点击事件
        frequency_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency_search.setText("");
            }
        });
        //usage清理图标点击事件
        usage_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usage_search.setText("");
            }
        });
        //desage清理图标点击事件
        desage_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desage_search.setText("");
            }
        });
        //frequency查询框事件
        frequency_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    frequency_clear.setVisibility(View.VISIBLE);
                } else {
                    frequency_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                selectFreqency(s1,frequency_iv_empty,czydm);

            }
        });
        //usage查询框事件
        usage_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    usage_clear.setVisibility(View.VISIBLE);
                } else {
                    usage_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                selectUsageDt(s1,usage_iv_empty,czydm);
            }
        });
        if (!edit) {
            assert selectedBean != null;
            String mc = selectedBean.getMC();
            final String gg = selectedBean.getGG();
            String jl = selectedBean.getJL();
            String kc = selectedBean.getKC();
            jlbl = selectedBean.getJLBL();
            dw = selectedBean.getDW();
            dw2 = selectedBean.getDW2();
            String dw2 = selectedBean.getDW2();
            yzdm = selectedBean.getDM();
            String ybbz = selectedBean.getYBBZ();
            tv_yzsj.setText(Common.getSystemTimeyMyHm());
//            initUsageSpinner(sp_yf);
//            initFreqencySpinner(sp_pl, et_sl, jlbl, et_jl);
            tv_gg.setText(gg);
            tv_yznr.setText(mc);
            tv_jldw.setText("  "+dw);
            tv_sldw.setText(dw2);
            usage_search.setText("");
            frequency_search.setText("");
            desage_search.setText("");
            if (jl.equals("0")) {
                desage_search.setText("");
            } else {
                desage_search.setText(jl);
            }
            et_sl.setText("1");
            tv_kc.setText(kc);
            if (!enable) {
                sp_jl.setVisibility(View.GONE);
                sp_yf.setVisibility(View.GONE);
                sp_pl.setVisibility(View.GONE);
            }
        } else {
            String mc = savedYizhu != null ? savedYizhu.getName() : null;
            assert savedYizhu != null;
            dw = savedYizhu.getDw();
            dw2 = savedYizhu.getDw2();
            String time = savedYizhu.getTime();
            String gg = savedYizhu.getGg();
            String kucun = savedYizhu.getKucun();
            int yfPosition = savedYizhu.getYfPosition();
            String jl = savedYizhu.getJl();
            int plPosition = savedYizhu.getPlPosition();
            String sl = savedYizhu.getSl();
            boolean ps = savedYizhu.isPs();
            boolean zby = savedYizhu.isZby();
            jlbl = savedYizhu.getJlbl();
            String bz = savedYizhu.getBz();
            YBBZ = savedYizhu.getYBBZ();
//            initUsageSpinner(sp_yf);
//            initFreqencySpinner(sp_pl, et_sl, jlbl, et_jl);
            sp_pl.setText(freqencyDicts.get(plPosition).getMC1());
            sp_yf.setText(usageDicts.get(yfPosition).getMC());
//            sp_jl.setText();
            tv_yznr.setText(mc);
            tv_gg.setText(gg);
            tv_yzsj.setText(time);
            tv_kc.setText(kucun);
            tv_jldw.setText("  "+dw);
            tv_sldw.setText(dw2);
//            sp_yf.setSelection(yfPosition);
            sp_jl.setText(jl);
 //           sp_pl.setSelection(plPostion);
            et_sl.setText(sl);
            luru_rb1.setChecked(zby);
            luru_rb2.setChecked(ps);
            et_bz.setText(bz);
        }
        tv_yzsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerNew.showTimeText(tv_yzsj,getHoldingActivity());
            }
        });
//        //剂量输入框查询事件
        desage_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                sp_jl.setText(s1);
                String shuLiang = initCount(jlbl, s1);
                et_sl.setText(shuLiang);

            }
        });
        jl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = desage_search.getText().toString();
                sp_jl.setText(s);
                MainActivity activity = (MainActivity) getHoldingActivity();
               activity.closeKeybord(jl_button,context);

            }
        });
        count_decrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String s = et_sl.getText().toString();
                int num = Integer.parseInt(s);
                int num2 = num - 1;
                if (num2 < 0) {
                    num2 = 0;
                }
                et_sl.setText(String.valueOf(num2));
            }
        });
        count_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_sl.getText().toString();
                int num = Integer.parseInt(s);
                int num2 = num + 1;
                et_sl.setText(String.valueOf(num2));
            }
        });
        usageRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right2.setVisibility(View.VISIBLE);
                right1.setVisibility(View.GONE);
                right3.setVisibility(View.GONE);
            }
        });
        desageRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right2.setVisibility(View.GONE);
                right1.setVisibility(View.VISIBLE);
                right3.setVisibility(View.GONE);
            }
        });
        frequencyRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right2.setVisibility(View.GONE);
                right1.setVisibility(View.GONE);
                right3.setVisibility(View.VISIBLE);
            }
        });

//        if (!freqencyDicts.isEmpty()) {
//             cs = freqencyDicts.get(0).getCS();
//        }else{
//            cs = "0";
//        }
        //常用频率列表点击事件
        frequencyListAdapter.setOnItemClickListener(new FrequencyListAdapter.OnItemClickListener()  {
            @Override
            public void onClick(View view, int position) {
                FreqencyDict freqencyDict = freqencyDicts.get(position);
                sp_pl.setText(freqencyDict.getMC1());
                plDM = freqencyDicts.get(position).getDM();
                cs = freqencyDicts.get(position).getCS();
                String s = initCount(jlbl, sp_jl.getText().toString());
                et_sl.setText(s);
                MainActivity activity = (MainActivity) getHoldingActivity();
                activity.closeKeybord(frequency_list, context);
            }
        });
        //常用用法点击事件
        usageListAdapter.setOnItemClickListener(new UsageListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                UsageDict usageDict = usageDicts.get(position);
                sp_yf.setText(usageDict.getMC());
               yfDM = usageDicts.get(position).getDM();
//                if(usage_search.getText().toString().length()>0) {
//                    usage_search.setText("");
//                }
                MainActivity activity = (MainActivity) getHoldingActivity();
                activity.closeKeybord(usage_list, context);

;            }
        });
        //常用剂量点击事件
        desageListAdapter.setOnItemClickListener(new DesageListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                DesageDict desageDict = desageDicts.get(position);
                desage_search.setText(desageDict.getJL());
            }
        });
        final AlertDialog luruDialog = new AlertDialog.Builder(context).create();
        luruDialog.setView(new EditText(context));
        luruDialog.show();
        luruDialog.getWindow().setContentView(luruDialogView);
        WindowManager.LayoutParams attributes = luruDialog.getWindow().getAttributes();
        attributes.width = (int) (900* Common.density);
        attributes.height = (int) (550 * Common.density);
        luruDialog.getWindow().setAttributes(attributes);
        luruDialog.setCanceledOnTouchOutside(true);
        luruDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        luruDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        edit_iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psjg = luru_rb2.isChecked() ? "1" : "";
                zbybz = luru_rb1.isChecked() ? "1" : "0";
                addyizhuJsonList(tv_yzsj, sp_jl, et_sl, et_bz, luruDialog, type, tv_yznr, tv_yzsj, tv_gg, tv_kc, usage_list, frequency_list, luru_rb1, luru_rb2, et_bz, jlbl, enable, replace, position);
            }
        });

    }

    /*根据剂量计算数量*/
    private String initCount(String jlbl, String jl) {
        if (jl.equals("")) {
            jl = "0";
        }
        double jlblNum = Double.parseDouble(jlbl);
        double jlNum = Double.parseDouble(jl);
        int shuliangNum = 0;
        if (jlblNum != 0) {
            shuliangNum = (int) ((jlNum / jlblNum) * Integer.parseInt(cs));
        }
        return String.valueOf(shuliangNum);
    }

    /*存储医嘱的json数据*/
    private void addyizhuJsonList(TextView tv_yzsj, TextView jiliangET, EditText shuliangET, EditText beizhu,
                                  Dialog dialog, String type, TextView name, TextView time, TextView gg, TextView kc,
                                  RecyclerView yf, RecyclerView pl, CheckBox zby, CheckBox ps, EditText bz, String jlbl, boolean kucunEnable, boolean replace, int position) {
        String yzsj = tv_yzsj.getText().toString();
        String jiliang = jiliangET.getText().toString();
        String sl = shuliangET.getText().toString();
        String bz2 = beizhu.getText().toString();
        if (type.equals("药品医嘱")) {
            if (TextUtils.isEmpty(jiliang)) {
                jiliangET.setError("剂量不能为空");
                return;
            }
            if (jiliang.equals("0")) {
                jiliangET.setError("剂量不能为0");
                return;
            }
            if (yfDM.equals("")){
                activityUtils.showToast("请先选择医嘱用法！");
                return;
            }
        }
        if (TextUtils.isEmpty(sl)) {
            shuliangET.setError("数量不能为空");
            return;
        }
        if (sl.equals("0") && (!type.equals("药品医嘱"))) {
            shuliangET.setError("数量不可为0");
            return;
        }
        if (replace) {
            this.yzdm = newYizhus.get(position).getYzdm();
        }
        YiZhuJson yiZhuJson;

        if (isLong.equals("false")) {
            yiZhuJson = new YiZhuJson(zyh, "", czydm, docKSDM, dm, yzdm, jiliang, "剂量单位", dw, yfDM, "", bz2, sl, dw2, "移动", zbybz, YBBZ, psjg, yzsj);
        } else {
            yiZhuJson = new YiZhuJson(zyh, "", czydm, docKSDM, dm, yzdm, jiliang, "剂量单位", dw, yfDM, plDM, bz2, sl, dw2, "移动", zbybz, YBBZ, psjg, yzsj);
        }
        if (replace) {
            yiZhuJsons.remove(position);
            yiZhuJsons.add(position, yiZhuJson);
        } else {
            yiZhuJsons.add(yiZhuJson);
        }
        addNewYizhuList(name, time, gg, kc, yf, jiliangET, pl, shuliangET, zby, ps, bz, jlbl, type, kucunEnable, replace, position, yzdm);
        dialog.dismiss();
    }

    /*存储医嘱*/
    private void addNewYizhuList(TextView contentET, TextView startTimeET, TextView ggTV, TextView kcTV,
                                 RecyclerView yfSP, TextView jlET,RecyclerView plSP, EditText slET, CheckBox zbyCB, CheckBox psCB, EditText bzET,
                                 String jlbl, String type, boolean kucunEnable, boolean replace, int position, String yzdm) {
        String yizhu = contentET.getText().toString();
        String time = startTimeET.getText().toString();
        String gg = ggTV.getText().toString();
        String kc = kcTV.getText().toString();
        int chooseFrequencyPosition = frequencyListAdapter.getChooseFrequencyPosition();
        int plPosition = chooseFrequencyPosition;
        String jl = jlET.getText().toString();
        int chooseUsagePosition = usageListAdapter.getChooseUsagePosition();
        int yfPosition = chooseUsagePosition;
        String sl = slET.getText().toString();
        boolean zby = zbyCB.isChecked();
        boolean ps = psCB.isChecked();
        String bz = bzET.getText().toString();
        SavedYizhu savedYizhu = new SavedYizhu(yizhu, time, gg, kc,yfPosition, jl,plPosition, sl, zby, ps, bz, jlbl, type, kucunEnable, yzdm, false, "",YBBZ,"",dw,dw2);
        if (replace) {
            newYizhus.remove(position);
            newYizhus.add(position, savedYizhu);
        } else {
            newYizhus.add(savedYizhu);
        }

        savedRecyclerAdapter.updateList(newYizhus,freqencyDicts,isLong);
    }

    @Override
    public void onRefresh() {
        if (flag != -1) {
            gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
        }
    }

    public void get2KSDM(String ksdmFromDoc, String ksdmFromPatient) {
        docKSDM = ksdmFromDoc;
        patientKSDM = ksdmFromPatient;
    }

    public void gettDocOrderInfoByZyh(String zyh, int type, String sjbz, final boolean scrollToBtm) {
//        Log.e("WQ", "医嘱" + "住院号==" + zyh + " 长短期==" + type + "  分类==" + sjbz);
        if (type == 0) {
            yizhu_tv_title.setText("临时医嘱");
            isLong = "false";
            iv_chongzheng.setSelected(true);
            iv_chongzheng.setEnabled(false);
            yizhu_iv_cado.setVisibility(View.GONE);
            item_tv_nurCa.setVisibility(View.GONE);
        } else {
            yizhu_tv_title.setText("长期医嘱");
            isLong = "true";
            iv_chongzheng.setSelected(false);
            iv_chongzheng.setEnabled(true);
            yizhu_iv_cado.setVisibility(View.GONE);
            item_tv_nurCa.setVisibility(View.GONE);
        }
        this.zyh = zyh;
        this.sjbz = sjbz;
        yiZhuBeanList.clear();
        swipeRefreshLayout.setRefreshing(true);
        flag = type;
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("yzlx", String.valueOf(type));
        params.put("sjbz", sjbz);
        String url = Constant.BaseURL + "/GetDocOrderInfoByZyh";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        swipeRefreshLayout.setRefreshing(false);
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (header_check.getVisibility() == View.INVISIBLE) {
                            header_check.setVisibility(View.GONE);
                        }
                        Gson gson = new Gson();
                        yiZhuBeanList = gson.fromJson(response, new TypeToken<List<YiZhuBean>>() {
                        }.getType());
                        newYiZhuAdapter.refreshData(yiZhuBeanList);
                        if (scrollToBtm) {
                            yizhuListView.smoothScrollToPosition(yiZhuBeanList.size() - 1);
                        }
                    }
                });
//        Log.e("wcx", "gettDocOrderInfoByZyh: "+yfItems.length );
    }

    /*录入界面的逻辑*/
    private void getMaTypes() {
        typeCodes.clear();
        types.clear();
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/GetMaTypes";
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
                        try {
                            JSONArray jsonArray = new JSONArray(response);
//                            Log.e("WQ", "response==" + response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String dm = jsonObject.getString("DM");
                                String mc = jsonObject.getString("MC");
                                types.add(mc);
                                typeCodes.add(dm);
                            }
                            dm = typeCodes.get(0);
                            initSpinner();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Log.e("WQ", "出来了");
                        }
                        selectUsageDt("",null,czydm);
//                        selectDesage(yzdm,null,czydm);
                    }
                });
    }

    private void initSpinner() {
        String[] typeItems = types.toArray(new String[types.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, typeItems);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
//        Log.e("WQ", "here");
        type = types.get(0);
    }

    private void selectUsageDt(String queryContent, final ImageView usage_iv_empty,final String ysdm) {
        usageDicts.clear();
        Map<String, String> params = new HashMap<>();
        params.put("queryContent",queryContent);
        params.put("ysdm",ysdm);
        String url = Constant.BaseURL + "/SelectUsageDt";
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
                        Gson gson = new Gson();
                        usageDicts = gson.fromJson(response, new TypeToken<List<UsageDict>>() {
                        }.getType());
                        if(usageListAdapter!=null) {
                            usageListAdapter.setUsageDicts(usageDicts);
                        }
                        if(usage_iv_empty != null && usageDicts.size()==0){
                            usage_iv_empty.setVisibility(View.VISIBLE);
                        }else if(usage_iv_empty != null && usageDicts.size()!=0){
                            usage_iv_empty.setVisibility(View.GONE);
                        }
//                        Log.e(TAG, "onResponse: "+freqencyDicts );
                       initUsageSpinner();
                        selectFreqency("",null,ysdm);
//                        selectDesage(yzdm,null,ysdm);
                    }
                });
    }
    //常用剂量使用方法
    private void selectDesage(final String yzdm, final ImageView desage_iv_empty,final String ysdm) {
       desageDicts.clear();
        Map<String, String> params = new HashMap<>();
        params.put("yzdm",yzdm);
        params.put("ysdm",ysdm);
        String url = Constant.BaseURL + "/SelectDesage";
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
                        Gson gson = new Gson();
                        desageDicts = gson.fromJson(response, new TypeToken<List<DesageDict>>() {
                        }.getType());
                        if(desageListAdapter!=null) {
                            desageListAdapter.setDesageDicts(desageDicts);
                        }
                        if(desage_iv_empty != null && desageDicts.size()==0){
                            desage_iv_empty.setVisibility(View.VISIBLE);
                        }else if(desage_iv_empty != null && desageDicts.size()!=0){
                            desage_iv_empty.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initUsageSpinner() {
        yfItems = new String[usageDicts.size()];
        for (int i = 0; i < usageDicts.size(); i++) {
            yfItems[i] = usageDicts.get(i).getMC();
        }
    }

    /*获取药品频率字典*/
    private void selectFreqency(String queryContent,final ImageView frequency_iv_empty,final String ysdm) {
        freqencyDicts.clear();

        Map<String, String> params = new HashMap<>();
        params.put("queryContent",queryContent);
        params.put("ysdm",ysdm);
//        Log.e(TAG, "selectFreqency: "+queryContent );
        String url = Constant.BaseURL + "/SelectFreqency";
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
//                        isAllLoad = true;
                        Gson gson = new Gson();
                        freqencyDicts = gson.fromJson(response, new TypeToken<List<FreqencyDict>>() {
                        }.getType());

                        plItems = new String[freqencyDicts.size()];

                        for (int i = 0; i < freqencyDicts.size(); i++) {
                            plItems[i] = freqencyDicts.get(i).getMC1();
                        }
                        if(frequencyListAdapter!=null) {
                            frequencyListAdapter.setFrequencyDicts(freqencyDicts);
                        }
                        if( null != frequency_iv_empty && freqencyDicts.size()!=0){
                            frequency_iv_empty.setVisibility(View.GONE);
                        }else if(null != frequency_iv_empty && freqencyDicts.size()==0){
                            frequency_iv_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    private void getDrugCodeInfo(String yzlx, String jsnr, final DrugAdapter drugAdapter) {
//        Log.e("WQ", "yzlx" + yzlx + "   jsnr==" + jsnr);
        Map<String, String> params = new HashMap<>();
        params.put("yzlx", yzlx);
        params.put("jsnr", jsnr);
        params.put("rowBegin", "");
        params.put("rowEnd", "");
        String url = Constant.BaseURL + "/GetDrugCodeInfo";
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
                        try {
                            drugCodeInfors.clear();
                            Gson gson = new Gson();
                            drugCodeInfors = gson.fromJson(response, new TypeToken<List<DrugCodeInfor>>() {
                            }.getType());
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            activityUtils.showToast("药品数据异常");
                            return;
                        }
                        if (null != drugAdapter) {
                            drugAdapter.setDrugCodeInfors(drugCodeInfors);
                        }
                    }
                });
    }

    private void saveMa(String json, String isLong, final PopupWindow popWindow) {
        if (saveDialog != null && !saveDialog.isShowing()) {
            saveDialog.show();
        }
        Map<String, String> params = new HashMap<>();
//        Log.e("WQ", "我的json===" + json);
        params.put("json", json);
        params.put("isLong", isLong);
        params.put("yyzh", "");
        Log.e(TAG, "saveMa: "+params );
        String url = Constant.BaseURL + "/SaveMa";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build().connTimeOut(30*1000).writeTimeOut(30*1000).readTimeOut(30*1000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        saveDialog.dismiss();
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        saveDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            String success = object.getString("response");
                            String bz = object.getString("bz");
                            activityUtils.showToast(bz);
                            if (success.equals("true")) {
                                yiZhuJsons.clear();
                                newYizhus.clear();
                                popWindow.dismiss();
                                gettDocOrderInfoByZyh(zyh, flag, sjbz, true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void submitMa(final String zyh, String ysdm, String isLong) {
        progressDialog.setMessage("提交医嘱中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("ysdm", ysdm);
        params.put("isLong", isLong);
        params.put("ljwyy", "");
        String url = Constant.BaseURL + "/SubmitMa";
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
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            activityUtils.showToast(object.getString("bz"));
                            gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //提交医嘱 使用ca签名了，聊城退役没有
    private void submitMa(final String zyh, String ysdm,String sjson,String isLong) {
        progressDialog.setMessage("提交医嘱中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("ysdm", ysdm);
        params.put("isLong", isLong);
        params.put("ljwyy", "");
        FileUtils.recordToFile(czydm,"CA-入参-提交之前",sjson);
        String url = Constant.BaseURL + "/SubmitMa";
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
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            activityUtils.showToast(object.getString("bz"));
                            gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void deleteMa(String json, String isLong) {
        progressDialog.setMessage("删除医嘱中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("json", json);
        params.put("isLong", isLong);
        String url = Constant.BaseURL + "/DeleteMa";
        Log.e(TAG, "deleteMa: "+params.toString() );
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
                        header_check.setVisibility(View.GONE);
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            activityUtils.showToast(object.getString("bz"));
                            gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void stopMa(String json, String isLong) {
        progressDialog.setMessage("停止医嘱中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("json", json);
        params.put("isLong", isLong);
        String url = Constant.BaseURL + "/StopMa";
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
                        header_check.setVisibility(View.GONE);
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            activityUtils.showToast(object.getString("bz"));
                            gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setGroupMa(String inpNo, String minMaNo, String maxMaNo, String isLongMa, String maNoList) {
        progressDialog.setMessage("医嘱结组中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("inpNo", inpNo);
        params.put("minMaNo", minMaNo);
        params.put("maxMaNo", maxMaNo);
        params.put("isLongMa", isLongMa);
        params.put("maNoList", maNoList);
//        Log.e("WQ", "myParams==" + params);
        String url = Constant.BaseURL + "/SetGroupMa";
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
                            JSONObject object = array.getJSONObject(0);
                            String bz = object.getString("bz");
                            String response1 = object.getString("response");
                            if (response1.equals("true")) {
                                header_check.setVisibility(View.GONE);
                                gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*inpNo: 住院号
     currentMaNo:要解除组的医嘱号
     isLongMa:长期临时标志 1：长期
*/
    private void removeGroupMa(String inpNo, String currentMaNo, String isLongMa) {
        Map<String, String> params = new HashMap<>();
        params.put("inpNo", inpNo);
        params.put("currentMaNo", currentMaNo);
        params.put("isLongMa", isLongMa);
        String url = Constant.BaseURL + "/RemoveGroupMa";
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
                            JSONObject object = array.getJSONObject(0);
                            String mResponse = object.getString("response");
                            String bz = object.getString("bz");
                            activityUtils.showToast(bz);
                            if (mResponse.equals("true")) {
                                header_check.setVisibility(View.GONE);
                                gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void dealwithItem(int position, int type) {
        if (position < yiZhuBeanList.size()) {
            List<YiZhuSelected> list = new ArrayList<>();
            YiZhuSelected yiZhuSelected = new YiZhuSelected();
            YiZhuBean yiZhuBean = yiZhuBeanList.get(position);
            String zyh = yiZhuBean.getZYH();
            String yzh = yiZhuBean.getYZH();
            yiZhuSelected.setYsdm(czydm);
            yiZhuSelected.setYzh(yzh);
            yiZhuSelected.setZyh(zyh);
            list.add(yiZhuSelected);
            Gson gson1 = new Gson();
            final String s = gson1.toJson(list);
            String isLong = "";
            if (flag == 1) {
                isLong = "true";
            } else if (flag == 0) {
                isLong = "false";
            }
            if (type == 1) {
                deleteMa(s, isLong);
            } else {
                String yztstemp = isLong.equals("true")?"停止":"撤销" ;
                final  String  islongtemp = isLong;
                AlertDialog.Builder buildertj = new AlertDialog.Builder(context);
                buildertj.setPositiveButton("确认"+yztstemp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        stopMa(s, islongtemp);
                    }
                });
                buildertj.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = buildertj.create();
                alertDialog.setMessage("确定要"+yztstemp+"该条医嘱吗？");
                alertDialog.show();

            }
        }

    }

    private void resetMa(String zyh, String czydm, String kjks) {
        activityUtils.showToast("正在重整医嘱...");
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        params.put("czydm", czydm);
        params.put("kjks", kjks);
        String url = Constant.BaseURL + "/ResetMa";
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
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //筛选医嘱
    public void sortYizhu(String yizhuType, String state) {
        if (state.equals("7")) {
            state = isLong.equals("true") ? "7" : "8";
        }
        stateChosen = state;
        List<YiZhuBean> first = new ArrayList<>();
        if (typeChosen.equals("")) {
            first = yiZhuBeanList;
        } else {
            for (int i = 0; i < yiZhuBeanList.size(); i++) {
                YiZhuBean yiZhuBean = yiZhuBeanList.get(i);
                String lxdm = yiZhuBean.getLXDM();
                if (lxdm.equals(typeChosen)) {
                    first.add(yiZhuBean);
                }
            }
        }
        List<YiZhuBean> second = new ArrayList<>();
        if (state.equals("")) {
            second = first;
        } else {
            for (int i = 0; i < first.size(); i++) {
                YiZhuBean yiZhuBean = first.get(i);
                String yzzt = yiZhuBean.getYZZT();
                if (yzzt.equals(state)) {
                    second.add(yiZhuBean);
                }
            }
        }
        newYiZhuAdapter.refreshData(second);
    }

    /*判断医嘱fragment中是否有未保存的医嘱*/
    public boolean canTransPatient() {
        return yiZhuJsons.isEmpty();
    }

    public void deleteNotSavedYizhu() {
        newYizhus.clear();
        yiZhuJsons.clear();
    }

    /*中草药处方录入*/

    /*中药颗粒处方医嘱录入*/
    private void initHerbalWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.herbal_luru_popwindow, null);
        final PopupWindow herbalPopWindow = new PopupWindow(view, (int) (800 * Common.density), (int) (610 * Common.density));
        herbalFindViewById(view, herbalPopWindow);
        herbalPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        herbalPopWindow.setOutsideTouchable(true);
        herbalPopWindow.setTouchable(true);
        herbalPopWindow.setFocusable(true);
        herbalPopWindow.setBackgroundDrawable(new BitmapDrawable());
        herbalPopWindow.setAnimationStyle(android.R.anim.slide_in_left);
        herbalPopWindow.showAtLocation(view, Gravity.END, (int) (10 * Common.density), (int) (43 * Common.density));
        herbalPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                patientRecipelDetailsList.clear();
            }
        });
    }

    private void herbalFindViewById(View view, final PopupWindow herbalPopWindow) {
        herbal_pop_fatherList = view.findViewById(R.id.herbal_pop_fatherList);
        final RecyclerView recipelDetailsRecycler = view.findViewById(R.id.herbal_savedList);
        ImageView herbal_iv_delete = view.findViewById(R.id.herbal_iv_delete);
        ImageView herbal_iv_modify = view.findViewById(R.id.herbal_iv_modify);
        tv_ypmc = view.findViewById(R.id.herbal_tv_ypmc);
        et_jl = view.findViewById(R.id.herbal_et_jl);
        tv_jldw = view.findViewById(R.id.herbal_tv_jldw);
        tv_kcts = view.findViewById(R.id.herbal_tv_kcts);
        et_bz = view.findViewById(R.id.herbal_et_bz);
        sp_pl = view.findViewById(R.id.herbal_sp_pl);
        sp_gyfs = view.findViewById(R.id.herbal_sp_gyfs);
        sp_ayfs = view.findViewById(R.id.herbal_sp_ayfs);
        sp_fyyq = view.findViewById(R.id.herbal_sp_fyyq);
        tv_qyyf = view.findViewById(R.id.herbal_tv_qyyf);
        et_sl = view.findViewById(R.id.herbal_et_sl);
        tv_sldw = view.findViewById(R.id.herbal_tv_sldw);
        et_zhutuo = view.findViewById(R.id.herbal_et_zhutuo);
        herbal_tv_cfdh = view.findViewById(R.id.herbal_tv_cfdh);
        ImageView herbal_iv_modifyB = view.findViewById(R.id.herbal_iv_modifyB);
        ImageView iv_commit = view.findViewById(R.id.herbal_iv_commit);

        getPatientZcyCf(zyh, herbal_pop_fatherList);
        ImageView iv_commonDrug = view.findViewById(R.id.herbal_iv_commonDrug);
        ImageView herbal_add  =  view.findViewById(R.id.herbal_iv_add);
        ImageView herbal_decrease = view.findViewById(R.id.herbal_iv_decrease);
        Button herbal_cf_finish = view.findViewById(R.id.herbal_cf_finish);
        layout_djfa = view.findViewById(R.id.layout_djfa);
        layout_jzjl = view.findViewById(R.id.layout_jzjl);
//        final TextView copy_menu = view.findViewById(R.id.copy_menu);
//        final TextView delete_menu = view.findViewById(R.id.delete_menu);
        //处方新增按钮
        Button herbal_cf_add = view.findViewById(R.id.herbal_cf_add);
        sp_djfa = view.findViewById(R.id.herbal_sp_djfa);
        sp_jzjl = view.findViewById(R.id.herbal_sp_jzjl);
        ImageView herbal_change = view.findViewById(R.id.herbal_iv_change);
        if (!brewways.isEmpty()) {
            initBrewaysSpinner(sp_ayfs);
        } else {
            selectBrewway(sp_ayfs);
        }
        if (!daiJianFangAnList.isEmpty()) {
            initDaiJianFangAnSpinner(sp_djfa);
        } else {
            selectDJFA(sp_djfa);
        }
        if (!jianZhuJiLiangList.isEmpty()){
            initJianZhuJiLiangSpinner(sp_jzjl);
        }else {
            selectJZJL(sp_jzjl);
        }
        if (requires.isEmpty()) {
            selectRequire(sp_fyyq);
        } else {
            initRequireSpinner(sp_fyyq);
        }
        initGyplSpinner(sp_pl);
        initGyfsSpinner(sp_gyfs);

        gridLayoutManager = new GridLayoutManager(context,4);
        recipelDetailsAdapter = new RecipelDetailsAdapter(context, patientRecipelDetailsList,gridLayoutManager);
        recipelDetailsRecycler.setLayoutManager(gridLayoutManager);
        recipelDetailsRecycler.setAdapter(recipelDetailsAdapter);
        recipelDetailsAdapter.setOnItemClickListener(new RecipelDetailsAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                herbalChildPosition = position;
                PatientRecipelDetails details = patientRecipelDetailsList.get(position);
                if(!details.getYPMC().equals("")) {
                    String ypmc = details.getYPMC();
                    String dose = details.getDOSE();
                    String dw = details.getDW();
                    String bz = details.getBZ();
                    for (int i = 0; i < patientRecipelDetailsList.size(); i++) {
                        if (i == herbalChildPosition) {
                            patientRecipelDetailsList.get(i).setIschecked(true);
                        } else {
                            patientRecipelDetailsList.get(i).setIschecked(false);
                        }
                    }
                    recipelDetailsAdapter.updateList(patientRecipelDetailsList);
//                HerbalDrugName herbalDrugName = herbalDrugNameList.get(position);
//                String sl = herbalDrugName.getSL();
                    loadXiaoXiangInfos(ypmc, dose, dw, "", bz);
                }else {
                    if(patientRecipels.size()>0) {
                        PatientRecipel recipel = patientRecipels.get(herbalFatherPosition);
                        String cfzt = recipel.getCFZT();
                        if (cfzt.equals("2")) {
                            activityUtils.showToast("提交状态的处方不可修改");
                            return;
                        }

                    }
                    showAddChuFangDialog();
                }

            }
        });
//        ImageView herbal_plus = view.findViewById(R.id.herbal_iv_plus);
//        herbal_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        herbal_pop_fatherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                herbalFatherPosition = position;
                PatientRecipel bean = patientRecipels.get(position);
//                bean.setHerbalChecked(true);
                String dh = bean.getDH();
                String gyplmc = bean.getGYPLMC();
                String ypyfmc = bean.getYPYFMC();
                String jyfsmc = bean.getJYFSMC();
                String fyyqmc = bean.getFYYQMC();
                kfmc = bean.getKFMC();
                qyyfdm = bean.getQYYFDM();
                String cffs = bean.getCFFS();
                String yszt = bean.getYSZT();
                if (!dh.equals("")) {
                    getPatientZcyCfInfoByDh(dh, null);
                }
                loadDaXiangInfos(gyplmc, ypyfmc, jyfsmc, fyyqmc, kfmc, cffs, yszt, "", dh);
                for (int i = 0; i < patientRecipels.size(); i++) {
                    if (i == position) {
                        patientRecipels.get(i).setHerbalChecked(true);
                    } else {
                        patientRecipels.get(i).setHerbalChecked(false);
                    }
                }
                patientRecipleAdapter.updateList(patientRecipels);
                loadXiaoXiangInfos("","","","","");

            }
        });
        herbal_pop_fatherList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                herbalFatherPosition = position;
                for (int i = 0; i < patientRecipels.size(); i++) {
                    if (i == position) {
                        patientRecipels.get(i).setHerbalChecked(true);
                    } else {
                        patientRecipels.get(i).setHerbalChecked(false);
                    }
                }
                patientRecipleAdapter.updateList(patientRecipels);
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                if(patientRecipels.get(position).getCFZT().equals("1")){
                    popupMenu.getMenu().clear();
                    popupMenu.getMenu().add("删除处方");
                }else{
                    popupMenu.getMenu().clear();
                    popupMenu.getMenu().add("复制处方");
                }
                String dh = patientRecipels.get(position).getDH();
                if (!dh.equals("")) {
                    getPatientZcyCfInfoByDh(dh, null);
                }
                Menu menu1 = popupMenu.getMenu();
        // 通过XML文件添加菜单项,menu布局
                popupMenu.getMenuInflater().inflate(R.menu.herbal_popupmenu, menu1);
        //        menu的item点击事件
                popupMenu.setOnMenuItemClickListener(menuItem);
         //       popupMenu.setOnMenuItemClickListener(menu2);
                popupMenu.show();
                return true;
            }

        });
        herbal_pop_fatherList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
         @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
             menu.add(0, 0, 0, "复制处方");
            }
        });
        /*子项修改*/
        herbal_iv_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*单号待定*/
                String jiliang = et_jl.getText().toString();
                String beizhu = et_bz.getText().toString();
                if (TextUtils.isEmpty(jiliang)) {
                    activityUtils.showToast("剂量格式不正确");
                    return;
                }
                if (jiliang.equals("0")) {
                    activityUtils.showToast("剂量不能为0");
                    return;
                }
                PatientRecipel recipel = patientRecipels.get(herbalFatherPosition);
                String cfzt = recipel.getCFZT();
                if (cfzt.equals("2")) {
                    activityUtils.showToast("提交状态的处方不可修改");
                    return;
                }
                String dh = recipel.getDH();
                String ypdm = patientRecipelDetailsList.get(herbalChildPosition).getYPDM();
                updateZxZcycf(ypdm, dh, beizhu, jiliang);
                loadXiaoXiangInfos("","","","","");
            }
        });
        /*子项删除*/
        herbal_iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*单号待定*/
                if (patientRecipels.size()<=0){
                    activityUtils.showToast("暂未找到处方数据");
                    return;
                }
                PatientRecipel recipel = patientRecipels.get(herbalFatherPosition);
                String cfzt = recipel.getCFZT();
                if (cfzt.equals("2")) {
                    activityUtils.showToast("提交状态的处方不可删除");
                    return;
                }
                if(tv_ypmc.getText().toString().equals("")){
                    activityUtils.showToast("请选择需要删除的选项");
                    return;
                }
                String dh = recipel.getDH();
                String ypdm = patientRecipelDetailsList.get(herbalChildPosition).getYPDM();
                deleteZxZcycf(ypdm, dh);
                loadXiaoXiangInfos("","","","","");
            }
        });
        /*常用处方*/
        iv_commonDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activityUtils.showToast("无法获取药房");
//                return;
                initCommonHerbalDialog();
            }
        });
        herbal_decrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!et_jl.getText().toString().equals("")) {
                    String s = et_jl.getText().toString();
                    int num = Integer.parseInt(s);
                    int num2 = num - 1;
                    if (num2 < 0) {
                        num2 = 0;
                    }
                    et_jl.setText(String.valueOf(num2));
                }
            }
        });
        herbal_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_jl.getText().toString().equals("")) {
                    String s = et_jl.getText().toString();
                    int num = Integer.parseInt(s);
                    int num2 = num + 1;
                    et_jl.setText(String.valueOf(num2));
                }
            }
        });

        //列表行式块式转换转换
        herbal_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridLayoutManager.getSpanCount() == 1) {
                    gridLayoutManager.setSpanCount(4);
                    recipelDetailsRecycler.setLayoutManager(new GridLayoutManager(context, 4));
                    recipelDetailsRecycler.scrollToPosition(0);
                } else {
                    gridLayoutManager.setSpanCount(1);
                    recipelDetailsRecycler.setLayoutManager(new LinearLayoutManager(context));
                    recipelDetailsRecycler.scrollToPosition(0);
                }
                recipelDetailsAdapter.notifyItemRangeChanged(0,recipelDetailsAdapter.getItemCount());
            }
        });


        /*大项修改*/
        herbal_iv_modifyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*单号待定*/
                if (patientRecipels.size()<=0){
                    activityUtils.showToast("暂未找到处方数据");
                    return;
                }
                PatientRecipel recipel = patientRecipels.get(herbalFatherPosition);
                String cfzt = recipel.getCFZT();
                if (cfzt.equals("2")) {
                    activityUtils.showToast("提交状态的处方不可修改");
                    return;
                }
                String dh = recipel.getDH();
                String count = et_sl.getText().toString();
                String zhutuo = et_zhutuo.getText().toString();
                if (TextUtils.isEmpty(count)) {
                    activityUtils.showToast("付数格式不正确");
                    return;
                }
                if (count.equals("0")) {
                    activityUtils.showToast("付数不能为0");
                    return;
                }
                updateDxZcycf(dh, herbalFrequencyCode, herbalWaysCode, brewayNo, takeUsageNo, qyyfdm, count, zhutuo);
            }
        });
        //处方明细新增按钮点击事件
        herbal_cf_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patientRecipels.size()>0) {
                    PatientRecipel recipel = patientRecipels.get(herbalFatherPosition);
                    String cfzt = recipel.getCFZT();
                    if (cfzt.equals("2")) {
                        activityUtils.showToast("提交状态的处方不可修改");
                        return;
                    }
                    showAddChuFangDialog();
                }
            }
        });
        /*提交处方*/
        iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientRecipelDetailsList.isEmpty()) {
                    activityUtils.showToast("处方为空");
                } else {
                    for(int i=0;i<patientRecipelDetailsList.size();i++) {
                        if(patientRecipelDetailsList.get(i).getYPMC().equals("")){
                            patientRecipelDetailsList.remove(i);
                        }
                    }
                    submitZcycf(zyh, herbalPopWindow);
                }
            }
        });
        herbal_cf_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*单号待定*/

                String jiliang = et_jl.getText().toString();
                String beizhu = et_bz.getText().toString();
                if (TextUtils.isEmpty(jiliang)) {
                    activityUtils.showToast("剂量格式不正确");
                    return;
                }
                if (jiliang.equals("0")) {
                    activityUtils.showToast("剂量不能为0");
                    return;
                }
                PatientRecipel recipel = patientRecipels.get(herbalFatherPosition);
                String cfzt = recipel.getCFZT();
                if (cfzt.equals("2")) {
                    activityUtils.showToast("提交状态的处方不可修改");
                    return;
                }
                if(tv_ypmc.getText().toString().equals("")){
                    activityUtils.showToast("请选择需要修改的选项");
                    return;
                }
                if(patientRecipelDetailsList.size()>0) {
                    String dh = recipel.getDH();
                    String ypdm = patientRecipelDetailsList.get(herbalChildPosition).getYPDM();
                    updateZxZcycf(ypdm, dh, beizhu, jiliang);
                    loadXiaoXiangInfos("","","","","");
                }
            }
        });
    }

    Mypopmenu.OnMeunItemPositionListener menu2 = new Mypopmenu.OnMeunItemPositionListener() {
        @Override
        public void OnMeunItemPositionClick(Mypopmenu.OnMeunItemPositionListener menuItem, int pos) {
            Log.e(TAG, "OnMeunItemPositionClick: " );
            activityUtils.showToast(pos);
        }

    };

    PopupMenu.OnMenuItemClickListener menuItem = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            PatientRecipel selectedPatientRecipe = patientRecipels.get(herbalFatherPosition);
            if(!selectedPatientRecipe.getCFZT().equals("1")) {
                PatientRecipel copyPatientRecipe = new PatientRecipel(selectedPatientRecipe.getZYH(), selectedPatientRecipe.getDOC_ID(), selectedPatientRecipe.getCFSJ(), ""
                        , selectedPatientRecipe.getCFZT(), selectedPatientRecipe.getQYYFDM(), selectedPatientRecipe.getKFMC(), selectedPatientRecipe.getJYFSMC(), selectedPatientRecipe.getJYFS(), selectedPatientRecipe.getFYYQMC()
                        , selectedPatientRecipe.getFYYQDM(), selectedPatientRecipe.getYSZT(), selectedPatientRecipe.getCFFS(), selectedPatientRecipe.getGYPLDM(), selectedPatientRecipe.getGYPLMC(), selectedPatientRecipe.getYPYFMC()
                        , selectedPatientRecipe.getYPYFDM(), selectedPatientRecipe.isHerbalChecked(), "保存");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String format = dateFormat.format(date);
                copyPatientRecipe.setCFSJ(format);
                String dh1 = selectedPatientRecipe.getDH();
                getPatientZcyCfInfoByDh(dh1, copyPatientRecipe);
                return false;
            }else {
                String dh = selectedPatientRecipe.getDH();
                deleteFxZcycf(dh);
                return false;
            }
        }

    };


    //新建中草药弹出框
    private void showAddChuFangDialog() {
        View addChuFangView = LayoutInflater.from(context).inflate(R.layout.herbal_add_dialog,null);
        AlertDialog addChuFangDialog = new AlertDialog.Builder(context).create();
        addChuFangDialog.show();
        WindowManager.LayoutParams attributes = addChuFangDialog.getWindow().getAttributes();
        addChuFangDialog.setContentView(addChuFangView);
        attributes.width = (int) (700 * Common.density);
        attributes.height = (int) (480 * Common.density);
        addChuFangDialog.getWindow().setAttributes(attributes);
        addChuFangDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        herbalDrugNameDialog(addChuFangView, addChuFangDialog);
    }
    private void herbalDrugNameDialog(final View view, final AlertDialog dialog){
        final RecyclerView herbal_ypmc_list = view.findViewById(R.id.herbal_ypmc_list);
        Button herbal_dialog_save = view.findViewById(R.id.herbal_dialog_save);
        ImageView herbal_zcyjl_add = view.findViewById(R.id.herbal_zcyjl_add);
        ImageView herbal_zcyjl_decrease  = view.findViewById(R.id.herbal_zcyjl_decrease);
        final EditText herbal_add_jl = view.findViewById(R.id.herbal_add_jl);
        final EditText herbal_add_bz = view.findViewById(R.id.herbal_add_bz);
        final TextView herbal_tv_jldw = view.findViewById(R.id.herbal_tv_jldw);
        final EditText herbal_add_ypmc = view.findViewById(R.id.herbal_add_ypmc);
        //点击事件,调用接口
        herbalDrugNameAdapter = new HerbalDrugNameAdapter(context, herbalDrugNameList);
        newPatientRecipelDetails = new PatientRecipelDetails("","","","","","","","","","","","");
        herbal_ypmc_list.setAdapter(herbalDrugNameAdapter);
        SelectHerbalDrugName("");

        herbal_dialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(herbal_add_ypmc.getText().toString().equals("") ||herbal_add_jl.getText().toString().equals("")){
                    activityUtils.showToast("请添加药品名称或药品计量");
                    return;
                }

                if(herbalDrugOthersList.size()>0){
                    String cddm = herbalDrugOthersList.get(0).getCDDM();
                    String lsj = herbalDrugOthersList.get(0).getLSJ();
                    String pc = herbalDrugOthersList.get(0).getPC();
                    String ph = herbalDrugOthersList.get(0).getPH();
                    newPatientRecipelDetails.setCDDM(cddm);
                    newPatientRecipelDetails.setLSJ(lsj);
                    newPatientRecipelDetails.setPC(pc);
                    newPatientRecipelDetails.setPH(ph);
                }else{
                    newPatientRecipelDetails.setCDDM("");
                    newPatientRecipelDetails.setLSJ("");
                    newPatientRecipelDetails.setPC("");
                    newPatientRecipelDetails.setPH("");
                }
                String bz = herbal_add_bz.getText().toString();
                String jl = herbal_add_jl.getText().toString();
                newPatientRecipelDetails.setBZ(bz);
                newPatientRecipelDetails.setDOSE(jl);
                newPatientRecipelDetails.setCN_FLAG(" ");
                newPatientRecipelDetails.setSXH((patientRecipelDetailsList.size()+1)+"");
                recipelDetailsAdapter.updateList(patientRecipelDetailsList);
                String dh = newPatientRecipelDetails.getDH();
                String ypdm = newPatientRecipelDetails.getYPDM();
                String zcymxDw = newPatientRecipelDetails.getDW();
                String pc = newPatientRecipelDetails.getPC();
                String ph = newPatientRecipelDetails.getPH();
                String  sxh = newPatientRecipelDetails.getSXH();
                String lsj = newPatientRecipelDetails.getLSJ();
                String cddm = newPatientRecipelDetails.getCDDM();
                String cn_flag = " ";
                for(int i = 0;i< patientRecipelDetailsList.size();i++)
                    if(patientRecipelDetailsList.get(i).getDH().equals(dh)&& patientRecipelDetailsList.get(i).getYPDM().equals(ypdm)
                            && patientRecipelDetailsList.get(i).getPC().equals(pc)&& patientRecipelDetailsList.get(i).getPH().equals(ph)
                            && patientRecipelDetailsList.get(i).getCDDM().equals(cddm)){
                        activityUtils.showToast("该药品已存在,请查看处方明细");
                        return;
                    }
                InsertZcyMx(dh,ypdm,jl,zcymxDw,bz,pc,ph,sxh,cn_flag,lsj,cddm,czydm,ksdm);
                loadXiaoXiangInfos("","","","","");
                dialog.dismiss();
            }
        });
        herbal_zcyjl_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!herbal_add_jl.getText().toString().equals("")) {
                    String s = herbal_add_jl.getText().toString();
                    int num = Integer.parseInt(s);
                    int num2 = num + 5;
                    herbal_add_jl.setText(String.valueOf(num2));
                }
            }
        });
        herbal_zcyjl_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!herbal_add_jl.getText().toString().equals("")) {
                    String s = herbal_add_jl.getText().toString();
                    int num = Integer.parseInt(s);
                    if(num>=5) {
                        int num2 = num - 5;
                        herbal_add_jl.setText(String.valueOf(num2));
                    }else{
                        int num2 = 0;
                        herbal_add_jl.setText(String.valueOf(num2));
                    }
                }
            }
        });
        herbal_add_ypmc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                SelectHerbalDrugName(s1);
            }
        });


        herbalDrugNameAdapter.setOnItemClickListener(new HerbalDrugNameAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String dw = herbalDrugNameList.get(position).getDW();
                String ypmc = herbalDrugNameList.get(position).getYPMC();
                String ypdm = herbalDrugNameList.get(position).getYPDM();
                String sl = herbalDrugNameList.get(position).getSL();
                String dh = "";
                //获取处方单号
                if(patientRecipelDetailsList.size()>0) {
                    dh = patientRecipelDetailsList.get(0).getDH();
                }else{
                    dh = "";
                }
                herbal_tv_jldw.setText(dw);
//                herbal_add_ypmc.setText(ypmc+"("+gg+")");
                herbal_add_ypmc.setText(ypmc);
                newPatientRecipelDetails.setYPMC(ypmc);
                newPatientRecipelDetails.setYPDM(ypdm);
                newPatientRecipelDetails.setDW(dw);
                newPatientRecipelDetails.setDH(dh);
                SelectHerbalDrugOthers(ypdm, sl);
                herbal_add_jl.requestFocus();
            }
        });
        FlexboxLayoutManager herbalManager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        herbal_ypmc_list.setLayoutManager(herbalManager);
    }
    /*加载处方大项的信息*/
    private void loadDaXiangInfos(String pl, String gyfs, String jyfs, String fyyq, String qyyf, String fushu, String zhutuo, String price, String recipleNo) {
        loadSpinner(sp_pl, pl, plItems);
        loadSpinner(sp_gyfs, gyfs, yfItems);
        loadSpinner(sp_ayfs, jyfs, brewaysNames);
        loadSpinner(sp_fyyq, fyyq, requireNames);
        tv_qyyf.setText(qyyf);
        et_sl.setText(fushu);
        et_zhutuo.setText(zhutuo);
        herbal_tv_cfdh.setText(recipleNo);
    }


    private void loadSpinner(Spinner spinner, String content, String[] items) {
        if (null!=items&& items.length>0) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].equals(content)) {
                    spinner.setSelection(i);
                }
            }
        }
    }

    /*加载处方小项的信息*/
    private void loadXiaoXiangInfos(String ypmc, String jl, String dw, String kucuntishi, String bz) {
        tv_ypmc.setText(ypmc);
        et_jl.setText(jl);
        tv_jldw.setText(dw);
        tv_kcts.setText(kucuntishi);
        et_bz.setText(bz);
    }

    /*中草药处方录入常用药弹出框*/
    private void initCommonHerbalDialog() {
        View herbalDrugView = LayoutInflater.from(context).inflate(R.layout.dialog_herbal_drugs, null);
        final AlertDialog herbalDrugDialog = new AlertDialog.Builder(context).create();
        herbalDrugDialog.show();
        herbalDrugDialog.setContentView(herbalDrugView);
        WindowManager.LayoutParams attributes = herbalDrugDialog.getWindow().getAttributes();
        attributes.width = (int) (700 * Common.density);
        attributes.height = (int) (480 * Common.density);
        herbalDrugDialog.getWindow().setAttributes(attributes);
        herbalDialogFindId(herbalDrugView, herbalDrugDialog);
        herbalDrugDialog.setCanceledOnTouchOutside(true);
        ImageView herbal_iv_dismiss = herbalDrugView.findViewById(R.id.herbal_iv_dismiss);
        herbal_iv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                herbalDrugDialog.dismiss();
            }
        });
    }

    private void herbalDialogFindId(final View view, final AlertDialog dialog) {
//        getCYCFList(ksdm, czydm, herbalRanges[herbalTypePosition]);
        herbal_cfList = view.findViewById(R.id.herbal_cfList);
        CheckBox herbal_cb_all = view.findViewById(R.id.herbal_cb_all);
        RecyclerView herbal_drugRecycler = view.findViewById(R.id.herbal_drugRecycler);
        ImageView herbal_iv_luru = view.findViewById(R.id.herbal_iv_luru);
        Spinner herbal_sp_commonRoom = view.findViewById(R.id.herbal_sp_commonRoom);
        if (kuFangs.isEmpty()) {
            getKFDM(herbal_sp_commonRoom);
        } else {
            initKuFangSpinner(herbal_sp_commonRoom);
        }
        RadioGroup herbal_rg = view.findViewById(R.id.herbal_rg);
        ImageView herbal_iv_empty = view.findViewById(R.id.herbal_iv_empty);
        RadioButton radioButton = (RadioButton) herbal_rg.getChildAt(1);
        getCYCFList(ksdm,"", herbalRanges[1]);
        herbal_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = group.indexOfChild(group.findViewById(checkedId));
                herbalTypePosition = position;
                if (position == 2) {
                    getCYCFList(ksdm, czydm, herbalRanges[position]);
                } else if (position == 1) {
                    getCYCFList(ksdm, "", herbalRanges[position]);
                } else {
                    getCYCFList("", "", herbalRanges[position]);
                }
            }
        });
        radioButton.setChecked(true);
        herbal_cfList.setEmptyView(herbal_iv_empty);
        herbal_cfList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recipelFatherPosition = position;
                String cycfh = commonDrugFathers.get(position).getCYCFH();
                getCYCFInfoByCfdm(cycfh);
            }
        });
        herbal_drugRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recipelChildAdapter = new RecipelChildAdapter(context, commonDrugChildList);
        herbal_drugRecycler.setAdapter(recipelChildAdapter);
        recipelChildAdapter.setOnCheckedListener(new RecipelChildAdapter.OnCheckedListener() {
            @Override
            public void checked(View view, int position, boolean ischecked) {
                for (int i = 0; i < commonDrugChildList.size(); i++) {
                    if (i == position) {
                        CommonDrugChild commonDrugChild = commonDrugChildList.get(i);
                        commonDrugChild.setChecked(ischecked);
                    }
                }
                recipelChildAdapter.refreshData(commonDrugChildList);
            }
        });
        herbal_cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < commonDrugChildList.size(); i++) {
                    commonDrugChildList.get(i).setChecked(isChecked);
                }
                recipelChildAdapter.refreshData(commonDrugChildList);
            }
        });
        herbal_iv_luru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null==commonDrugFathers||commonDrugFathers.size()<=0) {
                    activityUtils.showToast("请先选择处方！");
                    return;
                }
                    CommonDrugFather commonDrugFather = commonDrugFathers.get(recipelFatherPosition);
                    List<CommonDrugChild> checkedItem = recipelChildAdapter.getCheckedItem();
                    dealWithCommitingHerbals(commonDrugFather, checkedItem, dialog);

            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                commonDrugChildList.clear();
            }
        });
    }

    /*处理需要录入的中草药数据*/
    private void dealWithCommitingHerbals(CommonDrugFather father, List<CommonDrugChild> childrenList, AlertDialog dialog) {
        CommonDrugChild commonDrugChild = childrenList.get(0);
        String jyfsdm = commonDrugChild.getJYFSDM();
        String fyyq = commonDrugChild.getFYYQ();
        String yszt = commonDrugChild.getYSZT();
        String fs = commonDrugChild.getFS();
        String gypldm = commonDrugChild.getGYPLDM();
        String ypyfdm = commonDrugChild.getYPYFDM();
        String dh;
        if (TextUtils.isEmpty(newRecipelNo)) {
            dh = "";
        } else {
            dh = newRecipelNo;
        }
        Gson gson = new Gson();
        List<HerbalFatherJson> fatherJsons = new ArrayList<>();
        HerbalFatherJson herbalFatherJson = new HerbalFatherJson(zyh, czydm, yfdm, docKSDM, patientKSDM, jyfsdm, fyyq, yszt, dh, fs, gypldm, ypyfdm,"","");
        fatherJsons.add(herbalFatherJson);
        String json = gson.toJson(fatherJsons);
        List<HerbalChildJson> childJsons = new ArrayList<>();
        for (int i = 0; i < childrenList.size(); i++) {
            CommonDrugChild child = childrenList.get(i);
            String ma_code = child.getMA_CODE();
            String jl = child.getJL();
            String dw = child.getDW();
            String bz = child.getBZ();
            String sxh = child.getSXH();
            HerbalChildJson herbalChildJson = new HerbalChildJson(ma_code, jl, dw, bz, "", "", sxh, "", "");
            childJsons.add(herbalChildJson);
        }
        String zxjson = gson.toJson(childJsons);
        saveZcycf(json, zxjson, dialog);
    }

    /*获取常用处方*/
    private void getCYCFList(String ksdm, String ysdm, String jb) {
//        Log.e("WQ", "ksdm" + ksdm + "   ysdm==" + ysdm + "  jb" + jb);
        commonDrugFathers.clear();
        Map<String, String> params = new HashMap<>();
        params.put("ksdm", ksdm);
        params.put("ysdm", ysdm);
        params.put("jb", jb);
        String url = Constant.BaseURL + "/GetCYCFList";
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
//                        Log.e("WQ", "常用==" + response);
                        Gson gson = new Gson();
                        commonDrugFathers = gson.fromJson(response, new TypeToken<List<CommonDrugFather>>() {
                        }.getType());
                        List<String> recipelNames = new ArrayList<>();
                        for (int i = 0; i < commonDrugFathers.size(); i++) {
                            CommonDrugFather commonDrugFather = commonDrugFathers.get(i);
                            String cycfmc = commonDrugFather.getCYCFMC();
                            recipelNames.add(cycfmc);
                        }
                        ArrayAdapter<String> cfFatherAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, recipelNames);
                        herbal_cfList.setAdapter(cfFatherAdapter);
                    }
                });
    }

//(新增)查询中草药药品名称
    private void SelectHerbalDrugName(String queryContent) {
        Map<String, String> params = new HashMap<>();
        params.put("queryContent", queryContent);
        String url = Constant.BaseURL + "/SelectHerbalDrugName";
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
                       progressDialog.dismiss();
                        Gson gson = new Gson();
                        try {
                            herbalDrugNameList = gson.fromJson(response, new TypeToken<List<HerbalDrugName>>() {
                            }.getType());

                            if(herbalDrugNameAdapter!=null) {
                                herbalDrugNameAdapter.setHerbalDrugNameList(herbalDrugNameList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //(新增)查询中草药药品其他信息
    private void SelectHerbalDrugOthers(String ypdm, String sl) {
        Map<String, String> params = new HashMap<>();
        params.put("ypdm", ypdm);
        params.put("sl",sl);
        String url = Constant.BaseURL + "/SelectHerbalDrugOthers";
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
                        progressDialog.dismiss();
                        Gson gson = new Gson();
                        try {
                            herbalDrugOthersList = gson.fromJson(response, new TypeToken<List<HerbalDrugOthers>>() {
                            }.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //(新增)修改中草药信息
    private void updateZcyMx(String ypdm, String dh, String bz, String jl) {
        Map<String, String> params = new HashMap<>();
        params.put("YPDM", ypdm);
        params.put("DH", dh);
        params.put("BZ", bz);
        params.put("JL", jl);
        String url = Constant.BaseURL + "/UpdateZCYMX";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                getPatientZcyCfInfoByDh(savedHerbalDH, null);
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*获取常用处方子项明细*/
    private void getCYCFInfoByCfdm(String cfdm) {
//        Log.e("WQ", "cfdm==" + cfdm);tag
        commonDrugChildList.clear();
        Map<String, String> params = new HashMap<>();
        params.put("cfdm", cfdm);
        String url = Constant.BaseURL + "/GetCYCFInfoByCfdm";
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
                        Gson gson = new Gson();
                        commonDrugChildList = gson.fromJson(response, new TypeToken<List<CommonDrugChild>>() {
                        }.getType());
                        for (int i = 0; i < patientRecipelDetailsList.size(); i++) {
                            String ypdm = patientRecipelDetailsList.get(i).getYPDM();
                            for (int j = 0; j < commonDrugChildList.size(); j++) {
                                CommonDrugChild commonDrugChild = commonDrugChildList.get(j);
                                String ma_code = commonDrugChild.getMA_CODE();
                                if (ypdm.equals(ma_code)) {
                                    commonDrugChild.setPicked(true);
                                }
                            }
                        }
                        recipelChildAdapter.refreshData(commonDrugChildList);
                    }
                });
    }

    /*获取库房字典*/
    private void getKFDM(final Spinner kuFangSpinner) {
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/GetKFDM";
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
                        Gson gson = new Gson();
                        kuFangs = gson.fromJson(response, new TypeToken<List<KuFang>>() {
                        }.getType());
                        initKuFangSpinner(kuFangSpinner);
                    }
                });
    }

    private void initKuFangSpinner(Spinner kufangSpinner) {
        String[] kufangNames = new String[kuFangs.size()];
        for (int i = 0; i < kuFangs.size(); i++) {
            kufangNames[i] = kuFangs.get(i).getKFMC();
        }
        if(kuFangs!=null&&kuFangs.size()<=0){ activityUtils.showToast("库房不能为空"); return;}
        yfdm = kuFangs.get(0).getKFDM();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, kufangNames);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
        kufangSpinner.setAdapter(adapter);
        kufangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yfdm = kuFangs.get(position).getKFDM();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /*保存中草药处方*/
    private void saveZcycf(String json, String zxjson, final AlertDialog dialog) {
        Log.e("WQ", "json==" + json);
        Log.e("WQ", "jzxson==" + zxjson);
        progressDialog.setMessage("中草药处方保存中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
      //  Log.e("WQ", unicodeJson);
       // Log.e("WQ", unicodezxjson);
        params.put("json", json);
        params.put("zxjson", zxjson);
        String url = Constant.BaseURL + "/SaveZcycf";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                getPatientZcyCf(zyh, herbal_pop_fatherList);
                                if (null != dialog) {
                                    dialog.dismiss();
                                }
                            }
                            activityUtils.showToast(bz);
                            if (response1.equals("true")) {
                                patientRecipleAdapter.refreshData(patientRecipels);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

//(新增)保存中草药明细
    //InsertZcyMx(dh,ypdm,jl,zcymxDw,bz,pc,ph,sxh,cn_flag,lsj,cddm);
    private void InsertZcyMx(final String dh, String ypdm, String jl,String zcymxDw,String bz,String pc,String ph,String sxh,String cn_flag,String lsj,String cddm,String ysdm,String ksdm) {
        progressDialog.setMessage("中草药处方明细新增中...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("dh",dh );
        params.put("ypdm",ypdm );
        params.put("jl",jl );
        params.put("zcymxDw",zcymxDw );
        params.put("bz",bz );
        params.put("pc",pc );
        params.put("ph",ph );
        params.put("sxh",sxh );
        params.put("cn_flag",cn_flag );
        params.put("lsj",lsj );
        params.put("cddm",cddm );
        params.put("ysdm",ysdm);
        params.put("ksdm",ksdm);
        Log.e(TAG, "InsertZcyMx: "+ params.toString() );
        String url = Constant.BaseURL + "/InsertCfmx";
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
                        if (!dh.equals("")) {
                            getPatientZcyCfInfoByDh(dh, null);
                        }
                        recipelDetailsAdapter.updateList(patientRecipelDetailsList);
                    }
                });
    }


    /*修改中草药处方子项*/
    private void updateZxZcycf(String ypdm, String dh, String bz, String sl) {
        progressDialog.setMessage("中草药处方明细修改中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("ypdm", ypdm);
        params.put("dh", dh);
        params.put("bz", bz);
        params.put("sl", sl);
        String url = Constant.BaseURL + "/UpdateZxZcycf";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                getPatientZcyCfInfoByDh(savedHerbalDH, null);
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
//删除中草药颗粒小项列表
    private void deleteZxZcycf(String ypdm, String dh) {
        Map<String, String> params = new HashMap<>();
        params.put("ypdm", ypdm);
        params.put("dh", dh);
        String url = Constant.BaseURL + "/DeleteZxZcycf";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                getPatientZcyCfInfoByDh(savedHerbalDH, null);
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
//删除中草药颗粒大项列表
    private void deleteFxZcycf(String dh) {
        Map<String, String> params = new HashMap<>();
        params.put("dh", dh);
        String url = Constant.BaseURL + "/DeleteFxZcycf";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                getPatientZcyCf(zyh,herbal_pop_fatherList);
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*修改中草药处方大项*/
    private void updateDxZcycf(String cfdh, String gypl, String gyfs, String jyfs, String fyyq, String qyyf, String fs, String yszt) {
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("cfdh", cfdh);
        params.put("gypl", gypl);
        params.put("gyfs", gyfs);
        params.put("jyfs", jyfs);
        params.put("fyyq", fyyq);
        params.put("qyyf", qyyf);
        params.put("fs", fs);
        params.put("yszt", yszt);
        String url = Constant.BaseURL + "/UpdateDxZcycf";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                getPatientZcyCf(zyh, herbal_pop_fatherList);
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*提交中草药处方*/
    private void submitZcycf(String zyh, final PopupWindow herbalPopWindow) {
        progressDialog.setMessage("提交中草药处方中...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        String url = Constant.BaseURL + "/SubmitZcycf";
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
                            String bz = jsonObject.getString("bz");
                            String response1 = jsonObject.getString("response");
                            if (response1.equals("true")) {
                                herbalPopWindow.dismiss();
                            }
                            activityUtils.showToast(bz);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*获取煎药方式字典*/
    private void selectBrewway(final Spinner spinner) {
        brewways.clear();
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/SelectBrewway";
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
                        Gson gson = new Gson();
                        brewways = gson.fromJson(response, new TypeToken<List<Brewway>>(){
                        }.getType());
                        initBrewaysSpinner(spinner);

                    }
                });
    }

//查询代煎方案字典
    private void selectDJFA(final Spinner spinner) {
        daiJianFangAnList.clear();
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/SelectDJFA";
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
                        Gson gson = new Gson();
                        daiJianFangAnList = gson.fromJson(response, new TypeToken<List<DaiJianFangAn>>() {
                        }.getType());
                        initDaiJianFangAnSpinner(spinner);
                    }
                });
    }



//查询煎煮剂量字典
    private void selectJZJL(final Spinner spinner) {
        jianZhuJiLiangList.clear();
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/SelectJZJL";
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
                        Gson gson = new Gson();
                        jianZhuJiLiangList = gson.fromJson(response, new TypeToken<List<JianZhuJiLiang>>() {
                        }.getType());
                        initJianZhuJiLiangSpinner(spinner);
                    }
                });
    }
    private void initBrewaysSpinner(Spinner jianyaoSpinner) {
        brewaysNames = new String[brewways.size()];
        for (int i = 0; i < brewways.size(); i++) {
            brewaysNames[i] = brewways.get(i).getJYFSMC();
        }
        brewayNo = brewways.get(0).getJYFSDM();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, brewaysNames);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
        jianyaoSpinner.setAdapter(adapter);
        jianyaoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brewayNo = brewways.get(position).getJYFSDM();
                if(brewways.get(position).getJYFSMC().equals("代煎")){
                    layout_djfa.setVisibility(View.VISIBLE);
                    layout_jzjl.setVisibility(View.VISIBLE);
                }else{
                    layout_djfa.setVisibility(View.GONE);
                    layout_jzjl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initDaiJianFangAnSpinner(Spinner daiJianFangAnSpinner) {
        daiJianFangAnNames = new String[daiJianFangAnList.size()];
        for (int i = 0; i < daiJianFangAnList.size(); i++) {
            daiJianFangAnNames[i] = daiJianFangAnList.get(i).getDJFAMC();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, daiJianFangAnNames);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
        daiJianFangAnSpinner.setAdapter(adapter);
        daiJianFangAnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initJianZhuJiLiangSpinner(Spinner spinner) {
        jianZhuJiLiangNames = new String[jianZhuJiLiangList.size()];
        for (int i = 0; i < jianZhuJiLiangList.size(); i++) {
            jianZhuJiLiangNames[i] = jianZhuJiLiangList.get(i).getYYJL();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, jianZhuJiLiangNames);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*获取中药服用要求字典*/
    private void selectRequire(final Spinner spinner) {
        requires.clear();
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/SelectRequire";
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
                        Gson gson = new Gson();
                        requires = gson.fromJson(response, new TypeToken<List<Require>>() {
                        }.getType());
                        initRequireSpinner(spinner);
                    }
                });
    }

    private void initRequireSpinner(Spinner RequireSpinner) {
        requireNames = new String[requires.size()];
        for (int i = 0; i < requires.size(); i++) {
            requireNames[i] = requires.get(i).getMC();
        }
        takeUsageNo = requires.get(0).getDM();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, requireNames);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
        RequireSpinner.setAdapter(adapter);
        RequireSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                takeUsageNo = requires.get(position).getDM();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initGyplSpinner(Spinner spinner) {
        if (!freqencyDicts.isEmpty()) {
            herbalFrequencyCode = freqencyDicts.get(0).getDM();
            initCommonSpinner(spinner, plItems);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    herbalFrequencyCode = freqencyDicts.get(position).getDM();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void initGyfsSpinner(Spinner spinner) {
        if (!usageDicts.isEmpty()) {
            herbalWaysCode = usageDicts.get(0).getDM();
            initCommonSpinner(spinner, yfItems);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    herbalWaysCode = usageDicts.get(position).getDM();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void initCommonSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /*获取患者处方大项*/
    private void getPatientZcyCf(String zyh, final ListView listView) {
        progressDialog.show();
        patientRecipels.clear();
        Map<String, String> params = new HashMap<>();
        params.put("zyh", zyh);
        String url = Constant.BaseURL + "/GetPatientZcyCf";
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
                        Gson gson = new Gson();
                        try {
                            patientRecipels = gson.fromJson(response, new TypeToken<List<PatientRecipel>>() {
                            }.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (patientRecipels.size() > 0) {
                            for (int i = 0; i < patientRecipels.size(); i++) {
                                PatientRecipel bean = patientRecipels.get(i);
                                String cfzt = bean.getCFZT();
                                String dh = bean.getDH();
                                if (null != cfzt && cfzt.equals("1")) {
                                    newRecipelNo = dh;
                                    break;
                                } else {
                                    newRecipelNo = "";
                                }
                            }
                           if (null!=patientRecipels&&patientRecipels.size()>0){
                                patientRecipels.get(0).setHerbalChecked(true);
                           }
                            patientRecipleAdapter = new PatientRecipleAdapter(context, patientRecipels);
                            listView.setAdapter(patientRecipleAdapter);

                          listView.performItemClick(listView.getChildAt(0), 0, listView.getItemIdAtPosition(0));
//                                listView.setSelector(R.color.commonBg);

                        }
                    }
                });
    }

    /*通过单号获取患者处方明细*/
    private void getPatientZcyCfInfoByDh(final String dh, final PatientRecipel copyPatientRecipe) {
        progressDialog.setMessage("处方明细获取中...");
        progressDialog.show();
        savedHerbalDH = dh;
        patientRecipelDetailsList.clear();
        Map<String, String> params = new HashMap<>();
        params.put("dh", dh);
        String url = Constant.BaseURL + "/GetPatientZcyCfInfoByDh";
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
                            Gson gson = new Gson();
                            patientRecipelDetailsList = gson.fromJson(response, new TypeToken<List<PatientRecipelDetails>>() {
                            }.getType());


                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if (null!=copyPatientRecipe){
                            copyAndSaveZcycf(patientRecipelDetailsList,copyPatientRecipe);
                        }
                        PatientRecipelDetails plus = new PatientRecipelDetails(dh,"","","","","","","","","","","");
                        patientRecipelDetailsList.add(plus);
                        recipelDetailsAdapter.updateList(patientRecipelDetailsList);
                    }
                });
    }

    private void copyAndSaveZcycf(List<PatientRecipelDetails> patientRecipelDetailsList, PatientRecipel copyPatientRecipe) {
        //制作参数
        Gson gson = new Gson();
        String jyfsdm = copyPatientRecipe.getJYFS();
        String fyyq = copyPatientRecipe.getFYYQDM();
        String yszt = copyPatientRecipe.getYSZT();
        String dh = null;
        String gypldm = copyPatientRecipe.getGYPLDM();
        String ypyfdm = copyPatientRecipe.getYPYFDM();
        String fs = copyPatientRecipe.getCFFS();
        String jyfsmc = copyPatientRecipe.getJYFSMC();
        yfdm = copyPatientRecipe.getQYYFDM();
        List<HerbalFatherJson> fatherJsons = new ArrayList<>();
        String djfa = sp_djfa.getSelectedItem().toString();
        String jzjl = sp_jzjl.getSelectedItem().toString();
        String json;

        if(jyfsmc.equals("代煎")) {
            HerbalFatherJson herbalFatherJson = new HerbalFatherJson(zyh, czydm, yfdm, docKSDM, patientKSDM, jyfsdm, fyyq, yszt, dh, fs, gypldm, ypyfdm,djfa,jzjl);
            fatherJsons.add(herbalFatherJson);
            json = gson.toJson(fatherJsons);
        }else {
            HerbalFatherJson herbalFatherJson = new HerbalFatherJson(zyh, czydm, yfdm, docKSDM, patientKSDM, jyfsdm, fyyq, yszt, dh, fs, gypldm, ypyfdm,"","");
            fatherJsons.add(herbalFatherJson);
            json = gson.toJson(fatherJsons);
        }
        List<HerbalChildJson> childJsons = new ArrayList<>();
        for(int i=0;i<patientRecipelDetailsList.size();i++){
            String jl = patientRecipelDetailsList.get(i).getDOSE();
            String dw = patientRecipelDetailsList.get(i).getDW();
            String bz = patientRecipelDetailsList.get(i).getBZ();
            String sxh = patientRecipelDetailsList.get(i).getSXH();
            String ma_code = patientRecipelDetailsList.get(i).getYPDM();
            HerbalChildJson herbalChildJson = new HerbalChildJson(ma_code, jl, dw, bz, "", "", sxh, "", "");
            childJsons.add(herbalChildJson);
        }
        String zxjson = gson.toJson(childJsons);
        saveZcycf(json, zxjson, null);
    }

    /*套医嘱*/
    /*套医嘱弹出框  initCommonHerbalDialog*/
    private void showTaoYizhuDialog() {
        View taoyizhuView = LayoutInflater.from(context).inflate(R.layout.taoyizhu_popwindow, null);
        AlertDialog taoyizhuDialog = new AlertDialog.Builder(context).create();
        taoyizhuDialog.show();
        taoyizhuDialog.setContentView(taoyizhuView);
        WindowManager.LayoutParams attributes = taoyizhuDialog.getWindow().getAttributes();
        attributes.width = (int) (700 * Common.density);
        attributes.height = (int) (480 * Common.density);
        taoyizhuDialog.getWindow().setAttributes(attributes);
        initTaoyizhuIDs(taoyizhuView, taoyizhuDialog);
    }

    private void initTaoyizhuIDs(View view, final AlertDialog alertDialog) {
        taoyizhuBeans.clear();
        final String yzbz;
        if (isLong.equals("true")) {
            yzbz = "0";
        } else {
            yzbz = "1";
        }
        getGroupMaList("2", czydm, "", yzbz);
        RadioGroup tao_rg = view.findViewById(R.id.tao_rg);
        RadioButton radioButton = (RadioButton) tao_rg.getChildAt(2);
        radioButton.setChecked(true);
        ExpandableListView tao_expandlist = view.findViewById(R.id.tao_expandlist);
        ImageView tao_iv_empty = view.findViewById(R.id.tao_iv_empty);
        final TextView tao_tv_name = view.findViewById(R.id.tao_tv_name);
        final TextView tao_tv_createMan = view.findViewById(R.id.tao_tv_createMan);
        final TextView tao_tv_createTime = view.findViewById(R.id.tao_tv_createTime);
        final SwipeRefreshLayout taoSwipe = view.findViewById(R.id.tao_swipe);
        RecyclerView tao_drugRecycler = view.findViewById(R.id.tao_drugRecycler);
        final CheckBox tao_cb_all = view.findViewById(R.id.tao_cb_all);
        ImageView tao_iv_sure = view.findViewById(R.id.tao_iv_sure);
        ImageView tao_iv_dismiss = view.findViewById(R.id.tao_iv_dismiss);
        tao_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = group.indexOfChild(group.findViewById(checkedId));
                if (position == 2) {
                    getGroupMaList(herbalRanges[position], czydm, "", yzbz);
                } else if (position == 1) {
                    getGroupMaList(herbalRanges[position], "", ksdm, yzbz);
                } else {
                    getGroupMaList(herbalRanges[position], "", "", yzbz);
                }
            }
        });
        mAdapter = new SimpleExpandableListAdapter(
                context,
                groupData,
                R.layout.simple_expandable_item2,
                new String[]{NAME, IS_EVEN},
                new int[]{android.R.id.text1, android.R.id.text2},
                childData,
                R.layout.simple_expancable_child,
                new String[]{NAME, IS_EVEN},
                new int[]{R.id.childBox}
        );
        mAdapter.setOnItemCheckedListener(new SimpleExpandableListAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(View view, int groupPosition, int childPosition) {
                TaoyizhuType.Child child = taoyizhuTypeList.get(groupPosition).getCUSTOM_CATEGORY_LIST().get(childPosition);
                String group_ma_code = child.getGROUP_MA_CODE();
                String group_ma_name = child.getGROUP_MA_NAME();
                String createperson = child.getCREATEPERSON();
                String createdate = child.getCREATEDATE();
                tao_tv_name.setText(group_ma_name);
                tao_tv_createMan.setText(createperson);
                tao_tv_createTime.setText(createdate);
                groupMaCode = group_ma_code;
                getGroupMaInfoById(group_ma_code, taoSwipe,tao_cb_all);
                mAdapter.setCheckState(groupPosition, childPosition);
            }
        });
        taoSwipe.setColorSchemeResources(android.R.color.holo_green_light);
        taoSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGroupMaInfoById(groupMaCode, taoSwipe,tao_cb_all);
            }
        });
        tao_expandlist.setEmptyView(tao_iv_empty);
        tao_expandlist.setAdapter(mAdapter);
        tao_drugRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        tao_drugRecycler.addItemDecoration(new MyItemDecoration(context, MyItemDecoration.VERTICAL_LIST));
        taoYizhuAdapter = new TaoYizhuAdapter(taoyizhuBeans, context);
        tao_drugRecycler.setAdapter(taoYizhuAdapter);
        tao_cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < taoyizhuBeans.size(); i++) {
                    taoyizhuBeans.get(i).setChecked(isChecked);
                }
                taoYizhuAdapter.updateData(taoyizhuBeans);
            }
        });
        tao_iv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TaoyizhuBean> checkedItems = taoYizhuAdapter.getCheckedItems();
                dealWithTaoyizhuJson(checkedItems, alertDialog);
            }
        });
        tao_iv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void dealWithTaoyizhuJson(List<TaoyizhuBean> list, AlertDialog alertDialog) {
        if (list.isEmpty()) {
            activityUtils.showToast("未勾选医嘱");
            return;
        }
        List<TaoYiZhuJson> taoYiZhuJsons = new ArrayList<>();
        String lx;
        if (isLong.equals("true")) {
            lx = "1";
        } else {
            lx = "0";
        }
        for (int i = 0; i < list.size(); i++) {
            TaoyizhuBean taoyizhuBean = list.get(i);
            String dm = taoyizhuBean.getDM();
            String dose = taoyizhuBean.getDOSE();
            String unit = taoyizhuBean.getUNIT();
            String frequency = taoyizhuBean.getFREQUENCY();
            String usage = taoyizhuBean.getUSAGE();
            String wzbz = taoyizhuBean.getWZBZ();
            String amount = taoyizhuBean.getAMOUNT();
            String selfprovided_medicine_flag = taoyizhuBean.getSELFPROVIDED_MEDICINE_FLAG();
            String skintest_flag = taoyizhuBean.getSKINTEST_FLAG();
            String group_no = taoyizhuBean.getGROUP_NO();
            String ybbz = taoyizhuBean.getYBBZ();
            TaoYiZhuJson taoYiZhuJson = new TaoYiZhuJson(zyh, "", czydm, docKSDM, lx, dm, dose, unit, unit, usage, frequency, wzbz, amount, "", "移动", selfprovided_medicine_flag, ybbz, skintest_flag, group_no, "");
            taoYiZhuJsons.add(taoYiZhuJson);
        }
        Gson gson = new Gson();
        String json = gson.toJson(taoYiZhuJsons);
        Log.e("WQ", "myJson==" + json);
        saveMaGroup(json, isLong, "", alertDialog);
    }

    /*获取套医嘱类别*/
    private void getGroupMaList(String jb, String czydm, String ksdm, String yzbz) {
        Log.e("WQ", "jb==" + jb + "  cyzdm==" + czydm + "  ksdm==" + ksdm + "  yzbz" + yzbz);
        groupData.clear();
        childData.clear();
        Map<String, String> params = new HashMap<>();
        params.put("jb", jb);
        params.put("czydm", czydm);
        params.put("ksdm", ksdm);
        params.put("yzbz", yzbz);
        String url = Constant.BaseURL + "/GetGroupMaList";
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
                        try {
                            Gson gson = new Gson();
                            taoyizhuTypeList = gson.fromJson(response, new TypeToken<List<TaoyizhuType>>() {
                            }.getType());
                            if (!taoyizhuTypeList.isEmpty()) {
                                for (int i = 0; i < taoyizhuTypeList.size(); i++) {
                                    TaoyizhuType taoyizhuType = taoyizhuTypeList.get(i);
                                    String custom_category_name = taoyizhuType.getCUSTOM_CATEGORY_NAME();
                                    Map<String, String> curGroupMap = new HashMap<>();
                                    groupData.add(curGroupMap);
                                    curGroupMap.put(NAME, custom_category_name);
                                    curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
                                    List<TaoyizhuType.Child> custom_category_list = taoyizhuType.getCUSTOM_CATEGORY_LIST();
                                    List<Map<String, String>> children = new ArrayList<>();
                                    for (int j = 0; j < custom_category_list.size(); j++) {
                                        TaoyizhuType.Child child = custom_category_list.get(j);
                                        String group_ma_name = child.getGROUP_MA_NAME();
                                        Map<String, String> curChildMap = new HashMap<>();
                                        children.add(curChildMap);
                                        curChildMap.put(NAME, group_ma_name);
                                        curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
                                    }
                                    childData.add(children);
                                }
                            }
                            mAdapter.refreshData(groupData, childData);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("WQ", e.toString());
                        }
                    }
                });
    }

    /*根据套医嘱代码获取套医嘱内容明细*/
    private void getGroupMaInfoById(String tyzdm, final SwipeRefreshLayout taoyizhuSwipe,CheckBox checkBox) {
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
        }
        taoyizhuBeans.clear();
        taoyizhuSwipe.setRefreshing(true);
        Map<String, String> params = new HashMap<>();
        params.put("tyzdm", tyzdm);
        String url = Constant.BaseURL + "/GetGroupMaInfoById";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        taoyizhuSwipe.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        taoyizhuSwipe.setRefreshing(false);
                        try {
                            Gson gson = new Gson();
                            taoyizhuBeans = gson.fromJson(response, new TypeToken<List<TaoyizhuBean>>() {
                            }.getType());
                            taoYizhuAdapter.updateData(taoyizhuBeans);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*保存套医嘱*/
    private void saveMaGroup(String json, String isLong, String yyzh, final AlertDialog alertDialog) {
        Map<String, String> params = new HashMap<>();
        params.put("json", json);
        params.put("isLong", isLong);
        params.put("yyzh", yyzh);
        String url = Constant.BaseURL + "/SaveMaGroup";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build().connTimeOut(30*1000).writeTimeOut(30*1000).readTimeOut(30*1000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object = jsonArray.getJSONObject(0);
                            String response1 = object.getString("response");
                            String bz = object.getString("bz");
                            if (response1.equals("true")) {
                                alertDialog.dismiss();
                                gettDocOrderInfoByZyh(zyh, flag, sjbz, true);
                            } else {
                                activityUtils.showToast(bz);
                                Log.e("WQ", "bz--" + bz);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @SuppressLint("NewApi")
    public void addSignjobgetSignResult(final String jsonstr, final AlertDialog yzbg, final String textctime, final boolean eczx , String zyh) {

        getHoldingActivity().showLoadingDialog();
        final String base64data = StringUtils.encodeToString(jsonstr.toString());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("version", CAContant.Sign_version);
        hashMap.put("appId",CAContant.Sign_APPID);
        hashMap.put("signAlgo",CAContant.Sign_signAlgo);

        hashMap.put("algo", CAContant.Sign_algorithm); // 签名任务的签名算法
        //Log.e(TAG, "jsonstr: "+jsonstr.toString().replaceAll("[\\s*\t\n\r]", "") );
        hashMap.put("data", base64data); // 待签数据StringUtils.encodeToString
        hashMap.put("dataType", "DATA"); // 待签类型（DATA：原文，HASH：hash数据，WEB_SEAL：网页签章）
        hashMap.put("description", "临时医嘱非药品执行：住院号："+zyh+" 时间："+textctime);
        String JsonString ="";
        try {
            JsonString =  CARequestUtils.generateRequestJson(hashMap, CAContant.Sign_sourceCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("wcx", "JudgeQueryCert: "+JsonString.toString() );
        FileUtils.recordToFile("CA--入参",CAContant.CA_addSignJobUrl,JsonString);
        OkHttpUtils.postString().url(CAContant.Sign_ServerUrl+CAContant.CA_addSignJobUrl).mediaType(OkHttpUtils.JSONTYPE)
                .content(JsonString).build().execute(new com.tphy.http.okhttp.callback.StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                getHoldingActivity().hideLoadingDialog();
                FileUtils.recordToFile(czydm,CAContant.CA_addSignJobUrl,e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                getHoldingActivity().hideLoadingDialog();
                try {
                    CAResAddSignJob caResponse = new Gson().fromJson(response, new TypeToken<CAResAddSignJob>() {
                    }.getType());
                    if (caResponse.getMessage().equals("SUCCESS")) {
                        String signDataId = caResponse.getData().getSignDataId();
                        goCossSignFromSdk(signDataId,base64data,jsonstr,yzbg,textctime,eczx);

                    } else {

                        getHoldingActivity().showToast(caResponse.getMessage());
                    }
                }catch (Exception e){
                    getHoldingActivity().showToast("接口数据解析失败！"+e.getMessage());
                    e.printStackTrace();
                }
                //toastLong(response);
            }
        });
    }

    @SuppressLint("NewApi")
    public void addSignjobgetSignResultBySubmit(final String jsonstr,final String zyh, final String czydm, final String isLong) {

        getHoldingActivity().showLoadingDialog();
        final String base64data = StringUtils.encodeToString(jsonstr.toString());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("version", CAContant.Sign_version);
        hashMap.put("appId",CAContant.Sign_APPID);
        hashMap.put("signAlgo",CAContant.Sign_signAlgo);
        hashMap.put("algo", CAContant.Sign_algorithm); // 签名任务的签名算法
        Log.e(TAG, "jsonstr: "+jsonstr.toString().replaceAll("[\\s*\t\n\r]", "") );
        hashMap.put("data", base64data); // 待签数据StringUtils.encodeToString
        hashMap.put("dataType", "DATA"); // 待签类型（DATA：原文，HASH：hash数据，WEB_SEAL：网页签章）
        hashMap.put("description", "长期医嘱非药品执行：住院号："+zyh+" 时间："+"");
        String JsonString ="";
        try {
            JsonString =  CARequestUtils.generateRequestJson(hashMap, CAContant.Sign_sourceCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("wcx", "JudgeQueryCert: "+JsonString.toString() );
        FileUtils.recordToFile("CA--入参",CAContant.CA_addSignJobUrl,JsonString);
        OkHttpUtils.postString().url(CAContant.Sign_ServerUrl+CAContant.CA_addSignJobUrl).mediaType(OkHttpUtils.JSONTYPE)
                .content(JsonString).build().execute(new com.tphy.http.okhttp.callback.StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                getHoldingActivity().hideLoadingDialog();
                FileUtils.recordToFile(czydm,CAContant.CA_addSignJobUrl,e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                getHoldingActivity().hideLoadingDialog();
                try {
                    CAResAddSignJob caResponse = new Gson().fromJson(response, new TypeToken<CAResAddSignJob>() {
                    }.getType());
                    if (caResponse.getMessage().equals("SUCCESS")) {
                        String signDataId = caResponse.getData().getSignDataId();
                        Log.e("wcx", "signDataId: "+signDataId );
                        goCossSignFromSdkBySubmit(signDataId,base64data,jsonstr,czydm,zyh,isLong);

                    } else {

                        getHoldingActivity().showToast(caResponse.getMessage());
                    }
                }catch (Exception e){
                    getHoldingActivity().showToast("接口数据解析失败！"+e.getMessage());
                    e.printStackTrace();
                }
                //toastLong(response);
            }
        });
    }

    private void goCossSignFromSdk(final String signDataId, final String base64data, final String jsonstr, final AlertDialog yzbg, final String excetime, final boolean eczx) {

        String userpin = MainApplication.getSign_currentuserpin();
        if (null==userpin||!userpin.split("_")[0].equals(czydm)){
            String msg_title  = "";
            msg_title = "请先输入PIN码验证";

            final View view = getHoldingActivity().getLayoutInflater().inflate(R.layout.dialog_pin, null);

            new android.app.AlertDialog.Builder(getHoldingActivity()).setMessage(msg_title).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    EditText pin = view.findViewById(R.id.act_pin);
                    Log.e("WQ", "pin"+pin.getText());
                    if (pin.getText().toString().equals("")){
                        getHoldingActivity().showToast("PIN码不能为空");
                        return;
                    }
                    cossSignWithPin(pin.getText().toString(),signDataId,base64data,jsonstr,yzbg,excetime,eczx);
                }
            }).setNegativeButton("取消", null).setView(view).setCancelable(false).show();
        }else {

            String[] userinfo = MainApplication.getSign_currentuserpin().split("_");
            cossSignWithPin(userinfo[1],signDataId,base64data,jsonstr,yzbg,excetime,eczx);

        }
    }

    private void goCossSignFromSdkBySubmit(final String signDataId, final String base64data, final String jsonstr ,final String ysdm ,final String zyh, final String isLong) {

        String userpin = MainApplication.getSign_currentuserpin();
        if (null==userpin||!userpin.split("_")[0].equals(czydm)){
            String msg_title  = "";
            msg_title = "请先输入PIN码验证";

            final View view = getHoldingActivity().getLayoutInflater().inflate(R.layout.dialog_pin, null);

            new android.app.AlertDialog.Builder(getHoldingActivity()).setMessage(msg_title).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    EditText pin = view.findViewById(R.id.act_pin);
                    Log.e("WQ", "pin"+pin.getText());
                    if (pin.getText().toString().equals("")){
                        getHoldingActivity().showToast("PIN码不能为空");
                        return;
                    }
                    cossSignWithPinBySubmit(pin.getText().toString(),signDataId,base64data,ysdm,zyh,isLong);
                }
            }).setNegativeButton("取消", null).setView(view).setCancelable(false).show();
        }else {
            String[] userinfo = MainApplication.getSign_currentuserpin().split("_");
            cossSignWithPinBySubmit(userinfo[1],signDataId,base64data,ysdm,zyh,isLong);

        }
    }
    private void cossSignWithPin(final String spin, final String signDataId, final String base64data, final String jsonstr, final AlertDialog yzbg, final String excetime, final boolean eczx) {
        CASignUserStatusDao caSignUserStatusDao = GreenDaoManager.getInstance().getSession().getCASignUserStatusDao();
        QueryBuilder<CASignUserStatus> cbuilder = caSignUserStatusDao.queryBuilder();
        List<CASignUserStatus> list = cbuilder.where(CASignUserStatusDao.Properties.Czydm.eq(czydm)).list();

        String msspidtemp =  list.size()<=0 ?"":list.get(0).getMsspid();
        SignetCossApi.getCossApiInstance(CAContant.Sign_APPID, CAContant.Sign_ServerUrl).cossSignWithPin(getHoldingActivity(),
                msspidtemp, signDataId, spin, new CossSignPinCallBack() {
                    @Override
                    public void onCossSignPin(final CossSignPinResult result) {
                        if (result.getErrCode().equalsIgnoreCase(CAContant.successCode)) {
                            getHoldingActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getHoldingActivity().showToast("签名成功");
                                    MainApplication.setSign_currentuserpin(czydm+"_"+spin);
                                    // InsertPatinetInfo(jsonstr,zyh,signDataId,result.getSignature(),result.getCert(),base64data);
                                    submitYzbgData(signDataId,result.getSignature(),result.getCert(),base64data, jsonstr,yzbg,excetime,false);
                                }
                            });
                        } else {
                            getHoldingActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getHoldingActivity().showToast(result.getErrCode() + " " + result.getErrMsg());
                                }
                            });
                        }
                    }
                });
    }

    //提交时使用的CA
    private void cossSignWithPinBySubmit(final String spin, final String signDataId, final String base64data, final String ysdm, final String zyh, final String isLong) {
        CASignUserStatusDao caSignUserStatusDao = GreenDaoManager.getInstance().getSession().getCASignUserStatusDao();
        QueryBuilder<CASignUserStatus> cbuilder = caSignUserStatusDao.queryBuilder();
        List<CASignUserStatus> list = cbuilder.where(CASignUserStatusDao.Properties.Czydm.eq(czydm)).list();

        String msspidtemp =  list.size()<=0 ?"":list.get(0).getMsspid();
        SignetCossApi.getCossApiInstance(CAContant.Sign_APPID, CAContant.Sign_ServerUrl).cossSignWithPin(getHoldingActivity(),
                msspidtemp, signDataId, spin, new CossSignPinCallBack() {
                    @Override
                    public void onCossSignPin(final CossSignPinResult result) {
                        if (result.getErrCode().equalsIgnoreCase(CAContant.successCode)) {

                            getHoldingActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getHoldingActivity().showToast("签名成功");
                                    MainApplication.setSign_currentuserpin(czydm+"_"+spin);
                                    // InsertPatinetInfo(jsonstr,zyh,signDataId,result.getSignature(),result.getCert(),base64data);
                                    submitMa(zyh,ysdm,base64data,isLong);
                                }
                            });
                        } else {
                            getHoldingActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getHoldingActivity().showToast(result.getErrCode() + " " + result.getErrMsg());
                                }
                            });
                        }
                    }
                });
    }

    private void submitYzbgData(String signDataId, String Signatrue, String strCert, String base64Data, String sjson, final AlertDialog dialogYzbg, String zxsj, final boolean eczx) {
        activityUtils.showProgressdialog(getHoldingActivity() ,"正在保存数据..");

        Log.e("wcx", "signDataId: "+signDataId );
        Log.e("wcx", "Signatrue: "+Signatrue );
        Log.e("wcx", "strCert: "+strCert );
        Log.e("wcx", "base64Data: "+base64Data );
        Log.e("wcx", "sjson: "+sjson );
        Log.e("wcx", "zxsj: "+zxsj );
        Log.e("wcx", "eczx: "+eczx+"" );
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("json",sjson);
        hashMap.put("zxsj",zxsj);
        hashMap.put("signdataid",signDataId);
        hashMap.put("signdata",base64Data);
        hashMap.put("signresult",Signatrue);
        hashMap.put("signcert",strCert);
        hashMap.put("eczxbz",eczx+"");
        FileUtils.recordToFile(czydm,"CA-入参-提交之前",sjson);
        String url = MainApplication.ydhlURL+"/InsertLsYzDo";
        OkHttpUtils.post().url(url).params(hashMap).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                activityUtils.showToast("接口异常");
                activityUtils.hideProgressdialog();
            }

            @Override
            public void onResponse(String response, int id) {
                activityUtils.hideProgressdialog();
                if (response.equals("") || response.equals("[]")) {
                    return;
                }
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                    String  result = object.getString("success");
                    if (result.equals("true")){
                        if(null!= dialogYzbg&&dialogYzbg.isShowing()) dialogYzbg.dismiss();
                        activityUtils.showToast("执行成功");
                        gettDocOrderInfoByZyh(zyh, flag, sjbz, false);
                    } else {
                        activityUtils.showToast(object.getString("bz"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    activityUtils.showToast("数据解析失败！");
                }

            }
        });
    }


    class YzbgSubmitBean{

        private String czydm;
        private String zyh;
        private String yzh;

        public String getZxsj() {
            return zxsj;
        }

        public void setZxsj(String zxsj) {
            this.zxsj = zxsj;
        }

        private String zxsj;


        public String getCzydm() {
            return czydm;
        }

        public void setCzydm(String czydm) {
            this.czydm = czydm;
        }

        public String getZyh() {
            return zyh;
        }

        public void setZyh(String zyh) {
            this.zyh = zyh;
        }

        public String getYzh() {
            return yzh;
        }

        public void setYzh(String yzh) {
            this.yzh = yzh;
        }

    }
    //医嘱提交时候CA用的类
    class YzSubmit{
        private String czydm;
        private String zyh;
        private String yzh;

        public String getCzydm() {
            return czydm;
        }

        public void setCzydm(String czydm) {
            this.czydm = czydm;
        }

        public String getZyh() {
            return zyh;
        }

        public void setZyh(String zyh) {
            this.zyh = zyh;
        }

        public String getYzh() {
            return yzh;
        }

        public void setYzh(String yzh) {
            this.yzh = yzh;
        }
    }

}
