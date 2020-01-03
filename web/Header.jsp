<%-- 
    Document   : Header
    Created on : 9 ส.ค. 2553, 21:26:02
    Author     : Vable
--%>
<%@taglib prefix="puts" uri="/WEB-INF/tlds/PutsTLD.tld" %>
<%@page import="sit.int303.grianisland.core.model.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            timedCountMoney();
            function timedCountMoney()
            {
                getMoney();
                t=setTimeout("timedCountMoney()",5000);
            }
            function getMoney()
            {
                $.get("GetMoney", function(data) {
                    $("#HeaderMoney").html("Now you have money :"+data+"<br><br>If you need some tips click <a href=\"/GrianIsland/HowtoPlay.jsp\">here</a>");
                });
            }
        </script>
    </head>
    <body>
        <div class="headerDiv" style="background-image: url(images/header/header3_game.jpg);width: 1020px; height: 160px;" >
            <div class="profile" style="position: relative;float: left;left: 250px;top :60px;width: 550px;height: 95px" >
                <b id="HeaderMoney" style="color:#ffffff;float:left;position:relative">(${player.money} gam)<br>If you need some tips click<a href="/HowtoPlay.jsp">here</a></b>
                
                <img src="images/avatar/<puts:GetAvatar userId="${player.id}" />" width="90px" height="90px" style="float: right" />
                <div class="detail" style="float: right;width: 150px;height: 95px">
                    <div id="name" style="float: left;width: 150px;height: 40px;">
                        <H1 style="color: white;text-align: left">${player.name}</H1>
                        <H1 style="color: white;text-align: left"><puts:GetMapName mapId="${player.map}" mapmanager="${applicationScope['maps']}" />${application.maps}</H1>
                    </div>
                </div>
            </div>
            <div class="menu" style="float: right;width: 200px;height: 160px">
                <a href="/GrianIsland/Game.jsp">
                    <img src="images/header/home.png" alt="Home" border="0" style="float: left;top: 100px;position: relative" />
                </a>
                <a href="/GrianIsland/ChooseOpponent.jsp">
                    <img src="images/header/HomeIcon.png" alt="HomeIcon" width="122" height="160" border="0" style="float: left;position: relative"/>
                </a>
            </div>
        </div>
    </body>
</html>