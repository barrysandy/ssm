package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MenuMapper extends BaseMapper<Menu> {

    String getParentIdByMenuId(String menuId);

    /**
     * 通过菜parentId和menuUrl来获取菜单menuId
     * @param menuId = parentId
     * @param menuUrl
     * @return 被查询的menuId OR null
     */
    @Select("select menuId from menu where parentId = #{menuId} AND menuUrl=#{menuUrl}")
    int selectMenuIDByParentIDANDUrl(@Param("menuId")Integer menuId, @Param("menuUrl")String menuUrl);

    /**
     * 按menuId查询菜单
     * @param menuId = parentId
     * @return 被查询的menuId OR null
     */
    @Select("select * from menu where menuId = #{menuId} limit 0,1")
    Menu selectMenuByParentID(@Param("menuId")Integer menuId);


    /**
     * 通过menuName查询菜单menuId
     * @param menuName
     * @return 被查询的menuId OR 0
     */
    @Select("select menuId from menu where menuName=#{menuName} limit 0,1")
    int selectMenuIDByName(@Param("menuName")String menuName);

    /**
     * 通过menuId更新状态
     * @param menuId
     * @param state
     * @return 被查询的menuId OR 0
     */
    @Update("update menu set state=#{state} where menuId=#{menuId} ")
    int updateIsParent(@Param("menuId")Integer menuId,@Param("state")String state);

    /**
     * 通过menuId更新名称
     * @param menuId
     * @param menuName
     * @return 被查询的menuId OR 0
     */
    @Update("update menu set menuName=#{menuName} where menuId=#{menuId} ")
    int updateMeanName(@Param("menuId")Integer menuId,@Param("menuName")String menuName);

}