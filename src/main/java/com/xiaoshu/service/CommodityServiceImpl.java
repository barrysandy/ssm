package com.xiaoshu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.xiaoshu.api.Api;
import com.xiaoshu.dao.CommodityMapper;
import com.xiaoshu.dao.FileResourceMapper;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.FileResource;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import org.springframework.stereotype.Service;

@Service("commodityService")
public class CommodityServiceImpl implements CommodityService{
	
	@Resource private CommodityMapper mapper;
	@Resource private FileResourceMapper fileResourceMapper;


	@Override
	public Integer saveCommodityService(Commodity bean) throws Exception {
		bean = updateImageAndDetailsBean(bean,0,mapper,fileResourceMapper);
		return mapper.saveCommodityDao(bean);
	}

	@Override
	public Integer updateCommodityService(Commodity bean) throws Exception {
		bean = updateImageAndDetailsBean(bean,1,mapper,fileResourceMapper);
		return mapper.updateCommodityDao(bean);
	}
	@Override
	public Integer updateVideoService(Commodity bean) throws Exception {
		//旧资源
		fileResourceMapper.deleteByCidAndClass(bean.getId(),"CommodityVideo");

		//视频MaterialId存在，更新资源
		if(bean.getVideoMaterialId() != null){
			if(!"".equals(bean.getVideoMaterialId())){
				String url = ToolsImage.getImageUrlByServer(bean.getVideoMaterialId());
				FileResource fileResource = new FileResource(UUID.randomUUID().toString(), url, "Video", bean.getVideoMaterialId(), bean.getId(), "CommodityVideo", String.valueOf(new Date().getTime()), "", "");
				bean.setVideoResource(fileResource);
				fileResourceMapper.save(fileResource);
			}
		}

		return mapper.updateVideoDao(bean);
	}

	@Override
	public Integer deleteCommodityService(Integer id) throws Exception {
		return mapper.deleteCommodityDao(id);
	}

	@Override
	public Commodity findCommodityByNameService(String commodityName) throws Exception {
		return mapper.findCommodityByNameDao(commodityName);
	}

	@Override
	public List<Commodity> findAllCommodityByKeyService(Integer index, Integer pageSize, String key,Integer seller_id) throws Exception {
		return mapper.findAllCommodityByKeyDao(index, pageSize, key,seller_id);
	}

	@Override
	public Integer totalCommodityByKeyService(String key,Integer seller_id) throws Exception {
		return mapper.totalCommodityByKeyDao(key,seller_id);
	}

	@Override
	public Integer totalCommodityService() throws Exception {
		return mapper.totalCommodityDao();
	}

	@Override
	public Commodity findCommodityByIdService(Integer id) throws Exception {
		Commodity commodity = mapper.findCommodityByIdDao(id);
		if(commodity != null){
			List<FileResource> list = fileResourceMapper.getlistByCidAndClass(id,"Commodity");
			if(list != null){
				commodity.setResource(list);
			}
			FileResource videoResource = fileResourceMapper.getByCidAndClass(id,"CommodityVideo");
			if(videoResource != null){
				commodity.setVideoResource(videoResource);
			}

		}
		return commodity;
	}

	@Override
	public Commodity getDateBeanById(Integer id ) {
		return mapper.getDateBeanById(id);
	}

	@Override
	public Commodity findCommodityByIdPriceStatusOne(Integer id ) throws Exception {
		return mapper.findCommodityByIdPriceStatusOne(id);
	}

	@Override
	public Commodity findFristSellGoodsDao(Integer commodityState) throws Exception {
		return mapper.findFristSellGoodsDao(commodityState);
	}
	
	@Override
	public Integer updateCommodityStateService(Integer id, Integer commodityState) throws Exception {
		//更新上/下架状态
		if(commodityState == 0){
			commodityState = 1;
		}else if(commodityState == 1){
			commodityState = 0;
		}
		return mapper.updateCommodityStateDao(id, commodityState);
	}

	@Override
	public Integer findCommodityStateByIdService(Integer id) throws Exception {
		return mapper.findCommodityStateByIdDao(id);
	}

	@Override
	public Integer getCommodityStockByIDService(Integer id) throws Exception {
		return mapper.getCommodityStockByIDDao(id);
	}

	@Override
	public Integer updateCommodityStockService(Integer id, Integer commodityStock,Integer oldCommodityStock) throws Exception {
		return mapper.updateCommodityStock(id, commodityStock, oldCommodityStock);
	}

	@Override
	public Integer findcommodityStockByIdService(Integer id) throws Exception {
		return mapper.findcommodityStockByIdDao(id);
	}


	@Override
	public Integer updateActivityIdById(Integer id,String wechatActivityId) throws Exception {
		return mapper.updateActivityIdById(id , wechatActivityId);
	}

