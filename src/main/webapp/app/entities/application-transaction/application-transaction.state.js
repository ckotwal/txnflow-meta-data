(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('application-transaction', {
            parent: 'entity',
            url: '/application-transaction',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ApplicationTransactions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/application-transaction/application-transactions.html',
                    controller: 'ApplicationTransactionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('application-transaction-detail', {
            parent: 'application-transaction',
            url: '/application-transaction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ApplicationTransaction'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/application-transaction/application-transaction-detail.html',
                    controller: 'ApplicationTransactionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ApplicationTransaction', function($stateParams, ApplicationTransaction) {
                    return ApplicationTransaction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'application-transaction',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('application-transaction-detail.edit', {
            parent: 'application-transaction-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction/application-transaction-dialog.html',
                    controller: 'ApplicationTransactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApplicationTransaction', function(ApplicationTransaction) {
                            return ApplicationTransaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('application-transaction.new', {
            parent: 'application-transaction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction/application-transaction-dialog.html',
                    controller: 'ApplicationTransactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                eventCount: null,
                                eventRepositoryType: null,
                                repositoryEventName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('application-transaction', null, { reload: 'application-transaction' });
                }, function() {
                    $state.go('application-transaction');
                });
            }]
        })
        .state('application-transaction.edit', {
            parent: 'application-transaction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction/application-transaction-dialog.html',
                    controller: 'ApplicationTransactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApplicationTransaction', function(ApplicationTransaction) {
                            return ApplicationTransaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('application-transaction', null, { reload: 'application-transaction' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('application-transaction.delete', {
            parent: 'application-transaction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction/application-transaction-delete-dialog.html',
                    controller: 'ApplicationTransactionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ApplicationTransaction', function(ApplicationTransaction) {
                            return ApplicationTransaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('application-transaction', null, { reload: 'application-transaction' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
