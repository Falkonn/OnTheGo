
var mailModule = angular.module('mailModule', ['UserService', 'ngStorage']);


mailModule.controller('mailController',['$scope','UserDataService', '$localStorage', 
    function ($scope, UserDataService, $localStorage) {
    
    $scope.loggedIn = false;
    $scope.result = "";
    $scope.userEmail = $localStorage.userEmail;
    
    this.sendEmail = function() {
        UserDataService.postUserMail($scope.userEmail)
        .then(function() {
            console.log("email post done");
            // Confirmation of successful hit of mail in the database
            $scope.result = "Hello ";
            getUserInfo();
            // Getting User Info to show to the user
            function getUserInfo(){
                UserDataService.getUserByMail($scope.userEmail).success(function(response){
                    $scope.names = response;
                }).error(function(){});
            }
            // Save user's mail to local Storage
            $localStorage.userEmail = $scope.userEmail;
            // Confirmation that the mail was sent successfully from the backend
    
        }, function (response) {
            console.error(response.status);
            // Not Found in the Database
            if(response.status === 404)
                $scope.result = "This mail does not exist in our database";
            // Unknown server error
            else
                $scope.result = "Server Error";
        });
    };
    
    this.sendPin = function() {
        UserDataService.postUserPin($scope.userPin)
        .then(function()  {
            console.log("pin post done");
            $scope.resultPin = "Correct Pin!";
            /* Confirmation in back end and when verified, should be able to
             * procede to next page (Name, Phone and Email confirmation page).
             */
            
        }, function (response) {
            console.error($scope.userPin);
            // Not Found in the Database
            if(response.status === 404)
                $scope.resultPin = "This pin does not match your mail";
            // Unknown server error
            else
                $scope.resultPin = "Server Error";
        });
            
    };
}]);


