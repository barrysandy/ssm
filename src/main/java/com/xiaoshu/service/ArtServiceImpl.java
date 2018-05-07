package com.xiaoshu.service;

import java.util.*;
import javax.annotation.Resource;

import com.xiaoshu.api.Api;
import com.xiaoshu.api.Set;
import com.xiaoshu.dao.ArtMapper;
import com.xiaoshu.entity.Art;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsHttpRequest;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 文章
* @author: Kun
* @date: 2018-01-09 10:49
 */
@Service("artService")
public class ArtServiceImpl implements ArtService {

	@Resource
	private ArtMapper artMapper;
	@Resource
	private MenuService menuService;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<Art> selectByPage(Map map) {
		return artMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return artMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public Art selectByPrimaryKey(String id,int replaceUrl) {
		Art art = artMapper.selectByPrimaryKey(id);
		if( replaceUrl != 0){
			if(art.getArtImg() != null){
				if (!"".equals(art.getArtImg())){
					String param = "material_id=" + art.getArtImg();
					String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
					if(json != null){
						if(!"".equals(json)){
							JSONObject jsonObject = JSONObject.fromObject(json);
							String image = jsonObject.getString("url");
							art.setArtImg(image);

						}else{
							art.setArtImg(Set.SYSTEM_URL + "/resources/img/fileNotFind.jpg");
						}
					}else{
						art.setArtImg(Set.SYSTEM_URL + "/resources/img/fileNotFind.jpg");
					}
				}
			}
		}
		return art;
	}

	/**
	 * 更新
	 */
	@Override
	public int update(Art bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return artMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Art bean) {
		String menuId = bean.getMenuId();
		String parentMenuId = menuService.getParentIdByMenuId(menuId);
		bean.setParentMenuId(parentMenuId);
		return artMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		Art bean = artMapper.selectByPrimaryKey(id);
		bean.setStatus("-1");
		return artMapper.updateByPrimaryKey(bean);
	}

	/**
	 * 分期查询文章
	 */

	/**
	 * 分期查询文章
	 * @param menuId 文章的所属公众号menuId
	 * @param current 查询的文章是第多少期
	 * @return String (JSON)
	 * @author zhou.zhengkun
	 * @date 2018/01/17 0017 14:26
	 */
	@Override
	public List<Art> selectByPeriod(String menuId,Integer current){
		return artMapper.selectByPeriod(menuId,current);
	}

	/**
	 * 查询本期文章
	 */
	@Override
	public String getCurrentPeriodArt(String menuId) {
		List<Art> currentArtList = artMapper.getCurrentPeriodArt(menuId);
		/** 用material_id替换图片url */
		if(currentArtList != null){
			Iterator<Art> iterator = currentArtList.iterator();
			while (iterator.hasNext()){
				Art art = iterator.next();
				if(art != null){
					if(art.getArtImg() != null){
						if(!"".equals(art.getArtImg())){
							//替换素材的在文件服务器上的url
							String param = "material_id=" + art.getArtImg();
							String json = ToolsHttpRequest.sendGet(Api.GET_FILE_URL, param);
							if(json != null){
								if(!"".equals(json)){
									JSONObject jsonObject = JSONObject.fromObject(json);
									String image = jsonObject.getString("url");
									art.setArtImg(image);
								}else{
									art.setArtImg("无效的material_id，该资源已经被删除了。");
								}
							}else{
								art.setArtImg("无效的material_id，该资源已经被删除了。");
							}
						}
					}
				}
			}
		}
		return JSONUtils.toJSONString(currentArtList);
	}

}
