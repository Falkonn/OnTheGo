
var registerModule = angular.module('registerModule', ['UserService', 'ngStorage']);


registerModule.controller('registerController',['$scope','UserDataService', '$localStorage', '$location', 
    function ($scope, UserDataService, $localStorage, $location) {
    
    // Checks if user is logged in and redirect to app-info screen in this case
    $scope.init = function() {
        // For debugging
        //$localStorage.$reset();
        $scope.loggedIn = $localStorage.loggedIn;
        console.log("loggedIn" + $scope.loggedIn);
        if($scope.loggedIn)
            $location.path('/appinfo');
        // Redirect to welcome screen if not logged in (Except if in register or confirm screen)
        else if($location.url()!='/register' && $location.url()!='/confirm')
            $location.path('/');
        //If pin is undefined redirect to register screen
        else if($location.url()=='/confirm' && !$localStorage.userPin)
            $location.path('/register');
    };
    // Run Init 
    $scope.init();
    
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
                    console.log(response);
                    $scope.names = response;
                    // Saving User info to localStorage
                    $localStorage.user = $scope.names[0];
                    // Showing User Info
                    $scope.result = "Hello " + $localStorage.user.firstName + ' ' + $localStorage.user.lastName;
                     // Confirmation that the mail was sent successfully from the backend
                    $scope.result += ". A pin code is sent to your mail!"
                }).error(function(){console.log(response.status);});
           
    
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
        
        UserDataService.postUserPin($scope.userEmail + ' ' + $scope.userPin)
            .then(function(response)  {
                console.log(response);
                /* Confirmation in back end and when verified, should be able to
                 * procede to next page (Name, Phone and Email confirmation page).
                 */
                $scope.names = response;
                // Saving User info to localStorage again in case the user has already got the pin
                //$localStorage.user = $scope.names;
                $scope.resultPin = "Correct Pin!";
                $localStorage.userPin = $scope.userPin;
                console.log($localStorage);
                $location.path('/confirm');

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
        console.log($localStorage.user);
        $localStorage.user.firstName = $scope.userName, 
        $localStorage.user.email = $scope.userEmail,
        $localStorage.user.telefon = $scope.userPhone,

        UserDataService.postUserConfirmData(JSON.stringify($localStorage.user)).then(function(response) {          
            // Mark loggedIn to localStorage so that the user does not need to register again
            $localStorage.loggedIn = true;
            console.log("registered complete");
            // Redirect to main page
            $location.path('/appinfo');
        }, function (response) {
            console.error(response.status);
        });
    }; 
}]);


