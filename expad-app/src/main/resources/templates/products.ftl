<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <div class="text-center"><h1>Product Records</h1></div>
    <table class="table table-stripped" id="data-table" width="100%">
        <thead>
            <tr>
                <th><a class="btn btn-primary btn-sm" href="/cards/product/0">Add New</a></th>
                <th>CODE</th>
                <th>BIN</th>
                <th>NAME</th>
                <th>CURRENCY</th>
            </tr>
        </thead>
        <tbody>
            <#list products as product>
            <tr>
                <td><a href="/cards/product/${product.getId()}" class="btn btn-default">${product.getId()}</a></td>
                <td><#if product.getCode()??>${product.getCode()}</#if></td>
                <td><#if product.getBin()??>${product.getBin()}</#if></td>
                <td><#if product.getName()??>${product.getName()}</#if></td>
                <td><#if product.getCurrencyCode()??>${product.getCurrencyCode()}</#if></td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>