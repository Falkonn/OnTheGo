
var mainModule = angular.module('mainModule', ['ui.bootstrap', 'httpService', 'cameraService', 'ngStorage' ])

.controller('mainController',['$scope','httpServ', 'cameraServ', '$localStorage', '$location', '$route',
    function ($scope, httpServ, cameraServ, $localStorage, $location, $route) {
        var mv = $scope;
        
        // Init values
        mv.init = function() {
            mv.userId = $localStorage.user.userId;
            mv.teamId = $localStorage.user.teamId;
        };
        // Run Init 
        mv.init();
        mv.loggedIn = true;
        mv.rules = 1;
        mv.hasUserMedia = cameraServ.hasUserMedia;
       
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
        if($location.url()==='/assignments'){
            var data = { "userId": mv.userId, "teamId": mv.teamId};
                 httpServ.getTasksAndPoints(JSON.stringify(data)).success(function(response){
                    $localStorage.tasks = response;
                    // Success - Save tasks in localStorage
                    for (var i=0 ; i < response.length ; i++){
                        $localStorage.tasks[i] = JSON.parse(response[i]);
                    }
                    mv.assignments = { "tasks": $localStorage.tasks };
                }, function(response){
                    console.log(response);
            });
          
        }
        else if($location.url()==='/team'){
             // Load the team and members of the user's team
            httpServ.getTeamByUserId(mv.userId).success(function(response){
                // Success - Save team and members in localStorage
                $localStorage.team = response;
                /////////////////////// GRUPPER OCH DESS MEDLEMMAR
                mv.team = {
                        "teamNumber":       $localStorage.team[0],
                        "teamName":         $localStorage.team[1],
                        "numberOfMembers":  $localStorage.team[2],
                        "members":          $localStorage.team[3]
                };
                console.log(mv.team);
            }, function(response){
                // Failed to load teams from db
              //  t.badresult = "" + response.status;
            });
           
        }
       
        
        /////////////////////// TASKS
        /**
         * Submitting a task answer to the backend.
         * Handles all types of answers (isPersonal: true/false and taskType: String/Checkbox) 
         * @param {type} t
         * @returns {undefined}
         */
        mv.submitAnswer = function(t, answer, done){
            if(t.check)
                answer = "" + t.check;
            var data = {"taskId": t.taskId, "userId": mv.userId, "answer": answer, "taskDone": done };
            // Sending Answer Data 
            console.log(data);
            httpServ.postTaskAnswer(data).then(function(response){
                // Score Added successfully
                if(done){
                    t.result = "Sent successfully!";
                    // Set task values to update frontend (or route reload, to be decided)
                    t.taskDone = true;
                    t.userAnswer = answer;
                    t.user = $localStorage.user;
                    console.log("Added Score by " + t.user.firstName + "!");
                }
                // Score Deleted successfully
                else{
                    t.result = "Deleted successfully!";
                    t.taskDone = false;
                    console.log("Deleted Score!");
                }
                // By reloading everything is updated
                //$route.reload();
            },
            function(response){
                console.log(response.status);
                // Failure Code received from backend
                if(response.status === 404)
                {
                    // Failed to add score (somebody else has already answered it)
                    if(done){
                        t.result = "Failed to Add Score. It is already answered by " + t.user.userId;
                         console.log("Failed to add Score! It is answered by" + t.user.userId);
                    }
                    // Failed to delete score (task is already cancelled)
                    else{
                        t.result = "Failed to cancel task. It is already cancelled!";
                        console.log("Failed to cancel task! Task already cancelled");
                    }
                }
                // Unknown Server Error
                else
                    t.result = "Server Error: " + response.status;
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
        
        mv.checkUserId = function(userId){
            if(mv.userId == userId )
                return true;
            else 
                return false;
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
