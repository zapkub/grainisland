<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/demo.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="css/screen.css" media="all" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <script type="text/javascript" src="js/execute.js"></script>
        <script type="text/javascript" src="js/jquery.corner.js"></script>
        <style type="text/css">
            .clear  {
                clear:  both;
            }
        </style>
        <script type="text/javascript" charset="UTF-8">
            var command;
            var param;
            var xmlData;
            var area;
            timedCount();
            function timedCount()
            {
                doCommand();
                t=setTimeout("timedCount()",5000);
            }
            function doCommand()
            {
                if(command==searchPuts)
                {
                    searchPuts();
                }
                else if(command==loadFriend)
                {
                    loadFriend();
                }
                else if(command==getPublicTimeline)
                {
                    getPublicTimeline();
                }
                else if(command==getTimeline)
                {
                    getTimeline(param);
                }
                else if(command==mentioning)
                {
                    mentioning(param);
                }

            }
            function deleteFriend(id)
            {
                $.get("DeleteFriend?id="+id, function(data)
                {
                    if(data.toString()=="ok")
                    {
                        getTimeline(id);
                    }
                    else
                    {
                        alert("การตอบรับล้มเหลว")
                    }
                });
            }
            function acceptFriend(id)
            {
                $.get("AcceptFriend?id="+id, function(data)
                {
                    if(data.toString()=="ok")
                    {
                        getTimeline(id);
                    }
                    else
                    {
                        alert("การตอบรับล้มเหลว")
                    }
                });
            }

            function searchPuts()
            {
                var message = encodeURIComponent($("#keyword").val());
                command=searchPuts;
                $.get("FindPuts?keyword="+message, function(data)
                {
                    xmlData=data;
                    area="#findTimeline";
                    var result=formatPublicPutsXML(data,20);
                    if(result=="")
                        $("#findTimeline").html("ไม่มีข้อความใดที่ตรงตาม keyword");
                    else
                        $("#findTimeline").html(result);
                });
            }

            function loadFriend()
            {
                command=loadFriend;
                $.get("GetFriendList", function(data)
                {
                    var result=formatFriend(data);
                    if(result=="")
                        $("#friendList").html("ยังไม่มีเพื่อน");
                    else
                        $("#friendList").html(result);
                });
            }
            function formatFriend(xmlDoc)
            {
                var requestFriend=xmlDoc.getElementsByTagName("friend-request");
                var inFriend=xmlDoc.getElementsByTagName("in-friend");
                var x=requestFriend[0].getElementsByTagName("friend");
                var friendHtml="";
                for (i=0;i<x.length;i++)
                {
                    friendHtml+="<li>";
                    friendHtml+="<p style='text-align:left;color: #000000'>";
                    friendHtml+="<img alt='User image' src='"
                    friendHtml+=x[i].getElementsByTagName("friend-img")[0].childNodes[0].nodeValue;
                    friendHtml+="' style='float:left;width:50px;height:50px'>";
          
                    friendHtml+="<H1 style='text-align: left' onclick='you("+x[i].getElementsByTagName("friend-id")[0].childNodes[0].nodeValue+")'>";
                    friendHtml+=x[i].getElementsByTagName("friend-name")[0].childNodes[0].nodeValue;
                    friendHtml+="</H1>";

                    friendHtml+="<b onclick='if(confirm(\"คุณต้องการเพิ่ม"+x[i].getElementsByTagName("friend-name")[0].childNodes[0].nodeValue+"เป็นพื่อน?\"))acceptFriend("+x[i].getElementsByTagName("friend-id")[0].childNodes[0].nodeValue+");loadFriend();'>ตอบรับเพื่อน</b>";
                    friendHtml+=" - <b onclick='if(confirm(\"คุณต้องการปฏิเสธ"+x[i].getElementsByTagName("friend-name")[0].childNodes[0].nodeValue+"?\"))deleteFriend("+x[i].getElementsByTagName("friend-id")[0].childNodes[0].nodeValue+");loadFriend();'>ปฏิเสธเพื่อน</b>";

                    friendHtml+="<p class='clear' style='padding-top:5px;'><hr /></p>";
                    friendHtml+="</p>";
                    friendHtml+="</li>";
                }
                x=inFriend[0].getElementsByTagName("friend");
                if(friendHtml!="")
                    friendHtml+="<hr size='5' style='background-color: red' />";
                for (i=0;i<x.length;i++)
                {
                    friendHtml+="<li>";
                    friendHtml+="<p style='text-align:left;color: #000000'>";
                    friendHtml+="<img alt='User image' src='"
                    friendHtml+=x[i].getElementsByTagName("friend-img")[0].childNodes[0].nodeValue;
                    friendHtml+="' style='float:left;width:50px;height:50px'>";

                    friendHtml+="<H1 style='text-align: left' onclick='you("+x[i].getElementsByTagName("friend-id")[0].childNodes[0].nodeValue+")'>";
                    friendHtml+=x[i].getElementsByTagName("friend-name")[0].childNodes[0].nodeValue;
                    friendHtml+="</H1>";

                    friendHtml+="<b onclick='if(confirm(\"คุณต้องการลบ "+x[i].getElementsByTagName("friend-name")[0].childNodes[0].nodeValue+" ออกจากเพื่อน?\"))deleteFriend("+x[i].getElementsByTagName("friend-id")[0].childNodes[0].nodeValue+");loadFriend();'>ลบเพื่อน</b>";

                    friendHtml+="<p class='clear' style='padding-top:5px;'><hr /></p>";
                    friendHtml+="</p>";
                    friendHtml+="</li>";
                }
                return friendHtml;
            }
            function getPublicTimeline()
            {
                command=getPublicTimeline;
                $.get("GetPublicTimeline", function(data)
                {
                    area="#publictimeline";
                    xmlData=data;
                    var result=formatPublicPutsXML(data,20);
                    if(result=="")
                        $("#publictimeline").html("ไม่มีข้อความใดๆ");
                    else
                        $("#publictimeline").html(result);
                });
            }

            function getTimeline(id)
            {
                command=getTimeline;
                param=id;
                $.get("GetTimeline?id="+id, function(data)
                {
                    xmlData=data;
                    var result=formatPutsXML(data,20);
                    if(result=="")
                        $("#timeline").html("ไม่มีข้อความใดๆ");
                    else
                        $("#timeline").html(result);
                });
            }

            function mentioning(id)
            {
                command=mentioning;
                param=id;
                $.get("GetMentioning?id="+id, function(data)
                {
                    xmlData=data;
                    var result=formatPublicPutsXML(data,20);
                    if(result=="")
                        $("#mentioning").html("ไม่มีข้อความใดๆ");
                    else
                        $("#mentioning").html(result);
                });
            }

            function getPublicTimelineAll()
            {
                var result=formatPublicPutsXML(xmlData,-1);
                    if(result=="")
                        $(area).html("ไม่มีข้อความใดๆ");
                    else
                        $(area).html(result);
            }

            function getTimelineAll()
            {
                var result=formatPutsXML(xmlData,-1);
                    if(result=="")
                        $("#timeline").html("ไม่มีข้อความใดๆ");
                    else
                        $("#timeline").html(result);
            }

            function getMentioningAll()
            {
                var result=formatPutsXML(xmlData,-1);
                    if(result=="")
                        $("#mentioning").html("ไม่มีข้อความใดๆ");
                    else
                        $("#mentioning").html(result);
            }
            function deletePuts(putsId)
            {
                $.get("DeletePuts?putsId="+putsId, function(data)
                {

                    if(data.toString()=="ok")
                    {
                        mentioning(-2);
                    }
                    else
                    {
                        alert("can not delete!!");
                    }
                });
            }

            function formatPutsXML(xmlDoc,number)
            {
                var putsHtml="";
                var x=xmlDoc.getElementsByTagName("puts");
                for (i=0;i<x.length;i++)
                {
                    if(i==number)
                    {
                        putsHtml+="<li>";
                        putsHtml+="<p onclick='getTimelineAll()'>more</p>" ;
                        putsHtml+="</li>";
                        break;
                    }
                    else
                    {
                        putsHtml+="<li>";
                        putsHtml+="<p style='text-align:left;color: #000000'>";
                        putsHtml+="<span style='float:left;width:290px;min-height: 50px;height:auto'>";
                        putsHtml+=x[i].getElementsByTagName("message")[0].childNodes[0].nodeValue;
                        if(xmlDoc.getElementsByTagName("isown")[0].childNodes[0].nodeValue=="yes")
                            putsHtml+="<img src='images/puts/delete-icon.png' style='float:right;position:relative;margin-right:0px;margin-top:0px' onclick=\"if(confirm('คุณต้องการลบ "+x[i].getElementsByTagName("message")[0].childNodes[0].nodeValue+" ใช่หรือไม?')){deletePuts("+x[i].getElementsByTagName("puts-id")[0].childNodes[0].nodeValue+");getTimeline(-2);}\" />";
                        putsHtml+="<br /><br />";
                        putsHtml+="<font color='gray'>"+x[i].getElementsByTagName("puts-time")[0].childNodes[0].nodeValue+"</font>";
                        putsHtml+="</span>";
                        putsHtml+="<p class='clear' style='padding-top:5px;'><hr /></p>";
                        putsHtml+="</p>";
                        putsHtml+="</li>";
                    }
                }

                $("#targetPic").attr("src", xmlDoc.getElementsByTagName("user-img")[0].childNodes[0].nodeValue);
                $("#targetName").html(xmlDoc.getElementsByTagName("user-name")[0].childNodes[0].nodeValue);
                var isFriend=xmlDoc.getElementsByTagName("request-frined")[0].childNodes[0].nodeValue;
                var sriendString="";
                //เป็นเพื่อน return 0 ร้องขอไป return 1 ต้องตอบรับ return -1 ไม่เป็น return -2 ตัวเอง return 2
                if(isFriend==-2)
                {
                    sriendString="<p onclick='if(confirm(\"คุณต้องการเพิ่ม"+xmlDoc.getElementsByTagName("user-name")[0].childNodes[0].nodeValue+"เป็นพื่อน?\"))acceptFriend("+xmlDoc.getElementsByTagName("user-id")[0].childNodes[0].nodeValue+")'>เพิ่มเป็นเพื่อนน</p>";
                }
                else if(isFriend==-1)
                {
                    sriendString="<b onclick='if(confirm(\"คุณต้องการเพิ่ม"+xmlDoc.getElementsByTagName("user-name")[0].childNodes[0].nodeValue+"เป็นพื่อน?\"))acceptFriend("+xmlDoc.getElementsByTagName("user-id")[0].childNodes[0].nodeValue+")'>ตอบรับเพื่อน</b>";
                    sriendString+=" - <b onclick='if(confirm(\"คุณต้องการปฏิเสธ"+xmlDoc.getElementsByTagName("user-name")[0].childNodes[0].nodeValue+"?\"))deleteFriend("+xmlDoc.getElementsByTagName("user-id")[0].childNodes[0].nodeValue+")'>ปฏิเสธเพื่อน</b>";
                }
                else if(isFriend==0)
                {
                    sriendString="<p onclick='if(confirm(\"คุณต้องการลบ"+xmlDoc.getElementsByTagName("user-name")[0].childNodes[0].nodeValue+"ออกจากพื่อน?\"))deleteFriend("+xmlDoc.getElementsByTagName("user-id")[0].childNodes[0].nodeValue+")'>ลบออกจากเพื่อน</p>";
                }
                else if(isFriend==1)
                {
                    sriendString="รอการตอบรับเพื่อน";
                }
                else if(isFriend==2)
                {
                    sriendString="ตัวคุณเอง";
                }
                $("#isFriend").html(sriendString);
                return putsHtml;
            }

            function formatPublicPutsXML(xmlDoc,number)
            {
                var putsHtml="";
                var x=xmlDoc.getElementsByTagName("friend");
                for (i=0;i<x.length;i++)
                {
                    putsHtml+="<li>";
                    putsHtml+="<p style='text-align:left;color: #000000'>";
                    putsHtml+="<img alt='User image' src='"
                    putsHtml+=x[i].getElementsByTagName("friend-img")[0].childNodes[0].nodeValue;
                    putsHtml+="' style='float:left;width:50px;height:50px'>";

                    putsHtml+="<H1 style='text-align: left' onclick='you("+x[i].getElementsByTagName("friend-id")[0].childNodes[0].nodeValue+")'>";
                    putsHtml+=x[i].getElementsByTagName("friend-name")[0].childNodes[0].nodeValue;
                    putsHtml+="</H1>";

                    putsHtml+="<p class='clear' style='padding-top:5px;'><hr /></p>";
                    putsHtml+="</p>";
                    putsHtml+="</li>";
                }
                if(putsHtml!="")
                    putsHtml+="<hr size='5' style='background-color: red' />";
                x=xmlDoc.getElementsByTagName("puts");
                for (i=0;i<x.length;i++)
                {
                    if(i==number)
                    {
                        putsHtml+="<li>";
                        putsHtml+="<p onclick='getPublicTimelineAll()'>more</p>" ;
                        putsHtml+="</li>";
                        break;
                    }
                    else
                    {
                        putsHtml+="<li>";
                        putsHtml+="<p style='text-align:left;color: #000000'>";
                        putsHtml+="<img alt='User image' src='"
                        putsHtml+=x[i].getElementsByTagName("user-img")[0].childNodes[0].nodeValue;
                        putsHtml+="' style='float:left;width:50px;height:50px'>";
                        putsHtml+="<span style='float:left;width:240px;min-height: 50px;height:auto'>";
                        putsHtml+="<b onclick='you("+x[i].getElementsByTagName("user-id")[0].childNodes[0].nodeValue+")'>";
                        putsHtml+=x[i].getElementsByTagName("user-name")[0].childNodes[0].nodeValue;
                        putsHtml+="</b>";
                        putsHtml+=" : ";
                        putsHtml+=x[i].getElementsByTagName("message")[0].childNodes[0].nodeValue;
                        if(x[i].getElementsByTagName("isown")[0].childNodes[0].nodeValue=="yes")
                            putsHtml+="<img src='images/puts/delete-icon.png' style='float:right;position:relative;margin-right:0px;margin-top:0px' onclick=\"if(confirm('คุณต้องการลบ "+x[i].getElementsByTagName("message")[0].childNodes[0].nodeValue+" ใช่หรือไม?')){deletePuts("+x[i].getElementsByTagName("puts-id")[0].childNodes[0].nodeValue+");getPublicTimeline();}\" />";
                        putsHtml+="<br /><br />";
                        putsHtml+="<font color='gray'>"+x[i].getElementsByTagName("puts-time")[0].childNodes[0].nodeValue+"</font>";
                        putsHtml+="</span>";
                        putsHtml+="<p class='clear' style='padding-top:5px;'><hr /></p>";
                        putsHtml+="</p>";
                        putsHtml+="</li>";
                    }
                }
                return putsHtml;
            }

            function countLetter()
            {
                var count =250-$("#messageBox").val().length;
                $("#count").html(count);
                if(count<0 || count>=250)
                {
                    $("#puts").attr('disabled', 'disabled');
                }
                else
                {
                    $("#puts").removeAttr('disabled');
                }
            }
            function home()
            {
                $("#you").hide();
                $("#mention").hide();
                $("#friend").hide();
                $("#search").hide();
                $("#home").show();
                getPublicTimeline();
            }
            function you(id)
            {
                $("#home").hide();
                $("#mention").hide();
                $("#friend").hide();
                $("#search").hide();
                $("#you").show();
                getTimeline(id);
            }
            function mention(id)
            {
                $("#home").hide();
                $("#friend").hide();
                $("#search").hide();
                $("#you").hide();
                $("#mention").show();
                mentioning(id);
            }
            function friend()
            {
                $("#home").hide();
                $("#search").hide();
                $("#you").hide();
                $("#mention").hide();
                $("#friend").show();
                loadFriend();
            }
            function searchP()
            {
                $("#home").hide();
                $("#you").hide();
                $("#mention").hide();
                $("#friend").hide();
                $("#search").show();
            }
            $(function() {


                $("#messageBox").keyup(
                    function()
                    {
                        countLetter();
                    }
                );
                $("#messageBox").keydown(
                    function()
                    {
                        countLetter();
                    }
                );
                $("#puts").click(
                    function()
                    {
                        var message = encodeURIComponent($("#messageBox").val());
                        $.get("Puts?message="+message,
                        function()
                        {
                            getPublicTimeline();
                        });
                        $("#messageBox").val("");
                        countLetter();
                    });

                $("#refresh").click(
                    function()
                    {
                        getPublicTimeline();
                    }
                );
            });
            $("#div-1").corner();
        </script>
        <title>JSP Page</title>
    </head>
    <body onload="getPublicTimeline()">
    <div id="div-1" style="padding: 10px; background-color: #26b3f7; height: 480px; width: 330px">

        <!--Public Timeline-->

        <div id="home" style="height: 420px">
            <div id="div-1" style="padding: 10px; background-color: #0972a5; height: 60px; width: 310px;">
                <form action="#" method="POST" name="putsForm">
                    <textarea id="messageBox" cols="20" name="messageBox" rows="4" style="width: 227px; height: 30px;float:left"></textarea>
                    <span style="float:left;position: relative;padding-left: 5px">
                        <h2 id="count" style="color: #ffffff;margin-top: -10px;margin-bottom:5px;position: relative">250</h2>
                        <input id="puts" disabled="disable" style="width: 51px; height: 30px;position: relative" type="button" value="Puts" />
                    </span>
                </form>
            </div>
            <div id="div-1" style="padding: 5px; background-color: #729fcf; height: 20px; width: 310px; margin-top: 10px;">
                <p id="refresh" style="margin-top: 0px" >refresh</p> </div>
            <div id='mycustomscroll' class='flexcroll' style="margin-top: 10px; background-color: aliceblue; height: 290px; width: 330px; text-align: left;overflow:auto">
                    <ol id ="publictimeline" style="padding:5px;margin:0px;list-style:none none;width:300px; height: 50px;">
                    </ol>
            </div>
        </div>

        <!--Timeline-->

        <div id="you" style="display: none;height: 420px;">
            <div id="div-1" style="padding: 10px; background-color: #0972a5; height: 80px;">
                <img alt="Timeline owner picture"  id="targetPic" src="images/puts/You.png" height="80px" width="80px" style="position: relative;float: left">
                <span style="color: #ffffff;margin-left: 10px;position: relative;float: left;">
                    <h1 id="targetName" style="text-align: left;color: #ffffff;margin-bottom: 0px"></h1>
                    <h2 id="isFriend" style="text-align: left;margin-top: 0px"></h2>
                </span>
            </div>
            <div style="margin-top: 10px; background-color: aliceblue;height: 310px;width: 330px; text-align: left;overflow:auto">
                <ol id ="timeline" style="padding:5px;margin:0px;list-style:none none;width:300px; height: 50px;">
                </ol>
            </div>
        </div>

        <!--Mentioning-->

        <div id="mention" style="display: none;height: 420px">
            <div id="div-1" style="padding: 10px; background-color: #0972a5; height: 40px;">
                <h1 id="targetName" style="color: #ffffff;margin-left: 10px;position: relative;float: left">Mentioning ${player.name}</h1>
            </div>
            <div style="margin-top: 10px; background-color: aliceblue;height: 350px;width: 330px; text-align: left;overflow:auto">
                <ol id ="mentioning" style="padding:5px;margin:0px;list-style:none none;width:300px; height: 50px;">
                </ol>
            </div>
        </div>
        <!--Friends-->
        <div id="friend" style="display: none;height: 420px">
            <div id="div-1" style="padding: 10px; background-color: #0972a5; height: 40px;">
                <h1 id="friendTitle" style="color: #ffffff;margin-left: 10px;position: relative;float: left">${player.name}'s friends</h1>
            </div>
            <div style="margin-top: 10px; background-color: aliceblue;height: 350px;width: 330px; text-align: left;overflow:auto">
                <ol id ="friendList" style="padding:5px;margin:0px;list-style:none none;width:300px; height: 50px;">
                </ol>
            </div>
        </div>
        <!--Search-->
        <div id="search" style="display: none;height: 420px">
            <div id="div-1" style="padding: 10px; background-color: #0972a5; height: 40px; width: 310px;">
                <form action="#" method="POST" name="putsForm">
                    <input onkeyup="searchPuts()" type="text" id="keyword" value="" style="float:left;width: 220px;position: relative" />
                    <span style="float:left;position: relative;padding-left: 5px">
                        <input id="searchBT" onclick="searchPuts()" style="width: 60px; height: 35px;position: relative" type="button" value="Search"/>
                    </span>
                </form>
            </div>
            <div id='mycustomscroll' class='flexcroll' style="margin-top: 10px; background-color: aliceblue; height: 350px; width: 330px; text-align: left;overflow:auto">
                    <ol id ="findTimeline" style="padding:5px;margin:0px;list-style:none none;width:300px; height: 50px;">
                    </ol>
            </div>
        </div>

            <ul id="nav-shadow">
                <li class="button-color-1"><a onclick="home()" title="Home">Link Text</a></li>
                <li class="button-color-2"><a onclick="you(-2)" title="You">Link Text</a></li>
                <li class="button-color-3"><a onclick="mention(-2)" title="Mentioning You">Link Text</a></li>
                <li class="button-color-4"><a onclick="friend()" title="friend">Link Text</a></li>
                <li class="button-color-5"><a onclick="searchP()" title="Search">Link Text</a></li>
            </ul>
        </div>
    </body>
</html>
