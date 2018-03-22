package com.yyhd.joke.api;

import com.yyhd.joke.api.response.BaseResponse;
import com.yyhd.joke.api.response.BindingWeChatResponse;
import com.yyhd.joke.api.response.CommentResponse;
import com.yyhd.joke.api.response.Coupon;
import com.yyhd.joke.api.response.FeedBackResponse;
import com.yyhd.joke.api.response.FundResponse;
import com.yyhd.joke.api.response.HasNewMsgResponse;
import com.yyhd.joke.api.response.MyDiscipleResponse;
import com.yyhd.joke.api.response.MyIncomeResponse;
import com.yyhd.joke.api.response.PublishReplyResponse;
import com.yyhd.joke.api.response.RateResponse;
import com.yyhd.joke.api.response.ReplyDetailResponse;
import com.yyhd.joke.api.response.UploadPhotoResponse;
import com.yyhd.joke.bean.CommentsBean;
import com.yyhd.joke.bean.DataAllBean;
import com.yyhd.joke.db.entity.JokeType;
import com.yyhd.joke.db.entity.UserInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/8/3
 * 备注:
 */
public interface SGApiService {

    /**
     * 获取段子类型
     * @return
     */
//    @GET("api/v1/getTypeList")
    @GET("api/v2/getTypeList")
    Observable<BaseResponse<List<JokeType>>> getJokeTypes();

    /**
     * 刷新首页列表
     * @param size
     * @param category
     * @return
     */
    @GET("api/v2/article/load/list")
    Observable<BaseResponse<List<DataAllBean>>> getRefreshList(
            @Query("pageSize") int size,
            @Query("category") String category,
            @Query("userId") String userId
    );

    @GET("api/v2/article/load/list")
    Observable<BaseResponse<List<DataAllBean>>> getRefreshList(
            @Query("pageSize") int size,
            @Query("category") String category
    );



    /**
     * 加载更多首页列表
     * @param size
     * @param category
     * @return
     */
    @GET("api/v2/article/load/list")
    Observable<BaseResponse<List<DataAllBean>>> loadMoreList(
            @Query("pageSize") int size,
            @Query("category") String category,
            @Query("userId") String userId
    );
    /**
     * 加载更多首页列表
     * @param size
     * @param category
     * @return
     */
    @GET("api/v2/article/load/list")
    Observable<BaseResponse<List<DataAllBean>>> loadMoreList(
            @Query("pageSize") int size,
            @Query("category") String category
    );

    /**
     * 注册设备uuid
     * @return
     */
    @POST("registerDevice")
    Observable<BaseResponse<Object>> registerDevice();


    /**
     * 登录账户
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
//    @POST("api/v2/login")
    @POST("api/v2/user/login")
    Observable<BaseResponse<UserInfo>> login(@Field("mobile") String mobile, @Field("password") String password);

    /**
     * 注册账户
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
//    @POST("api/v2/register")
    @POST("api/v2/user/register")
    Observable<BaseResponse<UserInfo>> regist(@Field("mobile") String mobile, @Field("password") String password, @Field("captcha") String code);

    /**
     * 意见反馈
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("api/v2/feed/addFeed")
    Observable<BaseResponse<FeedBackResponse>> feedBack(@Field("content") String content, @Field("userId") String userId);

    /**
     * 检查手机号是否已注册
     * @param mobile
     * @return
     */
//    @GET("api/v1/checkMobile")
    @GET("api/v2/user/checkMobile")
    Observable<BaseResponse<Boolean>> checkPhoneNumIsResisted(@Query("mobile") String mobile);


    /**
     * 获取验证码
     * @param mobile
     * @return
     */
//    @FormUrlEncoded
//    @POST("api/v1/sendSmscaptcha")
    @GET("api/v2/user/send/captcha/login")
    Observable<BaseResponse<Object>> getVerifyCode(@Query("mobile") String mobile);

    /**
     * 重置密码
     * @param phoneNumber
     * @param newPassword
     * @param captcha
     * @return
     */
//    @FormUrlEncoded
//    @POST("api/v1/resetPassword")
    @GET("api/v2/user/resetPassword")
    Observable<BaseResponse<UserInfo>> resetPassword(@Query("mobile") String phoneNumber, @Query("newPassword") String newPassword, @Query("captcha") String captcha);

