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
			vm.user = user.data.name;
		});
	});

})();