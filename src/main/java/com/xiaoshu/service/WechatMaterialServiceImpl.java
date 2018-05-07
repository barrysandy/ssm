package com.xiaoshu.service;

import com.xiaoshu.api.Api;
import com.xiaoshu.dao.WechatMaterialMapper;
import com.xiaoshu.entity.WechatMaterial;
import com.xiaoshu.tools.ToolsHttpRequest;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号菜单配置表
* @author: Kun
* @date: 2018-01-09 10:53
 */
@Service("wechatMaterialService")
public class WechatMaterialServiceImpl implements WechatMaterialService {

	@Resource private WechatMaterialMapper wechatMaterialMapper;

	/**
	 * 分页查询
	 */
	@Override
	public List<WechatMaterial> selectByPage(Map map) {
		return wechatMaterialMapper.selectByPage(map);
	}

	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return wechatMaterialMapper.selectCount(record);
	}

	/**
	 * 主键查询
	 */
	@Override
	public WechatMaterial selectByPrimaryKey(String id) {
		return wechatMaterialMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(WechatMaterial bean) {
		return wechatMaterialMapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 新增
	 */
	@Override
	public int insert(WechatMaterial bean) {
		return wechatMaterialMapper.insertSelective(bean);
	}

	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		WechatMaterial bean = wechatMaterialMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return wechatMaterialMapper.updateByPrimaryKey(bean);
	}


	/**
	 * 保存一个material对象
	 * @param material 对象
	 * @author XGB
	 * @date  2018-01-22 17:11
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Override
	public int insertWMmater(WechatMaterial material) throws Exception{
		return wechatMaterialMapper.insertWMmater(material);
	}


	/**
	 * 按id更新material对象
	 * @param material 对象
	 * @author XGB
	 * @date  2018-01-22 17:11
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Override
	public int updateWMmater(WechatMaterial material) throws Exception{
		return wechatMaterialMapper.updateWMmater(material);
	}


	/**
	 * 按material id删除指定material数据
	 * @param id material在数据库的id
	 * @author XGB
	 * @date  2018-01-22 17:12
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Override
	public int deleteWMmater(int id) throws Exception{
		return wechatMaterialMapper.deleteWMmater(id);
	}

	/**
	 * 按material media_id删除指定material数据
	 * @param material_id
	 * @author XGB
	 * @date  2018-01-22 17:16
	 * @return 返回影响数据库的记录条数
	 * @throws Exception 抛出异常
	 */
	@Override
	public int deleteWMmaterByMedia_Id(String material_id) throws Exception{
		return wechatMaterialMapper.deleteWMmaterByMedia_Id(material_id);
	}

	/**
	 * 按素材id查询素材
	 * @param id 素材id
	 * @author XGB
	 * @date  2018-01-22 17:19
	 * @return 返回null 或 者被查询的素材对象
	 * @throws Exception 抛出异常
	 */
	@Override
	public WechatMaterial selectWMmaterById(int id) throws Exception{
		return wechatMaterialMapper.selectWMmaterById(id);
	}


	/**
	 * 按素材material_id查询素材
	 * @param material_id 素材material_id
	 * @param parentMenuId   素材parentMenuId
	 * @author XGB
	 * @date  2018-02-2 10:52
	 * @return 返回null 或 者被查询的素材对象
	 * @throws Exception 抛出异常
	 */
	@Override
	public WechatMaterial getWMmaterMaterialIdAndParentMenuId(String material_id,String parentMenuId){
		WechatMaterial bean = wechatMaterialMapper.getWMmaterMaterialIdAndParentMenuId(material_id,parentMenuId);
		if(bean != null){
			if(bean.getUrl() != null){
				if(!"".equals(bean.getUrl())){
					//替换素材的在文件服务器上的url
					String param = "material_id=" + bean.getUrl();
					String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
					if(json != null){
						if(!"".equals(json)){
							JSONObject jsonObject = JSONObject.fromObject(json);
							String image = jsonObject.getString("url");
							bean.setUrl(image);
						}else{
							bean.setUrl("无效的material_id，该资源已经被删除了。");
						}
					}else{
						bean.setUrl("无效的material_id，该资源已经被删除了。");
					}
				}
			}
		}
		bean.setMaterial_id(material_id);
		return bean;
	}

}
