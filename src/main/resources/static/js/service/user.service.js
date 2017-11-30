(function () {
    'use strict';

    angular
        .module('main')
        .factory('UserService', UserService);

    function UserService($http, $rootScope) {
        var service = {};

        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Setup = Setup;

        return service;

        function Setup(scope, vm, service, window, location, FlashService, AuthenticationService, adminEdit) {

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
                switch(vm.modo) {
                    case 'alteração':
                        service.Update(vm.user)
                        .then(function (updateResponse) {
                            if (updateResponse.success) {
                                if(!adminEdit){
                                    AuthenticationService.authenticate({ username : vm.user.username, password : vm.user.password }, function(authResponse){
                                        vm.dataLoading = false;
                                        if(authResponse.success){
                                            FlashService.Success('Alteração dos dados de ' + authResponse.user.username + ' bem sucedida', true);
                                            location.path('/home/'+$rootScope.usuarioAtual.role);
                                        }
                                        else{
                                            FlashService.Error(authResponse.message + ': Erro ao alterar dados', false);
                                            window.scrollTo(0, 0);
                                        }
                                    });
                                }
                                else{
                                    vm.dataLoading = false;
                                    FlashService.Success('Alteração dos dados bem sucedida', true);
                                    location.path('/home/admin');
                                }
                            } else {
                                vm.dataLoading = false;
                                FlashService.Error(updateResponse.message + ': Erro ao alterar dados', false);
                                window.scrollTo(0, 0);
                            }
                        });
                        break;
                    case 'cadastro':
                        service.Create(vm.user)
                        .then(function (response) {
                            vm.dataLoading = false;
                            if (response.success) {
                                if (response.body.status === 201) {
                                    FlashService.Success('Cadastro bem sucedido', true);
                                    vm.nomeclinica ? location.path('/home/clinica') : location.path('/');
                                }
                            } else {
                                FlashService.Error(response.message + ': Nome de usuario ja existe', false);
                                window.scrollTo(0, 0);
                            }
                        });
                        break;
                }
            }

            return register;
        }

        function GetById(id) {
            return $http.get('/user/id/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get('/user/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }

        function handleSuccess(res) {
            return { success: true, body: res };
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();