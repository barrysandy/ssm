package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatActivityPrize;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface WechatActivityPrizeMapper {

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:38
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
    int saveRecord(WechatActivityPrize record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
    int saveSelective(WechatActivityPrize record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WechatActivityPrize 实体类
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
	WechatActivityPrize getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
    int updateByPrimaryKeySelective(WechatActivityPrize record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
    int updateByPrimaryKey(WechatActivityPrize record);

    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
    List<WechatActivityPrize> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: XGB
     * @date: 2018-02-06 11:38
	 */
    int selectCount(Map record);

	/**
	 * 按奖品的活动id查询奖品
	 * @param id 活动id
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 15:14
	 */
//	@Select("SELECT * FROM wechat_activity_prize where WECHAT_ACTIVITY_ID=#{id}")
	List<WechatActivityPrize> getByActivityId(String id);

	/**
	 * 查询所有活动奖品，活动id，name字段
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 15:31
	 */
	@Select("SELECT ID,NAME FROM wechat_activity_prize")
	List<WechatActivityPrize> getAllActivityPrize();

	/**
	 * 更新奖品的所属活动
	 * @param  id
	 * @param  wechatActivityId
	 * @return int 更新结果
	 * @author: XGB
	 * @date: 2018-02-11 16:50
	 */
	@Update("update wechat_activity_prize set WECHAT_ACTIVITY_ID=#{wechatActivityId} where ID=#{id} ")
	int updateWechatActivityIdById(@Param("id") String id, @Param("wechatActivityId") String wechatActivityId);

}