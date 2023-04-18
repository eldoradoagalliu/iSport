<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/header-style.css">
    <link rel="stylesheet" type="text/css" href="/css/account-style.css">
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
                    <a class="nav-link active float-end" href="/account/${currentUser.id}" tabindex="-1" aria-disabled="true">
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
    <div>
        <c:if test="${emptyPasswordErrorMessage != null}">
            <div class="text-danger text-center m-1"><c:out value="${emptyPasswordErrorMessage}"></c:out></div>
        </c:if>
        <c:if test="${oldPasswordReuseMessage != null}">
            <div class="text-danger text-center m-1"><c:out value="${oldPasswordReuseMessage}"></c:out></div>
        </c:if>
        <c:if test="${incorrectOldPasswordMessage != null}">
            <div class="text-danger text-center m-1"><c:out value="${incorrectOldPasswordMessage}"></c:out></div>
        </c:if>
        <c:if test="${successfulPasswordChangeMessage != null}">
            <div class="text-success text-center m-1"><c:out value="${successfulPasswordChangeMessage}"></c:out></div>
        </c:if>
    </div>
    <div class="d-flex justify-content-between end-part">
        <div class="mb-3">
            <div class="info">
                <h1 class="profile-underline">Profile Page</h1>
                <div>Name: <c:out value="${user.getFullName()}"/></div>
                <div>Email: <c:out value="${user.email}"/></div>
                <div>Birthdate: <c:out value="${user.birthdateFormatter()}"/></div>

                <c:if test="${user.getEvents().size() > 0}">
                    <div class="mt-2">Event History:</div>
                    <ul class="event-scroll">
                        <c:forEach var="event" items="${user.getEvents()}">
                            <li><c:out value="${event.eventName}"/></li>
                        </c:forEach>
                    </ul>
                </c:if>

                <c:if test="${futureEvents.size() > 0 && currentUser.id == user.id}">
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
                    <c:if test="${user.getProfilePhotoPath() == null}">
                        <img src="/profile-photos/default/account-icon.png" alt="Default image" class="profile-photo"/>
                    </c:if>
                    <c:if test="${user.getProfilePhotoPath() != null}">
                        <img src="${user.getProfilePhotoPath()}" alt="${user.getFullName()} image" class="profile-photo"/>
                    </c:if>
                </div>
                <c:if test="${currentUser.id == user.id}">
                    <form:form class="p-3" action="/uploadProfilePhoto/${user.id}" method="post" enctype="multipart/form-data">
                        <label class="form-label text-center">Upload profile picture</label>
                        <div class="d-flex">
                            <input class="form-control" type="file" name="photo" accept="image/*" required="required"/>
                            <button class="btn btn-primary mx-1 col-4">Add profile picture</button>
                        </div>
                    </form:form>
                </c:if>
            </div>
            <div class="d-flex float-end mt-4">
                <c:if test="${currentUser.id == user.id}">
                    <div>
                        <form action="/account/edit/${user.id}">
                            <button class="btn btn-secondary edit-button">Edit Details</button>
                        </form>
                    </div>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle dropdown-button" type="button"
                                id="dropdownButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Change Password
                        </button>
                        <form:form action="/account/changePassword" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <div class="dropdown-menu p-1 mt-2" id="dropdown-menu" aria-labelledby="dropdownButton">
                                <div class="dropdown-item">
                                    <label>Input the Old Password</label>
                                    <input type="password" class="form-control" name="oldPassword" placeholder="Old Password">
                                    <label>Add New Password</label>
                                    <input type="password" class="form-control" name="newPassword" placeholder="New Password">
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
<script type="text/javascript" src="/script/dropdowns.js"></script>
</body>
</html>