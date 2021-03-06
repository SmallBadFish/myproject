package com.myproject.easyui.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.myproject.model.Menu;

@Repository(value = "menuDao")
public interface MenuDao {
	/**
	 * 根据菜单类型获取菜单
	 * @author yinxunzhi
	 * @time 2015年5月7日上午10:40:06
	 * @param menu
	 * @return
	 */
	public List<Menu> getMenuListByType(String type);
	
	/**
	 * 获取父菜单下的子菜单
	 * @author yinxunzhi
	 * @time 2015年5月7日上午11:14:39
	 * @param menu
	 * @return
	 */
	public List<Menu> getMenuListByParentId(String parentId);
	
	/**
	 * 获取父菜单列表
	 * @author yinxunzhi
	 * @time 2015年5月12日下午5:40:06
	 * @return
	 */
	public List<Menu> getMenuList();
	
	/**
	 * 获取menu
	 */
	public Menu getMenuById(String id);
	/**
	 * 删除菜单
	 */
	public void delMenu(String id);
	/**
	 * 更新菜单
	 * @author 尹逊志
	 * @time 2015年5月25日下午1:21:39
	 *
	 */
	public void updateMenu(Menu menu);
	
	/**
	 * 添加菜单
	 * @author 尹逊志
	 * @time 2015年5月30日下午7:07:15
	 *
	 */
	public void addMenu(Menu menu);

	public List<Menu> getAllMenus();
}
