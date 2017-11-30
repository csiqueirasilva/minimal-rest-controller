(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/clinica", {
				templateUrl: "homeclinica.html",
				controller: "HomeClinicaController",
				controllerAs: 'vm'
			});
	});

	app.controller('HomeClinicaController', function HomeClinicaController($rootScope, $location, $http, UserService, $window, AuthenticationService, FlashService) {
		var vm = this;
		
		AuthenticationService.GetCurrentUser().then(function(user) {
			vm.user = user.data.name;
		});
	});

})();