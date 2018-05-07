package com.xiaoshu.service;


import com.xiaoshu.entity.CommodityGroupMember;

import java.util.List;

/** 标准版 */
public  interface CommodityGroupMemberService {


	/** save one */
	Integer save(CommodityGroupMember bean) throws Exception;

	/** update all */
	Integer updateAll(CommodityGroupMember bean) throws Exception;

	/** delete ById */
	Integer deleteById(String id) throws Exception;

	/** get ById */
	CommodityGroupMember getById(String id) throws Exception;


	/** update Re_Set GROUP_ID LENDER  */
	Integer updateReSetLenderById(String groupId) throws Exception;

	/** update LENDER */
	Integer updateLenderById(Integer lender, String id) throws Exception;

	/** update Status */
	Integer updateStatusById(Integer status,String id) throws Exception;


	/** LIST By GroupIdAndStatus */
	List<CommodityGroupMember> getListByGroupIdAndStatus(String groupId,Integer status) throws Exception;

	/** count ByStatus  */
	Integer countByStatus(String groupId,Integer status) throws Exception;

	/** get GROUP_ID */
	String getGroupIdById(Integer commodityId,String userId) throws Exception;

	/** get CommodityGroupMember ByUserIdAndCId */
	CommodityGroupMember getByUserIdAndCId( Integer commodityId, String userId) throws Exception;

}
