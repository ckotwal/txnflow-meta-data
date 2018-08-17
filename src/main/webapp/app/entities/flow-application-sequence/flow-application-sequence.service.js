(function() {
    'use strict';
    angular
        .module('txnMetaDataApp')
        .factory('FlowApplicationSequence', FlowApplicationSequence);

    FlowApplicationSequence.$inject = ['$resource'];

    function FlowApplicationSequence ($resource) {
        var resourceUrl =  'api/flow-application-sequences/:id';

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
