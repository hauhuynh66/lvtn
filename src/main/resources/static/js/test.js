var json1 = {
    room: 1,
    temp: {
        temp: 36,
        x: true,
        y: false
    },
    humi: {
        humi: 80,
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


var json2 = {
    room: 1,
    temp: {
        temp: 36,
        x: false,
        y: false
    },
    humi: {
        humi: 80,
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

var d = {
    id : "01led01",
    room : 1,
    status : "ON"
};


$("#tst1").on('click', function () {
    $.ajax({
        type: "POST",
        url: "/data/add",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json1),
        success: function (data) {
            console.log(data);
        },
        error: function (err) {
            console.log(err);
        }
    })
});

$("#tst2").on('click', function () {
    $.ajax({
        type: "POST",
        url: "/data/add",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json2),
        success: function (data) {
            console.log(data);
        },
        error: function (err) {
            console.log(err);
        }
    })
});