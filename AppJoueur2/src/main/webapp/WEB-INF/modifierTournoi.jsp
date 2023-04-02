<!doctype html>
<html lang="fr">
<head>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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


<main role="main" class="container">
    <div>
        <p class="lead">Bienvenue <c:out value="${ConnectedUser.login}"> </c:out>
        </p>
    </div>
    <div class="starter-template">
        <h1>Modifier un tournoi</h1>
        <p class="lead"><c:if test="${!empty erreur}"><p style="color:red;"><c:out value="${erreur}"/></p></c:if>
    </div>
    <div style="width:40%; margin:auto;">

        <form class="needs-validation " method="post" action="ModifierTournoi" novalidate>
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationCustom01">Nom du tournoi</label>
                    <input type="text" class="form-control" style="width:400px;" id="validationCustom01" name="txtNom"
                           required>
                    <div class="valid-feedback">
                        Très bien!
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationCustom02">code du tournoi</label>
                    <input type="text" class="form-control" style="width:400px;" id="validationCustom02"
                           name="txtCode" required>
                    <div class="valid-feedback">
                        Très bien!
                    </div>
                </div>
            </div>
            <button class="btn btn-primary center" type="submit">Submit form</button>
        </form>

        <div>
            <div>
                <table>
                    <tbody>
                    <tr>
                        <c:forEach items="${listTournoi}" var="i" varStatus="loop">
                        <th scope="row">${i.id}</th>
                        <td><c:out value="${i.nom}"> </c:out></td>
                        <td><c:out value="${i.code}"> </c:out></td>
                        <td>
                            <button type="button" class="btn btn-outline-primary">Modifier</button>
                            <button type="button" class="btn btn-outline-warning">Supprimer</button>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
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
