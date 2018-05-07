package com.xiaoshu.service;

import com.xiaoshu.entity.PublicAccountInfo;

import java.util.*;

/**
 * 
* 公众号基础信息表
* @author Kun
* @date 2018-01-03 17:04
 */
public interface PublicAccountInfoService {

	/**
	* 分頁查詢
	* @return
    * @author：Kun
    * @date：2018-01-03 17:04
	 */
	List<PublicAccountInfo> selectByPage(Map map);
	/**
	 * 
	* 详情
    * @author：Kun
    * @date：2018-01-03 17:04
	 */
	PublicAccountInfo selectByPrimaryKey(String id);
	/**
	 * 
	* 描述方法作用
	* @return
    * @author：Kun
    * @date：2018-01-03 17:04
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	* @param bean
	* @return
    * @author：Kun
    * @date：2018-01-03 17:04
	 */
	int update(PublicAccountInfo bean);
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：Kun
    * @date：2018-01-03 17:04
	 */
	int insert(PublicAccountInfo bean);
	/**
	 * 
	* 删除
	* @param id
	* @return
	* @return
	* @author lishiqiang
	* @date 2017-3-16
	* modify history
	 */
	int delete(String id);


	PublicAccountInfo getInfoByUserId(Integer userId,String menuId);

	/**
	 * 查询PublicAccountInfo
	 * @param menuId 公众号menuId
	 * @author XGB
	 * @date  2018-01-17 11:50
	 */
	PublicAccountInfo getInfoByMenuid(String menuId);

	PublicAccountInfo getByParentMenuId(String parentMenuId);

	/**
	 * 查询\所有PublicAccountInfo
	 * @author XGB
	 * @date  2018-01-17 21:00
	 */
	List<PublicAccountInfo> selectListAll(int usable);

	/**
	 * 根据openPlatform查询不为parentMenuId的公众号parentMenuId（也就是查询绑定的另一个parentMenuId）
	 * @param openPlatform
	 *  @param parentMenuId
	 * @author XGB
	 * @date  2018-03-9 10:23
	 */
	String getParentMenuIdByOpenPlatform(String openPlatform,String parentMenuId);

	String getIdByParentMenuId(String parentMenuId);

	String getOtherParentMenuIdByMenuId(String parentMenuId);

	String getParentMenuIdById(String id) throws Exception;

	/** 获取公众号类型 */
	int getAccountTypeByParentMenuId( String parentMenuId) throws Exception;
}
