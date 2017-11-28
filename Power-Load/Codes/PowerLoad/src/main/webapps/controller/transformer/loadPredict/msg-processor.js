/**
 * In charge of interactive with server
 */

var getLoadPredictDefault = function(showIds, callBack){
	 
	  var ids=[];
	  for(var i = 0; i < showIds.length; i++){
		  var tmp={"id":showIds[i]};
		  ids.push(tmp);
	  }
	  
	  var param = {"ids":ids};
	  
	  var url = "/PowerLoad/api/transmgr/powerload/forecast/default";
	  
	  msgPOST(url, param, callBack);
}

var getLoadPredictCustomize = function(id,industryParam, callBack,index){
	
	  var param = {"id":id,"industryParam":industryParam};
	  
	  var url = "/PowerLoad/api/transmgr/powerload/forecast/customize";
	  
	  msgPOST(url, param, callBack,index);
}