(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/home/admin/listar/user", {
				templateUrl: "listarusuario.html",
				controller: "ListarUsuarioController"
			});
	});

	app.controller('ListarUsuarioController', function ($scope, $mdDialog, FlashService, $rootScope, BuscaRestService, UserService, $http, $interval, $location) {

		$scope.excluirUsuario = function (ev, usuario) {

			$mdDialog.show({
				controller: DialogController,
				templateUrl: 'confirmdialog.tmpl.html',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true
			})
				.then(function (answer) {
					if(answer) {
						UserService.GetServiceByType(usuario).Delete(usuario.id)
							.then(function() {
								FlashService.Success("usuário " + usuario.username + " excluído");
								BuscaRestService.repeat();
							});
					}
				}, function () {
				});

			function DialogController($scope, $mdDialog) {
				$scope.texto = 'O usuário <span class="texto-destaque">' + usuario.username + '</span> será excluido. Você tem certeza?';

				$scope.resposta = function (answer) {
					$mdDialog.hide(answer);
				};
			}
			
			 ev.stopPropagation();
		};


	});

})();