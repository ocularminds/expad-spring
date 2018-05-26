<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <center><h1>User Records</h1></center>
    <table class="table table-stripped" id="data-table" width="100%">
        <thead>
            <tr>
                <th><input name="operation" type="button" class="btn btn-primary btn-sm"  value="Add New" id="btn-add"></th>
                <th>Staff ID</th>
                <th>Full Name</th>
                <th>Role</th>
                <th>Branch</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
            <tr>
                <td><a href="/expad/services/admin/api/users/${user.getId()}" class="btn btn-default detail-link">${user.getId()}</a></td>
                <td><#if user.getStaffCode()??>${user.getStaffCode()}</#if></td>
                <td><#if user.getFullName()??>${user.getFullName()}</#if></td>
                <td><#if user.getRole()??>${user.getRole()}</#if></td>
                <td><#if user.getBranch()??>${user.getBranch()}</#if></td>
                <td><#if user.isDisabled()??>${user.isDisabled()?string("Disabled","Active")}</#if></td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<form id="_form" action="/expad/services/admin/api/users" style="display:none;"  method="POST">
    <input type="hidden" name="id" value="" />
    <div class="row"><div class="col-md-12"><b>User Details </b></div></div>
    <div class="row">
        <div class="col-md-12">
            <p><span class="regerror"> INFO: Please fill in the form below and click the submit button to set up the user. </span></p>
            <p>To reset your password please answer your security question, then type in and verify your new password.To learn what is acceptable as a password, see <a href="#">guidelines for <strong>eXPad</strong> IDs and passwords</a>.</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3"><strong>Title</strong></div>
        <div class="col-md-3"><select name="title" class="categories"></select></div>
        <div class="col-md-3"><strong>User ID</strong></div>
        <div class="col-md-3"><input class="form-control" id="staffCode" name="staffCode" size="32" maxlength="42" value="" type="text" /></div>
    </div>
    <div class="row">
        <div class="col-md-3"><b>First Name</b></div>
        <div class="col-md-3"><input class="form-control" name="firstName" size="32" maxlength="42" value="" type="text" /></div>
        <div class="col-md-3"><strong>Surname</strong></div>
        <div class="col-md-3"><input class="form-control" name="lastName" size="32" maxlength="42" value="" type="text" /></div>
    </div>
    <div class="row">
        <div class="col-md-3"><b>Other names</b></div>
        <div class="col-md-3"><input class="form-control"name="otherName" size="32" maxlength="42" value="" type="text" /></div>
        <div class="col-md-3"><b>Branch</b></div>
        <div class="col-md-3"><select name="branch" class="categories">
                <#list branches as branch>
                <option value="${branch.getCode()}">${branch.getName()}</option>
                </#list>
            </select></div>
    </div>
    <div class="row">
        <div class="col-md-3"><strong>Email</strong></div>
        <div  class="col-md-3"><input class="form-control" id="email" name="email" size="40" maxlength="40" value="" type="text" /></div>
        <div class="col-md-3">Role</div>
        <div  class="col-md-3">
            <select name="role" id="role" class="categories">
                <#list roles as role>
                <option value="${role.getId()}">${role.getName()}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3"><label for="newpassword"><b><span class="regerror">New password:</span></b><br />
                <span class="fnt" style="color: rgb(102, 102, 102);">(Minimum 6 characters)</span></label></div>
        <div  class="col-md-3"><input class="form-control" id="PASS1" name="PASS1" size="32" maxlength="31" value="ocular-minds" type="password" /></div>
        <div class="col-md-3"><b>Verify new password</b> </div>
        <div  class="col-md-3"><input class="form-control" id="PASS2" name="PASS2" size="32" maxlength="31" value="ocular-minds" type="password" /></div>
    </div>
    <div class="row">
        <div  class="col-md-3"><strong>Password Expiry</strong>[ in days] </div>
        <div  class="col-md-3"><input class="form-control" id="expiry" name="expiry" size="5" maxlength="5" value="60" type="text" /></div>
        <div  class="col-md-3"><strong>View Only ?
            </strong></div>
        <div  class="col-md-3"> <input name="readonly" type="radio" value="Y" />Yes&nbsp;
            <input name="readonly" type="radio" value="N" checked/>No</div>
    </div>
    <div class="row">
        <div class="col-md-3">**</div>
        <div  class="col-md-3">
            <strong>Disabled ?
            </strong>
            <input name="disabled" type="radio" value="Y" />
            Yes
            <input name="disabled" type="radio" value="N" checked/>
            No
        </div>
        <div  class="col-md-3">
            <input name="operation" type="button" class="btn btn-primary btn-save"  value="Save"/>&nbsp;
            <a href="/expad/services/admin/users" class="btn btn-primary btn-close">Close</a>
        </div>
    </div>
</form>
<script language="javascript" src="/expad/scripts/form.js"></script>
</@template.screen>