'use strict';

describe('Controller Tests', function() {

    describe('ApplicationTransactionField Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockApplicationTransactionField, MockApplicationTransaction, MockFlowApplicationSequence;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockApplicationTransactionField = jasmine.createSpy('MockApplicationTransactionField');
            MockApplicationTransaction = jasmine.createSpy('MockApplicationTransaction');
            MockFlowApplicationSequence = jasmine.createSpy('MockFlowApplicationSequence');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ApplicationTransactionField': MockApplicationTransactionField,
                'ApplicationTransaction': MockApplicationTransaction,
                'FlowApplicationSequence': MockFlowApplicationSequence
            };
            createController = function() {
                $injector.get('$controller')("ApplicationTransactionFieldDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'txnMetaDataApp:applicationTransactionFieldUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
