package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivityTestQuestionsMapper;
import com.xiaoshu.entity.WechatActivityTestQuestions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 微信活动表
* @author: XGB
* @date: 2018-01-09 10:50
 */
@Service("wechatActivityTestQuestionsService")
public class WechatActivityTestQuestionsServiceImpl implements WechatActivityTestQuestionsService {

	@Resource private WechatActivityTestQuestionsMapper mapper;

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-03-05 11:00
	 */
	public int deleteByPrimaryKey(String id){
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	public int saveRecord(WechatActivityTestQuestions record){
		return mapper.saveRecord(record);
	}


	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatActivityTestQuestions 实体类
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	public WechatActivityTestQuestions getByPrimaryKey(String id){
		return mapper.getByPrimaryKey(id);
	}

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:302018-03-05 11:00
	 */
	public int updateByPrimaryKeySelective(WechatActivityTestQuestions record){
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	public List<WechatActivityTestQuestions> selectByPage(Map map){
		return mapper.selectByPage(map);
	}

	/**
	 * 查询总条数
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	public int selectCount(Map record){
		return mapper.selectCount(record);
	}

	/**
	 * 根据活动ID查询题目列表
	 * @return List
	 * @author: XGB
	 * @date: 2018-03-05 11:00
	 */
	public List<WechatActivityTestQuestions> getListTestQuestionsByActivityID(String wechatActivityId,String status){
		return mapper.getListTestQuestionsByActivityID(wechatActivityId,status);
	}


}
