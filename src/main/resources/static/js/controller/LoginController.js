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

	app.controller('LoginController', function LoginController($http, $location, $rootScope, FlashService, $window) {

		var vm = this;

		var authenticate = function (credentials, callback) {

			var headers = credentials ? {authorization: "Basic "
					+ btoa(credentials.username + ":" + credentials.password)
			} : {};

			$http.get('login', {headers: headers}).then(function (response) {
				if (response.data.name) {
					$rootScope.authenticated = true;
				} else {
					$rootScope.authenticated = false;
				}
				callback && callback();
			}, function () {
				$rootScope.authenticated = false;
				callback && callback();
			});
		};

		authenticate();

		vm.credentials = {};
		vm.login = function () {
			authenticate(vm.credentials, function () {
				if ($rootScope.authenticated) {
					$location.path("/admin");
				} else {
					FlashService.Error('Falha ao logar, verifique se o usuario e a senha estao corretos', true);
				}
			});
		};

		vm.logout = function () {
			$http.post('/logout', {}).finally(function () {
				$window.location.reload();
				$rootScope.authenticated = false;
			});
		};

	});

})();