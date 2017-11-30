(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/clinica/consulta", {
				templateUrl: "form/consulta.html",
				controller: "RegistroConsultaController",
				controllerAs: 'vm'
			});
	});

	app.controller('RegistroConsultaController', RegistroConsultaController);
	function RegistroConsultaController($location, $scope, $http, $rootScope, $window, FlashService, UserService, ConsultaService, AuthenticationService) {

		var vm = this;
		vm.modo = 'cadastro';

		AuthenticationService.GetCurrentUser().then(function(user) {
			vm.nomeclinica = user.data.name;
		});

		$scope.loadPacientes = function ($query) {
			return $http.get('json/pacientes', { cache: true }).then(function (response) {
				var objetos = response.data;
				return objetos.filter(function (objeto) {
					return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
				});
			});
		};

		$scope.loadMedicos = function ($query) {
			return $http.get('clinica/medicos/' + vm.nomeclinica, { cache: true }).then(function (response) {
				var objetos = response.data;
				return objetos.filter(function (objeto) {
					return objeto.nome.toLowerCase().indexOf($query.toLowerCase()) !== -1;
				});
			});
		};

		vm.reg = UserService.Setup($rootScope, vm, ConsultaService, $window, $location, FlashService, null, false);

		vm.register = function () {
			UserService
			.GetByUsername(vm.nomeclinica)
			.then(function (response) {
				if (response.success) {
					vm.clinica = response.body.data;
					vm.user.clinica = vm.clinica;
					vm.user.paciente = vm.pacientes[0];
					vm.user.medico = vm.medicos[0];
					vm.user.exames = null; // TODO: EXAMES
					vm.reg();
				}
			});
		};
	}

})();