
var mainModule = angular.module('mainModule', ['ui.bootstrap', 'httpService', 'ngStorage' ])

.controller('mainController',['$scope','httpServ', '$localStorage', '$location',
    function ($scope, httpServ, $localStorage, $location) {
        var mv = $scope;
        var tasks;
        // Check if tasks are already stored in the local storage
        // If not load them from DB
        mv.init = function() {
            // If no tasks in the localStorage when loading assignments
            // This ONLY FOR PERSISTENT DATA 
            //if((typeof $localStorage.tasks !== 'undefined' || $localStorage.tasks !== null))
            //{        
                // Load tasks them from DB and save them in localStorage
                if($location.url()=='/assignments'){
                    httpServ.getTasks().success(function(response){
                        // Success - Save tasks in localStorage
                        $localStorage.tasks = response;
                        t.badresult = "";
                        // Check for this team id, user id if the tasks are done or not
                        // post(team-id, user-id)
                    }, function(response){
                        // Failed to load tasks from db
                        t.badresult = "" + response.status;
                    });
                }
                else if($location.url()=='/team'){
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
        /////////////////////// TASKS - Elnaz http.post function - To be included in the Service.
        mv.submitAnswer = function(t){
            
            var data = {"taskId": t.id, "userId": mv.userId, "answer": t.answer};
            // Sending Answer Data 
            console.log(9 + ' ' + 19 + ' ' + t.answer)
            httpServ.postTaskAnswer(9 + ' ' + 19 + ' ' + t.answer).then(function(response){
                // Success
                //t.badresult = "";
                //t.done = true;
            },
            function(response){
                // Failed
                //t.done = false;
                //t.badresult = "" + response.status;
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
        mv.assignments = {
            "tasks": $localStorage.tasks
        }
        
        /*mv.assignments = {
            "numberOfAssignments": 40,
            "numberOfTasksAnswered": 7,
            "tasks": [
                {
                    "id": 1,
                    "name": "Forum att kommunicera via",
                    "description": "För att gruppen ska kunna kommunicera och lära känna varandra, behöver ni hitta ett gemensamt forum för kommunikation.",
                    "personal": false,
                    "taskType": 2,
                    "theme": 1,
                    "estimation": "2 minuter",
                    "points": 10,
                    "answer": "",
                    "done": false
                },
                {
                    "id": 2,
                    "name": "Gilla Alten Sweden på LinkedIn",
                    "description": "Logga in på LinkedIn, sök på Alten Sweden och gilla. Om du redan har gillat Alten Sweden kan du också bocka för uppgiften.",
                    "personal": true,
                    "taskType": 2,
                    "theme": 1,
                    "estimation": "1 minut",
                    "points": 10,
                    "answer": "",
                    "done": false
                },
                {
                    "id": 3,
                    "name": "Skicka en selfie",
                    "description": "Ta en selfie och ladda upp. När du ser att bilden finns i gruppvyn kan du bocka för uppgiften.",
                    "personal": true,
                    "taskType": 1,
                    "theme": 2,
                    "estimation": "1-5 minuter",
                    "points": 10,
                    "answer": "",
                    "done": false
                },
                {
                    "id": 4,
                    "name": "Bli vänner på LindedIn",
                    "description": "I gruppvyn kan du se vilka personer som är medlemmar i din grupp (om du vill göra detta innan ni har upprättat kontakt). Sök upp de på LinkedIn och bli vänner med de. När du blivit vänner med alla som har LinkedIn i din grupp kan du bocka för uppgiften.",
                    "personal": true,
                    "taskType": 2,
                    "theme": 3,
                    "estimation": "1-10 minuter",
                    "points": 10,
                    "answer": "",
                    "done": false
                },
                {
                    "id": 5,
                    "name": "Designa en Alten-drink",
                    "description": "Vad tycker du vore en god och passande drink för Alten?",
                    "personal": true,
                    "taskType": 2,
                    "theme": 4,
                    "estimation": "1-10 minuter",
                    "points": 10,
                    "answer": "",
                    "done": false
                }
            ]
        };*/

        /////////////////////// GRUPPER OCH DESS MEDLEMMAR
        mv.team = {
                "teamNumber": $localStorage.team[0],
                "teamName":   $localStorage.team[1],
                "numberOfMembers": $localStorage.team[2],
                "members": $localStorage.team[3]
        };
      
        mv.checkImage = function(memberId){
            if($localStorage.user.id == memberId)
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
