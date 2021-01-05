var temp = $("#temp-chart");
var humid = $("#humid-chart");
var house_id = $("#house_id").text();
var m = 20;
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
            console.log(res);
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

function update() {
    $.ajax({
        type: "GET",
        url: "/data/top/"+house_id+"/dht",
        success: function (data) {
            var res = jQuery.parseJSON(data);
            console.log(res);
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

