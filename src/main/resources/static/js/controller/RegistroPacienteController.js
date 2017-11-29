(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/registro/paciente", {
				templateUrl: "form/paciente.html",
				controller: "RegistroPacienteController",
				controllerAs: 'vm'
			});
	});

	app.controller('RegistroPacienteController', function RegistroPacienteController($location, $rootScope, $window, FlashService, PacienteService, UserService) {
		var vm = this;
		vm.modo = "cadastro";
		vm.register = UserService.Setup($rootScope, vm, PacienteService, $window, $location, FlashService);
	});

})();