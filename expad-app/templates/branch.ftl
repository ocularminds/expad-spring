<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script type="text/javascript">$('#data-table').DataTable({"processing": true, "deferLoading": 57});</script>
<div class="table-responsive" id="table-list">
    <table class="table table-stripped" data-pagination="true" data-flat="true" id="data-table">
        <thead>
            <tr>
                <th><input name="operation" type="button" class="btn btn-primary btn-sm"  value="Add New" onclick="window.location = '/expad/services/admin/branch"/></th>
                <th><strong>Name</strong></th>
            </tr>
        </thead>
        <tbody>
            <#list branches as branch>
            <tr>
                <td align="left"><a href="/expad/services/admin/api/branch/${branch.getCode()}" class="btn btn-default detail-link">${branch.getCode()}</a></td>
                <td align="left">${branch.getName()}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</div>
<form id="_form" action="/expad/services/admin/merchant" method="post" style="display:none;">
    <input type="hidden" name="id" value="">
    <div class="row">
        <div class="col-md-12"><strong>Branch Detail </strong></div>
    </div>
    <div class="row">
        <div class="col-md-5"><b>Code</b></div>
        <div class="col-md-7"><input class="form-control" id="code" name="code" size="15" value="" type="text" /></div>
    </div>
    <div class="row">
        <div class="col-md-5"><b>Branch Name</b></div>
        <div class="col-md-7"><input class="form-control" id="name" name="name" size="35" value="" type="text" /></div>
    </div>
    <div class="row">
        <div class="col-md-7 col-md-offset-5">
            <input name="Button" type="button" class="btn btn-primary" value="Close" onclick="window.location = '/expad/services/admin/branch'"/>&nbsp;
            <button name="operation" type="button" class="btn btn-primary btn-save">Save</button></div>
    </div>
</form>
<script>
    $(document).ready(function () {
        $('#data-table').DataTable({"processing": true, "deferLoading": 57});
        $('.detail-link').click(function (e) {
            e.preventDefault();
            var endpoint = $(this).attr('href');
            $.ajax({type: "GET", url: endpoint, dataType: 'json',
                success: function (c) {
                    if (c != null || c.id != 'NA') {
                        $.each(c, function (key, value) {
                            $("#_form").find("input[name='" + key + "']").val(value);
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