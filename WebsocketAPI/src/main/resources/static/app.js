var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/sensor-data');
    //var socket = new SockJS('ws://ec2-54-221-136-232.compute-1.amazonaws.com:9003/sensor-data');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/collect', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    //stompClient.send("/app/API/hello", {}, JSON.stringify({'sensorId': $("#sensorId").val(), 'data': $("#sensorData").val()}));

    //stompClient.send("/app/API/hello", {}, JSON.stringify({'sensorId': '0a78de1f-4308-4784-aa9d-8d4b16d19470','data': 42069}));
    console.log("HI");
    let sensorId = $("#sensorId").val();
    let sensorData = $("#sensorData").val();

    console.log("sensorId" + sensorId);
    console.log("sensorData" + sensorData);
    stompClient.send("/app/API/data", {}, JSON.stringify({'sensorId': sensorId, 'data': sensorData}));
    //console.log( JSON.stringify({'sensorId': sensorId, 'data': sensorData}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

