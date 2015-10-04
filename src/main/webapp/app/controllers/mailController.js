/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global app */

app.controller("mailCtrl", function ($scope, $http) {
    $scope.hello = {mail: "test@alten.se"};
    $scope.newMail = "";
    $scope.sendPost = function() {
        var data = $.param({
            json: JSON.stringify({
                mail: $scope.newMail
            })
        });
        $http.post("/echo/json/", data).success(function(data, status) {
            $scope.hello = data;
        });
    }                   
});

