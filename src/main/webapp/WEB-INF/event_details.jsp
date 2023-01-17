<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Event Details</title>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <script type="module" src="./event_details.jsp"></script>
</head>
<body class="container" style="background: #037759">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="height: 50px; width: 1000px;margin-left: 50px">
    <div class="container-fluid">
        <a class="navbar-brand" href="/dashboard" style="font-size: 24px">iSport</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav" style="margin-left: 465px">
                <li class="nav-item" style="margin-left: 20px">
                    <a class="nav-link active" aria-current="page" href="/dashboard">Home</a>
                </li>
                <li class="nav-item" style="margin-left: 20px">
                    <a class="nav-link active" href="/new">New</a>
                </li>
                <li class="nav-item" style="margin-left: 20px">
                    <a class="nav-link active" href="/search">Search</a>
                </li>
                <li style="margin-top: 7px; margin-left: 20px"><svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="white" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg></li>
                <li class="nav-item">
                    <a class="nav-link active" href="/account/${user.id}" tabindex="-1" aria-disabled="true">Account</a>
                </li>
                <li class="nav-item">
                    <form id="logoutForm" method="POST" action="/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Logout" style="border: none; background: transparent; color: white; font-weight: 500; width: 70px"/>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script type="text/javascript">
    function initMap() {
        const location = { lat: <c:out value="${event.latitude}"/>, lng: <c:out value="${event.longitude}"/> };

        const map = new google.maps.Map(document.getElementById("map"), {
            zoom: 16,
            center: location,
        });

        const marker = new google.maps.Marker({
            position: location,
            map: map,
        });
    }
    window.initMap = initMap;
</script>
<div style="font-weight: 600; width: 1000px; background: #ebd402; margin-left: 50px; padding: 20px; font-size: 20px">
    <h2 style="font-weight: 550; border-bottom: 2px solid black" class="text-center">Details of the ${event.eventName} event</h2>
<%--    <div>Title: <c:out value="${event.eventName}"/></div>--%>
    <div class="d-flex align-content-center">
        <div style="width: 400px">
            <div>Number of Attendees: <c:out value="${event.attendees}"/></div>
            <div>Event Date and Time: <c:out value="${event.fullDateFormatter()}"/></div>
            <div>Description: <c:out value="${event.description}"/></div>
            <div>Users attending this event:</div>
            <ol style="overflow-y: auto; height: 100px">
                <c:forEach var="user" items="${users}">
                    <li><c:out value="${user.fullName()}"/></li>
                </c:forEach>
            </ol>
        </div>
        <div>
            <h2 style="margin-left: 220px">Location</h2>
            <div id="map" style="margin-left: 40px; height: 400px; width: 500px; background: transparent"></div>

            <c:if test="${user.id == event.creator.id}">
                <div class="d-flex align-content-center">
                    <form action="/event/edit/${event.id}">
                        <div style="margin-left: 280px; margin-top: 40px"><button style="box-shadow: 1px 1px black; border: 2px solid black; font-size: 18px; font-weight: 600; color: white; padding: 3px 20px" class="btn btn-secondary">Edit</button></div>
                    </form>
                    <form action="/event/delete/${event.id}">
                        <div style="margin-left: 20px; margin-top: 40px"><button style="box-shadow: 1px 1px black; border: 2px solid black; font-size: 18px; font-weight: 600; color: white; padding: 3px 22px" class="btn btn-danger">Delete Event!</button></div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
    <c:if test="${user.getEvents().contains(event)}">
    <div style="border: 3px solid black; padding: 5px; overflow-y: auto; height: 150px; margin-top: 10px">
        <div style="margin-bottom: 5px">Messages:</div>
        <c:forEach var="message" items="${messages}">
            <div><c:out value="${message.user.firstName}"/>: <c:out value="${message.content}"/></div>
        </c:forEach>
    </div>
        <%--@elvariable id="message" type=""--%>
        <form:form action="/event/message/${event.id}" method="post" modelAttribute="message">
            <div class="d-flex align-content-center" style="margin-top: 10px">
                <div class="form-floating col-sm-11" style="font-size: 18px">
                    <form:textarea path="content" type="number" class="form-control" placeholder="Write a message" style="font-weight: 600"></form:textarea>
                    <form:label path="content" style="font-weight: 600">Write a message</form:label>
                    <div><form:errors path="content" class="text-danger"/></div>
                </div>
                <div style="margin-left: 3px; margin-top: 1px">
                    <button style="box-shadow: 1px 1px black; border: 2px solid black; font-size:20px; font-weight: 600; color: white; padding: 10px 20px" class="btn btn-primary">Send</button>
                </div>
            </div>
        </form:form>
    </c:if>
</div>
<footer class="text-center text-white m-1 text-decoration-underline">Copyright © 2022 - Eldorado Agalliu</footer>
<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBHQV4LenUiXtgJlf2HbyAMEBl3cSsHGi0&callback=initMap&v=weekly"
        defer
></script>
</body>
</html>