(function () {

	var app = angular.module('main', ['ui.mask', 'ui.bootstrap', 'ui.bootstrap.tpls', "ui.bootstrap.datepicker", "ui.bootstrap.datepickerPopup", 'ngSanitize', 'ngAnimate', 'ngRoute', 'ngCookies', 'ngTagsInput']);

	app.config(function ($routeProvider, $httpProvider) {

		$routeProvider
			.otherwise({
				redirectTo: "/"
			});

		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

		$httpProvider.interceptors.push(function ($q, $location, FlashService) {
			return {
				'responseError': function (rejection) {
					if (rejection.status === 401) {
						$location.path('/');

					} else if (rejection.status === 404) {
						FlashService.Error('Pagina nao encontrada', true);
						$location.path('/');
					}
					return $q.reject(rejection);
				}
			};
		});
	});

	app.run(function ($rootScope, $cookies) {
		$rootScope.usuarioAtual = $cookies.get('usuarioAtual');
	});

})();