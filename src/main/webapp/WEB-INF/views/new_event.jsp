<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>New Event</title>
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
    <h1 class="text-center new-event-underline mb-4">New Sport Event</h1>
    <%--@elvariable id="event" type=""--%>
    <div class="events-inputs">
        <form:form action="/event" method="post" modelAttribute="event">
            <div class="form-floating mb-3 col">
                <form:input path="eventName" class="form-control input-font" placeholder="Event title"/>
                <form:label path="eventName" class="label-font">Event title</form:label>
                <div class="m-1"><form:errors path="eventName" class="text-danger"/></div>
            </div>
            <div class="form-floating mb-3 col">
                <form:input path="location" class="form-control input-font" placeholder="Location"/>
                <form:label path="location" class="label-font">Location</form:label>
                <div class="m-1"><form:errors path="location" class="text-danger"/></div>
            </div>
            <div class="row g-2">
                <div class="form-floating mb-3 col-6">
                    <form:input path="latitude" type="number" step="0.00000000001" class="form-control input-font"
                                placeholder="Latitude"/>
                    <form:label path="latitude" class="label-font">Latitude</form:label>
                    <div class="m-1"><form:errors path="latitude" class="text-danger"/></div>
                </div>
                <div class="form-floating mb-3 col-6">
                    <form:input path="longitude" type="number" step="0.0000000001" class="form-control input-font"
                                placeholder="Longitude"/>
                    <form:label path="longitude" class="label-font">Longitude</form:label>
                    <div class="m-1"><form:errors path="longitude" class="text-danger"/></div>
                </div>
            </div>
            <div class="form-floating mb-3 col">
                <form:input path="attendees" type="number" min="2" class="form-control input-font"
                            placeholder="Number of attendees"/>
                <form:label path="attendees" class="label-font">Number of attendees</form:label>
                <div class="m-1"><form:errors path="attendees" class="text-danger"/></div>
            </div>
            <div class="row g-2 mb-3">
                <div class="form-floating col-6">
                    <input type="time" name="time" value="${time}" class="form-control input-font" placeholder="Time"/>
                    <label class="label-font">Time</label>
                </div>
                <div class="form-floating col-6">
                    <input type="date" name="date" value="${date}" class="form-control input-font" placeholder="Date"/>
                    <label class="label-font">Date</label>
                </div>
                <c:if test="${nullDateTimeErrorMessage != null}">
                    <div class="text-danger m-1"><c:out value="${nullDateTimeErrorMessage}"/></div>
                </c:if>
                <c:if test="${dateTimeErrorMessage != null}">
                    <div class="text-danger m-1"><c:out value="${dateTimeErrorMessage}"/></div>
                </c:if>
            </div>
            <div class="form-floating mb-3 col">
                <form:textarea path="description" type="number" class="form-control description-input"
                               placeholder="Description"/>
                <form:label path="description" class="label-font">Description</form:label>
                <div class="m-1"><form:errors path="description" class="text-danger"/></div>
            </div>
            <button class="btn btn-success col-6 float-end create-button">Create Event</button>
        </form:form>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script src="/webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>