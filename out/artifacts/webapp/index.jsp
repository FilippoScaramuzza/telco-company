<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if(session.getAttribute("user") != null && request.getParameter("request_type").equals("SUCCESSFUL_LOGIN")) {
        //response.sendRedirect(request.getContextPath() + "/packages");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>

    <title>Telco - Login or Register</title>

    <style>
        html {
            margin: 50px;
        }

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

<h1 class="title is-1">
    Telco Company &#174;
</h1>
<h1 class="sub-title">
    Login or Register
</h1>

<br><br>

<div class="columns is-centered">
    <div class="column is-3">
        <h2 class="title">
            Log In
        </h2>

        <c:if test="${pageContext.request.getParameter('request_type') == 'USER_NOT_FOUND'}">
            <div class="notification is-danger is-light" style="width: 300px;">
                <button class="delete"></button>
                User not found! Try with a different username and/or password.
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
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

    <div class="column is-2 or-column">
        <p>or</p>
    </div>
    <div class="column is-3">
        <h2 class="title">
            Register
        </h2>
        <form>
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
                        <input class="input is-primary" type="email" placeholder="Email" name="email" required>
                    </label>
                    <span class="icon is-small is-left">
                    <i class="fas fa-envelope"></i>
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
            <div class="field">
                <p class="control">
                    <label>
                        <input class="input is-primary" type="text" placeholder="Name (Optional)" name="name">
                    </label>
                </p>
            </div>
            <div class="field">
                <p class="control">
                    <label>
                        <input class="input is-primary" type="text" placeholder="Surname (Optional)" name="surname">
                    </label>
                </p>
            </div>
            <br>
            <button class="button is-primary" type="submit">
                <span class="icon is-small">
                    <i class="fas fa-user-plus"></i>
                </span>
                <span>
                    Sign Up
                </span>
            </button>
        </form>
    </div>


</div>
</body>
</html>