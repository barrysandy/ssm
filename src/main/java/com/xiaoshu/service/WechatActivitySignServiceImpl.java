package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivitySignMapper;
import com.xiaoshu.entity.WechatActivitySign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 微信活动报名表
* @author: XGB
* @date: 2018-01-09 9:57
 */
@Service("wechatActivitySignService")
public class WechatActivitySignServiceImpl implements WechatActivitySignService {

	@Resource private WechatActivitySignMapper mapper;
		/**
		 * 按活动id查询全部属性
		 * @return List
		 * @author: XGB
		 * @date: 2018-02-09 9:54
		 */
		public List<WechatActivitySign> getAllSignByActivityId(String wechatActivityId,int draw){
			return mapper.getAllSignByActivityId(wechatActivityId,draw);
		}

		/**
		 * 删除
		 * @param id 主键ID
		 * @return int 修改行数
		 * @author XGB
		 * @date  2018-02-06 11:37
		 */
		public int deleteByPrimaryKey(String id){
			return mapper.deleteByPrimaryKey(id);
		}

		/**
		 * 新增
		 * @param record 实体类
		 * @return int 新增个数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int saveRecord(WechatActivitySign record){
			return mapper.saveRecord(record);
		}

		/**
		 * 新增(只写入不为NULL的字段)
		 * @param record 实体类
		 * @return int 新增个数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int saveSelective(WechatActivitySign record){
			return mapper.saveSelective(record);
		}

		/**
		 * 通过主键ID查询
		 * @param id 实体类
		 * @return WechatActivitySign 实体类
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public WechatActivitySign getByPrimaryKey(String id){
			return mapper.getByPrimaryKey(id);
		}

		/**
		 * 更新数据(只更新不为NULL的字段)
		 * @param record 实体类
		 * @return int 更新生效行数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int updateByPrimaryKeySelective(WechatActivitySign record){
			return mapper.updateByPrimaryKeySelective(record);
		}

		/**
		 * 更新数据
		 * @param record 实体类
		 * @return int 更新生效行数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int updateByPrimaryKey(WechatActivitySign record){
			return mapper.updateByPrimaryKey(record);
		}

		/**
		 * 更新数据(更新中奖状态)
		 * @param id
		 * @param draw
		 * @return int 更新生效行数
		 * @author: XGB
		 * @date: 2018-02-25 10:43
		 */
		public int updateByPrimaryKeyDraw(String id,int draw){
			return mapper.updateByPrimaryKeyDraw(id,draw);
		}

		/**
		 * 查询用户是否报名
		 * @param wechatActivityId 活动id
		 * @param userId 用户id
		 * @return 返回查询的统计数
		 */
		public int selectUserExitSign(String wechatActivityId,String userId){
			return mapper.selectUserExitSign(wechatActivityId,userId);
		}

		/**
		 * 分页查询
		 * @param search 查询条件
		 * @param startRow 分页开始
		 * @param pageSize 每页数
		 * @param wechatActivityId 活动id
		 * @param draw 抽奖
		 * @return List
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public List<WechatActivitySign> selectByPage(String search ,int startRow,int pageSize,String wechatActivityId,int draw){
			return mapper.selectByPage(search ,startRow,pageSize,wechatActivityId,draw);
		}

		/**
		 * 查询总条数
		 * @param search 查询条件
		 * @param startRow 分页开始
		 * @param pageSize 每页数
		 * @param wechatActivityId 活动id
		 * @param draw 抽奖
		 * @return int 总条数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int selectCount(String search ,int startRow,int pageSize,String wechatActivityId,int draw){
			return mapper.selectCount(search ,startRow,pageSize,wechatActivityId,draw);
		}


		public WechatActivitySign getByUserAndActivityId(String wechatActivityId,String userId){
			return mapper.getByUserAndActivityId(wechatActivityId,userId);
		}
}