    /**
     * 获取段子详情(不包含评论信息)
     * @param jokeId
     * @return
     */
    @GET("api/v2/article/{id}")
    Observable<BaseResponse<DataAllBean>> getJokeDetail(@Path("id") String jokeId);

    /**
     * 获取段子评论集合(上拉加载更多时用的)
     * @param page
     * @param articleId
     * @return
     */
    @GET("api/v2/comment/getCommentList")
    Observable<BaseResponse<CommentResponse>> getComments(
            @Query("articleId") String articleId,
            @Query("pageNo") int page
    );

    /**
     * 实名发表评论
     * @param requestBody
     * @return
     */
    @POST("api/v2/comment/addComment")
    Observable<BaseResponse<CommentsBean>> publishComment4RealName(@Body RequestBody requestBody);

    /**
     * 匿名发表评论
     * @param requestBody
     * @return
     */
    @Multipart
    @POST("api/v2/comment/addComment")
    Observable<BaseResponse<CommentsBean>> publishComment4Anonymity(@Body RequestBody requestBody);


    /**
     * 发表回复(包括实名和匿名)
     * @param map
     * @return
     */
    @Multipart
    @POST("api/v2/comment/addComment")
    Observable<BaseResponse<PublishReplyResponse>> publishReply(@PartMap Map<String, RequestBody> map);



    /**
     * 上传单张图片
     * @param part
     * @return
     */
    @Multipart
    @POST("uploader")
    Observable<BaseResponse<UploadPhotoResponse>> uploadImage(@Part() MultipartBody.Part part);//单张图片上传


    /**
     * 更新用户头像
     * @param requestBody
     * @return
     */
    @POST("api/v2/user/updateUserInfo")
    Observable<BaseResponse<UserInfo>> updateAvatar(
            @Body RequestBody requestBody
    );

    /**
     * 更改用户昵称
     * @param userId
     * @param nickName
     * @return
     */
    @Multipart
    @POST("api/v2/user/updateUserInfo")
    Observable<BaseResponse<Object>> updateNickName(
            @Part("userId") RequestBody userId,
            @Part("nickName") RequestBody nickName
    );

    /**
     * 更改用户微信号
     * @param userId
     * @param weChat
     * @return
     */
    @Multipart
    @POST("api/v2/user/updateUserInfo")
    Observable<BaseResponse<Object>> updateWeChat(
            @Part("userId") RequestBody userId,
            @Part("weChat") RequestBody weChat
    );

    /**
     * 更改用户微信号
     * @param userId
     * @param wechatName
     * @return
     */
    @GET("api/v1/updateUserInfo")
    Observable<BaseResponse<Object>> updateWechat(
            @Query("userId") String userId,
            @Query("wx") String wechatName
    );


    /**
     * 上传行为日志
     * @param actionLog
     * @return
     */
    @FormUrlEncoded
    @POST("api/addlog")
    Observable<BaseResponse<Boolean>> uploadActionLog(@Field("log") String actionLog);

    /**
     * 绑定微信
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v1/bindWechat")
    Observable<BaseResponse<BindingWeChatResponse>> bindingWeChat(@FieldMap Map<String, String> map);

    /**
     * 清空消息
     * @param userId
     * @return
     */
//    @GET("/api/v1/clearMsg")
    @GET("/api/v2/notify/delete/{userId}")
    Observable<BaseResponse<Object>> clearMsg(@Path("userId") String userId);

    /**
     * 标记已读
     * @param userId
     * @return
     */
    @GET("api/v2/notify/markReadByUserId/{userId}")
    Observable<BaseResponse<Object>> markReadedMsg(@Path("userId") String userId);

    /**
     * 获取兑现的红包集合
     * @return
     */
    @GET("api/v2/coupon/getCouponList")
    Observable<BaseResponse<List<Coupon>>> getCoupon();

    /**
     * 获取用户信息
     * @return
     */
//    @GET("api/v1/getUserInfoByUserId")
    @GET("api/v2/user/getUserInfo")
    Observable<BaseResponse<UserInfo>> getUserInfo(@Query("userId") String userId);

    /**
     * 上传好友的邀请码
     * @param userId
     * @param inviteCode
     * @return
     */
    @GET("api/v2/master/takeAsMaster/{userId}")
    Observable<BaseResponse<Object>> uploadInviteCode(@Path("userId") String userId, @Query("inviteCode") String inviteCode);

