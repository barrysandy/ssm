package com.xiaoshu.service;


import com.xiaoshu.dao.*;
import com.xiaoshu.entity.Meeting;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/** 标准版 */
@Service("meetingService")
public class MeetingServiceImpl implements MeetingService{

	@Resource private MeetingMapper mapper;

	/** save one */
	@Override
	public Integer save(Meeting bean,String oldImage) throws Exception {
		ToolsImage.updateSSMFile(bean.getImage(),oldImage);
		bean.setId(UUID.randomUUID().toString());
		bean.setCreateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));
		return mapper.save(bean);
	}

	/** update 状态 */
	@Override
	public Integer updateResponseStatusById(Integer status,String updateTime,String id) throws Exception {
		return mapper.updateResponseStatusById(status,updateTime, id);
	}

	/** update all */
	@Override
	public Integer updateAll(Meeting bean,String oldImage) throws Exception {
		bean.setUpdateTime(ToolsDate.getStringDate(ToolsDate.simpleSecond));
		return mapper.updateAll(bean);
	}

	/** update excelPath */
	@Override
	public Integer updateExcelById(String excelPath,String id) throws Exception {
		Meeting old = mapper.getById(id);
		//TODO 更新关联的资源
		if(old != null){
			if(old.getExcelPath() != null){
				ToolsImage.updateSSMFile(excelPath,old.getExcelPath());
			}
		}
		return mapper.updateExcelById(excelPath,id);
	}

	/** delete ById */
	@Override
	public Integer deleteById(String id) throws Exception {
		Meeting bean = mapper.getById(id);
		//TODO 删除关联的资源
		if(bean != null){
			if(bean.getImage() != null){
				ToolsImage.deleteSSMFile(bean.getImage());
			}
		}
		//TODO 删除相关的报名列表

		return mapper.deleteById(id);
	}

	/** select ById */
	@Override
	public Meeting getById(String id) throws Exception {
		return mapper.getById(id);
	}

	/** select List */
	@Override
	public List<Meeting> getListByKeyWord(Integer status,Integer index, Integer pageSize, String keyword, String date1, String date2) throws Exception {
		List<Meeting> list = mapper.getListByKeyWord(status,index, pageSize, keyword, date1, date2);
		//TODO 替换资源id 为图片url
		if(list != null){
			if(list.size() > 0){
				Iterator<Meeting> iterator = list.iterator();
				while (iterator.hasNext()){
					Meeting bean = iterator.next();
					if(bean.getImage() != null){
						String image = ToolsImage.getImageUrlByServer(bean.getImage());
						bean.setImage(image);
					}
				}
			}
		}
		return list;
	}

	/** Count List */
	@Override
	public Integer getCountByKeyWord(Integer status,String keyword,String date1,String date2) throws Exception {
		return mapper.getCountByKeyWord(status,keyword, date1, date2);
	}
}
