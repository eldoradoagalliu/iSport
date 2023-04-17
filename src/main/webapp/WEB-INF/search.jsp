<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/header-style.css">
    <link rel="stylesheet" type="text/css" href="/css/event-style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark nav-border">
    <div class="container-fluid">
        <a class="navbar-brand logo-font" href="/dashboard">iSport</a>
        <div>
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
        <c:if test="${events.size() > 0}">
            <table class="table table-success table-striped table-hover">
                <thead>
                <th>Event Name</th>
                <th>Location Name</th>
                <th>Member</th>
                <th>Date</th>
                <th>Creator</th>
                <th>Action</th>
                </thead>
                <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td><a href="/event/${event.id}" class="customized-link">${event.eventName}</a></td>
                        <td>${event.location}</td>
                        <td>${event.getNumberOfAttenders()}/${event.attendees}</td>
                        <td>${event.fullDateFormatter()}</td>
                        <td><a href="/account/${event.creator.id}" class="text-dark">${event.creator.getFullName()}</a>
                        </td>
                        <td>
                            <c:if test="${event.getNumberOfAttenders() == event.attendees}">Full</c:if>
                            <c:if test="${event.users.contains(currentUser)}">
                                <a href="/event/leave/${event.id}" class="text-danger m-1">Leave event</a>
                            </c:if>
                            <c:if test="${event.getNumberOfAttenders() < event.attendees && !event.users.contains(currentUser)}">
                                <a href="/event/join/${event.id}">Join event</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script type="text/javascript" src="/script/navbar-dropdown.js"></script>
</body>
</html>