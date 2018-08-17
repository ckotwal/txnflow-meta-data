(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transaction-flow', {
            parent: 'entity',
            url: '/transaction-flow',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TransactionFlows'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-flow/transaction-flows.html',
                    controller: 'TransactionFlowController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('transaction-flow-detail', {
            parent: 'transaction-flow',
            url: '/transaction-flow/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TransactionFlow'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-flow/transaction-flow-detail.html',
                    controller: 'TransactionFlowDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TransactionFlow', function($stateParams, TransactionFlow) {
                    return TransactionFlow.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transaction-flow',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transaction-flow-detail.edit', {
            parent: 'transaction-flow-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow/transaction-flow-dialog.html',
                    controller: 'TransactionFlowDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionFlow', function(TransactionFlow) {
                            return TransactionFlow.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-flow.new', {
            parent: 'transaction-flow',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow/transaction-flow-dialog.html',
                    controller: 'TransactionFlowDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lOB: null,
                                name: null,
                                flowCorrelationId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transaction-flow', null, { reload: 'transaction-flow' });
                }, function() {
                    $state.go('transaction-flow');
                });
            }]
        })
        .state('transaction-flow.edit', {
            parent: 'transaction-flow',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow/transaction-flow-dialog.html',
                    controller: 'TransactionFlowDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionFlow', function(TransactionFlow) {
                            return TransactionFlow.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-flow', null, { reload: 'transaction-flow' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-flow.delete', {
            parent: 'transaction-flow',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow/transaction-flow-delete-dialog.html',
                    controller: 'TransactionFlowDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TransactionFlow', function(TransactionFlow) {
                            return TransactionFlow.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-flow', null, { reload: 'transaction-flow' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
