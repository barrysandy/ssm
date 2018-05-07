package com.xiaoshu.dao;

import com.xiaoshu.entity.PublicAccountInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PublicAccountInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(PublicAccountInfo record);

    int insertSelective(PublicAccountInfo record);

    PublicAccountInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PublicAccountInfo record);

    int updateByPrimaryKey(PublicAccountInfo record);
    
    List<PublicAccountInfo> selectByPage(Map map);
	
    int selectCount(Map record);

    PublicAccountInfo getInfoByUserId(Map map);

    @Select("select * from public_account_info where MENU_ID = #{menuId}")
    PublicAccountInfo getInfoByMenuid(String menuId);

    @Select("select * from public_account_info where PARENT_MENU_ID = #{parentMenuId}")
    PublicAccountInfo getByParentMenuId(String parentMenuId);

    @Select("select * from public_account_info where USABLE = 1")
    List<PublicAccountInfo> selectListAll(int usable);

    @Select("select PARENT_MENU_ID from public_account_info where OPEN_PLATFORM = #{openPlatform} AND PARENT_MENU_ID != #{parentMenuId}")
    String getParentMenuIdByOpenPlatform(@Param("openPlatform") String openPlatform, @Param("parentMenuId") String parentMenuId);


    @Select("select ID from public_account_info where PARENT_MENU_ID = #{parentMenuId}")
    String getIdByParentMenuId(@Param("parentMenuId") String parentMenuId);


    @Select("SELECT PARENT_MENU_ID FROM public_account_info WHERE OPEN_PLATFORM = " +
            "(SELECT OPEN_PLATFORM FROM public_account_info WHERE PARENT_MENU_ID =#{parentMenuId}) AND PARENT_MENU_ID !=#{parentMenuId}")
    String getOtherParentMenuIdByMenuId(@Param("parentMenuId") String parentMenuId);

    @Select("select PARENT_MENU_ID from public_account_info where ID = #{id}")
    String getParentMenuIdById(@Param("id") String id);

    /** 获取公众号类型 */
    @Select("select ACCOUNT_TYPE from public_account_info where PARENT_MENU_ID = #{parentMenuId}")
    int getAccountTypeByParentMenuId(@Param("parentMenuId") String parentMenuId);
}