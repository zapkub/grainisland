<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="puts" uri="/WEB-INF/tlds/PutsTLD.tld" %>
<%response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
response.setHeader("Pragma","no-cache");%>
<timeline>
    <user>
        <c:choose>
            <c:when test="${param.id==-2 or param.id==sessionScope.player.id}">
                <user-id>${sessionScope.player.id}</user-id>
                <user-name>${sessionScope.player.name}</user-name>
                <user-img>images/avatar/${sessionScope.player.avatar}</user-img>
                <isown>yes</isown>
                <request-frined>2</request-frined>
            </c:when>
            <c:otherwise>
                <user-id>${param.id}</user-id>
                <user-name><puts:GetName userId="${param.id}" /></user-name>
                <user-img>images/avatar/<puts:GetAvatar userId="${param.id}" /></user-img>
                <isown>no</isown>
                <request-frined><puts:inRequestFriend player="${player}" targetId="${param.id}" /></request-frined>
            </c:otherwise>
        </c:choose>
    </user>
    <c:forEach var="puts" items="${requestScope.putsMessages}">
        <puts>
           <puts-id>${puts.putsId}</puts-id>
           <puts-time>${puts.date}</puts-time>
           <message><![CDATA[${puts.putsMessage}]]></message>
	</puts>
    </c:forEach>
</timeline>