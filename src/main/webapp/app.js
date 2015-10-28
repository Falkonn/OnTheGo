'use strict';
/* 
 *  The Shop App
 */
var OnTheGo = angular.module('OnTheGo', [
    'ngRoute', 'registerModule', 'mainModule', 'cameraModule'
]);

OnTheGo.config(['$routeProvider', '$locationProvider',
    function ($routeProvider, $locationProvider) {  // Injected object $routeProvider
        //console.log("In the routeProvider");
        $routeProvider.
            when('/', {
                title: 'Welcome',
                templateUrl: 'partials/welcome.html',
                controller: 'registerController',
                controllerAs: 'regCtrl',
            }).
            when('/register', {
                title: 'Register',
                templateUrl: 'partials/register.html',
                controller: 'registerController',
                controllerAs: 'regCtrl'
            }).
            when('/confirm', {
                title: 'Confirmation',
                templateUrl: 'partials/confirm.html',
                controller: 'registerController',
                controllerAs: 'regCtrl'
            }).
            when('/team', {
                title: 'Mitt Team',
                templateUrl: 'partials/team.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/assignments', {
                title: 'Uppgifter',
                templateUrl: 'partials/assignments.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/scoreboard', {
                title: 'Poängtavla',
                templateUrl: 'partials/scoreboard.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/party', {
                title: 'Festen',
                templateUrl: 'partials/party.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/schedule', {
                title: 'Konferensen',
                templateUrl: 'partials/schedule.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/map', {
                title: 'Festinfo',
                templateUrl: 'partials/map.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/about', {
                title: 'Appinfo',
                templateUrl: 'partials/appinfo.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            otherwise({
                redirectTo: 'index.html'
            });
        
        //$locationProvider.html5Mode(true);
    }]);


