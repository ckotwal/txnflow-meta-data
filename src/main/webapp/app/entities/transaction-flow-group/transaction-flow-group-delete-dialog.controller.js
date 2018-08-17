(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowGroupDeleteController',TransactionFlowGroupDeleteController);

    TransactionFlowGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'TransactionFlowGroup'];

    function TransactionFlowGroupDeleteController($uibModalInstance, entity, TransactionFlowGroup) {
        var vm = this;

        vm.transactionFlowGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TransactionFlowGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
