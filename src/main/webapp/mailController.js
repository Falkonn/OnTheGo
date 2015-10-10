
var mailModule = angular.module('mailModule', ['UserService', 'ngStorage']);


mailModule.controller('mailController',['$scope','UserDataService', '$localStorage', 
    function ($scope, UserDataService, $localStorage) {
    
    $scope.loggedIn = false;
    $scope.result = "";
    $scope.userPin = "";
    $scope.userEmail = $localStorage.userEmail;
    
    this.sendEmail = function() {
     
        UserDataService.postUserMail($scope.userEmail)
        .then(function(result) {
            console.log("email post done");
            // Confirmation of successful hit of mail in the database
            $scope.result = "Hello ";
            getUserInfo();
            // Getting User Name and Last name and saving them to local storage
            function getUserInfo(){
                UserDataService.getUserByMail($scope.userEmail).success(function(response){
                    $scope.names = response;
                }).error(function(response){});
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


