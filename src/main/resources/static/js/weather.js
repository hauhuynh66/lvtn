var temp = $("#temp-chart");
var humid = $("#humid-chart");
var house_id = $("#house_id").text();
var m = 20;
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
var hst = $("#ht-chart");
var hsh = $("#hh-chart");
var tchart = new Chart(temp,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Temperature",
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
            yAxes: [{
                ticks: {
                    suggestedMin: 20,
                    suggestedMax: 50
                },
                scaleLabel: {
                    display: true,
                    labelString: "Temperature"
                }
            }]
        }
    }
});

var hchart = new Chart(humid,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Humidity",
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
            yAxes: [{
                ticks: {
                    suggestedMin: 50,
                    suggestedMax: 100
                },
                scaleLabel: {
                    display: true,
                    labelString: "Humidity"
                }
            }]
        }
    }
});

var ht = new Chart(hst,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Temperature",
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
                    suggestedMax: 50
                },
                scaleLabel: {
                    display: true,
                    labelString: "Temperature"
                }
            }]
        }
    }
});

var hh = new Chart(hsh,{
    type: "line",
    data: {
        labels: [],
        datasets: [{
            label: "Temperature",
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
                    suggestedMin: 50,
                    suggestedMax: 100
                },
                scaleLabel: {
                    display: true,
                    labelString: "Humidity"
                }
            }]
        }
    }
});

function rein(){
    ht.destroy();
    hh.destroy();
    ht = new Chart(hst,{
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Temperature",
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
                        suggestedMax: 50
                    },
                    scaleLabel: {
                        display: true,
                        labelString: "Temperature"
                    }
                }]
            }
        }
    });
    hh = new Chart(hsh,{
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Humidity",
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
                        suggestedMin: 50,
                        suggestedMax: 100
                    },
                    scaleLabel: {
                        display: true,
                        labelString: "Humidity"
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

function getData() {
    $.ajax({
        type: "GET",
        url: "/data/list/"+house_id+"/dht",
        success: function (data) {
            var res = jQuery.parseJSON(data);
            res.reverse();
            jQuery(res).each(function (i, value) {
                addData(tchart, i, value.temp);
                addData(hchart, i, value.humid);
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}

$("#getH").on('click', function () {
    getHistory(from, to);
});

function getHistory(from, to) {
    rein();
    $.ajax({
        type: "GET",
        url: "http://localhost:8400/data/"+house_id+"/history/weather",
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
                addData(ht, d, {x: d, y: data[n].temp});
                addData(hh, d, {x: d, y: data[n].humid});
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function update() {
    $.ajax({
        type: "GET",
        url: "/data/top/"+house_id+"/dht",
        success: function (data) {
            var res = jQuery.parseJSON(data);
            addData(tchart, m++, res.temp);
            removeData(tchart, 0);
            addData(hchart, m++, res.humid);
            removeData(hchart, 0);
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

function addData2(chart, label, d1, d2) {
    chart.data.labels.push(label);
    chart.data.datasets[0].data.push(d1);
    chart.data.datasets[1].data.push(d2);
    chart.update();
}

