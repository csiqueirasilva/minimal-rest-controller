(function () {
    'use strict';

    angular
            .module('main')
            .factory('UserService', UserService);

    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;
        service.Setup = Setup;

        return service;

        function Setup(scope, vm, UserService, window, location, FlashService) {

            scope.dateOptions = {
                maxDate: new Date(),
                minDate: new Date(1920, 0, 1)
            };

            scope.dtNascimentoDados = {};

            scope.dtNascimentoDados.popup = false;

            scope.dtNascimentoPick = function () {
                scope.dtNascimentoDados.popup = true;
            };

            function register() {
                vm.dataLoading = true;
                UserService.Create(vm.user)
                        .then(function (response) {
                            vm.dataLoading = false;
                            if (response.success) {
                                if (response.body.status === 201) {
                                    FlashService.Success('Registro bem sucedido', true);
                                    location.path('/');
                                }
                            } else {
                                FlashService.Error(response.message + ': Nome de usuario ja existe', true);
                                window.scrollTo(0, 0);
                            }
                        });
            }

            return register;
        }

        function GetAll() {
            return $http.get('/user/').then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get('/user/id/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get('/user/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user) {
            return $http.post('/user/', user).then(handleSuccess, handleError('Erro no cadastro'));
        }

        function Update(user) {
            return $http.put('/user/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete('/user/' + id).then(handleSuccess, handleError('Error deleting user'));
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