(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/", {
				templateUrl: "main.html",
				controller: "LoginController",
				controllerAs: 'vm'
			});
	});

	app.controller('LoginController', function LoginController($http, $location, $rootScope, FlashService, $window, AuthenticationService) {

		var vm = this;
		vm.credentials = {};

		if (AuthenticationService.IsAuthenticated()) {
			AuthenticationService.GoUserHome();
		} else {
			AuthenticationService.ClearUser();
			vm.login = function () {
				AuthenticationService.authenticate(vm.credentials, function (response) {
					if (response.success) {
						$location.path("/home/" + response.user.role);
					} else {
						FlashService.Error('Falha ao logar, verifique se o usuario e a senha estao corretos', false);
					}
				});
			};
		}
		
	});

})();