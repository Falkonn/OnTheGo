
var registerModule = angular.module('registerModule', ['httpService', 'ngStorage']);


registerModule.controller('registerController',['$scope','httpServ', '$localStorage', '$location', 
    function ($scope, httpServ, $localStorage, $location) {
    
    // Checks if user is logged in and redirect to app-info screen in this case
    $scope.init = function() {
        // Clean localstorage (for debugging)
        //$localStorage.$reset();
        // Dummy object (for debugging)
        /*$localStorage.user = {  id: "28",
                                firstName: "Vasileios",
                                lastName:  "Golematis",
                                email:     "vasileios.golematis@alten.se",
                                telefon:   "0767649596",
                                city: "Gothenburg",
                                department: "Embedded Systems",
                                teamId:     "1",
                                picId:      "1",
                                pinCode: "fMLHyHjBOkI="
        };*/
        // LoggedIn variable
        $scope.loggedIn = $localStorage.loggedIn;
        if(typeof $localStorage.user == 'undefined' || $localStorage.user == null)
            $scope.hidePin = true;
                
        if($scope.loggedIn)
            $location.path('/info');
        // Redirect to welcome screen if not logged in (Except if in register or confirm screen)
        else if($location.url()!='/register' && $location.url()!='/confirm')
            $location.path('/');
        // If pin is undefined redirect to register screen
        else if($location.url()=='/confirm' && !$localStorage.userPin)
            $location.path('/register');
        
        // In confirm Screen -> Set values from localStorage
        if(typeof $localStorage.user !== 'undefined' && $localStorage.user !== null && $location.url()=='/confirm' ){
            $scope.userEmail = $localStorage.user.email;
            $scope.userName =  $localStorage.user.firstName; 
            $scope.userPhone = $localStorage.user.telefon;
        }
    };
    // Run Init 
    $scope.init();
    
    this.isRegistered = function() {
        return $localStorage.loggedIn;
    };
    
    $scope.result = "";

    this.sendEmail = function() {
        $scope.result = "Verifierar e-postadress..."
        httpServ.postUserMail($scope.userEmail)
        .then(function() {
            // Getting User Info to show to the user
            httpServ.getUserByMail($scope.userEmail).success(function(response){
                    console.log(response);
                    $scope.names = response;
                    // Saving User info to localStorage
                    $localStorage.user = $scope.names[0];
                    // Showing User Info
                    $scope.result = "Hej " + $localStorage.user.firstName + ' ' + $localStorage.user.lastName;
                     // Confirmation that the mail was sent successfully from the backend
                    $scope.result += ". En PIN-kod har skickats till din e-post!"
                    // Hide mail boolean variable
                    $scope.hideMail = true;
                    // Show next steps
                    $scope.hidePin = false;
                }).error(function(){console.log(response.status);});          
    
        }, function (response) {
            console.error($scope.userEmail);
            // Not Found in the Database
            if(response.status === 404)
                $scope.result = "Denna e-post finns inte i databasen. Är du säker på att du använde den till att registrera dig på eventet?";
            // Unknown server error
            else
                $scope.result = "Servererror";
        });
    };
   
    // Sends mail and Pin to the backend
    this.sendPin = function() {
        
        httpServ.postUserPin($scope.userEmail + ' ' + $scope.userPin)
            .then(function(response)  {
                // Pin Verified
                $scope.resultPin = "Korrekt PIN-kod!";
                $localStorage.userPin = $scope.userPin;
                // Redirecting to confirm screen
                $location.path('/confirm');

            }, function (response) {
                console.error($scope.userPin);
                // Not Found in the Database
                if(response.status === 404)
                    $scope.resultPin = "Denna PIN-kod matchar inte den som sändes till din e-post. Prova igen!";
                // Unknown server error
                else
                    $scope.resultPin = "Misslyckades att skicka e-post. Vänligen försök igen.";
            });
            
    };
  
    // Send Confirmation Data and update Db from the backend
    this.sendConfirmData = function() {     
        // Update local values
        console.log($localStorage.user);
        $localStorage.user.firstName = $scope.userName, 
        $localStorage.user.email = $scope.userEmail,
        $localStorage.user.telefon = $scope.userPhone,

        httpServ.postUserConfirmData(JSON.stringify($localStorage.user)).then(function(response) {          
            // Mark loggedIn to localStorage so that the user does not need to register again
            $localStorage.loggedIn = true;
            $scope.loggedIn = true;
            // Redirect to main page
            $location.path('/');
            console.log("registered complete");
        }, function (response) {
            console.error(response.status);
        });
    }; 
}]);

