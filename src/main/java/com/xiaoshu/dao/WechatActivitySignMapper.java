package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatActivitySign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface WechatActivitySignMapper {

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:35
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:35
	 */
    int saveRecord(WechatActivitySign record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:35
	 */
    int saveSelective(WechatActivitySign record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WechatActivitySign 实体类
     * @author: XGB
     * @date: 2018-02-06 11:35
	 */
	WechatActivitySign getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:36
	 */
    int updateByPrimaryKeySelective(WechatActivitySign record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:36
	 */
    int updateByPrimaryKey(WechatActivitySign record);

	/**
	 * 更新数据(更新中奖状态)
	 * @param id
	 * @param draw
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-25 10:43
	 */
	@Update("update wechat_activity_sign set DRAW=#{draw} Where WECHAT_ACTIVITY_ID=#{id} ")
	int updateByPrimaryKeyDraw(@Param("id") String id,@Param("draw") int draw);

    /**
	 * 分页查询
	 * @param search 查询条件
	 * @param startRow 分页开始
	 * @param pageSize 每页数
	 * @param wechatActivityId 活动id
	 * @param draw 抽奖
	 * @return List
     * @author: XGB
     * @date: 2018-02-06 11:36
	 */
    List<WechatActivitySign> selectByPage(@Param("search") String search ,@Param("startRow") int startRow,@Param("pageSize") int pageSize,@Param("wechatActivityId") String wechatActivityId,@Param("draw") int draw);

	/**
	 * 查询总条数
	 * @param search 查询条件
	 * @param startRow 分页开始
	 * @param pageSize 每页数
	 * @param wechatActivityId 活动id
	 * @param draw 抽奖
	 * @return int 总条数
     * @author: XGB
     * @date: 2018-02-06 11:36
	 */
    int selectCount(@Param("search") String search ,@Param("startRow") int startRow,@Param("pageSize") int pageSize,@Param("wechatActivityId") String wechatActivityId,@Param("draw") int draw);

	/**
	 * 查询用户是否报名
	 * @param wechatActivityId 活动id
	 * @param userId 用户id
	 * @return 返回查询的统计数
	 */
	@Select("select count(ID) from wechat_activity_sign where  WECHAT_ACTIVITY_ID =#{wechatActivityId} and USER_ID =#{userId}")
	int selectUserExitSign(@Param("wechatActivityId") String wechatActivityId,@Param("userId") String userId);

	/**
	 * 查询所有报名
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-12 14:41
	 */
	List<WechatActivitySign> getAllSignByActivityId(@Param("wechatActivityId") String wechatActivityId,@Param("draw") int draw);


	@Select("select * from wechat_activity_sign where  WECHAT_ACTIVITY_ID =#{wechatActivityId} and USER_ID =#{userId}")
	WechatActivitySign getByUserAndActivityId(@Param("wechatActivityId") String wechatActivityId,@Param("userId") String userId);


}