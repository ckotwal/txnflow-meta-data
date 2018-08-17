(function() {
    'use strict';
    angular
        .module('txnMetaDataApp')
        .factory('TransactionFlow', TransactionFlow);

    TransactionFlow.$inject = ['$resource'];

    function TransactionFlow ($resource) {
        var resourceUrl =  'api/transaction-flows/:id';

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
