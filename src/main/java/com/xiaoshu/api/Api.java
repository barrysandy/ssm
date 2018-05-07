package com.xiaoshu.api;

/**
 * 系统接口登记
 * @author XGB on 2018/04/19
 */
public class Api {


    /**
     * 扫描补全用户信息
     */
    public static final String SCAN_UPDATE_USER = Set.SYSTEM_URL + "scan/interfaceScanUpdateUserInfo";


    /**
     * 用一个公众号标识换取另一个与之关联的标识
     */
    public static final String GET_COMMODITY = Set.SYSTEM_URL + "apiInterface/interfaceGetCommdity";

    /**
     * 用一个公众号标识换取另一个与之关联的标识
     */
    public static final String GET_OTHER_MENUID = Set.SYSTEM_URL + "apiInterface/interfaceGetOtherMenuId";

    /**
     * 用一个公众号标识换取另一个与之关联的标识
     */
    public static final String GET_GROUP_ID = Set.SYSTEM_URL + "apiInterface/interfaceGetGroupId";

    /**
     * 自动退款申请
     * @param id 订单id
     */
    public static final String SEND_REFUND_AUTO = Set.SYSTEM_URL + "orderRefund/interfaceAutoReurn";


    /** 发送短信消息 */
    public static final String SEND_SMS = Set.SYSTEM_URL + "messageRecord/interfaceSendMsg";

    /** 群发短信消息 */
    public static final String SEN_SMS_GROUP = Set.SYSTEM_URL + "messageRecord/interfaceSendAllMeg";


    /** 清理OR刷新 */
    public static final String REMOVE_JSSDK = Set.SYSTEM_URL + "/wechat/interfaceRemoveJSSDKTicket";

    /** 刷新组团 */
    public static final String SCAN_GROUP = Set.SYSTEM_URL + "/scan/interfaceScanGroup";


    /** 刷新商品的状态 */
    public static final String SCAN_COMMODITY_STATE = Set.SYSTEM_URL + "/scan/interfaceScanUpdateTimeStatus";

    /** 检查账单 */
    public static final String CHECK_WATER = Set.SYSTEM_URL + "/waterBill/interfaceCheckWaterBill";

    /**
     * 查询接口的URL
     * @param keyes 接口标识
     */
    public static final String GET_URL_INTERFACE = Set.SYSTEM_URL + "interfaceInfo/getUrlinterfaces";


    /**
     * 查询公众号帐号信息
     * @param menuId 公众号menuId
     */
    public static String GET_PUBLICNUMBER_TOKEN = Set.SYSTEM_URL + "publicAccountInfo/getTokenByMenuid";

    /**
     * 查询所有微信公众号帐号信息
     * @param usable 公众号启用状态
     */
    public static String GET_ALLPUBLICNUMBER = Set.SYSTEM_URL + "publicAccountInfo/interfaceGetAll";

    /**
     * 通过menuId查询帐号部分信息
     * @param menuId 菜单ID
     * @param returnContent 返回内容 (appId,appSecret,effectiveTime)
     * @author zhou.zhengkun
     * @date 2018/01/18 0018 15:31
     */
    public static String GET_ACCOUNT_INFO = Set.SYSTEM_URL + "publicAccountInfo/interface/selectInfoByMenuId";



    /**
     * 通过期号查询文章
     * @param current 期号
     * @param menuId
     * @author zhou.zhengkun
     * @date 2018/01/17 0017 15:09
     */
    public static String SELECT_ART_BY_PERIOD = Set.SYSTEM_URL + "art/interfaceGetPeriodArt";

    /**
     * 查询本期文章
     * @param menuId
     * @author zhou.zhengkun
     * @date 2018/01/17 0017 15:15
     */
    public static String FINAL_ART = Set.SYSTEM_URL + "art/interfaceGetCurrentPeriodArt";




    /**
     * 关键词查询
     * @param menuId 关键字所属公众号
     * @param keyes 关键字
     * @return 返回关键字对象或 0
     * @author XGB
     * @date 2018-01-18 11:05
     */
    public static String GET_KEYWORRDS_BYMID_AND_KEY = Set.SYSTEM_URL + "keyWords/getKeyinterfaces";



    /**
     * 判断用户是否存在,不存在则保存
     * @param openid 用户openid
     * @param menuId 表示需要保存用户的menuId
     * @return 返回0 或者 非0字符串
     * @author XGB
     * @date 2018-01-18 14:39
     */
    public static String GET_USEREXIT = Set.SYSTEM_URL + "focusedUserInfo/interfaceUserExit";

    /**
     * 更新用户的关注状态  focusedUserInfo/interfaceUpdateUserSubscribe
     * @param menuId
     * @param openid
     * @param subscribe
     * @return 返回0表示更新失败 > 0 表示更新成功
     * @author XGB
     * @date 2018-01-22 10:15
     */
    public static String UPDATE_USER_SUBSCRIBE = Set.SYSTEM_URL + "focusedUserInfo/interfaceUpdateUserSubscribe";

