(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .controller('FlowApplicationSequenceDeleteController',FlowApplicationSequenceDeleteController);

    FlowApplicationSequenceDeleteController.$inject = ['$uibModalInstance', 'entity', 'FlowApplicationSequence'];

    function FlowApplicationSequenceDeleteController($uibModalInstance, entity, FlowApplicationSequence) {
        var vm = this;

        vm.flowApplicationSequence = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FlowApplicationSequence.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
