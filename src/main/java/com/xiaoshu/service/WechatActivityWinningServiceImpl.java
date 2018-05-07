package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivityWinningMapper;
import com.xiaoshu.entity.WechatActivityWinning;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 微信活动表
* @author: XGB
* @date: 2018-01-09 10:50
 */
@Service("wechatActivityWinningService")
public class WechatActivityWinningServiceImpl implements WechatActivityWinningService {

	@Resource private WechatActivityWinningMapper mapper;
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:39
	 */
	public int deleteByPrimaryKey(String id){
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 删除
	 * @param wechatActivityId 活动ID
	 * @return int 修改行数
	 * @author XGB
	 * @date  2018-02-06 11:39
	 */
	public int deleteByWechatActivitySign(String wechatActivityId){
		return mapper.deleteByWechatActivitySign(wechatActivityId);
	}

	/**
	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public int saveRecord(WechatActivityWinning record){
		return mapper.saveRecord(record);
	}

	/**
	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public int saveSelective(WechatActivityWinning record){
		return mapper.saveSelective(record);
	}

	/**
	 * 通过主键ID查询
	 * @param id 实体类
	 * @return WechatActivityWinning 实体类
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public WechatActivityWinning getByPrimaryKey(String id){
		return mapper.getByPrimaryKey(id);
	}

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public int updateByPrimaryKeySelective(WechatActivityWinning record){
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public int updateByPrimaryKey(WechatActivityWinning record){
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public List<WechatActivityWinning> selectByPage(Map map){
		return mapper.selectByPage(map);
	}

	/**
	 * 查询总条数
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public int selectCount(Map record){
		return mapper.selectCount(record);
	}

    /**
     * 通过code查询
     * @param code 核销码
     * @return int 0 表示不存在
     * @author: XGB
     * @date: 2018-02-26 10:39
     */
    public int getExitByCode(String code){
        return mapper.getExitByCode(code);
    }

	/**
	 * 分页查询
	 * @param userId
	 * @param startRow
	 * @param pageSize
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public List<WechatActivityWinning> getListPageByUserId(String userId,int startRow,int pageSize){
		return mapper.getListPageByUserId(userId,startRow,pageSize);
	}

	/**
	 * 查询总条数
	 * @param userId
	 * @return int 总条数
	 * @author: XGB
	 * @date: 2018-02-06 11:39
	 */
	public int selectCountByUserId(String userId){
		return mapper.selectCountByUserId(userId);
	}

	/**
	 * 按Code获取中奖信息
	 * @param code
	 * @return List
	 * @author: XGB
	 * @date: 2018-02-07 14:55
	 */
	public WechatActivityWinning getByCode(String code){
		return mapper.getByCode(code);
	}

	/**
	 * 核销
	 * @param code
	 * @return int 更新生效行数
	 * @author: XGB
	 * @date: 2018-02-07 15:40
	 */
	public int updateStateByCode(String code){
		return mapper.updateStateByCode(code);
	}

}
