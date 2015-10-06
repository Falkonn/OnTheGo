'use strict';

/* 
 *  The Shop App
 */
var shop = angular.module('Shop', [
    'ngRoute', 'base64', 'ngCookies',
    'ProductCatalogueControllers',
    'ProductCatalogueService'
]);

shop.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        $routeProvider.
                when('/index', {
                    templateUrl: 'index.html'
                }).
                when('/products', {
                    templateUrl: 'partials/products/products.html',
                    controller: 'ProductListCtrl'
                }).
                when('/products/delete/:id', {
                    templateUrl: 'partials/products/products.html',
                    controller: 'ProductDeleteCtrl'
                }).
                when('/orders', {
                    templateUrl: 'partials/orders/orders.html',
                    controller: 'OrdersCtrl'
                }).
                when('/confirmation/:id', {
                    templateUrl: 'partials/orders/confirmation.html',
                controller: 'ConfirmationCtrl'
                }).
                when('/admin', {
                    templateUrl: 'partials/authentication/admin_page.html',
                    controller: 'AdminController'
                }).
                when('/auth', {
                    templateUrl: 'auth.html',
                    controller: 'homeCtrl'
                }).
                when('/creditcard/:id', {
                    templateUrl: 'partials/products/creditcard-input.html',
                    controller: 'CreditcardController'
                }).
                otherwise({
                    redirectTo: 'partials/home/home.html'
                });
    }]);

