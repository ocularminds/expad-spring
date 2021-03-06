<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <h1>Cards Awaiting Approval Records</h1>
    <table class="table table-stripped" id="data-table" width="100%">
        <thead>
            <tr>
                <th>Serial No</th>
                <th>Cardholder</th>
                <th>Pan</th>
                <th>Staff Id</th>
                <th>Date</th>
                <th>Status</th>
            </tr>
        </thead>
		<tbody>
	<#list cards as card>
	<tr>
		<td><a href="/cards/approval/" class="btn btn-primary btn-view">${card.getId()}</a></td>
		<td>${card.getPreferedName()}</td>
		<td>${card.getPan()}</td>
		<td><#if card.getUserId()??>${card.getUserId()}</#if></td>
		<td><#if card.getCreateDate()??>${card.getCreateDate()}</#if></td>
		<td>${card.getStatus()}</td>
	 </tr>
	 </#list>
     </tbody>
    </table>
</div>
<form id="_form" action="/cards/products" method="POST" style="display:none;">
<input type="hidden" name="id" value=""/>
<div class="row">
    <div class="col-md-3"><b>Role Name</b></div>
    <div class="col-md-3"><input class="form-control" id="rolename" name="rolename" size="32" maxlength="42" value="" type="text" /></div>
</div>
<div class="row">
    <div class="col-md-3"><b>Description</b></div>
    <div class="col-md-3"><input class="form-control" id="desc" name="desc" size="32" maxlength="42" value="" type="text" /></div>
</div>
<div class="row">
    <div class="col-md-12 text-center">
        <input name="operation" type="button" class="btn btn-primary"  id="operation"  value="Approve"/>
        <input name="operation" type="button" class="btn btn-primary"  id="operation"  value="Reject"/>
        <input name="Button" type="button"  class="btn btn-primary" value="Close" onclick="window.location= '/main'"/></div>
</div>
</form>
<script type="text/javascript">
$(document).ready(function(){
	$('#data-table').DataTable({"processing": true,"deferLoading": 57 });
   $("#_form").hide();
   $('.detail-link').click(function(e){
	   e.preventDefault();
	   var endpoint = $(this).attr('href');
	   $.ajax({type:"GET",url:endpoint,dataType : 'json',
			success: function(c) {
				if(c != null || c.id != 'NA'){
					$.each(c,function(key,value) {
						$("#_form").find("input[name='"+key+"']").val(value);
					});
					$("#table-list").hide();
					$("#_form").show();
				}
			 }
		});
    });
});
</script>
</@template.screen>