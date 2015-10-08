
var confirmModule = angular.module('confirmModule',[])

.controller('confirmController',['$scope','$http', function ($scope, $http) {
    
    $http.get("/users").then(function(response) {
            $scope.userName = "";
            $scope.userPhone = "";
            $scope.userEmail = "";
            console.log("post done");
        }, function (error) {
            console.error(error);
        });
        
    this.sendConfirmData = function() {
        //var data = JSON.stringify(this.userEmail);
        $http.post("/confirmpath", JSON.stringify($scope.userEmail, $scope.userPhone, $scope.userEmail  )).then(function() {          
            console.log("post done");
        }, function (error) {
            console.error(error);
        });
    };                  
}]);


