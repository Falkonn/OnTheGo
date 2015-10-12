
var mainModule = angular.module('mainModule', [])


.controller('mainController',['$scope','$http', function ($scope, $http) {
    var mc = $scope;
    mc.loggedIn = true;
    
    $scope.assignmentConfirmation = "Glöm inte att du måste kunna bevisa att \n\
        du/gruppen har utfört uppdraget.";
    
    /////////////////////// UPPGIFTER
    $scope.assignments = {
        "numberOfAssignments": 10,
        "tasks": [
            {
                "id": 1,
                "name": "Bestäm ett forum att kommunicera via",
                "details": "För att gruppen ska kunna kommunicera och lära känna varandra, behöver ni hitta ett gemensamt forum för kommunikation.",
                "type": "grupp",
                "estimation": "2 minuter",
                "points": 10,
                "checked": false
            },
            {
                "id": 2,
                "name": "Gilla Alten Sweden på LinkedIn",
                "details": "Logga in på LinkedIn, sök på Alten Sweden och gilla. Om du redan har gillat Alten Sweden kan du också bocka för uppgiften.",
                "type": "personlig",
                "estimation": "1 minut",
                "points": 10,
                "checked": false
            },
            {
                "id": 3,
                "name": "Skicka en selfie",
                "details": "Ta en selfie och ladda upp. När du ser att bilden finns i gruppvyn kan du bocka för uppgiften.",
                "type": "personlig",
                "estimation": "1-5 minuter",
                "points": 10,
                "checked": false
            },
            {
                "id": 4,
                "name": "Bli vänner på LindedIn",
                "details": "I gruppvyn kan du se vilka personer som är medlemmar i din grupp (om du vill göra detta innan ni har upprättat kontakt). Sök upp de på LinkedIn och bli vänner med de. När du blivit vänner med alla som har LinkedIn i din grupp kan du bocka för uppgiften.",
                "type": "personlig",
                "estimation": "1-10 minuter",
                "points": 10,
                "checked": false
            },
            {
                "id": 5,
                "name": "Designa en Alten-drink",
                "details": "Vad tycker du vore en god och passande drink för Alten?",
                "type": "personlig",
                "estimation": "1-10 minuter",
                "points": 10,
                "checked": false
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


