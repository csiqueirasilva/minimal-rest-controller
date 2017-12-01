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
			var path = user.data.authorities[0].authority.toLowerCase();
			if(path != 'admin'){
				$location.path("/home/"+path);
				FlashService.Error('Você não pode acessar esta pagina', true);
			}
			user.data.name==vm.oldUsername ? vm.keepLogin = true : vm.keepLogin = false;
		});

			PessoaFisicaService
				.GetByUsername(vm.oldUsername)
				.then(function (response) {
					if (response.success) {
						vm.user = response.body.data;
						vm.user.password = '';
					}
				});

			vm.register = UserService.Setup($rootScope, vm, PessoaFisicaService, $window, $location, FlashService, AuthenticationService);

	});

})();