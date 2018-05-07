package com.xiaoshu.service;


import com.xiaoshu.entity.WechatMaterial;

import java.util.List;
import java.util.Map;

/**
 * 微信素材表
 * @author: XGB
 * @date: 2018-01-22 17:30
 */
public interface WechatMaterialService {

	/**
	 * 通过主键ID查询
	 * @param id 主键ID
	 * @return 微信素材实体类
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	WechatMaterial selectByPrimaryKey(String id);

	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int update(WechatMaterial bean);

	/**
	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int insert(WechatMaterial bean);

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-23 10:41
	 */
	int delete(String id);

	/**
	 * 保存一个material对象
	 * @param material 对象
	 * @author XGB
	 * @date  2018-01-22 17:11
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	int insertWMmater(WechatMaterial material) throws Exception;


	/**
	 * 按id更新material对象
	 * @param material 对象
	 * @author XGB
	 * @date  2018-01-22 17:11
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	int updateWMmater(WechatMaterial material) throws Exception;


	/**
	 * 按material id删除指定material数据
	 * @param id material在数据库的id
	 * @author XGB
	 * @date  2018-01-22 17:12
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	int deleteWMmater(int id) throws Exception;

	/**
	 * 按material media_id删除指定material数据
	 * @param material_id
	 * @author XGB
	 * @date  2018-01-22 17:16
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	int deleteWMmaterByMedia_Id(String material_id) throws Exception;

	/**
	 * 按素材id查询素材
	 * @param id 素材id
	 * @author XGB
	 * @date  2018-01-22 17:19
	 * @return 返回null 或 者被查询的素材对象
	 * @throws Exception 抛出异常
	 */
	WechatMaterial selectWMmaterById(int id) throws Exception;

	/**
	 * 按素材material_id查询素材
	 * @param material_id 素材material_id
	 * @param parentMenuId   素材parentMenuId
	 * @author XGB
	 * @date  2018-02-2 10:52
	 * @return 返回null 或 者被查询的素材对象
	 * @throws Exception 抛出异常
	 */
	WechatMaterial getWMmaterMaterialIdAndParentMenuId(String material_id,String parentMenuId);


	/**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
	 * @author: XGB
	 * @date: 2018-01-22 17:20
	 */
	List<WechatMaterial> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-01-22 17:20
	 */
	int selectCount(Map record);

}
