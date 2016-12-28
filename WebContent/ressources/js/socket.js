var joueurSocket = new WebSocket("ws://localhost:10080/Abalone/joueurSocket");
joueurSocket.onmessage = onMessage;

function onMessage(event) { //On reçoit un message
    var truc = JSON.parse(event.data);
    alert(truc);
}

function addMachin(name, type, description) {
    var machin = {
        action: "add",
        name: name,
        type: type,
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));
}