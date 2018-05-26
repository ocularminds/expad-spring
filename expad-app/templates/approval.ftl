<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<div class="table-responsive" id="table-list">
    <center><h1>Cards Awaiting Approval Records</h1></center>
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
                <td><a href="/expad/services/cards/api/approval/${card.getId()}" class="btn btn-primary card-link">${card.getId()}</a></td>
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
<form id="_form" action="" method="POST" style="display:none;">
    <input type="hidden" name="id" value=""/>
    <div class="row"><div class="col-md-12" align="center"><h1>Customer Card Detail </h1></div></div>
    <div class="row"><div class="col-md-12" align="center"><p>Please supply the account no and click the search button.</p></div></div>
    <div class="row">
        <div class="col-md-4">Customer Name</div>
        <div class="col-md-6"><input class="form-control" name="customerName" size="32" maxlength="42" value="" type="text" /></div>
    </div>
    <div class="row">
        <div class="col-md-4">Card No: </strong></div>
        <div class="col-md-6"><input class="form-control" name="pan" size="32" maxlength="42" value="" type="text" /></div>
    </div>
    <div class="row">
        <div class="col-md-4">Serial No</div>
        <div class="col-md-6"><input class="form-control" id="serial" name="id" size="15" maxlength="42" value="" type="text" /></div>
        <div class="col-md-2"><strong>LEGEND</strong></div>
    </div>
    <div class="row">
        <div class="col-md-4">Pin</div>
        <div class="col-md-6"><strong>XXXX</strong></div>
        <div class="col-md-2"><span class="error"><strong>O</strong></span> - Issued </div>
    </div>
    <div class="row">
        <div class="col-md-4">STATUS</div>
        <div class="col-md-6"><input name="status"></div></div>
    <div class="col-md-2"><span class="error"><strong>D</strong></span> - processed Activated</div>
</div>
<div class="row">
    <div class="col-md-4">Customer ID :</div>
    <div class="col-md-6"><input class="form-control" id="customerId" size="15" maxlength="42" value="" type="text" /></div>
    <div class="col-md-2"><strong class="error">A</strong> - Approved </div>
</div>
<div class="row">
    <div class="col-md-4"><b>Collection Center</b></div>
    <div class="col-md-6"><select name="collectingSol" class="form-control">
            SELECT SOL_ID,SOL_DESC FROM SOL ORDER BY 2</select></div>
    <div class="col-md-2"><strong class="error">R</strong> - Rejected </div>
</div>
<div class="row">
    <div class="col-md-4">Phone No :</div>
    <div class="col-md-6"><input class="form-control" id="phone" name="phone" size="32" maxlength="42" value="" type="text" /></div>
</div>
<div class="row">
    <div class="col-md-4">Email :</div>
    <div class="col-md-6"><input class="form-control" id="email" name="email" size="32" maxlength="42" value="" type="text" /></div>
</div>
<div class="row"><div class="col-md-12" align="center">Linked Accounts :</div></div>
<div class="row">
    <div class="col-md-12" align="center">
        <ul class="list-group" id="account-link"></ul>
    </div>
</div>
<div class="row">
    <div class="col-md-8 col-md-offset-4"><input type="button" name="Button" value="Close" onclick="window.close()" class="btn btn-default"/>
        <input type="submit" name="operation" value="Approve" class="btn btn-success"/>&nbsp;
        <input type="submit" name="operation" value="Reject" class="btn btn-danger" /></div>
</div>
</form>
<script language="javascript" src="/expad/scripts/form.js"></script>
</@template.screen>