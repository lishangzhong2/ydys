package com.tphy.hospitaldoctor.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.bjca.CAContant;
import com.tphy.hospitaldoctor.bjca.CARequestUtils;
import com.tphy.hospitaldoctor.bjca.CaResGetAuthCode;
import com.tphy.hospitaldoctor.bjca.CaResQueryUserInfo;
import com.tphy.hospitaldoctor.common.base.BaseAppManager;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.service.DownloadService;
import com.tphy.hospitaldoctor.ui.bean.KeShi;
import com.tphy.hospitaldoctor.ui.dao.CASignUserStatus;
import com.tphy.hospitaldoctor.ui.dao.CASignUserStatusDao;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.FileUtils;
import com.tphy.hospitaldoctor.utils.GreenDaoManager;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.http.okhttp.OkHttpUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.org.bjca.signet.coss.api.SignetCossApi;
import cn.org.bjca.signet.coss.bean.CossGetCertResult;
import cn.org.bjca.signet.coss.bean.CossReqCertResult;
import cn.org.bjca.signet.coss.component.core.enums.CertType;
import cn.org.bjca.signet.coss.interfaces.CossGetCertCallBack;
import cn.org.bjca.signet.coss.interfaces.CossReqCertCallBack;
import okhttp3.Call;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class NewLoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressBar mProgressView;
    private Button mEmailSignInButton;
    private SharedPreferences.Editor editor;
    private List<KeShi> keShiList;
    private BaseAppManager instance;
    private String versionName;
    private AlertDialog installDialog;
    private String versionURL;
    private String updateInfo = "";
    private ActivityUtils activityUtils;
    private ServiceConnection serviceConnection;
    private ProgressBar updateProgress;
    private View installView;
    private TextView tv_percent;
    private TextView tv_updateInfo;
    private AlertDialog PREDialog;
    private String chooseMsspidtemp = "";
    private LinearLayout ll_pwd;
    private ImageView iv_moreunfold;
    private List<String> userNames ;
    private  HashMap<String, String> userListMap;
    private boolean IsshowingAutotext = false;
    private TextView tv_xzzs,tv_wjmm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        activityUtils = new ActivityUtils(this);
        instance = BaseAppManager.getInstance();
        instance.addActivity(this);
        instance.clearToLogin();
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        ImageView login_bg = findViewById(R.id.login_bg);
        login_bg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                initDialog();
                return false;
            }
        });
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
//                    beforelogin();
                    return true;
                }
                return false;
            }
        });
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 attemptLogin();
//               beforelogin();
            }
        });
        mProgressView = findViewById(R.id.login_progress);
        ll_pwd = findViewById(R.id.ll_pwd);
        tv_wjmm = findViewById(R.id.tv_wjmm);
        iv_moreunfold = findViewById(R.id.iv_moreunfold);
        tv_xzzs = findViewById(R.id.tv_xzzs);
        keShiList = new ArrayList<>();
        userNames = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initOpera();
//        本地存储
        initSharedPfreference();
        checkUpdate();
        getVerson();
        activityUtils.showToast(Constant.BaseURL);
        GetUserListResult();
    }

    private void initOpera() {
        /*将password里面的值清空*/
        mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!userNames.contains(s.toString().trim())){
                    mPasswordView.setText("");
                }

            }
        });
        //如果点了删除键 应该是想要重新输入用户 这个时候 清空当先的 chooseMsspidtemp
