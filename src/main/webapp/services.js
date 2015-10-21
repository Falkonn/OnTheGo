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
                /********* TeamView Services **********/
                
                //////// Add TeamView http Get services here ///////
                
                getTeamByUserId: function(userId) {
                    return $http.get(urlBase + '/teambyuserid/' + userId);
                },
                
                //////// Add TeamView http Post services here ///////
                
                postImage: function() {
                    return $http.get(urlBase + '/upload');
                },
                
                /********* Task Services **********/
                
                //////// Add Task http GET services here ///////
                getTasks: function() {
                    return $http.get(urlBase + '/Tasks');
                },
                
                //////// Add Task http POST services here ///////
                postTaskAnswer: function(data) {
                    // TODO: Must decide how to pass 3 parameters.
                    return $http.post(urlBase + '/answers', data);
                },
              
                
                
                /********* Score Services **********/
                
                //////// Add Score http get services here ///////
                getScore: function() {
                    return $http.get(urlBase + '/Scores');
                }
                
                //////// Add Score http post services here ///////
                
        };
     
    }]);

