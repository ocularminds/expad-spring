<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<script language="javascript" src="/scripts/card.js"></script>
<form id="_form" method="post" action="/cards/order">
    <div class="row">
        <div class="col-md-12"><b>Please supply the account no and click the search button</b>.
            <p id="info" class="label label-danger" style="font-size:14px"></p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-7">
            <div class="panel panel-default">
                <div class="panel-heading"> Customer Account Information</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">Card Type</div>
                        <div class="col-md-3">
                            <select name="custType" class="form-control">
                                <option selected="selected" value="NA">-Select--</option>
                                <option value="C">Verve Card</option>
                                <option value="S">Customised Card</option>
                                <option value="N">N.Y.S.C Card</option>
                                <option value="P">Prepaid VIA Card</option>
                            </select>
                        </div>
                        <div class="col-md-2"><strong>Merchant</strong></div>
                        <div class="col-md-4"><select name="merchant" id="merchant" class="form-control">
                                <option selected="selected" value="NA">-----Please Select-----</option>
                                <#list merchants as merchant>
                                    <option value="${merchant.getAcronymn()}">${merchant.getName()}</option>
                                </#list>
                            </select><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">Enrollment No</div>
                        <div class="col-md-7"><input class="form-control" id="photoId" name="photoId" size="20" maxlength="42" value="" type="text" /></div>
                        <div class="col-md-2"><input  class="btn btn-primary btn-sm" value="Search"  name="bt00" type="button" id="btn-photo-search"/><br></div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">Customer ID</div>
                        <div class="col-md-7"><input class="form-control" id="customerId" name="customerId" size="15" maxlength="42" value="" type="text" style="font-weight:bold;"/></div>
                        <div class="col-md-2"><input value="Search"  name="Search4" type="button"  class="btn btn-default btn-sm" id="btn-customer-search"/><br></div>
                    </div>
                    <div class="row">
                        <div class="col-md-3"><b>Account No</b></div>
                        <div class="col-md-7"><input class="form-control" id="accountno" name="accountno" size="20" value="" type="text" /></div>
                        <div class="col-md-2"><input class="btn btn-success btn-sm" value="Search"  name="Search" type="button"  id="btn-account-search"/>
                            <input id="accountlen" name="accountlen"  value="${config.getAccountSize()}" type="hidden" />
                            <input class="form-control" id="preferedName" name="preferedName" size="20" maxlength="42" value="" type="hidden" /><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">Card Product</div>
                        <div class="col-md-9">
                            <select name="productCode" id="productCode" class="form-control">
                                <#list products as product>
                                <option value="${product.getId()}">${product.getName()}</option>
                                </#list>
                            </select><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">Customer Name</div>
                        <div class="col-md-9"><input class="form-control" id="customerName" name="customerName" size="45" maxlength="26" value="" type="text" onchange="javascript:eval(this.form.preferedName.value = this.value);" readonly="readonly" style="font-weight:bold;"/><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">Phone No</div>
                        <div class="col-md-4"><input class="form-control" id="phone" name="phone" size="26" maxlength="42" value="" type="text" style="font-weight:bold;"/></div>
                        <div class="col-md-2">DoB</div>
                        <div class="col-md-3"><input class="form-control" id="dob" name="dob" size="20" value="" type="text" style="font-weight:bold;"/><br></div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">Email</div>
                        <div class="col-md-4"><input class="form-control" id="email" name="email" size="28" maxlength="42" value="" type="text"  style="font-weight:bold;"/></div>
                        <div class="col-md-2">Branch</div>
                        <div class="col-md-3"><input class="form-control" id="sol" name="sol" size="15" maxlength="42" value="" type="text" readonly="readonly" style="font-weight:bold;"/>
                        <br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 col-md-offset-4"><input id="collectingBranch" name="collectingBranch" value="${user.getBranch()}" type="hidden" />
                            <button class="btn btn-default" type="button"  id="btn-close" onclick="window.location = '/screen'">Close</button>&nbsp;
                            <button class="btn btn-primary" name="btn-save" id="btn-save"  type="button">Create Card</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-5">
            <div class="panel panel-default">
                <div class="panel-heading">Account Information</div>
                <div class="panel-body">
                    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="table table-stripped std" style="font-size:11px">
                        <thead>
                            <tr style="text-align:center;">
                                <th>-</th>
                                <th>A/c No</th>
                                <th>Account Name</th>
                                <th>Type</th>
                            </tr>
                        </thead>
                        <tbody id="accounts">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">Bio-Data</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-4"><strong>Name </strong></div>
                        <div class="col-md-8" id="fn" align="center">&nbsp;</div>
                    </div>
                    <div class="row">
                        <div class="col-md-4"><strong>Matric</strong></div>
                        <div class="col-md-4" id="matric" align="center"></div>
                        <div class="col-md-4"><strong>DOB</strong></div>
                        <div class="col-md-4" id="dob" align="center"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-6" align="center"><strong>Photo </strong></div>
                        <div class="col-md-6" align="center"><strong>Signature</strong></div>
                    </div>
                    <div class="row">
                        <div class="col-md-6" id="photo" align="center">&nbsp;</div>
                        <div class="col-md-6" id="signs" align="center" class="infoBox">.</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</@template.screen>