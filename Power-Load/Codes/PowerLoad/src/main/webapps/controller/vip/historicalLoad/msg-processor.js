/**
 * In charge of interactive with server
 */

var getHisPowerLoad = function(startDate,endDate,showIds, callBack){
	 
	  var ids=[];
	  for(var i = 0; i < showIds.length; i++){
		  var tmp={"id":showIds[i]};
		  ids.push(tmp);
	  }
	  
	  var param = {"startDate":startDate,"endDate":endDate,"ids":ids};
	  
	  var url = "/PowerLoad/api/vipmgr/powerload/history";
	  
	  msgPOST(url, param, callBack);
}

