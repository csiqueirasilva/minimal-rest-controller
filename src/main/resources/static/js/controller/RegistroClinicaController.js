(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/registro/clinica", {
				templateUrl: "form/clinica.html",
				controller: "RegistroClinicaController",
				controllerAs: 'vm'
			});
	});

	app.controller('RegistroClinicaController', RegistroClinicaController);
	function RegistroClinicaController($location, $scope, $http, $rootScope, $window, FlashService, UserService, ClinicaService) {
		var vm = this;

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

		vm.register = UserService.Setup($rootScope, vm, ClinicaService, $window, $location, FlashService);
	}

})();