package com.xiaoshu.service;

import com.xiaoshu.dao.CommodityGroupMapper;
import com.xiaoshu.entity.CommodityGroup;
import com.xiaoshu.vo.MyGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commodityGroupService")
public class CommodityGroupServiceImpl implements CommodityGroupService{
	
	@Resource
	private CommodityGroupMapper mapper;

	/** save one */
	@Override
	public Integer save(CommodityGroup bean) throws Exception {
		return  mapper.save(bean);
	}

	/** update all */
	@Override
	public Integer updateAll(CommodityGroup bean) throws Exception {
		return  mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return  mapper.deleteById(id);
	}

	/** get ById */
	@Override
	public CommodityGroup getById(String id) throws Exception {
		return  mapper.getById(id);
	}


	/** update MaxPerson */
	@Override
	public Integer updateMaxPersonByCommdityId(Integer maxPerson,String commodityId) throws Exception {
		return  mapper.updateMaxPersonByCommdityId(maxPerson,commodityId);
	}

	/** update TotalPerson */
	@Override
	public Integer updateTotalPersonById(Integer totalPerson,Integer oldTotalPerson,String id) throws Exception {
		return  mapper.updateTotalPersonById(totalPerson,oldTotalPerson,id);
	}

	/** update Status */
	@Override
	public Integer updateStatusById(Integer status,String id) throws Exception {
		return  mapper.updateStatusById(status,id);
	}


	/** LIST ByCommodityId */
	@Override
	public List<CommodityGroup> getListByCIDAndStatus(Integer index, Integer pageSize, Integer commodityId, String status) throws Exception {
		return  mapper.getListByCIDAndStatus(index, pageSize, commodityId, status);
	}

	/** count ByStatus  */
	@Override
	public Integer countByStatus(Integer commodityId,Integer status) throws Exception {
		return  mapper.countByStatus(commodityId,status);
	}

	/** LIST MyGroup */
	@Override
	public List<MyGroup> getListMyGroupByUserId(Integer index, Integer pageSize, String userId) throws Exception {
		return  mapper.getListMyGroupByUserId(index,pageSize,userId);
	}

	/** countMyGroup */
	@Override
	public Integer countMyGroupByUserId(String userId) throws Exception {
		return  mapper.countMyGroupByUserId(userId);
	}

	/** LIST BY STATUS = 0  AND nowTime > GROUP_TIME */
	@Override
	public List<CommodityGroup> getListByStatusAndAfterTime(Integer index,Integer pageSize,String groupTime,Integer status) throws Exception {
		return  mapper.getListByStatusAndAfterTime(index, pageSize, groupTime, status);
	}

	/** 统计最新活动列表 */
	@Override
	public Integer countByStatusAndAfterTime(String groupTime,Integer status) throws Exception {
		return  mapper.countByStatusAndAfterTime(groupTime , status);
	}

	@Override
	public String getGroupCodeById(String id) throws Exception {
		return  mapper.getGroupCodeById(id);
	}

	@Override
	public String geteIdByGroupCod(String groupCode) throws Exception {
		return  mapper.geteIdByGroupCod(groupCode);
	}

}
