package com.xiaoshu.wechat.api;

/**
 * 微信公众号开放的接口
 * @author XGB
 */
public class WeChatAPI {

	/**
	 * 接口声明：生成微信带参二维码
	 * 参数：详情查看文档: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542
	 */
	public static String CREATE_QR_CODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

	/**
	 * 接口声明：通过ticket获取微信带参二维码图片
	 * 参数：详情查看文档: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542
	 */
	public static String SHOW_QR_CODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	/**
	 * 接口声明：通过ticket获取微信带参二维码图片并保存到本地文件夹中
	 * 参数：详情查看文档: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542
	 */
	public static String SHOW_QR_CODE_IMAGE = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

	/**
	 * 接口声明：微信退款接口
	 * 参数：详情查看文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	 */
	public static String PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	/**
	 * 接口声明：微信退款查询接口
	 * 参数：详情查看文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	 */
	public static String PAY_REFUND_QUERY = "https://api.mch.weixin.qq.com/secapi/pay/refundquery";

	/**
	 * 接口声明：获取ACCESS_TOKEN接口
	 * 参数：	参数					是否必须		说明
	 *		(1)grant_type			是			获取access_token填写client_credential
	 *		(2)appid				是			第三方用户唯一凭证
	 *		(3)secret				是		        第三方用户唯一凭证密钥，即appsecret
	 */
	public static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 接口声明：微信自定义菜单创建接口
	 * http请求方式：POST（请使用https协议）
	 * 参数：(1)access_token=ACCESS_TOKEN
	 */
	public static String Create_Menu ="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 接口声明：微信自定义菜单查询接口
	 * http请求方式：GET
	 * 参数：(1)access_token=ACCESS_TOKEN
	 */
	public static String Get_Menu = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 接口声明：微信自定义菜单删除接口
	 * http请求方式：GET
	 * 参数：(1)access_token=ACCESS_TOKEN
	 */
	public static String Delete_Menu = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	 
	
	/**
	 * 接口声明：微信用户同意授权，获取code（授权第一步）
	 * http请求方式：GET
	 * 参数：	参数						是否必须		说明
	 *		(1)appid				是			公众号的唯一标识
	 *		(2)redirect_uri			是			授权后重定向的回调链接地址，请使用urlEncode对链接进行处理
	 *		(3)response_type		是			返回类型，请填写code
	 *		(4)scope				是			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 *		(5)state				否			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 *		(6)#wechat_redirect		是			无论直接打开还是做页面302重定向时候，必须带此参数
	 */
	public static String Oauth2_GetCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	 
	
	/**
	 * 接口声明：微信通过code换取网页授权access_token（授权第二步）
	 * http请求方式：GET
	 * 参数：	参数						是否必须		说明
	 *		(1)appid				是			公众号的唯一标识
	 *		(2)secret				是			公众号的appsecret
	 *		(3)code					是			填写第一步获取的code参数
	 *		(4)grant_type			是			填写为authorization_code
	 */
	public static String Oauth2_GetAccess_Token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	 
	
	/**
	 * 接口声明：微信刷新access_token（如果需要）（授权第三步）
	 * http请求方式：GET
	 * 参数：	参数						是否必须		说明
	 *		(1)appid				是			公众号的唯一标识
	 *		(2)grant_type			是			填写为authorization_code
	 *		(3)refresh_token		是			填写通过access_token获取到的refresh_token参数
	 */
	public static String Oauth2_Refresh_Token = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	
	
	/**
	 * 接口声明：微信拉取用户信息(需scope为 snsapi_userinfo)（授权第四步）
	 * http请求方式：GET
	 * 参数：	参数						是否必须		说明
	 *		(1)access_token			是			网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 *		(2)openid				是			用户的唯一标识
	 *		(3)lang					是			返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 */
	public static String Oauth2_UserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	
	/**
	 * 接口声明：微信检验授权凭证（access_token）是否有效（授权第五步）
	 * http请求方式：GET
	 * 参数：	参数						是否必须		说明
	 *		(1)access_token			是			网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 *		(2)openid				是			用户的唯一标识
	 */
	public static String Oauth2_Auth = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	
	
