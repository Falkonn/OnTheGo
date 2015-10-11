'use strict';

/* Services */

var userService = angular.module('UserService', []);

// User Data Service
userService.factory('UserDataService', ['$http',
    function($http) {
        var urlBase = 'http://localhost:8080';
       
        return {
                
                getUsers: function() {
                    return $http.get(urlBase + '/users');
                },
                
                getAllUsers: function() {
                    return $http.get(urlBase + '/allusers');
                },
                
                getUserByMail: function(userEmail) {
                    return $http.get(urlBase + '/userbyemail/' + userEmail, userEmail);
                },
                             
                getUserById: function(userId) {
                    return $http.get(urlBase + '/userbyid/' + userId, userId);
                },
                
                postUserMail: function(userEmail) {
                    return $http.post(urlBase + '/emailpath',userEmail );
                },
                
                 postUserPin: function(userPin) {
                    return $http.post(urlBase + '/pinpath',userPin );
                }  
        };
     
    }]);

