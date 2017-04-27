package com.rock.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rock.Application.Constant;
import com.rock.Helper.ApiClient;
import com.rock.Helper.MyImageLoader;
import com.rock.Helper.UIHelper;
import com.rock.common.BitmapUtil;
import com.rock.model.User;
import com.rock.model.UserInfo;
;
import org.json.JSONObject;
import qiniu.config.Conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserEditActivity extends BaseActivity {
    private EditText ETnickName;
    private EditText EThobby;
    private EditText EThometown;
    private EditText ETintroduction;
    private EditText ETxingzuo;
    private EditText ETqinggan;
    private EditText ETjob;
    private EditText ETsex;
    private EditText ETsignature;
    private EditText ETuserName;
    private Button   completeBtn;
    private Handler editHandler;
    private UserInfo userInfo;
    boolean uploading = false;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    private File tempFile;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private String fileName = "";
    private String imageNameSmall;
    private User user;
    private ImageView IVuser_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit);
        user = (User)getIntent().getSerializableExtra("user");
        getUserInfo();
        context = this;
        initialHeader();
        if(user != null){
            initViews();
        }
    }

    private void initViews(){
        ETsignature = (EditText)findViewById(R.id.signature);
        ETnickName = (EditText)findViewById(R.id.nickname);
        EThobby = (EditText)findViewById(R.id.hobby);
        EThometown  = (EditText)findViewById(R.id.hometown);
        ETintroduction  = (EditText)findViewById(R.id.introduction);
        ETxingzuo  = (EditText)findViewById(R.id.xingzuo);
        ETqinggan  = (EditText)findViewById(R.id.qinggan);
        ETjob  = (EditText)findViewById(R.id.job);
        ETsex  = (EditText)findViewById(R.id.sex);
        completeBtn = (Button)findViewById(R.id.userinfo_submit);
        IVuser_icon = (ImageView)findViewById(R.id.user_icon);
        MyImageLoader.getInstance().displayImage(user.getHeadPic(),IVuser_icon);
        IVuser_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhoto();
            }
        });
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = ETnickName.getText().toString();
                String hobby = EThobby.getText().toString();
                String hometown  = EThometown.getText().toString();
                String introduction  = ETintroduction.getText().toString();
                String xingzuo  = ETxingzuo.getText().toString();
                String qinggan  = ETqinggan.getText().toString();
                String job = ETjob.getText().toString();
                String sex = ETsex.getText().toString();
                String signature = ETsignature.getText().toString();
                if("".equals(nickname)){
                    Toast.makeText(context, "昵称不能为空", Toast.LENGTH_LONG).show();
                }else{
                    RequestParams params = new RequestParams();
                    params.put("uid",uid);
                    params.put("sex",sex);
                    params.put("nickname",nickname);
                    params.put("jiaxiang",hometown);
                    params.put("xingzuo",xingzuo);
                    params.put("qinggan",qinggan);
                    params.put("hobby",hobby);
                    params.put("job",job);
                    params.put("signature",signature);

                    params.put("introduction",introduction);
                    UIHelper.showProgressDialog(context);
                    ApiClient.userInfoEdit(context, getEditHandler(), params);
                }

            }
        });
    }
    private Handler getEditHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                UIHelper.dismissProgressDialog();
                super.handleMessage(msg);
                if(msg.what == 1){
                    Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
                }
                else {
                     Toast.makeText(context, (String)msg.obj, Toast.LENGTH_LONG).show();
                    getUserInfo();
                }
            }
        };
    }
    public void initialHeader(){
        super.initialHeader();
        TextView head = (TextView) headMiddle.findViewById(R.id.community_name);
        head.setText("个人资料");
        headRight.setImageResource(0);
    }
    public Handler getUserHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                UIHelper.dismissProgressDialog();
                if(msg.what==-1){
                    return;
                }
                userInfo = (UserInfo) msg.obj;
                ETnickName.setText(userInfo.getNickname());
                ETsignature.setText(userInfo.getSignature());
                EThobby.setText(userInfo.getHobby());
                EThometown.setText(userInfo.getHometown());
                ETintroduction.setText(userInfo.getIntroduction());
                ETxingzuo.setText(userInfo.getXingzuo());
                ETintroduction.setText(userInfo.getIntroduction());
                ETqinggan.setText(userInfo.getQinggan());
                ETjob.setText(userInfo.getJob());
                ETsex.setText(userInfo.getSex());
            }
        };
    }
    public void getUserInfo(){
        UIHelper.showProgressDialog(context);
        Handler handler = getUserHandler();
        ApiClient.getUserDetail(handler,uid);
    }

    private void getPhoto() {
        Dialog alertDialog = new AlertDialog.Builder(this).setTitle("选择获取图片方式")
                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 调用系统的拍照功能
                        Intent intentPaizhao = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intentPaizhao.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(),
                                        "/imageName.jpg")));
                        startActivityForResult(intentPaizhao, PHOTOHRAPH);
                    }
                }).setNegativeButton("图库", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 调用系统的相册
                        Intent intentPhotoset = new Intent(Intent.ACTION_PICK,
                                null);
                        intentPhotoset.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                IMAGE_UNSPECIFIED);
                        // 调用剪切功能
                        startActivityForResult(intentPhotoset, PHOTOZOOM);
                    }
                }).create();
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File filePaizhao = createFile(Environment
                    .getExternalStorageDirectory() + "/imageName.jpg");
            startPhotoZoom(Uri.fromFile(filePaizhao));
        }

        if (data == null)
            return;
        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                BitmapFactory.decodeResource(photo);
//                String imgType = opts.outMimeType;
                File file = createFile(Constant.IMGCACHE);
                FileOutputStream fileOutputStream = null;
                imageNameSmall = getStringToday();
                fileName = file.getPath() + "/" + imageNameSmall + "."+"jpg";
                // images.add(fileName);
                try {
                    fileOutputStream = new FileOutputStream(fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                photo.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream);
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // head_iv.setBackground(null);
                //
                IVuser_icon.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(photo,
                        15));
                // head_iv.setBackground(new BitmapDrawable(photo));
                try {
                    updateUserImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public File createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();// 创建目录，多级目录
        }
        return file;
    }
    /**
     * 缩放
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    public void updateUserImage(){
        UploadManager uploadManager = new UploadManager();
        final String iconPath = uid+"rock"+imageNameSmall+".jpg";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("x:foo", "fooval");
//        final UploadOptions opt = new UploadOptions(params, null, true, null, null);
        uploadManager.put(new File(fileName),iconPath , Conf.getToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                    setUserIcon(iconPath);
                    }
                }, null);
    }

    public void setUserIcon(String iconPath){
        String iconUrl = "http://testox.qiniudn.com/"+iconPath;
        RequestParams requestParams = new RequestParams();
        requestParams.put("path",iconUrl);
        requestParams.put("uid",uid);
        ApiClient.setUserIcon(context,getIconHandler(),requestParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public Handler getIconHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(context, (String)msg.obj, Toast.LENGTH_LONG).show();
            }
        };
    }
}
