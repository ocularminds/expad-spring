<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <table class="table table-stripped" data-pagination="true" data-flat="true" id="data-table">
        <thead>
            <tr>
                <th><a class="btn btn-primary btn-sm" href="/admin/branch/0">Add New</a></th>
                <th><strong>Name</strong></th>
            </tr>
        </thead>
        <tbody>
            <#list branches as branch>
            <tr>
                <td align="left"><a href="/admin/branch/${branch.getCode()}" class="btn btn-default">${branch.getCode()}</a></td>
                <td align="left">${branch.getName()}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>