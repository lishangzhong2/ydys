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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.common.base.BaseAppManager;
import com.tphy.hospitaldoctor.common.config.Constant;
import com.tphy.hospitaldoctor.service.DownloadService;
import com.tphy.hospitaldoctor.ui.bean.KeShi;
import com.tphy.hospitaldoctor.utils.ActivityUtils;
import com.tphy.hospitaldoctor.utils.Common;
import com.tphy.hospitaldoctor.utils.StringCallback;
import com.tphy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class

LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

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

    private EditText mEmailView;
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
            }
        });
        mProgressView = findViewById(R.id.login_progress);
        keShiList = new ArrayList<>();
//        本地存储
        initSharedPfreference();
        checkUpdate();
        getVerson();
        activityUtils.showToast(Constant.BaseURL);
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
                            installAPK(file, LoginActivity.this);
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
        View ipView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_setip, null);
        final EditText et1 = ipView.findViewById(R.id.ip_et1);
        final EditText et2 = ipView.findViewById(R.id.ip_et2);
        et1.setText(Constant.BaseURL.split("/")[2].toString());
        et2.setText(Constant.BaseURL.split("/")[3].toString());
        TextView dismiss = ipView.findViewById(R.id.ip_tv_dismiss);
        TextView confirm = ipView.findViewById(R.id.ip_tv_confirm);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        final AlertDialog ipdialog = builder.create();
        ipdialog.setView(new EditText(LoginActivity.this));
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
                    Toast.makeText(LoginActivity.this, "IP地址不能为空", Toast.LENGTH_SHORT).show();
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
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
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
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
                            String appVersionName = Common.getAppVersionName(LoginActivity.this);
                            float onLineVersion = Float.parseFloat(versionName);
                            float myVersion = Float.parseFloat(appVersionName);
                            if (onLineVersion > myVersion) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                     if (getPackageManager().canRequestPackageInstalls()){
                                         PREDialog = new AlertDialog.Builder(LoginActivity.this)
                                                 .setTitle("设置")
                                                 .setCancelable(false)
                                                 .setMessage("请先允许运行必要的权限（如存储，安装应用等），否则程序将无法正常使用")
                                                 .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         LoginActivity.this.finish();
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

}

