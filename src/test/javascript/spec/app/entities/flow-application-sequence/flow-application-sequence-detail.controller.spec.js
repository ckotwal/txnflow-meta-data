'use strict';

describe('Controller Tests', function() {

    describe('FlowApplicationSequence Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFlowApplicationSequence, MockApplicationTransactionField, MockTransactionFlow, MockApplicationTransaction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFlowApplicationSequence = jasmine.createSpy('MockFlowApplicationSequence');
            MockApplicationTransactionField = jasmine.createSpy('MockApplicationTransactionField');
            MockTransactionFlow = jasmine.createSpy('MockTransactionFlow');
            MockApplicationTransaction = jasmine.createSpy('MockApplicationTransaction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'FlowApplicationSequence': MockFlowApplicationSequence,
                'ApplicationTransactionField': MockApplicationTransactionField,
                'TransactionFlow': MockTransactionFlow,
                'ApplicationTransaction': MockApplicationTransaction
            };
            createController = function() {
                $injector.get('$controller')("FlowApplicationSequenceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'txnMetaDataApp:flowApplicationSequenceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
