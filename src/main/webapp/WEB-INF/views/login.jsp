<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Logging to Movie Service</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous" />
</head>
<body>
<div class="container-fluid text-center">
    <form th:action="@{/login}" method="post" style="max-width: 350px; margin: 0 auto;">

        <div th:if="${param.error}">
            <p class="text-danger">[[${session.SPRING_SECURITY_LAST_EXCEPTION.message}]]</p>
        </div>

        <div th:if="${param.logout}">
            <p class="text-warning">You have been logged out.</p>
        </div>

        <div class="border border-secondary p-3 rounded">
            <p>Access to Movie Service</p>
            <p>
                <input type="email" name="email" class="form-control" placeholder="E-mail" required autofocus/>
            </p>
            <p>
                <input type="password" name="password" class="form-control" placeholder="Password" required />
            </p>
            <p>
                <input type="checkbox" name="remember-me" />&nbsp;Remember Me
            </p>
            <p>
                <input type="submit" value="Login" class="btn btn-primary" />
            </p>
        </div>
    </form>
</div>
</body>
</html>