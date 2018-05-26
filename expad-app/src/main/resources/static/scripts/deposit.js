var menuid;
var req;

function showAccountInfo(form){

document.getElementById("msgcard").innerHTML = "<h3>Verifying card information, wait..</h3>"+
						"<img src='images/busy.gif'></center>";
var url = "processFactory.do?ACTION=PCD&SN=" + escape(form.sn.value);
   if (window.XMLHttpRequest) {
       req = new XMLHttpRequest();
   } else if (window.ActiveXObject) {
       req = new ActiveXObject("Microsoft.XMLHTTP");
   }
   //req.open("POST", url, true);
   req.open("GET", url, true);
   req.onreadystatechange = getCallbackCard;
   req.send(null);
   
   return false;
}

function getCallbackCard() {

    if (req.readyState == 4) {
        if (req.status == 200) {
            // update the HTML DOM based on whether or not message is valid
			parseCardInfo();
        }
    }
}

function parseCardInfo() {

    	var xpan = req.responseXML.getElementsByTagName("PAN")[0];
	var xexpiry = req.responseXML.getElementsByTagName("EXPIRY")[0];
	var xoffset = req.responseXML.getElementsByTagName("OFFSET")[0];
	var xserialno = req.responseXML.getElementsByTagName("SERIAL")[0];
	var xemail = req.responseXML.getElementsByTagName("EMAIL")[0];
	var xmask = req.responseXML.getElementsByTagName("MASK")[0];
	var xphone = req.responseXML.getElementsByTagName("PHONE")[0];
	var cardid = req.responseXML.getElementsByTagName("CID")[0];
	var custname = req.responseXML.getElementsByTagName("CNAME")[0];
	var custsol = req.responseXML.getElementsByTagName("SOL")[0];
	var phone = req.responseXML.getElementsByTagName("PHONE")[0];
	
	//document.getElementById("customerid").value=custid.childNodes[0].nodeValue;
	document.getElementById("cid").value=cardid.childNodes[0].nodeValue;
	showAccountDetail(cardid.childNodes[0].nodeValue);
	document.getElementById("customername").value=custname.childNodes[0].nodeValue;
	document.getElementById("prefname").value=custname.childNodes[0].nodeValue;
	document.getElementById("email").value=xemail.childNodes[0].nodeValue;
	document.getElementById("phone").value=xphone.childNodes[0].nodeValue;
	document.getElementById("SOL").value=custsol.childNodes[0].nodeValue;
	document.getElementById("msgcard").innerHTML = "";
	document.getElementById("pan").value=xpan.childNodes[0].nodeValue;
	document.getElementById("mask").value=xmask.childNodes[0].nodeValue;
	document.getElementById("expiry").value=xexpiry.childNodes[0].nodeValue;
	document.getElementById("offset").value=xoffset.childNodes[0].nodeValue;
	document.getElementById("serial_no").value=xserialno.childNodes[0].nodeValue;	
	
	document.atm.operation.disabled = false;

	if(xexpiry.childNodes[0].nodeValue == 'NA'){
	    document.getElementById("msgcard").innerHTML = "Card Information not found!";	
	}
}

function showAccountDetail(id){
	var val = URLencode(id);
	jsrsExecute("remoteMethodFactory.do", getAccountCallback, "showPrePaidAccount", val);
		
}	
	
function getAccountCallback(msg){

	var doc = document.getElementById("accountno");
	doc.value = msg;
}

function submitFormData(form){
	form.submit();
}

function enableSubmit(value){
 if(value == 'Y'){
 	document.atm.operation.disabled = false;
 }
}


function checkSelection(form){
  
  
  if(form.phone.value == ''){
   alert('WARN:You must enter the  customer phone number!');
   return false;
  }
  
  if(form.email.value == ''){
   alert('WARN:You must enter the email!');
   return false;
  }
  
   if(form.customername.value == '' || form.customername.value == 'null'){
     alert('WARN: Customer Name field is required!');
     return false;
  }
  
  if(isNaN(form.amount.value) || form.amount.value == ''){
     alert('WARN:Valid number is required for amount field!');
     return false;
  }
  
  /*if(!(showConfirmedPan(form.pan.value))){
    return false;
  }*/
  
}

function updateNarrationField(form){
	form.narration.value= form.SOL.value+'/'+form.pan.value+'/'+form.amount.value;
}


function showConfirmedPan(pan){		
			
	var url = "panRevalidator.jsp?PAN="+ pan ;
	var title = "exPad :: Pan Confirmation";
	var MyArgs = new Array(title);
	var WinSettings = "center:yes;resizable:no;dialogWidth:400px;"+
					  "dialogHeight:270px;scroll:no;status:no;help:no;edge:sunken";	
	document.getElementById("tempPan").value = pan;		
	document.getElementById("pan").value = "xxxxxxxxxxxxxxxxxx";	
	var s = window.showModalDialog(url, MyArgs, WinSettings);
	
	if(s != '1'){
		return false;
	}else{
	
		document.getElementById("pan").value = pan;
		return true;
	}
}
