package com.xiaoshu.dao;

import com.xiaoshu.entity.KeyWords;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.*;
public interface KeyWordsMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-09 10:50
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    int insert(KeyWords record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    int insertSelective(KeyWords record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return KeyWords 实体类
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    KeyWords selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    int updateByPrimaryKeySelective(KeyWords record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    int updateByPrimaryKey(KeyWords record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    List<KeyWords> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-09 10:50
	 */
    int selectCount(Map record);

	/**
	 * 按keyes查询关键字：精准查询
	 * @param menuId PARENT_MENU_ID为公众号的menuId
	 * @param keyes 查询的关键字完全匹配字符
	 * @author: XGB
	 * @return 返回查询到的关键字
	 * @throws Exception
	 */
	@Select("select * from key_words where PARENT_MENU_ID = #{menuId} AND KEYES = #{keyes} limit 0,1")
	KeyWords selectByKey(@Param("menuId")String menuId,@Param("keyes")String keyes);
}