	@Override
	public Integer updateTotalGroupById(Integer id,Integer totalGroup,Integer oldTotalGroup) throws Exception {
		return mapper.updateTotalGroupById(id , totalGroup,oldTotalGroup);
	}

	@Override
	public Integer updateTimeStatusById(Integer id,Integer timeStatus) throws Exception {
		return mapper.updateTimeStatusById(id , timeStatus);
	}



	@Override
	public List<Commodity> getNewList(Integer index,Integer pageSize,Integer typese,Integer timeStatus,Integer showList)  throws Exception {
		return mapper.getNewList(index , pageSize,typese,timeStatus,showList);
	}

	@Override
	public List<Commodity> getNewListAFewData(Integer index,Integer pageSize, String typese, Integer timeStatus) throws Exception {
		return mapper.getNewListAFewData(index , pageSize,typese,timeStatus);
	}

	@Override
	public Integer countNewList( Integer typese, Integer timeStatus,Integer showList) throws Exception {
		return mapper.countNewList(typese,timeStatus,showList);
	}


	/**
	 * 按照 id 查询 wechatActivityId
	 * @param id
	 * @return
	 */
	@Override
	public String getWechatActivityIdById(Integer id) throws Exception {
		return mapper.getWechatActivityIdById(id);
	}


	/** get MaxGroup */
	@Override
	public Integer getMaxGroupById(Integer id) throws Exception {
		return mapper.getMaxGroupById(id);
	}

	/** get createGroupNumber */
	@Override
	public Integer getCreateGroupNumberById(Integer id) throws Exception {
		return mapper.getCreateGroupNumberById(id);
	}

	@Override
	public Commodity getLestById(Integer id ) throws Exception {
		return mapper.getLestById(id);
	}


	/**
	 *
	 * @param bean
	 * @param type
	 * @return
	 */
	private static Commodity updateImageAndDetailsBean(Commodity bean,Integer type,CommodityMapper mapper,FileResourceMapper fileResourceMapper){
		try {
			//TODO 删除所有 FileResource
			fileResourceMapper.deleteByCidAndClass(bean.getId(),"Commodity");

			//TODO 图片资源存储
			if(bean.getArrayImg() != null){
				if(!"".equals(bean.getArrayImg())){
					String[] arrayImg = bean.getArrayImg().split(",");
					if(arrayImg != null){
						List<FileResource> resourceslist = new ArrayList<FileResource>();
						for (int i = 0;i<arrayImg.length ;i++) {
							if (arrayImg[i] != null) {
								if (!"".equals(arrayImg[i])) {
									/** 设置封面和分享图片为第一张 */
									String imageUrl = ToolsImage.getImageUrlByServer(arrayImg[i]);
									if (i == 0) {
										bean.setImage(imageUrl);
										bean.setShareImage(imageUrl);
									}
									/** 通知更新所有引用1 */
									String param = "material_id=" + arrayImg[i];
									String json = ToolsHttpRequest.sendPost(Api.UPDATE_FILE_STATUS, param);
									System.out.println("------------ [out] Notify the picture material_id [" + param + "] server update file reference to 1 Return: " + json + " ------------");

									//TODO 添加 FileResource
									FileResource fileResource = new FileResource(UUID.randomUUID().toString(), imageUrl, "Image", arrayImg[i], bean.getId(), "Commodity", String.valueOf(new Date().getTime()), "", "");
									fileResourceMapper.save(fileResource);
								}

							}
						}

						//TODO 更新资源
						if(type == 1) {
							Commodity oldCommodity = mapper.findCommodityByIdDao(bean.getId());//旧资源
							if(oldCommodity != null){
								if(oldCommodity.getArrayImg() != null){
									if(!"".equals(oldCommodity.getArrayImg())){
										String newImg = bean.getArrayImg();
										String oldImg = oldCommodity.getArrayImg();
										String[] oldArrayImg = oldImg.split(",");
										if(oldArrayImg != null) {
											for (int i = 0; i < oldArrayImg.length; i++) {
												if(newImg.indexOf(oldArrayImg[i]) != -1 ){//旧引用包含新引用
												}else{
													//删除素材的在文件服务器上的url
													String param = "material_id=" + oldArrayImg[i];
													String json = ToolsHttpRequest.sendGet(Api.DEL_FILE, param);
													System.out.println("------------ [out] Delete material_id [" + param + "] the URL of the material on the file server Return: " + json + " ------------");
												}
											}
										}
									}
								}
							}
						}

					}
				}
			}
			//TODO 更新详情图标
			bean.setCommodityDetails(ToolsString.conversionIconCSSCode(bean.getCommodityDetails()));
		}catch (Exception e){
			e.printStackTrace();
		}
		return bean;
	}


}
