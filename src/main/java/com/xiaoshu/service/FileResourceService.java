package com.xiaoshu.service;


import com.xiaoshu.entity.FileResource;

import java.util.List;

/** 标准版 */
public  interface FileResourceService {

	/** save one */
	Integer save(FileResource bean) throws Exception;

	/** update all */
	Integer updateAll(FileResource bean) throws Exception;

	/** delete ById */
	Integer deleteById( String id) throws Exception;

	/** delete ByCidAndClass */
	Integer deleteByCidAndClass(Integer citeId, String citeClass) throws Exception;

	/** 按照 CITE_ID、CITE_CLASS 查询集合 */
	List<FileResource> getlistByCidAndClass( Integer citeId, String citeClass) throws Exception;

	/** 按照 CITE_ID、CITE_CLASS 查询对象 */
	FileResource getByCidAndClass(Integer citeId, String citeClass) throws Exception;

	/** select ByID */
	FileResource getById( String id) throws Exception;


}
