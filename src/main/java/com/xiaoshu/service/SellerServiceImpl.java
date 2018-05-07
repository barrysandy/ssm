package com.xiaoshu.service;

import java.util.List;

import javax.annotation.Resource;

import com.xiaoshu.dao.SellerMapper;
import com.xiaoshu.entity.Seller;
import org.springframework.stereotype.Service;

@Service("sellerService")
public class SellerServiceImpl implements SellerService{
	
	@Resource
	private SellerMapper mapper;

	@Override
	public int saveSellerService(Seller seller) throws Exception {
		return mapper.saveSellerDao(seller);
	}

	@Override
	public int updateSellerService(Seller seller) throws Exception {
		return mapper.updateSellerDao(seller);
	}

	@Override
	public int deleteSellerService(int id) throws Exception {
		return mapper.deleteSellerDao(id);
	}

	@Override
	public Seller findSellerByOpenidService(String openid) throws Exception {
		return mapper.findSellerByOpenidDao(openid);
	}

	@Override
	public Seller findLoginSellerService(String loginName, String password) throws Exception {
		return mapper.findLoginSellerDao(loginName, password);
	}

	@Override
	public List<Seller> findAllSellerService(int index, int pageSize, String key) throws Exception {
		return mapper.findAllSellerDao(index, pageSize, key);
	}

	@Override
	public int totalSellerByKeyService(String key) throws Exception {
		return mapper.totalSellerByKeyDao(key);
	}

	@Override
	public Seller findSellerByLoginNameService(String sellerName) throws Exception {
		return mapper.findSellerByLoginNameDao(sellerName);
	}

	@Override
	public int totalSellerService() throws Exception {
		return mapper.totalSellerDao();
	}

	@Override
	public Seller findSellerByIdService(Integer id) throws Exception {
		return mapper.findSellerByIdDao(id);
	}


}
