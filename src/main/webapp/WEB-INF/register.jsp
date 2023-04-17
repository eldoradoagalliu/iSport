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

    <div class="form-register">
        <%--@elvariable id="user" type="user"--%>
        <form:form action="/register" method="post" modelAttribute="user">
            <h2 class="text-decoration-underline text-center mb-3 register">Register</h2>
            <div class="d-flex align-content-center m-1">
                <form:label path="firstName" class="col-sm-5 col-form-label">First Name: </form:label>
                <form:input path="firstName" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="firstName" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1">
                <form:label path="lastName" class="col-sm-5 col-form-label">Last Name: </form:label>
                <form:input path="lastName" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="lastName" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1">
                <form:label path="email" class="col-sm-5 col-form-label">Email: </form:label>
                <form:input path="email" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="email" class="text-danger"/></div>
            <c:if test="${emailExistsErrorMessage != null}">
                <div class="text-danger m-1"><c:out value="${emailExistsErrorMessage}"></c:out></div>
            </c:if>

            <div class="d-flex align-content-center m-1">
                <form:label path="password" class="col-sm-5 col-form-label">Password: </form:label>
                <form:input path="password" type="password" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="password" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1 mb-2">
                <form:label path="confirm" class="col-sm-5 col-form-label">Confirm Password: </form:label>
                <form:input path="confirm" type="password" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="confirm" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1">
                <form:label path="birthdate" class="col-sm-5 col-form-label">Birthdate: </form:label>
                <form:input path="birthdate" type="date" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="birthdate" class="text-danger"/></div>

            <button class="btn btn-primary register-button mt-2">Register</button>
        </form:form>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
</body>
</html>