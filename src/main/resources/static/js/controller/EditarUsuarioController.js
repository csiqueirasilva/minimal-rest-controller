(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/admin/listar/user/:id", {
				templateUrl: "editarusuario.html",
				controller: "EditarUsuarioController",
				controllerAs: 'vm'
			});
	});

	app.controller('EditarUsuarioController', function ($http, $rootScope, $scope, UserService, PessoaFisicaService, MedicoService, PacienteService, ClinicaService, FlashService, $window, $location, $routeParams, AuthenticationService) {

		var PATH_MEDICO = "form/medico.html",
			PATH_CLINICA = "form/clinica.html",
			PATH_ADMIN = "form/admin.html",
			PATH_PACIENTE = "form/paciente.html";

		var id = $routeParams.id;
		var vm = this;
		vm.modo = "alteração";

		AuthenticationService.GetCurrentUser().then(function(user) {
		});

		function getPath(roles) {
			var ret = null;
			for (var i = 0; i < roles.length && !ret; i++) {
				if (roles[i].role === "MEDICO") {
					ret = PATH_MEDICO;
				} else if (roles[i].role === "CLINICA") {
					ret = PATH_CLINICA;

					$scope.loadEspecialidades = function ($query) {
						return $http.get('json/especialidades', { cache: true }).then(function (response) {
							var objetos = response.data;
							return objetos.filter(function (objeto) {
								return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
							});
						});
					};

					$scope.loadMedicos = function ($query) {
						return $http.get('json/medicos', { cache: true }).then(function (response) {
							var objetos = response.data;
							return objetos.filter(function (objeto) {
								return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
							});
						});
					};

					$scope.loadExames = function ($query) {
						return $http.get('json/exames', { cache: true }).then(function (response) {
							var objetos = response.data;
							return objetos.filter(function (objeto) {
								return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
							});
						});
					};

				} else if (roles[i].role === "ADMIN") {
					ret = PATH_ADMIN;
				} else if (roles[i].role === "PACIENTE") {
					ret = PATH_PACIENTE;
				}
			}

			if (ret !== PATH_CLINICA) {
				$scope.dateOptions = {
					maxDate: new Date(),
					minDate: new Date(1920, 0, 1)
				};

				$scope.dtNascimentoDados = {};

				$scope.dtNascimentoDados.popup = false;

				$scope.dtNascimentoPick = function () {
					$scope.dtNascimentoDados.popup = true;
				};
			}

			return ret;
		}

		UserService
			.GetById(id)
			.then(function (response) {
				if (response.success) {
					AuthenticationService.GetCurrentUser().then(function(user) {
						response.body.data.username == user.data.name ? vm.keepLogin = true : vm.keepLogin = false;
					});
					vm.user = response.body.data;
					vm.user.password = '';
					vm.includePath = getPath(vm.user.roles);
				}
			});

		function register() {
			vm.dataLoading = true;

			var service;

			if (vm.includePath === PATH_MEDICO) {
				service = MedicoService;
			} else if (vm.includePath === PATH_CLINICA) {
				service = ClinicaService;
			} else if (vm.includePath === PATH_ADMIN) {
				service = PessoaFisicaService;
			} else if (vm.includePath === PATH_PACIENTE) {
				service = PacienteService;
			}
			var userRegister = UserService.Setup($rootScope, vm, service, $window, $location, FlashService, AuthenticationService);
			userRegister();
		}

		vm.register = register;

	});

})();