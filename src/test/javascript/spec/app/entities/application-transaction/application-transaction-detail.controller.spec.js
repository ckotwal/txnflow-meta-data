'use strict';

describe('Controller Tests', function() {

    describe('ApplicationTransaction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockApplicationTransaction, MockFlowApplicationSequence, MockApplicationTransactionField;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockApplicationTransaction = jasmine.createSpy('MockApplicationTransaction');
            MockFlowApplicationSequence = jasmine.createSpy('MockFlowApplicationSequence');
            MockApplicationTransactionField = jasmine.createSpy('MockApplicationTransactionField');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ApplicationTransaction': MockApplicationTransaction,
                'FlowApplicationSequence': MockFlowApplicationSequence,
                'ApplicationTransactionField': MockApplicationTransactionField
            };
            createController = function() {
                $injector.get('$controller')("ApplicationTransactionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'txnMetaDataApp:applicationTransactionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
