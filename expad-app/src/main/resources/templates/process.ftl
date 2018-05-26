<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form>
    <tbody>
        <tr>
            <td class="boldtitle"><div align="center" class="style5">Bulk Upload Processing Unit. </div></td>
        </tr>
        <tr>
            <td width="510"><span class="style4">INFO:</span> <br />
    <li>1. This section generates all files required for Debit Card request.</li>
    <li>2. These file include:Post Card Files, files for Card Personalization Companies,Pin Files and orher ones required.</li></td>
</tr>

<tr>
    <td class="dotted"><img alt="" src="images/c.gif" height="1" width="1" /></td>
</tr>

<tr>
    <td class="dotted"><img alt="" src="images/c.gif" height="1" width="1" /></td>
</tr>
<tr>
    <td><img alt="" src="images/c.gif" height="10" width="1" />
        <table width="100%" border="1" cellpadding="0" cellspacing="0" class="infoBoxContents">
            <tbody>

                <tr >
                    <td width="180" class="lbg">
                        <div align="center">
                            <input type="checkbox" name="checkbox" value="checkbox" class="form-control"/>
                        </div></td>
                    <td width="318" class="lbg"><div align="center"><b>Output File  </b></div></td>
                    <td width="509" class="lbg"><div align="center">File Description </div></td>
                </tr>
                <tr>
                    <td><div align="center">
                            <input name="checkbox3" type="checkbox" value="checkbox" checked="checked" class="form-control"/>
                        </div></td>
                    <td><span class="style4"> pinFile.txt</span></td>
                    <td><strong>Card Pin Mailer File</strong></td>
                </tr>
                <tr>
                    <td><div align="center">
                            <input name="checkbox3" type="checkbox" value="checkbox" checked="checked" class="form-control"/>
                        </div></td>
                    <td><span class="style4"> cardFile.txt</span></td>
                    <td><strong>Card's File </strong> </td>
                </tr>
                <tr>
                    <td><div align="center">
                            <input name="checkbox3" type="checkbox" value="checkbox" checked="checked" class="form-control"/>
                        </div></td>
                    <td><span class="style4"> accountbalances.txt</span></td>
                    <td>Customer Account Balances </td>
                </tr>
                <tr>
                    <td><div align="center"><b>
                                <input name="checkbox27" type="checkbox" value="checkbox" checked="checked" class="form-control"/>
                            </b></div></td>
                    <td><span class="style4"> accounts.txt </span></td>
                    <td>File for all downloaded accounts </td>
                </tr>
                <tr>
                    <td><div align="center"><b>
                                <input name="checkbox24" type="checkbox" class="form-control" value="checkbox" checked="checked"/>
                            </b></div></td>
                    <td><span class="style4"> cardaccounts.txt</span></td>
                    <td>Cards Personlization file </td>
                </tr>
                <tr>
                    <td><div align="center"><b>
                                <input name="checkbox25" type="checkbox" class="form-control" value="checkbox" checked="checked"/>
                            </b></div></td>
                    <td><span class="style4"> cards.txt </span></td>
                    <td>File for all downloaded accounts </td>
                </tr>
                <tr>
                    <td><div align="center"><b>
                                <input type="checkbox" name="checkbox26" value="checkbox" class="form-control" checked/>
                            </b></div></td>
                    <td><span class="style4"> pinMailerFile.txt </span></td>
                    <td>Pin Mailer File </td>
                </tr>

                <tr valign="top">
                    <td><div align="center"><b>
                                <input type="checkbox" name="checkbox2222" value="checkbox" class="form-control" checked="checked"/>
                            </b></div></td>
                    <td><span class="style4">AccountOverideLimit.txt</span></td>
                    <td>Account Override Limits File </td>
                </tr>
                <tr valign="top">
                    <td><div align="center"><b>
                                <input type="checkbox" name="checkbox222" value="checkbox" class="form-control" checked="checked"/>
                            </b></div></td>
                    <td><span class="style4">CardOverrideLimit.txt</span></td>
                    <td>Card Override Limits File </td>
                </tr>
                <tr valign="top">
                    <td><div align="center"><b>
                                <input type="checkbox" name="checkbox223" value="checkbox" class="form-control" checked="checked"/>
                            </b></div></td>
                    <td><span class="style4">Statement.txt</span></td>
                    <td>Statements File </td>
                </tr>
                <tr valign="top">
                    <td><div align="center"><b>
                                <input type="checkbox" name="checkbox22" value="checkbox" class="form-control" checked/>
                            </b></div></td>
                    <td><span class="style4"> schedule.txt</span> </td>
                    <td>Schedul File </td>
                </tr>
                <tr valign="top">
                    <td colspan="3"></td>
                </tr>
                <tr valign="top">
                    <td colspan="2"></td>
                    <td align="left">
                        <input name="operation" id="operation" type="button" class="flgd" value="Process Files"/>
                        <input name="Button2" type="button" class="flgd" value="Close"/></td>
                </tr>
            </tbody>
        </table></td>
</tr>
</tbody>
</form>

</@template.screen>