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
    $('.role-link').click(function (e) {
        e.preventDefault();
        var endpoint = $(this).attr('href');
        $.ajax({type: "GET", url: endpoint, dataType: 'json',
            success: function (fault) {
                if ((fault !== null) && fault.data !== 'NA') {
                    if (fault.fault !== "00")
                        return;
                    $.each(fault.data, function (key, value) {
                        $("#_form").find("input[name='" + key + "']").val(value);
                    });
                    $("#available").html(fault.group);
                    $("#chosen").html(fault.value);
                    $("#table-list").hide();
                    $("#_form").show();
                }

                $(".picklist").partsSelector({selectClass: "selected",
                    unSelectClass: "un-selected",
                    currentClass: "selected-current",
                    lastClass: "selected-last",
                    shiftClass: "selected-shift",
                    ctrlClass: "selected-ctrl",
                    children: false,
                    event: "mousedown",
                    cursor: "pointer",
                    dragEvent: "mouseenter",
                    enableClickDrag: true,
                    enableShiftClick: true,
                    enableCtrlClick: true,
                    enableSingleClick: true,
                    enableSelectAll: true,
                    enableDisableSelection: true,
                    enableTouchCtrlDefault: true,
                    enableDesktopCtrlDefault: false,
                    totalSelector: false,
                    menuSelector: false,
                    menuXOffset: 0,
                    menuYOffset: 0
                });
            }
        });
    });

    $('.card-link').click(function (e) {
        e.preventDefault();
        var endpoint = $(this).attr('href');
        $.ajax({type: "GET", url: endpoint, dataType: 'json',
            success: function (fault) {
                if (fault !== null || fault.data !== 'NA') {
                    $.each(fault.data, function (key, value) {
                        $("#_form").find("input[name='" + key + "']").val(value);
                    });
                    $("#account-link").html(fault.value);
                    $("#table-list").hide();
                    $("#_form").show();
                }
            }
        });
    });
    $('.btn-add').click(function (e) {
        e.preventDefault();
        $("#table-list").hide();
        $("#_form").show();
    });
    $('.btn-add').click(function (e) {
        e.preventDefault();
        $("#table-list").hide();
        $("#_form").show();
    });
    $("#btn-save").click(function (e) {
        $("#info").html("<center><img src='/expad/images/loading.gif' style='height:70px;width:70px;'><h3>Processing request, wait..</h3></center>");
        var endpoint = $('#_form').attr('action');
        e.preventDefault();
        $(this).prop("disabled", true);
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
                    $("#btn-save").prop("disabled", false);
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
    //$("#_form").hide();
    $(".picklist").partsSelector({selectClass: "selected",
			unSelectClass: "un-selected",
			currentClass: "selected-current",
			lastClass: "selected-last",
			shiftClass: "selected-shift",
			ctrlClass: "selected-ctrl",
			children: false,
			event: "mousedown",
			cursor: "pointer",
			dragEvent: "mouseenter",
			enableClickDrag: true,
			enableShiftClick: true,
			enableCtrlClick: true,
			enableSingleClick: true,
			enableSelectAll: true,
			enableDisableSelection: true,
			enableTouchCtrlDefault: true,
			enableDesktopCtrlDefault: false,
			totalSelector: false,
			menuSelector: false,
			menuXOffset: 0,
			menuYOffset: 0
	});
});