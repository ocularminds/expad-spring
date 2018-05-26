<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form id="_form" action="/admin/branch" method="post">
    <input type="hidden" name="id" value="<#if branch.getId()??>${branch.getId()}</#if>">
    <div class="row">
        <div class="col-md-12 text-center"><h1>Branch Detail </h1><br></div>
    </div>
    <div class="row">
        <div class="col-md-5"><b>Code</b></div>
        <div class="col-md-7"><input class="form-control" id="code" name="code" size="15" value="<#if branch.getCode()??>${branch.getCode()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-5"><b>Branch Name</b></div>
        <div class="col-md-7"><input class="form-control" id="name" name="name" size="35" value="<#if branch.getName()??>${branch.getName()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-7 col-md-offset-5">
            <a class="btn btn-primary" href="/admin/branch">Close</a>&nbsp;
            <button name="operation" type="submit" class="btn btn-primary btn-save">Save</button></div>
    </div>
</form>
</@template.screen>