(function () {
    'use strict';

    angular
        .module('main')
        .factory('UserService', UserService);

    function UserService($http, $rootScope, PacienteService, MedicoService, PessoaFisicaService, ClinicaService) {
        var service = {};

        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Setup = Setup;
		service.GetServiceByType = GetServiceByType;

        return service;

		function GetServiceByType(usuario) {
			var ret = null;
			if(usuario instanceof Object && usuario.roles instanceof Array) {
				for(var i = 0; ret === null && i < usuario.roles.length; i++) {
					var role = usuario.roles[i];
					if(role.role === "CLINICA") {
						ret = ClinicaService;
					} else if (role.role === "MEDICO") {
						ret = MedicoService;
					} else if (role.role === "ADMIN") {
						ret = PessoaFisicaService;
					} else if (role.role === "PACIENTE") {
						ret = PacienteService;
					}
				}
			}
			return ret;
		}

        function Setup(scope, vm, service, window, location, FlashService, AuthenticationService) {

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
                if (vm.modo == 'alteração') {
                    service.Update(vm.user)
                        .then(function (updateResponse) {
                            if (updateResponse.success) {
                                if (vm.keepLogin) {
                                    AuthenticationService.authenticate({ username: vm.user.username, password: vm.user.password }, function (authResponse) {
                                        vm.dataLoading = false;
                                        if (authResponse.success) {
                                            FlashService.Success('Alteração dos dados de ' + authResponse.user.username + ' bem sucedida', true);
                                            location.path('/home/' + $rootScope.usuarioAtual.role);
                                        }
                                        else {
                                            FlashService.Error(authResponse.message + ': Erro ao alterar dados', false);
                                            window.scrollTo(0, 0);
                                        }
                                    });
                                }
                                else {
                                    vm.dataLoading = false;
                                    FlashService.Success('Alteração dos dados bem sucedida', true);
                                    location.path('/home/admin');
                                }
                            }
                            else {
                                vm.dataLoading = false;
                                FlashService.Error(updateResponse.message + ': Erro ao alterar dados', false);
                                window.scrollTo(0, 0);
                            }
                        });
                }
                else {
                    service.Create(vm.user)
                        .then(function (response) {
                            vm.dataLoading = false;
                            if (response.success) {
                                if (response.body.status === 201) {
                                    FlashService.Success('Cadastro bem sucedido', true);
                                    vm.nomeclinica ? location.path('/home/clinica') : location.path('/'); //consulta
                                }
                            } else {
                                FlashService.Error(response.message + ': Nome de usuario ja existe', false);
                                window.scrollTo(0, 0);
                            }
                        });
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