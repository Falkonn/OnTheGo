'use strict';

/* Http Services */
var httpService = angular.module('httpService', []);

// User Data Service
httpService.factory('httpServ', ['$http',
    function($http) {
        //var urlBase = 'http://destination.lindholmen.alten.se/otg';
        var urlBase = 'http://localhost:8080/otg';
        
        return {
                /********* User Services **********/

                //////// Add User http get services here ///////
                getUsers: function() {
                    return $http.get(urlBase + '/users');
                },
                
                getAllUsers: function() {
                    return $http.get(urlBase + '/allusers');
                },
                
                getServerTime: function() {
                    return $http.get(urlBase + '/servertime');
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
                
                postImage: function(userIdAndImage) {
                    return $http.post(urlBase + '/upload', userIdAndImage);
                },
                
                /********* Task Services **********/
                
                //////// Add Task http GET services here ///////
                getTasks: function() {
                    return $http.get(urlBase + '/Tasks');
                },
                
                getTasksAndPoints: function(data){
                    return $http.get(urlBase + '/TasksAndPoints/' + data);
                },
                
                //////// Add Task http POST services here ///////
                postTaskAnswer: function(data) {
                    return $http.post(urlBase + '/answers', data);
                },
                
                cancelTaskAnswer: function(data) {
                    /* TODO: Check with Khaled and Evelina if we should use a 
                     * POST or DELETE when removing rows in the score table.
                     */
                    return $http.post(urlBase + '/DeleteAnswer', data);
                },

                
                
                /********* ScoreBoard Services **********/
                
                //////// Add Score http get services here ///////
                getScore: function() {
                    return $http.get(urlBase + '/Scores');
                },
                

                getScoreByTeamId: function(teamId) {
                    return $http.get(urlBase + '/scoreboard/' + teamId, teamId);
                },
                
                getAllTeams: function() {
                    return $http.get(urlBase + '/allteams');
                },
                
                getTeamScoreByTeamId: function(teamId) {
                    return $http.get(urlBase + '/scoreboard/' + teamId);
                },
                
                getTeamsAndScores: function() {
                    return $http.get(urlBase + '/teamsAndScores/');
                }
                
                //////// Add Score http post services here ///////
        };
     
    }]);

/* Camera Service */
var cameraService = angular.module('cameraService', []);

cameraService.factory('cameraServ', function($window) {
  var localStream = null;
  var videoElement = null;
  var hasUserMedia = function() {
    return !!getUserMedia();
  };

  var getUserMedia = function() {
    navigator.getUserMedia = ($window.navigator.getUserMedia || 
                              $window.navigator.webkitGetUserMedia ||
                              $window.navigator.mozGetUserMedia || 
                              $window.navigator.msGetUserMedia);
    return navigator.getUserMedia;
  };
  
  var getLocalStream = function(){
     return localStream;  
  };
  
  var getLocalVideo= function(){
     return videoElement;  
  };

  return {
    hasUserMedia: hasUserMedia(),
    getUserMedia: getUserMedia,
    setLocalStreamAndVideo: function(stream, video){
     localStream = stream;  
     videoElement = video;
    },
    getLocalStream: getLocalStream, 
    getLocalVideo: getLocalVideo 
  };
});