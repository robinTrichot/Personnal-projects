<!doctype html>
<html lang="fr">
<head>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/starter-template.css">
    <title>Hello, world!</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="#">Menu</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">Ajouter</a>
                <div class="dropdown-menu" aria-labelledby="dropdown01">
                    <a class="dropdown-item" href="ajouterJoueur">Ajouter joueur</a>
                    <a class="dropdown-item" href="AjouterTournoi">Ajouter tournoi</a>

                </div>
            </li>

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

    </div>
</nav>


<main role="main" class="container">
    <div>
        <p class="lead">Bienvenue <c:out value="${ConnectedUser.login}"> </c:out>
        </p>
    </div>
    <div class="starter-template">
        <h1>Ajouter un joueur</h1>
        <p class="lead"><c:if test="${!empty erreur}"><p style="color:red;"><c:out value="${erreur}"/></p></c:if>
    </div>

    <div style="width:40%; margin:auto;">

        <form class="needs-validation " method="post" action="ajouterJoueur" novalidate>
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationCustom01">Nom du joueur</label>
                    <input type="text" class="form-control" style="width:400px;" id="validationCustom01" name="txtNom"
                           required>
                    <div class="valid-feedback">
                        Très bien!
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationCustom02">Prénom du joueur</label>
                    <input type="text" class="form-control" style="width:400px;" id="validationCustom02"
                           name="txtPrenom" required>
                    <div class="valid-feedback">
                        Très bien!
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-3 mb-3">
                    <label for="validationCustom04">Sexe</label>
                    <select class="custom-select" id="validationCustom04" name="opSexe" style="width:400px;" required>
                        <option selected disabled>Sélectioner...</option>
                        <option value="F">Femme</option>
                        <option value="H">Homme</option>
                    </select>
                    <div class="valid-feedback">
                        Très bien!
                    </div>
                    <div class="invalid-feedback">
                        Veuillez choisir un sexe!
                    </div>
                </div>
            </div>

            <button class="btn btn-primary center" type="submit">Submit form</button>
        </form>


</main><!-- /.container -->

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>


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
</body>
</html>
