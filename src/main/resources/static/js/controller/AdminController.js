(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/admin", {
				templateUrl: "admin.html",
				controller: "AdminController",
				controllerAs: 'vm'
			});
	});

	app.controller('AdminController', function AdminController($rootScope, $location, $http, UserService, $window) {
		var vm = this;
		vm.allUsers = [];

		vm.logout = function () {
			$http.post('/logout', {}).finally(function () {
				$location.path("/");
				$window.location.reload();
				$rootScope.authenticated = false;
			});
		};

		initController();

		function initController() {
			loadCurrentUser();
			loadAllUsers();
		}

		function loadCurrentUser() {
			$http.get('login', {}).then(function (response) {
				if (response.data.name) {
					vm.user = response.data.name;
				} else {
					$rootScope.authenticated = false;
				}
			});
		}

		function loadAllUsers() {
			UserService.GetAll()
				.then(function (users) {
					vm.allUsers = users.body.data;
				});
		}
	});

})();