<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Event</title>
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
<div class="main-part edit-part">
    <h1 class="text-center edit-event-underline mb-4">Edit Sport Event</h1>
    <%--@elvariable id="event" type=""--%>
    <div class="events-inputs">
        <form:form action="/event/edit/${event.id}" method="post" modelAttribute="event">
            <input type="hidden" name="_method" value="put">
            <div class="form-floating mb-3 col">
                <form:input path="eventName" class="form-control input-font" placeholder="Event title"/>
                <form:label path="eventName">Event title</form:label>
                <div class="m-1"><form:errors path="eventName" class="text-white"/></div>
            </div>

            <div class="form-floating mb-3 col">
                <form:input path="location" class="form-control input-font" placeholder="Location"/>
                <form:label path="location">Location</form:label>
                <div class="m-1"><form:errors path="location" class="text-white"/></div>
            </div>

            <div class="row g-2">
                <div class="form-floating mb-3 col-6">
                    <form:input path="latitude" type="number" step="0.00000000001" class="form-control input-font"
                                placeholder="Latitude"/>
                    <form:label path="latitude">Latitude</form:label>
                    <div class="m-1"><form:errors path="latitude" class="text-white"/></div>
                </div>

                <div class="form-floating mb-3 col-6">
                    <form:input path="longitude" type="number" step="0.0000000001" class="form-control input-font"
                                placeholder="Longitude"/>
                    <form:label path="longitude">Longitude</form:label>
                    <div class="m-1"><form:errors path="longitude" class="text-white"/></div>
                </div>
            </div>

            <div class="form-floating mb-3 col">
                <form:input path="attendees" type="number" min="2" class="form-control input-font"
                            placeholder="Number of attendees"/>
                <form:label path="attendees">Number of attendees</form:label>
                <div class="m-1"><form:errors path="attendees" class="text-white"/></div>
            </div>

            <div class="row g-2 mb-3">
                <div class="form-floating col-6">
                    <input type="time" name="time" value="${time}" class="form-control input-font"
                           placeholder="Time"/>
                    <label>Time</label>
                </div>
                <div class="form-floating col-6">
                    <input type="date" name="date" value="${date}" class="form-control input-font"
                           placeholder="Date"/>
                    <label>Date</label>
                </div>
                <c:if test="${nullDateTimeErrorMessage != null}">
                    <div class="text-white m-1"><c:out value="${nullDateTimeErrorMessage}"></c:out></div>
                </c:if>
                <c:if test="${dateTimeErrorMessage != null}">
                    <div class="text-white m-1"><c:out value="${dateTimeErrorMessage}"></c:out></div>
                </c:if>
            </div>

            <div class="form-floating mb-3 col">
                <form:textarea path="description" type="number" class="form-control description-input"
                               placeholder="Description"/>
                <form:label path="description">Description</form:label>
                <div class="m-1"><form:errors path="description" class="text-white"/></div>
            </div>

            <button class="btn btn-dark col-6 float-end create-button">Edit Event</button>
        </form:form>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script type="text/javascript" src="/script/navbar-dropdown.js"></script>
</body>
</html>