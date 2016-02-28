"use strict";
$(function() {
    $.ajax({
        method: "GET",
        url: "game/",
        dataType: "json",
        contentType: "text/json"
    }).success(function(data) {
        var lival = _.reduce(_.map(data,function(player) {
            return "<li>" + player.name + " completed: " + player.completedGames + " best score: " + player.bestRound + "</li>";
        }),function(a,b) {
            return a+b;
        });
        $("#playerList").html(lival);
    });
});
