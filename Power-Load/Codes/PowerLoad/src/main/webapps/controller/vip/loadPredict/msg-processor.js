/**
 * In charge of interactive with server
 */

var getTransPowerLoadFeatrues = function(showIds, callBack){
	 
	  var ids=[];
	  for(var i = 0; i < showIds.length; i++){
		  var tmp={"id":showIds[i]};
		  ids.push(tmp);
	  }
	  
	  var param = {"startDate":1,"endDate":2,"ids":ids};
	  
	  var url = "/PowerLoad/api/transmgr/powerload/feature";
	  
	  msgPOST(url, param, callBack);
}