    /**
     * 更新用户的关注状态  focusedUserInfo/interfaceUpdateUserSubscribe
     * @param menuId
     * @param openid
     * @param subscribe
     * @return 返回0表示更新失败 > 0 表示更新成功
     * @author XGB
     * @date 2018-01-22 10:21
     */
    public static String UPDATEUSERSUBSCRIBE = Set.SYSTEM_URL + "focusedUserInfo/interfaceUpdateUserSubscribe";

    /**
     * 关注用户删除
     * @param id 用户主键ID
     * */
    public static String FOCUSED_USER_DEL = Set.SYSTEM_URL + "focusedUserInfo/del.htm&id=ID";

    /**
     * 判断用户是否关注了另一个公众号
     * @param String openid 用当前的openid
     * @param String menuId 用户授权登录的menuId
     * */
    public static String FOCUSED_USER_JUDGING_SUB = Set.SYSTEM_URL + "apiInterface/interfaceJudgingSubscriptions";


    /**
     * 根据openid查询用户信息是否完整/并补全不完整的信息  focusedUserInfo/interfaceUserInfoIntegrity
     * @param openid
     * @param menuId
     * @return 0 不完整 1 完整信息 2不完整并更新成功了
     * @author XGB
     * @date 2018-01-17 10:32
     */
    public static String UPDATE_USER = Set.SYSTEM_URL + "focusedUserInfo/interfaceUserInfoIntegrity";

    /**
     * 精准查询 - menuId/名字 (两者选一个都可以,两个都传默认按照menuId进行查询)
     * @param menuId 菜单的主键ID
     * @param menuName 菜单名字
     * @return String
     * @author zhou.zhengkun
     * @date 2018/01/18 0018 16:15
     */
    public static String PRECISE_SEARCH_MENU  = Set.SYSTEM_URL + "weChatMenu/interface/getMenuByIdOrName";

    /**
     * 通过parentId查询菜单
     * @param parentId 父级菜单ID (一级菜单parentId 为 ROOT)
     * @return String
     * @author zhou.zhengkun
     * @date 2018/01/18 0018 15:55
     */
    public static String GET_MENU_BY_PARENT_ID = Set.SYSTEM_URL + "weChatMenu/interface/getMenuListByParentId";


    /**
     * 根据MenuId查询父MenuId  menu/interfaceGetParentMenuId?menuId=menuId
     * @param menuId
     * @return parentMenuId
     * @author XGB
     * @date 2018-01-20 8:59
     */
    public static String GET_PARENTMENUIDBYMENUID  = Set.SYSTEM_URL + "menu/interfaceGetParentMenuId";


    /**
     * 微信配置的接口地址
     * @param sign 微信验证接口的参数，此参数代表对接的公众号
     * @return parentMenuId
     * @author XGB
     * @date 2018-02-5 10:05
     */
    public static String WECHAT_INTERFACE_URL  = Set.SYSTEM_URL + "wechat/interfaceApi?sign=SIGN";



    /**
     * 根据图片的material_id获取图片url
     * SSM图片服务器API 地址：http://www.daxi51.com/ssm_file/api
     * 请求方式GET
     * 请求参数 String material_id 资源material_id
     */
    public static final String GET_FILE_URL = Set.SYSTEM_SSM_FILE + "fileManager/interfaceGetFileURLByMaterial_id";

    /**
     * 根据图片的material_id获取图片磁盘路径
     * SSM图片服务器API 地址：http://www.daxi51.com/ssm_file/api
     * 请求方式GET
     * 请求参数 String material_id 资源material_id
     */
    public static final String GET_FILE_PATH = Set.SYSTEM_SSM_FILE + "fileManager/interfaceGetFileDiskPathByMaterial_id";

    /**
     * 根据图片的material_id更新文件服务器的资源引用
     * SSM图片服务器API 地址：http://www.daxi51.com/ssm_file/api
     * 请求方式POST
     * 请求参数 String material_id 资源material_id
     */
    public static final String UPDATE_FILE_STATUS = Set.SYSTEM_SSM_FILE + "fileManager/interfaceUpdateFileStatus";

    /**
     * 根据图片的material_id删除文件服务器的资源引用及磁盘文件
     * SSM图片服务器API 地址：http://www.daxi51.com/ssm_file/api
     * 请求方式GET
     * 请求参数 String material_id 资源material_id
     */
    public static final String DEL_FILE = Set.SYSTEM_SSM_FILE + "fileManager/interfaceDelFileNotMQ";

    /**
     * 发送短信接口
     * 短信服务器API 地址：http://www.daxi51.com/ssm_file/api
     * 请求方式GET
     * 请求参数 String mobile,String content
     */
    public static final String SEND_MSG = "http://www.daxi51.com/ssm_file/fileManager/interfaceDelFileNotMQ";
}
