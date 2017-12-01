(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/paciente", {
				templateUrl: "homepaciente.html",
				controller: "HomePacienteController",
				controllerAs: 'vm'
			});
	});

	app.controller('HomePacienteController', function HomePacienteController($rootScope, $location, $http, UserService, $window, $cookies, AuthenticationService) {
		var vm = this;
		AuthenticationService.GetCurrentUser().then(function(user) {
			if(user.data.authorities["0"].authority == 'PACIENTE')
				vm.user = user.data.name;
		});
	});

})();