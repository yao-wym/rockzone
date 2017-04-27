package com.rock.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.rock.Activity.*;
import com.rock.model.User;

/**
 * Created by wym on 2014/10/29.
 */
public class UIHelper {
    public static  ProgressDialog loading_Dialog;
    protected static boolean isProgressShowing = false;
    public static void showWebView(Context context,String url,String title) {
        Intent intent = new Intent(context, ShowWebViewActivity.class);
        intent.putExtra("webUrl",url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    public static void showWaimaiView(Context context) {
        Intent intent = new Intent(context, ServiceDetailActivity.class);
        context.startActivity(intent);
    }
    public static void showNearbyView(Context context) {
        Intent intent = new Intent(context, CommunityListActivity.class);
        context.startActivity(intent);
    }


    public static void showMoreView(Context context) {
        Intent intent = new Intent(context, ServiceDetailActivity.class);
        context.startActivity(intent);
    }
    public static void showErweimaView(Context context) {
        Intent intent = new Intent(context, ErweimaActivity.class);
        context.startActivity(intent);
    }
    public static void showPropertyView(Context context) {
        Intent intent = new Intent(context, ServiceDetailActivity.class);
        context.startActivity(intent);
    }
    public static void showUserEditView(Context context,User user) {
        Intent intent = new Intent(context, UserEditActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("user",user);
        intent.putExtras(mBundle);
        context.startActivity(intent);
    }
    public static void showChangePwdView(Context context) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        context.startActivity(intent);
    }
    public static void showBianminView(Context context) {
        Intent intent = new Intent(context, RockBianminActivity.class);
        context.startActivity(intent);
    }
    public static void showSettingView(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
    public static void showUserCollection(Context context) {
        Intent intent = new Intent(context, UserCollectionActivity.class);
        context.startActivity(intent);
    }
    public static void showOrderListView(Context context) {
        Intent intent = new Intent(context,UserOrderListActivity.class);
        context.startActivity(intent);
    }
    public static void showAdvDetail(Context context, String id) {
        Intent intent = new Intent(context, AdvDetailActivity.class);
        intent.putExtra("advId",id);
        context.startActivity(intent);
    }
    public static void showCommunityListView(Context context) {
        Intent intent = new Intent(context, CommunityListActivity.class);
        context.startActivity(intent);
    }
    public static void showCommunityDetailView(Context context,String communityID) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("communityID",communityID);
        context.startActivity(intent);
    }
    public static void showCommentView(Context context,String articleId,String replyAuthor) {
        Intent intent = new Intent(context, ArticleCommentActivity.class);
        intent.putExtra("articleId",articleId);
        intent.putExtra("replyAuthor",replyAuthor);
        context.startActivity(intent);
    }
    public static void showGoodsCommentView(Context context,String goodsId) {
        Intent intent = new Intent(context, ShowCommentListActivity.class);
        intent.putExtra("goodsId",goodsId);
        context.startActivity(intent);
    }
    public static void showGoodsDescribeView(Context context,String webStr,String title) {
        Intent intent = new Intent(context, ShowWebViewActivity.class);
        intent.putExtra("webStr",webStr);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    public static void showArticleCommentView(Context context,String articleId) {
        Intent intent = new Intent(context, ShowCommentListActivity.class);
        intent.putExtra("articleId",articleId);
        context.startActivity(intent);
    }
    public static void showCollectionView(Context context,String goodsId) {
        Intent intent = new Intent(context, UserCollectionActivity.class);
        intent.putExtra("goodsId",goodsId);
        context.startActivity(intent);
    }
    public static void showPostView(Context context,String forumId) {
        Intent intent = new Intent(context, ForumPostActivity.class);
        intent.putExtra("forumId",forumId);
        context.startActivity(intent);
    }
    public static void showPostDetailView(Context context,String postId) {
        Intent intent = new Intent(context, ForumPostDetailActivity.class);
        intent.putExtra("postId",postId);
        context.startActivity(intent);
    }
    public static void showUserCenterView(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);

        context.startActivity(intent);
    }

    /**
     *
     * @param context
     * @param final_flag 为1则不能返回，为0可以返回
     */
    public static void showLoginView(Context context,int final_flag) {
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra("final_flag",final_flag);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    public static void showRegisterView(Context context,String nickname) {
        Intent intent = new Intent(context,RegisterActivity.class);
        intent.putExtra("nickname",nickname);
        context.startActivity(intent);
    }
    public static void showResetValidView(Context context) {
        Intent intent = new Intent(context,PasswordResetValidActivity.class);
        context.startActivity(intent);
    }
    public static void showCommunityArticleView(Context context,String id,int titleId) {
        Intent intent = new Intent(context,CommunityArticleListActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("titleId",titleId);
        context.startActivity(intent);
    }
    public static void showArticleDetailView(Context context,String id,String title) {
        Intent intent = new Intent(context,CommunityArticleDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    public static void showPhoneDetailView(Context context,String id) {
        Intent intent = new Intent(context,PhoneDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
    public static void showGoodsDetailView(Context context,String id) {
        Intent intent = new Intent(context,GoodsDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
    public static void showOrderSubmitView(Context context,Bundle goods) {
        Intent intent = new Intent(context,OrderSubmitActivity.class);
        intent.putExtras(goods);
        context.startActivity(intent);
    }
    public static void showNextRegView(Context context,String phoneNum) {
        Intent intent = new Intent(context, Register2Activity.class);
        intent.putExtra("phoneNum",phoneNum);
        context.startActivity(intent);
    }
    public static void showNextResetView(Context context,String uid) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra("uid",uid);
        context.startActivity(intent);
    }
    public static void showHomeView(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
    public static void showMessageListView(Context context) {
        Intent intent = new Intent(context,UserMessageActivity.class);
        context.startActivity(intent);
    }
    public static void showStaticView(Context context,int layoutId,String title) {
        Intent intent = new Intent(context,StaticViewActivity.class);
        intent.putExtra("layoutId",layoutId);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    /**
     * 菜单显示登录或登出
     *
     * @param activity

    public static void showMenuLoginOrLogout(Activity activity, Menu menu) {
        if (((Context) activity.getApplication()).isLogin()) {
            menu.findItem(R.id.main_menu_user).setTitle(
                    R.string.main_menu_logout);
            menu.findItem(R.id.main_menu_user).setIcon(
                    R.drawable.ic_menu_logout);
        } else {
            menu.findItem(R.id.main_menu_user).setTitle(
                    R.string.main_menu_login);
            menu.findItem(R.id.main_menu_user)
                    .setIcon(R.drawable.ic_menu_login);
        }
    }
     */
    public static void initFootView(Activity activity,int position){
        LinearLayout foot_container = (LinearLayout)activity.findViewById(R.id.foot_container);
        ((ImageView)(((RelativeLayout)foot_container.getChildAt(0)).getChildAt(0)))
                .setImageResource(R.drawable.shouye_home);
        ((TextView)(((RelativeLayout)foot_container.getChildAt(0)).getChildAt(1)))
                .setTextColor(Color.rgb(114,114,114));
        ((ImageView)(((RelativeLayout)foot_container.getChildAt(1)).getChildAt(0)))
                .setImageResource(R.drawable.shouye_faxian);
        ((TextView)(((RelativeLayout)foot_container.getChildAt(1)).getChildAt(1)))
                .setTextColor(Color.rgb(114,114,114));
        ((ImageView)(((RelativeLayout)foot_container.getChildAt(2)).getChildAt(0)))
                .setImageResource(R.drawable.shouye_shangcheng);
        ((TextView)(((RelativeLayout)foot_container.getChildAt(2)).getChildAt(1)))
                .setTextColor(Color.rgb(114,114,114));
        ((ImageView)(((RelativeLayout)foot_container.getChildAt(3)).getChildAt(0)))
                .setImageResource(R.drawable.shouye_wode);
        ((TextView)(((RelativeLayout)foot_container.getChildAt(3)).getChildAt(1)))
                .setTextColor(Color.rgb(114,114,114));
        switch (position){
            case 1:
                ((ImageView)(((RelativeLayout)foot_container.getChildAt(0)).getChildAt(0)))
                        .setImageResource(R.drawable.shouye_home_press);
                ((TextView)(((RelativeLayout)foot_container.getChildAt(0)).getChildAt(1)))
                        .setTextColor(Color.rgb(255, 91, 59));
                break;
            case 2:
                ((ImageView)(((RelativeLayout)foot_container.getChildAt(1)).getChildAt(0)))
                        .setImageResource(R.drawable.shouye_faxian_press);
                ((TextView)(((RelativeLayout)foot_container.getChildAt(1)).getChildAt(1)))
                        .setTextColor(Color.rgb(255, 91, 59));
                break;
            case 3:
                ((ImageView)(((RelativeLayout)foot_container.getChildAt(2)).getChildAt(0)))
                        .setImageResource(R.drawable.shouye_shangcheng_press);
                ((TextView)(((RelativeLayout)foot_container.getChildAt(2)).getChildAt(1)))
                        .setTextColor(Color.rgb(255, 91, 59));
                break;
            case 4:
                ((ImageView)(((RelativeLayout)foot_container.getChildAt(3)).getChildAt(0)))
                        .setImageResource(R.drawable.shouye_wode_press);
                ((TextView)(((RelativeLayout)foot_container.getChildAt(3)).getChildAt(1)))
                        .setTextColor(Color.rgb(255, 91, 59));
                break;
        }
    }
    public static void showProgressDialog(Context context) {
        if (!isProgressShowing&&context!=null) {
            try {
                loading_Dialog = new ProgressDialog(context);
                loading_Dialog.setCancelable(true);
                loading_Dialog.setCanceledOnTouchOutside(false);
                loading_Dialog.show();
                loading_Dialog.setContentView(R.layout.loadingdialog);
                isProgressShowing = true;
            }catch (Exception e){
                e.printStackTrace();
            }


        } else {
            if (!loading_Dialog.isShowing()) {
                try {
                    loading_Dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void dismissProgressDialog() {
        if (loading_Dialog != null) {
            try {
                if (loading_Dialog.isShowing()) {
                    loading_Dialog.dismiss();
                    isProgressShowing = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
