<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <center><h1>Merchant Records</h1></center>
    <table class="table table-stripped" data-pagination="true" data-flat="true" id="data-table">
        <thead>
            <tr>
                <th><a class="btn btn-primary btn-sm" href="/admin/merchant/0">Add New</a></th>
                <th>SHORT NAME</th>
                <th>PROFILE NAME</th>
                <th>LOCATION</th>
            </tr>
        </thead>
        <tbody>
            <#list merchants as merchant>
            <tr>
                <td><a href="/admin/merchant/${merchant.getId()}" class="btn btn-default">${merchant.getId()}</a></td>
                <td>${merchant.getAcronymn()}</td>
                <td>${merchant.getName()}</td>
                <td><#if merchant.getLocation()??>${merchant.getLocation()}</#if></td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>