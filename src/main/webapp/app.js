'use strict';
/* 
 *  The Shop App
 */
var OnTheGo = angular.module('OnTheGo', [
    'ngRoute', 'registerModule', 'mainModule'
]);

OnTheGo.config(['$routeProvider', '$locationProvider',
    function ($routeProvider, $locationProvider) {  // Injected object $routeProvider
        //console.log("In the routeProvider");
        $routeProvider.
            when('/', {
                templateUrl: 'partials/welcome.html',
                controller: 'registerController',
                controllerAs: 'regCtrl',
            }).
            when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'registerController',
                controllerAs: 'regCtrl'
            }).
            when('/confirm', {
                templateUrl: 'partials/confirm.html',
                controller: 'registerController',
                controllerAs: 'regCtrl'
            }).
            when('/info', {
                title: 'App Info',
                templateUrl: 'partials/appinfo.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/team', {
                title: 'MITT TEAM',
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
                title: 'Pöangtavla',
                templateUrl: 'partials/scoreboard.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/party', {
                templateUrl: 'partials/party.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            when('/schedule', {
                title: 'Konferensinfo',
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
                templateUrl: 'partials/about.html',
                controller: 'mainController',
                controllerAs: 'mainCtrl'
            }).
            otherwise({
                redirectTo: 'index.html'
            });
        
        //$locationProvider.html5Mode(true);
    }]);

OnTheGo.run(['$rootScope', function($rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;
        });
    }]);
