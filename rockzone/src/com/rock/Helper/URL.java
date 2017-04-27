package com.rock.Helper;

/**
 * Created by wym on 2014/10/29.
 */
public class URL {
    public final static String HOST = "http://182.92.234.53";
    public final static String HOST_DIR = HOST+"/dev/index.php?s=";
    public final static String HOST_BLOG_API = HOST_DIR+"/blog";
    public final static String HOST_ADV_API = HOST_DIR+"/group/communityAPI";
    public final static String HOST_COMMUNITY_API = HOST_DIR+"/group/communityAPI";
    public final static String HOST_SHOP_API = HOST_DIR+"/shop/MallAPI";

    public final static String HOST_ARTICLE_API = HOST_BLOG_API+"/ArticleAPI";
    public final static String HOST_USER_API = HOST_DIR+"/usercenter";



//    首页广告轮播图list
    public final static String HOST_ADV_LIST = HOST_ADV_API+"/getHomeBannerAd";
//    获取社区列表
    public final static String HOST_COMMUNITY_LIST = HOST_COMMUNITY_API+"/getInfoByPage";
//    获取社区信息详情
    public final static String HOST_COMMUNITY_DETAIL = HOST_COMMUNITY_API+"/getDetailById";
//    获取社区分类服务列表
    public final static String HOST_COMMUNITY_SERVICE_LIST = HOST_COMMUNITY_API+"/getCateListById";
//    获取社区分类服务详情
    public final static String HOST_COMMUNITY_SERVICE_DETAIL = HOST_COMMUNITY_API+"/getArticleById/";
//    获取社区分类服务详情评论
    public final static String HOST_COMMUNITY_SERVICE_REPLY = HOST_COMMUNITY_API+"/getArticleReplyById";
//    登陆
    public final static String HOST_LOGIN =HOST_DIR+"/home/UserAPI/doLogin/";
//    注册
    public final static String HOST_REG =HOST_DIR+"/home/UserAPI/doRegister/";
//    验证码
    public final static String HOST_VALID =HOST_DIR+"/home/UserAPI/doSmsCode/";
//    积分商城列表
    public final static String HOST_GOODS_LIST =HOST_SHOP_API+"/goods/page/";
//    文章详情获取
    public final static String HOST_ARTICLE_DETAIL =HOST_ARTICLE_API+"/getArticleById/id/";
//    文章评论获取
    public final static String HOST_ARTICLE_REPLY =HOST_ARTICLE_API+"/getReplyById/id/";
//    小区分类文章列表
    public final static String HOST_ARTICLE_LIST =HOST_COMMUNITY_API+"/getArticleListById/id/";
//    用户中心数据API
    public final static String HOST_USERCENTER_INFO =HOST_USER_API+"/UserAPI/info/uid/";
//    用户中心数据API
    public final static String HOST_USERCENTER_MESSAGE =HOST_USER_API+"/UserAPI/message/uid/";
//    用户中心数据API
    public final static String HOST_GOODS_DETAIL =HOST_SHOP_API+"/goodsDetail/id/";
//    发现页轮播图API
    public final static String EXPLORE_ADV_LIST =HOST_ARTICLE_LIST+"52";
//    发现页按钮区API
    public final static String EXPLORE_BAR_LIST =HOST_ARTICLE_LIST+"53";
//    发现页瀑布流API
    public final static String EXPLORE_BOTTOM_LIST =HOST_ARTICLE_LIST+"54";
//    首页瀑布流API
    public final static String MAIN_BOTTOM_LIST =HOST_ARTICLE_LIST+"50";
//    重置密码验证码
    public final static String HOST_RESET_VALID =HOST_USER_API+"/UserAPI/doFindUidBySms";
//    重置密码
    public final static String HOST_RESET_PWD =HOST_USER_API+"/UserAPI/doResetPassword";
//    修改密码
    public final static String HOST_CHANGE_PWD =HOST_USER_API+"/UserAPI/doChangePassword";
//    回复
    public final static String HOST_ARTICLE_COMMENT =HOST_ARTICLE_API+"/doReply";
//    发帖
    public final static String HOST_FORUM_POST =HOST_DIR+"/admin/ArticleAPI/doUpdateArticle";
//    提交订单
    public final static String HOST_GOODS_BUY =HOST_DIR+"/shop/MallAPI/doGoodsBuy/";
//    商品评价
    public final static String HOST_GOODS_COMMENT =HOST_DIR+"/shop/MallAPI/doGoodReply";
//    查看订单
    public final static String HOST_ORDER_LIST =HOST_DIR+"/shop/MallAPI/myGoodsList/";
//    查看商品收藏
    public final static String HOST_GOODS_COLLECT_LIST =HOST_DIR+"/shop/MallAPI/goodCollectList/";
//    查看文章收藏
    public final static String HOST_ARTICLE_COLLECT_LIST =HOST_ARTICLE_API+"/articleCollectList/";
//    商品收藏
    public final static String HOST_GOODS_COLLECT =HOST_DIR+"/shop/MallAPI/doGoodCollect/";
//    文章收藏
    public final static String HOST_ARTICLE_COLLECT =HOST_ARTICLE_API+"/doArticleCollect/";
//    用户资料
    public final static String HOST_USERINFO_EDIT =HOST_USER_API+"/UserAPI/doSaveUserExtendInfo/";
//    用户详情
    public final static String HOST_USERINFO_DETAIL =HOST_USER_API+"/UserAPI/getUserExtendInfo/";
//    用户详情
    public final static String HOST_USERINFO_ICON_EDIT =HOST_USER_API+"/UserAPI/doSaveUserIcon/";
//    商品评论
    public final static String HOST_GOODS_COMMENT_LIST = HOST_SHOP_API+"/getGoodReplyById/id/";

}
