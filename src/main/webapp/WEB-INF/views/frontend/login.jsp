<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Login</title>

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

  <!-- Outer Row -->
  <div class="row justify-content-center">

    <div class="col-xl-10 col-lg-12 col-md-9">

      <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
          <!-- Nested Row within Card Body -->
          <div class="row">
            <div class="col-lg-6 d-flex d-lg-block bg-login-image text-center mt-2 mb-2">
              <img src="${BASE_URL}/assets/img/loginImage.jpg" height="400" >
            </div>
            <div class="col-lg-6">
              <div class="p-5">
                <div class="text-center">
                  <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                </div>
                <form class="user">
                  <div class="form-group">
                    <input type="email" class="form-control form-control-user"
                           id="inputEmail" aria-describedby="emailHelp"
                           placeholder="Enter Email Address...">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-user"
                           id="inputPassword" placeholder="Password">
                  </div>
                  <div class="form-group">
                    <div class="custom-control custom-checkbox small">
                      <input type="checkbox" class="custom-control-input" id="customCheck">
                      <label class="custom-control-label" for="customCheck">Remember
                        Me</label>
                    </div>
                  </div>
                  <a href="#" class="btn btn-primary btn-user btn-block user-btn-sign-in">
                    Login
                  </a>
                </form>
                <hr>
                <div class="text-center">
                  <a class="small" href="${BASE_URL}forgot-password">Forgot Password?</a>
                </div>
                <div class="text-center">
                  <a class="small" href="${BASE_URL}register">Create an Account!</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>

</div>



  <script type="text/javascript">
    document.querySelector('.user-btn-sign-in').addEventListener('click', () => {
      let email = document.getElementById('inputEmail').value;
      let password = document.getElementById('inputPassword').value;


      fetch('${BASE_URL}api/v1/auth/signIn', {
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: email,
          password: password
        })
      })
              .then(async response => {
                // console.log(response);
                if (!response.ok) {
                  alert("Sign In failed");
                  // console.log(JSON.stringify(response.json()));
                  return null;
                }
                return response.json();
              })
              .then(async data => {
                if (data) {
                  console.log("DATA ::::", JSON.stringify(data));
                  localStorage.setItem("accessToken", data?.data?.tokens?.accessToken);
                  localStorage.setItem("refreshToken", data?.data?.tokens?.refreshToken);
                  localStorage.setItem("user", data?.data?.user);

                  alert("Login success");
                  window.location.href = '${BASE_URL}';
                }
                // let obj=JSON.parse(data);
                // localStorage.setItem("accessToken", data.accessToken);
                // localStorage.setItem("refreshToken", data.refreshToken);
                // localStorage.setItem("expireIn", data.expireIn);
                //
                // localStorage.setItem("token", JSON.stringify(data));


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
