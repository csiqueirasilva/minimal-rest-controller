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
			var path = user.data.authorities[0].authority.toLowerCase();
			if(path == 'admin'){
				vm.keepLogin = false;
			}
			else if(user.data.name==vm.oldUsername){
				vm.keepLogin = true;
			}
			else{
				$location.path("/home/"+path);
				FlashService.Error('Você não pode acessar esta pagina', true);
			}
		});

		PacienteService
			.GetByUsername(vm.oldUsername)
			.then(function (response) {
				if (response.success) {
					vm.user = response.body.data;
					vm.user.password = '';
				}
			});

		vm.register = UserService.Setup($rootScope, vm, PacienteService, $window, $location, FlashService, AuthenticationService);

	});

})();