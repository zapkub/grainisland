<%-- 
    Document   : Battle_Card
    Created on : 16 ก.ย. 2553, 17:16:04
    Author     : Zapdos
--%>

<%@page import="sit.int303.grianisland.game.model.CardManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
         <%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>..:: Battle Card ::..</title>
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

    </head>
    <body>  <div class="workingArea">
            <div class="headerInc">
                <%@include file="Header.jsp" %>
            </div>

            <div id="game" class="game">
                <center>
        <%  String Bid = (String) request.getParameter("Bid"); %>
        <h1>Your BattleID is : <%=Bid%></h1>
            Choose your card to Duel !

                    <form action="Duel">
                        <input type="hidden" name="Bid" value="<%=Bid%>"
                       

                        <c:forEach var="pull" items="${cardset}">

                            <input type="submit" value="${pull.cardId}" name="cardId" />${pull.cardName} <br>

                        </c:forEach>
                     
                     </form>
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



            .
    
         
   
