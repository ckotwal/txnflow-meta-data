(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowDetailController', TransactionFlowDetailController);

    TransactionFlowDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TransactionFlow', 'FlowApplicationSequence', 'TransactionFlowGroup'];

    function TransactionFlowDetailController($scope, $rootScope, $stateParams, previousState, entity, TransactionFlow, FlowApplicationSequence, TransactionFlowGroup) {
        var vm = this;

        vm.transactionFlow = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('txnMetaDataApp:transactionFlowUpdate', function(event, result) {
            vm.transactionFlow = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
