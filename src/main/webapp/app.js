'use strict';
/* 
 *  The Shop App
 */
var OnTheGo = angular.module('OnTheGo', [
    'ngRoute', 'registerModule', 'mainModule'
]);

OnTheGo.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        //console.log("In the routeProvider");
        $routeProvider.
            when('/', {
                templateUrl: 'partials/welcome.html',
                controller: 'registerController'
            }).
            when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'registerController'
            }).
            when('/confirmation', {
                templateUrl: 'partials/confirm.html',
                controller: 'registerController'
            }).
            when('/main', {
                templateUrl: 'partials/main.html',
                controller: 'mainController'
            }).
            when('/main/team', {
                templateUrl: 'partials/team.html',
                controller: 'mainController'
            }).
            when('/main/assignments', {
                templateUrl: 'partials/assignments.html',
                controller: 'mainController'
            }).
            when('/main/scoreboard', {
                templateUrl: 'partials/scoreboard.html',
                controller: 'mainController'
            }).
            when('/main/schedule', {
                templateUrl: 'partials/schedule.html',
                controller: 'mainController'
            }).
            when('/main/map', {
                templateUrl: 'partials/map.html',
                controller: 'mainController'
            }).
            otherwise({
                redirectTo: 'index.html'
            });       
    }]);

