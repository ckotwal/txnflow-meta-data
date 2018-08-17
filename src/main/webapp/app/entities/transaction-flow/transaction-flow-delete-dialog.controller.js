(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowDeleteController',TransactionFlowDeleteController);

    TransactionFlowDeleteController.$inject = ['$uibModalInstance', 'entity', 'TransactionFlow'];

    function TransactionFlowDeleteController($uibModalInstance, entity, TransactionFlow) {
        var vm = this;

        vm.transactionFlow = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TransactionFlow.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
