(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transaction-flow-group', {
            parent: 'entity',
            url: '/transaction-flow-group',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TransactionFlowGroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-flow-group/transaction-flow-groups.html',
                    controller: 'TransactionFlowGroupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('transaction-flow-group-detail', {
            parent: 'transaction-flow-group',
            url: '/transaction-flow-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TransactionFlowGroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transaction-flow-group/transaction-flow-group-detail.html',
                    controller: 'TransactionFlowGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TransactionFlowGroup', function($stateParams, TransactionFlowGroup) {
                    return TransactionFlowGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transaction-flow-group',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transaction-flow-group-detail.edit', {
            parent: 'transaction-flow-group-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow-group/transaction-flow-group-dialog.html',
                    controller: 'TransactionFlowGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionFlowGroup', function(TransactionFlowGroup) {
                            return TransactionFlowGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-flow-group.new', {
            parent: 'transaction-flow-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow-group/transaction-flow-group-dialog.html',
                    controller: 'TransactionFlowGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transaction-flow-group', null, { reload: 'transaction-flow-group' });
                }, function() {
                    $state.go('transaction-flow-group');
                });
            }]
        })
        .state('transaction-flow-group.edit', {
            parent: 'transaction-flow-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow-group/transaction-flow-group-dialog.html',
                    controller: 'TransactionFlowGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TransactionFlowGroup', function(TransactionFlowGroup) {
                            return TransactionFlowGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-flow-group', null, { reload: 'transaction-flow-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transaction-flow-group.delete', {
            parent: 'transaction-flow-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transaction-flow-group/transaction-flow-group-delete-dialog.html',
                    controller: 'TransactionFlowGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TransactionFlowGroup', function(TransactionFlowGroup) {
                            return TransactionFlowGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transaction-flow-group', null, { reload: 'transaction-flow-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
