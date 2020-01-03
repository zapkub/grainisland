<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%-- 
    Document   : ChooseClan
    Created on : 9 ส.ค. 2553, 21:25:26
    Author     : Vable
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- import javascript and CSS-->
        <link type="text/css" href="css/dark-hive/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <title>::: choose Clan :::</title>
    <style type="text/css">
		.workingArea { width: 1020px; height: 760px; margin: auto; background-color: black; }
		#button { padding: .5em 1em; text-decoration: none; }
		#effect { width: 240px; height: 135px; padding: 0.4em; position: relative; }
		#effect h3 { margin: 0; padding: 0.4em; text-align: center; }
                #contentText{ font-family:fantasy; font-size: xx-large; font-weight: bolder;}
                #dialog-confirm{ width: 350px; height: 200px;}
    </style>
    <script type="text/javascript">
            function chooseClan(id)
            {
                $.get('/GrianIsland/ChooseClan?id='+id, function(data) {
                    if(data.toString()=="ok")
                    {
                        window.location = "Game.jsp";
                    }
                    else
                    {
                        alert(data);
                    }
                });
            }
        </script>
    </head>

    <body bgcolor="black">
        
        <div class="workingArea">
            <table border="0px">
                <tr>
                    <td colspan="3">
                        <img src="images/chooseclan/clanheader.gif" alt="" name ="clanHeader" id="clanHeader"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="images/chooseclan/cha_dog_full.jpg" alt="the dog clan" id="dog" onclick="if(confirm('คุณต้องการเข้ากลุ่มหมาใช่ไหม?'))chooseClan(2)" >
                        
                    </td>
                    <td>
                        <img src="images/chooseclan/cha_cat_full.jpg" alt="the cat clan" id="cat" onclick="if(confirm('คุณต้องการเข้ากลุ่มแมวใช่ไหม?'))chooseClan(3)">
                        
                    </td>
                    <td>
                        <img src="images/chooseclan/cha_rat_full.jpg" alt="the rat clan" id="rat" onclick="if(confirm('คุณต้องการเข้ากลุ่มหนูใช่ไหม?'))chooseClan(4)">
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <img src="images/chooseclan/footer.png" alt="" name ="footer" id="clanFooter"/>
                    </td>
               </tr>
            </table>
        </div>
    </body>
</html>


<!-- fixing code -->
<!-- Dialog Box Script for dog clan
       <script type="text/javascript">

    $(function() {
        var showPic = { to: {width: 480,height: 750} };
        var showName = { to: {width: 450,height: 260} };

        //stop showing dialog
        $("#dog-dialog-confirm").dialog({autoOpen: false});
        // event when you click on dog picture
        $("#dog").click(function() {
            $("#dog-dialog-confirm").dialog({autoOpen: true});
        });
        // status of dialog
		$("#dog-dialog-confirm").dialog({
			resizable: true,
            width:400,
			height:200,
			modal: true,
			buttons: {
                ok: function() {
                    alert("before");
                      chooseClan(2);
                       alert("after");

                        // send id = 2 to game.jsp chooseClan(2);
                     },
				Cancel: function() {
					$(this).dialog('close');
				}
			}
		});
	});
	</script> -->
        <!-- End Dialog Box Script for dog clan -->
        <!-- Preparing dog dialog box
             <div id="dog-dialog-confirm" title="Dog clan ?">
                 <p style="width: auto; height: auto; position: relative;">
                      <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
                      Are you sure to choose dog clan ?
                  </p>
             </div> -->
        <!-- End Preparing dog dialog box -->