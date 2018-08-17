(function() {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('flow-application-sequence', {
            parent: 'entity',
            url: '/flow-application-sequence',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FlowApplicationSequences'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/flow-application-sequence/flow-application-sequences.html',
                    controller: 'FlowApplicationSequenceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('flow-application-sequence-detail', {
            parent: 'flow-application-sequence',
            url: '/flow-application-sequence/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FlowApplicationSequence'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/flow-application-sequence/flow-application-sequence-detail.html',
                    controller: 'FlowApplicationSequenceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FlowApplicationSequence', function($stateParams, FlowApplicationSequence) {
                    return FlowApplicationSequence.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'flow-application-sequence',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('flow-application-sequence-detail.edit', {
            parent: 'flow-application-sequence-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flow-application-sequence/flow-application-sequence-dialog.html',
                    controller: 'FlowApplicationSequenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FlowApplicationSequence', function(FlowApplicationSequence) {
                            return FlowApplicationSequence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('flow-application-sequence.new', {
            parent: 'flow-application-sequence',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flow-application-sequence/flow-application-sequence-dialog.html',
                    controller: 'FlowApplicationSequenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                appSequence: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('flow-application-sequence', null, { reload: 'flow-application-sequence' });
                }, function() {
                    $state.go('flow-application-sequence');
                });
            }]
        })
        .state('flow-application-sequence.edit', {
            parent: 'flow-application-sequence',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flow-application-sequence/flow-application-sequence-dialog.html',
                    controller: 'FlowApplicationSequenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FlowApplicationSequence', function(FlowApplicationSequence) {
                            return FlowApplicationSequence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flow-application-sequence', null, { reload: 'flow-application-sequence' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('flow-application-sequence.delete', {
            parent: 'flow-application-sequence',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/flow-application-sequence/flow-application-sequence-delete-dialog.html',
                    controller: 'FlowApplicationSequenceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FlowApplicationSequence', function(FlowApplicationSequence) {
                            return FlowApplicationSequence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flow-application-sequence', null, { reload: 'flow-application-sequence' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
