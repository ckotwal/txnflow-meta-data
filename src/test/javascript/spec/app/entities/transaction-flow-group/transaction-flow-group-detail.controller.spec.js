'use strict';

describe('Controller Tests', function() {

    describe('TransactionFlowGroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTransactionFlowGroup, MockTransactionFlow;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTransactionFlowGroup = jasmine.createSpy('MockTransactionFlowGroup');
            MockTransactionFlow = jasmine.createSpy('MockTransactionFlow');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TransactionFlowGroup': MockTransactionFlowGroup,
                'TransactionFlow': MockTransactionFlow
            };
            createController = function() {
                $injector.get('$controller')("TransactionFlowGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'txnMetaDataApp:transactionFlowGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
