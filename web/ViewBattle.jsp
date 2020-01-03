<%-- 
    Document   : ViewBattle
    Created on : 16 ก.ย. 2553, 2:50:31
    Author     : Zapdos
--%>

<%@page import="sit.int303.grianisland.game.model.CardBattle"%>
<%@page import="sit.int303.grianisland.core.model.Player"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <link type="text/css" href="css/dark-hive/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
    <style type="text/css">
        .workingArea{ width: 1020px; height: 760px; top:  8px;    margin: auto; background-color: red;}
        .headerInc{ width: 1020px; height: 160px;   top:  0px; position:relative ;float:left  ;margin: auto; background: grey;}
        .game{      width: 670px; height: 500px;   top:  0px; float:left; margin: auto; background: gray; position:relative ;float:left }
        .puts{      width: 350px; height: 500px;   top:  0px; float:left; margin: auto; background:  #3399FF; position:relative ;float:left }
        .footerInc{ width: 1020px; height: 100px;   top:  0px;  margin: auto; background: grey;        position:relative ;float:left }
        body {
            background-color: #000;
        }
    </style>
    <TITLE> Challenge </TITLE>

    <body>



        <div class="workingArea">
            <div class="headerInc">
                <%@include file="Header.jsp" %>
            </div>

            <div id="game" class="game">
                <center>


        <h1>People how reval you !!</h1>
       
        <% LinkedList<String> BattleList = (LinkedList<String>) request.getAttribute("BattleList");
            Player pl = (Player) request.getSession().getAttribute("player");
               CardBattle cb = new CardBattle();
           

        out.print("<h1>Someone has Reval you!!<h><br>");
                            for(String Bid:BattleList){%>
                          
                                        <a href="/GrianIsland/Battle_Card.jsp?Bid=<%=Bid%>">
                                            <%=pl.getNameByid(cb.getUserIdByBattleId(Bid))%></a>
                                        <%


                                        
                                    }%>

   </center>

            </div>

            <div class="puts">
                <%@include file="PutsBoard.jsp" %>
            </div>
            <div class="footerInc">
                <%@include file="Footer.jsp"%>
            </div>
        </div>
    </body>
</html>
