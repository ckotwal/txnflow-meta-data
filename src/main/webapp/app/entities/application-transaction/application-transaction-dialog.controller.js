(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionDialogController', ApplicationTransactionDialogController);

    ApplicationTransactionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApplicationTransaction', 'FlowApplicationSequence', 'ApplicationTransactionField'];

    function ApplicationTransactionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ApplicationTransaction, FlowApplicationSequence, ApplicationTransactionField) {
        var vm = this;

        vm.applicationTransaction = entity;
        vm.clear = clear;
        vm.save = save;
        vm.flowapplicationsequences = FlowApplicationSequence.query();
        vm.applicationtransactionfields = ApplicationTransactionField.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.applicationTransaction.id !== null) {
                ApplicationTransaction.update(vm.applicationTransaction, onSaveSuccess, onSaveError);
            } else {
                ApplicationTransaction.save(vm.applicationTransaction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('txnMetaDataApp:applicationTransactionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
