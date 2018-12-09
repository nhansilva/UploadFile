(function(){
    var service = function($http, $log) {
        var getProducts = function(callback) {
            $http.get('/upload/get-agents')
            .then(function(response){
                callback(response.data);
            }, function(response){
                console.log(response.data );
            });
        };

        return {
            getProducts: getProducts
        };
    };


    var module = angular.module('client');
    module.factory('agentService', ['$http', '$log', service]);
})();
