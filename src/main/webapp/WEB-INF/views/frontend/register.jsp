<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Register</title>

    <!-- Custom fonts for this template-->
    <link href="${BASE_URL}/assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${BASE_URL}/assets/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-success">

<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-5 d-flex d-lg-block bg-login-image text-center mt-2 mb-2">
                    <img src="${BASE_URL}/assets/img/loginImage.jpg" height="400" >
                </div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                        </div>
                        <form class="user">
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input type="text" class="form-control form-control-user" id="inputFirstName"
                                           placeholder="First Name">
                                </div>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control form-control-user" id="inputLastName"
                                           placeholder="Last Name">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                <input type="email" class="form-control form-control-user" id="inputEmail"
                                       placeholder="Email Address">
                                </div>
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input type="contact" class="form-control form-control-user" id="inputContact"
                                           placeholder="Contact No">
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <input type="password" class="form-control form-control-user"
                                           id="inputPassword" placeholder="Password">
                                </div>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control form-control-user"
                                           id="exampleRepeatPassword" placeholder="Repeat Password">
                                </div>
                            </div>
                            <a href="#" class="btn btn-success btn-user btn-block btn-user-sign-up">
                                Register Account
                            </a>

                        </form>
                        <hr>
                        <div class="text-center">
                            <a class="small" href="${BASE_URL}/login">Already have an account? Login!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    // document.getElementsByClassName('btn-sign-up').item(0).addEventListener('click', function () {
    document.querySelector('.btn-user-sign-up').addEventListener('click', function () {

        let firstName = document.getElementById("inputFirstName").value;
        let lastName = document.getElementById("inputLastName").value;
        let email = document.getElementById('inputEmail').value;
        let contact = document.getElementById('inputContact').value;
        let password = document.getElementById('inputPassword').value;

        fetch('${BASE_URL}api/v1/auth/signUp', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName,
                lastName,
                email,
                password,
                contact
            })
        }).then(async response => {
            if (response.ok) {
                alert("Registration success. Please check your email inbox for verification link");
                window.location.href = "${BASE_URL}signIn";
            } else {
                // throw new Error();
                // return response.text();
                return null;
            }
            return await response.text();

        })
            .then(data => {
                if (!data) {
                    alert("Registration failed");
                }
            })
            .catch(error => {
               alert(error + "Error");
            });

    });
</script>
<!-- Bootstrap core JavaScript-->
<script src="${BASE_URL}/assets/vendor/jquery/jquery.min.js"></script>
<script src="${BASE_URL}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${BASE_URL}/assets/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${BASE_URL}/assets/js/sb-admin-2.min.js"></script>

</body>

</html>
