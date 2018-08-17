(function () {
    'use strict';

    angular
        .module('txnMetaDataApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
