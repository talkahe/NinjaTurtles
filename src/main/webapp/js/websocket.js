var NINJATURTLES = NINJATURTLES || {
    start: function() {
        var ws = new WebSocket("ws://localhost:8084/NinjaTurtles/endpoint");
        ws.onmessage = NINJATURTLES.receive;
        ws.onopen = NINJATURTLES.getPlayer;
        NINJATURTLES.ws = ws;
    },
    getPlayer: function () {
        NINJATURTLES.ws.send('1');
    },
    receive: function (e) {
        console.log(JSON.parse(e.data));
    },
    stop: function(){
        NINJATURTLES.ws.close();
    }
};

window.addEventListener('load', NINJATURTLES.start);
window.addEventListener('beforeunload', NINJATURTLES.stop);