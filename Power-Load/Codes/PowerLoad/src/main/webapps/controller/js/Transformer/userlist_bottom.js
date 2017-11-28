/**
 * Controller of fault-recognition page
 */

$("#fold").click(function(){	
	$userlist=$('#page-bar-userlist');
	if($userlist.is(':hidden')){
		$userlist.show();
	}else if($userlist.is(':visible')){
		$userlist.hide();
	}
});
	
	/*下方userlist删除*/
$("#trash").click(function(){	
	$('#page-bar-userlist').hide();
	$('#page-bar-userlist').children('div').hide();
	delId=[];
	//全部删除，即显示的，和要增加的都清空
	//删除的即初始那些都要删除
	for(var i=0;i<oriId.length;i++)
	{
		delId[i] = oriId[i];
	}
	addId=[];
	showId=[];
});
	
/*下方userlist恢复*/
$("#refresh").click(function(){	
	$('#page-bar-userlist').html(initialItem);
	$('#page-bar-userlist').show();
	//即恢复成初始的id,显示的也是是原来的
	addId=[];
	delId=[];
	for(var i=0;i<oriId.length;i++)
	{
		showId[i] = oriId[i];
	}
});


/*下方userlist保存*/
$("#save").click(function(){
	var itemType = 1;
	console.log("ori "+oriId);
	initialItem = $('#page-bar-userlist').html();
//	alert(initialItem);
	updateDisplayItem(itemType, addId, delId,saveDisplayItem);
});


/*上面的更新按钮*/
$("#fresh").click(function(){	
	$('#page-bar-userlist').html(initialItem);
	$('#page-bar-userlist').show();
	//即恢复成初始的id,显示的也是是原来的
	addId=[];
	delId=[];
	for(var i=0;i<oriId.length;i++)
	{
		showId[i] = oriId[i];
	}
	
	getTransPowerLoadFeatrues(showId,PowerLoadFeatruesShows);
});


function removeAttr(uid)
{
	console.log(uid);
	var name = '#'+uid;
	$(name).hide();
	var index1 = arrayContain(oriId,uid);
	var index2 = arrayContain(addId,uid);
	var index3 = arrayContain(showId,uid);
	//要删除某一个id
	//先判断是否在初始化的那里面，如果在，那么就要添加到要删除的那里头
	if(index1>=0)
	{
		delId.push(uid);
	}
	//如果不在初始化的里头，那么肯定在后面添加的里头，那么在后面添加里头的节点删去这个点
	else if(index2>=0)
	{
		addId.splice(index2,1);
	}
	//在已经显示的节点中删除这个点
	console.log("删除前show "+showId);
	showId.splice(index3,1);
	console.log("删除后show "+showId);
	console.log("oriId "+oriId);
}

