package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivityAnswerMapper;
import com.xiaoshu.entity.WechatActivityAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信活动表
* @author: XGB
* @date: 2018-01-09 10:50
 */
@Service("wechatActivityAnswerService")
public class WechatActivityAnswerServiceImpl implements WechatActivityAnswerService {

	@Resource private WechatActivityAnswerMapper mapper;

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-03-05 14:00
	 */
	public int deleteByPrimaryKey(String id){
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 删除questionsId的问题
	 * @param questionsId 问题主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-03-05 14:53
	 */
	public int deleteByQuestionId(String questionsId){
		return mapper.deleteByQuestionId(questionsId);
	}

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-03-05 14:00
	 */
	public int saveRecord(WechatActivityAnswer record){
		return mapper.saveRecord(record);
	}


	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatActivityAnswer 实体类
	 * @author: XGB
	 * @date: 2018-03-05 14:00
	 */
	public WechatActivityAnswer getByPrimaryKey(String id){
		return mapper.getByPrimaryKey(id);
	}

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 14:00
	 */
	public int updateByPrimaryKeySelective(WechatActivityAnswer record){
		return updateByPrimaryKeySelective(record);
	}

	/**
	 * 查询
	 * @param questionsId 参数MAP
	 * @return List
	 * @author: XGB
	 * @date: 2018-03-05 14:00
	 */
	public List<WechatActivityAnswer> getListByQuestionId(String questionsId){
		return mapper.getListByQuestionId(questionsId);
	}


}
