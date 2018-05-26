$(document).ready(function () {
    $("#btn-upload").click(function (event) {
        event.preventDefault();
        var form = $('#_form_upload')[0];
        var data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $("#btn-upload").prop("disabled", true);
        $("#info").html("<center><img src='/expad/images/loading.gif'></center>");
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/expad/services/bulk/upload",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#info").html('<h2>' + data.fault + '</h2>');
                var records = '';
                $.each(data.data, function (i, account) {
                    records = records.concat('<tr><td>' + account.id + '"></td><td>' + account.accountNumber + '</td><td>' + account.errorType + '</td></tr>');
                });
                $("#errors").html(records);
                $("#btn-upload").prop("disabled", false);
            },
            error: function (e) {
                $("#result").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btn-upload").prop("disabled", false);
            }
        });
    });
});