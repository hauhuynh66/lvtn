var t = $("#rs_t").val();
var h = $("#rs_h").val();
var s = $("#rs_s").val();
var l = $("#rs_l").val();
var id = $("#rs_id").val();
var data = {
    "id" : id,
    "t" : t,
    "h" : h,
    "s" : s,
    "l" : l
};

$("#send").on('click', function () {
    $.ajax({
        type: "POST",
        url: "./setting/change",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(data),
        success: function (data) {
            console.log(data);
        },
        error: function (err) {
            console.log(err);
        }
    })
});