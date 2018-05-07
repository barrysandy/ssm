package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivityMapper;
import com.xiaoshu.entity.WechatActivity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 微信活动表
* @author: XGB
* @date: 2018-01-09 10:50
 */
@Service("wechatActivityService")
public class WechatActivityServiceImpl implements WechatActivityService {

	@Resource private WechatActivityMapper mapper;

	/**
	 * 分页查询
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-06 16:51
	 */
	@Override
	public List<WechatActivity> selectByPage(Map map){
		return mapper.selectByPage(map);
	}

	/**
	 * 通过主键ID查询
	 * @author: XGB
	 * @date: 2018-02-06 16:51
	 */
	@Override
	public WechatActivity getByPrimaryKey(String id){
		return mapper.getByPrimaryKey(id);
	}

	/**
	 * 查询总条数
	 * @return 总条数
	 * @author: XGB
	 * @date: 2018-02-06 16:51
	 */
	@Override
	public Integer selectCount(Map record){
		return mapper.selectCount(record);
	}

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 16:51
	 */
	@Override
	public Integer updateRecord(WechatActivity record){
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 16:51
	 */
	@Override
	public Integer saveRecord(WechatActivity record){
		return mapper.saveRecord(record);
	}

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 16:51
	 */
	@Override
	public Integer delete(String id){
		return mapper.deleteByPrimaryKey(id);
	}


	/**
	 * 查询所有活动，活动id，title字段
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 11:37
	 */
	@Override
	public List<WechatActivity> getAllActivity(){
		return mapper.getAllActivity();
	}

	/**
	 * 通过主键ID查询活动TITLE
	 * @param id 实体类
	 * @return title
	 * @author: XGB
	 * @date: 2018-02-11 14:14
	 */
	@Override
	public String getTitleByPrimaryKey(String id){
		return mapper.getTitleByPrimaryKey(id);
	}

	/**
	 * 通过主键ID查询活动SCAN_USER_ID_ARRAY
	 * @param id 实体类
	 * @return scanUserIdArray
	 * @author: XGB
	 * @date: 2018-03-06 16:37
	 */
	@Override
	public String getScanUserIdArrayById(String id){
		return mapper.getScanUserIdArrayById(id);
	}

	/**
	 * 更新数据
	 * @param id 活动id
	 * @param scanUserIdArray 活动scanUserIdArray
	 * @return int 更新活动
	 * @author: XGB
	 * @date: 2018-03-07 10:04
	 */
	@Override
	public Integer updateScanUser(String id,String scanUserIdArray){
		return mapper.updateScanUser(id,scanUserIdArray);
	}


	@Override
	public String getUrlById(String id ) throws Exception{
		return mapper.getUrlById(id);
	}



	/** 获取当前正在进行的会话活动 */
	@Override
	public List<WechatActivity> getSignSessionList(String nowTime,String parentMenuId) throws Exception{
		return mapper.getSignSessionList(nowTime,parentMenuId);
	}

}
