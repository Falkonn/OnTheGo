
var mainModule = angular.module('mainModule', ['ui.bootstrap'])

.controller('mainController',['$scope','$http', function ($scope, $http) {
    var mc = $scope;
    mc.loggedIn = true;
    
    mc.rules = 1;
    
    mc.assignmentConfirmation = "Glöm inte att du måste kunna bevisa att \n\
        du/gruppen har utfört uppdraget.";
    
    /////////////////////// UPPGIFTER
    mc.assignments = {
        "numberOfAssignments": 40,
        "numberOfTasksAnswered": 7,
        "tasks": [
            {
                "id": 2,
                "name": "Forum att kommunicera via",
                "description": "För att gruppen ska kunna kommunicera och lära känna varandra, behöver ni hitta ett gemensamt forum för kommunikation.",
                "personal": false,
                "taskType": 1,
                "location": 1,
                "estimation": "2 minuter",
                "points": 10,
                "answer": "",
                "done": false
            },
            {
                "id": 1,
                "name": "Gilla Alten Sweden på LinkedIn",
                "description": "Logga in på LinkedIn, sök på Alten Sweden och gilla. Om du redan har gillat Alten Sweden kan du också bocka för uppgiften.",
                "personal": true,
                "tasktype": 2,
                "location": 1,
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
                "tasktype": 3,
                "location": 1,
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
                "tasktype": 1,
                "location": 2,
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
                "tasktype": 1,
                "location": 3,
                "estimation": "1-10 minuter",
                "points": 10,
                "answer": "",
                "done": false
            }
        ]
    };
    
    /////////////////////// GRUPPER OCH DESS MEDLEMMAR
    $scope.team = {
        "teamNumber": 43,
        "numberOfMembers": 5,
        "members": [
            {
                "id": 1,
                "firstName": "Mattias",
                "lastName": "Isene",
                "phone": "0723-532489",
                "email": "mattias.isene@alten.se",
                "department": "IT Systems",
                "city": "Göteborg",
                "selfie": "mattiasisene.jpg"
            },
            {
                "id": 2,
                "firstName": "Khaled",
                "lastName": "Alnawasreh",
                "phone": "telefon",
                "email": "khaled.alnawasreh@alten.se",
                "department": "IT Systems",
                "city": "Göteborg",
                "selfie": ""
            },
            {
                "id": 3,
                "firstName": "Lisa",
                "lastName": "Engkvist",
                "phone": "telefon",
                "email": "lisa.engkvist@alten.se",
                "department": "IT Systems",
                "city": "Göteborg",
                "selfie": ""
            },
            {
                "id": 4,
                "firstName": "Evelina",
                "lastName": "Vorobyeva",
                "phone": "telefon",
                "email": "evelina.vorobyeva@alten.se",
                "department": "IT Systems",
                "city": "Göteborg",
                "selfie": ""
            },
            {
                "id": 5,
                "firstName": "Vasileios",
                "lastName": "Golematis",
                "phone": "telefon",
                "email": "vasileios.golematis@alten.se",
                "department": "Embedded Systems",
                "city": "Göteborg",
                "selfie": ""
            }
        ]
    };
    
}]);
