<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form action="" method="post" id="_form_upload">
    <div class="row"><div class="col-md-12 text-center"><h1>Bulk Card Upload</h1></div></div>
    <div class="row">
        <div class="col-md-4 col-md-offset-1"><strong>Card Product </strong></div>
        <div class="col-md-6">
            <select name="productCode" id="productCode" class="form-control">
                <#list products as product>
                <option value="${product.getCode()}">${product.getName()}</option>
                </#list>
            </select><br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-1"><strong>Select Network</strong></div>
        <div class="col-md-6">
            <select id="network" name="network" class="form-control">
                <option selected value="interswitch">Interswitch</option>
                <option value="etranzact">eTranzact</option>
            </select><br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-1"><strong>Choose Record Type</strong></div>
        <div class="col-md-6">
            <select id="type" name="type" class="form-control">
                <option selected value="excel">Accounts Record</option>
                <option value="xml">Students Record</option>
            </select><br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-offset-1"><strong>Select File</strong></div>
        <div class="col-md-6"><input type="file" name="file" class="btn btn-primary"/>
        </div>
    </div>
    <div class="row"><div class="col-md-10 col-md-offset-1" id="info"></div></div>
        <div class="row">
            <div class="col-md-12 text-center"><br>
                <a class="btn btn-default btn-lg" href="/dashboard">Close</a>&nbsp;
                <input name="but" class="btn btn-primary btn-lg" value="Continue" type="submit" id="btn-upload"/>
            </div>
        </div>
</form>
<table class="table table-striped">
    <thead>
        <tr><th colspan="2" align="center">Upload Exception Report</th></tr>
        <tr><th>(S/No)</th><th>Account No</th><th>Error Type</th></tr>
    </thead>
    <tbody id="errors"></tbody>
</table>
<script language="javascript" src="/scripts/upload.js"></script>
</@template.screen>