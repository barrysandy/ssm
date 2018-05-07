package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatMaterial;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface WechatMaterialMapper {

	/**
	 * 保存一个material对象
	 * @param material 对象
	 * @author XGB
	 * @date  2018-01-22 17:11
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Insert("insert into wechat_material(ID,DESC_M,TYPESE,URL,MATERIAL_ID,CREATE_TIME,UPDATE_TIME,STATUS,MENU_ID,PARENT_MENU_ID) " +
			"values(#{id},#{descM}, #{typese},#{url},#{material_id},#{createTime},#{updateTime},#{status},#{menuId},#{parentMenuId} )")
	int insertWMmater(WechatMaterial material) throws Exception;
	
	
	/**
	 * 按id更新material对象
	 * @param material 对象
	 * @author XGB
	 * @date  2018-01-22 17:11
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Update("update wechat_material set DESC_M=#{descM},TYPESE=#{typese},URL=#{url},MATERIAL_ID=#{material_id},CREATE_TIME=#{createTime}," +
			"UPDATE_TIME=#{updateTime},STATUS=#{status},MENU_ID=#{menuId},PARENT_MENU_ID=#{parentMenuId} where id=#{id} ")
	int updateWMmater(WechatMaterial material) throws Exception;
	
	
	/**
	 * 按material id删除指定material数据
	 * @param id material在数据库的id
	 * @author XGB
	 * @date  2018-01-22 17:12
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Delete("delete from wechat_material where ID=#{id}")
	int deleteWMmater(int id) throws Exception;
	
	/**
	 * 按material media_id删除指定material数据
	 * @param material_id
	 * @author XGB
	 * @date  2018-01-22 17:16
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Delete("delete from wechat_material where MATERIAL_ID=#{material_id}")
	int deleteWMmaterByMedia_Id(String material_id) throws Exception;

	/**
	 * 按素材id查询素材
	 * @param id 素材id
	 * @author XGB
	 * @date  2018-01-22 17:19
	 * @return 返回null 或 者被查询的素材对象
	 * @throws Exception 抛出异常
	 */
	@Select("select * from wechat_material where ID = #{id}")
	WechatMaterial selectWMmaterById(@Param("id")int id) throws Exception;

	/**
	 * 按素材material_id查询素材
	 * @param material_id 素材material_id
	 * @param parentMenuId   素材parentMenuId
	 * @author XGB
	 * @date  2018-02-2 10:52
	 * @return 返回null 或 者被查询的素材对象
	 * @throws Exception 抛出异常
	 */
	@Select("select * from wechat_material where MATERIAL_ID = #{material_id} AND PARENT_MENU_ID = #{parentMenuId} limit 0,1 ")
	WechatMaterial getWMmaterMaterialIdAndParentMenuId(@Param("material_id")String material_id,@Param("parentMenuId")String parentMenuId);

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-23 10:41
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int insert(WechatMaterial record);

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int insertSelective(WechatMaterial record);

	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatMaterial 实体类
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	WechatMaterial selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int updateByPrimaryKeySelective(WechatMaterial record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int updateByPrimaryKey(WechatMaterial record);

	/**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	List<WechatMaterial> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
	 * @author: Kun
	 * @date: 2018-01-23 10:41
	 */
	int selectCount(Map record);
}
