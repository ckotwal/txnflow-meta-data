(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowGroupDialogController', TransactionFlowGroupDialogController);

    TransactionFlowGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TransactionFlowGroup', 'TransactionFlow'];

    function TransactionFlowGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TransactionFlowGroup, TransactionFlow) {
        var vm = this;

        vm.transactionFlowGroup = entity;
        vm.clear = clear;
        vm.save = save;
        vm.transactionflows = TransactionFlow.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transactionFlowGroup.id !== null) {
                TransactionFlowGroup.update(vm.transactionFlowGroup, onSaveSuccess, onSaveError);
            } else {
                TransactionFlowGroup.save(vm.transactionFlowGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('txnMetaDataApp:transactionFlowGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
