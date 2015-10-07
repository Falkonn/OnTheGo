'use strict';

/* 
 *  The Shop App
 */
var OnTheGo = angular.module('OnTheGo', [
    'ngRoute','mailModule'
]);

OnTheGo.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        $routeProvider.
                when('/index', {
                    templateUrl: 'index.html',
                    controller: 'mailController'
                }).
                otherwise({
                    redirectTo: 'index.html'
                });
    }]);

