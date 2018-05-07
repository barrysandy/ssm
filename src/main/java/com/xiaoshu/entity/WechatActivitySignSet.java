package com.xiaoshu.entity;

import java.util.Date;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 微信活动配置表的实体类<br/>
* @author: XGB
* @date: 2018-02-06 11:16
 */
public class WechatActivitySignSet {

	private String id; //(ID)
	private String chineseCharacterName; // 汉字名称(CHINESE_CHARACTER_NAME)
	private String name; //属性名称。(NAME)
	private String namePlaceholder; //名字默认占位符 (NAME_PLACEHOLDER)
	private String valuese; //属性名称对应的值。(VALUESE)
	private int setType; //是配置还是值。  0 配置，1值。(SET_TYPE)
	private String typese; //属性类型。radio/checkBox/input/textArea....(TYPESE)
	private int required; //是否必须。  -1 非必须，1必须。(REQUIRED)
	private String verificationType; //属性验证类型。(VERIFICATION_TYPE)
	private int sort; //排序(SORT)
	private int hide; //是否隐藏。  -1不 隐藏，1隐藏。(HIDE)
	private int repeat; //是否验证重复字段。  -1 忽略重复，1验证重复。(REPEATS)
	private String descM; //描述。(DESC_M)
	private String wechatActivityId; //微信活动的id。确定报的那个活动。(WECHAT_ACTIVITY_ID)
	private String wechatActivitySignId; //微信活动报名的id。确定报的那个报名。(WECHAT_ACTIVITY_SIGN_ID)
	private int status; //状态。-1逻辑删除 ，1存在。(STATUS)
	private Date createTime; //创建时间。(CREATE_TIME)
	private Date updateTime; //更新时间。(UPDATE_TIME)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChineseCharacterName() {
		return chineseCharacterName;
	}

	public void setChineseCharacterName(String chineseCharacterName) {
		this.chineseCharacterName = chineseCharacterName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamePlaceholder() {
		return namePlaceholder;
	}

	public void setNamePlaceholder(String namePlaceholder) {
		this.namePlaceholder = namePlaceholder;
	}

	public String getValuese() {
		return valuese;
	}

	public void setValuese(String valuese) {
		this.valuese = valuese;
	}

	public int getSetType() {
		return setType;
	}

	public void setSetType(int setType) {
		this.setType = setType;
	}

	public String getTypese() {
		return typese;
	}

	public void setTypese(String typese) {
		this.typese = typese;
	}

	public int getRequired() {
		return required;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}

	public String getWechatActivityId() {
		return wechatActivityId;
	}

	public void setWechatActivityId(String wechatActivityId) {
		this.wechatActivityId = wechatActivityId;
	}

	public String getWechatActivitySignId() {
		return wechatActivitySignId;
	}

	public void setWechatActivitySignId(String wechatActivitySignId) {
		this.wechatActivitySignId = wechatActivitySignId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public WechatActivitySignSet() {
	}

	public WechatActivitySignSet(String id, String name, String valuese, int setType, String typese, int required, String verificationType, int sort, int hide, int repeat, String descM, String wechatActivityId, String wechatActivitySignId, int status, Date createTime, Date updateTime) {
		this.id = id;
		this.name = name;
		this.valuese = valuese;
		this.setType = setType;
		this.typese = typese;
		this.required = required;
		this.verificationType = verificationType;
		this.sort = sort;
		this.hide = hide;
		this.repeat = repeat;
		this.descM = descM;
		this.wechatActivityId = wechatActivityId;
		this.wechatActivitySignId = wechatActivitySignId;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public WechatActivitySignSet(String id, String chineseCharacterName, String name, String namePlaceholder, String valuese, int setType, String typese, int required, String verificationType, int sort, int hide, int repeat, String descM, String wechatActivityId, String wechatActivitySignId, int status, Date createTime, Date updateTime) {
		this.id = id;
		this.chineseCharacterName = chineseCharacterName;
		this.name = name;
		this.namePlaceholder = namePlaceholder;
		this.valuese = valuese;
		this.setType = setType;
		this.typese = typese;
		this.required = required;
		this.verificationType = verificationType;
		this.sort = sort;
		this.hide = hide;
		this.repeat = repeat;
		this.descM = descM;
		this.wechatActivityId = wechatActivityId;
		this.wechatActivitySignId = wechatActivitySignId;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "WechatActivitySignSet{" +
				"id='" + id + '\'' +
				", chineseCharacterName='" + chineseCharacterName + '\'' +
				", name='" + name + '\'' +
				", namePlaceholder='" + namePlaceholder + '\'' +
				", valuese='" + valuese + '\'' +
				", setType=" + setType +
				", typese='" + typese + '\'' +
				", required=" + required +
				", verificationType='" + verificationType + '\'' +
				", sort=" + sort +
				", hide=" + hide +
				", repeat=" + repeat +
				", descM='" + descM + '\'' +
				", wechatActivityId='" + wechatActivityId + '\'' +
				", wechatActivitySignId='" + wechatActivitySignId + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}