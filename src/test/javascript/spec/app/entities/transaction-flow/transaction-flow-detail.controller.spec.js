'use strict';

describe('Controller Tests', function() {

    describe('TransactionFlow Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTransactionFlow, MockFlowApplicationSequence, MockTransactionFlowGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTransactionFlow = jasmine.createSpy('MockTransactionFlow');
            MockFlowApplicationSequence = jasmine.createSpy('MockFlowApplicationSequence');
            MockTransactionFlowGroup = jasmine.createSpy('MockTransactionFlowGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TransactionFlow': MockTransactionFlow,
                'FlowApplicationSequence': MockFlowApplicationSequence,
                'TransactionFlowGroup': MockTransactionFlowGroup
            };
            createController = function() {
                $injector.get('$controller')("TransactionFlowDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'txnMetaDataApp:transactionFlowUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
