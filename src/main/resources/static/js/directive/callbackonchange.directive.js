(function () {
	'use strict';

	var app = angular
		.module('main');

	app.directive('callbackOnChange', function () {
		return {
			restrict: 'A',
			replace: true,
			link: function (scope, element, attrs) {
				scope.$watch(attrs.callbackWatch, function (newVal, oldVal) {
					if(newVal === oldVal && newVal === undefined) return;
					scope.$eval(attrs.callbackOnChange);
				});
			}
		};
	});

})();
