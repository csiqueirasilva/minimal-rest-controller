(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/admin/editar/:username", {
				templateUrl: "form/admin.html",
				controller: "EditarAdminController",
				controllerAs: 'vm'
			});
	});

	app.controller('EditarAdminController', function ($http, $rootScope, $scope, UserService, PessoaFisicaService, FlashService, $window, $location, $routeParams, AuthenticationService) {

		var vm = this;
		vm.oldUsername = $routeParams.username;
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
			PessoaFisicaService.Update(vm.user)
				.then(function (updateResponse) {
					if (updateResponse.success) {
						AuthenticationService.authenticate({ username : vm.user.username, password : vm.user.password }, function(authResponse){
							vm.dataLoading = false;
							if(authResponse.success){
								FlashService.Success('Registro editado com sucesso', true);
								$location.path("/home/admin");
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