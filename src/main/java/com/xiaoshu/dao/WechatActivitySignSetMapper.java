package com.xiaoshu.dao;

import com.xiaoshu.entity.WechatActivitySignSet;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface WechatActivitySignSetMapper {

	/**
	 * 按活动id查询全部属性
	 * @param wechatActivityId 活动id
	 * @param setType 是配置还是值。  0 配置，1值。(SET_TYPE)
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-09 9:51
	 */
	List<WechatActivitySignSet> getAllByActivityId(@Param("wechatActivityId") String wechatActivityId, @Param("setType") int setType);

	/**
	 * 按活动报名id查询全部属性
	 * @param wechatActivitySignId 活动报名id
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-24 15:34
	 */
	@Select("SELECT ID,CHINESE_CHARACTER_NAME,NAME_PLACEHOLDER,NAME,VALUESE,SET_TYPE,TYPESE,REQUIRED,VERIFICATION_TYPE,SORT,HIDE,REPEATS,DESC_M,WECHAT_ACTIVITY_ID,WECHAT_ACTIVITY_SIGN_ID,STATUS,CREATE_TIME,UPDATE_TIME FROM wechat_activity_sign_set WHERE WECHAT_ACTIVITY_SIGN_ID = #{wechatActivitySignId} ORDER BY SORT")
	List<WechatActivitySignSet> getAllValuesByActivitySignId(@Param("wechatActivitySignId") String wechatActivitySignId);

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:37
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 删除
	 * @param wechatActivityId 活动id
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:37
	 */
	@Delete("delete from wechat_activity_sign_set where WECHAT_ACTIVITY_ID=#{wechatActivityId} AND SET_TYPE=#{setType}")
	int deleteByWechatActivityId(@Param("wechatActivityId") String wechatActivityId,@Param("setType") int setType);


	/**
	 * 删除
	 * @param wechatActivitySignId 活动id
	 * @param setType 属性类型
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-25 8:50
	 */
	@Delete("delete from wechat_activity_sign_set where WECHAT_ACTIVITY_SIGN_ID=#{wechatActivitySignId} AND SET_TYPE=#{setType}")
	int deleteByWechatActivitySignId(@Param("wechatActivitySignId") String wechatActivitySignId,@Param("setType") int setType);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
	@Insert("insert into wechat_activity_sign_set " +
			"(ID,CHINESE_CHARACTER_NAME,NAME_PLACEHOLDER,NAME,VALUESE,SET_TYPE,TYPESE,REQUIRED,VERIFICATION_TYPE,SORT,HIDE,REPEATS,WECHAT_ACTIVITY_ID,WECHAT_ACTIVITY_SIGN_ID,STATUS,CREATE_TIME,UPDATE_TIME,DESC_M)" +
			"VALUES " +
			"(#{id},#{chineseCharacterName},#{namePlaceholder},#{name},#{valuese},#{setType},#{typese},#{required},#{verificationType},#{sort},#{hide},#{repeat},#{wechatActivityId},#{wechatActivitySignId},#{status},#{createTime},#{updateTime},#{descM})")
    int saveRecord(WechatActivitySignSet record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
    int saveSelective(WechatActivitySignSet record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return WechatActivitySignSet 实体类
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
	WechatActivitySignSet getByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
    int updateByPrimaryKeySelective(WechatActivitySignSet record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
    int updateByPrimaryKey(WechatActivitySignSet record);

    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
    List<WechatActivitySignSet> selectByPage(Map map);

	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: XGB
     * @date: 2018-02-06 11:37
	 */
    int selectCount(Map record);


	/**
	 * 查询用户是否报名
	 * @param wechatActivityId 活动id
	 * @param signSetsValues 验证重复的值
	 * @return 返回查询的统计数
	 */
	@Select("SELECT ID,CHINESE_CHARACTER_NAME,NAME_PLACEHOLDER,NAME,VALUESE,SET_TYPE,TYPESE,REQUIRED,VERIFICATION_TYPE,SORT,HIDE,REPEATS,DESC_M,WECHAT_ACTIVITY_ID,WECHAT_ACTIVITY_SIGN_ID,STATUS,CREATE_TIME,UPDATE_TIME FROM wechat_activity_sign_set WHERE WECHAT_ACTIVITY_ID = #{wechatActivityId} AND VALUESE = #{signSetsValues}")
	int selectUserExitSignBySignSetValue(String wechatActivityId, String signSetsValues);


	/** 通过报名Id和活动ID以及名称确定报名参数值 */
	@Select("SELECT VALUESE FROM wechat_activity_sign_set WHERE WECHAT_ACTIVITY_SIGN_ID = #{wechatActivitySignId} AND WECHAT_ACTIVITY_ID = #{wechatActivityId} AND NAME=#{names} ")
	String getValueByAIDAndUID(@Param("wechatActivitySignId") String wechatActivitySignId ,@Param("wechatActivityId") String wechatActivityId ,@Param("names") String names );
}