(function() {
    'use strict';
    angular
        .module('txnMetaDataApp')
        .factory('TransactionFlowGroup', TransactionFlowGroup);

    TransactionFlowGroup.$inject = ['$resource'];

    function TransactionFlowGroup ($resource) {
        var resourceUrl =  'api/transaction-flow-groups/:id';

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
