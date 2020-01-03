<%-- 
    Document   : DuelResult
    Created on : 16 ก.ย. 2553, 19:15:29
    Author     : Zapdos
--%>


<%@page import="sit.int303.grianisland.game.model.Card"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>


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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add battle</title>
    </head>




    <body>

        <div class="workingArea">
            <div class="headerInc">
                <%@include file="Header.jsp" %>
            </div>

            <div id="game" class="game">
                <center>



            <h1>Dual Result !?</h1>

            <h1>${result}</h1>
            <br>

            <jsp:useBean id="uscard" scope="request" class="String" />
            <jsp:useBean id="opcard" scope="request" class="String" />



            <% Card card = new Card();
                        Card us = card.getCard(Integer.parseInt(uscard));
                        Card op = card.getCard(Integer.parseInt(opcard));%>

                        <table border="0">

                            <tbody>
                                <tr>
                                    <td> <img src="images/cards/resize/${uscard}.jpg" alt="card" style="float:left"width="150" height="200" /></td>
                                    <td><img src="images/cards/resize/${opcard}.jpg" alt="card" style="float:right" width="150" height="200"/></td>
                                </tr>
                            </tbody>
                        </table>


            Your card is : ${uscard} <%= us.getCardName()%> DEF : <%=us.getDEF()%>
               
            <br>

            vs Opponent card is : ${opcard}   <%= op.getCardName()%> ATK : <%=op.getATK()%>
            
            <br>
            <a href="/GrianIsland/Game.jsp"> back</a>


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
