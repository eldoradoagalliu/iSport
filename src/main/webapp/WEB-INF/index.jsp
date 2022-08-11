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
<div style="font-weight: 600; width: 1000px; border: 3px solid black; background: #ebd402; margin-top: 20px; margin-left: 60px; padding: 20px; font-size: 20px">
    <div class="text-center"><img src="/images/iSport_logo.png" style=" width: 160px; height: 180px; border: 3px solid black;"/></div>
    <h2 class="text-center" style="font-size: 24px; font-weight: bold">Free Pickup Game Finder and Organizer</h2>
    <div class="d-flex align-content-center justify-content-evenly" style="margin-top: 10px">
        <div style="width: 500px; font-weight: 600; margin-left: 20px; margin-top: 13px">
            <h2 class="text-decoration-underline text-center" style="font-weight: 600">Register</h2>
            <%--@elvariable id="newUser" type="newUser"--%>
            <form:form action="/register" method="post" modelAttribute="newUser">
                <div>
                    <form:label path="firstName">First Name: </form:label>
                    <form:input path="firstName" style="margin-left: 40px"/>
                    <div><form:errors path="firstName" class="text-danger"/></div>
                </div>

                <div style="margin-top: 10px">
                    <form:label path="lastName">Last Name: </form:label>
                    <form:input path="lastName" style="margin-left: 40px"/>
                    <div><form:errors path="lastName" class="text-danger"/></div>
                </div>

                <div style="margin-top: 10px">
                    <form:label path="email">Email: </form:label>
                    <form:input path="email" style="margin-left: 95px"/>
                    <div><form:errors path="email" class="text-danger"/></div>
                </div>

                <div style="margin-top: 10px">
                    <form:label path="password">Password: </form:label>
                    <form:input type="password" path="password" style="margin-left: 55px"/>
                    <div><form:errors path="password" class="text-danger"/></div>
                </div>

                <div style="margin-top: 10px">
                    <form:label path="confirm">Confirm PW: </form:label>
                    <form:input type="password" path="confirm" style="margin-left: 38px"/>
                    <div><form:errors path="confirm" class="text-danger"/></div>
                </div>

                <div style="margin-top: 10px">
                    <form:label path="birthdate">Birthdate: </form:label>
                    <form:input type="date" path="birthdate" max='2010-01-01' style="margin-left: 65px"/>
                    <div><form:errors path="birthdate" class="text-danger"/></div>
                </div>

                <div style="margin-left: 270px"><button style="box-shadow: 1px 1px black; border: 2px solid black; font-size: 18px; font-weight: 600; color: white; padding: 3px 20px; background: #037759" class="btn">Register</button></div>
            </form:form>
        </div>

        <div style="width: 500px; font-weight: 600; margin-left: 10px">
            <%--@elvariable id="newLogin" type="newLogin"--%>
            <form:form action="/login" method="post" modelAttribute="newLogin">
                <h2 class="text-decoration-underline text-center" style="font-weight: 600">Login</h2>
                <div style="margin-top: 10px">
                    <form:label path="email">Email: </form:label>
                    <form:input path="email" style="margin-left: 75px"/>
                    <div><form:errors path="email" class="text-danger"/></div>
                </div>

                <div style="margin-top: 10px">
                    <form:label path="password">Password: </form:label>
                    <form:input type="password" path="password" style="margin-left: 35px"/>
                    <div><form:errors path="password" class="text-danger"/></div>
                </div>

                <div style="margin-left: 270px"><button style="box-shadow: 1px 1px black; border: 2px solid black; font-size: 18px; font-weight: 600; color: white; padding: 3px 20px; background: #037759" class="btn">Login</button></div>
            </form:form>
        </div>
    </div>
</div>
<footer class="text-center text-white m-1 text-decoration-underline">Copyright © 2022 - Eldorado Agalliu</footer>
</body>
</html>