
var mailModule = angular.module('mailModule', [])

        .controller('mailController', ['$scope', '$http', function ($scope, $http) {
                $scope.userEmail = "";

                $scope.hello = {mail: "test@alten.se"};
                $scope.newMail = "";

                this.sendEmail = function () {
                    //var data = JSON.stringify(this.userEmail);
                    $http.post("/emailpath", $scope.userEmail).then(function () {

                        console.log("post done");
                    }, function (error) {
                        console.error(error);
                    });
                };
            }]);


