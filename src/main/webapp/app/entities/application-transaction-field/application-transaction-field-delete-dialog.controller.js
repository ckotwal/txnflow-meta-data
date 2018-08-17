(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionFieldDeleteController',ApplicationTransactionFieldDeleteController);

    ApplicationTransactionFieldDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApplicationTransactionField'];

    function ApplicationTransactionFieldDeleteController($uibModalInstance, entity, ApplicationTransactionField) {
        var vm = this;

        vm.applicationTransactionField = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ApplicationTransactionField.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
