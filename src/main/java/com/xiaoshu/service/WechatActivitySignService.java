package com.xiaoshu.service;

import com.xiaoshu.entity.WechatActivitySign;
import com.xiaoshu.entity.WechatActivitySignSet;

import java.util.List;
import java.util.Map;

/**
 * 微信活动报名表
 * @author: XGB
 * @date: 2018-02-12 12:10
 */
public interface WechatActivitySignService {


	/**
	 * 按活动id查询全部
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-09 9:54
	 */
	List<WechatActivitySign> getAllSignByActivityId(String wechatActivityId,int draw);

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:37
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int saveRecord(WechatActivitySign record);

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int saveSelective(WechatActivitySign record);

	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatActivitySign 实体类
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	WechatActivitySign getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int updateByPrimaryKeySelective(WechatActivitySign record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
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
	int updateByPrimaryKeyDraw(String id,int draw);

	/**
	 * 查询用户是否报名
	 * @param wechatActivityId 活动id
	 * @param userId 用户id
	 * @return 返回查询的统计数
	 */
	int selectUserExitSign(String wechatActivityId,String userId);


	/**
	 * 分页查询
	 * @param search 查询条件
	 * @param startRow 分页开始
	 * @param pageSize 每页数
	 * @param wechatActivityId 活动id
	 * @param draw 抽奖
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	List<WechatActivitySign> selectByPage(String search ,int startRow,int pageSize,String wechatActivityId,int draw);

	/**
	 * 查询总条数
	 * @param search 查询条件
	 * @param startRow 分页开始
	 * @param pageSize 每页数
	 * @param wechatActivityId 活动id
	 * @param draw 抽奖
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-02-06 11:37
	 */
	int selectCount(String search ,int startRow,int pageSize,String wechatActivityId,int draw);

	WechatActivitySign getByUserAndActivityId(String wechatActivityId,String userId);

}
