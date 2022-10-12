<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<body>
<h2>Welcome to movie-service</h2>

<h3>Hello, ${username}</h3>

<c:url value="/logout" var="logoutUrl"/>
<sf:form action="${logoutUrl}" method="post">
    <input type="submit" value="Logout">
</sf:form>
</body>
</html>