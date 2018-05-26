<#import "micros.html" as om>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.0 Transitional//EN">
<html>
    <@om.header title="Welcome"></@om.header>
    <body style="background-color:#4a4a4a;height: 100%;">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2" style="top:10%;"><br><br><br><br>
                    <div class="login-placeholder">
                        <h6 align="right">
                            <a class ="body-text" href="#">FEEDBACK</a>&nbsp;&nbsp; | &nbsp;&nbsp;
                            <a class ="body-text" href="#">ABOUT EXPAD </a>&nbsp;&nbsp; | &nbsp;&nbsp;
                            <a class ="body-text" href="#">HELP</a>&nbsp;&nbsp; | &nbsp;&nbsp;<a class="body-text" href="#">SUPPORT</a>
                        </h6>
                        <div id= "login-placeholder" class="col-md-12">
                            <div id = "logo" class="col-md-6">
                                <p style="font-size:48px;color:#ffffff;">ExPad 1.8.0<br><span style="font-size:14px;color:#fff">Simple and Efficient</span></p>
                                <div align="center">
                                </div>
                            </div>
                            <div id = "pipe" class="col-md-1"><center><img src="/images/pipe.png"></center></div>
                            <div class="col-md-5">
                                <form name="login" action="/authenticate" method="post">
                                    <div align="center"><img class="img-circle" src="/images/logo.png" style="margin-top:25px;margin-bottom:25px;height:75px;width:75px"></div>
                                    <input type="text" class="form-control input-lg"  name="login" placeholder="Enter Your Login Id" autocomplete="off" ${locked} onChange="clearPasswordField(this.form);"><br>
                                    <input type="password" class="form-control input-lg" name="password" placeholder="Enter Your Password" autocomplete="off" ${locked}><br>
                                    <button class="btn btn-lg btn-success btn-block" type="submit" ${locked}><font size="4px"><b><i class="fa fa-lock"></i>&nbsp;Sign in</b></font></button>
                                </form>
                                <font color="#FF0000" size="2">${error}</font>
                                <p style="color:red;font-size:16px"><marquee behavior="scroll" direction="left">
                                    ${LICENSE_INFO}
                                </marquee>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="/scripts/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="/scripts/bootstrap.min.js"></script>
        <script type="text/javascript" src="/scripts/jquery.metisMenu.js"></script>
        <script type="text/javascript" src="/scripts/jquery.slimscroll.min.js"></script>
        <script type="text/javascript" src="/scripts/inspinia.js"></script>
        <script type="text/javascript" src="/scripts/pace.min.js"></script>
        <div class="footer">
            <div class="pull-center">
                <center> <strong>Copyright</strong> Ocular-Minds Software &copy; 2006-2018</center>
            </div>
        </div>
    </body>
</html>