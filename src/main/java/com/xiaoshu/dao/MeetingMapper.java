package com.xiaoshu.dao;


import com.xiaoshu.entity.Meeting;
import org.apache.ibatis.annotations.*;
import java.util.List;

/** 标准版 */
public interface MeetingMapper {

	/** save one */
	@Insert("INSERT INTO t_meeting (" +
			"ID,IMAGE,TITLE,DESC_M,NAME," +
			"PHONE,CREATE_TIME,UPDATE_TIME,BEGIN_TIME,END_TIME," +
			"SIGN_TOTAL,STATUS,EXCEL_PATH) " +
			"VALUES(" +
			"#{id},#{image},#{title},#{descM},#{name}," +
			"#{phone},#{createTime},#{updateTime},#{beginTime},#{endTime}," +
			"#{signTotal},#{status},#{excelPath} )")
	Integer save(Meeting bean) throws Exception;

	/** update 状态 */
	@Update("UPDATE t_meeting SET STATUS=#{status},UPDATE_TIME=#{updateTime} where ID = #{id}")
	Integer updateResponseStatusById(@Param("status") Integer status, @Param("updateTime") String updateTime, @Param("id") String id) throws Exception;

	/** update excelPath */
	@Update("UPDATE t_meeting SET EXCEL_PATH=#{excelPath} where ID = #{id}")
	Integer updateExcelById(@Param("excelPath") String excelPath,@Param("id") String id) throws Exception;


	/** update all */
	@Update("UPDATE t_meeting SET TITLE=#{title},IMAGE=#{image},DESC_M=#{descM},NAME=#{name},PHONE=#{phone},CREATE_TIME=#{createTime}," +
			"UPDATE_TIME=#{updateTime},BEGIN_TIME=#{beginTime},END_TIME=#{endTime},SIGN_TOTAL=#{signTotal},STATUS=#{status},EXCEL_PATH=#{excelPath} WHERE ID=#{id} ")
	Integer updateAll(Meeting bean) throws Exception;

	/** delete ById */
	@Delete("DELETE FROM t_meeting WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id) throws Exception;

	/** select ById */
	@Select("SELECT ID,TITLE,IMAGE,DESC_M,NAME,PHONE,CREATE_TIME,UPDATE_TIME,BEGIN_TIME,END_TIME,SIGN_TOTAL,STATUS,EXCEL_PATH FROM t_meeting WHERE ID=#{id}")
	Meeting getById(@Param("id") String id) throws Exception;

	/** select List */
	List<Meeting> getListByKeyWord(@Param("status") Integer status,@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword, @Param("date1") String date1, @Param("date2") String date2) throws Exception;

	/** Count List */
	Integer getCountByKeyWord(@Param("status") Integer status,@Param("keyword") String keyword, @Param("date1") String date1, @Param("date2") String date2) throws Exception;

}
