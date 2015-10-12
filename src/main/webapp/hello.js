/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function Hello($scope, $http) {
    $http.get('greetings', {data: {greetings:name}}).
            success(function(data) {
                $scope.greetings = data;
            });

    $scope.update = function() {
        $http.get('greetings', {params: {greetings: $scope.greetings}}).
                success(function (data) {
                    $scope.greetings = data;
                });
    }
}
  