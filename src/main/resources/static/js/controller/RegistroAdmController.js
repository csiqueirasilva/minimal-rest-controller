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
	RegistroAdmController.$inject = ['$location', '$rootScope', '$window', 'FlashService', 'UserService'];
	function RegistroAdmController($location, $rootScope, $window, FlashService, UserService) {
		var vm = this;
		vm.register = UserService.Setup($rootScope, vm, UserService, $window, $location, FlashService);
	}

})();