'use strict';
/* 
 *  The Shop App
 */
var OnTheGo = angular.module('OnTheGo', [
    'ngRoute','mailModule', 'confirmModule', 'mainModule'
]);

OnTheGo.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        //console.log("In the routeProvider");
        $routeProvider.
            when('/', {
                templateUrl: 'partials/welcome.html',
                controller: 'mailController',
                controllerAs: 'mailCtrl'
            }).
            when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'mailController',
                controllerAs: 'mailCtrl'
            }).
            when('/confirm', {
                templateUrl: 'partials/confirm.html',
                controller: 'confirmController',
                controllerAs: 'confirmCtrl'
            }).
            when('/info', {
                templateUrl: 'partials/appinfo.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/team', {
                templateUrl: 'partials/team.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/assignments', {
                templateUrl: 'partials/assignments.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/scoreboard', {
                templateUrl: 'partials/scoreboard.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/schedule', {
                templateUrl: 'partials/schedule.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/map', {
                templateUrl: 'partials/map.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/about', {
                templateUrl: 'partials/about.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            otherwise({
                redirectTo: 'index.html'
            });       
    }]);

