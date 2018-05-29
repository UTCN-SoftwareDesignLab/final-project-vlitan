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
    var socket = new SockJS('/userEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //  setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/update', function (sequenceStatus) {
            showResult(JSON.parse(sequenceStatus.body));
        });
    })
    // sendRequest();
    setInterval(sendRequest, 500);
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showResult(sequenceStatus) {
    if (sequenceStatus.username == document.getElementById("loggedUser").innerHTML) {
        console.log(sequenceStatus);
        document.getElementById("currentSequenceName").innerHTML = "Current sequence is " + sequenceStatus.name;
        document.getElementById("currentIntervalName").innerHTML = "Current interval is " + sequenceStatus.currentInterval;
        document.getElementById("leftUntilNext").innerHTML = "Time left until next interval " + sequenceStatus.timeLeft;
    }
}

function sendRequest() {
    console.log("sending update request");
    stompClient.send("/app/userEndpoint", {}, null);
}

$(function () {
    $("form").on('submit', function (e) {
        //   e.preventDefault();
    });
    // connect();
    // $( "#connect" ).click(function() { connect(); });
    // $( "#disconnect" ).click(function() { disconnect(); });
    // $( "#send" ).click(function() { sendName(); });
});