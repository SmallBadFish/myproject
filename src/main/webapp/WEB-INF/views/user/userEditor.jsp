<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id = "addForm" class="easyui-layout" data-options="fit:true,border:false" >
<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:10px 60px 20px 60px">
    <form id="addUserForm" class="easyui-form" method="post">
		<table cellpadding="5px;" >
			<input  type="hidden" name="id" value="${role.id }" />
			<tr>
				<td><label for="username">用 &nbsp;户&nbsp;名:</label>  </td>
				<td><input class="easyui-textbox" type="text" name="username" value="${user.username }" data-options="required:true,validType:'length[6,16]'"   missingMessage="请填写用户名" /></td>
			</tr>
			<tr>
				<td><label for="email">电子邮箱:</label></td>
				<td><input class="easyui-textbox" type="text" name="email" data-options="required:true,validType:'email'" missingMessage="请填写Email"/></td>
			</tr>
			<tr>
				<td><label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label></td>
				<td><input class="easyui-textbox" type="password" name="password" data-options="required:true,validType:'length[1,16]'" missingMessage="请填写密码" /></td>
			</tr>
			<tr>
				<td><label for="repassword">重复密码:</label></td>
				<td><input class="easyui-textbox" type="password" name="repassword" data-options="required:true,validType:'length[1,16]'" missingMessage="请先填写密码"/></td>
			</tr>
		</table> 
	</form> 
  </div>
</div>
