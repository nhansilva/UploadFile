(function(){
    var service = function($http, $log) {
        var productExcel = null;
        var productExcelType = null;



        var hasProductExcel = function() {
            return productExcel != null;
        };

        var storeProductExcel = function(obj) {

            var name = obj.name;

            var fileExtension = name.toLowerCase().split('.').pop();

            $log.debug(fileExtension);

            if(fileExtension != 'xlsx') {
                toastr.alert('Only xlsx file is supported', 'Excel Upload Error');
                return;
            }

            productExcel = obj;
            productExcelType = fileExtension;

        };

        var uploadProductExcel =  function(token, callback) {
             var fd = new FormData();

             fd.append('file', productExcel);
             fd.append('token', token);

             var url = '/upload/upload-excel';

             $log.debug(url);

             $http({
                 method: 'POST',
                 url: url,
                 headers: {
                     'Content-Type': undefined
                 },
                 transformRequest: angular.identity,
                 data: fd
             }).success(function(response) {
                $log.debug(response);
                productExcel = null;
                productExcelType = null;
                if(callback) callback(response);
             }).error(function(response) {
                 $log.debug(response.exception);
                 $log.debug(response.message);
             });
        };
        return {
            uploadProductExcel: uploadProductExcel,
            storeProductExcel: storeProductExcel,
            hasProductExcel: hasProductExcel,
        };
    };

    var module = angular.module('client');
    module.factory('uploadService', ['$http', '$log', service]);

    module.directive('productExcelUpload', ['uploadService', function (uploadService) {
        return {
            restrict: 'A',
            scope : {
                productDataSet : "=ngModel"
            },
            link: function (scope, element, attr) {

                element.bind('change', function () {
                    uploadService.storeProductExcel(element[0].files[0]);
                });

            }
        };
    }]);

})();
