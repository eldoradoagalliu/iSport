<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Account Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/navbar-style.css">
    <link rel="stylesheet" type="text/css" href="/css/account-style.css">
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
    <div>
        <c:if test="${emptyPasswordErrorMessage != null}">
            <div class="text-danger text-center m-1"><c:out value="${emptyPasswordErrorMessage}"/></div>
        </c:if>
        <c:if test="${oldPasswordReuseMessage != null}">
            <div class="text-danger text-center m-1"><c:out value="${oldPasswordReuseMessage}"/></div>
        </c:if>
        <c:if test="${incorrectOldPasswordMessage != null}">
            <div class="text-danger text-center m-1"><c:out value="${incorrectOldPasswordMessage}"/></div>
        </c:if>
        <c:if test="${successfulPasswordChangeMessage != null}">
            <div class="text-success text-center m-1"><c:out value="${successfulPasswordChangeMessage}"/></div>
        </c:if>
    </div>
    <div class="d-flex justify-content-between end-part">
        <div class="mb-3">
            <div class="info">
                <h1 class="profile-underline">Profile Page</h1>
                <div>Name: <c:out value="${user.getFullName()}"/></div>
                <div>Email: <c:out value="${user.email}"/></div>
                <div>Birthdate: <c:out value="${user.getFormattedBirthdate()}"/></div>
                <c:if test="${!user.getEvents().isEmpty()}">
                    <div class="mt-2">Event History:</div>
                    <ul class="event-scroll">
                        <c:forEach var="event" items="${user.getEvents()}">
                            <li><c:out value="${event.eventName}"/></li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${!futureEvents.isEmpty() && currentUser.id == user.id}">
                    <div class="mt-2">Future Events:</div>
                    <ol class="event-scroll">
                        <c:forEach var="event" items="${futureEvents}">
                            <li><c:out value="${event.eventName}"/></li>
                        </c:forEach>
                    </ol>
                </c:if>
            </div>
        </div>
        <div class="flex-column">
            <div class="mt-3 photo-upload">
                <div>
                    <c:choose>
                        <c:when test="${user.getProfilePhotoPath() != null}">
                            <img src="${user.getProfilePhotoPath()}" alt="${user.getFullName()} profile photo"
                                 class="profile-photo"/>
                        </c:when>
                        <c:otherwise>
                            <img src="/profile-photos/default/account-icon.png" alt="Default profile photo"
                                 class="profile-photo"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${currentUser.id == user.id}">
                    <form:form class="p-3" action="/uploadProfilePhoto/${user.id}" method="post"
                               enctype="multipart/form-data">
                        <label class="form-label text-center">Upload a new profile picture</label>
                        <div class="d-flex">
                            <input class="form-control" type="file" name="photo" accept="image/*" required="required"/>
                            <button class="btn btn-primary mx-1 col-4">Upload</button>
                        </div>
                    </form:form>
                </c:if>
            </div>
            <div class="d-flex float-end mt-4">
                <c:if test="${currentUser.id == user.id}">
                    <div>
                        <form action="/account/${user.id}/edit">
                            <button class="btn btn-warning edit-button">Edit Details</button>
                        </form>
                    </div>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle dropdown-button" data-bs-toggle="dropdown">
                            Change Password
                        </button>
                        <form:form action="/account/change/password" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <div class="dropdown-menu p-2 mt-1">
                                <div class="dropdown-item bg-white">
                                    <label class="form-label">Old Password</label>
                                    <input type="password" class="form-control" name="oldPassword">
                                </div>
                                <div class="dropdown-item bg-white">
                                    <label class="form-label">New Password</label>
                                    <input type="password" class="form-control" name="newPassword">
                                </div>
                                <button class="btn btn-primary float-end mt-2">Submit</button>
                            </div>
                        </form:form>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script src="/webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>