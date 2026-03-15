package com.tphy.hospitaldoctor.ui.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseFragment;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.ui.activity.MainActivity;
import com.tphy.hospitaldoctor.ui.adapter.CardNavAdapter;
import com.tphy.hospitaldoctor.ui.adapter.HomeListAdapter;
import com.tphy.hospitaldoctor.ui.bean.CardNavBean;
import com.tphy.hospitaldoctor.ui.bean.PatientInfo;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.homeLayout)
    RelativeLayout homeLayout;
    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.home_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.card_nav_recycler)
    RecyclerView card_nav_recycler;
    private List<PatientInfo> patientInfoList;
    private List<PatientInfo> neededList;
    private HomeListAdapter adapter;
    private String mKSDM = "";
    private String mHLJB = "";
    private List<CardNavBean> cardNavBeans;
    private CardNavAdapter cardNavAdapter;
    private ActivityUtils activityUtils;
    private int loadCount;


    @Override
    protected int getLayoutID() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        adapter = new HomeListAdapter(patientInfoList, context);
        mHomeRecycler.setLayoutManager(new GridLayoutManager(context, 4));
        mHomeRecycler.setAdapter(adapter);
        adapter.setOnItemClickedListener(new HomeListAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(View view, final int position, final PatientInfo patientInfo) {
                final MainActivity holdingActivity = (MainActivity) getHoldingActivity();
                if (Constant.isSavedYizhuEmpty) {
                    holdingActivity.updateCardInfor(patientInfo);
                    resetSelectedPosition(position);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setPositiveButton("删除并切换", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holdingActivity.deleteUnSavedYizhu();
                            holdingActivity.updateCardInfor(patientInfo);
                            resetSelectedPosition(position);
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
        });
        Bundle arguments = getArguments();
        final String ksdm = arguments.getString("ksdm");
        getPatientList("", "", "", ksdm, "", "", true, false, "1", "50");
        mHomeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    loadCount++;
                    String start = String.valueOf(1 + loadCount * 50);
                    String end = String.valueOf(50 + loadCount * 50);
                    getPatientList("", "", "", ksdm, "", "", true, true, start, end);
                }
            }
        });
        initCardNavList();
    }

    private void resetSelectedPosition(int position) {
        List<PatientInfo> patientInfos = adapter.getPatientInfos();
        for (int i = 0; i < patientInfos.size(); i++) {
            if (i == position) {
                patientInfos.get(i).setSelected(true);
            } else {
                patientInfos.get(i).setSelected(false);
            }
        }
        adapter.refreshData(patientInfos);
    }

    private void initCardNavList() {
        card_nav_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        cardNavAdapter = new CardNavAdapter(cardNavBeans, context);
        card_nav_recycler.setAdapter(cardNavAdapter);
        cardNavAdapter.setOnItemClickListener(new CardNavAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int i = position * 4;
                mHomeRecycler.smoothScrollToPosition(i);
            }
        });

    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        patientInfoList = new ArrayList<>();
        neededList = new ArrayList<>();
        cardNavBeans = new ArrayList<>();
        activityUtils = new ActivityUtils(this);
    }

    /*获取首页卡片列表*/
    public void getPatientList(String czydm, String zyh, String xm, String ksdm, String hljb, String cybz, final boolean shouldSelect, final boolean loadmore, String start, String end) {
//        Log.e("WQ", "获取床位卡片==" + " 操作员代码" + czydm + " 住院号" + zyh + " 姓名" + xm + " 科室代码" + ksdm + " 护理级别" + hljb);
        if (!loadmore) {
            patientInfoList.clear();
            swipeRefreshLayout.setRefreshing(true);
        }
        this.mKSDM = ksdm;
        this.mHLJB = hljb;
        Map<String, String> params = new HashMap<>();
        params.put("czydm", czydm);
        params.put("zyh", zyh);
        params.put("xm", xm);
        params.put("ksdm", ksdm);
        params.put("hljb", hljb);
        params.put("cybz", cybz);
        params.put("rowBegin", start);
        params.put("rowEnd", end);
        Log.e(TAG, "getPatientList: "+params.toString() );
        String url = Constant.BaseURL + "/GetPatientList";
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

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onResponse(String response, int id) {
                        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
//                        swipeRefreshLayout.finishRefresh();
//                        if (loadmore) {
//                            swipeRefreshLayout.finishLoadMore();
//                        }
                        try {
                            List<PatientInfo> templist = new ArrayList<>();
                            int allPatient;
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String rn = jsonObject.getString("RN");
                                String zyh = jsonObject.getString("ZYH");
                                String blh = jsonObject.getString("BLH");
                                String zycs = jsonObject.getString("ZYCS");
                                String hzxm = jsonObject.getString("HZXM");
                                String xb = jsonObject.getString("XB");
                                String hznl = jsonObject.getString("HZNL");
                                String nldw = jsonObject.getString("NLDW");
                                String cwh = jsonObject.getString("CWH");
                                String cwdm = jsonObject.getString("CWDM");
                                String ksdm = jsonObject.getString("KSDM");
                                String ksmc = jsonObject.getString("KSMC");
                                String bmdm = jsonObject.getString("BMDM");
                                String bmmc = jsonObject.getString("BMMC");
                                String rysj = jsonObject.getString("RYSJ");
                                String ye = jsonObject.getString("YE");
                                String yj = jsonObject.getString("YJ");
                                String fy = jsonObject.getString("FY");
                                String yllx = jsonObject.getString("YLLX");
                                String ryzd = jsonObject.getString("RYZD");
                                String hljb = jsonObject.getString("HLJB");
                                String zzys = jsonObject.getString("ZZYS");/*zzys*/
                                String zrys = jsonObject.getString("ZRYS");
                                String zgys = jsonObject.getString("ZGYS");
                                String cybz = jsonObject.getString("CYBZ");
                                String hlks = jsonObject.getString("HLKS");
                                String gchs = jsonObject.getString("GCHS");
                                String gchsdm = jsonObject.getString("GCHSDM");
                                String lxrxm = jsonObject.getString("LXRXM");
                                String lxrdh = jsonObject.getString("LXRDH");
                                String zzjh = jsonObject.getString("ZZJH");
                                String ocwh = jsonObject.getString("OCWH");
                                String xx = jsonObject.getString("XX");
                                String gmyw = jsonObject.getString("GMYW");
                                String state = jsonObject.getString("STATE");
                                PatientInfo patientInfo = new PatientInfo(cwh, ksmc, zzys, zyh, hzxm, hljb, hznl, nldw, ryzd, ye, yllx, xb, blh, zycs, ksdm, bmdm, rysj, yj, fy,
                                        cwdm, zrys, zgys, cybz, hlks, gchs, gchsdm, lxrxm, lxrdh, zzjh, ocwh, bmmc,xx,gmyw,false,state);
                                patientInfo.setSelected(false);
                                templist.add(patientInfo);
                            }
                            if (!loadmore) {
                                patientInfoList = templist;
                            } else {
                                if (!templist.isEmpty()) {
                                    patientInfoList.addAll(templist);
                                }
//                                } else {
////                                    Snackbar.make(homeLayout,"已经到底了",Snackbar.LENGTH_SHORT).show();
//                                }
                            }
                            allPatient = patientInfoList.size();
                            int myPatient = 0;
                            int inHospital = 0;
                            int outHospita = 0;
                            int levelone = 0;
                            int leveltwo = 0;
                            int levelthree = 0;
                            int levelspecial = 0;
                            for (int i = 0; i < patientInfoList.size(); i++) {
                                PatientInfo patientInfo = patientInfoList.get(i);
                                String zzys = patientInfo.getZzys();
                                String zgys = patientInfo.getZgys();
                                String zrys = patientInfo.getZrys();
                                String cybz1 = patientInfo.getCybz();
                                String hljb1 = patientInfo.getHljb();
                                if (zzys.equals(czymc) || zgys.equals(czymc) || zrys.equals(czymc)) {
                                    myPatient++;
                                }
                                if (cybz1.equals("在院")) {
                                    inHospital++;
                                } else if (cybz1.equals("已出院")) {
                                    outHospita++;
                                }
                                switch (hljb1) {
                                    case "一级护理":
                                        levelone++;
                                        break;
                                    case "二级护理":
                                        leveltwo++;
                                        break;
                                    case "三级护理":
                                        levelthree++;
                                        break;
                                    case "特级护理":
                                        levelspecial++;
                                        break;
                                }
                            }

                            MainActivity holdingActivity = (MainActivity) getHoldingActivity();
                            holdingActivity.initHomeRenShu(String.valueOf(allPatient), String.valueOf(myPatient), String.valueOf(inHospital), String.valueOf(outHospita), String.valueOf(levelone), String.valueOf(leveltwo), String.valueOf(levelthree), String.valueOf(levelspecial));
                            holdingActivity.setCardList(patientInfoList);
                            if (null != adapter) {
                                if (shouldSelect) {
                                    getSelectSituation();
                                } else {
                                    adapter.refreshData(patientInfoList);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*获取mainActivity中的选择状态*/
    private void getSelectSituation() {
        MainActivity holdingActivity = (MainActivity) getHoldingActivity();
        this.mHLJB = holdingActivity.mHLJB;
        boolean isMine = holdingActivity.isMine;
        boolean isIn = holdingActivity.isIn;
        selectList(isMine, isIn, mHLJB, false, null);
    }

    /*筛选卡片列表*/
    public void selectList(boolean isMine, boolean isIn, String hljb, String charsquense) {
        List<PatientInfo> patientInfos = new ArrayList<>();
        if (charsquense.equals("")) {
            patientInfos = patientInfoList;
        } else {
            for (int i = 0; i < patientInfoList.size(); i++) {
                PatientInfo patientInfo = patientInfoList.get(i);
                String zyh = patientInfo.getZyh();
                String hzxm = patientInfo.getHzxm();
                if (zyh.contains(charsquense) || hzxm.contains(charsquense)) {
                    patientInfos.add(patientInfo);
                }
            }
        }
        selectList(isMine, isIn, hljb, true, patientInfos);
    }

    public void selectList(boolean isMine, boolean isIn, String hljb, boolean search, List<PatientInfo> list) {
//        Log.e("WQ", "开始的list" + patientInfoList.size() + "  ismine" + isMine + "  isIn" + isIn + "  hljb" + hljb);
        List<PatientInfo> firstList = new ArrayList<>();
        List<PatientInfo> originList;
        if (search) {
            originList = list;
        } else {
            originList = patientInfoList;
        }
        if (isMine) {
            if (!originList.isEmpty()) {
                for (int i = 0; i < originList.size(); i++) {
                    PatientInfo patientInfo = originList.get(i);
                    String zzys = patientInfo.getZzys();
                    String zgys = patientInfo.getZgys();
                    String zrys = patientInfo.getZrys();
                    if (zzys.equals(czymc) || zgys.equals(czymc) || zrys.equals(czymc)) {
                        firstList.add(patientInfo);
                    }
                }
            }
        } else {
            firstList = originList;
        }
        List<PatientInfo> secondList = new ArrayList<>();
        if (isIn) {
            for (int i = 0; i < firstList.size(); i++) {
                PatientInfo patientInfo = firstList.get(i);
                String cybz = patientInfo.getCybz();
                if (cybz.equals("在院")) {
                    secondList.add(patientInfo);
                }
            }
            card_nav_recycler.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < firstList.size(); i++) {
                PatientInfo patientInfo = firstList.get(i);
                String cybz = patientInfo.getCybz();
                if (cybz.equals("已出院")) {
                    secondList.add(patientInfo);
                }
            }
            card_nav_recycler.setVisibility(View.GONE);
        }
        List<PatientInfo> thirdList = new ArrayList<>();
        for (int i = 0; i < secondList.size(); i++) {
            PatientInfo patientInfo = secondList.get(i);
            String hljb1 = patientInfo.getHljb();
            if (hljb.equals("")) {
                thirdList = secondList;
            } else {
                if (hljb.equals(hljb1)) {
                    thirdList.add(patientInfo);
                }
            }
        }
        neededList = thirdList;
        initCardNavBeans(thirdList);
        adapter.refreshData(thirdList);
        MainActivity holdingActivity = (MainActivity) getHoldingActivity();
        holdingActivity.setCardList(neededList);
    }

    /*重置首页选择的条目*/
    public void resetHomeSelectedItem(List<PatientInfo> patientInfos) {
        if (null != adapter) {
            adapter.refreshData(patientInfos);
        }
    }

    /*滑动到初始*/
    public void scrollToSelectedPosition(int position) {
        if (mHomeRecycler != null) {
            mHomeRecycler.scrollToPosition(position);
        }
    }

    @Override
    public void onRefresh() {
        loadCount = 0;
        getSelectSituation();
        getPatientList("", "", "", mKSDM, "", "", true, false, "1", "50");
    }

    /*传递所选卡片的信息*/
    private void initCardNavBeans(List<PatientInfo> list) {
        cardNavBeans.clear();
        int count;
        if (list.size() % 4 == 0) {
            count = list.size() / 4;
        } else {
            count = list.size() / 4 + 1;
        }
        for (int i = 0; i < count; i++) {
//            String start = String.valueOf(i * 4 );
//            String end = String.valueOf(i * 4 + 3);
            int start = i * 4;
            int end = i * 4 + 3;
            String cwh = list.get(start).getCwh();
            String cwh1;
            if (end > list.size() - 1) {
                cwh1 = list.get(list.size() - 1).getCwh();
            } else {
                cwh1 = list.get(end).getCwh();
            }
            CardNavBean cardNavBean = new CardNavBean(cwh, cwh1);
            cardNavBeans.add(cardNavBean);
        }
        cardNavAdapter.refresh(cardNavBeans);
    }

    /*根据卡片床位号从小到大排序*/
    private List<PatientInfo> lineUp(List<PatientInfo> list) {
//        Log.e("WQ", "hah");
        int[] a = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            a[i] = Integer.parseInt(list.get(i).getOcwh());
        }
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
            }
        }
        List<PatientInfo> infos = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                PatientInfo patientInfo = list.get(j);
                String ocwh = patientInfo.getOcwh();
                if (a[i] == Integer.parseInt(ocwh)) {
                    infos.add(patientInfo);
                }
            }

        }
        return infos;
    }

    /*根据扫描出来的住院号筛选患者*/
    public PatientInfo pickPatientFromQRCode(String zhuyuanhao) {
        PatientInfo patientInfo = null;
        for (int i = 0; i < patientInfoList.size(); i++) {
            PatientInfo temp = patientInfoList.get(i);
            String zyh = temp.getZyh();
            if (zyh.equals(zhuyuanhao)) {
                patientInfo = temp;
            }
        }
        return patientInfo;
    }

    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
