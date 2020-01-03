<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
﻿<%-- 
    Document   : CardShop
    Created on : 1 ก.ย. 2553, 14:02:45
    Author     : 181
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mr.Card Shop </title>
    <style type="text/css">
    .head {
	font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
}
    dialog text {
	font-family: Tahoma, Geneva, sans-serif;
	color: #FF3;
}
p .npc{
    margin: 0px;
    color: white;
}
    </style>



    <script type="text/javascript">
    var id;
    var name;
    var card;
    function getEl(){
        var ev = arguments[0] || window.event,
        origEl = ev.target || ev.srcElement;
        className=origEl.className;
        id=origEl.id;
        if(className=="npc")
        {
            clickNpc() ;
        }else if(className=="card"){
            
            clickBuy();
        }else if(className=="sellCard"){

            clickSell();
        }
    }
    function clickBuy()
    {
         {$.get("/GrianIsland/ShopNpc?npc=buycard&card="+id, function(data) {
            if(!$("#message").isVisible && data!="")
            {
                $("#message").show(500);
            }
            else if( data=="")
            {
                $("#message").hide(500);
            }

            $('#message').html(data);
        });}

    }
    function clickSell()
    {
         {$.get("/GrianIsland/ShopNpc?npc=sellCard&card="+id, function(data) {
            if(!$("#message").isVisible && data!="")
            {
                $("#message").show(500);
            }
            else if( data=="")
            {
                $("#message").hide(500);
            }

            $('#message').html(data);
        });}

    }
    function clickNpc()
    {
        {$.get("/GrianIsland/ShopNpc?npc="+id, function(data) {
                $("#message").animate({"height": "300px","top":"100px"}, "slow");
            if(!$("#message").isVisible && data!="")
            {
                $("#message").show(500);
                $('#message').html(data);
            }
            else if( data=="")
            {
                $("#message").hide(500);
            }
        });}
    }
    document.onclick = getEl;
</script>

       <!-- animation for message -->
                <script type="text/javascript">
                    showReg=false;
                    $(function() {
                        //run the currently selected effect
                        function runEffect(){
			//get effect type from
                            var selectedEffect = 'slide';
                        //most effect types need no options passed by default
                            var options = {width: 280,height: 185};
			//run the effect
                            if(!showReg)
                            {
                                $("#message").show(selectedEffect,options,500);
                            }
                                else
                            {
                                var selectedEffect = 'explode';
                                var options = {width: 1,height: 1};
                                $("#message:visible").hide(selectedEffect,options,300)
                            }
                        };

		//use RegisterEffect
                        $("#button").click(
                            function() {
                                runEffect();
                                showReg=!showReg;
                                $("#message").animate({"height": "84px","top":"361px"}, "fast");
                                $('#message').html('<p id="shop" class="npc">ยินดีต้อนรับเข้าสู่ร้านขายการ์ด</p>');
                                return false;
                            }
                        )
                        $("#message").hide();
                    });
                </script>
            <!-- End of register project-->


    </head>
<body>

  <div height="79" Style="position:absolute; left: 0px; top: 0px; width: 670px; height: 500px;" id="map" >
      <img id="button" src="images/npcs/Untitled-1.png"  alt="Untitled-1" style = "position:relative;float: left;top:49px ;padding:0 ; width: 330px; height: 452px; z-index: 10; border: 0;" >
      <img src="images/maps/Cardshop.jpg" width="670" height="500" alt="Cardshop" Style ="position:absolute; left: 0px; top: -1px; height: 507px;" />
      <div class="head" id ="message" style="overflow: hidden;position:absolute; left: 192px; top: 361px; background-image: url(images/dialog.png); width: 378px; height: 84px; text-align: center; padding-left: 40px; padding-top: 40px; color: #FF6; padding-right: 40px;">
          <p id="shop" class="npc">ยินดีต้อนรับเข้าสู่ร้านขายการ์ด</p>
        <span style="text-align: center"></span>
      </div>
 </div>

</body>
</html>
