var CHAT_ID = "";

const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = async (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    CHAT_ID = $("#chat_id").val();
    if (CHAT_ID === "")
    {
        var sub = await stompClient.subscribe("/app/chat/register", (registerResponse) => {
            CHAT_ID = String.fromCharCode.apply(null, registerResponse._binaryBody);
            console.log(CHAT_ID);
    
            SubscribeToChat();

            sub.unsubscribe();
        });
    }
    else {
        SubscribeToChat();
    }
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function SubscribeToChat()
{
    stompClient.subscribe(`/chat/${CHAT_ID}`, (message) => {
        var h = document.createElement("h1");
        console.log(message);
        h.innerHTML = JSON.parse(
            String.fromCharCode.apply(null, message._binaryBody)).name;
        document.body.appendChild(h);
    });
}

function ProcessImage(message)
{
    var img = document.createElement("img");
        img.src = URL.createObjectURL(
            new Blob([message._binaryBody.buffer], { type : "image/png" }));
        document.body.appendChild(img);
}

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
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: `/app/chat/sendMessage/${CHAT_ID}`,
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});
