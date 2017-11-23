(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/user/editar/:id", {
				templateUrl: "editarusuario.html",
				controller: "EditarUsuarioController",
				controllerAs: 'vm'
			});
	});

	app.controller('EditarUsuarioController', function ($scope, UserService, PessoaFisicaService, MedicoService, PacienteService, ClinicaService, FlashService, $window, $location, $routeParams) {

		var PATH_MEDICO = "form/medico.html",
			PATH_CLINICA = "form/clinica.html",
			PATH_ADMIN = "form/admin.html",
			PATH_PACIENTE = "form/paciente.html";

		var id = $routeParams.id;
		var vm = this;

		function getPath(roles) {
			var ret = null;
			for (var i = 0; i < roles.length && !ret; i++) {
				if (roles[i].role === "MEDICO") {
					ret = PATH_MEDICO;
				} else if (roles[i].role === "CLINICA") {
					ret = PATH_CLINICA;
				} else if (roles[i].role === "ADMIN") {
					ret = PATH_ADMIN;
				} else if (roles[i].role === "PACIENTE") {
					ret = PATH_PACIENTE;
				}
			}
			return ret;
		}

		UserService
			.GetById(id)
			.then(function (response) {
				if (response.success) {
					vm.user = response.body.data;
					vm.user.password = '';
					vm.includePath = getPath(vm.user.roles);
				}
			});


		$scope.dateOptions = {
			maxDate: new Date(),
			minDate: new Date(1920, 0, 1)
		};

		$scope.dtNascimentoDados = {};

		$scope.dtNascimentoDados.popup = false;

		$scope.dtNascimentoPick = function () {
			$scope.dtNascimentoDados.popup = true;
		};

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

			service.Update(vm.user)
				.then(function (response) {
					vm.dataLoading = false;
					if (response.success) {
						FlashService.Success('Registro editado com sucesso', true);
						$location.path("/listar/usuarios");
					} else {
						FlashService.Error(response.message + ': Erro ao editar registro', true);
						$window.scrollTo(0, 0);
					}
				});
		}

		vm.register = register;

	});

})();