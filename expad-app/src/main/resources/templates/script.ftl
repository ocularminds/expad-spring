<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form method="post" action="/admin/scripts">
    <div class="panel panel-default">
        <div class="panel-heading"> Configuration Scripts</div>
        <div class="panel-body">
            <div class="row">
                <div class="col-md-4">Database Vendor</div>
                <div class="col-md-8"><input name="db" type="text" class="form-control" value="<#if script.getDb()??>${script.getDb()}</#if>" size="45" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-4">Account Script</div>
                <div class="col-md-8"><textarea name="account" cols="55" rows="5" class="form-control" ><#if script.getAccount()??>${script.getAccount()}</#if></textarea><br></div>
            </div>
            <div class="row">
                <div class="col-md-4"><b>Customer Script</b></div>
                <div class="col-md-8"><textarea name="customer" cols="55" rows="5" class="form-control" ><#if script.getCustomer()??>${script.getCustomer()}</#if></textarea><br></div>
            </div>
            <div class="row">
                <div class="col-md-4">Branch Script</div>
                <div class="col-md-8"><input name="branch" type="text" class="form-control" value="<#if script.getBranch()??>${script.getBranch()}</#if>" size="57"/><br></div>
            </div>
            <div class="row">
                <div class="col-md-8 col-md-offset-4"><input class="btn btn-primary btn-lg" value="Save" type="submit"/></div>
            </div>
        </div>
</form>
</@template.screen>