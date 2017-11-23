(function () {
    'use strict';

    var app = angular
            .module('main');

    app.config(function ($routeProvider) {
        $routeProvider
                .when("/listar/usuarios", {
                    templateUrl: "listarusuario.html",
                    controller: "ListarUsuarioController"
                });
    });

    app.controller('ListarUsuarioController', function ($scope, BuscaRestService, $http, $rootScope, $interval, $location) {
    });

})();