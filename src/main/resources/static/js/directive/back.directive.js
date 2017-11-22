(function () {
	'use strict';

	var app = angular
		.module('main');

	// src: https://stackoverflow.com/a/23351154/1988747
	app.directive('back', ['$window', function ($window) {
			return {
				restrict: 'A',
				link: function (scope, elem, attrs) {
					elem.bind('click', function () {
						$window.history.back();
					});
				}
			};
		}]);

})();