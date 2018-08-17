(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowDialogController', TransactionFlowDialogController);

    TransactionFlowDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TransactionFlow', 'FlowApplicationSequence', 'TransactionFlowGroup'];

    function TransactionFlowDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TransactionFlow, FlowApplicationSequence, TransactionFlowGroup) {
        var vm = this;

        vm.transactionFlow = entity;
        vm.clear = clear;
        vm.save = save;
        vm.flowapplicationsequences = FlowApplicationSequence.query();
        vm.transactionflowgroups = TransactionFlowGroup.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transactionFlow.id !== null) {
                TransactionFlow.update(vm.transactionFlow, onSaveSuccess, onSaveError);
            } else {
                TransactionFlow.save(vm.transactionFlow, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('txnMetaDataApp:transactionFlowUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
