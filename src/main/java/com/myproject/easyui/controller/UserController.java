package com.myproject.easyui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myproject.easyui.service.UserService;
import com.myproject.easyui.web.util.ResponseResult;
import com.myproject.model.User;

/**
 * 用户处理层
 * 
 * @author yinxunzhi
 * @creatTime 2015年4月7日下午4:40:06
 * @version 1.0
 * @description
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 用户列表显示页面
	 * 
	 * @author yinxunzhi
	 * @time 2015年4月7日下午4:41:38
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/user/userList", method = RequestMethod.GET)
	public String userListView(Model model) {
		return "/user/userList";
	}

	/**
	 * 返回用户列表所需数据
	 * 
	 * @author yinxunzhi
	 * @time 2015年4月7日下午4:41:58
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/user/userList", method = RequestMethod.POST)
	public String getUserData(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		if (rows == null) {
			rows = "20";
		}
		if (page == null) {
			page = "1";
		}
		PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		List<User> userList =  userService.getUserList();
		PageInfo<User> pageInfo = new PageInfo<User>(userList);
		ResponseResult result = new ResponseResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(userList);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	/**
	 * 用户添加编辑页面
	 * 
	 * @author yinxunzhi
	 * @time 2015年4月9日下午12:37:57
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/user/userEdit", method = RequestMethod.GET)
	public String userAddView(Model model,HttpServletRequest request) throws JsonProcessingException {
		String id = request.getParameter("id");
		if(id != null){
			String resource = "/mybatis-config-test.xml";
			InputStream is = UserController.class.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
			SqlSession session = factory.openSession();
			String statement = "com.myproject.mybatis.user.userMapper.getUser";
			User user = session.selectOne(statement, id);
			session.close();
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(user);
		}else{
			return "failer";
		}
	}

	/**
	 * 添加用户完成响应
	 * 
	 * @author yinxunzhi
	 * @time 2015年4月9日下午5:07:51
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/user/userEdit", method = RequestMethod.POST)
	public String userAddResponse(@ModelAttribute("user") User user,
			HttpServletRequest request, HttpServletResponse response) {
		user.setId(user.getUsername());
		user.setRegisterTime(new Date());
		user.setStatus(true);
		String resource = "/mybatis-config-test.xml";
		InputStream is = UserController.class.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = factory.openSession();
		String statement = "com.myproject.mybatis.user.userMapper.saveUser";
		session.insert(statement, user);
		session.commit();
		session.close();
		return null;
	}
}
