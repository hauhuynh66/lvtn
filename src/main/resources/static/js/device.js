$(".switch.device").on('click', function () {
    var s = $(this).attr('id');
    var id = s.split("_")[1];
    var data = {
        room : $("#room_id").text(),
        id : id
    };
    console.log(id);
    $.ajax({
        type: "POST",
        url: "http://192.168.137.1:4000/receive_device",
        origin: "http://192.168.137.236",
        contentType: "application/x-www-form-urlencoded",
        data : data,
        success: function (data) {
            console.log(data);
            $("#cb").prop('checked', true);
        },
        error: function (err) {
            console.log(err);
        }
    })
});