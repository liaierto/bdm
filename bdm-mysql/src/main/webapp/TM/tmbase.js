  var methodParmater = new Object();
  var columnSearch = new Object();
  var tabbleParmater = new Object();
  var currentPage = 1;
  methodParmater.currentpage =currentPage;
  methodParmater.pageRow     =10;
  tabbleParmater.currentpage =currentPage;
  tabbleParmater.pageRow     =10;
  
  var lastsel  =null;
  var savedRow = null;  
  var savedCol = null; 
  tmBase={

	callRemot :function(service,method,successfn){
		 $.ajax({
	          type : "post",
	          url: "BaseAction",
	          data: {
	                "service": service,
	                "method": method
	                },
	          dataType:"text",
	          async: false,
	          success: function(data) {
	             var pResult = JSON.parse(data);
	             successfn(pResult);  
	                        
	          },
              error: function(e){
            	  console.info(e);
             }
	    });
	},
	callRemots :function(service,method,parameter,successfn){
		 $.ajax({
	          type : "post",
	          url: "BaseAction",
	          data: {
	                "service": service,
	                "method": method,
	                "parameter": $.toJSON(parameter)
	                },
	          dataType:"text",
	          async: false,
	          success: function(data) {
	             var pResult = JSON.parse(data);
	             successfn(pResult);          
	          }
	    });
	},
	groupAppend :function(groups){
		 var size = groups.length;
	      $("#groups").html("<li><a href='mindex.html'><span class='am-icon-home'></span> 首页</a></li><li><button onclick='tmBase.addGroup()' class='am-btn am-btn-primary am-btn-block'>添加新分组</button></li>");
	      for(var i=0;i<size;i++){
	     	 $("#groups").append("<li><a href='javascript:void(0);' onclick='tmBase.queryMethod(\""+groups[i].group_id+"\")'><span class='am-icon-book am-icon-fw'></span>"+groups[i].group_name+"</a></li>");
	      }   
		
	},
	methodTableInit : function(){
	      jQuery("#methodTable").jqGrid({
	          url: 'BaseAction',
	      	datatype: "json",
	          postData: {"service": "methodService","method": "queryPage", "parameter":$.toJSON(methodTable)},
				height: 400,
	          height : 250,
	          colNames : [ 'id','方法名称', '方法描述', '类型', '查询列','查询参数','查询条件','关联表'],
	          colModel : [ 
	              {name : 'id',index : 'id',width : 190,sorttype : "string",hidden:true,editable : false},
	              {name : 'name',index : 'name',width : 190,sorttype : "string",editable : false},
	              {name : 'description',index : 'description',width : 190,sorttype : "string",editable : false},
	              {name : 'type',index : 'type',width : 190,editable:false},
	              {name : 'clomn', index : 'clomn', width : 190,sorttype : "string",editable : false},
	              {name : 'parameter', index : 'parameter', width : 190,sorttype : "string",editable : false},
	              {name : 'filter', index : 'filter', width : 190,sorttype : "string",editable : false},
	              {name : 'tableName', index : 'tableName', width : 190,sorttype : "string",editable : false}
	          ],
	          jsonReader: {
	              root: "rows",
	              page: "page",
	              total: "total",
	              records: "records",
	              repeatitems: false
	          },
	          pager: "#methodPager",
	          rowNum: '10', //每页显示记录数
	          multiselect: false, //是否支持多选   
	          sortorder: "desc",
	          closeAfterAdd: true, //保存后关闭窗体
	          recordtext: "记录 {0} - {1} 总记录数 {2}",//显示记录数的格式
	          emptyrecords: "无数据",//空记录时的提示信息
	          loadtext: "获取数据中...",//获得数据时的提示信息
	          pgtext: "第 {0}页 总页数 {1}",//页数显示格式
	          altRows: true,//隔行变色
	          altclass: 'ui-widget-content-altclass',//隔行变色样式
	          cellEdit: true,
	          cellsubmit: 'clientArray',
	          gridComplete: function(){
	          	
	          },
	          onSelectRow : function(id) {
	          },
	          onCellSelect: function(rowid, iCol, cellcontent){// 进入编辑框显示 rowid 唯一;iRow行号
	         },
	         onPaging: function(pgButton){//分页事件  
	          	
		            if(pgButton=="next_methodPager"){
		            	currentPage = currentPage+1;
		            }
		            if(pgButton=="prev_methodPager"){
		            	currentPage = currentPage-1;
		            }
		            if(pgButton=="last_methodPager"){

		            	currentPage =$("#methodTable").getGridParam ('lastpage');
		            }
		            if(pgButton=="first_methodPager"){
		            	currentPage=1;
		            }
				        methodParmater.currentpage =currentPage;
		                $("#methodTable").setGridParam ({ postData: {"service": "methodService","method": "queryPage", "parameter":$.toJSON(methodParmater) }}).trigger('reloadGrid');
		            },
				autowidth: true
	      });    
	},
	
	columnsTableInit : function(){
		jQuery("#columnsTable").jqGrid({
	          url: 'BaseAction',
	      	datatype: "json",
	          postData: { "service": "objectMetaService","method": "query", "parameter": $.toJSON(columnSearch)},
	          colNames : [ '列名称', '列描述'],
	          colModel : [ 
	              {name : 'name',index : 'name',width : 190,sorttype : "string",editable : false},
	              {name : 'description',index : 'description',width : 190,sorttype : "string",editable : false},
	          ],
	          jsonReader: {
	              root: "rows",
	              repeatitems: false
	          },
	          rowNum: '10', //每页显示记录数
	          multiselect: true, //是否支持多选   
	          sortorder: "desc",
	          closeAfterAdd: true, //保存后关闭窗体
	          emptyrecords: "无数据",//空记录时的提示信息
	          loadtext: "获取数据中...",//获得数据时的提示信息
	          altRows: true,//隔行变色
	          altclass: 'ui-widget-content-altclass',//隔行变色样式
	          cellEdit: false,
	          cellsubmit: 'clientArray',
	          gridComplete: function(){
	          	
	          },
				autowidth: true
	      });
		$("#columnsTable").setGridWidth(530);
	},
	
	addGroup : function(){
		$('#my-groups').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	  var objg = new Object();
		          var nameG = $("#groupName").val();
		          objg.group_name=nameG;
		          tmBase.callRemots("methodGroupService","add",objg,function(groups){
			             if(groups.code==1){
			            	 alert("保存成功");
			            	 window.location.reload();//刷新当前页面.
			             }else{
			                alert("保存失败");
			             }
		          });
		      },
		      onCancel: function(e) {
		      }
		    });
	},
	
	queryMethod : function(name){
		methodParmater.group_id =name;
		$("#methodTable").setGridParam ({ postData: {"service": "methodService","method": "queryPage", "parameter":$.toJSON(methodParmater) }}).trigger('reloadGrid');
	},

	addMethod : function(){
		this.callRemot("objectService", "query",function(pResult){
             var size = pResult.length;
             for(var i=0;i<size;i++){
            	 $("#tableSelect").append('<option value="' +pResult[i].name+'"> '+pResult[i].description+'</option>');
            	 
             }
             $('#my-method').modal({
       	      relatedTarget: this,
       	      onConfirm: function(e) {
       	    	  var addObj = new Object();
       	    	  addObj.id = "";
       	    	  addObj.name =  $("#methodId").val();
       	    	  addObj.description =  $("#methodName").val();
       	    	  addObj.type =  $("#methodType").val();
       	    	  addObj.clomn =  $("#searchColumn").val();
       	    	  addObj.parameter = $("#methodParamer").val();
       	    	  addObj.filter = $("#methodFilter").val();
       	    	  addObj.tableName = $("#tableSelect").val();
       	    	  addObj.group_id = methodParmater.group_id;
       	    	tmBase.callRemots("methodService", "update",addObj,function(groups){
       	    	  $(this).removeData('amui.modal');
       	    	  $("#methodTable").setGridParam ({ postData: {"service": "methodService","method": "queryPage", "parameter":$.toJSON(methodParmater) }}).trigger('reloadGrid');
       	    	});
       	      },
       	      onCancel: function(e) {
       	    	  $(this).removeData('amui.modal');
       	      }
       	    });
        });
	},
	
	editMethod : function(){
		this.callRemot("objectService", "query",function(pResult){
            var size = pResult.length;
            for(var i=0;i<size;i++){
           	 $("#tableSelect").append('<option value="' +pResult[i].name+'"> '+pResult[i].description+'</option>');
            }
          var id=$("#methodTable").jqGrid('getGridParam','selrow');
      	  var rowData = $("#methodTable").jqGrid('getRowData',id);
      	  $("#methodId").val(rowData.name);
      	  $("#methodName").val(rowData.description);
      	  //$("#methodType").val(rowData.type);
      	  $("#searchColumn").val(rowData.clomn);
      	  $("#methodParamer").val(rowData.parameter);
      	  $("#methodFilter").val(rowData.filter);
      	  $("#tableSelect").val(rowData.tableName);
      	  if (!$.AMUI.support.mutationobserver) {
      		$('#methodType').trigger('changed.selected.amui');
          }else{
        	  var selectted = $('#methodType').find('option[value="'+rowData.type+'"]');
        	  selectted.attr('selected', !selectted.get(0).selected);
          }
      	  $('#my-method').modal({
      	      relatedTarget: this,
      	      onConfirm: function(e) {
      	    	  var addObj = new Object();
      	    	  addObj.id = rowData.id;
      	    	  addObj.name =  $("#methodId").val();
      	    	  addObj.description =  $("#methodName").val();
      	    	  addObj.type =  $("#methodType").val();
      	    	  addObj.clomn =  $("#searchColumn").val();
      	    	  addObj.parameter = $("#methodParamer").val();
      	    	  addObj.filter = $("#methodFilter").val();
      	    	  addObj.tableName = $("#tableSelect").val();
      	    	  addObj.group_id = methodParmater.group_id;
      	    	tmBase.callRemots("methodService", "update",addObj,function(data){
         	    	  $(this).removeData('amui.modal');
         	    	  $("#methodTable").setGridParam ({ postData: {"service": "methodService","method": "queryPage", "parameter":$.toJSON(methodParmater) }}).trigger('reloadGrid');
         	    	});
      	      },
      	      onCancel: function(e) {
      	    	  $(this).removeData('amui.modal');
      	      }
      	    });
        });
	},
	
	delMethod : function(){
		 var id=$("#methodTable").jqGrid('getGridParam','selrow');
     	  var rowData = $("#methodTable").jqGrid('getRowData',id);
		methodParmater.id = rowData.id;
		this.callRemots("methodService", "remove",methodParmater,function(data){
			if(data.code==1){
				$("#methodTable").setGridParam ({ postData: {"service": "methodService","method": "queryPage", "parameter":$.toJSON(methodParmater) }}).trigger('reloadGrid');
			}
		});
	},
	
	colmnSearch : function(){
		
		$(".am-dimmer").addClass("am-active-im");
		
		columnSearch.object =  $("#tableSelect").val();
		  $("#columnsTable").setGridParam ({ postData: { "service": "objectMetaService","method": "query", "parameter":$.toJSON(columnSearch) }}).trigger('reloadGrid');
		  $('#my-columns').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	  var len = jQuery("#columnsTable").jqGrid("getRowData").length;
		    	  var valus = "";
		    	  for(var i=0;i<len;i++){
		    		  valus =valus+jQuery("#columnsTable").jqGrid("getRowData")[i].name+",";
		    	  }
		    	  $("#searchColumn").val(valus.substring(0, valus.length-1));
		    	  $(".am-dimmer").removeClass("am-active-im");
		      },
		      onCancel: function(e) {
		    	  $(".am-dimmer").removeClass("am-active-im");
		    	  
		      }
		    });
	},
	
	
	//表管理模块
	dataTableInit : function(){
	    jQuery("#dataTable").jqGrid({
            url: 'BaseAction',
        	datatype: "json",
            postData: {"service": "objectMetaService","method": "queryPage", "parameter":$.toJSON(tabbleParmater)},
  			height: 400,
            height : 250,
            colNames : [ 'm_id','属性名称', '属性描述', '类型', '长度','主键','主键自增' ],
            colModel : [ 
                {name : 'm_id',index : 'm_id',width : 190,sorttype : "string",hidden:true,editable : false},
                {name : 'name',index : 'name',width : 190,sorttype : "string",editable : true},
                {name : 'description',index : 'description',width : 190,sorttype : "string",editable : true},
                {name : 'type',index : 'type',width : 190,editable:true,formatter: "select",edittype:'select',editoptions: {value: 'varchar:字符串;int:整型;blob:二进制'}},
                {name : 'len', index : 'len', width : 190,sorttype : "string",editable : true},
                {name : 'iskey', index : 'iskey', width : 190,sorttype : "string",editable : false,formatter: "checkbox",formatoptions:{disabled:false}},
                {name : 'auto', index : 'auto', width : 190,sorttype : "string",editable : false,formatter: "checkbox",formatoptions:{disabled:false}}
            ],
            jsonReader: {
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: false
            },
            pager: "#dataTablePager",
            rowNum: '10', //每页显示记录数
            multiselect: false, //是否支持多选   
            sortorder: "desc",
            closeAfterAdd: true, //保存后关闭窗体
            recordtext: "记录 {0} - {1} 总记录数 {2}",//显示记录数的格式
            emptyrecords: "无数据",//空记录时的提示信息
            loadtext: "获取数据中...",//获得数据时的提示信息
            pgtext: "第 {0}页 总页数 {1}",//页数显示格式
            altRows: true,//隔行变色
            altclass: 'ui-widget-content-altclass',//隔行变色样式
            cellEdit: true,
            cellsubmit: 'clientArray',
            gridComplete: function(){
            	
            },
            onSelectRow : function(id) {
                if (id && id !== lastsel) {
                    jQuery("#dataTable").saveRow(lastsel, false, 'clientArray');
                    lastsel = id;
                }
            },
            onCellSelect: function(rowid, iCol, cellcontent){// 进入编辑框显示 rowid 唯一;iRow行号
           	    savedRow = rowid;  
                savedCol = iCol; 
           },
           onPaging: function(pgButton){//分页事件  
            	
  	            if(pgButton=="next_dataTablePager"){
  	            	currentPage = currentPage+1;
  	            }
  	            if(pgButton=="prev_dataTablePager"){
  	            	currentPage = currentPage-1;
  	            }
  	            if(pgButton=="last_dataTablePager"){

  	            	currentPage =$("#dataTable").getGridParam ('lastpage');
  	            }
  	            if(pgButton=="first_dataTablePagerr"){
  	            	currentPage=1;
  	            }
  	            tabbleParmater.currentpage =currentPage;
  	            $("#dataTable").setGridParam ({ postData: {"service": "objectMetaService","method": "queryPage", "parameter":$.toJSON(tabbleParmater) }}).trigger('reloadGrid');
  	            },
  			autowidth: true
        });
	},
	
	tableAppend : function(pResult){
		 
         var size = pResult.length;
         $("#tables").html("<li><a href='mindex.html'><span class='am-icon-home'></span> 首页</a></li>");
         for(var i=0;i<size;i++){
        	 $("#tables").append("<li><a href='javascript:void(0);' onclick='tmBase.tableManage(\""+pResult[i].name+"\",\""+pResult[i].description+"\")'><span class='am-icon-table'></span>"+pResult[i].description+"（"+pResult[i].name+"）</a></li>");
         }   
	                
	},
	
	tableManage : function(name,desc){
		tabbleParmater.object = name;
		tabbleParmater.description = desc;
		$("#dataTable").setGridParam ({ postData: {"service": "objectMetaService","method": "queryPage", "parameter":$.toJSON(tabbleParmater) }}).trigger('reloadGrid');
	},
	
	addTableMeta : function(){
		jQuery("#dataTable").saveRow(lastsel, false, 'clientArray');
	      var id = jQuery("#dataTable").jqGrid("getRowData").length;
	      var data = {
	              name: "name", 
	              description: "desc", 
	              type: "int",
	              length : ""
	          };
	      jQuery("#dataTable").jqGrid('addRowData', id+1, data); 
	},
	
	saveTable : function(){
		if(tabbleParmater.object!=null && tabbleParmater.object!=undefined){
			$("#tableName").val(tabbleParmater.object);
			$("#tableDesc").val(tabbleParmater.description);
			 $("#tableName").attr("disabled",true);  
		}else{
			$("#tableName").val("");
			$("#tableDesc").val("");
		}
		 $('#my-prompt').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	  $("#dataTable").jqGrid('saveCell', savedRow, savedCol);  
		          jQuery("#dataTable").saveRow(lastsel, false, 'clientArray');
		          
		          var obj = new Object();
		          var tableName = $("#tableName").val();
		          var type = $("input[name='radio']:checked").val();
		          obj.object=tableName;
		          obj.description=$("#tableDesc").val();
		          obj.type=type;
		          obj.rows=jQuery("#dataTable").jqGrid("getRowData");
		          tmBase.callRemots("objectService", "createObject",obj,function(pResult){
		        	  if(pResult.code==1){
			            	 alert("保存成功");
			             }else{
			                alert("保存失败");
			             }
		   		  });
		      },
		      onCancel: function(e) {
		      }
		    });
	}
	
}