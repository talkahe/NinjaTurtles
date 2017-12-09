var NT = NT || {
    start: function(username) {
        var ws = ws || new WebSocket(`wss://${window.location.host}${window.location.pathname}game/${username}`);
        ws.onmessage = NT.receive;
        ws.onerror = NT.reset;
        NT.ws = ws;
    },
    welcomePlayer: function () {
        $("#welcomeModal").modal("show");
    },
    usernameSubmitted: function(){
        NT.username = $("#username").val();
        $("#welcome").html(`Welcome ${NT.username}!`);
        NT.start(NT.username);
        $("#welcomeModal").modal("hide");
    },
    receive: function (e) {
        console.log(JSON.parse(e.data));

        var msg = JSON.parse(e.data);
        var data = msg.data;
        switch(msg.action){
            case "startingGame":
                $("#gameOverModal").modal("hide");
                $("#map").empty();
                $("#rankingList").empty();

                NT.renderMap(data.map);
                data.players.forEach(player => {
                    NT.updatePlayerPosition(player);
                    NT.updateRanking(player);
                });
                $(window).on("keyup", NT.onKeyUp);
                break;

            case "playerUpdate":
                NT.updateRanking(data.player);
                NT.updatePlayerPosition(data.player);
                break;

            case "removeItem":
                var coords = data.tile.coords;
                $("#map").find(`[data-coords='${coords.x},${coords.y}']`).removeClass("item");
                break;

            case "removePlayer":
                $("#map").find(`[data-username='${data.player.username}']`).remove();
                $("#rankingList").find(`[data-user='${data.player.username}']`).remove();
                break;

            case "gameOver":
                $(window).off("keyup");
                $("#gameOverModal").modal("show");
                $("#gameOverModal #winner").text(`Winner: ${data.winner.username}!`);

                $("#gameOverModal #playAgain").on('click', function(){
                    NT.start(NT.username);
                });
                break;

        }
    },
    renderMap: function(map){
        var table = $("#map");

        for(var x = 0; x < map.length; x++) {
            var row = map[x];
            var tr = $("<div class='row justify-content-center align-items-center'></div>");
            for(var y = 0; y < row.length; y++) {
                var tile = map[x][y];
                var td;
                if(tile !== null){
                    td = $(`<div data-coords='${x},${y}' class='tile'> </div>`);
                    if(tile.hasItem){
                        td.addClass("item");
                    }
                }else{
                    td = $(`<div data-coords='${x},${y}' class='tile blocked'> </div>`);
                }
                tr.append(td);
            }
            table.append(tr);
        }
    },
    updateRanking: function(player){
        $("#points span").text(player.points);
        if($("#rankingList").find(`[data-user='${player.username}']`).length){
            $("#rankingList").find(`[data-user='${player.username}']`).find("span").text(player.points);
        }else{
            $("#rankingList").append(`<li data-user='${player.username}'>${player.username} <span class='badge badge-primary'>${player.points}</span></li>`);
        }
    },
    updatePlayerPosition: function(player){
        var moveTo = $("#map").find(`[data-coords='${player.coords.x},${player.coords.y}']`);
        var playerAvatar = $("#map").find(`[data-username='${player.username}']`);
        if(playerAvatar.length){
            playerAvatar.detach().appendTo(moveTo);
        }else{
            moveTo.append(`<img class='player-avatar' data-username='${player.username}' src='img/tortoise.png' style='background-color: ${randomColor({seed: player.username})};'></img>`);
        }
    },
    onKeyUp: function(e){
        var direction;
        switch(e.key){
            case "ArrowRight":
                direction = "right";
                break;
            case "ArrowLeft":
                direction = "left";
                break;
            case "ArrowUp":
                direction = "up";
                break;
            case "ArrowDown":
                direction = "down";
                break;
            default: 
                return;
        }
        var msg = {
            action: "move",
            data: {
                direction: direction
            }
        };
        if(NT.ws.readyState == 1)
            NT.ws.send(JSON.stringify(msg));
    },
    reset: function(){
        NT.stop();
        $("#map").empty();
        $("#rankingList").empty();
        NT.welcomePlayer();
    },
    stop: function(){
        $(window).off("keyup");
        if(NT.ws)
            NT.ws.close();
    }
};

$(NT.welcomePlayer());
$('#startPlaying').on('click', NT.usernameSubmitted);
$(window).on('beforeunload', NT.stop);
