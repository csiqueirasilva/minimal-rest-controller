(function () {

	var app = angular.module('main', ['ui.mask', 'ui.bootstrap', 'ui.bootstrap.tpls', "ui.bootstrap.datepicker", "ui.bootstrap.datepickerPopup", 'ngSanitize', 'ngAnimate', 'ngRoute', 'ngCookies', 'ngTagsInput', 'ngMaterial']);

	app.config(function ($routeProvider, $httpProvider) {

		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

		$routeProvider
			.otherwise({
				redirectTo: "/"
			});

		$httpProvider.interceptors.push(function ($q, $location, FlashService, $cookies) {
			return {
				'responseError': function (rejection) {
					var usuarioAtual = $cookies.getObject('usuarioAtual');
					if (rejection.status === 400) {
						if (usuarioAtual)
							$location.path("/home/" + usuarioAtual.role);
						else
							$location.path("/");
						FlashService.Error('URL Inválida', true);
					} else if (rejection.status === 401) {
						if (usuarioAtual)
							$location.path("/home/" + usuarioAtual.role);
						else
							$location.path("/");
						FlashService.Error('Você não pode acessar esta pagina', true);
					} else if (rejection.status === 403) {
						if (usuarioAtual)
							$location.path("/home/" + usuarioAtual.role);
						else
							$location.path("/");
						FlashService.Error('Você não pode acessar esta pagina', true);
					} else if (rejection.status === 404) {
						if (usuarioAtual)
							$location.path("/home/" + usuarioAtual.role);
						else
							$location.path("/");
						FlashService.Error('Pagina nao encontrada', true);
					}
					return $q.reject(rejection);
				}
			};
		});
	});

	app.run(function (AuthenticationService) {
		AuthenticationService.ReloadUserFromCookie();
	});

})();