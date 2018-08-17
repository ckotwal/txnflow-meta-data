(function() {
    'use strict';
    angular
        .module('txnMetaDataApp')
        .factory('ApplicationTransaction', ApplicationTransaction);

    ApplicationTransaction.$inject = ['$resource'];

    function ApplicationTransaction ($resource) {
        var resourceUrl =  'api/application-transactions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
