(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionDeleteController',ApplicationTransactionDeleteController);

    ApplicationTransactionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApplicationTransaction'];

    function ApplicationTransactionDeleteController($uibModalInstance, entity, ApplicationTransaction) {
        var vm = this;

        vm.applicationTransaction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ApplicationTransaction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
