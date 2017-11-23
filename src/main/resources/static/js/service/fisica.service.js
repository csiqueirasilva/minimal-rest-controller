(function () {
    'use strict';

    angular
            .module('main')
            .factory('PessoaFisicaService', PessoaFisicaService);

    function PessoaFisicaService($http) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get('/fisica/').then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get('/fisica/id/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get('/fisica/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user) {
            return $http.post('/fisica/', user).then(handleSuccess, handleError('Erro no cadastro'));
        }

        function Update(user) {
            return $http.put('/fisica/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete('/fisica/' + id).then(handleSuccess, handleError('Error deleting user'));
        }

        function handleSuccess(res) {
            return {success: true, body: res};
        }

        function handleError(error) {
            return function () {
                return {success: false, message: error};
            };
        }
    }

})();