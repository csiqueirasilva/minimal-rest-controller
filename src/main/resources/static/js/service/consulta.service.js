(function () {
    'use strict';

    angular
            .module('main')
            .factory('ConsultaService', ConsultaService);

    ConsultaService.$inject = ['$http'];
    function ConsultaService($http) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;

        return service;

        function GetAll() {
            return $http.get('/consulta/').then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get('/consulta/id/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function Create(user) {
            return $http.post('/consulta/', user).then(handleSuccess, handleError('Erro no cadastro'));
        }

        function Update(user) {
            return $http.put('/consulta/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete('/consulta/' + id).then(handleSuccess, handleError('Error deleting user'));
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