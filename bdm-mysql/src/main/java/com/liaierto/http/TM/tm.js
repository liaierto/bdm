
function tmOperator(methodName,rows,filterVal,successfn){
	var parameter = new Object();
	var filter = new Object();
	filter.value = filterVal;
	parameter.rows = rows;
	parameter.filter = filter;
	 $.ajax({
         type : "post",
         url: "NewDataAction",
         data: {
               "method": methodName,
               "parameter":$.toJSON(parameter)
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
}

function tmQueryOperator(methodName,filterVal,current,pageRow,successfn){
	var parameter = new Object();
	var filter = new Object();
	filter.value = filterVal;
	parameter.filter = filter;
	parameter.curentPage = current;
	parameter.pageRow = pageRow;
	 $.ajax({
        type : "post",
        url: "DataAction",
        data: {
        	"method": methodName,
        	"curentPage":current,
        	"pageRow":pageRow,
        	"parameter":$.toJSON(parameter)
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
}