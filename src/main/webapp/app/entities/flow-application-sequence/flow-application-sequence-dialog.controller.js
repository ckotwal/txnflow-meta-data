(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('FlowApplicationSequenceDialogController', FlowApplicationSequenceDialogController);

    FlowApplicationSequenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FlowApplicationSequence', 'ApplicationTransactionField', 'TransactionFlow', 'ApplicationTransaction'];

    function FlowApplicationSequenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FlowApplicationSequence, ApplicationTransactionField, TransactionFlow, ApplicationTransaction) {
        var vm = this;

        vm.flowApplicationSequence = entity;
        vm.clear = clear;
        vm.save = save;
        vm.applicationtransactionfields = ApplicationTransactionField.query();
        vm.transactionflows = TransactionFlow.query();
        vm.applicationtransactions = ApplicationTransaction.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.flowApplicationSequence.id !== null) {
                FlowApplicationSequence.update(vm.flowApplicationSequence, onSaveSuccess, onSaveError);
            } else {
                FlowApplicationSequence.save(vm.flowApplicationSequence, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('txnMetaDataApp:flowApplicationSequenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
