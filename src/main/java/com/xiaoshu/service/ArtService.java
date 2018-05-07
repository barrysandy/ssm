package com.xiaoshu.service;

import com.xiaoshu.entity.Art;

import java.util.*;

/**
 * 文章
 * @author: Kun
 * @date: 2018-01-09 10:49
 */
public interface ArtService {

	/**
	 * 分页查询
	 * @param map 查询条件
	 * @return List
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
	List<Art> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
	 * @author: Kun
	 * @date: 2018-01-09 10:49
	 */
	int selectCount(Map record);
	
	/**
 	 * 通过主键ID查询
	 * @param id 主键ID
	 * @param replaceUrl 是否替换对应的资源url
	 * @return Art 实体类
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
	Art selectByPrimaryKey(String id,int replaceUrl);

	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
	 int update(Art bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:49
	 */
	 int insert(Art bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-09 10:49
	 */
	 int delete(String id);

	 /**
	  * 分期查询文章
	  * @param menuId 文章的所属公众号menuId
	  * @param current 查询的文章是第多少期
	  * @return String (JSON)
	  * @author zhou.zhengkun
	  * @date 2018/01/17 0017 14:26
	  */
	 List<Art> selectByPeriod(String menuId,Integer current);

	/**
	 * 查询本期文章
	 * @return String (JSON)
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 14:26
	 */
	String getCurrentPeriodArt(String menuId);
}
