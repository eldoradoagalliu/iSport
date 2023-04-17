<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Event Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/header-style.css">
    <link rel="stylesheet" type="text/css" href="/css/event-style.css">
    <script type="module" src="./event_details.jsp"></script>
</head>
<body>
<script type="text/javascript">
    function initMap() {
        const location = {lat: <c:out value="${event.latitude}"/>, lng: <c:out value="${event.longitude}"/>};

        const map = new google.maps.Map(document.getElementById("map"), {
            zoom: 6,
            center: location,
        });

        const marker = new google.maps.Marker({
            position: location,
            map: map,
        });
    }

    window.initMap = initMap;
</script>

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
    <h2 class="text-center details-underline">Details of the ${event.eventName}
        event</h2>
    <div class="d-flex align-content-center justify-content-between">
        <div class="details">
            <div>Number of Attendees: <c:out value="${event.attendees}"/></div>
            <div>Event Date and Time: <c:out value="${event.fullDateFormatter()}"/></div>
            <div>Description: <c:out value="${event.description}"/></div>
            <c:if test="${users.size() > 0}">
                <div>Users attending this event:</div>
                <ol class="event-scroll">
                    <c:forEach var="user" items="${users}">
                        <li><c:out value="${user.getFullName()}"/></li>
                    </c:forEach>
                </ol>
            </c:if>

        </div>
        <div>
            <h2 class="text-center">Location</h2>
            <div id="map" class="event-location"></div>

            <c:if test="${currentUser.id == event.creator.id}">
                <div class="d-flex align-content-center float-end mt-4">
                    <form action="/event/edit/${event.id}">
                        <button class="btn btn-secondary button-font">Edit Event</button>
                    </form>
                    <form:form action="/event/delete/${event.id}">
                        <input type="hidden" name="_method" value="delete">
                        <button class="btn btn-danger mx-2 button-font">Delete Event!</button>
                    </form:form>
                </div>
            </c:if>
        </div>
    </div>
    <c:if test="${currentUser.events.contains(event)}">
        <c:if test="${messages.size() > 0}">
            <div class="message-box mt-5" id="message-box">
                <div class="mb-1 details-underline">Messages:</div>
                <c:forEach var="message" items="${messages}">
                    <div><c:out value="${message.user.firstName}"/>: <c:out value="${message.content}"/></div>
                </c:forEach>
            </div>
        </c:if>
        <%--@elvariable id="message" type=""--%>
        <form:form action="/event/message/${event.id}" method="post" modelAttribute="message">
            <div class="d-flex align-content-center mt-3">
                <div class="form-floating message-input">
                    <form:textarea path="content" type="number" class="form-control description-input"
                                   placeholder="Write a message"/>
                    <form:label path="content">Write a message</form:label>
                </div>
                <button id="sendButton" class="btn btn-primary send-button float-end mx-2">Send</button>
            </div>
            <div class="m-1 mb-5"><form:errors path="content" class="text-danger"/></div>
        </form:form>
    </c:if>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script src="https://maps.googleapis.com/maps/api/js?key=your_key&callback=initMap&v=weekly"
        defer>
</script>
<script type="text/javascript" src="/script/navbar-dropdown.js"></script>
</body>
</html>