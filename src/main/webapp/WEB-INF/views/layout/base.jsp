<%@taglib prefix="layout" uri="http://callidora.lk/jsp/template-inheritance" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>${app.getString("APP_NAME")} - ${app.getString("DESCRIPTION")}</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="${BASE_URL}/assets/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="${BASE_URL}/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


    <!-- Customized Bootstrap Stylesheet -->
    <link href="${BASE_URL}/assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${BASE_URL}/assets/css/style.css" rel="stylesheet">
</head>

<body>

<!-- Spinner Start -->
<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
    <div class="spinner-grow text-primary" role="status"></div>
</div>
<!-- Spinner End -->

<!-- header -->
<jsp:include page="includes/header.jsp"/>
<!-- header -->

<!-- Modal Search Start -->
<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
        <div class="modal-content rounded-0">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex align-items-center">
                <div class="input-group w-75 mx-auto d-flex">
                    <input type="search" class="form-control p-3" placeholder="keywords"
                           aria-describedby="search-icon-1">
                    <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal Search End -->

<!-- contents-->
<layout:block name="contents">

</layout:block>
<!-- contents-->



<!-- Footer -->
<jsp:include page="includes/footer.jsp"/>
<!-- Footer -->




<!-- Back to Top -->
<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${BASE_URL}/assets/lib/easing/easing.min.js"></script>
<script src="${BASE_URL}/assets/lib/waypoints/waypoints.min.js"></script>
<script src="${BASE_URL}/assets/lib/lightbox/js/lightbox.min.js"></script>
<script src="${BASE_URL}/assets/lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="${BASE_URL}/assets/js/main.js"></script>
<script>
    const BASE_URL = '${BASE_URL}';

    function secureFetch(url, options = {}) {
        const token = localStorage.getItem("accessToken");
        const expireIn = localStorage.getItem("expireIn");
        console.log(token);

        const expireAt = new Date(Number(expireIn)).getTime();
        const expireGate = new Date().getTime() + 2 * 60 * 1000;

        if (token && expireAt && expireAt < expireGate) {
            console.log("token expired. Need new token...");
            return refreshToken().then(newToken => {
                options.headers = {
                    ...options.headers,
                    Authorization: 'Bearer ' + newToken
                }
                console.log("NEW TOKEN : " + newToken);
                return fetch(url, options);

            })
        }

        if (token) {
            console.log("Token available...");
            options.headers = {
                ...options.headers,
                Authorization: 'Bearer ' + token
            }
        } else {
            console.log("Token unavailable..");
            window.location = '${BASE_URL}login';
        }

        return fetch(url, options);

    }

    function refreshToken() {
        const oldRefreshToken = localStorage.getItem("refreshToken");
        console.log("OLD REFRESH TOKEN : " + oldRefreshToken);
        const formData = new URLSearchParams();
        formData.append('token', oldRefreshToken);

        if (oldRefreshToken) {
            return fetch('${BASE_URL}login/refresh-token', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData
            }).then(response => {
                if (!response.ok) {
                    window.location = '${BASE_URL}login';
                }
                return response.json();
            }).then(data => {
                localStorage.setItem("accessToken", data.accessToken);
                localStorage.setItem("refreshToken", data.refreshToken);
                localStorage.setItem("expireIn", data.expireIn);
                return data.accessToken;
            });
        } else {
            localStorage.removeItem("accessToken");
            localStorage.removeItem("expireIn");
            window.location = '${BASE_URL}login';
        }


    }


    <%--document.querySelector('.btn-wishlist').addEventListener('click', () => {--%>
    <%--    secureFetch('${BASE_URL}api/v1/category', {--%>
    <%--        headers: {--%>
    <%--            'Content-Type': 'application/json'--%>
    <%--        }--%>
    <%--    })--%>
    <%--        .then(response => response.text())--%>
    <%--        .then(text => {--%>
    <%--            console.log(text);--%>
    <%--        });--%>
    <%--});--%>


</script>
<layout:block name="script"></layout:block>
</body>

</html>
