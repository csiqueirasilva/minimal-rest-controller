(function () {
	'use strict';

	var app = angular
		.module('main');

	app.factory('BuscaRestService', function ($rootScope, $interval, $http) {

		var data = {};

		data.lastFetch = null;
		data.lastStroke = 0;
		data.lastPage = null;

		var scope;
		var restPath;

		function getCompiledRestPath(string, page) {
			return restPath.replace('{busca}', string).replace('{pagina}', page);
		}

		var exp = {};

		exp.setScope = function (s) {
			scope = s;
		};

		exp.setRestPath = function (rp) {
			restPath = rp;
		};

		exp.getStroke = function () {
			return data.lastStroke;
		};

		exp.getLastFetch = function () {
			return data.lastFetch;
		};

		exp.clearIntervals = function () {
			if (data.lastFetch) {
				$interval.clear(data.lastFetch);
				data.lastFetch = null;
			}
		};

		var INTERVAL_STROKE_FETCH = 800;

		exp.repeat = function () {
			this.fetch(data.lastPage);
		};

		exp.fetch = function fetch(pageNumber) {
			$('.possibilidade-busca').hide();
			$('#loading').show();

			if (data.lastFetch === null) {

				if (!pageNumber) {
					pageNumber = 1;
				}

				data.lastPage = pageNumber;

				data.lastFetch = $interval(function () {

					var now = new Date().getTime();

					if ((now - data.lastStroke) > INTERVAL_STROKE_FETCH) {

						var val = $('#input-busca').val();

						if (!val) {
							val = " ";
						}

						$('.paginas-resultados').attr('ng-disabled', true);
						$('.possibilidade-busca').hide();
						$('#loading').show();

						$http.get(getCompiledRestPath(val, pageNumber))
							.then(function (response) {
								scope._resultados = response.data;
								//console.log(scope._resultados);
								$('#loading').hide();
								if (!response.data || response.data.content.length === 0) {
									$('#no-results').show();
								} else {
									$('#tabela-resultados').show();
									scope.currentPage = pageNumber;
									scope.totalItems = response.data.totalElements;
									$('.paginas-resultados').show();
									$('.paginas-resultados').attr('ng-disabled', false);
								}

							}, function (error) {
								$rootScope.handleRestError(error);
							})
							.then(function () {
								$interval.cancel(data.lastFetch);
								data.lastFetch = null;
								$('#loading').hide();
							});
					}

				}, INTERVAL_STROKE_FETCH * 1.1);
			}

			data.lastStroke = new Date().getTime();
		};

		return exp;

	})
		// src: https://stackoverflow.com/questions/19726179/how-to-make-ng-bind-html-compile-angularjs-code	
		.directive('buscaRest', function (BuscaRestService, $compile, $rootScope, $location, $interval, $http) {

			var ITEMS_PER_PAGE = 20; // todo: get via ajax

			return {
				restrict: 'A',
				replace: true,
				link: function (scope, element, attrs) {

					var buffer = element[0].innerHTML.replace(/<busca-rest-td( class=("|').*?("|'))?/g, '<td');
					buffer = buffer.replace(/\/busca-rest-td/g, '/td');

					var customContentRegExp = /<busca-rest-custom.*?>([\s\S]*?)<\/busca-rest-custom>/mg;
					var customContentReplaceExp = /<busca-rest-custom.*>([\s\S]*)<\/busca-rest-custom>/mg;

					var customBuffer = '';

					var customMatch = buffer.match(customContentRegExp);

					if (customMatch !== null) {

						customBuffer += customMatch[0]
							.replace(/<busca-rest-custom( class=("|').*?("|'))?/g, '<div')
							.replace(/\/busca-rest-custom/g, '/div');

						buffer = buffer.replace(customContentReplaceExp, '');
					}

					var headerContentRegExp = /<busca-rest-th.*?>(.*?)<\/busca-rest-th>/g;
					var headerContentReplaceExp = /<busca-rest-th.*>(.*)<\/busca-rest-th>/g;

					var headerBuffer = '';

					var headerMatch = buffer.match(headerContentRegExp);

					if (headerMatch !== null) {
						headerBuffer = '<thead><tr>';
						for (var i = 0; i < headerMatch.length; i++) {
							headerBuffer += headerMatch[i]
								.replace(/<busca-rest-th( class=("|').*?("|'))?/g, '<th')
								.replace(/\/busca-rest-th/g, '/th');
						}
						headerBuffer += '</tr></thead>';

						buffer = buffer.replace(headerContentReplaceExp, '');
					}

					var profilePath = attrs.profilePath;
					var restPath = attrs.restPath;

					var paginationElement = `<ul uib-pagination force-ellipses="true" boundary-links="true" id="bottom-pagination-busca" style="display: none;" total-items="totalItems" ng-click="fetchResultadoBusca(currentPage)" ng-model="currentPage" max-size="10" items-per-page="` + ITEMS_PER_PAGE + `" class="pagination-sm paginas-resultados possibilidade-busca" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></ul>`;

					scope.profilePath = profilePath;

					attrs._buscaRESTContent = `
		<input id="input-busca" class="form-control" ng-model="inputBusca" ng-keyup="fetchResultadoBusca()" />`
						+
						customBuffer
						+
						paginationElement
						+
						`
<table style="display: none;" id="tabela-resultados" class="table table-striped possibilidade-busca">`
						+ headerBuffer +
						`<tbody><tr ng-class="{jlink: profilePath}" ng-repeat="objeto in _resultados.content" ` + (profilePath ? 'ng-click="abrirProfile(objeto)"' : '') + `>
`
						+ buffer +
						`
	</tr></tbody>
</table>

`
					 + paginationElement + 
					`

<div style="display: none;" id="loading" class="possibilidade-busca">
	<!--img src="https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif" /-->
	<img src="https://darkiemindyou.files.wordpress.com/2015/04/loading6_230x230-cooler.gif" />
</div>

<div style="display: none;" id="no-results" class="possibilidade-busca">
	<h2>Nada foi encontrado</h2>
</div>
`;
					scope._resultados = [];
					scope.totalItems = 0;
					scope.currentPage = 1;

					element[0].innerHTML = attrs._buscaRESTContent;
					$compile(element.contents())(scope);

					BuscaRestService.clearIntervals();

					if (profilePath) {
						scope.abrirProfile = function (objeto) {
							$location.path(profilePath.replace(":id", objeto.id));
						};
					}

					BuscaRestService.setScope(scope);
					BuscaRestService.setRestPath(restPath);

					scope.fetchResultadoBusca = function (pageNumber) {
						BuscaRestService.fetch(pageNumber);
						return true;
					};

					angular.element(document).ready(function () {
						scope.fetchResultadoBusca(1);
					});
					
				}
			};
		});

})();
