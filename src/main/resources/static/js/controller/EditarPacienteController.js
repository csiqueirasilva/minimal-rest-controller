(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/paciente/editar/:username", {
				templateUrl: "form/paciente.html",
				controller: "EditarPacienteController",
				controllerAs: 'vm'
			});
	});

	app.controller('EditarPacienteController', function ($http, $rootScope, $scope, UserService, PacienteService, FlashService, $window, $location, $routeParams, AuthenticationService) {

		var vm = this;
		vm.oldUsername  = $routeParams.username;
		vm.modo = "alteração";

		AuthenticationService.GetCurrentUser().then(function(user) {
			if(user.data.name !== vm.oldUsername && user.data.authorities[0].authority !== 'ADMIN')
				$location.path("/");
		});

		UserService
			.GetByUsername(vm.oldUsername)
			.then(function (response) {
				if (response.success) {
					vm.user = response.body.data;
					vm.user.password = '';
				}
			});

		function register() {
            vm.dataLoading = true;
            
			PacienteService.Update(vm.user)
				.then(function (updateResponse) {
					vm.dataLoading = false;
					if (updateResponse.success) {
						AuthenticationService.authenticate({ username : vm.user.username, password : vm.user.password }, function(authResponse){
							vm.dataLoading = false;
							if(authResponse.success){
								FlashService.Success('Registro editado com sucesso', true);
								$location.path("/home/paciente");
							}
							else{
								FlashService.Error(authResponse.message + ': Erro ao editar registro', true);
								$window.scrollTo(0, 0);
							}
						});
					} else {
						FlashService.Error(updateResponse.message + ': Erro ao editar registro', true);
						$window.scrollTo(0, 0);
					}
				});
		}

		vm.register = register;

	});

})();