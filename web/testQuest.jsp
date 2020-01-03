<%@page import="java.util.Collection"%>
<%@page import="java.util.HashMap"%>
<%@page import="sit.int303.grianisland.game.model.Npc"%>
<%@page import="sit.int303.grianisland.game.model.Map"%>
<script type="text/javascript">
    var id;
    var name;
    function getEl(){
        var ev = arguments[0] || window.event,
        origEl = ev.target || ev.srcElement;
        id=origEl.id;
        name=origEl.name;
        if(id=="npc")
        {
            clickNpc() ;
        }
    }
    function clickNpc()
    {
        alert("test");
        //alert("/GrianIsland/GetList?id="+name);
        {$.get("/GrianIsland/TalkNpc?npc="+name, function(data) {
                alert(data);
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
    function parseXML(data)
            {
                if (window.DOMParser)
                {
                    parser=new DOMParser();
                    xmlDoc=parser.parseFromString(data,"text/xml");
                }
                else // Internet Explorer
                {
                    xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
                    xmlDoc.async="false";
                    xmlDoc.loadXML(data);
                }
                return xmlDoc;
            }
    document.onclick = getEl;
</script>
<div style="width: 670px;height: 500px;background-color: #ffcccc;background-image: url(images/maps/${map.mapId}.jpg);">
    <div id="message" style="display: none;border-style:  solid; border-color: #000000; border-width: thin; position:relative; left: 10px; top: 330px; width: 644px; height: 136px; background-color:#FFFFFF;z-index:10;">
    </div>
    <jsp:useBean id="map" class="Map" scope="request" />
    <% HashMap<Integer,Npc> npcs=map.getNpcs();
        Collection<Npc> npcsValues=npcs.values();
        for(Npc npc:npcsValues){ %>
        <img alt="" height="79" src="images/npcs/<%=npc.getNpcName() %>.png"  style="position:absolute; left: <%=npc.getPosY() %>px; top:<%=npc.getPosX() %>px;" id="npc" name="<%=npc.getNpcId() %>"/>
    <%}%>
</div>
