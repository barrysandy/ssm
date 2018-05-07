package com.xiaoshu.dao;

import com.xiaoshu.entity.Art;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.*;
public interface ArtMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-09 10:49
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    int insert(Art record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    int insertSelective(Art record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
	 * @return Art 实体类
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    Art selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    int updateByPrimaryKeySelective(Art record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    int updateByPrimaryKey(Art record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    List<Art> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
    int selectCount(Map record);

	/**
	 * 分期查询文章
	 * @param menuId 文章的所属公众号menuId
	 * @param current 查询的文章是第多少期
	 * @return String (JSON)
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 14:26
	 */
	@Select("select * from  art where  PARENT_MENU_ID = #{menuId} AND CURRENT_PERIOD = #{current} ORDER BY CURRENT_PERIOD_SORT")
	List<Art> selectByPeriod(@Param("menuId") String menuId, @Param("current") Integer current);

	/**
	 * 查询本期文章
	 * @return List
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 14:26
	 */
	@Select("select * from  art where  PARENT_MENU_ID = #{menuId} AND CURRENT_PERIOD = (SELECT MAX(CURRENT_PERIOD) from art where PARENT_MENU_ID = #{menuId}) ORDER BY CURRENT_PERIOD_SORT")
	List<Art> getCurrentPeriodArt(String menuId);
}