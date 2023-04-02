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
        <form class="form-inline my-2 my-lg-0" action="ListMatch" method="post">
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
    <form class="form-inline my-2 my-lg-0" method="post" action="ListMatch">
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
    <h1><span style="color: darkgreen "> Liste des Matchs</span></h1>
   <br>
    <p>Il suffit de taper le nom d'un joueur dans le barre de recheche</p>
    <p> Vous retrouverez comme données : le match auquel il a participé, son nombre de victoires/défaites ainsi que son ratio</p>
  </div>

</main><!-- /.container -->
<div class="container">
<table class="table">
  <thead>
  <th scope="col" style="width:25%">Joueur</th>
  <th scope="col" style="width:25%">Victoires</th>
  <th scope="col" style="width:20%">Défaites</th>
  <th scope="col" style="width:20%">Pourcentage Victoire(s)</th>
  </thead>
  <tbody>
  <c:if test="${empty list2 or list2 == null}">
    <tr>
      <td colspan="5" style="text-align: center"> Je n'ai trouvé aucun résultat</td>
    </tr>
  </c:if>
  <tr>
    <c:forEach items="${listVD}" var="occ" varStatus="loop">
    <th scope="row"><span style="color: red">  ${nomJoueur} </span></th>
    <td><c:out value="${occ.id_vainqueur}"> </c:out></td>
    <td><c:out value="${occ.id_finaliste}" escapeXml="true"> </c:out></td>
      <td><c:out value="${occ.id_vainqueur /  ((occ.id_vainqueur + occ.id_finaliste)) * 100}" escapeXml="true"> </c:out>%</td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<br>
  <table class="table">
    <thead>
    <tr>
      <th scope="col" style="width:10%">#</th>
      <th scope="col" style="width:25%">idEpreuve</th>
      <th scope="col" style="width:20%">Vainqueur</th>
      <th scope="col" style="width:20%">Finaliste</th>
    </tr>
    </thead>
    <tbody>

    <c:if test="${empty list2 or list2 == null }">
      <tr>
        <td colspan="5" style="text-align: center"> Je n'ai trouvé aucun résultat</td>
      </tr>
    </c:if>

    <tr>
      <c:forEach items="${list2}" var="occ" varStatus="loop">
      <th scope="row">${occ.id} </th>
      <td>${occ.id_epreuve}</td>
      <td> <c:if test="${ occ.id_vainqueur == nomJoueur}">
        <span style="color: red"><c:out value="${occ.id_vainqueur}"></span></c:out>
      </c:if>
        <c:if test="${ occ.id_vainqueur != nomJoueur}">
          <c:out value="${occ.id_vainqueur}"></c:out>
        </c:if>
      </td>
      <td>
        <c:if test="${occ.id_finaliste == nomJoueur}">
          <span style="color: red">  <c:out value="${occ.id_finaliste}" escapeXml="true"> </c:out> </span>
        </c:if>
        <c:if test="${occ.id_finaliste != nomJoueur}">
            <c:out value="${occ.id_finaliste}" escapeXml="true"> </c:out>
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



