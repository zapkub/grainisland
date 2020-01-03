<%@ page isErrorPage="true" import="java.io.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Error</title>
        <link type="text/css" href="css/dark-hive/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <style type="text/css">
            .workingArea{ width: 1020px; height: 760px; top:  8px;    margin: auto; background-color: black;}
          
            .showError{ width: 500px; height: 80px; top: 0px; margin: auto;}
            .molex{ width: 400px; height: 400px; top:90px; margin: auto;    left: 270px; background-color: #5c5c5c;}
            .puts{      width: 350px; height: 500px;   top:  0px; float:left; margin: auto; background:  #3399FF; position:relative ;float:left }
            
}
        </style>
    </head>
    <body bgcolor="black">
        <div class="workingArea">
                <div id="showError" >
                    <font color="white">
                       <left> <%= exception.getMessage() %><br></left>
                    </font>
                </div>
                <div id="molex">
                    <img align="center" src="images/npcs/1/npc_full_size/molex.png">
                   
                </div>
                <center><a href="index.jsp">กลับไปหน้าแรก</a></center>
            </div>
        
    </body>
</html>