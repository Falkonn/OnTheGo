
var mainModule = angular.module('mainModule', ['ui.bootstrap', 'httpService', 'ngStorage' ])

.controller('mainController',['$scope','httpServ', '$localStorage', '$location', '$route',
    function ($scope, httpServ, $localStorage, $location, $route) {
        var mv = $scope;
        var tasks;
        
        // Load data from database (assignments/tasks)
        mv.init = function() {
                 mv.userId = $localStorage.user.id;
                // Load tasks them from DB and save them in localStorage
                if($location.url()==='/assignments'){
                    var data = { "userId": $localStorage.user.id, "teamId": $localStorage.user.teamId}
                    console.log(JSON.stringify(data)); 
                     httpServ.getTasksAndPoints(JSON.stringify(data)).success(function(response){
                        // Success - Save tasks in localStorage
                        for (var i=0 ; i < response.length ; i++){
                            $localStorage.tasks[i] = JSON.parse(response[i]);
                        }
                        //console.log($localStorage.tasks);
                        // Check for this team id, user id if the tasks are done or not
                        // post(team-id, user-id)
                    }, function(response){
                        console.log(response);
                    });
                }
                else if($location.url()==='/team'){
                    // Load the team and members of the user's team
                    httpServ.getTeamByUserId($localStorage.user.id).success(function(response){
                        // Success - Save team and members in localStorage
                        $localStorage.team = response;
                       // t.badresult = "";
                    }, function(response){
                        // Failed to load teams from db
                      //  t.badresult = "" + response.status;
                    });
                }
            //}
        };
        // Run Init 
        mv.init();
        mv.loggedIn = true;
        mv.rules = 1;
       
        
        /////////////////////// TASKS
        /**
         * Submitting a task answer to the backend.
         * Handles all types of answers (isPersonal: true/false and taskType: String/Checkbox) 
         * @param {type} t
         * @returns {undefined}
         */
        mv.submitAnswer = function(t){
            
            var data = {"taskId": t.taskId, "userId": mv.userId, "answer": t.answer, "taskDone": true };
            // Sending Answer Data 
            console.log(data);
            httpServ.postTaskAnswer(data).then(function(response){
                // Success
                t.badresult = "";
                t.taskDone = true; // Used to show/hide information in the Front End.
                $route.reload();
            },
            function(response){
                // Failed
                t.taskDone = false;
                t.badresult = "" + response.status;
            });
        };
        
        mv.cancelAnswer = function(t){
            var data = {"taskId": t.taskId, "userId": mv.userId, "answer": t.userAnswer, "taskDone": false };
            // Sending Answer Data 
            console.log(data);
            httpServ.postTaskAnswer(data).then(function(response){
                // Success
                t.badresult = "";
                t.taskDone = true; // Used to show/hide information in the Front End.
            },
            function(response){
                // Failed
                //t.done = false;
                //t.badresult = "" + response.status;
                $route.reload();
            });
        };
        
        mv.assignmentConfirmation = "Glöm inte att du måste kunna bevisa att \n\
            du/gruppen har utfört uppdraget.";

//////////// HELP FUNCTIONS
        mv.checkTaskType = function(taskType, expected){
            if(taskType === expected){
                return true;
            }
            else{
                return false;
            }
        };
        
        mv.checkUserVsUser = function(userId){
            if(mv.userId === userId ){
                return true;
            }
            else return false;
        };
        
        mv.parseTaskType = function(taskType){
            switch(taskType)
            {
                case "1": return 1;
                case "2": return 2;
                default:  return 0;
            }
        };
        
        mv.parseTaskTheme = function(taskTheme){
            switch(taskTheme)
            {
                case "1": return 1;
                case "2": return 2;
                case "3": return 3;
                case "4": return 4;
                default:  return 0;
            }
        };
        
        /**
         * jsonParse - parses a string to JSON
         * @param {type} str
         * @returns {Array|Object}
         */
        mv.jsonParse = function(str){
            if(typeof str === "String"){
                return JSON.parse(str);
            }
            else{
                return str;
            }
        };

//////////// JSON DUMMY CODE BELOW!

        /////////////////////// UPPGIFTER
        /**
         * From TASK table:
         * ----------------
         * name = heading
         * description = information/details regarding the task
         * personal = Boolean - True (do it yourself) or False (do it with the team)
         * taskType = [1|2] where 1=String, 2=Checkbox
         * theme = [1|2|3|4] where 
         *      1=Gör Själv, 2=Lär känna varandra, 3=Kluringar, 4=På festen
         * estimation = time estimation for the task to be completed
         * 
         * From SCORE table:
         * -----------------
         * answer = will be empty from start and then posting answers to the database,
         *      and mainly retrieved to be able to show other members of a group 
         *      that a group question has already been answered.
         * done = to see if the tasks has been done or not in order to control 
         *      what data to show.
         * 
         */
        mv.assignments = { "tasks": $localStorage.tasks };
        console.log(mv.assignments);
        /////////////////////// GRUPPER OCH DESS MEDLEMMAR
        mv.team = {
                "teamNumber":       $localStorage.team[0],
                "teamName":         $localStorage.team[1],
                "numberOfMembers":  $localStorage.team[2],
                "members":          $localStorage.team[3]
        };
      
        mv.checkUserId = function(userId){
            if($localStorage.user.id == userId)
                return true;
            else
                return false;           
        };
        
        /////////////////////// GRUPPER OCH DESS MEDLEMMAR
        mv.lindholmen = {
            "floors": [
                {
                    "id": 1,
                    "floor": "Vån 6",
                    "image": "img/logos/wordpress.jpg",
                    "activities": [
                        {
                            "startTime": "19:00", 
                            "endTime": "20:00",
                            "heading": "Heading 6.1",
                            "information": "info 6.1"
                        },
                        {
                            "startTime": "20:00", 
                            "endTime": "20:30",
                            "heading": "Heading 6.2",
                            "information": "info 6.2"
                        }
                    ]
                },
                {
                    "id": 2,
                    "floor": "Vån 5",
                    "image": "img/logos/wordpress.jpg",
                    "activities": [
                        {
                            "startTime": "20:00", 
                            "endTime": "21:00",
                            "heading": "Heading 5.1",
                            "information": "info 5.1"
                        },
                        {
                            "startTime": "21:00", 
                            "endTime": "21:30",
                            "heading": "Heading 5.2",
                            "information": "info 5.2"
                        }
                    ]
                },
                {
                    "id": 3,
                    "floor": "Vån 4",
                    "image": "img/logos/wordpress.jpg",
                    "activities": [
                        {
                            "startTime": "20:00", 
                            "endTime": "21:00",
                            "heading": "Heading 5.1",
                            "information": "info 5.1"
                        },
                        {
                            "startTime": "21:00", 
                            "endTime": "21:30",
                            "heading": "Heading 5.2",
                            "information": "info 5.2"
                        }
                    ]
                },
                {
                    "id": 4,
                    "floor": "Vån 1",
                    "image": "img/logos/wordpress.jpg",
                    "activities": [
                        {
                            "startTime": "19:00", 
                            "endTime": "19:30",
                            "heading": "Heading 1.1",
                            "information": "info 1.1"
                        },
                        {
                            "startTime": "02:00", 
                            "endTime": "03:00",
                            "heading": "Heading 1.2",
                            "information": "info 1.2"
                        }
                    ]
                }
            ]
        };
    
    }
]);
