var report = $("#report_table").DataTable();
var time = new Date();
$(document).ready(function () {
    basic();
    extra();
});

function basic(){
    $.ajax({
        type: 'GET',
        url: './report/basic',
        success: function (data) {
            var res = jQuery.parseJSON(data);
            
        },
        error: function (e) {
            console.log(e);
        }
    })
}

function extra(){
    $.ajax({
        type: 'GET',
        url: './report/extra',
        success: function (data) {
            var res = jQuery.parseJSON(data);
            console.log(res);
        },
        error: function (e) {
            console.log(e);
        }
    })
}

$("#export-btn").on('click', function () {

});