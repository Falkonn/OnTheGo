
var mainModule = angular.module('mainModule', ['ui.bootstrap', 'httpService', 'cameraService', 'ngStorage', 'ngSanitize' ])

.controller('mainController',['$scope', '$sce', 'httpServ', 'cameraServ', '$localStorage', '$location', '$route', 
    function ($scope, $sce, httpServ, cameraServ, $localStorage, $location, $route) {
        
        var mv = $scope;
        
        // Init values
        mv.init = function() {
            mv.userId = $localStorage.user.userId;
            mv.teamId = $localStorage.user.team.teamId;
        };
        
        // Run Init 
        mv.init();
        mv.loggedIn = true;
        mv.rules = 1;
        mv.hasUserMedia = cameraServ.hasUserMedia;
        // Close Camera if open
        if(mv.hasUserMedia){
            var video = cameraServ.getLocalVideo();
            var stream = cameraServ.getLocalStream();
            if((video!==null && typeof video!== 'undefined') && (stream!==null && typeof stream!== 'undefined'))
            {
                video.pause();
                stream.stop();
            }
        }
        
        
        /**
         * Function to find one/many URLs within a String and format them as 
         * HTML including allow the HTML-format.
         * @param {String} text
         * @returns {trustAsHTML} result Text as Trusted HTML format.
         */
        mv.checkStringForURLS = function(text) {
            var re = /(http|ftp|https)(:\/\/)([\w_-]+(?:(?:\.[\w_-]+)+))([\w.,@?^=%&:\/~+#-]*[\w@?^=%&\/~+#-])?/g; 
            var str = 'Någon text som inte är en länk och sedan en http://www.alten.se för att sedan ha lite mer text https://www.google.com, asd.';
            var subst = '<a href="$1$2$3" target=_blank>$3</a>'; 
            var result = $sce.trustAsHtml(text.replace(re, subst));
            return result;
        };
        
        
        
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
        else if($location.url()==='/scoreboard'){
             // Load all the teams
            httpServ.getAllTeams().then(function(response){
                // Success - Save team and members in localStorage
                mv.allTeams = response.data;
                var getScoreByTeamId = function(t){
                    httpServ.getTeamScoreByTeamId(mv.allTeams[t].teamId).then(function(response){
                        // Success - Save team and members in localStorage
                        mv.allTeams[t].teamScore = response.data;                       
                    }, function(response){
                        // Failed to load score of a team                   
                        console.log(response);
                    });
                };
                // Load the Scores of all teams
                for(var t=0 ; t< mv.allTeams.length ; t++){                  
                    getScoreByTeamId(t);
                    
                }
            }, function(response){
                // Failed to load teams 
                console.log(response);
            });
        };
 
        
        
        // Load the team and members of the user's team
        mv.getTeam = function() {
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
                mv.team.score = mv.getTeamScore();
            }, function(response){
                // Failed to load teams from db
              //  t.badresult = "" + response.status;
            });
        };
        
        mv.getTeam();
           
        /////////////////////// TASKS
        /**
         * Submitting a task answer to the backend.
         * Handles all types of answers (isPersonal: true/false and taskType: String/Checkbox) 
         * @param {taskType} t
         * @param {string} answer String with either a answer text or empty string for checkboxes.
         * @param {boolean} done If true, answering task, if false, cancelling task.
         * @returns {undefined}
         */
        mv.submitAnswer = function(t, answer, done){
            if(t.check)
                answer = "" + t.check;
            var data = {"taskId": t.taskId, "userId": mv.userId, "answer": answer, "taskDone": done };
            // Sending Answer Data 
//            console.log(data);
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
                    t.check = false;
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
        
        
        mv.getTeamScore = function(){
//            console.log(mv.teamId);
            httpServ.getScoreByTeamId(mv.teamId).success(function(response){
                $localStorage.team.score = response;
                mv.team.score = response;
                console.log("mv.team.score:" + mv.team.score);
            }, function(response){
                console.log("Failed to load score from db");
              //  t.badresult = "" + response.status;
            });
        };
        
        
        

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
            if( mv.userId == userId )
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
    }

])

.filter('isUser', function() {
    return function(input, userId) {
        var out = [];
            for (var i = 0; i < input.length; i++){
                if(input[i].userId === userId)
                    out.push(input[i]);
            }      
        return out;
    };
})

.filter('isNotUser', function() {
    return function(input, userId) {
        var out = [];
            for (var i = 0; i < input.length; i++){
                if(input[i].userId !== userId)
                    out.push(input[i]);
            }      
        return out;
    };
});
