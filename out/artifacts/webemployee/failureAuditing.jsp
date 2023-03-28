<%@ page import="dev.filipposcaramuzza.db2_telco.entities.User" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.ServicePackage" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Service" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Order" %>
<%@ page import="dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User user;
    user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/");
    }
%>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>


        body {
            text-align: center;
        }

        .column {
            text-align: left;
        }

        /*.packages {
            border-right: 2px solid lightseagreen;
        }*/


        .tag {
            margin: 5px 0px;
        }
    </style>

    <title>Telco - Packages</title>
</head>
<body>
<section class="hero is-info">
    <div class="hero-body">
        <p class="title">
            Telco Service &#174;
        </p>
        <p class="subtitle">
            Manage Service Packages
        </p>
    </div>
</section>
<!-- <h1 class="title is-1">
    Telco Service &#174;
</h1>
<h1 class="sub-title">
    Available Packages and Your Subscriptions
</h1>-->
<br>
<div class="columns is-centered">
    <div class="column is-5 packages">
        <nav class="breadcrumb" aria-label="breadcrumbs">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Landing Page</a></li>
                <li class="is-active"><a href="#" aria-current="page">Failures Auditing</a></li>
            </ul>
        </nav>
        <h3 class="title is-3">
            Failures Auditing
        </h3>

        <table class="table">
            <thead>
                <tr>
                    <td>
                        <strong>Username</strong>
                    </td>
                    <td>
                        <strong>Username</strong>
                    </td>
                    <td>
                        <strong>Amount of Failures</strong>
                    </td>
                    <td>
                        <strong>Last Failure</strong>
                    </td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="failureAuditing" items="${failuresAuditing}">
                    <tr class="js-modal-trigger">
                        <td>
                            ${failureAuditing.getUser().getUsername()}
                        </td>
                        <td>
                            ${failureAuditing.getUser().getEmail()}
                        </td>
                        <td>
                            ${failureAuditing.getUser().getFailedPayments()}
                        </td>
                        <td>
                            ${failureAuditing.getLastFailureDate().toString()} @ ${failureAuditing.getLastFailureTime().toString()}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="column is-2">
        <h3 class="title is-3">
            Profile
        </h3>
        <c:choose>
            <c:when test="${user == null}">
                <div class="notification is-warning is-light">
                    You are browsing as guest. If you want to sign in go to the <a
                        href="${pageContext.request.contextPath}/">Log In page</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="box">
                    <b>Username:</b> ${user.getUsername()}<br>
                    <b>E-Mail:</b> ${user.getEmail()}<br>
                    <b>Name:</b> ${user.getName()}<br>
                    <b>Surname:</b> ${user.getSurname()}<br>

                </div>
                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/activationschedule">Activation Schedules</a>
                </div>
                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/insolvents">Insolvent Users</a>
                </div>
                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/failureauditing">Failure Auditing</a>
                </div>
                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/sales">Sales Report</a>
                </div>
                <br>
                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/logout">Log Out</a>
                </div>

            </c:otherwise>
        </c:choose>

    </div>
</div>
<script>
    function addNewLine() {
        let row = document.getElementById("serviceOptionTable").insertRow(document.getElementById("serviceOptionTable").rows.length - 1);
        let cell1 = row.insertCell()
        let cell2 = row.insertCell()
        let cell3 = row.insertCell()

        cell1.innerHTML = "<input class='input' type='search' name='names[]' placeholder='Option name...' list='serviceOptionNames' required/>";
        cell2.innerHTML = "<input class='input' type='number' name='optionNums[]' placeholder='Number...' required/>";
        cell3.innerHTML = "<input class='input' type='number' name='optionFees[]' placeholder='Extra Fee' required/>";
    }

    document.addEventListener('DOMContentLoaded', () => {
        // Functions to open and close a modal
        function openModal($el) {
            $el.classList.add('is-active');
        }

        function closeModal($el) {
            $el.classList.remove('is-active');
        }

        function closeAllModals() {
            (document.querySelectorAll('.modal') || []).forEach(($modal) => {
                closeModal($modal);
            });
        }

        // Add a click event on buttons to open a specific modal
        (document.querySelectorAll('.js-modal-trigger') || []).forEach(($trigger) => {
            const modal = $trigger.dataset.target;
            const $target = document.getElementById(modal);
            console.log($target);

            $trigger.addEventListener('click', () => {
                openModal($target);
            });
        });

        // Add a click event on various child elements to close the parent modal
        (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button') || []).forEach(($close) => {
            const $target = $close.closest('.modal');

            $close.addEventListener('click', () => {
                closeModal($target);
            });
        });

        // Add a keyboard event to close all modals
        document.addEventListener('keydown', (event) => {
            const e = event || window.event;

            if (e.keyCode === 27) { // Escape key
                closeAllModals();
            }
        });
    });
</script>
</body>
</html>
