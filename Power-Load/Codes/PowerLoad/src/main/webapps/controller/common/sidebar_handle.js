/**
 * Controller of fault-recognition page
 */
var allData;
var itemData=[];

var setting = {
  view: {
    dblClickExpand: false,
    showLine: true,
    selectedMulti: false
  },

  data: {
    simpleData: {
      enable:true,
      idKey: "id",
      pIdKey: "pId",
      rootPId: ""
    }
  },

  callback: {
    //第三级点击事件的执行
    onClick:zTreeOnclick
  }
};

//设置点击事件，即更新第三节列表
function zTreeOnclick(event,treeId, treeNode)
{
  var zTree = $.fn.zTree.getZTreeObj("treeDemo");
  if (treeNode.isParent) {
    zTree.expandNode(treeNode);
    return false;
  }
  else {
    var orinindex = arrayContain(oriId,treeNode.url);
    var addindex = arrayContain(addId,treeNode.url);
    var showindex = arrayContain(showId,treeNode.url);
    var deleindex = arrayContain(delId,treeNode.url);

    /*
     * 点击事件的逻辑：
     * 如果没有显示，那么要判断是不是之前点击删除按钮被删除：
     * 如果是之前被删除的，那么显示出来就行了
     * 如果不是之前被删除的，那么就添加这么一个id显示出来
     *
     * 同时对于id数组的操作是，这样样的：
     * 1、添加到要显示的id数组里面
     * 2、如果不属于初始化的id，而同时在新增的id里头也没有的话，那么就添加到addId里头
     * 3、有可能要添加的东西之前是要删除的，因此删除delId里头相关id
     */
    if(showindex<0)
    {

      if(showId.length==8){
        return true;
      }
      var name = $('#'+treeNode.url);
      //表示是之前删除的,显示出来就行了
      if(name[0])
      {
        name.show();
      }
      //表示是新增的，那么就新增加这个item的内容
      else
      {
        var real = addItem(treeNode.name,treeNode.url);
        $("#page-bar-userlist").append(real);
      }
      //添加到要显示的id数组里面
      showId.push(treeNode.url)

      if(addindex<0&&orinindex<0)
      {
        addId.push(treeNode.url);
      }
      if(deleindex>=0)
      {
        delId.splice(deleindex,1);
      }
    }

    return true;
  }
}
$('#searchcontent').bind('input propertychange', function() {
    search($('#searchcontent').val());
});

var search=function(content){
  if(content==''){
    updateList(allData);
  }else{
    zNodes=[];
    var ticon="/PowerLoad/img/trans.jpg";
    if(objType==2){
      ticon="/PowerLoad/img/vip.jpg";
    }
    for(var i=0;i<itemData.length;i++){
      if(itemData[i].objName.indexOf(content)!=-1){
        zNodes.push({
            id:itemData[i].objID,
            pId:-1,
            isParent:false,
            name:itemData[i].objName,
            url:itemData[i].objID,
            icon:ticon
          });
      }
    }
    var t = $("#treeDemo");
    t = $.fn.zTree.init(t, setting, zNodes);
  }
}
//更新tree
var updateList = function(data){
  allData=data;
  refreshCachedList(data);
  var t = $("#treeDemo");
  t = $.fn.zTree.init(t, setting, zNodes);
}


//缓存第一二级列表数据
var refreshCachedList = function(data){
  var ticon = "/PowerLoad/img/trans.jpg";
  if (objType == 2) {
    ticon = "/PowerLoad/img/vip.jpg";
  }
  zNodes = [];
  for(var i = 0; i < data.length; i++){
    var item = data[i];
    zNodes.push({
      id:''+i,
      pId:-1,
      isParent:true,
      name:item.v_deptName,
      icon:"/PowerLoad/img/dept.jpg"
    });

    var children = item.children;
    
    for(var j=0;j<children.length;j++)
    {
      var v_item = children[j];
      zNodes.push({
        id:i+''+j,
        pId:''+i,
        isParent:true,
        name:v_item.v_deptName,
        icon:"/PowerLoad/img/dept1.jpg"
      });
      
      var items=v_item.items;
      
      for(var k=0;k<items.length;k++){
        zNodes.push({
          id:i+''+j+''+k,
          pId:i+''+j,
          isParent:false,
          name:items[k].objName,
          url:items[k].objID,
          icon:ticon
        });
        
        itemData.push(items[k]);
      }
    }
  }
}