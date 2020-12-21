var bs = $("#basic_report").DataTable();
var ex = $("#extra_report").DataTable();
$("#startDate").datepicker({
    autoclose:true,
    format: 'mm/dd/yyyy'
});
var from = new Date().getTime();
var to = new Date(from+86400000).getTime();
$("#startDate").datepicker("update", new Date());
$("#endDate").datepicker({
    autoclose:true,
    format: 'mm/dd/yyyy'
});
$("#endDate").datepicker("update", new Date(to));

$(document).ready(function () {
    basic(from, to);
    extra(from, to);
});

function basic(from, to){
    bs.clear().draw(false);
    $.ajax({
        type: 'GET',
        url: './report/basic?from='+from+'&to='+to,
        success: function (data) {
            var res = jQuery.parseJSON(data);
            $.each(res.list,function (i,item) {
                console.log(item);
                var row = [item.des, item.data, new Date(item.time).toISOString()];
                bs.row.add(row).draw(false);
            });
        },
        error: function (e) {
            console.log(e);
        }
    })
}

function extra(from, to){
    ex.clear().draw(false);
    $.ajax({
        type: 'GET',
        url: './report/extra?from='+from+'&to='+to,
        success: function (data) {
            var res = jQuery.parseJSON(data);
            $.each(res.list,function (i,item) {
                console.log(item);
                var row = [item.des, item.data.length];
                ex.row.add(row).draw(false);
            });
        },
        error: function (e) {
            console.log(e);
        }
    })
}

$("#export-btn").on('click', function () {
    window.location = './excel';
});

$("#filter").on('click', function () {
    from = new Date($("#startDate").val()).getTime();
    to = new Date($("#endDate").val()).getTime();
    basic(from, to);
    extra(from, to);
});

