package com.xiaoshu.dao;


import com.xiaoshu.entity.Seller;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SellerMapper {
	/**
	 * 添加商家
	 */
	@Insert("insert into seller(sellerName,loginName,password,email,phone,openid,qq,menuId) values(#{sellerName},#{loginName},#{password},#{email},#{phone},#{openid},#{qq},#{menuId} )")
	int saveSellerDao(Seller seller) throws Exception;
	
	/**
	 * 更新商家信息
	 * @param seller
	 * @return 返回影响数据库的记录条数
	 * @throws Exception
	 */
	@Update("update seller set sellerName=#{sellerName},loginName=#{loginName},password=#{password},email=#{email},phone=#{phone},openid=#{openid},qq=#{qq},menuId=#{menuId} where id=#{id} ")
	int updateSellerDao(Seller seller) throws Exception;
	
	/**
	 * 删除商家
	 * @param id
	 */
	@Delete("delete from seller where id=#{id}")
	int deleteSellerDao(int id) throws Exception;
	
	/**
	 * 按照openid查询商家
	 * @param openid 
	 * @return
	 */
	@Select("select * from seller where openid = #{openid}")
	Seller findSellerByOpenidDao(@Param("openid")String openid) throws Exception;
	
	/**
	 * 按照id查询商家
	 * @param id
	 * @return
	 */
	@Select("select * from seller where id = #{id}")
	Seller findSellerByIdDao(@Param("id")Integer id) throws Exception;
	
	
	/**
	 * 商家登陆
	 * @param loginName 登陆名
	 * @param password 登陆密码
	 * @return
	 */
	@Select("select * from seller where loginName = #{loginName} and password = #{password}")
	Seller findLoginSellerDao(@Param("loginName")String loginName,@Param("password")String password) throws Exception;
	
	/**
	 * 按照loginName、email、phone、qq模糊分页查询商家列表
	 * @param index
	 * @param pageSize
	 * @param key
	 * @return 返回商家集合
	 * @throws Exception 抛出异常
	 */
	//@Select("select * from  admin where loginName LIKE concat(concat('%',#{key}),'%') OR  email LIKE concat(concat('%',#{key}),'%') OR  phone LIKE concat(concat('%',#{key}),'%') OR  phone LIKE concat(concat('%',#{key}),'%') OR  qq LIKE concat(concat('%',#{key}),'%') limit #{index},#{pageSize} ")
	List<Seller> findAllSellerDao(@Param("index") int index, @Param("pageSize") int pageSize, @Param("key") String key) throws Exception;
	
	/**
	 * 按照key统计商家个数
	 * @return
	 * @throws Exception
	 */
	int totalSellerByKeyDao(@Param("key") String key) throws Exception;
	
	
	/**
	 * 按查sellerName询询商家
	 * @return  返回被查询的商家
	 * @throws Exception
	 */
	@Select("select * from seller where sellerName = #{sellerName} ")
	Seller findSellerByLoginNameDao(@Param("sellerName")String sellerName) throws Exception;

	/**
	 * 统计总记录数
	 */
	@Select("select count(*) from seller")
	int totalSellerDao() throws Exception;
	
}
