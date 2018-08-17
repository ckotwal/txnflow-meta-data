(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionController', ApplicationTransactionController);

    ApplicationTransactionController.$inject = ['ApplicationTransaction'];

    function ApplicationTransactionController(ApplicationTransaction) {

        var vm = this;

        vm.applicationTransactions = [];

        loadAll();

        function loadAll() {
            ApplicationTransaction.query(function(result) {
                vm.applicationTransactions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
