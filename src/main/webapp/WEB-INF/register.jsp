<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>iSport</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style1.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
</head>
<body class="container" style="background: #037759">
<div style="font-weight: 600; width: 1000px; border: 3px solid black; background: #ebd402; margin-top: 10px; margin-left: 60px; padding: 20px; font-size: 18px">
    <div class="text-center"><img src="/images/iSport_logo.png" style=" width: 140px; height: 160px; border: 3px solid black;"/></div>
    <h2 class="text-center" style="font-size: 24px; font-weight: bold">Free Pickup Game Finder and Organizer</h2>

    <div class="form-register" style="border: 2px solid">
        <%--@elvariable id="user" type="user"--%>
            <form:form action="/register" method="post" modelAttribute="user">
                <h2 class="text-decoration-underline text-center" style="font-weight: 600">Register</h2>
                <div class="d-flex align-content-center">
                    <form:label path="firstName" class="col-sm-5 col-form-label">First Name: </form:label>
                    <form:input path="firstName" class="form-control"/>
                </div>
                <div class="d-flex align-content-center"><form:errors path="firstName" class="text-danger"/></div>

                <div class="d-flex align-content-center">
                    <form:label path="lastName" class="col-sm-5 col-form-label">Last Name: </form:label>
                    <form:input path="lastName" class="form-control"/>
                </div>
                <div class="d-flex align-content-center"><form:errors path="lastName" class="text-danger"/></div>


                <div class="d-flex align-content-center">
                    <form:label path="email" class="col-sm-5 col-form-label">Email: </form:label>
                    <form:input path="email" class="form-control"/>
                </div>
                <div class="d-flex align-content-center"><form:errors path="email" class="text-danger"/></div>


                <div class="d-flex align-content-center">
                    <form:label path="password" class="col-sm-5 col-form-label">Password: </form:label>
                    <form:input path="password" type="password" class="form-control"/>
                </div>
                <div class="d-flex align-content-center"><form:errors path="password" class="text-danger"/></div>


                <div class="d-flex align-content-center">
                    <form:label path="confirm" class="col-sm-5 col-form-label">Confirm Password: </form:label>
                    <form:input path="confirm" type="password" class="form-control"/>
                </div>

                <div class="d-flex align-content-center">
                    <form:label path="birthdate" class="col-sm-5 col-form-label">Birthdate: </form:label>
                    <form:input path="birthdate" type="date" class="form-control"/>
                </div>
                <div class="d-flex align-content-center"><form:errors path="birthdate" class="text-danger"/></div>

                <button class="btn btn-primary" style="font-size: 18px; color: white; width: 280px; margin-left: 215px">Register</button>
            </form:form>
        </div>
</div>
<footer class="text-center text-white m-1 text-decoration-underline">Copyright © 2022 - Eldorado Agalliu</footer>
</body>
</html>