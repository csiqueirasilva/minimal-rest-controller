(function () {
	'use strict';

	var app = angular
		.module('main');

	app.config(function ($routeProvider) {
		$routeProvider
			.when("/registro", {
				templateUrl: "registro.html",
			});
	});

})();

