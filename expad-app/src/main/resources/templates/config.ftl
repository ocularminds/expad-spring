<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<ul class="nav nav-tabs">
    <li nav-item active><a class="nav-link active" href="#app-profile"  data-toggle="tab">Company Profile</a></li>
    <li nav-item><a class="nav-link" href="#app-process"  data-toggle="tab">Request Processing</a></li>
    <li nav-item><a class="nav-link" href="#app-audits"  data-toggle="tab">Audits</a></li>
</ul>
<form method="POST" action="/admin/config" id="_form_config">
    <div class="tab-content">
        <div class="tab-pane fade show" id="app-profile">
            <p>Please <strong>NOTE</strong> that the parameters setup here are used to drive the enter application. Any alteration will be audited.</p>
            <div class="row">
                <div class="col-md-3"><b>Company Name   </b></div>
                <div class="col-md-9"><input name="name" type="text" class="form-control" value="<#if config.getName()??>${config.getName()}</#if>" size="30" maxlength="42" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Short Name </b></div>
                <div class="col-md-9"><input class="form-control" name="shortName" size="15" maxlength="42" value="<#if config.getShortName()??>${config.getShortName()}</#if>" type="text" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Address</b></div>
                <div class="col-md-9"><textarea name="address" rows="2" cols="30" class="form-control"><#if config.getAddress()??>${config.getAddress()}</#if></textarea></div>
            </div>
        </div>
        <div class="tab-pane fade" id="app-process">        
            <p>Application Processing</p>
            <div class="row">
                <div class="col-md-3"><strong>Card Program </strong></div>
                <div class="col-md-9"><input class="form-control" name="cardProgram" size="28" maxlength="42" value="<#if config.getCardProgram()??>${config.getCardProgram()}</#if>" type="text" /></div>
            </div>
            <div class="row">
                <div class="col-md-3"><strong>Inactive Card Code </strong></div>
                <div class="col-md-3"><input class="form-control" id="INACARD" name="inActiveCardCode" size="10" maxlength="42" value="<#if config.getInActiveCardCode()??>${config.getInActiveCardCode()}</#if>" type="text" /></div>
                <div class="col-md-3"><strong>Restriction Code </strong></div>
                <div class="col-md-3"><input class="form-control" name="restrictionCode" size="10" maxlength="42" value="<#if config.getInActiveCardCode()??>${config.getRestrictionCode()}</#if>" type="text" /></div>
            </div>
            <div class="row">
                <div class="col-md-3">Request per customer  </div>
                <div class="col-md-3"><input class="form-control"  name="maximumRequestPerCustomer" size="10" maxlength="42" value="<#if config??>${config.getMaximumRequestPerCustomer()!10}</#if>" type="text" /></div>
                <div class="col-md-3">Accounts per Card  </div>
                <div class="col-md-3"><input class="form-control"  name="maximumAccountPerRequest" size="10" maxlength="42" value="<#if config??>${config.getMaximumAccountPerRequest()!10}</#if>" type="text" /></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Exclude Account Types  </b></div>
                <div class="col-md-3"><input name="excludeAccountType" value="<#if config.getExcludeAccountType()??>${config.getExcludeAccountType()}</#if>"/></div>
                <div class="col-md-3"><b>Batch Reset Flag</b></div>
                <div class="col-md-3"><input name="resetflag" type="radio" value="N"/> Disabled
                    <input name="resetflag" type="radio" value="Y" /> Enabled
                </div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Download Type</b></div>
                <div class="col-md-3">
                    <select name="DOWN_TYPE" class="bluebullet">
                        <option value="M">Manual</option>
                        <option value="A">Automatic</option>
                    </select></div>
                <div class="col-md-3"><strong>Download Rate </strong></div>
                <div class="col-md-3">
                    <select name="FREQ" class="bluebullet">
                        <option value="D3">Every 1 Hours</option>
                        <option value="W">Every 2 Hours</option>
                        <option value="F">Every 4 Hours</option>
                        <option value="ED">End of Days</option>
                    </select></div>
            </div>
            <div class="row">
                <div class="col-md-3"><strong>Prefix Card File With U </strong></div>
                <div class="col-md-3"><input name="PREFIX_U" type="radio" value="Y"/>
                    Yes
                    <input name="PREFIX_U" type="radio" value="N" checked=""/>
                    No</div><div class="col-md-3"><strong>Notify for activation</strong></div>
                <div class="col-md-3"><input name="notification" type="radio" value="N" />
                    Yes
                    <input name="notification" type="radio" value="Y" />
                    No </div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>File Download Path   </b></div>
                <div class="col-md-3"><input class="form-control"  name="postCardDirectory" size="28" maxlength="42" value="<#if config.getPostCardDirectory()??>${config.getPostCardDirectory()}</#if>" type="text" /></div>
                <div class="col-md-3"><b>Last Modification  </b></div>
                <div class="col-md-3"><input class="form-control" name="lastModification" size="10" maxlength="42" value="<#if config.getLastModification()??>${config.getLastModification()}</#if>" type="text" readonly/><br></div>
            </div>
            <div class="row">
                <div class="col-md-12"><div align="center"><b> ACCOUNT</b> <strong> TYPE</strong> <strong>MAPPING</strong> </div></div>
            </div>
            <div class="row">
                <div class="col-md-3"><strong>Account Size   </strong></div>
                <div class="col-md-3"><input class="form-control" id="ACCT_SIZE" name="accountSize" size="28" maxlength="42" value="<#if config??>${config.getAccountSize()!12}</#if>" type="text" /></div>
                <div class="col-md-3"><strong>PAN Size </strong></div>
                <div class="col-md-3"><input class="form-control" id="PAN_SIZE" name="panSize" size="19" maxlength="42" value="<#if config??>${config.getPanSize()!18}</#if>" type="text" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Start Position</b></div>
                <div class="col-md-3"><input class="form-control" id="ACCT_START_POS" name="accountStartPosition" size="15"  value="<#if config??>${config.getAccountStartPosition()!3}</#if>" type="text" /></div>
                <div class="col-md-3"><strong>End Position </strong></div>
                <div class="col-md-3"><input name="accountEndPosition" type="text" class="form-control" id="ACCT_END_POS" value="<#if config??>${config.getAccountEndPosition()!5}</#if>" size="19" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Savings Acct. Type</b></div>
                <div class="col-md-3"><input class="form-control" id="SAVINGS_MAP_CODE" name="savingsMapCode" size="15" maxlength="42" value="<#if config??>${config.getSavingsMapCode()!001}</#if>" type="text" /></div>
                <div class="col-md-3"><b>Current Acct. Type  </b></div>
                <div class="col-md-3"><input  name="CURRENT_MAP_CODE" type="text" class="form-control" id="currentMapCode" value="<#if config??>${config.getCurrentMapCode()!001}</#if>" size="19" maxlength="42" /><br></div>
            </div>
        </div>    
        <div class="tab-pane fade" id="app-audits">            
            <p>Audits and others</p>
            <div class="row">
                <div class="col-md-3"><strong>Mail Server Address  </strong></div>
                <div class="col-md-9"><input class="form-control" id="mailserver" name="mailServer" size="28" maxlength="42" value="<#if config.getMailServer()??>${config.getMailServer()}</#if>" type="text" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Min Password Length   </b></div>
                <div class="col-md-3"><input class="form-control" id="minpassword" name="minimumPassword" size="15"  value="<#if config??>${config.getMinimumPassword()!6}</#if>" type="text"/>
                    <font face="Verdana" color="#FF0000">characters(s)</font></div>
                <div class="col-md-3"><strong>Unsuccessful Login Attempts </strong></div>
                <div class="col-md-3"><input name="attemptLimit" type="text" class="form-control" id="MAX_ACCESS_ATTEMPT" value="<#if config??>${config.getAttemptLimit()!0}</#if>" /><br></div>
            </div>
            <div class="row">
                <div class="col-md-3"><b>Password Expires in<span style="face-face:Verdana;color:#FF0000">day(s)</span></b></div>
                <div class="col-md-3"><input class="form-control" id="expiry" name="passwordExpiry" size="15" maxlength="42" value="<#if config??>${config.getPasswordExpiry()!30}</#if>" type="text" />
                </div>
                <div class="col-md-3"><b>Session Idle Time </b></div>
                <div class="col-md-3"><input class="form-control"  name="sessionIdleTime" size="15" maxlength="42" value="<#if config??>${config.getSessionIdleTime()!30}</#if>" type="text" /><br>
                    <font face="Verdana" color="#FF0000">minute(s)</font></div>
            </div>
            <div class="row">
                <div class="col-md-9 col-md-offset-3" style="text-align:center;">
                    <input name="operation" type="submit" class="btn btn-primary"  value="Save"/>
                    <input name="Button" type="button" class="btn btn-primary" value="Close" onclick="window.location = '/dashboard'"/></div>
            </div>
        </div>
    </div>
</form>
</@template.screen>