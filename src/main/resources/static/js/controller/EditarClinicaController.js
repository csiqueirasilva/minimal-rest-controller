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

		AuthenticationService.GetCurrentUser().then(function (user) {
			if (user.data.name !== vm.oldUsername && user.data.authorities[0].authority !== 'ADMIN')
				$location.path("/");
		});

		$scope.loadEspecialidades = function ($query) {
			return $http.get('json/especialidades', {cache: true}).then(function (response) {
				var objetos = response.data;
				return objetos.filter(function (objeto) {
					return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
				});
			});
		};

		$scope.loadMedicos = function ($query) {
			return $http.get('json/medicos', {cache: true}).then(function (response) {
				var objetos = response.data;
				return objetos.filter(function (objeto) {
					return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
				});
			});
		};

		$scope.loadExames = function ($query) {
			return $http.get('json/exames', {cache: true}).then(function (response) {
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

			vm.register = UserService.Setup($rootScope, vm, ClinicaService, $window, $location, FlashService, AuthenticationService, false);

	});

})();