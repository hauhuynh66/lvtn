var s = $("#smoke-chart");
var l = $("#light-chart");
var house_id = $("#house_id").text();
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
var hss = $("#hs-chart");
var hsl = $("#hl-chart");
var sChart = new Chart(s,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: 'Smoke Density',
            backgroundColor: '#d3d3d3',
            borderColor: '#000000',
            borderWidth: 3,
            data: []
        }]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                ticks: {
                    suggestedMin: 50,
                    suggestedMax: 400
                },
                scaleLabel: {
                    display: true,
                    labelString: "Smoke Density"
                }
            }]
        }
    }
});

var lChart = new Chart(l,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Light",
            backgroundColor: "#ffff00",
            borderColor: "#000000",
            borderWidth: 3,
            data: []
        }]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                ticks: {
                    suggestedMin: 0,
                    suggestedMax: 1000
                },
                scaleLabel: {
                    display: true,
                    labelString: "Light Intensity"
                }
            }]
        }
    }
});

var hl = new Chart(hsl,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Light Intensity",
            backgroundColor: "#ff0000",
            borderColor: "#000000",
            borderWidth: 3,
            data: []
        }]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            xAxes: [{
                type: 'time'
            }],
            yAxes: [{
                ticks: {
                    suggestedMin: 0,
                    suggestedMax: 400
                },
                scaleLabel: {
                    display: true,
                    labelString: "Light Intensity"
                }
            }]
        }
    }
});

var hs = new Chart(hss,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Smoke Density",
            backgroundColor: "#0000ff",
            borderColor: "#000000",
            borderWidth: 3,
            data: []
        }]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            xAxes: [{
                type: 'time'
            }],
            yAxes: [{
                ticks: {
                    suggestedMin: 0,
                    suggestedMax: 400
                },
                scaleLabel: {
                    display: true,
                    labelString: "Smoke Density"
                }
            }]
        }
    }
});

function rein(){
    hs.destroy();
    hl.destroy();
    hs = new Chart(hss,{
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Smoke Density",
                backgroundColor: "#ff0000",
                borderColor: "#000000",
                borderWidth: 3,
                data: []
            }]
        },
        options: {
            legend: {
                display: false
            },
            scales: {
                xAxes: [{
                    type: 'time'
                }],
                yAxes: [{
                    ticks: {
                        suggestedMin: 0,
                        suggestedMax: 1000
                    },
                    scaleLabel: {
                        display: true,
                        labelString: "Smoke Density"
                    }
                }]
            }
        }
    });
    hl = new Chart(hsl,{
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Light Intensity",
                backgroundColor: "#0000ff",
                borderColor: "#000000",
                borderWidth: 3,
                data: []
            }]
        },
        options: {
            legend: {
                display: false
            },
            scales: {
                xAxes: [{
                    type: 'time'
                }],
                yAxes: [{
                    ticks: {
                        suggestedMin: 0,
                        suggestedMax: 1000
                    },
                    scaleLabel: {
                        display: true,
                        labelString: "Light Intensity"
                    }
                }]
            }
        }
    });
}

$(document).ready(function () {
    getData();
    setInterval(update, 5000);
});

$("#getH").on('click', function () {
    getHistory(from, to);
});

function getHistory(from, to) {
    rein();
    $.ajax({
        type: "GET",
        url: "http://localhost:8400/data/"+house_id+"/history/misc",
        dataType: "json",
        data: {
            from: from,
            to: to
        },
        success: function (data) {
            console.log(data);
            var n;
            for(n=0;n<data.length;n++){
                var d = moment(new Date(data[n].date)).format("YYYY-MM-DD hh:mm a");
                console.log(d);
                addData(hs, d, {x: d, y: data[n].smoke});
                addData(hl, d, {x: d, y: data[n].light});
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}



function getData() {
    $.ajax({
        type: "GET",
        url: "/data/list/"+house_id+"/msc",
        success: function (data) {
            var res = jQuery.parseJSON(data);
            res.reverse();
            jQuery(res).each(function (i, value) {
                addData(sChart, i, value.smoke);
                addData(lChart, i, value.light);
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function update() {
    $.ajax({
        type: "GET",
        url: "/data/top/"+house_id+"/msc",
        success: function (data) {
            var res = jQuery.parseJSON(data);
            addData(sChart, Math.ceil(Math.random()*10), res.smoke);
            removeData(sChart, 0);
            addData(lChart, Math.ceil(Math.random()*10), res.light);
            removeData(lChart, 0);
        },
        error: function (e) {
            console.log(e);
        }
    })
}

function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach(function (dataset) {
        dataset.data.push(data);
    });
    chart.update();
}

function removeData(chart, index) {
    chart.data.labels.splice(index, 1);
    chart.data.datasets[0].data.splice(index, 1);
    chart.update();
}

