package com.xiaoshu.service;

import com.xiaoshu.entity.Seller;

import java.util.List;

public interface SellerService {

	/**
	 * 添加商家
	 */
	int saveSellerService(Seller seller) throws Exception;
	
	/**
	 * 更新商家信息
	 * @param seller
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	int updateSellerService(Seller seller) throws Exception;
	
	/**
	 * 删除商家
	 * @param id
	 */
	int deleteSellerService(int id) throws Exception;
	
	/**
	 * 按照openid查询商家
	 * @param openid 
	 * @return
	 */
	Seller findSellerByOpenidService(String openid) throws Exception;
	/**
	 * 按照id查询商家
	 * @param openid 
	 * @return
	 */
	Seller findSellerByIdService(Integer id) throws Exception;
	
	
	/**
	 * 商家登陆
	 * @param loginName 登陆名
	 * @param password 登陆密码
	 * @return
	 */
	Seller findLoginSellerService(String loginName,String password) throws Exception;
	
	/**
	 * 按照loginName、email、phone、qq模糊分页查询商家列表
	 * @param index
	 * @param pageSize
	 * @param key
	 * @return 返回商家集合
	 * @throws Exception 抛出异常
	 */
	List<Seller> findAllSellerService(int index, int pageSize, String key) throws Exception;
	
	/**
	 * 按照key统计商家个数
	 * @return
	 * @throws Exception
	 */
	int totalSellerByKeyService(String key) throws Exception;
	
	
	/**
	 * 按查sellerName询询商家
	 * @return  返回被查询的商家
	 * @throws Exception
	 */
	Seller findSellerByLoginNameService(String sellerName) throws Exception;

	/**
	 * 统计总记录数
	 */
	int totalSellerService() throws Exception;
}
