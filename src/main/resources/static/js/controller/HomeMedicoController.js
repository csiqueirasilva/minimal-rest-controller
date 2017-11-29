(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/medico", {
				templateUrl: "homemedico.html",
				controller: "HomeMedicoController",
				controllerAs: 'vm'
			});
	});

	app.controller('HomeMedicoController', function HomeMedicoController($rootScope, $location, $http, UserService, $window, AuthenticationService) {
		var vm = this;

		AuthenticationService.GetCurrentUser().then(function(user) {
			vm.user = user.data.name;
		});
	});

})();