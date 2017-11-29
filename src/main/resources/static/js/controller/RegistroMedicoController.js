(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/registro/medico", {
				templateUrl: "form/medico.html",
				controller: "RegistroMedicoController",
				controllerAs: 'vm'
			});
	});

	app.controller('RegistroMedicoController', function RegistroMedicoController($location, $rootScope, $window, FlashService, MedicoService, UserService) {
		var vm = this;
		vm.modo = "cadastro";
		vm.register = UserService.Setup($rootScope, vm, MedicoService, $window, $location, FlashService);
	});

})();