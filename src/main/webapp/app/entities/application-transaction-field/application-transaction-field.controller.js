(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('ApplicationTransactionFieldController', ApplicationTransactionFieldController);

    ApplicationTransactionFieldController.$inject = ['ApplicationTransactionField'];

    function ApplicationTransactionFieldController(ApplicationTransactionField) {

        var vm = this;

        vm.applicationTransactionFields = [];

        loadAll();

        function loadAll() {
            ApplicationTransactionField.query(function(result) {
                vm.applicationTransactionFields = result;
                vm.searchQuery = null;
            });
        }
    }
})();
