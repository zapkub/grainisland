<%@page import="sit.int303.grianisland.game.model.Building"%>
<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%@page import="java.util.Collection"%>
<%@page import="java.util.HashMap"%>
<%@page import="sit.int303.grianisland.game.model.Npc"%>
<%@page import="sit.int303.grianisland.game.model.Map"%>
<style>
    .choices{
        width: 260;
}
</style>
<script type="text/javascript">
    var className;
    var id;
    var name;
    function getEl(){
        var ev = arguments[0] || window.event,
        origEl = ev.target || ev.srcElement;
        className=origEl.className;;
        id=origEl.id;
        if(className=="npc")
        {
            clickNpc() ;
            name=origEl.name;
        }
        else if(className=="message")
        {
            clickMessage();
        }
        else if(className=="choice")
        {
            clickChoice();
            $(".choices").hide();
        }
    }
    function clickChoice()
    {
        {$.get("TalkNpc?npc="+npcid+"&message="+messageid+"&quest="+questid+"&query="+queryString+"&choice="+id, function(data) {
            $(".message").show(500);
            formatQuestXML(data);
        });}
    }
    function clickMessage()
    {
        {$.get("TalkNpc?npc="+npcid+"&message="+messageid+"&quest="+questid+"&query="+queryString, function(data) {
            $(".message").show(500);
            formatQuestXML(data);
        });}
    }
    function clickNpc()
    {
         {$.get("TalkNpc?npc="+id/*+"&message=2&quest=1"*/, function(data) {
            $(".message").show(500);
            $("#bigNpc").attr("src","images/npcs/${map.mapId}/npc_full_size/"+name+".png" );
            $("#bigNpc").show(500);
            formatQuestXML(data);
        });}
    }
    var npcid;
    var questid;
    var messageid;
    var queryString;
    function battle()
    {
        window.location = "ChooseOpponent.jsp";
    }
    function shop()
    {
        window.location = "CardStore.jsp";
    }
    function card()
    {
        window.location = "ViewCard";
    }
    function closeDialog()
    {
        $(".message").hide();
        $(".choices").hide();
        $("#bigNpc").hide();
    }
    function formatQuestXML(xmlDoc)
    {
        if(xmlDoc!=null && xmlDoc!="")
        {
            npcid=xmlDoc.getElementsByTagName("npc-id")[0].childNodes[0].nodeValue;
            questid=xmlDoc.getElementsByTagName("quest-id")[0].childNodes[0].nodeValue;
            messageid=xmlDoc.getElementsByTagName("message-id")[0].childNodes[0].nodeValue;
            var messagetext="<img onclick='closeDialog();' style=\"float: right;z-index: 11\" src=\"images/closebutton.png\" />"+xmlDoc.getElementsByTagName("message-text")[0].childNodes[0].nodeValue;
            $(".message").html(messagetext);
            queryString=xmlDoc.getElementsByTagName("query-string")[0].childNodes[0].nodeValue;
            var isquestion=xmlDoc.getElementsByTagName("isquestion")[0].childNodes[0].nodeValue;
            if(isquestion=="true")
            {
                $(".choices").show(500);
                var choice=xmlDoc.documentElement.getElementsByTagName("choice");
                var choices="";
                for (i=0;i<choice.length;i++)
                {
                    var choiceid=choice[i].getElementsByTagName("choice-id")[0].childNodes[0].nodeValue;
                    var choicemessage=choice[i].getElementsByTagName("choice-message")[0].childNodes[0].nodeValue;
                    choices+="<p class=\"choice\" id=\""+choiceid+"\">"+choicemessage+"</p><hr />";
                }
                $(".choices").html(choices);
            }
            var hasreward=xmlDoc.getElementsByTagName("has-reward")[0].childNodes[0].nodeValue;
            if(hasreward=="yes")
            {
                var rewardImg=xmlDoc.getElementsByTagName("reward-img")[0].childNodes[0].nodeValue;
                $("#reward").attr("src","images/cards/resize/"+rewardImg+".jpg" );
                $("#reward").show(1000);
            }

        }
        else
        {
            $(".message").hide();
            $("#bigNpc").hide();
        }
    }
    document.onclick = getEl;
</script>
<div style="width: 670px;height: 500px;background-color: #ffcccc;background-image: url(images/maps/${map.mapId}.jpg);">
    <img id="bigNpc"  style="display: none;position:absolute;top:0px;left:0px;z-index: 5" alt="" src="" />
    <div class="message" style="display: none;border-style:  solid; border-color: #000000; border-width: thin; left: 10px; top: 330px; width: 644px; height: 136px; background-color:#FFFFFF;z-index:10;position: absolute;padding-left: 10px;z-index: 10">
    </div>
    <div class="choices" style="overflow:scroll;display: none;border-style:  solid; border-color: #000000; border-width: thin;height: 200px;background-color: #ffffff;right:15px; width: 270px; top: 100px;position: absolute;padding-left: 10px;z-index: 10">
    </div>
    <jsp:useBean id="map" class="Map" scope="request" />
    <% HashMap<Integer,Building> buildings=map.getBuildings();
        Collection<Building> buildingsValues=buildings.values();
        for(Building building:buildingsValues){
            if(building.getRole().equals("shop")){
        %>
            <img onclick="shop()" alt="" src="images/maps/building/map${map.mapId}/<%=building.getBuildingName() %>.png"  style=" left: <%=building.getPosX() %>px; top:<%=building.getPosY() %>px;position: absolute" class="building" id="<%=building.getBuildingId() %>"/>
                   
            <%}else if(building.getRole().equals("card")){%>
            <img onclick="card()" alt="" src="images/maps/building/map${map.mapId}/<%=building.getBuildingName() %>.png"  style=" left: <%=building.getPosX() %>px; top:<%=building.getPosY() %>px;position: absolute" class="building" id="<%=building.getBuildingId() %>"/>
            <%}else if(building.getRole().equals("battle")){%>
            <img onclick="battle()" alt="" src="images/maps/building/map${map.mapId}/<%=building.getBuildingName() %>.png"  style=" left: <%=building.getPosX() %>px; top:<%=building.getPosY() %>px;position: absolute" class="building" id="<%=building.getBuildingId() %>"/>
            <%}else{%>
            <img alt="" src="images/maps/building/map${map.mapId}/<%=building.getBuildingName() %>.png"  style=" left: <%=building.getPosX() %>px; top:<%=building.getPosY() %>px;position: absolute" class="building" id="<%=building.getBuildingId() %>"/>
            <%}%>

    <%}%>
    <% HashMap<Integer,Npc> npcs=map.getNpcs();
        Collection<Npc> npcsValues=npcs.values();
        for(Npc npc:npcsValues){
        %>

        <img name="<%=npc.getNpcName() %>" alt="" src="images/npcs/${map.mapId}/npc_map_size/<%=npc.getNpcName() %>.png"  style=" left: <%=npc.getPosX() %>px; top:<%=npc.getPosY() %>px;position: absolute" class="npc" id="<%=npc.getNpcId() %>"/>
    <%}%>
    <img id="reward" onclick='$("#reward").hide(1000)'  style="display: none;position:absolute;top:20px;left:200px;z-index: 20" alt="" src="" />
</div>
