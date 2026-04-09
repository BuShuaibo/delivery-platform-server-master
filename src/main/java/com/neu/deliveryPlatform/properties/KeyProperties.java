package com.neu.deliveryPlatform.properties;

/**
 * @Author asiawu
 * @Date 2023-03-17 16:17
 * @Description: 所有有关key的参数
 */
public class KeyProperties {
    //提现
    public static final String TEMPORARY_WITHDRAWAL_CODE_PREFIX = "temporary_withdrawal_code:";
    //骑手状态
    public static final String RIDER_STATUS_PREFIX = "status:rider:";
    //Token相关
    public static final String TOKEN_PREFIX = "token:";
    public static final String TOKEN_HEADER = "Authorization";
    //存位置信息
    public static final String LOCATION_ORDER_PREFIX = "location:order:";
    public static final String LOCATION_RIDER_PREFIX = "location:rider:";
    public static final String LOCATION_USER_KEY = "user";
    public static final String LOCATION_RIDER_KEY = "rider";
    public static final String LOCATION_SHOPKEEPER_KEY = "shopkeeper";
    public static final String LOCATION_DISTANCE_KEY = "distance";
    public static final String LOCATION_LOCATION_KEY = "location";
    public static final String LOCATION_RIDER_AVAILABLE_KEY = "isAvailable";

    //权限相关的key
    public static final String ROLE_USER_KEY = "user";
    public static final String ROLE_RIDER_KEY = "rider";
    public static final String ROLE_SHOPKEEPER_KEY = "shopkeeper";
    public static final String ROLE_ADMIN_KEY = "admin";

    //上传文件相关key
    public static final String FILE_KEY = "file";
    public static final String IMAGE_KEY = "image";
    public static final String FILE_PLATFORM = "local-file";
    public static final String IMAGE_PLATFORM = "local-image";
    public static final String MUSIC_PREFIX = "buxiwan_music_";

    //支付相关的key
    public static final String ORDER_KEY = "ORDER_";
    //帮助相关的key
    public static final String CONTACT_KEY = "Contact";
    //图片访问次数的Key
    public static final String IMAGE_ACCESS_KEY = "IMAGE:";
}
