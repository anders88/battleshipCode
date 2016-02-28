"use strict";
$(function() {
    $("#registerBtn").click(function() {
        $.ajax({
            method: "POST",
            url: "game/addPlayer",
            dataType: "json",
            contentType: "text/json",
            data: JSON.stringify({
                name: $("#nameinput").val()
            })
        }).success(function(data) {
            $("#resultText").text("You have player with id " + data.id);
        });
    });
});
