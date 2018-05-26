<html>
    <head>
        <title>ExPad : Instant Card Issuance Solution | Login</title>
        <meta http-equiv=Content-Type content="text/html; charset=utf-8">
        <meta content="MSHTML 6.00.2900.2769" name=GENERATOR>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/main.css" rel="stylesheet">
        <link href="favicon.ico" rel="SHORTCUT ICON">
        <link href="favicon.ico" type=image/ico rel=icon>
        <script language="javascript" src="/scripts/jsrsClient_rev.js"></script>
        <script>
            var obj;
            function encryptFormField(obj) {
                this.obj = obj;
                var val = URLencode(obj.value);
                jsrsExecute("remoteMethodFactory.do", getEncrptionCallback, "encryptString", val);
            }
            function getEncrptionCallback(msg) {
                obj.value = msg;
            }
            function getEncrptionCallback(msg) {
                obj.value = msg;
            }
            function clearPasswordField(form) {
                form.password.value = "";
            }
        </script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-md-4 col-md-offset-4">
                    <h1 class="text-center login-title">Sign in to continue to ExPad</h1>
                    <div class="account-wall">
                        <img class="profile-img" src="images/logo.png"
                             alt="">
                        <form class="form-signin" name="login"  action="/authenticate" method="post">
                            <input name="userid" type="text" class="form-control input-lg" placeholder="Email" required autofocus autocomplete="off" ${locked} onChange="clearPasswordField(this.form);"><br>
                            <input name="password" type="password" class="form-control input-lg" placeholder="Password" required>
                            <button class="btn btn-lg btn-primary btn-block" type="submit">
                                Sign in</button>
                            <font color="#FF0000" size="2">${error}</font>
                            <p style="color:red;font-size:16px"><marquee behavior="scroll" direction="left">
                                ${LICENSE_INFO}
                            </marquee>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>