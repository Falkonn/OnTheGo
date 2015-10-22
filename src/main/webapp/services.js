'use strict';

/* Http Services */
var httpService = angular.module('httpService', []);

// User Data Service
httpService.factory('httpServ', ['$http',
    function($http) {
        var urlBase = 'http://localhost:8080';
        //var urlBase2 = 'http://10.87.16.152:3306/dlapp';
        
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
                
                //////// Add Task http GET services here ///////
                getTasks: function() {
                    return $http.get(urlBase + '/Tasks');
                },
                
                // New Function to get all tasks with merged score information into each task object
                getTasksAndPoints: function(data) {
                    return $http.get(urlBase + '/TasksAndPoint/' + data);
                },
                
                //////// Add Task http POST services here ///////
                postTaskAnswer: function(data) {
                    return $http.post(urlBase + '/TasksAnswer', data);
                },
                
                cancelTaskAnswer: function(data) {
                    /* TODO: Check with Khaled and Evelina if we should use a 
                     * POST or DELETE when removing rows in the score table.
                     */
                    return $http.post(urlBase + '/DeleteAnswer', data);
                },
              
                
                
                /********* Score Services **********/
                
                //////// Add Score http get services here ///////
                getScore: function() {
                    return $http.get(urlBase + '/Scores');
                }
                
                //////// Add Score http post services here ///////
                
        };
     
    }]);

