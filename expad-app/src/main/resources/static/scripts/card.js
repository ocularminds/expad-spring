$(document).ready(function () {
    $("#_form").show();
    $("#btn-customer-search").click(function (event) {
        $("#info").html("<h3>... Verifying customer information, wait..</h3>");
        var endpoint = "/api/customer/" + escape($("#customerId").val());
        selectCustomer(endpoint);
    });
    $("#btn-account-search").click(function (event) {
        $("#info").html("<h3>... verifying customer information, wait..</h3>");
        var sizeofaccount = $('#accountlen').val();
        var lenofaccount = $('#accountno').val().length;
        if ((parseInt(sizeofaccount) > lenofaccount) || (parseInt(sizeofaccount) < lenofaccount)) {
            $("#info").html('<h3>Invalid Account Size!</h3>');
            return false;
        }
        var endpoint = "/api/customer/account/" + escape($("#accountno").val());
        selectCustomer(endpoint);
    });
    $("#btn-photo-search").click(function (event) {
        $("#info").html("<h3>... Verifying photo enrollment information, wait..</h3>");
        var endpoint = "/api/bio/" + escape($("#photoid").val());
        $.ajax({type: "GET", url: endpoint, dataType: 'json',
            success: function (c) {
                if (c == null || c.id == 'NA') {
                    $("#photo").html("");
                    $("#info").html("Student photo enrollment could not be verified!");
                } else {
                    $("#photoid").val(c.id);
                    $("#fn").val(c.name);
                    $("#matric").val(c.no);
                    $("#dob").val(c.dob);
                    $("#photo").html("<img src='/api/customer/photo/'" + c.id + "' height='180' width='160'/>");
                    $("#signs").html("<img src='/api/customer/signature/'" + c.id + "' height='90' width='160'/>");
                    $("#info").html("");
                    $("#operation").attr('diabled', 'false');
                }
            }
        });
    });
    $("#btn-save").click(function (event) {
        $("#info").html("<center><img src='images/loading.gif' style='height:70px;width:70px;'><h3>... creating card, wait..</h3></center>");
        var endpoint = "/api/cards/order";
        event.preventDefault();
        $("#btn-save").prop("disabled", true);
        var fields = toJson($('#_form'));
        $.ajax({type: "POST", url: endpoint, data: JSON.stringify(fields), dataType: 'json', contentType: "application/json",
            success: function (fault) {
                $("#info").html(fault.fault);
                if (fault.error === '00') {
                    $("#btn-save").prop("disabled", false);
                    $('#customerId').val('');
                    $('#customerName').val('');
                    $('#preferedName').val('');
                    $('#sol').val('');
                    $('#dob').val('');
                    $('#collectingSol').val('');
                    $('#email').val('');
                } else {
                    $("#btn-save").prop("disabled", true);
                }
            },
            error: function (e) {
                $("#info").html("Sorry, card request can not be processed now.");
                console.log("error executing request : ", e);
                $("#btn-save").prop("disabled", false);
            }
        });

    });
    $("#btn-pan-search").click(function (event) {
        $("#info").html("<h3>... verifying pan information, wait..</h3>");
        var endpoint = "/api/cards/pan/" + escape($('#pan').val());
        $.ajax({type: "GET", url: endpoint, dataType: 'json',
            success: function (c) {
                if (c != null && c.expiryDate != 'NA') {
                    $("#operation").prop('disabled', false);
                    $('#pan').val(c.no);
                    $('#expiry').val(c.expiryDate);
                    $('#offset').val(c.offset);
                    $('#serial_no').val(c.id);
                    $('#prcode').val(c.productCode);
                    $('#prodName').val(c.productCode);
                    $('#info').html('');
                } else {
                    $("#operation").prop('disabled', true);
                    $("#info").html("<h3>* Invalid acount number *</h3>");
                }
            }
        });
    });

});

