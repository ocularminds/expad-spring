<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form action="/admin/password" method="POST">
        <div class="row">
            <div class="col-md-12 text-center"><h1>Change Password</h1></div>
        </div>
        <div class="row">
            <div class="col-md-12 text-center"><p style="font-size:14px;color:red;">INFO: Please fill in the form below and click the submit button to change your password.</p></div>
        </div>
        <div class="row">
            <div class="col-md-12"><h3 id="info"></h3></div>
        </div>
        <div class="row">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-4"><b>USER ID:</b></div>
            <div class="col-md-4"><input class="form-control" id="userId" name="userId" size="32" value="" type="text"/><br></div>
        </div>
        <div class="row">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-4"><b>Old Password:</b></div>
            <div class="col-md-4"><input class="form-control" id="password" name="password" size="32" value="" type="password" /><br></div>
        </div>
        <div class="row">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-4"><label for="newpassword"><b>New Password</b></label></div>
            <div class="col-md-4"><input class="form-control" id="PASS1" name="password1" size="32" maxlength="31" value="" type="password" /><br></div>
        </div>
        <div class="row">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-4"><b>Verify new password:</b> </div>
            <div class="col-md-4"><input class="form-control" id="PASS2" name="password2" size="32" maxlength="31" value="" type="password" /><br></div>
        </div>
        <div class="row">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-8"><input class="form-control" id="expiry" name="expiry" value="60" type="hidden" /></div>
        </div>
        <div class="row">
            <div class="col-md-12 text-center"><p style="color:red;font-size:14px"><#if fault.getFault()??>${config.getFault()}</#if></p>
            <div class="col-md-8"><input class="form-control" id="expiry" name="expiry" value="60" type="hidden" /></div>
        </div>
        <div class="row">
            <div class="col-md-2">&nbsp;</div>
            <div class="col-md-8">
                <button type="submit" class="btn btn-primary btn-lg btn-block">Change Password</button> </div>
        </div>
</form>
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>