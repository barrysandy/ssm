package com.xiaoshu.service;


import javax.annotation.Resource;

import com.xiaoshu.dao.OrderCodesMapper;
import com.xiaoshu.dao.OrderMapper;
import com.xiaoshu.entity.Order;
import com.xiaoshu.entity.OrderCodes;
import com.xiaoshu.enumeration.EnumsString;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsString;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Resource private OrderMapper mapper;
	@Resource private OrderCodesMapper orderCodesMapper;

	/** save one */
	@Override
	public int save(Order bean) throws Exception {
		return mapper.save(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById( Integer id) throws Exception {
		return mapper.deleteById(id);
	}

	/** 更新订单信息 */
	@Override
	public Integer updateAll(Order bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** 更新订单状态 */
	@Override
	public Integer updateOldTypeStateToOrderStateByOrderNo( String orderNo, Integer typeState, Integer oldTypeState,String descM) throws Exception {
		return mapper.updateOldTypeStateToOrderStateByOrderNo(orderNo, typeState, oldTypeState, descM);
	}

	/** 更新订单状态额外状态 */
	@Override
	public Integer updateOldStateToOrderStateByOrderNo( String orderNo, Integer state, Integer oldState) throws Exception {
		return mapper.updateOldStateToOrderStateByOrderNo(orderNo, state, oldState);
	}

	/** 更新订单 groupId */
	@Override
	public Integer updateGroupIdByOrderNo( String orderNo, String groupId ) throws Exception {
		return mapper.updateGroupIdByOrderNo(orderNo, groupId);
	}

	/** 按照 id 查询订单 */
	@Override
	public Order getById( Integer id) throws Exception {
		return mapper.getById(id);
	}

	/** 按照 orderNo 查询订单 */
	@Override
	public Order getByOrderNo( String orderNo ) throws Exception {
		return mapper.getByOrderNo(orderNo);
	}

	/** 按照 GROUP_ID  USER_ID  查询订单 */
	@Override
	public Order getByGroupIdAndUserId(String groupId , String userId) throws Exception {
		return mapper.getByGroupIdAndUserId(groupId ,userId);
	}

	/** 按照 GROUP_ID  查询订单集合 */
	@Override
	public List<Order> getListByGroupId(String groupId ) throws Exception {
		return mapper.getListByGroupId(groupId);
	}


	/** 按照 id 查询 orderNo */
	@Override
	public Integer getIdByOrderNo( String orderNo) throws Exception {
		return mapper.getIdByOrderNo(orderNo);
	}

	/** 按照 orderNo 查询 id  */
	@Override
	public String getOrderNoById( Integer id) throws Exception {
		return mapper.getOrderNoById(id);
	}

	/** 按照 orderNo 查询订单状态（typeState 订单状态 0未付款 1已付款 2已消费  -1查询所有状态） */
	@Override
	public Integer getTypeStateByOrderNo( String orderNo ) throws Exception {
		return mapper.getTypeStateByOrderNo(orderNo);
	}

	/** 查询 orderNo 是否存在 */
	@Override
	public Integer countByOrderNo( String orderNo ) throws Exception {
		return mapper.countByOrderNo(orderNo);
	}


	/** 查询今天的订单数量 */
	@Override
	public Integer countToday( String today ) throws Exception {
		return mapper.countToday(today);
	}

	/** 按商品统计已经购买的订单数量 */
	@Override
	public Integer countByCommdityId(Integer commodityId ) throws Exception{
		return mapper.countByCommdityId(commodityId);
	}

	/** 查询最后一个订单编号 */
	@Override
	public String getLastOrderNo() throws Exception {
		return mapper.getLastOrderNo();
	}

	/**
	 * 查询订单列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询订单列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	@Override
	public List<Order> listByKeyAndTypeStateAndStatusAndUserIdAndSellerId(String date1 ,String date2, Integer index, Integer pageSize, String key,Integer typeState, Integer status, Integer userId, Integer sellerId,Integer commodityId) throws Exception {
		return mapper.listByKeyAndTypeStateAndStatusAndUserIdAndSellerId( date1 , date2, index, pageSize, key, typeState, status, userId, sellerId,commodityId);
	}


	/**
	 * 统计订单
	 * @param key 关键字 orderNno 、 orderName 模糊分页查询订单列表
	 * @param userId 用户的id，查询某个用户的订单列表，启用需要 userId != -1
	 * @param sellerId 商家的id，查询某个商家的订单列表，启用需要 sellerId != -1
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	@Override
	public Integer countByKeyAndTypeStateAndStatusAndUserIdAndSellerId(String date1, String date2, String key,Integer typeState, Integer status, Integer userId, Integer sellerId,Integer commodityId) throws Exception {
		return mapper.countByKeyAndTypeStateAndStatusAndUserIdAndSellerId( date1 , date2, key, typeState, status, userId, sellerId,commodityId);
	}

	/**
	 * 查询订单列表
	 * @param index 分页开始
	 * @param pageSize 分页每页最大数
	 * @param userId 用户的id，查询某个用户的订单列表
	 * @return 返回订单集合
	 * @throws Exception 抛出异常
	 */
	@Override
	public List<Order> listByUserId(Integer index,Integer pageSize,String userId,String userId2,Integer typeState) throws Exception {
		return mapper.listByUserId( index, pageSize, userId,userId2, typeState);
	}

	/**
	 * 统计订单数量
	 * @param userId 用户的id，查询某个用户的订单列表
	 * @return 返回订单数量
	 * @throws Exception 抛出异常
	 */
	@Override
	public Integer countByUserId(String userId,String userId2,Integer typeState) throws Exception {
		return mapper.countByUserId( userId,userId2, typeState);
	}

	/** 绑定订单所属信息 */
	@Override
	public Integer updateBelongBy(String orderNo, String userId) throws Exception {
		return mapper.updateBelongBy(orderNo, userId);
	}

	/** 绑定微信订单号信息 */
	@Override
	public Integer updateTransactionIdByOrderOn(String orderNo,String transactionId) throws Exception {
		return mapper.updateTransactionIdByOrderOn(orderNo, transactionId);
	}

	/** 更新订单电话，联系人，和用户时间信息 */
	@Override
	public Integer updateContactsMsg(String orderNo, String userName, String userPhone, String userUseTime) throws Exception {
		return mapper.updateContactsMsg(orderNo, userName, userPhone, userUseTime);
	}

	/** 更新订单的支付类型 */
	@Override
	public Integer updatePayTypeByOrderOn(String orderNo,String payType) throws Exception {
		return mapper.updatePayTypeByOrderOn(orderNo, payType);
	}

	/** 按照订单描述和其状态为支付完成的订单列表 */
	@Override
	public List<Order> listBydescM(Integer index, Integer pageSize, String descM) throws Exception {
		return mapper.listBydescM(index, pageSize, descM);
	}

	/** 统计 按照订单描述和其状态为支付完成的订单列表 */
	@Override
	public Integer countBydescM(String descM) throws Exception {
		return mapper.countBydescM( descM);
	}

	/** 按照useCode查询所有核销码数量 */
	@Override
	public Integer countByUseCode(String useCode) throws Exception {
		return mapper.countByUseCode( useCode);
	}

	/** 查询所有已经付款的订单，用于群发短信 */
	@Override
	public List<Order> listByCommodityIdAndHasPay(Integer commodityId, String groupId) throws Exception {
		return mapper.listByCommodityIdAndHasPay( commodityId, groupId);
	}



	/** 统计商品订单的成人数量 */
	@Override
	public Integer countByNUMBER(Integer commodityId) throws Exception {
		return mapper.countByNUMBER( commodityId);
	}

	/** 统计商品订单的儿童数量 */
	@Override
	public Integer countByNUMBER2(Integer commodityId) throws Exception {
		return mapper.countByNUMBER2( commodityId);
	}


	/** 生成订单编号*/
	@Override
	public String getOrderNumber() throws Exception {
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
		try{
			/** 同步锁 */
			synchronized(this){
				//订单格式DAXI+年月日+四位编号
				StringBuffer sb = new StringBuffer();
				sb = ToolsString.getOrderNo(sb);
				int total = mapper.countToday(ToolsDate.getTimeZero(ToolsDate.simpleSecond));
				if(total == 0){
					sb.append("0000");
				}else{
					//查询最后一个订单编号
					String lastOrderNo = mapper.getLastOrderNo();
					if(lastOrderNo != null && !"".equals(lastOrderNo)){
						lastOrderNo = lastOrderNo.substring(12);
						int lastNo = Integer.parseInt(lastOrderNo) + 1;
						//为订单后四位补位“0”
						if(lastNo<10){
							sb.append("000");
						}
						else if(lastNo<100 && lastNo>=10){
							sb.append("00");
						}
						else if(lastNo<1000 && lastNo>=100){
							sb.append("0");
						}
						sb.append(lastNo);
					}
				}
				String orderNo = EnumsString.ORDER_PREFIX + sb.toString();
				System.out.println("------------ [LOG["+nowTime+"] interfaceCreateOrderNumber orderNo : " + orderNo + " ------------");
				return orderNo;
			}
		}catch (Exception e){
			e.printStackTrace();
			return "exception";
		}
	}

	/** 生成订单核销码编号*/
	@Override
	public String getOrderCode() throws Exception {
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
		/** 生成的核销码 */
		String useCode = null;
		try{
			/** 同步锁 */
			synchronized(this){
				Integer total = orderCodesMapper.countAll();
				//核销码超过的部分补位
				if(total > 999999 && total <= 9999999){//七位 需要补位
					useCode = ToolsString.generateRandNumber(7);
				}
				else if(total > 9999999  && total <= 99999999){//八位 需要补位
					useCode = ToolsString.generateRandNumber(8);
				}
				else if(total > 99999999 && total <= 999999999){//九位 需要补位
					useCode = ToolsString.generateRandNumber(9);
				}
				else if(total < 100000){
					//六位随机码
					useCode = ToolsString.generateRandNumber(6);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return "exception";
		}
		System.out.println("------------ [LOG["+nowTime+"] interfaceCreateOrderCode useCode : " + useCode + " ------------");
		return useCode;
	}


}
