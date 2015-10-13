'use strict';

/* Http Services */

var httpService = angular.module('httpService', []);

// User Data Service
httpService.factory('httpServ', ['$http',
    function($http) {
        var urlBase = 'http://localhost:8080';
       
        return {
                /********* User Services **********/

                //////// Add User http get services here ///////
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
                
                //////// Add User http Post services here ///////
                postUserMail: function(userEmail) {
                    return $http.post(urlBase + '/emailpath',userEmail );
                },
                
                postUserPin: function(userPin) {
                    return $http.post(urlBase + '/pinpath',userPin );
                },
             
                postUserConfirmData: function(user) {
                    return $http.post(urlBase + '/confirmpath', user);
                },
                
                /********* Task Services **********/
                
                //////// Add Task http get services here ///////
                getTasks: function() {
                    return $http.get(urlBase + '/Tasks');
                },
                
                //////// Add Task http post services here ///////
                
                
                /********* Score Services **********/
                
                //////// Add Score http get services here ///////
                getScore: function() {
                    return $http.get(urlBase + '/Tasks');
                }
                
                //////// Add Score http post services here ///////
                
        };
     
    }]);