function selectCustomer(endpoint) {
    $.ajax({type: "GET", url: endpoint, dataType: 'json',
        success: function (c) {
            if (c == null || c.sol == 'NA') {
                $("#info").html("Customer account number could not be verified!");
                $("#accounts").html("");
                $("#CardList").html("");
                $('#operation').prop('disabled', true);
            } else {
                $('#operation').prop('disabled', false);
                $('#customerId').val(c.id);
                $('#customerName').val(c.name);
                $('#preferedName').val(c.name);
                $('#sol').val(c.sol);
                $('#dob').val(c.dateOfBirth);
                $('#collectingSol').val(c.sol);
                $('#email').val('');
                var uria = '/api/customer/' + c.id + '/accounts';
                $('#info').html("");
                $.ajax({type: "GET", url: uria, dataType: 'json',
                    success: function (accounts) {
                        var records = '';
                        $.each(accounts, function (i, account) {
                            records = records.concat('<tr><td><input type=checkbox name="accounts[]" value="' + account.accountNumber + '"></td><td>' + account.accountNumber + '</td><td>' +
                                    account.accountName + '</td><td>' + account.schemeCode + '</td></tr>');
                        });
                        $("#accounts").html(records);
                    }
                });
            }
        }
    });
}

function getCustomerAccountCallback(val) {

    var doc2 = $("#accounts2");
    var doc3 = $("#accounts3");
    //alert(msg);
    if (val == 'invalid') {
        alert('Account not Found!');
    } else {

        if (whichAccount == '2') {
            doc2.innerHTML = val;
        } else {
            doc3.innerHTML = val;
        }
    }

}

function submitFormData(form) {
    form.submit();
}

function enableSubmit(value) {
    if (value == 'Y') {
        document.atm.operation.disabled = false;
    }
}

function enableLinkAccount(obj, form) {

    if (obj.checked == true) {

        if (obj.value == '2') {
            form.accountno2.disabled = false;
            form.Search2.disabled = false;
        }

        if (obj.value == '3') {
            form.accountno3.disabled = false;
            form.Search3.disabled = false;
        }

    }

    if (!obj.checked) {

        if (obj.value == '2') {
            form.accountno2.disabled = true;
            form.Search2.disabled = true;
            var account2 = $("#accounts2");
            account2.innerHTML = "";

        }

        if (obj.value == '3') {
            form.accountno3.disabled = true;
            form.Search3.disabled = true;
            var account3 = $("#accounts3");
            account3.innerHTML = "";
        }

    }
}

function addAccount(form, obj) {
    if (obj.checked == true) {
        $("#linked").val(parseInt($("#linked").val()) + 1);
        $("#selected").val(obj.value);
    } else {
        $("#linked").val(parseInt($("#linked").val()) - 1);
        $("#selected").val("");
    }
}

function addService(form, obj) {
    if (obj.checked == true) {
        form.serviceCount.value = parseInt(form.serviceCount.value) + 1;
    } else {
        form.serviceCount.value = parseInt(form.serviceCount.value) - 1;
    }
}

function checkSelection(form) {
    if (form.links.value == '0') {
        alert('WARN:You must select at least an account.');
        return false;
    }

    if (form.mobileno.value == '') {
        alert('WARN:You must enter the  customer phone number!');
        return false;
    }

    if (form.email.value == '') {
        alert('WARN:You must enter the email!');
        return false;
    }

    if (!(showConfirmedPan(form.pan.value))) {
        return false;
    }

}

function showConfirmedPan(pan) {

    var url = "panRevalidator.jsp?PAN=" + pan;
    var title = "ExPad :: Pan Confirmation";
    var MyArgs = new Array(title);
    var WinSettings = "center:yes;resizable:no;dialogWidth:400px;" +
            "dialogHeight:270px;scroll:no;status:no;help:no;edge:sunken";
    $("#tempPan").val(pan);
    $("#pan").val("xxxxxxxxxxxxxxxxxx");
    var s = window.showModalDialog(url, MyArgs, WinSettings);

    if (s != '1') {
        return false;
    } else {
        $("#pan").val(pan);
        return true;
    }
}
