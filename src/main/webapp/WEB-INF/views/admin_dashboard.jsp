<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/admin-dashboard-style.css">
</head>
<body class="container">
<h1 class="text-center text-u">Admin Dashboard</h1>
<div class="d-flex justify-content-end mt-5">
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-primary logout">Logout</button>
    </form>
</div>
<c:if test="${!users.isEmpty()}">
    <h2>Users on the iSport Application</h2>
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
                <td><a href="/account/${user.id}" class="link">${user.getFullName()}</a></td>
                <td>${user.email}</td>
                <td>${user.getFormattedBirthdate()}</td>
                <td class="d-flex align-content-center">
                    <form action="/account/${user.id}/edit">
                        <button class="btn btn-warning">Edit</button>
                    </form>
                    <form:form action="/account/${user.id}" method="post">
                        <input type="hidden" name="_method" value="delete">
                        <button class="btn btn-danger">Delete</button>
                    </form:form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>
<c:if test="${!events.isEmpty()}">
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
                <td><a href="/event/${event.id}" class="link">${event.eventName}</a></td>
                <td>${event.location}</td>
                <td>${event.getNumberOfAttenders()}</td>
                <td>${event.getFormattedDateTime()}</td>
                <td class="d-flex align-content-center">
                    <form action="event/${event.id}/edit">
                        <button class="btn btn-warning">Edit</button>
                    </form>
                    <form:form action="/event/${event.id}" method="post">
                        <input type="hidden" name="_method" value="delete">
                        <button class="btn btn-danger">Delete</button>
                    </form:form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
</body>
</html>