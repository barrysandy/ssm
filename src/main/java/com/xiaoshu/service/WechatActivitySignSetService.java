package com.xiaoshu.service;

import com.xiaoshu.entity.WechatActivitySignSet;

import java.util.List;
import java.util.Map;

/**
 * 微信活动属性配置表
 * @author: XGB
 * @date: 2018-02-09 9:48
 */
public interface WechatActivitySignSetService {


	/**
	 * 按活动id查询全部属性
	 * @param wechatActivityId 活动id
	 * @param setType 是配置还是值。  0 配置，1值。(SET_TYPE)
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-09 9:51
	 */
	List<WechatActivitySignSet> getAllByActivityId(String wechatActivityId,int setType);


	/**
	 * 按活动报名id查询全部属性
	 * @param wechatActivitySignId 活动报名id
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-24 15:34
	 */
	List<WechatActivitySignSet> getAllValuesByActivitySignId(String wechatActivitySignId);

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:37
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 删除
	 * @param wechatActivityId 活动id
	 * @param setType 属性类型
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-10 9:50
	 */
	int deleteByWechatActivityId(String wechatActivityId,int setType);

	/**
	 * 删除
	 * @param wechatActivitySignId 活动id
	 * @param setType 属性类型
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-25 8:50
	 */
	int deleteByWechatActivitySignId(String wechatActivitySignId,int setType);

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int saveRecord(WechatActivitySignSet record);

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int saveSelective(WechatActivitySignSet record);

	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatActivitySignSet 实体类
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	WechatActivitySignSet getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int updateByPrimaryKeySelective(WechatActivitySignSet record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int updateByPrimaryKey(WechatActivitySignSet record);

	/**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	List<WechatActivitySignSet> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int selectCount(Map record);


	/**
	 * 查询用户是否报名
	 * @param wechatActivityId 活动id
	 * @param signSetsValues 验证重复的值
	 * @return 返回查询的统计数
	 */
	int selectUserExitSignBySignSetValue(String wechatActivityId, String signSetsValues);


	String getValueByAIDAndUID( String wechatActivitySignId , String wechatActivityId , String names ) throws Exception;

}
