package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatActivityWinning;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface WechatActivityWinningMapper {

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:39
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 删除
	 * @param wechatActivityId 活动ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:39
	 */
	@Delete("delete from wechat_activity_winning where WECHAT_ACTIVITY_ID=#{wechatActivityId}")
	int deleteByWechatActivitySign(@Param("wechatActivityId") String wechatActivityId);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
    int saveRecord(WechatActivityWinning record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
    int saveSelective(WechatActivityWinning record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WechatActivityWinning 实体类
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
	WechatActivityWinning getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
    int updateByPrimaryKeySelective(WechatActivityWinning record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
    int updateByPrimaryKey(WechatActivityWinning record);

    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
    List<WechatActivityWinning> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: XGB
     * @date: 2018-02-06 11:39
	 */
    int selectCount(Map record);

    /**
     * 通过code查询
     * @param code 核销码
     * @return int 0 表示不存在
     * @author: XGB
     * @date: 2018-02-26 10:39
     */
    @Select("SELECT COUNT(ID) FROM wechat_activity_winning WHERE CODE = #{code}")
    int getExitByCode(@Param("code") String code);


	/**
	 * 分页查询
	 * @param userId
	 * @param startRow
	 * @param pageSize
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	@Select("SELECT * FROM wechat_activity_winning WHERE USER_ID =#{userId} order by CREATE_TIME DESC limit #{startRow},#{pageSize}")
	List<WechatActivityWinning> getListPageByUserId(@Param("userId") String userId,@Param("startRow") int startRow,@Param("pageSize") int pageSize);

	/**
	 * 查询总条数
	 * @param userId
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	@Select("SELECT COUNT(ID) FROM wechat_activity_winning WHERE USER_ID =#{userId}")
	int selectCountByUserId(@Param("userId") String userId);

	/**
	 * 按Code获取中奖信息
	 * @param code
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-07 14:55
	 */
	@Select("SELECT * FROM wechat_activity_winning WHERE CODE =#{code} limit 0,1")
	WechatActivityWinning getByCode(@Param("code") String code);

	/**
	 * 核销
	 * @param code
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-07 15:40
	 */
	@Update("update wechat_activity_winning set STATUS = 1 WHERE CODE =#{code}")
	int updateStateByCode(@Param("code") String code);

}