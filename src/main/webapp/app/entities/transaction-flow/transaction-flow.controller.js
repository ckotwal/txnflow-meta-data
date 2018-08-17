(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('TransactionFlowController', TransactionFlowController);

    TransactionFlowController.$inject = ['TransactionFlow'];

    function TransactionFlowController(TransactionFlow) {

        var vm = this;

        vm.transactionFlows = [];

        loadAll();

        function loadAll() {
            TransactionFlow.query(function(result) {
                vm.transactionFlows = result;
                vm.searchQuery = null;
            });
        }
    }
})();
