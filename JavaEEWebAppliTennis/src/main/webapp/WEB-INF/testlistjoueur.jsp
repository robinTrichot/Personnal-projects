<!doctype html>
<html lang="fr">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <!-- Required meta tags -->
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/starter-template.css">
    <title>Hello, world!</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="">Menu</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <c:if test="${sessionScope.isAdmin == true }">


            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">Ajouter</a>
                <div class="dropdown-menu" aria-labelledby="dropdown01">
                    <a class="dropdown-item" href="ajouterJoueur">Ajouter joueur</a>
                    <a class="dropdown-item" href="AjouterTournoi">Ajouter tournoi</a>

                </div>
            </li>
            </c:if>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">Lister</a>
                <div class="dropdown-menu" aria-labelledby="dropdown01">
                    <a class="dropdown-item" href="ListJoueur"><span style="color: teal">  Lister joueurs </span></a>
                    <a class="dropdown-item" href="ListTournoi"><span style="color: orange ">  Lister tournois </span></a>
                    <a class="dropdown-item" href="ListMatch"><span style="color: darkgreen ">  Lister matchs </span></a>
                    <a class="dropdown-item" href="ListEpreuve"><span><b> Lister epreuves </b></span></a>
                </div>
            </li>
            <li class="nav-item">
                <form class="form-inline my-2 my-lg-0" action="ListJoueur" method="post">
                    <button class="btn btn-secondary my-2 my-sm-0" type="submit" name="action1" value="Deconnexion">
                        Deconnexion
                    </button>
                </form>
            </li>

            <li class="nav-item" id="profil">
                <a class="navbar-brand" href="" style="color: red;  margin-left: 10px">
                    Profil : <c:out value="${ConnectedUser.profil == 43 ? 'Admin' : 'Utilisateur'}"></c:out>
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                        aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </li>

        </ul>
        <form class="form-inline my-2 my-lg-0" method="post" action="ListJoueur">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="txtSearch">
            <button class="btn btn-secondary my-2 my-sm-0" type="submit" name="action1" value="Rechercher">Search
            </button>
        </form>
    </div>
</nav>


<main role="main" class="container">
    <div>
        <p class="lead">Bienvenue <c:out value="${ConnectedUser.login}"> </c:out>
        </p>
    </div>
    <div class="starter-template">
        <h1> <span style="color: teal"> Liste des joueurs</span></h1>
        <br>
<p> Voici une liste non exhaustive des joueurs de tennis (Homme/Femme) ayant participés à différents tournois internationaux</p>
    </div>

</main><!-- /.container -->
<div class="container">
<c:if test="${sessionScope.isAdmin == true}">
    <div style="    padding: 1.5rem;    margin-right: 0;    margin-left: 0;    border-width: .2rem;">
        <a type="button" class="btn btn-primary" href="ajouterJoueur">Ajouter joueur</a>
    </div>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th scope="col" style="width:10%">#</th>
            <th scope="col" style="width:25%">Nom</th>
            <th scope="col" style="width:20%">Prenom</th>
            <th scope="col" style="width:20%">Sexe</th>
            <th scope="col" style="width:20%"></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty list2 or list2.size() == 0}">
            <tr>
                <td colspan="5" style="text-align: center"> Je n'ai trouvé aucun résultat</td>
            </tr>
        </c:if>

        <tr>
            <c:forEach items="${list2}" var="i" varStatus="loop">
            <th scope="row">${i.id} </th>
            <td>${i.prenom}</td>
            <td><c:out value="${i.nom}"> </c:out></td>
                <c:if test="${i.sexe == 'F'}">
            <td> <span style="color: red"> <c:out value="${i.sexe}" escapeXml="true"> </c:out> </span> </td>
                </c:if>
                <c:if test="${i.sexe == 'H'}">
                    <td> <span style="color: blue"> <c:out value="${i.sexe}" escapeXml="true"> </c:out> </span> </td>
                </c:if>
            <td>
                <c:if test="${sessionScope.isAdmin == true }">
                    <a type="button" class="btn btn-outline-primary" href="ModifierJoueur?id=${i.id}" role="button">
                        Modifier </a>
                    <a type="button" class="btn btn-outline-warning" href="SupprimerJoueur?id=${i.id}" role="button">
                        Supprimer </a>
                </c:if>

                <c:if test="${sessionScope.isAdmin == false }">
                    Vous n'avez pas accés aux modifications
                </c:if>
            </td>
        </tr>
        </c:forEach>

        </tbody>
    </table>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</div>
</body>
</html>


