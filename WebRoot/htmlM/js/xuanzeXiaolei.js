  
 
//选择小类
    function checkbox(h,j){
      if(h==01){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 01化学原料"
      }
      if(h==02){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 02颜料油漆"
      }
      if(h==03){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 03日化用品"
      }
      if(h==04){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 04燃料油脂"
      }
      if(h==05){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 05医用医药"
      }
      if(h==06){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 06金属材料"
      }
      if(h==07){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 07机械设备"
      }
      if(h==08){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 08手动器械"
      }
      if(h==09){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 09科学仪器"
      }
      if(h==10){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 10医疗器械"
      }
      if(h==11){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 11灯具空调"
      }
      if(h==12){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 12运载工具"
      }
      if(h==13){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 13军火烟花"
      }
      if(h==14){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 14珠宝钟表"
      }
      if(h==15){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 15乐器用品"
      }
      if(h==16){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 16办公用品"
      }
      if(h==17){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 17橡胶制品"
      }
      if(h==18){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 18皮革皮具"
      }
      if(h==19){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 19建筑材料"
      }
      if(h==20){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 20家具制品"
      }
      if(h==21){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 21厨房洁具"
      }
      if(h==22){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 22绳网袋篷"
      }
      if(h==23){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 23纱线丝品"
      }
      if(h==24){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 24布料传单"
      }
      if(h==25){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 25服装鞋帽"
      }
      if(h==26){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 26纽扣拉链"
      }
      if(h==27){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 27地毯席垫"
      }
      if(h==28){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 28健身器材"
      }
      if(h==29){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 29肉类食品"
      }
      if(h==30){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 30方便食品"
      }
      if(h==31){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 31饲料种籽"
      }
      if(h==32){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 32啤酒饮料"
      }
      if(h==33){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 33含酒精酒"
      }
      if(h==34){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 34烟草烟具"
      }
      if(h==35){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 35广告销售"
      }
      if(h==36){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 36金融物管"
      }
      if(h==37){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 37建筑修理"
      }
      if(h==38){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 38通讯服务"
      }
      if(h==39){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 39运输储藏"
      }
      if(h==40){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 40材料加工"
      }
      if(h==41){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 41教育娱乐"
      }
      if(h==42){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 42网站法律"
      }
      if(h==43){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 43餐饮酒店"
      }
      if(h==44){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 44医疗园艺"
      }
      if(h==45){
         document.getElementById('biaoti').innerHTML="所选分类>>>>>> 45社会服务"
      }
      var str=document.getElementsByName(j);
      var objarray=str.length;
      var chestr="";
      document.getElementById('show_text').innerHTML=" "; //初始清空
      for (i=0;i<objarray;i++)
	  {
		  if(str[i].checked == true)
		  {
		  
		    document.getElementById('show_text').innerHTML+=str[i].value+"<br>";
		  }
		  
	  }
	
		  document.getElementById("yilei").style.display="none";
		  document.getElementById("jieguo").style.display="block";
		  document.getElementById("erlei01").style.display="none";
		  document.getElementById("erlei02").style.display="none";
		  document.getElementById("erlei03").style.display="none";
		  document.getElementById("erlei04").style.display="none";
          document.getElementById("erlei05").style.display="none";
          document.getElementById("erlei06").style.display="none";
          document.getElementById("erlei07").style.display="none";
          document.getElementById("erlei08").style.display="none";
          document.getElementById("erlei09").style.display="none";
          document.getElementById("erlei10").style.display="none";
          document.getElementById("erlei11").style.display="none";
          document.getElementById("erlei12").style.display="none";
          document.getElementById("erlei13").style.display="none";
          document.getElementById("erlei14").style.display="none";
          document.getElementById("erlei15").style.display="none";
          document.getElementById("erlei16").style.display="none";
          document.getElementById("erlei17").style.display="none";
          document.getElementById("erlei18").style.display="none";
          document.getElementById("erlei19").style.display="none";
          document.getElementById("erlei20").style.display="none";
          document.getElementById("erlei21").style.display="none";
          document.getElementById("erlei22").style.display="none";
          document.getElementById("erlei23").style.display="none";
          document.getElementById("erlei24").style.display="none";
          document.getElementById("erlei25").style.display="none";
          document.getElementById("erlei26").style.display="none";
          document.getElementById("erlei27").style.display="none";
          document.getElementById("erlei28").style.display="none";
          document.getElementById("erlei29").style.display="none";
          document.getElementById("erlei30").style.display="none";
          document.getElementById("erlei31").style.display="none";
          document.getElementById("erlei32").style.display="none";
          document.getElementById("erlei33").style.display="none";
          document.getElementById("erlei34").style.display="none";
          document.getElementById("erlei35").style.display="none";
          document.getElementById("erlei36").style.display="none";
          document.getElementById("erlei37").style.display="none";
          document.getElementById("erlei38").style.display="none";
          document.getElementById("erlei39").style.display="none";
          document.getElementById("erlei40").style.display="none";
          document.getElementById("erlei41").style.display="none";
          document.getElementById("erlei42").style.display="none";
          document.getElementById("erlei43").style.display="none";
          document.getElementById("erlei44").style.display="none";
          document.getElementById("erlei45").style.display="none";
	      
    }
    
    function getParams(key) {
        var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    }
    
    
    
    
    
    
    
    