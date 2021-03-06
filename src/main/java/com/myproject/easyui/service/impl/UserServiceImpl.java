package com.myproject.easyui.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.easyui.dao.RoleMenuDao;
import com.myproject.easyui.dao.UserDao;
import com.myproject.easyui.dao.UserRoleDao;
import com.myproject.easyui.service.MenuService;
import com.myproject.easyui.service.UserService;
import com.myproject.model.Menu;
import com.myproject.model.Permission;
import com.myproject.model.User;
import com.myproject.model.UserRole;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private MenuService menuService;

	public List<Permission> getPermissions(User user) {
		return null;
	}

	public User getUser(User user) {
			user=userDao.getUser(user);
			List<UserRole> roles = userRoleDao.getRoleId(user.getId());
			StringBuffer roleIds =new StringBuffer();
			if (roles!=null &&roles.size()>0) {
				for (int i = 0; i < roles.size(); i++) {
					if (i!=roles.size()-1) {
						roleIds.append(roles.get(i).getRoleId().toString()).append(",");
					}else {
						roleIds.append(roles.get(i).getRoleId().toString());
					}
				}
			}
			user.setRoleIds(roleIds.toString());
			return user;
	}

	public List<Menu> getResourceList(String id) {
//		List<Menu> resourceList = new ArrayList<Menu>(0);
//		List<UserRole> roles  = userRoleDao.getRoleId(id);
//		if (roles!=null && roles.size()>0) {
//			List<RoleMenu> menus =new ArrayList<RoleMenu>();
//			for (UserRole userRole : roles) {
//				menus = roleMenuDao.getMenuId(userRole.getRoleId().toString());
//			}
//			if (menus!=null && menus.size()>0) {
//				List<Menu> menuList = new ArrayList<Menu>();
//				for (RoleMenu roleMenu : menus) {
//					resourceList.add(menuService.getMenuById(roleMenu.getMenuId().toString()));
//				}
				
//				if (menuList!=null && menuList.size()>0) {
//					for (Menu menu : menuList) {
//						if (menu.getUrl()!=null) {
//							resourceList.add(menu);
//						}
//					}
//				}
//			}
//		}
		return null;
	}

	public List<User> getUserList() {
		return userDao.getUserList();
	}

	public void deltetUser(String id) {
		userDao.deleteUser(id);
	}

	public void addUser(User user) {
		userDao.addUser(user);
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
		List<UserRole> userRoles = userRoleDao.getRoleId(user.getId());
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		String roleIds = user.getRoleIds();
		if (userRoles.size()>0) {
			userRoleDao.deleteUserRole(userRole);
			if (roleIds!=null&&""!=roleIds) {
				for (UserRole userRole2 : userRoles) {
					userRole2.setUserId(user.getId());
					String[] roleids = roleIds.split(",");
					for (String roleId : roleids) {
						userRole2.setRoleId(Integer.parseInt(roleId));
						userRoleDao.addUserRole(userRole2);
					}
				}
			}
		}else {
			if (roleIds!=null&&""!=roleIds) {
				String[] roleids = roleIds.split(",");
				for (String roleId : roleids) {
					userRole.setRoleId(Integer.parseInt(roleId));
					userRoleDao.addUserRole(userRole);
				}
			}
		}
		
	}
	
	public void addUserRole(User user){
		UserRole userRole = new UserRole();
		String roleIds = user.getRoleIds();
		if (roleIds!=null&& "" !=roleIds) {
				String[] roleIdList = roleIds.split(",");
				for (int i = 0; i < roleIdList.length; i++) {
					userRole.setUserId(user.getId());
					userRole.setRoleId(Integer.parseInt(roleIdList[i]));
					userRoleDao.addUserRole(userRole);
				}
		}
	}
}
