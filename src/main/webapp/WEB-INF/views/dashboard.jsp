<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/navbar-style.css">
    <link rel="stylesheet" type="text/css" href="/css/dashboard-style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark nav-border">
    <div class="container-fluid">
        <a class="navbar-brand logo-font" href="/dashboard">iSport</a>
        <div>
            <c:if test="${currentUser.isAdmin()}">
                <a class="navbar-brand m-2" href="/">Admin Dashboard</a>
            </c:if>
            <button class="btn btn-danger notification-button">
                <span class="badge badge-light">
                    <c:choose>
                        <c:when test="${numberOfMessages == null}">0</c:when>
                        <c:otherwise><c:out value="${numberOfMessages}"/></c:otherwise>
                    </c:choose>
                </span>
            </button>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
        <div class="navbar-dropdown collapse bg-dark p-4" id="navbarToggleExternalContent">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/account/${currentUser.id}" tabindex="-1">
                        <img src="/images/person-circle.svg" alt="Person icon"/> Account
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/event">New sport event</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/search">Search event</a>
                </li>
                <li class="nav-item">
                    <form id="logoutForm" method="post" action="/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="logout-button float-end">Logout</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="main-part">
    <h1 class="welcome-underline">Welcome ${currentUser.firstName}</h1>
    <p class="info-font">Today is ${todayDate} and you have ${numberOfEvents}
        <c:choose>
            <c:when test="${numberOfEvents == 0}">events today.</c:when>
            <c:when test="${numberOfEvents == 1}">event today:</c:when>
            <c:when test="${numberOfEvents != 1 && numberOfEvents > 0}">events today:</c:when>
        </c:choose>
    </p>
    <c:if test="${!todayEvents.isEmpty()}">
        <table class="table table-success table-striped table-hover">
            <thead>
            <th>Event Name</th>
            <th>Location</th>
            <th>Attendees</th>
            <th>Event Time</th>
            </thead>
            <c:forEach var="event" items="${todayEvents}">
                <tbody>
                <tr>
                    <td><a href="/event/${event.id}" class="table-link">${event.eventName}</a></td>
                    <td>${event.location}</td>
                    <td>${event.getNumberOfAttenders()}/${event.attendees}</td>
                    <td>${event.getFormattedTime()}</td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${joinedEvents.isEmpty()}">
        <p class="info-font">You haven't joined any event yet. It seems that you are getting lazy...
            <img src="/images/emoji-expressionless-fill.svg" alt="Expressionless emoji"/>
        </p>
    </c:if>
    <c:if test="${!joinedEvents.isEmpty()}">
        <p class="info-font">Here are your joined future events:</p>
        <table class="table table-success table-striped table-hover">
            <thead>
            <th>Event Name</th>
            <th>Location</th>
            <th>Attendees</th>
            <th>Event Time & Date</th>
            </thead>
            <c:forEach var="event" items="${joinedEvents}">
                <tbody>
                <tr>
                    <td><a href="/event/${event.id}" class="table-link">${event.eventName}</a></td>
                    <td>${event.location}</td>
                    <td>${event.getNumberOfAttenders()}/${event.attendees}</td>
                    <td>${event.getFormattedDateTime()}</td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </c:if>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script src="/webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>