package com.xiaoshu.service;

import com.xiaoshu.dao.WechatActivitySignSetMapper;
import com.xiaoshu.entity.WechatActivitySignSet;
import com.xiaoshu.tools.ToolsUnicode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 微信活动报名表
* @author: XGB
* @date: 2018-01-09 9:57
 */
@Service("wechatActivitySignSetService")
public class WechatActivitySignSetServiceImpl implements WechatActivitySignSetService {

	@Resource private WechatActivitySignSetMapper mapper;
		/**
		 * 按活动id查询全部属性
		 * @param wechatActivityId 活动id
		 * @param setType 是配置还是值。  0 配置，1值。(SET_TYPE)
		 * @return List
		 * @author: XGB
		 * @date: 2018-02-09 9:51
		 */
		public List<WechatActivitySignSet> getAllByActivityId(String wechatActivityId,int setType){
			return mapper.getAllByActivityId(wechatActivityId,setType);
		}

		/**
		 * 按活动报名id查询全部属性
		 * @param wechatActivitySignId 活动报名id
		 * @return List
		 * @author: XGB
		 * @date: 2018-02-24 15:34
		 */
		public List<WechatActivitySignSet> getAllValuesByActivitySignId(String wechatActivitySignId){
			List<WechatActivitySignSet> list = mapper.getAllValuesByActivitySignId(wechatActivitySignId);
			if(list != null){
				Iterator<WechatActivitySignSet> iterator = list.iterator();
				while(iterator.hasNext()){
					WechatActivitySignSet bean = iterator.next();
					if(bean.getValuese() != null){
						if(!"".equals(bean.getValuese())){
							if("nickname".equals(bean.getName())){
								bean.setValuese(ToolsUnicode.unicode2String(bean.getValuese()));
							}
						}
					}

				}
			}
			return list;
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
         * 删除
         * @param wechatActivityId 活动id
         * @return int 修改行数
         * @author XGB
         * @date  2018-02-10 9:50
         */
         public int deleteByWechatActivityId(String wechatActivityId,int setType){
             return mapper.deleteByWechatActivityId(wechatActivityId,setType);
         }

		/**
		 * 删除
		 * @param wechatActivitySignId 活动id
		 * @param setType 属性类型
		 * @return int 修改行数
		 * @author XGB
		 * @date  2018-02-25 8:50
		 */
		public int deleteByWechatActivitySignId(String wechatActivitySignId,int setType){
			return mapper.deleteByWechatActivitySignId(wechatActivitySignId,setType);
		}

		/**
		 * 新增
		 * @param record 实体类
		 * @return int 新增个数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int saveRecord(WechatActivitySignSet record){
			if(record != null){
				if(record.getValuese() != null && record.getName() != null){
					if("nickname".equals(record.getName())){
						record.setValuese(ToolsUnicode.string2Unicode(record.getValuese()));
					}
				}
			}
			return mapper.saveRecord(record);
		}

		/**
		 * 新增(只写入不为NULL的字段)
		 * @param record 实体类
		 * @return int 新增个数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int saveSelective(WechatActivitySignSet record){
			return mapper.saveSelective(record);
		}

		/**
		 * 通过主键ID查询
		 * @param id 实体类
		 * @return WechatActivitySignSet 实体类
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public WechatActivitySignSet getByPrimaryKey(String id){
			return mapper.getByPrimaryKey(id);
		}

		/**
		 * 更新数据(只更新不为NULL的字段)
		 * @param record 实体类
		 * @return int 更新生效行数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int updateByPrimaryKeySelective(WechatActivitySignSet record){
			return mapper.updateByPrimaryKeySelective(record);
		}

		/**
		 * 更新数据
		 * @param record 实体类
		 * @return int 更新生效行数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int updateByPrimaryKey(WechatActivitySignSet record){
			return mapper.updateByPrimaryKey(record);
		}

		/**
		 * 分页查询
		 * @param map 参数MAP
		 * @return List
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public List<WechatActivitySignSet> selectByPage(Map map){
			return mapper.selectByPage(map);
		}

		/**
		 * 查询总条数
		 * @return int 总条数
		 * @author: XGB
		 * @date: 2018-02-06 11:37
		 */
		public int selectCount(Map record){
			return mapper.selectCount(record);
		}


		/**
		 * 查询用户是否报名
		 * @param wechatActivityId 活动id
		 * @param signSetsValues 验证重复的值
		 * @return 返回查询的统计数
		 */
		public int selectUserExitSignBySignSetValue(String wechatActivityId, String signSetsValues){
			return  mapper.selectUserExitSignBySignSetValue(wechatActivityId,signSetsValues);
		}


		public String getValueByAIDAndUID( String wechatActivitySignId , String wechatActivityId , String names ) throws Exception{
			return  mapper.getValueByAIDAndUID(wechatActivitySignId,wechatActivityId,names);
		}
}
