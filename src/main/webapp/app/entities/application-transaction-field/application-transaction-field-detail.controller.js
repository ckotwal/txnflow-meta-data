(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionFieldDetailController', ApplicationTransactionFieldDetailController);

    ApplicationTransactionFieldDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApplicationTransactionField', 'ApplicationTransaction', 'FlowApplicationSequence'];

    function ApplicationTransactionFieldDetailController($scope, $rootScope, $stateParams, previousState, entity, ApplicationTransactionField, ApplicationTransaction, FlowApplicationSequence) {
        var vm = this;

        vm.applicationTransactionField = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('txnMetaDataApp:applicationTransactionFieldUpdate', function(event, result) {
            vm.applicationTransactionField = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
