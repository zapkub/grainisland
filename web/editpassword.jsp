<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%--
    Document   : Game
    Created on : 9 ส.ค. 2553, 21:25:51
    Author     : Vable
--%>
<%@page import="sit.int303.grianisland.core.model.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>:: game play ::</title>
        <link type="text/css" href="css/dark-hive/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <style type="text/css">
            .workingArea{ width: 1020px; height: 760px; top:  8px;    margin: auto; background-color: red;}
            .headerInc{ width: 1020px; height: 160px;   top:  0px; position:relative ;float:left  ;margin: auto; background: grey;}
            .game{      width: 670px; height: 500px;   top:  0px; float:left; margin: auto; background: seashell ; position:relative ;float:left }
            .puts{      width: 350px; height: 500px;   top:  0px; float:left; margin: auto; background:  #3399FF; position:relative ;float:left }
            .footerInc{ width: 1020px; height: 100px;   top:  0px;  margin: auto; background: grey;        position:relative ;float:left }
        body {
	background-color: #000;
}
        </style>
        <script type="text/javascript" language="JavaScript">
            function check()
            {
                var ext = document.upform.uploadfile.value;
                ext = ext.substring(ext.length-3,ext.length);
                ext = ext.toLowerCase();
                if(ext == 'jpg' || ext == 'png' || ext == 'gif')
                {
                    return true;
                }
                else
                {
                    alert('You selected a .'+ext+' file; please select a .jpg or .png or .gif file instead!');
                    return false;
                }
            }
        </script>

    </head>
    <body>
        <div class="workingArea">
            <div class="headerInc">
                <%@include file="Header.jsp" %>
            </div>
            <div id="game" class="game">
                <h1><img src="images/editprofile.png" width="341" height="79" alt="editprofile"/>
                </h1><br><br />
                <div style="width: 335px;height: 500;float: left">
                    <form method="POST" action="EditProfile" style="margin-left: 20px">
                        <table border="0">
                            <tr>
                            <img src="images/changepassword.png" width="219" height="31" alt="changepassword"/>

                                <td>
                                   Old Password
                                </td>
                                <td>
                                    <input type="password" name="oldpassword" value="" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    New Password
                                </td>
                                <td>
                                    <input type="password" name="newpassword" value="" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Confirm Password
                                </td>
                                <td>
                                    <input type="password" name="newpassagn" value="" />
                                </td>
                            </tr>
                        </table>
                        <center>&nbsp;&nbsp; <input type="submit" value="OK" /></center>
                    </form>
                    <h1 style="color: red">${message}</h1>
                </div>
                <div style="width: 335px;height: 500;float: left;text-align: center">
                    <img src="images/avatar/<puts:GetAvatar userId="${player.id}"  />" width="150px" /><br />
                    <form method="post" action="ChangeAvatar" name="upform" enctype="multipart/form-data" onsubmit="return check();">
                        <input type="file" name="uploadfile" size="25"><br /><br />
                        <input type="hidden" name="todo" value="upload">
                        <input type="submit" name="Submit" value="Upload">
                        <input type="reset" name="Reset" value="Cancel">
                    </form>
                </div>
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
