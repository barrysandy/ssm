package com.xiaoshu.service;


import com.xiaoshu.entity.CommodityGroup;
import com.xiaoshu.vo.MyGroup;

import java.util.List;

/** 标准版 */
public  interface CommodityGroupService {


	/** save one */
	Integer save(CommodityGroup bean) throws Exception;

	/** update all */
	Integer updateAll(CommodityGroup bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** get ById */
	CommodityGroup getById(String id) throws Exception;


	/** update MaxPerson */
	Integer updateMaxPersonByCommdityId(Integer maxPerson,String commodityId) throws Exception;

	/** update TotalPerson */
	Integer updateTotalPersonById(Integer totalPerson,Integer oldTotalPerson,String id) throws Exception;

	/** update Status */
	Integer updateStatusById(Integer status,String id) throws Exception;


	/** LIST ByCommodityId */
	List<CommodityGroup> getListByCIDAndStatus(Integer index, Integer pageSize, Integer commodityId, String status) throws Exception;

	/** count ByStatus  */
	Integer countByStatus(Integer commodityId,Integer status) throws Exception;

	/** LIST MyGroup */
	List<MyGroup> getListMyGroupByUserId(Integer index, Integer pageSize, String userId) throws Exception;

	/** countMyGroup */
	Integer countMyGroupByUserId(String userId) throws Exception;


	/** LIST BY STATUS = 0  AND nowTime > GROUP_TIME */
	List<CommodityGroup> getListByStatusAndAfterTime(Integer index,Integer pageSize,String groupTime,Integer status) throws Exception;

	/** 统计最新活动列表 */
	Integer countByStatusAndAfterTime(String groupTime,Integer status) throws Exception;

	String getGroupCodeById(String id) throws Exception;

	String geteIdByGroupCod(String groupCode) throws Exception;
}
