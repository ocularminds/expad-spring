<#import "screen.html" as template>
<@template.screen user=user modules=modules>
<form id="_form" action="/cards/product" method="POST">
    <input type="hidden" name="id" value="<#if product.getId()??>${product.getId()}</#if>" />
    <div class="row">
        <div class="col-md-12 text-center"><h2>Product Detail</h2></div>
    </div>    
	<div class="row">
	    <div class="col-md-4">Network</div>
	    <div class="col-md-8"><select id="network" name="network" class="form-control">
		    <option selected="selected" value="interswitch"> Interswitch</option>
		    <option value="etranzact"> e-Tranzact</option>
		</select><br></div>
	</div>   
    <div class="row">
        <div class="col-md-3">Product Code </div>
        <div class="col-md-3"><input class="form-control" id="code" name="code" size="15" maxlength="42" value="<#if product.getCode()??>${product.getCode()}</#if>" type="text" /></div>
        <div class="col-md-3">Product Name</div>
        <div class="col-md-3"><input class="form-control" id="name" name="name" size="32" maxlength="42" value="<#if product.getName()??>${product.getName()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-3">Product Bin</div>
        <div class="col-md-3"><input class="form-control" id="bin" name="bin" size="15" maxlength="15" value="<#if product.getBin()??>${product.getBin()}</#if>" type="text" /></div>
        <div class="col-md-3">Pan Format</div>
        <div class="col-md-3">
            <select name="format" id="format" class="form-control">
                <option value="0">BRANCH_PRODUCT_RANDOM_FORMAT</option>
                <option value="1">BRANCH_RANDOM_PRODUCT_FORMAT</option>
                <option value="2">PRODUCT_BRANCH_RANDOM_FORMAT</option>
                <option value="3">PRODUCT_RANDOM_BRANCH_FORMAT</option>
                <option value="4">RANDOM_BRANCH_PRODUCT_FORMAT</option>
                <option value="5">RANDOM_PRODUCT_BRANCH_FORMAT</option>
            </select><br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">Inactive Card Code</div>
        <div class="col-md-3"><input class="form-control" id="inActiveCardCode" name="inActiveCardCode" size="15" maxlength="6" value="<#if product.getInActiveCardCode()??>${product.getInActiveCardCode()}</#if>" type="text" /></div>
        <div class="col-md-3">Currency Code</div>
        <div class="col-md-3"><input class="form-control" id="currency" name="currencyCode" size="15" maxlength="5" value="<#if product.getCurrencyCode()??>${product.getCurrencyCode()}</#if>" type="text" /><br></div>
    </div>
    <div class="row">
        <div class="col-md-3">Trailing text(pppp or ssspppp)</div>
        <div class="col-md-3"><input class="form-control" name="trailingText" size="15" maxlength="15" value="<#if product.getTrailingText()??>${product.getTrailingText()}</#if>" type="text" /></div>
        <div class="col-md-4"><input name="operation" type="submit" class="btn btn-primary btn-save"  value="Save"/>&nbsp;
            <a href="/cards/product" class="btn btn-primary"id="cls-btn">Close</a>
        </div>
</form>
</@template.screen>