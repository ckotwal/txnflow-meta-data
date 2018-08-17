(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowGroupDetailController', TransactionFlowGroupDetailController);

    TransactionFlowGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TransactionFlowGroup', 'TransactionFlow'];

    function TransactionFlowGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, TransactionFlowGroup, TransactionFlow) {
        var vm = this;

        vm.transactionFlowGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('txnMetaDataApp:transactionFlowGroupUpdate', function(event, result) {
            vm.transactionFlowGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
