package com.xiaoshu.service;

import com.xiaoshu.dao.FileResourceMapper;
import com.xiaoshu.entity.FileResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("fileResourceService")
public class FileResourceServiceImpl implements FileResourceService{
	
	@Resource
	private FileResourceMapper mapper;

	/** save one */
	@Override
	public Integer save(FileResource bean) throws Exception {
		return mapper.save(bean);
	}

	/** update all */
	@Override
	public Integer updateAll(FileResource bean) throws Exception {
		return mapper.updateAll(bean);
	}

	/** delete ById */
	@Override
	public Integer deleteById( String id) throws Exception {
		return mapper.deleteById(id);
	}

	/** delete ByCidAndClass */
	@Override
	public Integer deleteByCidAndClass(Integer citeId, String citeClass) throws Exception {
		return mapper.deleteByCidAndClass(citeId ,citeClass);
	}

	/** 按照 CITE_ID、CITE_CLASS 查询集合 */
	@Override
	public List<FileResource> getlistByCidAndClass( Integer citeId, String citeClass) throws Exception {
		return mapper.getlistByCidAndClass(citeId,citeClass);
	}

	/** 按照 CITE_ID、CITE_CLASS 查询对象 */
	@Override
	public FileResource getByCidAndClass(Integer citeId, String citeClass) throws Exception {
		return mapper.getByCidAndClass(citeId,citeClass);
	}

	/** select ByID */
	@Override
	public FileResource getById( String id) throws Exception {
		return mapper.getById(id);
	}

}
