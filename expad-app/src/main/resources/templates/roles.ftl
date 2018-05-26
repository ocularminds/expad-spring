<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <center><h1>User Groups</h1></center>
    <table class="table table-stripped" data-pagination="true" data-flat="true" id="data-table">
        <thead>
            <tr>
                <th><a class="btn btn-primary btn-sm" href="/admin/roles/0">Add New</a></th>
                <th>Group Name</th>
                <th>Description</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <#list roles as role>
            <tr>
                <td><a href="/admin/roles/${role.getId()}" class="btn btn-default">${role.getId()}</a></td>
                <td><#if role.getName()??>${role.getName()}</#if></td>
                <td><#if role.getDescription()??>${role.getDescription()}</#if></td>
                <td><#if role.isDisabled()??>${role.isDisabled()?string("Disabled","Active")}</#if></td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>