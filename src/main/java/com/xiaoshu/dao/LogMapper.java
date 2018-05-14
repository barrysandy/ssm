package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Log;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LogMapper extends BaseMapper<Log> {

	void truncateTable();

	/** 清理失效日志*/

	@Delete("DELETE from log WHERE createTime <#{date} ")
	Integer deleteInvalidLogByDate(Date date);
}