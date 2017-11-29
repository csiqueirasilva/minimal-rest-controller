(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/paciente/:id", {
				templateUrl: "listarconsulta.html",
				controller: "ListarConsultaController",
				controllerAs: 'vm'
			});
	});

	app.controller('ListarConsultaController', function ($http, $scope, FlashService, $window, $location, $routeParams, UserService, AuthenticationService) {

		var vm = this;
		vm.clinicaid = $routeParams.id;

		initController();

		function initController() {
			AuthenticationService.GetCurrentUser().then(function(user) {
				vm.username = user.data.name;
			});
			UserService
			.GetById(vm.clinicaid)
			.then(function (response) {
				if (response.success) {
					vm.clinicanome = response.body.data.nome;
				}
			});
		}

		vm.deleteClinica = function () {
			var string = "/paciente/" + vm.username + "/" + vm.clinicaid;
			$http.delete(string).then(
				function () {
					$location.path("/home/paciente");
				},
				function () {
					$location.path("/");
				});
		}
	});

})();