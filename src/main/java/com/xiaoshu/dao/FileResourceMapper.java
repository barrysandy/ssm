package com.xiaoshu.dao;


import com.xiaoshu.entity.FileResource;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 标准版 */
public interface FileResourceMapper {

	/**
	 * ID, URL, TYPESE, MATERIAL_ID, CITE_ID, CITE_CLASS ,CREATE_TIME ,UPDATE_TIME ,DESC_M
	 *
	 * id, url, typese, materialId, citeId, citeClass ,createTime , updateTime , descM
	 */

	/** save one */
	@Insert("INSERT INTO file_resource (ID,URL,TYPESE, MATERIAL_ID,CITE_ID, CITE_CLASS, CREATE_TIME, UPDATE_TIME, DESC_M) " +
			"VALUES(#{id},#{url},#{typese},#{materialId},#{citeId},#{citeClass},#{createTime},#{updateTime},#{descM} )")
	Integer save(FileResource bean) throws Exception;

	/** update all */
	@Update("UPDATE file_resource SET URL=#{url},TYPESE=#{typese},MATERIAL_ID=#{materialId},CITE_ID=#{citeId},CITE_CLASS=#{citeClass},CREATE_TIME=#{createTime},UPDATE_TIME=#{updateTime},DESC_M=#{descM} WHERE ID=#{id} ")
	Integer updateAll(FileResource bean) throws Exception;

	/** delete ById */
	@Delete("DELETE FROM file_resource WHERE ID=#{id}")
	Integer deleteById(@Param("id") String id) throws Exception;

	/** delete ByCidAndClass */
	@Delete("DELETE FROM file_resource WHERE CITE_ID = #{citeId} AND CITE_CLASS = #{citeClass}")
	Integer deleteByCidAndClass(@Param("citeId") Integer citeId,@Param("citeClass") String citeClass) throws Exception;


	/** 按照 CITE_ID、CITE_CLASS 查询集合 */
	@Select("SELECT * FROM file_resource WHERE CITE_ID = #{citeId} AND CITE_CLASS = #{citeClass} ORDER BY CREATE_TIME DESC ")
	List<FileResource> getlistByCidAndClass(@Param("citeId") Integer citeId,@Param("citeClass") String citeClass) throws Exception;

	/** 按照 CITE_ID、CITE_CLASS 查询对象 */
	@Select("SELECT * FROM file_resource WHERE CITE_ID = #{citeId} AND CITE_CLASS = #{citeClass} ORDER BY CREATE_TIME DESC Limit 0,1 ")
	FileResource getByCidAndClass(@Param("citeId") Integer citeId,@Param("citeClass") String citeClass) throws Exception;

	/** select ByID */
	@Select("SELECT * FROM file_resource WHERE ID = #{id}")
	FileResource getById(@Param("id") String id) throws Exception;


}
