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

    ServicePackage servicePackageEdit = new ServicePackage();

    if (request.getAttribute("servicePackageEdit") != null) {
        servicePackageEdit = (ServicePackage) request.getAttribute("servicePackageEdit");
    }

    if ((long) (int) servicePackageEdit.getServices().stream().filter(service -> service.getID() == 3).count() > 1)
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
                <li><a href="${pageContext.request.contextPath}/packages">Packages</a></li>
                <li class="is-active"><a href="#" aria-current="page">Configure</a></li>
            </ul>
        </nav>
        <h3 class="title is-3">
            Create Service Package
        </h3>
        <div>
            <form method="post" action="${pageContext.request.contextPath}/configure">
                <label>
                    <strong>Service Package Name</strong><br>
                    <input class="input" type="text" name="servicePackageName" style="width: 50%" value="${servicePackageEdit.getName()}" required/>
                </label>
                <br><br>

                <strong>Services</strong>
                <table class="table">
                    <thead>
                    <tr>
                        <th></th>
                        <th><strong>ID</strong></th>
                        <th><strong>Name</strong></th>
                        <th><strong>Service Options</strong></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="service" items='<%= request.getAttribute("services") %>'>
                    <tr>
                        <td>
                            <label>
                                <c:choose>
                                    <c:when test="${servicePackageEdit.getServices().stream().filter(s -> s.getID() == service.getID()).count() > 0}">
                                        <input type="checkbox" name="services[]" value="${service.getID()}" checked/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="services[]" value="${service.getID()}"/>
                                    </c:otherwise>
                                </c:choose>

                            </label>
                        </td>
                        <td>
                                ${service.getID()}
                        </td>
                        <td>
                                ${service.getName()}
                        </td>
                        <td>
                            <ul>
                                <c:forEach var="serviceOption" items="${service.getServiceOptions()}">
                                    <li>
                                        <span class="has-text-info">${serviceOption.getOptionNum()}</span> ${serviceOption.getName()}
                                        at <span
                                            class="has-text-info">${serviceOption.getExtraOptionFee().setScale(2)}</span>/month
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                    <tfoot>
                    <td><a href="#" class="js-modal-trigger" data-target="modal-js-service">add service...</a></td>
                    </tfoot>
                </table>
                <br><br>

                <strong>Optional Product</strong>
                <table class="table">
                    <thead>
                    <tr>
                        <th></th>
                        <th><strong>ID</strong></th>
                        <th><strong>Name</strong></th>
                        <th><strong>Fee/Month</strong></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="optionalProduct" items='<%= request.getAttribute("optionalProducts") %>'>
                    <tr>
                        <td>
                            <label>
                                <c:choose>
                                    <c:when test="${servicePackageEdit.getOptionalProducts().stream().filter(op -> op.getID() == optionalProduct.getID()).count() > 0}">
                                        <input type="checkbox" name="optionalProducts[]"
                                               value="${optionalProduct.getID()}" checked/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="optionalProducts[]"
                                               value="${optionalProduct.getID()}"/>
                                    </c:otherwise>
                                </c:choose>
                            </label>
                        </td>
                        <td>
                                ${optionalProduct.getID()}
                        </td>
                        <td>
                                ${optionalProduct.getName()}
                        </td>
                        <td>
                                ${optionalProduct.getMonthlyFee()}
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                    <tfoot>
                    <td><a href="#" class="js-modal-trigger" data-target="modal-js-optionalproduct">add optional
                        product...</a></td>
                    </tfoot>
                </table>

                <br><br>

                <strong>ValidityPeriod</strong>
                <table class="table">
                    <thead>
                    <tr>
                        <th></th>
                        <th><strong>ID</strong></th>
                        <th><strong>Months Num.</strong></th>
                        <th><strong>Fee/Month</strong></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="validityPeriod" items='<%= request.getAttribute("validityPeriods") %>'>
                    <tr>
                        <td>
                            <label>
                                <c:choose>
                                    <c:when test="${servicePackageEdit.getValidityPeriods().stream().filter(vp -> vp.getID() == validityPeriod.getID()).count() > 0}">
                                        <input type="checkbox" name="validityPeriods[]"
                                               value="${validityPeriod.getID()}" checked/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="validityPeriods[]"
                                               value="${validityPeriod.getID()}"/>
                                    </c:otherwise>
                                </c:choose>
                            </label>
                        </td>
                        <td>
                                ${validityPeriod.getID()}
                        </td>
                        <td>
                                ${validityPeriod.getMonthsNum()}
                        </td>
                        <td>
                                ${validityPeriod.getMonthlyFee()}
                        </td>
                    </tr>
                    </c:forEach>

                    <tbody>
                    <tfoot>
                    <td><a href="#" class="js-modal-trigger" data-target="modal-js-validityperiod">add validity
                        period...</a></td>
                    </tfoot>
                </table>

                <br><br>
                <button type="submit" class="button is-primary"
                        onclick="location.href = '${pageContext.request.contextPath}/configure';">
                    <c:choose>
                        <c:when test="${not empty servicePackageEdit}">
                            <input type="hidden" name="requestType" value="<%= RequestType.EDIT_SERVICE_PACKAGE %>" />
                            <input type="hidden" name="IDServicePackage" value="${servicePackageEdit.getID()}" />
                            <span class="icon">
                                <i class="fas fa-edit"></i>
                            </span>
                            <span>
                                Edit Package
                            </span>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="requestType" value="<%= RequestType.CREATE_SERVICE_PACKAGE %>"/>
                            <span class="icon">
                                <i class="fas fa-plus"></i>
                            </span>
                            <span>
                                Create Package
                            </span>
                        </c:otherwise>
                    </c:choose>

                </button>
            </form>
        </div>

        <div id="modal-js-optionalproduct" class="modal">
            <div class="modal-background"></div>
            <div class="modal-card">
                <header class="modal-card-head">
                    <p class="modal-card-title">Add Optional Product</p>
                    <button class="delete" aria-label="close"></button>
                </header>
                <form method="post" action="create">
                    <section class="modal-card-body">
                        <input type="hidden" name="requestType" value="<%= RequestType.CREATE_OPTIONAL_PRODUCT %>"/>
                        <label>
                            <input class="input" type="text" name="name" placeholder="Name..." required/>
                        </label>
                        <br><br>
                        <label>
                            <input class="input" type="number" name="monthlyFee" placeholder="Monthly Fee (€)..."
                                   required/>
                        </label>
                    </section>
                    <footer class="modal-card-foot">
                        <button class="button is-success" type="submit">Add</button>
                    </footer>
                </form>

            </div>
        </div>
        <div id="modal-js-validityperiod" class="modal">
            <div class="modal-background"></div>
            <div class="modal-card">
                <header class="modal-card-head">
                    <p class="modal-card-title">Add Validity Period</p>
                    <button class="delete" aria-label="close"></button>
                </header>
                <form method="post" action="create">
                    <section class="modal-card-body">
                        <input type="hidden" name="requestType" value="<%= RequestType.CREATE_VALIDITY_PERIOD %>"/>
                        <label>
                            <input class="input" type="number" name="monthsNum" placeholder="Number of months..."
                                   required/>
                        </label>
                        <br><br>
                        <label>
                            <input class="input" type="number" name="monthlyFee" placeholder="Monthly Fee (€)..."
                                   required/>
                        </label>
                    </section>
                    <footer class="modal-card-foot">
                        <button class="button is-success" type="submit">Add</button>
                    </footer>
                </form>

            </div>
        </div>
        <div id="modal-js-service" class="modal">
            <div class="modal-background"></div>
            <div class="modal-card">
                <header class="modal-card-head">
                    <p class="modal-card-title">Add Service</p>
                    <button class="delete" aria-label="close"></button>
                </header>
                <form method="post" action="create">
                    <section class="modal-card-body">
                        <input type="hidden" name="requestType" value="<%= RequestType.CREATE_SERVICE %>"/>

                        <input class="input" type="text" name="serviceName" placeholder="Service Name"
                               list="serviceNames"/><br><br>
                        <datalist id="serviceNames">
                            <c:forEach var="serviceName" items="${serviceNames}">
                            <option value="${serviceName}">
                                </c:forEach>
                        </datalist>
                        <table class="table" id="serviceOptionTable">
                            <thead>
                            <td>Option Name</td>
                            <td>Option Num.</td>
                            <td>Extra Fee</td>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <input class="input" type="text" name="names[]" list="serviceOptionNames"
                                           placeholder='Option name...' required/>
                                </td>
                                <td>
                                    <input class="input" type="number" name="optionNums[]" placeholder='Number...'
                                           required/>
                                </td>
                                <td>
                                    <input class="input" type="number" name="optionFees[]" placeholder='Extra Fee'
                                           required/>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td><a href="#" onclick="addNewLine()">new line...</a></td>
                            </tr>
                            </tfoot>
                        </table>
                        <datalist id="serviceOptionNames">
                            <c:forEach var="serviceOption" items="${serviceOptionNames}">
                            <option value="${serviceOption}">
                                </c:forEach>
                        </datalist>
                    </section>
                    <footer class="modal-card-foot">
                        <button class="button is-success" type="submit">Add</button>
                    </footer>
                </form>

            </div>
        </div>
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
