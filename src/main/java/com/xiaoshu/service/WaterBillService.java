package com.xiaoshu.service;

import com.xiaoshu.entity.WaterBill;

import java.util.List;



public interface WaterBillService {


	/**
	 * 新增
	 * @param waterBill
	 * @return int 返回影响数据库的行数
	 * @author XGB
	 * @date  2018-03-15 09:52
	 */
	int save(WaterBill waterBill) throws Exception;

	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 返回影响数据库的行数
	 * @author XGB
	 * @date  2018-03-15 09:52
	 */
	int deleteById(Integer id) throws Exception;

	/**
	 * 按照id查询一条记录
	 * @param id 主键ID
	 * @return WaterBill对象
	 * @author XGB
	 * @date  2018-03-15 09:52
	 */
	WaterBill getById(Integer id) throws Exception;

	/**
	 * 查询订单号是否存在
	 * @param order_no 订单号
	 * @return int 存在的数据条数
	 * @author XGB
	 * @date  2018-03-15 09:53
	 */
	int existByOrderNo(String order_no ) throws Exception;


	/**
	 * 按照 orderNo 查询一条记录
	 * @param orderNo
	 * @return WaterBill对象
	 * @author XGB
	 * @date  2018-03-15 09:52
	 */
	WaterBill getByOrderNo(String orderNo) throws Exception;

	/**
	 * 查询流水集合
	 * @param index 查询开始
	 * @param pageSize 查询数据大小
	 * @param date1 查询开始时间
	 * @param date2 查询结束时间
	 * @param key 查询条件（与订单号有关）
	 * @return List集合
	 * @author XGB
	 * @date  2018-03-15 09:55
	 */
	List<WaterBill> getListByKey(int index,int pageSize,String date1,String date2,String key,int status) throws Exception;

	/**
	 * 查询总记录数
	 * @param date1 查询开始时间
	 * @param date2 查询结束时间
	 * @param key 查询条件（与订单号有关）
	 * @return int 查询的记录数
	 * @author XGB
	 * @date  2018-03-15 09:56
	 */
	int getTotalListByKey(String date1,String date2,String key,int status) throws Exception;

	/**
	 * 更新状态（删除或者恢复）
	 * @param id 订单ID
	 * @param status 订单状态
	 * @return int 影响数据库的记录数
	 * @author XGB
	 * @date  2018-03-15 09:56
	 */
	int updateStateById(Integer id,Integer status) throws Exception;

	/**
	 * 更新全部
	 * @param waterBill
	 * @return int 影响数据库的记录数
	 * @author XGB
	 * @date  2018-03-27 14:15
	 */
	int updateAllById(WaterBill waterBill) throws Exception;
}
