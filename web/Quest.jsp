<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.Collection"%>
<%@page import="sit.int303.grianisland.game.model.quest.QuestChoice"%>
<%@page import="java.util.HashMap"%>
<%@page import="sit.int303.grianisland.game.model.quest.QuestMessage"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    QuestMessage message=(QuestMessage)request.getAttribute("message");
    HashMap<Integer,QuestChoice> choices= message.getChoices();
    Collection<QuestChoice> choiceList =choices.values();
%>
<quest>
    <npc-id>${npc}</npc-id>
    <quest-id>${questid}</quest-id>
    <message-id>${message.questMessageId}</message-id>
    <message-text><![CDATA[${message.message}]]></message-text>
    <query-string>${query}</query-string>
    <isquestion>${message.question}</isquestion>
    <choices>
        <% for(QuestChoice choice:choiceList){ %>
            <choice>
                <choice-id><%=choice.getChoiceId() %></choice-id>
                <choice-message><%=choice.getChoiceMessage() %></choice-message>
            </choice>
        <%}%>
    </choices>
        <c:choose>
    <c:when test="${reward!=null}">
        <has-reward>yes</has-reward>
        <reward>
            <reward-img>${reward}</reward-img>
        </reward>
    </c:when>
    <c:otherwise >
        <has-reward>no</has-reward>
    </c:otherwise>
        </c:choose>
</quest>