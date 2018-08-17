(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('FlowApplicationSequenceDetailController', FlowApplicationSequenceDetailController);

    FlowApplicationSequenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FlowApplicationSequence', 'ApplicationTransactionField', 'TransactionFlow', 'ApplicationTransaction'];

    function FlowApplicationSequenceDetailController($scope, $rootScope, $stateParams, previousState, entity, FlowApplicationSequence, ApplicationTransactionField, TransactionFlow, ApplicationTransaction) {
        var vm = this;

        vm.flowApplicationSequence = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('txnMetaDataApp:flowApplicationSequenceUpdate', function(event, result) {
            vm.flowApplicationSequence = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
