<%@ page import="static dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType.SUCCESSFUL_LOGIN" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if(session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/packages");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <title>Telco - Employee Login</title>

    <style>
        /*html {
            margin: 50px;
        }*/

        body {
            text-align: center;
        }

        .column {
            text-align: center;
        }

        .or-column {
            display: flex;
            justify-content: center;
            align-content: center;
            flex-direction: column;
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            (document.querySelectorAll('.notification .delete') || []).forEach(($delete) => {
                const $notification = $delete.parentNode;

                $delete.addEventListener('click', () => {
                    $notification.parentNode.removeChild($notification);
                });
            });
        });
    </script>
</head>
<body>
<section class="hero is-info">
    <div class="hero-body">
        <p class="title">
            Telco Service &#174;
        </p>
        <p class="subtitle">
            Login - Employee Application
        </p>
    </div>
</section>

<!--<h1 class="title is-1">
    Telco Service &#174;
</h1>
<h1 class="sub-title">
    Login or Register
</h1>-->
<br><br>

<div class="columns is-centered">
    <div class="column is-3">
        <h2 class="title">
            Log In
        </h2>
        <c:if test="${pageContext.request.getAttribute('requestType') == 'USER_NOT_FOUND'}">
            <div class="notification is-danger is-light">
                <button class="delete"></button>
                User not found! Try with a different username and/or password.
            </div>
        </c:if>
        <c:if test="${pageContext.request.getAttribute('requestType') == 'USER_NOT_AUTHORIZED'}">
            <div class="notification is-danger is-light">
                <button class="delete"></button>
                User not authorized!
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login" class="box">
            <div class="field">
                <p class="control has-icons-left">
                    <label>
                        <input class="input is-primary" type="text" placeholder="Username" name="username" required>
                    </label>
                    <span class="icon is-small is-left">
                    <i class="fas fa-user"></i>
                </span>
                </p>
            </div>
            <div class="field">
                <p class="control has-icons-left">
                    <label>
                        <input class="input is-primary" type="password" placeholder="Password" name="password" required>
                    </label>
                    <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                </span>
                </p>
            </div>
            <br>
            <%
                if(request.getParameter("loginNeeded") != null) {
                    out.print("<input type='hidden' value='1' name='loginNeeded' />");
                }
            %>
            <button class="button is-primary" type="submit">
                <span class="icon is-small">
                    <i class="fas fa-sign-in-alt"></i>
                </span>
                <span>
                    Log In
                </span>
            </button>
        </form>
    </div>

    <div class="column is-1 or-column">

    </div>
    <div class="column is-3">
        Use the credentials given by the company.
    </div>


</div>
</body>
</html>