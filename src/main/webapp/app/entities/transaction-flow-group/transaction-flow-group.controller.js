(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowGroupController', TransactionFlowGroupController);

    TransactionFlowGroupController.$inject = ['TransactionFlowGroup'];

    function TransactionFlowGroupController(TransactionFlowGroup) {

        var vm = this;

        vm.transactionFlowGroups = [];

        loadAll();

        function loadAll() {
            TransactionFlowGroup.query(function(result) {
                vm.transactionFlowGroups = result;
                vm.searchQuery = null;
            });
        }
    }
})();
