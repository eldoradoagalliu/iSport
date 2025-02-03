<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Search</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/navbar-style.css">
    <link rel="stylesheet" type="text/css" href="/css/event-style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark nav-border">
    <div class="container-fluid">
        <a class="navbar-brand logo-font" href="/dashboard">iSport</a>
        <div>
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
    <form:form action="/search" method="post">
        <div class="d-flex align-content-center justify-content-between">
            <div class="d-flex">
                <h1>Search by:</h1>
                <select name="selectedOption" id="selectedOption" class="options mt-2">
                    <option value="eventName">Event Name</option>
                    <option value="locationName">Location Name</option>
                    <option value="creator">Creator</option>
                </select>
            </div>
            <div class="mt-2">
                <input name="search" id="search" class="search-input" placeholder="Search" required="required"/>
                <button class="btn btn-success submit-button">Submit</button>
            </div>
        </div>
    </form:form>
    <div class="mt-3 table-font bottom-margin">
        <c:if test="${!events.isEmpty()}">
            <table class="table table-success table-striped table-hover">
                <thead>
                <th>Event Name</th>
                <th>Location Name</th>
                <th>Member</th>
                <th>Event Time & Date</th>
                <th>Creator</th>
                <th>Action</th>
                </thead>
                <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td><a href="/event/${event.id}" class="customized-link">${event.eventName}</a></td>
                        <td>${event.location}</td>
                        <td>${event.getNumberOfAttenders()}/${event.attendees}</td>
                        <td>${event.getFormattedDateTime()}</td>
                        <td><a href="/account/${event.creator.id}" class="text-dark">${event.creator.getFullName()}</a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${event.getNumberOfAttenders() == event.attendees}">Full</c:when>
                                <c:when test="${event.users.contains(currentUser)}">
                                    <form:form action="/event/${event.id}/leave" method="get">
                                        <a href="/event/${event.id}/leave" class="text-danger m-1">Leave event</a>
                                    </form:form>
                                </c:when>
                                <c:when test="${event.getNumberOfAttenders() < event.attendees && !event.users.contains(currentUser)}">
                                    <form:form action="/event/${event.id}/join" method="get">
                                        <a href="/event/${event.id}/join">Join event</a>
                                    </form:form>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script src="/webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>