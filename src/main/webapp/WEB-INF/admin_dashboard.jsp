<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style2.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
</head>
<body class="container">
<h1 class="text-center text-u">Admin Dashboard</h1>
<div class="d-flex align-content-center justify-content-between">
    <h2>Users using the iSport Appliaction</h2>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" class="btn btn-primary" style="color: white; font-weight: 500"/>
    </form>
</div>

<table class="table table-hover table-dark table-striped text-center">
    <thead>
    <th>User Name</th>
    <th>Email</th>
    <th>Birthdate</th>
    <th>Actions</th>
    </thead>

    <c:forEach var="user" items="${users}">
        <tbody>
        <tr>
            <td><a href="/account/${user.id}" style="color: white; text-decoration: none">${user.fullName()}</a></td>
            <td>${user.email}</td>
            <td>${user.birthdateFormatter()}</td>
            <td class="d-flex align-content-center">
                <form action="/account/edit/${user.id}}">
                    <button class="btn btn-secondary">Edit</button>
                </form>
                <form action="/account/delete/${user.id}">
                    <button class="btn btn-danger">Delete</button>
                </form>
            </td>
        </tr>
        </tbody>
    </c:forEach>
</table>

<h2>Events Planned</h2>
<table class="table table-hover table-dark table-striped text-center">
    <thead>
    <th>Event Name</th>
    <th>Location</th>
    <th>Number of Attenders</th>
    <th>Date</th>
    <th>Actions</th>
    </thead>

    <c:forEach var="event" items="${events}">
        <tbody>
        <tr>
            <td><a href="/event/${event.id}" style="color: white; text-decoration: none">${event.eventName}</a></td>
            <td>${event.location}</td>
            <td>${event.getNumberOfAttenders()}</td>
            <td>${event.fullDateFormatter()}</td>
            <td class="d-flex align-content-center">
                <form action="event/edit/${event.id}">
                    <button class="btn btn-secondary">Edit</button>
                </form>
                <form action="/event/delete/${event.id}">
                    <button class="btn btn-danger">Delete</button>
                </form>
            </td>
        </tr>
        </tbody>
    </c:forEach>
</table>
<footer class="text-center text-white m-1 text-decoration-underline">Copyright © 2022 - Eldorado Agalliu</footer>
</body>
</html>