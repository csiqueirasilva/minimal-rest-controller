(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/clinica/:id", {
				templateUrl: "clinica.html",
				controller: "ClinicaController",
			});
	});

	app.controller('ClinicaController', function ($scope, $http, $rootScope, $location, $routeParams, FlashService, $cookies, AuthenticationService) {

		var id = $routeParams.id;

		$http.get('json/clinica/' + id)
			.then(function (response) {
				$scope._clinica = response.data;

				$scope._clinica.rede = $scope._clinica.tipoAtendimento === "GRATUITO" ? "Pública" : "Privada";

				for (var i = 0; i < $scope._clinica.telefonesDeContato.length; i++) {
					var tel = $scope._clinica.telefonesDeContato[i];
					var cut = Math.ceil((tel.length - 2) / 2);
					tel = "(" + tel.substring(0, 2) + ") " + tel.substring(2, 2 + cut) + "-" + tel.substring(2 + cut, tel.length);
					$scope._clinica.telefonesDeContato[i] = tel;
				}

			}, function () {
				alert("Erro ao recuperar resultados!");
				$location.path("/");
			})
			.then(function () {
				$('#loading').hide();
			});

		$scope.addClinica = function () {
			AuthenticationService.GetCurrentUser().then(function(user){
				if(user.data.authorities["0"].authority == "PACIENTE"){
					$http.post('/paciente/' + user.data.name + '/' + id).finally(
						function () {
							$location.path("/home/paciente");
					});
				}
			})
		}

	});

})();