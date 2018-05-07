package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivityPrizeMapper;
import com.xiaoshu.entity.WechatActivityPrize;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 微信活动表
* @author: XGB
* @date: 2018-02-11 10:54
 */
@Service("wechatActivityPrizeService")
public class WechatActivityPrizeServiceImpl implements WechatActivityPrizeService {

	@Resource private WechatActivityPrizeMapper mapper;


	/**
	 * 分页查询
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 10:54
	 */
	public List<WechatActivityPrize> selectByPage(Map map){
		return mapper.selectByPage(map);
	}

	/**
	 * 通过主键ID查询
	 * @author: XGB
	 * @date: 2018-02-11 10:54
	 */
	public WechatActivityPrize getByPrimaryKey(String id){
		return mapper.getByPrimaryKey(id);
	}

	/**
	 * 查询总条数
	 * @return 总条数
	 * @author: XGB
	 * @date: 2018-02-11 10:54
	 */
	public int selectCount(Map record){
		return mapper.selectCount(record);
	}

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-11 10:54
	 */
	public int updateRecord(WechatActivityPrize record){
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-11 10:54
	 */
	public int saveRecord(WechatActivityPrize record){
		return mapper.saveRecord(record);
	}

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-11 10:54
	 */
	public int delete(String id){
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 按奖品的活动id查询奖品
	 * @param id 活动id
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 15:14
	 */
	public List<WechatActivityPrize> getByActivityId(String id){
		return mapper.getByActivityId(id);
	}

	/**
	 * 查询所有活动奖品，活动id，name字段
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-11 15:31
	 */
	public List<WechatActivityPrize> getAllActivityPrize(){
		return mapper.getAllActivityPrize();
	}


	/**
	 * 更新奖品的所属活动
	 * @param  id
	 * @param  wechatActivityId
	 * @return int 更新结果
	 * @author: XGB
	 * @date: 2018-02-11 16:50
	 */
	public int updateWechatActivityIdById(String id,String wechatActivityId){
		return mapper.updateWechatActivityIdById(id,wechatActivityId);
	}
}
