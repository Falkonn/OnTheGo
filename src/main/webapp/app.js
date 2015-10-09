'use strict';
/* 
 *  The Shop App
 */
var OnTheGo = angular.module('OnTheGo', [
    'ngRoute','mailModule', 'confirmModule'
]);

OnTheGo.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        //console.log("In the routeProvider");
        $routeProvider.
            when('/', {
                templateUrl: 'partials/welcome.html',
                controller: 'mailController'
            }).
            when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'mailController'
            }).
            when('/confirmation', {
                templateUrl: 'confirmation.html',
                controller: 'confirmController'
            }).
            when('/main', {
                templateUrl: 'main.html',
                controller: 'mainController'
            }).
            when('/main/team', {
                templateUrl: 'team.html',
                controller: 'mainController'
            }).
            when('/main/assignments', {
                templateUrl: 'assignments.html',
                controller: 'mainController'
            }).
            when('/main/scoreboard', {
                templateUrl: 'scoreboard.html',
                controller: 'mainController'
            }).
            when('/main/schedule', {
                templateUrl: 'schedule.html',
                controller: 'mainController'
            }).
            when('/main/map', {
                templateUrl: 'map.html',
                controller: 'mainController'
            }).
            otherwise({
                redirectTo: 'index.html'
            });       
    }]);

