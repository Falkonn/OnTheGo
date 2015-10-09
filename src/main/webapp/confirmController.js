
var confirmModule = angular.module('confirmModule',[])

.controller('confirmController',['$scope','$http', function ($scope, $http) {
    
    $http.get("/users").then(function(response) {
            $scope.userName = ""; // Get from db
            $scope.userPhone = ""; // Get from db
            $scope.userEmail = ""; // Get from localstorage
            console.log("post done");
        }, function (error) {
            console.error(error);
        });
        
    this.sendConfirmData = function() {
        $http.post("/confirmpath", JSON.stringify($scope.userEmail, $scope.userPhone, $scope.userEmail  )).then(function() {          
            console.log("post done");
        }, function (error) {
            console.error(error);
        });
    };                  
}]);


