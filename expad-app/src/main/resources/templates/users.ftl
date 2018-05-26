<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <center><h1>User Records</h1></center>
    <table class="table table-stripped" id="data-table" width="100%">
        <thead>
            <tr>
                <th><a class="btn btn-primary btn-sm" href="/admin/users/0">Add New</a></th>
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
                <td><a href="/admin/users/${user.getId()}" class="btn btn-default">${user.getId()}</a></td>
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
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>