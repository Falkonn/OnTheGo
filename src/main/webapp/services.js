'use strict';

/* Services */

var productCatalogueService = angular.module('ProductCatalogueService', []);

// Representing the remote RESTful ProductCatalogue
productCatalogueService.factory('ProductCatalogueProxy', ['$http',
    function($http) {
        var url = 'http://localhost:8080/allusers';

        return {
            getUsersList: function(userInfo) {
                return $http.get(url, userInfo);
            }
        };
    }]);

