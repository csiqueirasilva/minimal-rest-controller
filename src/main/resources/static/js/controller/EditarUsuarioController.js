(function () {
    'use strict';

    var app = angular
            .module('main');

    app.config(function ($routeProvider) {
        $routeProvider
                .when("/user/editar/:id", {
                    templateUrl: "form/medico.html",
                    controller: "EditarUsuarioController",
                    controllerAs: 'vm'
                });
    });

    app.controller('EditarUsuarioController', function ($scope, $http, UserService, $location, $routeParams) {

        var id = $routeParams.id;
        var vm = this;

        UserService
                .GetById(id)
                .then(function (response) {
                    if (response.success) {
                        vm.user = response.body.data;
                        vm.user.password = '';
                        vm.includePath = 'form/medico.html';
                    }
                });

    });

})();