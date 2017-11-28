/*初始化显示item的情况
 * 即放入oriId，showId的东西
 * 同时放入要增加的东西
 */

var displayItem = function(data){
	oriId = [];
	delId = [];
	addId = [];
	var real = '';
	for(var i = 0; i < data.length; i++){
		var item = data[i];
		real = real+addItem(item.objName,item.objID);
		//初始化请求，一开始就有的id，和显示的id
		oriId.push(item.objID);
		showId.push(item.objID);
	}
	initialItem = real;
	$("#page-bar-userlist").append(initialItem);
	fresh();
}


$("#fold").click(function(){
	$userlist=$('#page-bar-userlist');
	if($userlist.is(':hidden')){
		$userlist.show();
		$("#toggleBtn > img:eq(0)").attr('src','../../img/fold.png');
		$("#toggleBtn > img:eq(0)").attr('title','收起');
	}else if($userlist.is(':visible')){
		$userlist.hide();
		$("#toggleBtn > img:eq(0)").attr('src','../../img/open.png');
		$("#toggleBtn > img:eq(0)").attr('title','展开');
	}
});

/*下方userlist删除所有
 *也就是把初始化加到所有的delId=[]
 *同时清空要增加的和显示的
 */
$("#trash").click(function(){
	//$('#page-bar-userlist').hide();
	$('#page-bar-userlist').children('div').remove();
	delId=[];
	//全部删除，即显示的，和要增加的都清空
	//删除的即初始那些都要删除
	delId=[];
	for(var i=0;i<oriId.length;i++)
	{
		delId[i] = oriId[i];
	}
	addId=[];
	showId=[];
});
	
/*下方userlist恢复到一开始保存时的样子
 * 这里恢复到增加和删除都清空
 * 然后显示的变成初始化的样子
 */
$("#refreshPage").click(function(){	
	$('#page-bar-userlist').html(initialItem);
	$('#page-bar-userlist').show();
	//即恢复成初始的id,显示的也是是原来的
	addId=[];
	delId=[];
	showId=[];
	for(var i=0;i<oriId.length;i++)
	{
		showId[i] = oriId[i];
	}
});


/*下方userlist保存
 * 把现在显示的item内容保留下来
 * 传addId，delId进行数据库增加和删除的更新操作
 * 存储成功识执行saveDisplayItem的回调函数
 */
$("#save").click(function(){
	var param=[];
	for(var i=0;i<showId.length;i++){
		var name="";
		for(var j=0;j<itemData.length;j++){
			if(itemData[j].objID==showId[i]){
				name=itemData[j].objName;
				break;
			}
		}
		param.push({"objType":objType,"objID":showId[i],"objName":name});
	}
	updateDisplayItem(param,saveDisplayItem);
});

/*
 * 删除某一个id
 * 1、先删除显示的id数组
 * 判断要删除的那个是否在初始化的里头
 * 如果在，就要加入要进行删除的id的数组里头
 * 如果不在，就意味着要删除的这个是新增的，那么就删除add里头的id
 */
function removeAttr(uid)
{
	var name = '#'+uid;
	$(name).hide();
	var index1 = arrayContain(oriId,uid);
	var index2 = arrayContain(addId,uid);
	var index3 = arrayContain(showId,uid);
	//要删除某一个id
	//先在已经显示的节点中删除这个点
	showId.splice(index3,1);
	//然判断是否在初始化的那里面，如果在，那么就要添加到要删除的那里头
	if(index1>=0)
	{
		delId.push(uid);
	}
	//如果不在初始化的里头，那么肯定在后面添加的里头，那么在后面添加里头的节点删去这个点
	else if(index2>=0)
	{
		addId.splice(index2,1);
	}
}

/*
 * 存储时的回调函数
 * 即存储成功时，保存下面的list的内容
 * 同时清除增加，删除，同时将初始化id变成现在显示的id
 */
var saveDisplayItem = function(data){
	if(data=="succeed")
	{
		alert("保存成功");
		initialItem = $('#page-bar-userlist').html();
		addId=[];
		delId=[];
		oriId=[];
		for(var i=0;i<showId.length;i++)
		{
			oriId[i] = showId[i];
		}
	}
	else if(data=="fail")
	{
		alert("保存失败");
	}
}

var addItem = function(name,id)
{
	var item = itemcontent;
	item = item.replace('place',name);
	item = item.replace('uid',''+id);
	item = item.replace('uid',''+id);
	return item;
}






