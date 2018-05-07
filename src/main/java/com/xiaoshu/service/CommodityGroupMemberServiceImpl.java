package com.xiaoshu.service;

import com.xiaoshu.dao.CommodityGroupMemberMapper;
import com.xiaoshu.entity.CommodityGroupMember;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commodityGroupMemberService")
public class CommodityGroupMemberServiceImpl implements CommodityGroupMemberService{
	
	@Resource
	private CommodityGroupMemberMapper mapper;



	/** save one */
	@Override
	public Integer save(CommodityGroupMember bean) throws Exception {
		return mapper.save(bean);
	}

	/** update all */
	@Override
	public Integer updateAll(CommodityGroupMember bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** get ById */
	@Override
	public CommodityGroupMember getById(String id) throws Exception {
		return mapper.getById(id);
	}


	/** update Re_Set GROUP_ID LENDER  */
	@Override
	public Integer updateReSetLenderById(String groupId) throws Exception {
		return mapper.updateReSetLenderById(groupId);
	}

	/** update LENDER */
	@Override
	public Integer updateLenderById(Integer lender, String id) throws Exception {
		return mapper.updateLenderById(lender,id);
	}

	/** update Status */
	@Override
	public Integer updateStatusById(Integer status,String id) throws Exception {
		return mapper.updateStatusById(status ,id);
	}


	/** LIST By GroupIdAndStatus */
	@Override
	public List<CommodityGroupMember> getListByGroupIdAndStatus(String groupId,Integer status) throws Exception {
		return mapper.getListByGroupIdAndStatus(groupId , status);
	}

	/** count ByStatus  */
	@Override
	public Integer countByStatus(String groupId,Integer status) throws Exception {
		return mapper.countByStatus(groupId,status);
	}

	/** get GROUP_ID */
	@Override
	public String getGroupIdById(Integer commodityId,String userId) throws Exception {
		return mapper.getGroupIdById(commodityId,userId);
	}

	/** get CommodityGroupMember ByUserIdAndCId */
	@Override
	public CommodityGroupMember getByUserIdAndCId( Integer commodityId, String userId) throws Exception {
		return mapper.getByUserIdAndCId(commodityId,userId);
	}

}
