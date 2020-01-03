<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="puts" uri="/WEB-INF/tlds/PutsTLD.tld" %>
<%response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
response.setHeader("Pragma","no-cache");%>
<timeline>
     <c:forEach var="puts" items="${requestScope.putsMessages}">
         <puts>
            <user-id>${puts.userId}</user-id>
            <user-name><puts:GetName userId="${puts.userId}" /></user-name>
            <user-img>images/avatar/<puts:GetAvatar userId="${puts.userId}" /></user-img>
            <puts-id>${puts.putsId}</puts-id>
            <puts-time>${puts.date}</puts-time>
            <message><![CDATA[${puts.putsMessage}]]></message>
            <c:choose>
                <c:when test="${puts.userId==sessionScope.player.id}">
                    <isown>yes</isown>
                </c:when>
                <c:otherwise>
                    <isown>no</isown>
                </c:otherwise>
            </c:choose>
	</puts>
    </c:forEach>
    <c:forEach var="friend" items="${requestScope.friends}">
        <friend>
            <friend-id>${friend.id}</friend-id>
            <friend-name>${friend.name}</friend-name>
            <friend-img>images/avatar/${friend.avatar}</friend-img>
        </friend>
    </c:forEach>
</timeline>