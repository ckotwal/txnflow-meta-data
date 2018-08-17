(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionFieldDialogController', ApplicationTransactionFieldDialogController);

    ApplicationTransactionFieldDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApplicationTransactionField', 'ApplicationTransaction', 'FlowApplicationSequence'];

    function ApplicationTransactionFieldDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ApplicationTransactionField, ApplicationTransaction, FlowApplicationSequence) {
        var vm = this;

        vm.applicationTransactionField = entity;
        vm.clear = clear;
        vm.save = save;
        vm.applicationtransactions = ApplicationTransaction.query();
        vm.flowapplicationsequences = FlowApplicationSequence.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.applicationTransactionField.id !== null) {
                ApplicationTransactionField.update(vm.applicationTransactionField, onSaveSuccess, onSaveError);
            } else {
                ApplicationTransactionField.save(vm.applicationTransactionField, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('txnMetaDataApp:applicationTransactionFieldUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