//        mEmailView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
//                if(keyCode == KeyEvent.KEYCODE_DEL) {
//                    //chooseMsspidtemp= "";
//                    ll_pwd.setVisibility(View.GONE);
//                    //mPasswordView.setVisibility(View.GONE);
//                }
//                if(mEmailView.getText().toString().trim().equals("")){
//                    initUserCacheData(false);
//                }
//
//                return false;
//            }});

        iv_moreunfold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsshowingAutotext){//如果正在显示下拉列表
                    mEmailView.dismissDropDown();
                    //iv_moreunfold.setBackgroundDrawable(getResources().getDrawable(R.drawable.more_unfold));
                    iv_moreunfold.setBackgroundResource(R.mipmap.more_unfold);
                }else {
                    mEmailView.showDropDown();
                    iv_moreunfold.setBackgroundResource(R.mipmap.less);

                }
                IsshowingAutotext = !IsshowingAutotext;
                chooseMsspidtemp ="";
                tv_xzzs.setVisibility(View.GONE);
            }
        });
        mEmailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //paassword.setText(userspwds.get(position));

                mPasswordView.setText("");
                iv_moreunfold.setBackgroundResource(R.mipmap.more_unfold);
                IsshowingAutotext =false;
                String inputname = mEmailView.getText().toString().trim();
                String usernametemp = "";
                if (null!=userListMap&&userListMap.size()>0) {
                    //变量通过value  找到key
                    Set<Map.Entry<String, String>> entries = userListMap.entrySet();
                    for (Map.Entry<String, String> mas:entries) {
                        // 如果 使用usernams.getposition 适配器的数据没有跟着 输入刷新
                        String usname =inputname ; // userNames.get(position).replace("★", "");
                        boolean equals = mas.getKey().equals(usname);
                        if (equals){
                            chooseMsspidtemp = mas.getValue();
                            usernametemp = usname;
                            break;
                        }
                    }
                    judgeGetCertFromSDK(chooseMsspidtemp,usernametemp);
                    SaveCertByMsspid(null,chooseMsspidtemp,usernametemp,false);
                }
                Log.e("wcx", "onItemClick: "+inputname );

            }
        });
    }

    private void checkUpdate() {
        installDialog = new AlertDialog.Builder(this).create();
        installDialog.setCanceledOnTouchOutside(false);
        installDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                activityUtils.showToast("请更新程序版本");
                return keyCode == KeyEvent.KEYCODE_BACK && null != installDialog && installDialog.isShowing();
            }
        });
        installView = LayoutInflater.from(this).inflate(R.layout.dialog_update, null);
        TextView tv_update = installView.findViewById(R.id.newTvUpdate);
        tv_percent = installView.findViewById(R.id.tv_percent);
        tv_updateInfo = installView.findViewById(R.id.tv_updateInfo);
        updateProgress = installView.findViewById(R.id.newProgressBar);
        tv_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgress.setVisibility(View.VISIBLE);
                downloadApk(versionURL);
            }
        });
    }

    private void downloadApk(final String versionURL) {
        if (null == serviceConnection)
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                    DownloadService mService = binder.getService();
                    mService.downApk(versionURL, new DownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {

                        }

                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onProgress(int progress) {
                            tv_percent.setText(String.format("%d%%", progress));
                            updateProgress.setProgress(progress);
                        }

                        @Override
                        public void onComplete(File file) {
                            installAPK(file, NewLoginActivity.this);
                        }

                        @Override
                        public void onFail(String msg) {
                            activityUtils.showToast(msg);
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    /*设置IP的dialog*/
    private void initDialog() {
        View ipView = LayoutInflater.from(NewLoginActivity.this).inflate(R.layout.dialog_setip, null);
        final EditText et1 = ipView.findViewById(R.id.ip_et1);
        final EditText et2 = ipView.findViewById(R.id.ip_et2);
        et1.setText(Constant.BaseURL.split("/")[2].toString());
        et2.setText(Constant.BaseURL.split("/")[3].toString());
        TextView dismiss = ipView.findViewById(R.id.ip_tv_dismiss);
        TextView confirm = ipView.findViewById(R.id.ip_tv_confirm);
        AlertDialog.Builder builder = new AlertDialog.Builder(NewLoginActivity.this);
        final AlertDialog ipdialog = builder.create();
        ipdialog.setView(new EditText(NewLoginActivity.this));
        ipdialog.show();
        ipdialog.getWindow().setContentView(ipView);
        ipdialog.setCanceledOnTouchOutside(false);
        dismiss.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ipdialog.dismiss();
            }
        });
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et1.getText().toString();
                String s1 = et2.getText().toString();

                if (!s.equals("") || !s1.equals("")) {
                    String ip = "http://" + s + "/" + s1 + "/mdwebservice/mdservice.asmx";
                    editor.putString(Constant.MYURL, ip);
                    editor.commit();
                    Constant.BaseURL = ip;
//                    Log.e("WQ", "IP  ===" + Constant.BaseURL);
                    ipdialog.dismiss();
                } else {
                    Toast.makeText(NewLoginActivity.this, "IP地址不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /*初始化本地缓存*/
    private void initSharedPfreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        String myurl = sharedPreferences.getString(Constant.MYURL, "");
        String mAccount = sharedPreferences.getString("ACCOUNT", "");
        String mPassword = sharedPreferences.getString("PASSWORD", "");
        if (!TextUtils.isEmpty(myurl)) {
            Constant.BaseURL = myurl;
        }
        if (!TextUtils.isEmpty(mAccount)) {
            mEmailView.setText(mAccount);
        }
        if (!TextUtils.isEmpty(mPassword)) {
            mPasswordView.setText(mPassword);
        }
        editor = sharedPreferences.edit();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    private void beforelogin(){
        String czydm = mEmailView.getText().toString().trim();
        if(ll_pwd.getVisibility()==View.VISIBLE){
            //   attemptLogin(serverUrl,paassword.getText().toString().trim());
            attemptLogin();
        }else {
            String msspid = JudgeFirstLogin();
            if (msspid.equals("")) {
                //toastShort("初次登录，请先下载证书");
                // 根据账号找msspid ，去服务器上找用户拿到msspid
                getMsspidByCzydm(czydm);
                JudgeCaUserStatus(czydm);
            } else {
                // 说明登录过
                judgeGetCertFromSDK(msspid, mEmailView.getText().toString());
            }

        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid()) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {


            showProgress(true);
            login(email, password);
        }
    }

    private boolean isEmailValid() {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        mEmailSignInButton.setVisibility(show ? View.GONE : View.VISIBLE);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    public void forgotpwd(View view) {

        if (chooseMsspidtemp.equals("")){
            activityUtils.showToast("用户msspid 不能为空");
            return;
        }
        getAuthcode(chooseMsspidtemp);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
    }

    /*登录*/
    public void login(final String account, final String password) {
        String ip = Common.getIP(this);
        Map<String, String> params = new HashMap<>();
        params.put("czydm", account);
        params.put("pwd", password);
        params.put("padip", ip);
        params.put("dcbz", "0");
        String url = Constant.BaseURL + "/LoginHandle";
//        OkHttpUtils.get().url("http://192.168.1.168/bzsyjh/json.txt").build().execute(new StringCallback(){
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build().connTimeOut(30*1000).readTimeOut(30*1000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mProgressView.setVisibility(View.GONE);
                        mEmailSignInButton.setVisibility(View.VISIBLE);
                        activityUtils.showToast(Common.getException(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        keShiList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject o = (JSONObject) jsonArray.get(0);
                            String response1 = o.getString("response");
                            if (response1.equals("true")) {
                                editor.putString(Constant.SP_ACCOUNT, account);
                                String czydm = o.getString("czydm");//操作员代码
                                String czymc = o.getString("czymc");//操作员名称
                                String ksdm = o.getString("ksdm");//科室代码
                                String ksmc = o.getString("ksmc");//科室名称
                                String jb = o.getString("jb");//级别
                                String jmkl = o.getString("jmkl");//级别
                                JSONArray kslb = o.getJSONArray("kslb");
                                for (int i = 0; i < kslb.length(); i++) {
                                    JSONObject jsonObject = kslb.getJSONObject(i);
                                    String dept_id = jsonObject.getString("DEPT_ID");
                                    String dept_name = jsonObject.getString("DEPT_NAME");
                                    String time_limit = jsonObject.getString("TIME_LIMIT");
                                    String ksrs = jsonObject.getString("KSRS");
                                    KeShi keShi = new KeShi(dept_id, dept_name, time_limit, ksrs);
                                    keShiList.add(keShi);
                                }
                                editor.putString("czydm", czydm);
                                editor.putString("czymc", czymc);
                                editor.putString("ksdm", ksdm);
                                editor.putString("ksmc", ksmc);
                                editor.putString("jb", jb);
                                editor.putString("jmkl", jmkl);
                                editor.putString("ACCOUNT", account);
                                editor.putString("PASSWORD", password);
                                editor.commit();
                                Intent intent = new Intent(NewLoginActivity.this, MainActivity.class);
                                intent.putExtra("keshi", (Serializable) keShiList);
                                startActivity(intent);
                            } else {
                                String bz = o.getString("bz");
                                mPasswordView.setError(bz);
                                mPasswordView.requestFocus();
                                mProgressView.setVisibility(View.GONE);
                                mEmailSignInButton.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*获取版本号*/
    private void getVerson() {
        Map<String, String> params = new HashMap<>();
        String url = Constant.BaseURL + "/GetVerson";
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        activityUtils.showToast("网络错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject jsonObject = array.getJSONObject(0);
                            versionName = jsonObject.getString("response");
                            versionURL = jsonObject.getString("bz");
                            updateInfo = jsonObject.getString("gxsm");
                            String appVersionName = Common.getAppVersionName(NewLoginActivity.this);
                            float onLineVersion = Float.parseFloat(versionName);
                            float myVersion = Float.parseFloat(appVersionName);
                            if (onLineVersion > myVersion) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if (getPackageManager().canRequestPackageInstalls()){
                                        PREDialog = new AlertDialog.Builder(NewLoginActivity.this)
                                                .setTitle("设置")
                                                .setCancelable(false)
                                                .setMessage("请先允许运行必要的权限（如存储，安装应用等），否则程序将无法正常使用")
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        NewLoginActivity.this.finish();
                                                    }
                                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Uri uri = Uri.parse("package:"+getPackageName());
                                                        Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS,uri);
                                                        startActivityForResult(intent, 19901);
                                                    }
                                                })
                                                .create();
                                        PREDialog.show();
                                        return;
                                    }

                                }
                                showInstallDialog();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void showInstallDialog(){

        tv_updateInfo.setText(updateInfo);
        installDialog.show();
        installDialog.setContentView(installView);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null!=PREDialog&&PREDialog.isShowing()) PREDialog.dismiss();
        showInstallDialog();
        if (resultCode!=RESULT_OK){
            activityUtils.showToast("没有给予权限，将无法更新程序！");
        }
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    /*安装apk*/
    protected void installAPK(File file, Context context) {
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //在服务中开启actiivity必须设置flag
            Uri contentUri = FileProvider.getUriForFile(context, "com.tphy.hospitaldoctor", file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Uri uri = Uri.parse("file://" + file.toString());
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        instance.removeActivity(this);
    }


    private void getAuthcode(String msspid) {

        activityUtils.showToast("产生激活码");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId",msspid);

        hashMap.put("version",CAContant.Sign_version);
        hashMap.put("appId",CAContant.Sign_APPID);
        hashMap.put("signAlgo",CAContant.Sign_signAlgo);
        hashMap.put("transId","1234567");
        String JsonString ="";
        try {
            JsonString =  CARequestUtils.generateRequestJson(hashMap, CAContant.Sign_sourceCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("wcx", "getAuthcode: "+JsonString.toString() );
        FileUtils.recordToFile("CA--入参",CAContant.CA_getAuthCode,JsonString.toString());
        OkHttpUtils.postString().url(CAContant.Sign_ServerUrl+CAContant.CA_getAuthCode).mediaType(OkHttpUtils.JSONTYPE)
                .content(JsonString).build().execute(new com.tphy.http.okhttp.callback.StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                activityUtils.hideProgressdialog();
                Log.e("wcx", "onError: "+CAContant.CA_getAuthCode);
                FileUtils.recordToFile(mEmailView.getText().toString(), CAContant.CA_getAuthCode,e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                activityUtils.hideProgressdialog();
                Log.e("wcx", "onResponse: "+response );
                try {
                    CaResGetAuthCode caResponse = new Gson().fromJson(response, new TypeToken<CaResGetAuthCode>() {
                    }.getType());
                    if (caResponse.getMessage().equals("SUCCESS")) {
                        String authCode = caResponse.getData().getAuthCode();
                        aossReqCert(authCode);

                    } else {
                        //FileUtil.recordToFile(number.getText(),"getsuthcode",caResponse.getMessage());
                        activityUtils.showToast(caResponse.getMessage());
                    }
                }catch (Exception e){
                    activityUtils.showToast("接口数据解析失败！"+e.getMessage());
                    e.printStackTrace();
                }
                //toastLong(response);
            }
        });
    }


    //传递激活码激活证书
    private void aossReqCert(String authCode) {

        SignetCossApi.getCossApiInstance(CAContant.Sign_APPID, CAContant.Sign_ServerUrl)
                .cossReqCert(NewLoginActivity.this, authCode, new CossReqCertCallBack() {
                    @Override
                    public void onCossReqCert(final CossReqCertResult result) {
                        if (result.getErrCode().equalsIgnoreCase(CAContant.successCode)) {
                            activityUtils.showToast("激活成功");
                            downloadCertAndSave(result.getMsspID(),mEmailView.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ll_pwd.setVisibility(View.VISIBLE);
                                    activityUtils.showToast("激活成功，请从新选择要登录的用户");
                                    //number.setText("");
                                    GetUserListResult();
                                }
                            });
                        } else if (result.getErrCode().equalsIgnoreCase("0x81400003")) {
                            activityUtils.showToast(result.getErrMsg());
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    activityUtils.showToast(result.getErrCode() + " " + result.getErrMsg());
                                }
                            });
                        }
                    }
                });
    }

    private void downloadCertAndSave(final String msspID, final String name) {

        SignetCossApi.getCossApiInstance(CAContant.Sign_APPID, CAContant.Sign_ServerUrl)
                .cossGetCert(NewLoginActivity.this, msspID, CertType.ALL_CERT, new CossGetCertCallBack() {
                    @Override
                    public void onGetCert(CossGetCertResult result) {
                        if (result.getErrCode().equalsIgnoreCase(CAContant.successCode)) {
                            String scert = result.getCertMap().get(CertType.SM2_SIGN_CERT);
                            if (null!=scert&&!scert.equals("")) {
                                SaveCertByMsspid(scert, msspID, name,true);
                            }else {
                                activityUtils.showToast("未找到本地的有效证书，存储失败");
                            }
                        } else {
                            activityUtils.showToast(result.getErrCode() + " " + result.getErrMsg());
                        }
                    }
                });

    }

    private void SaveCertByMsspid(String crettemp, String msspidtemp, String usernametemp,boolean firstinsert) {
        CASignUserStatusDao caSignUserStatusDao = GreenDaoManager.getInstance().getSession().getCASignUserStatusDao();
        CASignUserStatus hasbena = caSignUserStatusDao.load(usernametemp);
        if (null == hasbena) {
            //第一次插入
            if (firstinsert){
                caSignUserStatusDao.insertInTx(new CASignUserStatus(usernametemp, msspidtemp, "1", usernametemp, crettemp, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            }else {
                //为空的时候插入是 相同操作員不同賬號
                // List<CASignUserStatus> caSignUserStatuses = caSignUserStatusDao.loadAll();
                QueryBuilder<CASignUserStatus> qb = caSignUserStatusDao.queryBuilder();
                List<CASignUserStatus> list  = qb.where(CASignUserStatusDao.Properties.Msspid.eq(msspidtemp)).list();
                if (null==list||list.size()==0) {
                    activityUtils.showToast("初始化操作员数据失败");
                }else {
                    CASignUserStatus cabe = list.get(0);
                    caSignUserStatusDao.insertInTx(new CASignUserStatus(usernametemp, msspidtemp, "1", usernametemp, cabe.getCert(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

                }
            }
        }else {
            if (null==crettemp) {
                crettemp = hasbena.getCert();
            }
            caSignUserStatusDao.updateInTx(new CASignUserStatus(usernametemp, msspidtemp, "1", usernametemp, crettemp, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

        }

        initUserCacheData(true);
    }

    /**
     * 缓存的患者登录的账号数据
     * @param loadselection
     */
    private void initUserCacheData(boolean loadselection) {

        CASignUserStatusDao caSignUserStatusDao = GreenDaoManager.getInstance().getSession().getCASignUserStatusDao();
        QueryBuilder<CASignUserStatus> caqbuilder = caSignUserStatusDao.queryBuilder();
        List<CASignUserStatus> list = caqbuilder.orderDesc(CASignUserStatusDao.Properties.Active_time).list();
        if (null==list||list.size()==0){
            iv_moreunfold.setVisibility(View.GONE);
        }

        for (int j = 0; j < list.size(); j++) {
            //如果是初始化页面进来的默认选中 否则是删除的时候 不知道加载
            if (loadselection) {
                if (j == 0) {
                    mEmailView.setText(list.get(j).getCzydm());
                    mEmailView.setSelection(list.get(j).getCzydm().length());
                    //paassword.setText(stringc.split("★")[1]);
                }
            }
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, userNames);
        mEmailView.setThreshold(1);
        mEmailView.setAdapter(stringArrayAdapter);

    }


    @SuppressLint("NewApi")
    private void GetUserListResult() {
//		SignetCossApi.getCossApiInstance(HomeApplication.Sign_APPID, HomeApplication.Sign_ServerUrl)
//				//.cossGetUserState(getAct(), etMsspId.getText().toString().trim(), new CossGetUserStateCallBack() {
//				.cossGetUserState(getAct(), "", new CossGetUserStateCallBack() {
//					@Override
//					public void onGetUserState(final CossGetUserStateResult result) {
//						if (result.getErrCode().equalsIgnoreCase(successCode)) {
//							toastShort("获取用户状态成功，用户状态码：" + result.getUserStateCode());
//
//
//						} else {
//							toastShort("获取用户状态失败，错误码：" + result.getErrCode());
//						}
//					}
//				});
        userNames.clear();
//		CossGetUserListResult result = SignetCossApi.getCossApiInstance(HomeApplication.Sign_APPID, HomeApplication.Sign_ServerUrl)
//				.cossGetUserList(getAct());
//		if (result.getErrCode().equalsIgnoreCase(successCode)) {
//			//etUserList.setText(result.getUserListMap().toString());
//			Log.e(TAG, "GetUserListResult: "+result.getUserListMap().size() );
//			if (result.getUserListMap().size()==0){
//				toastShort("本地暂无用户证书，请先注册证书！");
//			}else {
//
//				userListMap = result.getUserListMap();
//				Set<String> userkeys = userListMap.keySet();
//				for (String key: userkeys) {
//					//Log.e(TAG, "GetUserListResult: "+key +"=="+userListMap.get(key) );
//					//saveUserNumPwd(userListMap.get(key),"");
//					userNames.add(userListMap.get(key));
//				}
//
//			}
//		} else {
//			toastShort(result.getErrCode() + " " + result.getErrMsg());
//			//etUserList.setText(result.getErrCode() + " " + result.getErrMsg());
//		}

        CASignUserStatusDao caSignUserStatusDao = GreenDaoManager.getInstance().getSession().getCASignUserStatusDao();
        QueryBuilder<CASignUserStatus> caqbuilder = caSignUserStatusDao.queryBuilder();
        List<CASignUserStatus> list = caqbuilder.orderDesc(CASignUserStatusDao.Properties.Active_time).list();
        if (null==list||list.size()==0){
            activityUtils.showToast("本地暂无用户证书，请先注册证书！");
        }else {

            userListMap = new HashMap<>();
            for (CASignUserStatus key: list) {
                userListMap.put(key.getCzydm(), key.getMsspid());
                //Log.e(TAG, "GetUserListResult: "+key +"=="+userListMap.get(key) );
                //saveUserNumPwd(userListMap.get(key),"");
                userNames.add(key.getCzydm());
            }
        }

        //初始化账号缓存数据
        initUserCacheData(true);

    }

    //从sdk 中获取用户状态
    private void judgeGetCertFromSDK(final String Msspidtemp, final String usernametemp) {

        SignetCossApi.getCossApiInstance(CAContant.Sign_APPID, CAContant.Sign_ServerUrl)
                .cossGetCert(NewLoginActivity.this, Msspidtemp, CertType.ALL_CERT, new CossGetCertCallBack() {
                    @Override
                    public void onGetCert(CossGetCertResult result) {
                        chooseMsspidtemp = Msspidtemp;
                        if (result.getErrCode().equalsIgnoreCase(CAContant.successCode)) {
                            String scert = result.getCertMap().get(CertType.SM2_SIGN_CERT);
                            if (null!=scert&&!scert.equals("")){
                                activityUtils.showToast("可以登录");
                                ll_pwd.setVisibility(View.VISIBLE);
                                tv_xzzs.setVisibility(View.GONE);
                                tv_wjmm.setVisibility(View.VISIBLE);
                                SaveCertByMsspid(scert,Msspidtemp,mEmailView.getText().toString().trim(),false);
                                return;
                            }else {
                                ll_pwd.setVisibility(View.GONE);
                                tv_xzzs.setVisibility(View.VISIBLE);
                                tv_wjmm.setVisibility(View.GONE);

                                activityUtils.showToast("未找到证书，点击下载证书！");
                            }
                        }else {
                            //toastShort(result.getErrCode() + " " + result.getErrMsg());
                            ll_pwd.setVisibility(View.GONE);
                            tv_xzzs.setVisibility(View.VISIBLE);
                            tv_wjmm.setVisibility(View.GONE);
                            activityUtils.showToast("未找到证书，点击下载证书！");
                        }

                    }
                });
//		//判斷证书狀態
//		SignetCossApi.getCossApiInstance(HomeApplication.Sign_APPID, HomeApplication.Sign_ServerUrl)
//				.cossGetUserState(getAct(), Msspidtemp, new CossGetUserStateCallBack() {
//					@Override
//					public void onGetUserState(final CossGetUserStateResult result) {
//						if (result.getErrCode().equalsIgnoreCase(successCode)) {
//							//JudgeQueryCert( Msspidtemp, usernametemp);
//							toastShort("可以登录");
//
//							paassword.setVisibility(View.VISIBLE);
//							tv_xzzs.setVisibility(View.GONE);
//						} else {
//							paassword.setVisibility(View.GONE);
//							tv_xzzs.setVisibility(View.VISIBLE);
//							chooseMsspidtemp =Msspidtemp;
//							toastShort("获取用户状态失败，错误码："+ result.getErrCode()+"");
//							toastShort("点击下载证书重新下载");
//
//						}
//					}
//				});
    }

    //判断是否是第一次登录
    private String JudgeFirstLogin() {
        return GetMsspid(mEmailView.getText().toString().trim());

    }

    public String GetMsspid(String czydm){
        //从本地数据库中取出来
        CASignUserStatusDao caSignUserStatusDao = GreenDaoManager.getInstance().getSession().getCASignUserStatusDao();

        QueryBuilder<CASignUserStatus> cbuilder = caSignUserStatusDao.queryBuilder();
        //czydm = "41272819920421331X";
        //List<CASignUserStatus> list = cbuilder.where(CASignUserStatusDao.Properties.Czydm.eq(czydm)).list();
        List<CASignUserStatus> list = cbuilder.where(CASignUserStatusDao.Properties.Czydm.eq(czydm)).list();

        return  list.size()<=0 ?"":list.get(0).getMsspid();
    }

    private void getMsspidByCzydm(String czydm) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("czydm", czydm);
        String url = Constant.BaseURL2222 + "/getMsspidByCzydm";
        OkHttpUtils.post().params(hashMap).url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                activityUtils.showToast("接口异常,未找到用户的Msspid，请联系工程师！");
            }

            @Override
            public void onResponse(String response, int id) {
                if (response.equals("[]")){
                    activityUtils.showToast("未找到用户的Msspid，请联系工程师！");
                    return;
                }else {
                    //测试使用
                    // attemptLogin();
                    //20210621
                    JudgeCaUserStatus(response);
                }
            }
        });


    }

    // 调Ca接口 判断用户状态
    private void JudgeCaUserStatus(String msspid) {
        activityUtils.showProgressdialog(NewLoginActivity.this,"检查用户状态");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idNumber","");
        hashMap.put("idType","");
        //hashMap.put("userId","");
        //hashMap.put("userId","90bb887d1d8e52245ce142c30c3fdef5374f95fde02bf913318bee1e58d310e1");
        hashMap.put("userId",msspid);
        //	hashMap.put("uniqueId",czydm);
        hashMap.put("uniqueId","");
        hashMap.put("appId",CAContant.Sign_APPID);
        hashMap.put("version", CAContant.Sign_version);
        hashMap.put("signAlgo", CAContant.Sign_signAlgo);
        hashMap.put("transId", "1234567");
        String JsonString ="";
        try {
            JsonString =  CARequestUtils.generateRequestJson(hashMap, CAContant.Sign_sourceCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUtils.recordToFile("CA--入参",CAContant.CA_QueryUserInfo,JsonString);
        OkHttpUtils.postString().url(CAContant.Sign_ServerUrl+CAContant.CA_QueryUserInfo).mediaType(OkHttpUtils.JSONTYPE)
                .content(JsonString).build().execute(new com.tphy.http.okhttp.callback.StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                activityUtils.hideProgressdialog();
                Log.e("wcx", "onError: "+CAContant.CA_QueryUserInfo);
                FileUtils.recordToFile(mEmailView.getText().toString(),CAContant.CA_QueryUserInfo,e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                activityUtils.hideProgressdialog();
                CaResQueryUserInfo caResponse = new Gson().fromJson(response,new TypeToken<CaResQueryUserInfo>(){}.getType());
                if (caResponse.getMessage().equals("SUCCESS")) {
                    //将msspid 存到 设备的数据库上
                    judgeGetCertFromSDK(caResponse.getData().getUserId(), mEmailView.getText().toString());
                    //getAuthcode(cbean.getUserId());
                }else {
                    //FileUtil.recordToFile(number.getText(),"getsuthcode",caResponse.getMessage());
                    activityUtils.showToast(caResponse.getMessage());
                }
                //toastLong(response);
            }
        });
    }
}
