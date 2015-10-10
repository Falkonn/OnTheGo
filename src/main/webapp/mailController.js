
var mailModule = angular.module('mailModule', [])


.controller('mailController',['$scope','$http', function ($scope, $http) {
    
    $scope.loggedIn = false;
                
    $scope.userEmail = "";
    $scope.userPin = "";
    
    $scope.hello = {mail: "test@alten.se"};
    $scope.newMail = "";
    
    this.sendEmail = function() {
        //var data = JSON.stringify(this.userEmail);
        $http.post("/emailpath", $scope.userEmail)
        .then(function(result) {
            console.log("email post done");
            console.log(result);
    
            /* Confirmation of the email in the backend and if confirmed 
             * an email is sent to the user with the pin code to be able to
             * login.
             * 
             * Front End - Stay at this page, with information for the user 
             * if the email was invalid (try again) or valid, information for 
             * user that he/she has got an email with the pin code.
             */
    
        }, function (error) {
            console.error(error);
        });
    };
    
    this.sendPin = function() {
        $http.post("/pinpath", JSON.stringify($scope.userPin))
        .then(function(result) {
            console.log("pin post done");
            
            /* Confirmation in back end and when verified, should be able to
             * procede to next page (Name, Phone and Email confirmation page).
             */
            
        }, function (error) {
            console.error(error);
        });
            
    };
}]);


