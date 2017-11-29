(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/admin", {
				templateUrl: "homeadmin.html",
				controller: "HomeAdminController",
				controllerAs: 'vm'
			});
	});

	app.controller('HomeAdminController', function HomeAdminController($rootScope, $location, $http, UserService, $window, AuthenticationService) {
		var vm = this;

		AuthenticationService.GetCurrentUser().then(function(user) {
			vm.user = user.data.name;
		});
	});

})();