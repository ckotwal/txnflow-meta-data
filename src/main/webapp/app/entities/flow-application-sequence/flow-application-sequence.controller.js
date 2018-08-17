(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('FlowApplicationSequenceController', FlowApplicationSequenceController);

    FlowApplicationSequenceController.$inject = ['FlowApplicationSequence'];

    function FlowApplicationSequenceController(FlowApplicationSequence) {

        var vm = this;

        vm.flowApplicationSequences = [];

        loadAll();

        function loadAll() {
            FlowApplicationSequence.query(function(result) {
                vm.flowApplicationSequences = result;
                vm.searchQuery = null;
            });
        }
    }
})();
