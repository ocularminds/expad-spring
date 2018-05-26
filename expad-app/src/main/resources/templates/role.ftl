<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form action="/admin/roles" method="POST">
    <input type="hidden" name="id" value="<#if role.getId()??>${role.getId()}</#if>"/>
    <div class="row"><div class="col-md-12 text-center"><h1>Role Details</h1></div></div>
    <div class="row">
        <div class="col-md-3"><b>Role Name</b></div>
        <div class="col-md-3"><input class="form-control" id="name" name="name" size="32" maxlength="42" value="<#if role.getName()??>${role.getName()}</#if>" type="text" /><br></div>
        <div class="col-md-3"><b>Disabled</b></div>
        <div class="col-md-3"><select name="disabled" id="disabled" class="form-control">
                <option value="true">Yes</option>
                <option value="false">No</option>
            </select></div>
    </div>
    <div class="row">
        <div class="col-md-3"><b>Description</b></div>
        <div class="col-md-9"><input class="form-control" id="description" name="description" size="32" maxlength="42" value="<#if role.getDescription()??>${role.getDescription()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-12 text-center">
            <a href="/admin/roles" class="btn btn-primary">Close</a>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>
    <div class="row">
    <div class="col-md-3">&nbsp;</div>
        <div class="col-md-9" style="margin-top:25px;">
            <select id="basic" name="functions" multiple="multiple" class="form-control">
		<#list available as a>
		    <option value="${a.getId()}">${a.getDescription()}</option>
                </#list>
		<#list picked as f>
		   <option value="${f.getId()}" selected="selected">${f.getDescription()}</option>
                </#list>
            </select>
        </div>
    </div>
</form>
<script type="text/javascript" src="/lib/picklist/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/lib/picklist/jquery-picklist.js"></script>
<script type="text/javascript">
$(function(){
   $("#basic").pickList();
});
</script>
<link type="text/css" href="/lib/picklist/jquery-picklist.css" rel="stylesheet" />
<script language="javascript" src="/scripts/form.js"></script>
</@template.screen>