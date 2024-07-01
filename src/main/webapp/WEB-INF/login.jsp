<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>iSport</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="main-part">
    <div class="text-center"><img src="/images/iSport_logo.png" class="logo p-1"/></div>
    <h2 class="text-center isport-title m-2">Free Pickup Game Finder and Organizer</h2>

    <div class="form-login">
        <%--@elvariable id="user" type="user"--%>
        <form:form action="/login" method="post" modelAttribute="user">
            <h2 class="text-decoration-underline text-center mb-3 login">Login</h2>
            <c:if test="${logoutMessage != null}">
                <div class="text-success text-center fw-bolder m-3"><h5><c:out value="${logoutMessage}"></c:out></h5>
                </div>
            </c:if>
            <c:if test="${errorMessage != null}">
                <div class="text-danger text-center fw-bold m-3"><h5><c:out value="${errorMessage}"></c:out></h5></div>
            </c:if>
            <div class="mb-3 d-flex align-content-center">
                <form:label path="email" class="col-sm-4 col-form-label">Email: </form:label>
                <form:input path="email" class="form-control input-font" required="required"/>
            </div>

            <div class="mb-3 d-flex align-content-center">
                <form:label path="password" class="col-sm-4 col-form-label">Password: </form:label>
                <form:input path="password" type="password" class="form-control input-font" required="required"/>
            </div>

            <button class="btn btn-success login-button col-sm-4">Login</button>
            <div class="mt-2 text-center">Don't have an account?<a href="/register" class="register-link">Register
                here</a></div>
        </form:form>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
</body>
</html>