<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
response.setHeader("Pragma","no-cache");%>
<friends>
    <friend-request>
        <c:forEach var="friend2" items="${requestScope.friendsrequest}">
            <friend>
               <friend-id>${friend2.id}</friend-id>
               <friend-name>${friend2.name}</friend-name>
               <friend-img>images/avatar/${friend2.avatar}</friend-img>
            </friend>
        </c:forEach>
    </friend-request>
    <in-friend>
        <c:forEach var="friend" items="${requestScope.friends}">
            <friend>
               <friend-id>${friend.id}</friend-id>
               <friend-name>${friend.name}</friend-name>
               <friend-img>images/avatar/${friend.avatar}</friend-img>
            </friend>
        </c:forEach>
    </in-friend>
</friends>
