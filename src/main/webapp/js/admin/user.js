var datagrid;
$(function(){
	$("form > div").css("margin" , "10px 0px 0px 0px");
	datagrid = $('#gridUser').datagrid({   
	    url:baseUrl+'/user/userList',
	    fit:true,
	    fitColumns:true,
	    rownumbers:true,
	    columns:[[   
	        {field:'id',title:'ID',width:'10%',sortable:true},   
	        {field:'username',title:'用户名',width:'15%',sortable:true},   
	        {field:'email',title:'邮箱',width:'15%'},   
	        {field:'realname',title:'姓名',width:'5%'},   
	        {field:'registerTime',title:'注册时间',width:'15%',sortable:true,formatter: function(value,row,index){
	        	if (value != null) {
	        		var date = new Date(value);
	        		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
	        		+ date.getDate();
	        	}
	        }},   
	        {field:'ipsIdentification',title:'ips',width:'15%',sortable:true} 
	    ]],
	    toolbar:[
		    {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			},'-',{
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('unselectAll');
				}
			}, '-', {
				text : '批量修改用户角色',
				iconCls : 'icon-edit',
				handler : function() {
					editRole();
				}
			}, '-'],
		pagination : true,
		pageSize : 20,
		singleSelect : true
	}); 
}); 


//注册用户
function append(){
	parent.$.modalDialog({   
	    title: '添加用户',   
	    width: 400,   
	    height: 400,
	    minimizable:true,
	    maximizable:true,
	    resizable:true,
	    closed: false,
	    href: baseUrl+'/user/userAdd',
	    buttons:[{
	    	text:'添加',
	    	handler:function(){
	    		parent.$.modalDialog.openner_datagrid = datagrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
	    		var f = parent.$.modalDialog.handler.find("#addUserForm");
	    		f.submit();
	    	}
	    },{
	    	text:'取消',
	    	handler:function(){
	    		parent.$.modalDialog.handler.dialog('close');
	    	}
	    }]
	});   
}


//提交表单
function submitForm(){
	//显示进度条
	$.messager.progress();
	$('#ff').form('submit', {
		url: '${pageContext.request.contextPath}/user/userEdit',
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 当form不合法的时候隐藏工具条
			}
			return isValid;	// 返回false将停止form提交 
		},
		success: function(data){
			$.messager.progress('close');// 当成功提交之后隐藏进度条
			$('#dlgForm').dialog('close');//关闭添加用户对话框
			$('#gridUser').datagrid('reload');//重新加载数据
		}
	});
}

//清空表单
function clearForm(){
	$('#ff').form('clear');
}


//编辑用户
function edit(){
	var row = datagrid.datagrid('getSelected');
	if (row) {
		parent.$.modalDialog({
			title:'编辑用户',
			width: 400,   
		    height: 300,
		    minimizable:true,
		    maximizable:true,
		    resizable:true,
		    closed: false,
		    href: baseUrl+'/user/userEdit?id='+row.id,
		    buttons : [{
		    	text:'提交',
		    	handler:function(){
		    		parent.$.modalDialog.openner_datagrid=datagrid;
		    		var f = parent.$.modalDialog.handler.find("#editUserForm");
		    		f.submit();
		    	}
		    },{
		    	text:'取消',
		    	handler:function(){
		    		parent.$.modalDialog.handler.dialog('close');
		    	}
		    }]
		});
	}else{
		$.messager.alert('提示', '请选择要编辑的记录！', 'error');
	}
}
//删除用户
function remove(){
	var row = datagrid.datagrid('getSelected');
	if(row != null){
		$.messager.confirm('询问', '您是否要删除当前资源？',function(b) {
			if(b){
    			$.messager.progress();
    			$.post(baseUrl+'/user/userRemove',
    					{id:row.id},
    					function(data){
    						if(data.success){
    							datagrid.datagrid('reload');
    							$.messager.alert('提示',data.msg,'info');
    						}else{
    							$.messager.alert('提示',data.msg,'error');
    						}
    						$.messager.progress('close');
    					},'JSON');
				}
		});
	}else{
		$.messager.alert('提示', '请选择要删除的用户！', 'error');
	}
}
