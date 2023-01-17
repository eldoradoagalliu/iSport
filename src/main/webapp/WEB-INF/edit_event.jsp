<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Event</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style3.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
</head>
<body class="container">
<div style="width: 1000px; background: gray;margin-left: 50px; padding: 20px; font-size: 20px; font-weight: 600">
    <h1 class="text-center" style="margin-bottom: 20px; color: white">Edit Event</h1>
    <%--@elvariable id="event" type=""--%>
    <form:form action="/event/edit/${event.id}" method="post" modelAttribute="event">
    <div class="form-floating mb-3 col-sm-6 eventInput">
        <form:input path="eventName" class="form-control" placeholder="Event title" value="${event.eventName}"/>
        <form:label path="eventName">Event title</form:label>
        <div><form:errors path="eventName" class="text-danger"/></div>
    </div>
    <div class="form-floating mb-3 col-sm-6 eventInput">
        <form:input path="location" class="form-control" placeholder="Location" value="${event.location}"/>
        <form:label path="location">Location</form:label>
        <div><form:errors path="location" class="text-danger"/></div>
    </div>
    <div class="row g-2">
    <div class="form-floating mb-3 col-sm-3 eventInput">
        <form:input path="latitude" type="number" step="0.00000000000001" class="form-control" placeholder="Latitude"
                    value="${event.latitude}"/>
        <form:label path="latitude">Latitude</form:label>
        <div><form:errors path="latitude" class="text-danger"/></div>
    </div>
    <div class="form-floating mb-3 col-sm-3">
        <form:input path="longitude" type="number" step="0.00000000000001" class="form-control" placeholder="Longitude"
                    style="font-weight: 600" value="${event.longitude}"/>
        <form:label path="longitude" style="font-weight: 600">Longitude</form:label>
        <div><form:errors path="longitude" class="text-danger"/></div>
    </div>
</div>
    <div class="form-floating mb-3 col-sm-6 eventInput">
        <form:input path="attendees" type="number" min="2" class="form-control" placeholder="Number of attendees"
                    value="${event.attendees}"/>
        <form:label path="attendees">Number of attendees</form:label>
        <div><form:errors path="attendees" class="text-danger"/></div>
    </div>
    <div class="row g-2">
        <div class="form-floating mb-3 col-sm-3 eventInput">
            <form:input path="date" type="date" class="form-control" placeholder="Date" value="${event.date}"/>
            <form:label path="date">Date</form:label>
            <div><form:errors path="date" class="text-danger"/></div>
        </div>
        <div class="form-floating mb-3 col-sm-3">
            <form:input path="time" type="time" class="form-control" placeholder="Time" style="font-weight: 600"
                        value="${event.time}"/>
            <form:label path="time" style="font-weight: 600">Time</form:label>
            <div><form:errors path="time" class="text-danger"/></div>
        </div>
    </div>
    <div class="form-floating mb-3 col-sm-6 eventInput">
        <form:textarea path="description" type="number" class="form-control" placeholder="Description"
                       style="height: 90px; font-weight: 600" value="${event.description}"/>
        <form:label path="description">Description</form:label>
        <div><form:errors path="description" class="text-danger"/></div>
    </div>

    <div style="margin-left: 535px"><button style="box-shadow: 1px 1px black; border: 2px solid black;
        font-size: 18px; font-weight: 600; color: white; padding: 3px 35px; background: #037759" class="btn">Edit Event</button></div>
</form:form>
</div>
<footer class="text-center text-white m-1 text-decoration-underline">Copyright © 2022 - Eldorado Agalliu</footer>
</body>
</html>