    /**
     * 提现
     * @param userId
     * @param couponType
     * @param couponAmount
     * @return
     */
    @GET("api/v2/fund/withdraw/{userId}")
    Observable<BaseResponse<Object>> withdraw(@Path("userId") String userId, @Query("couponType") String couponType, @Query("couponAmount") int couponAmount);


    /**
     * 获取邀请码
     * @return
     */
    @GET("/api/v2/user/createInviteCode/{userId}")
    Observable<BaseResponse<UserInfo>> getInviteCode(@Path("userId") String userId);

    /**
     * 获取收入明细
     * @return
     */
    @GET("api/v2/profit/getProfitRecord/{userId}")
    Observable<BaseResponse<List<MyIncomeResponse>>> getMyTransaction(
            @Path("userId") String userId,
            @Query("category") String category,
            @Query("pageNo") int page,
            @Query("pageSize") int pageSize
    );

    /**
     * 阅读奖励
     * @return
     */
    @GET("api/v2/fund/reward/{userId}")
    Observable<BaseResponse<Object>> award(
            @Path("userId") String userId
    );

    /**
     * 获取汇率
     * @return
     */
//    @GET("/api/v1/getRate")
    @GET("api/v2/rate/getRate")
    Observable<BaseResponse<RateResponse>> getRate();

    /**
     * 是否有新消息
     * @return
     */
    @GET("api/v1/hasNewMsg")
    Observable<BaseResponse<HasNewMsgResponse>> hasNewMsg(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("uploadNotification4Receive")
    Observable<BaseResponse<Object>> uploadNotification4Receive(@Field("notification_id") int notification_id);


    @FormUrlEncoded
    @POST("uploadNotification4Click")
    Observable<BaseResponse<Object>> uploadNotification4Click(@Field("notification_id") int notification_id);




    @FormUrlEncoded
    @POST("api/v2/track/add")
    Observable<BaseResponse<Boolean>> lvpi(@Field("userId") String user, @Field("uuid") String uuid, @Field("category") String pindao,
                                           @Field("articleId") String articleId, @Field("title") String title, @Field("duration") long duration);



    //赞段子
    @GET("/api/v2/article/upVote")
    Observable<BaseResponse<Object>> diggJoke(
            @Query("articleId") String jokeId
    );

    //踩段子
    @GET("/api/v2/article/downVote")
    Observable<BaseResponse<Object>> buryJoke(
            @Query("articleId") String jokeId
    );

    //赞评论
    @GET("/api/v2/comment/upVote")
    Observable<BaseResponse<Object>> diggComment(
            @Query("commentId") String commentId
    );

    //踩评论(暂无此需求)
    @GET("/api/v2/comment/downVote")
    Observable<BaseResponse<Object>> buryComment(
            @Query("commentId") String commentId
    );

    //获取某条评论的回复列表
    @GET("/api/v2/comment/{commentId}")
    Observable<BaseResponse<ReplyDetailResponse>> getCommentReplys(
            @Path("commentId") String commentId,
            @Query("pageNo") int pageNo
    );

    //告诉服务器,客户端启动了app
    @GET("/api/v2/user/cumulativeLogin/{userId}")
    Observable<BaseResponse<UserInfo>> cumulativeLogin(
            @Path("userId") String userId
    );

    /**
     * 上传绿皮数据
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("api/v2/track/batchAdd")
    Observable<BaseResponse<Object>> uploadLp(@Field("data") String data);

    /**
     * 上传推送数据
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("api/v2/track/uploadNotificationMsg")
    Observable<BaseResponse<Object>> uploadNotificationMsg(@Field("data") String data);


    /**
     * 分享
     * @param articleId
     * @return
     */
    @GET("api/v2/article/share")
    Observable<BaseResponse<Object>> share(@Query("articleId") String articleId);

    /**
     * 获取我的资金
     * @param userId
     * @return
     */
    @GET("api/v2/fund/getMyFund/{userId}")
    Observable<BaseResponse<FundResponse>> getMyFund(@Path("userId") String userId);

    /**
     * 获取徒弟列表
     * @param userId
     * @return
     */

    @GET("api/v2/master/getMyApprentice/{userId}")
    Observable<BaseResponse<MyDiscipleResponse>>getMyDiscipleList(@Path("userId") String userId);


}
