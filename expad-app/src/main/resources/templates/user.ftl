<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form id="_form" action="/admin/users"  method="POST">
    <input type="hidden" name="id" value="<#if editableUser.getId()??>${editableUser.getId()}</#if>" />
    <div class="row"><div class="col-md-12 text-center"><h1>User Details </h1></div></div>
    <div class="row">
        <div class="col-md-12">
            <p><span class="label labe-error"> INFO: Please fill in the form below and click the submit button to set up the editableUser. </span></p>
            <p>To reset your password please answer your security question, then type in and verify your new password.To learn what is acceptable as a password, see <a href="#">guidelines for <strong>ExPad</strong> IDs and passwords</a>.</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3"><strong>User ID</strong></div>
        <div class="col-md-3"><input class="form-control" name="staffCode" size="32" maxlength="42" value=""<#if editableUser.getStaffCode()??>${editableUser.getStaffCode()}</#if>" type="text" /><br></div>
        <div class="col-md-3"><b>Full Name</b></div>
        <div class="col-md-3"><input class="form-control" name="fullName" size="32" maxlength="42" value="<#if editableUser.getFullName()??>${editableUser.getFullName()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-3">Role</div>
	  <div  class="col-md-3">
	    <select name="role" id="role" class="form-control">
		<#list roles as role>
		<option value="${role.getId()}">${role.getName()}</option>
		</#list>
	    </select>
        </div>
        <div class="col-md-3"><b>Branch</b></div>
        <div class="col-md-3">
           <select name="branch" class="form-control">
                <#list branches as branch>
                <option value="${branch.getCode()}">${branch.getName()}</option>
                </#list>
            </select><br>
         </div>
    </div>
    <div class="row">
        <div class="col-md-3"><strong>Email</strong></div>
        <div  class="col-md-3"><input class="form-control" id="email" name="email" size="40" maxlength="40" value="" type="text" /></div>
        <div  class="col-md-3"><strong>Password Expiry</strong>[ in days] </div>
        <div  class="col-md-3"><input class="form-control" id="expiry" name="expiry" size="5" maxlength="5" value="60" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-3"><label for="newpassword"><b><span class="regerror">New password:</span></b><br />
                <span class="fnt" style="color: rgb(102, 102, 102);">(Minimum 6 characters)</span></label></div>
        <div  class="col-md-3"><input class="form-control" id="PASS1" name="password" size="32" maxlength="31" value="ocular-minds" type="password" /></div>
        <div class="col-md-3"><b>Verify new password</b> </div>
        <div  class="col-md-3"><input class="form-control" id="PASS2" name="password2" size="32" maxlength="31" value="ocular-minds" type="password" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-3">**</div>
        <div  class="col-md-3">
            <strong>Disabled ?
            </strong>
            <input name="disabled" type="radio" value="true" />
            Yes
            <input name="disabled" type="radio" value="false" checked/>
            No
        </div>
        <div  class="col-md-3">
            <input name="operation" type="submit" class="btn btn-primary btn-save"  value="Save"/>&nbsp;
            <a href="/admin/users" class="btn btn-primary btn-close">Close</a>
        </div>
    </div>
</form>
</@template.screen>