$(document).ready(function ($) {
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
                    $("#_view").show();
                }
            }
        });
    });
    $('#btn-add').click(function (e) {
        e.preventDefault();
        $("#table-list").hide();
        $("#_form").show();
    });
    $("#btn-save").click(function (e) {
        $("#success").html("<center><img src='/img/loading.gif' style='height:70px;width:70px;'><h3>Processing request, wait..</h3></center>");
        $("#success").show();
        var endpoint = $('#_form').attr('action');
        e.preventDefault();
        $(this).prop("disabled", true);
        var fields = toJson($('#_form'));
        $.ajax({type: "POST", url: endpoint, data: JSON.stringify(fields), dataType: 'json', contentType: "application/json",
            success: function (fault) {
                $("#success").html(fault.fault);
                if (fault.error === '00') {
                    $("#btn-save").prop("disabled", false);
                    $('#id').val('');
                    $("#btn-save").prop("disabled", false);
                } else {
                    $("#btn-save").prop("disabled", false);
                }
            },
            error: function (e) {
                $("#error").html("Sorry, request can not be processed now.");
                $("#error").show();
                $("#success").hide();
                console.log("error executing request : ", e);
                $("#btn-save").prop("disabled", false);
            }
        });
    });
});
function dateFormatter(value) {
    new Date(value).toLocaleString();
}
function amountformata(number) {
    return accounting.formatMoney(number, " ", 2);
}
function showDialog(id) {
    $("#" + id).modal('show');
}

function toJson($form) {
    var a = $form.serializeArray();
    var o = {};
    $.each(a, function (i, e) {
        if (e.name.substr(-2) == "[]") {
            e.name = e.name.substr(0, e.name.length - 2);
            o[e.name] = [];
        }

        if (o[e.name]) {
            if (!o[e.name].push) {
                o[e.name] = [o[e.name]];
            }
            o[e.name].push(e.value || '');
        } else {
            o[e.name] = e.value || '';
        }
    });
    return o;
}

var commons = {record: {}, records: [], fault: {}};
commons.load = function (uri) {
    commons.records = [];
    commons.show_loading_message();
    $.getJSON(uri, function (json) {
        commons.records = json;
    });
    commons.hide_loading_message();
    return commons.records;
};
commons.createTable = function (tableId, tcolumns, tdata) {
    tcolumns.unshift({data: null, defaultContent: '', className: 'select-checkbox', orderable: false});
    var table = $(tableId).DataTable({
        dom: "Bfrtip", keys: true, "columns": tcolumns, "ajax": function (data, callback, settings) {
            callback({data: tdata});
        },
        "select": {"style": "os", "selector": "td:first-child"}, buttons: [], responsive: true
    });
    return table;
};
commons.combo = function (id, url, val) {
    $.getJSON(url, function (data) {
        $('#' + id + ' option').remove();
        var options = '<option value="0">Please select ' + id + '</option>';
        var d = val;
        $.each(data, function (i, item) {
            if (item.id == d) {
                options = options.concat('<option value="' + item.id + '" selected="true">' + item.text + '</option>');
            } else {
                options = options.concat('<option value="' + item.id + '">' + item.text + '</option>');
            }
        });
        $('#' + id).append(options);
    });
};
commons.show_message = function (message_text, message_type) {
    $('#message').html('<p>' + message_text + '</p>').attr('class', message_type);
    $('#message_container').show();
    if (typeof timeout_message !== 'undefined') {
        window.clearTimeout(timeout_message);
    }
    timeout_message = setTimeout(function () {
        hide_message();
    }, 8000);
};
// Hide message
commons.hide_message = function () {
    $('#message').html('').attr('class', '');
    $('#message_container').hide();
}

// Show loading message
commons.show_loading_message = function () {
    $('#loading_container').show();
}
// Hide loading message
commons.hide_loading_message = function () {
    $('#loading_container').hide();
}

// Show lightbox
commons.show_lightbox = function () {
    $('.lightbox_bg').show();
    $('.lightbox_container').show();
}
// Hide lightbox
commons.hide_lightbox = function () {
    $('.lightbox_bg').hide();
    $('.lightbox_container').hide();
}
commons.hide_ipad_keyboard = function () {
    document.activeElement.blur();
    $('input').blur();
};

$(document).on('click', '.lightbox_bg', function () {
    commons.hide_lightbox();
});
$(document).on('click', '.lightbox_close', function () {
    commons.hide_lightbox();
});
