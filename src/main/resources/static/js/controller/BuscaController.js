(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/busca", {
				templateUrl: "busca.html",
				controller: "BuscaController"
			});
	});

	app.controller('BuscaController', function ($scope, BuscaRestService, $http, $rootScope, $interval, $location) {

		var buscarParticipacoesComFiltros = function (novo) {
			var p = "json/busca/{busca}/{pagina}" + "/" + novo;
			BuscaRestService.setRestPath(p);
			BuscaRestService.repeat();
		};

		$scope.$watch('tipoBusca', function (novo, antigo) {
			if (novo === antigo && novo === undefined)
				return;
			buscarParticipacoesComFiltros(novo);
		});

	});

})();