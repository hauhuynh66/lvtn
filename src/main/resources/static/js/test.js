var json = {
    room: 1,
    temp: {
        temp: 27,
        x: false,
        y: false
    },
    humid: {
        humid: 80,
        x: false,
        y: false
    },
    smoke: {
        smoke: 300,
        x: false,
        y: false
    },
    light: {
        light: 500,
        x: false,
        y: false
    },
    time: new Date()
};

var j = [json, json];
$("#btn").on('click', function () {
    $.ajax({
        type: "POST",
        url: "/recover",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(j),
        success: function (data) {
            console.log(data);
        },
        error: function (err) {
            console.log(err);
        }
    })
});