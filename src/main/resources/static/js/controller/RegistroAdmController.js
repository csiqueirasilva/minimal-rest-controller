(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/registro/admin", {
				templateUrl: "form/admin.html",
				controller: "RegistroAdmController",
				controllerAs: 'vm'
			});
	});

	app.controller('RegistroAdmController', RegistroAdmController);
	function RegistroAdmController($location, $rootScope, $window, FlashService, UserService, PessoaFisicaService) {
		var vm = this;
		vm.modo = 'cadastro';
		vm.register = UserService.Setup($rootScope, vm, PessoaFisicaService, $window, $location, FlashService, null, false);
	}

})();