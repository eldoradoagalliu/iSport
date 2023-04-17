<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/header-style.css">
    <link rel="stylesheet" type="text/css" href="/css/dashboard-style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark nav-border">
    <div class="container-fluid">
        <a class="navbar-brand logo-font" href="/dashboard">iSport</a>
        <div>
            <c:if test="${currentUser.getRoles().get(0).getName().contains('ADMIN')}">
                <a class="navbar-brand m-2" href="/">Admin Dashboard</a>
            </c:if>
            <button class="btn btn-danger notification-button">
                <span class="badge badge-light">
                    <c:if test="${numberOfMessages == null}">0</c:if>
                    <c:if test="${numberOfMessages != null}">
                        <c:out value="${numberOfMessages}"></c:out>
                    </c:if>
                </span>
            </button>
            <button class="btn btn-light navbar-toggler" id="navbarButton">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
        <div class="collapse bg-dark p-4" id="navbarDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/account/${currentUser.id}" tabindex="-1"
                       aria-disabled="true">
                        <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="white"
                             class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                            <path fill-rule="evenodd"
                                  d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                        Account
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/new">Add a new sport event</a>
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
        <c:if test="${numberOfEvents == 0}">events today.</c:if>
        <c:if test="${numberOfEvents == 1}">event today:</c:if>
        <c:if test="${numberOfEvents != 1 && numberOfEvents > 0}">events today:</c:if>
    </p>
    <c:if test="${todayEvents.size() > 0}">
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
                    <td>${event.fullTimeFormatter()}</td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${joinedEvents.size() == 0}">
        <p class="info-font">You haven't joined any event yet. It seems that you are getting lazy...
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                 class="bi bi-emoji-expressionless-fill" viewBox="0 0 16 16">
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zM4.5 6h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1zm5 0h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1zm-5 4h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1z"/>
            </svg>
        </p>
    </c:if>

    <c:if test="${joinedEvents.size() > 0}">
        <p class="info-font">Here are your joined future events:</p>
        <table class="table table-success table-striped table-hover">
            <thead>
            <th>Event Name</th>
            <th>Location</th>
            <th>Attendees</th>
            <th>Date & Time</th>
            </thead>
            <c:forEach var="event" items="${joinedEvents}">
                <tbody>
                <tr>
                    <td><a href="/event/${event.id}" class="table-link">${event.eventName}</a></td>
                    <td>${event.location}</td>
                    <td>${event.getNumberOfAttenders()}/${event.attendees}</td>
                    <td>${event.fullDateFormatter()}</td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </c:if>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script type="text/javascript" src="/script/navbar-dropdown.js"></script>
</body>
</html>