	/**
	 * @author 接口声明：微信新增永久图文素材
	 * http请求方式: POST，https协议
	 * @pararm  参数：	参数						是否必须		说明
	 *		(1)access_token			是			调用接口凭证
	 *		(2)type					是			①图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式②语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式③视频（video）：10MB，支持MP4④格式 缩略图（thumb）：64KB，支持JPG格式
	 * @return 返回 media_id
	 *	{
  	 *		"media_id":MEDIA_ID
	 *	}
	 */
	public static String Add_Material = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	
	/**
	 * @author 接口声明：微信删除永久素材
	 * http请求方式: POST，https协议
	 * @pararm  参数：	参数						是否必须		说明
	 *		(1)access_token			是			调用接口凭证
	 *		(2)media_id				是			需要删除的素材media_id
	 * @return 返回说明
	 *	{
	 *	   "errcode":ERRCODE,
	 *	   "errmsg":ERRMSG
	 *	}
	 *	正常情况下调用成功时，errcode将为0。
	 */
	public static String Del_Material = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";//&media_id=MEDIA_ID
	
	
	/**
	 * @author 接口声明：微信获取用户基本信息（包括UnionID机制）
	 * http请求方式: GET
	 * @pararm  参数：	参数						是否必须		说明
	 *		(1)access_token			是			调用接口凭证
	 *		(2)openid				是			普通用户的标识，对当前公众号唯一
	 *		(3)lang					是			返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 */
	public static String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	
	/**
	 * @author 接口声明：微信获取用户列表
	 * http请求方式: GET
	 * @pararm  参数：	参数						是否必须		说明
	 *		(1)access_token			是			调用接口凭证
	 *		(2)next_openid			是			第一个拉取的OPENID，不填默认从头开始拉取
	 */
	public static String User_Array = "https://api.weixin.qq.com/cgi-bin/user/get";
	
	/**
	 * @author 接口声明：微信获取unioni
	 * http请求方式: GET
	 * @pararm  参数：	参数						是否必须		说明
	 *		(1)access_token			是			调用接口凭证(这里为微信接口凭证而非授权凭证)
	 *		(2)openid			            是			需要获取用户的openid
	 *		(3)lang			                        否			lang=zh_CN 中文方式
	 */
	public static String UnionidUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * @author 接口声明：微信拉取授权登录
	 * http请求方式: GET
	 * @pararm  参数：	参数						是否必须		说明
	 *		(1)access_token			是			调用接口凭证(这里为微信接口凭证为授权凭证，需要重新获取，不能使用本系统与微信服务器的凭证)
	 *		(2)openid			            是			需要获取用户的openid
	 */
	public static String Oauth2UserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	
	
	/**
	 * @author 接口声明：获取网页授权凭证
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static String Oauth2AccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * @author 接口声明：获取网页JSSDK ticket
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static String GetWebTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";


	/**
	 * @author 接口声明：微信网页授权登录(弹出授权方式，静默授权需修改参数scope=snsapi_userinfo为scope=snsapi_base)
	 * @param appId 公众账号的唯一标识
	 * @param redirect_uri 授权成功的跳转页面
	 * @return WeixinAouth2Token
	 */
	public static String Oauth2WeChat = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";


	/**
	 * @author 接口声明：群发消息 请求方式POST（参数查看https://www.cnblogs.com/0201zcr/p/5866296.html）
	 * @param access_token 调用接口凭证
	 * @return
	 */
	public static String SENDMASS = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";


	/**
	 * 上传图文消息素材【订阅号与服务号认证后均可用】
	 * API 说明地址 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
	 */
	public static String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
}
