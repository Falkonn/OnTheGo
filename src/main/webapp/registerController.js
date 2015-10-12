
var registerModule = angular.module('registerModule', ['UserService', 'ngStorage']);


registerModule.controller('registerController',['$scope','UserDataService', '$localStorage', '$location', 
    function ($scope, UserDataService, $localStorage, $location) {
    
    // Check if user is logged in and redirect to main screen in this case
    $scope.init = function() {
        // For debugging
        $localStorage.$reset(); 
        $scope.loggedIn = $localStorage.loggedIn;
        if($scope.loggedIn)
            $location.path('/main');
    };
 
    $scope.result = "";
    // Set values from localStorage
    if(typeof $localStorage.user !== 'undefined' && $localStorage.user !== null ){
        $scope.userEmail = $localStorage.user.email;
        $scope.userName =  $localStorage.user.firstName; 
        $scope.userPhone = $localStorage.user.telefon;
    }

    this.sendEmail = function() {
        UserDataService.postUserMail($scope.userEmail)
        .then(function() {
            // Getting User Info to show to the user
            UserDataService.getUserByMail($scope.userEmail).success(function(response){
                    $scope.names = response;
                    // Saving User info to localStorage
                    $localStorage.user = $scope.names[0];
                    // Showing User Info
                    $scope.result = "Hello " + $localStorage.user.firstName + ' ' + $localStorage.user.lastName;
                }).error(function(){});
            // Confirmation that the mail was sent successfully from the backend
            $scope.result += ". A pin code is sent to your mail!"
    
        }, function (response) {
            console.error(response.status);
            // Not Found in the Database
            if(response.status === 404)
                $scope.result = "This mail does not exist in our database.";
            // Unknown server error
            else
                $scope.result = "Server Error";
        });
    };
   
    // Sends mail and Pin to the backend
    this.sendPin = function() {
        var mailAndPin = {
                            email: $scope.userEmail,
                            pin:   $scope.userPin
                          }
        UserDataService.postUserPin(JSON.stringify(mailAndPin))
        .then(function()  {
            console.log("pin post done");
            /* Confirmation in back end and when verified, should be able to
             * procede to next page (Name, Phone and Email confirmation page).
             */
            $scope.resultPin = "Correct Pin!";
            $localStorage.userPin = $scope.userPin;
            $location.path('/confirmation');
            
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
  
    // Send Confirmation Data and update Db from the backend
    this.sendConfirmData = function() {     
        // Update local values
        $localStorage.user.firstName = $scope.userName, 
        $localStorage.user.email = $scope.userEmail,
        $localStorage.user.telefon = $scope.userPhone,

        UserDataService.postUserConfirmData(JSON.stringify($localStorage.user)).then(function(response) {          
            // Mark loggedIn to localStorage so that the user does not need to register again
            $localStorage.loggedIn = true;
            // Redirect to main page
            $location.path('/main');
        }, function (response) {
            console.error(response.status);
        });
    }; 
}]);


