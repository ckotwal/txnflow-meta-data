(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionDetailController', ApplicationTransactionDetailController);

    ApplicationTransactionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApplicationTransaction', 'FlowApplicationSequence', 'ApplicationTransactionField'];

    function ApplicationTransactionDetailController($scope, $rootScope, $stateParams, previousState, entity, ApplicationTransaction, FlowApplicationSequence, ApplicationTransactionField) {
        var vm = this;

        vm.applicationTransaction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('txnMetaDataApp:applicationTransactionUpdate', function(event, result) {
            vm.applicationTransaction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
