<script language = 'javascript'>
    function showBusyStatus() {
        document.getElementById("msg").innerHTML = "<center><img src='images/busy.gif'></center>";
    }
</script>
<form action="upload1" method="post">
    <div class="row"><div class="col-md-12 text-center"><h1>Bulk Card Upload Step 1 of 2</h1></div></div>
    <div class="row">
        <div class="col-md-5"><strong>Card Product </strong></div>
        <div class="col-md-7">
             <select name="productCode" id="productCode" class="form-control">
                <#list products as product>
                <option value="${product.getProductCode()}">${product.getProductCode()}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5"><strong>Select Network</strong></div>
        <div class="row">
            <select id="network" name="network" class="form-control">
                <option selected value="interswitch">Interswitch</option>
                <option value="etranzact">eTranzact</option>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5"><strong>Choose Record Type</strong></div>
        <div class="row">
            <div class="col-md-7">
                <select id="type" name="type" class="form-control">
                    <option selected value="excel">Accounts Record</option>
                    <option value="xml">Students Record</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 text-center">
                <input name="but" class="btn btn-default" value="Close" type="button" onclick="window.location = '/main'"/>
                <input name="but" class="btn btn-primary btn-lg" value="Continue" type="submit" onclick='showBusyStatus()' id="btn-upload"/>
            </div>
        </div>
</form>
