(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/clinica/editar/:username", {
				templateUrl: "form/clinica.html",
				controller: "EditarClinicaController",
				controllerAs: 'vm'
			});
	});

	app.controller('EditarClinicaController', function ($http, $rootScope, $scope, UserService, ClinicaService, FlashService, $window, $location, $routeParams, AuthenticationService) {

		var vm = this;
		vm.oldUsername = $routeParams.username;
		vm.modo = "alteração";

		AuthenticationService.GetCurrentUser().then(function(user) {
			if(user.data.name != vm.oldUsername)
				$location.path("/");
		});
		
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

		UserService
			.GetByUsername(vm.oldUsername)
			.then(function (response) {
				if (response.success) {
					vm.user = response.body.data;
					vm.user.password = '';
				}
			});

		function register() {
			vm.dataLoading = true;

			ClinicaService.Update(vm.user)
				.then(function (updateResponse) {
					vm.dataLoading = false;
					if (updateResponse.success) {
						AuthenticationService.authenticate({ username: vm.user.username, password: vm.user.password }, function (authResponse) {
							vm.dataLoading = false;
							if (authResponse.success) {
								FlashService.Success('Registro editado com sucesso', true);
								$location.path("/home/clinica");
							}
							else {
								FlashService.Error(authResponse.message + ': Erro ao editar registro', true);
								$window.scrollTo(0, 0);
							}
						});
					} else {
						FlashService.Error(updateResponse.message + ': Erro ao editar registro', true);
						$window.scrollTo(0, 0);
					}
				});
		}

		vm.register = register;

	});

})();