(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('application-transaction-field', {
            parent: 'entity',
            url: '/application-transaction-field',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ApplicationTransactionFields'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/application-transaction-field/application-transaction-fields.html',
                    controller: 'ApplicationTransactionFieldController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('application-transaction-field-detail', {
            parent: 'application-transaction-field',
            url: '/application-transaction-field/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ApplicationTransactionField'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/application-transaction-field/application-transaction-field-detail.html',
                    controller: 'ApplicationTransactionFieldDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ApplicationTransactionField', function($stateParams, ApplicationTransactionField) {
                    return ApplicationTransactionField.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'application-transaction-field',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('application-transaction-field-detail.edit', {
            parent: 'application-transaction-field-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction-field/application-transaction-field-dialog.html',
                    controller: 'ApplicationTransactionFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApplicationTransactionField', function(ApplicationTransactionField) {
                            return ApplicationTransactionField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('application-transaction-field.new', {
            parent: 'application-transaction-field',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction-field/application-transaction-field-dialog.html',
                    controller: 'ApplicationTransactionFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                isIdentifier: null,
                                filterValue: null,
                                isOutput: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('application-transaction-field', null, { reload: 'application-transaction-field' });
                }, function() {
                    $state.go('application-transaction-field');
                });
            }]
        })
        .state('application-transaction-field.edit', {
            parent: 'application-transaction-field',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction-field/application-transaction-field-dialog.html',
                    controller: 'ApplicationTransactionFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApplicationTransactionField', function(ApplicationTransactionField) {
                            return ApplicationTransactionField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('application-transaction-field', null, { reload: 'application-transaction-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('application-transaction-field.delete', {
            parent: 'application-transaction-field',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/application-transaction-field/application-transaction-field-delete-dialog.html',
                    controller: 'ApplicationTransactionFieldDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ApplicationTransactionField', function(ApplicationTransactionField) {
                            return ApplicationTransactionField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('application-transaction-field', null, { reload: 'application-transaction-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
