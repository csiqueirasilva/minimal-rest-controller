(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/logout", {
				templateUrl: "logout.html",
				controller: "LogoutController",
				controllerAs: 'vm'
			});
	});

	app.controller('LogoutController', function LogoutController($http, $location, $rootScope, FlashService, $window, AuthenticationService) {

		var vm = this;
		vm.credentials = {};

		$rootScope.logout = true;

		AuthenticationService.ClearUser();
	});

})();