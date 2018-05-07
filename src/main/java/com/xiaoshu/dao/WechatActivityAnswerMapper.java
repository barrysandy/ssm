package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatActivityAnswer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WechatActivityAnswerMapper {

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-03-05 14:00
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 删除questionsId的问题
	 * @param questionsId 问题主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-03-05 14:53
	 */
	@Delete("delete from wechat_activity_answer where QUESTIONS_ID=#{questionsId}")
	int deleteByQuestionId(@Param("questionsId") String questionsId);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-03-05 14:00
	 */
    int saveRecord(WechatActivityAnswer record);


	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WechatActivityAnswer 实体类
     * @author: XGB
     * @date: 2018-03-05 14:00
	 */
	WechatActivityAnswer getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 14:00
	 */
    int updateByPrimaryKeySelective(WechatActivityAnswer record);

    /**
	 * 查询
	 * @param questionsId 参数MAP
	 * @return List
     * @author: XGB
     * @date: 2018-03-05 14:00
	 */
    List<WechatActivityAnswer> getListByQuestionId(String questionsId);

}