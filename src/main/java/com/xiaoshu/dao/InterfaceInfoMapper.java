package com.xiaoshu.dao;

import com.xiaoshu.entity.InterfaceInfo;
import org.apache.ibatis.annotations.Select;

import java.util.*;
public interface InterfaceInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-01-19 10:01
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    int insert(InterfaceInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    int insertSelective(InterfaceInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return InterfaceInfo 实体类
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    InterfaceInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    int updateByPrimaryKeySelective(InterfaceInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    int updateByPrimaryKey(InterfaceInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    List<InterfaceInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-01-19 10:01
	 */
    int selectCount(Map record);

	/**
	 * 通过KEYES查询
	 * @param keyes 实体类
	 * @return String url
	 * @author: XGB
	 * @date: 2018-01-30 15:21
	 */
	@Select("select URL from interface_info where KEYES = #{keyes} limit 0,1")
	String selectUrlByKeyes(String keyes);

}