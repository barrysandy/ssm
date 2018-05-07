package com.xiaoshu.service;

import com.xiaoshu.entity.WechatActivityTestQuestions;

import java.util.List;
import java.util.Map;

/**
 * 微信活动问题列表表
 * @author: XGB
 * @date: 2018-03-05 11:10
 */
public interface WechatActivityTestQuestionsService {

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-03-05 11:00
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	int saveRecord(WechatActivityTestQuestions record);


	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatActivityTestQuestions 实体类
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	WechatActivityTestQuestions getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:302018-03-05 11:00
	 */
	int updateByPrimaryKeySelective(WechatActivityTestQuestions record);

	/**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	List<WechatActivityTestQuestions> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	int selectCount(Map record);

	/**
	 * 根据活动ID查询题目列表
	 * @return List
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	List<WechatActivityTestQuestions> getListTestQuestionsByActivityID(String wechatActivityId,String status);

}
