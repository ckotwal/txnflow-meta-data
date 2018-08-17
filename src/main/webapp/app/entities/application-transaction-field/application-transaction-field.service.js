(function() {
    'use strict';
    angular
        .module('txnMetaDataApp')
        .factory('ApplicationTransactionField', ApplicationTransactionField);

    ApplicationTransactionField.$inject = ['$resource'];

    function ApplicationTransactionField ($resource) {
        var resourceUrl =  'api/application-transaction-fields/:id';

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
