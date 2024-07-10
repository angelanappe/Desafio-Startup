<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cl.praxis.models.dto.UsuarioDTO" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Acceso al sistema</h1>
        <c:if test="${not empty usuario}">
            <div class="alert alert-success" role="alert">
                Bienvenido, ${usuario.nombre} (${usuario.correo})!
            </div>
        </c:if>
        <c:if test="${empty usuario}">
            <%
                response.sendRedirect("login.jsp");
            %>
        </c:if>
    </div>
</body>
</html>