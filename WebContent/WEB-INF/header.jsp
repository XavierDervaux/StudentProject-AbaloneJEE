<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
    	<meta charset="utf-8" />
       	<title>Abalone</title>
       	<link rel="icon" type="image/png" href="ressources/style/img/favicon.png">
        <link rel="stylesheet" href="ressources/style/bootstrap.min.css"> 
   		<link rel="stylesheet" href="ressources/style/style.css">
   		
   		<script type="text/javascript" src="ressources/js/jquery-3.1.1.min.js"></script> 
   		<script type="text/javascript" src="ressources/js/bootstrap.min.js"></script>
   		<script type="text/javascript" src="ressources/js/abalone.js"></script> 
    </head> 
    <body>
    	 <header>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">
                    <img src="ressources/style/img/logo_header.png" width="140"/>
                    </a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                   		<c:if test="${not empty joueur}"> 
                   		 	<li><a>Bienvenue ${joueur.getPseudo()}</a></li>
                   		</c:if>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <body onload="init();">