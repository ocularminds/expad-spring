<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form id="_form" action="/admin/merchant" method="post">
    <input type="hidden" name="id" value="<#if merchant.getId()??>${merchant.getId()}</#if>">
    <div class="row">
        <div class="col-md-12 text-center"><h1>Merchant Detail </h1>
            <p><span class="regerror"> INFO: To edit this profile, simply change the affected fields and click the <strong>SAVE</strong> button.</span></p></div>
    </div>
    <div class="row">
        <div class="col-md-5"><b>Name :</b></div>
        <div class="col-md-7"><input class="form-control" id="name" name="name" size="15" value="<#if merchant.getName()??>${merchant.getName()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-5"><b>Short Name</b></div>
        <div class="col-md-7"><input class="form-control" id="acronymn" name="acronymn" size="35" value="<#if merchant.getAcronymn()??>${merchant.getAcronymn()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-5">Location</div>
        <div class="col-md-7"><input class="form-control" id="location" name="location" size="35" value="<#if merchant.getLocation()??>${merchant.getLocation()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-7 col-md-offset-5">
            <a class="btn btn-primary" href="/admin/merchant">Close</a>&nbsp;
            <button class="btn btn-primary" type="submit">Save</button></div>
    </div>
</form>
</@template.screen>