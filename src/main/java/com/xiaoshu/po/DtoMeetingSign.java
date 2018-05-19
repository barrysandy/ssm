package com.xiaoshu.po;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 签字人实体类
 * @author XGB
 * @date 2018-05-9 15:37
 */
@Component
public class DtoMeetingSign implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String 姓名;
	private String 性别;
	private String 电话;
	private String 公司;
	private String 类别;
	private String 职位;
	private String 晚宴;
	private String 备注;

	public String get姓名() {
		return 姓名;
	}

	public void set姓名(String 姓名) {
		this.姓名 = 姓名;
	}

	public String get性别() {
		return 性别;
	}

	public void set性别(String 性别) {
		this.性别 = 性别;
	}

	public String get电话() {
		return 电话;
	}

	public void set电话(String 电话) {
		this.电话 = 电话;
	}

	public String get公司() {
		return 公司;
	}

	public void set公司(String 公司) {
		this.公司 = 公司;
	}

	public String get类别() {
		return 类别;
	}

	public void set类别(String 类别) {
		this.类别 = 类别;
	}

	public String get职位() {
		return 职位;
	}

	public void set职位(String 职位) {
		this.职位 = 职位;
	}

	public String get晚宴() {
		return 晚宴;
	}

	public void set晚宴(String 晚宴) {
		this.晚宴 = 晚宴;
	}

	public String get备注() {
		return 备注;
	}

	public void set备注(String 备注) {
		this.备注 = 备注;
	}

	public DtoMeetingSign() { }

	public DtoMeetingSign(String 姓名, String 性别, String 电话, String 公司, String 类别, String 职位, String 晚宴, String 备注) {
		this.姓名 = 姓名;
		this.性别 = 性别;
		this.电话 = 电话;
		this.公司 = 公司;
		this.类别 = 类别;
		this.职位 = 职位;
		this.晚宴 = 晚宴;
		this.备注 = 备注;
	}

	@Override
	public String toString() {
		return "DtoMeetingSign{" +
				"姓名='" + 姓名 + '\'' +
				", 性别='" + 性别 + '\'' +
				", 电话='" + 电话 + '\'' +
				", 公司='" + 公司 + '\'' +
				", 类别='" + 类别 + '\'' +
				", 职位='" + 职位 + '\'' +
				", 晚宴='" + 晚宴 + '\'' +
				", 备注='" + 备注 + '\'' +
				'}';
	}
}