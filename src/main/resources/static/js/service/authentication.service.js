(function () {
    'use strict';

    angular
        .module('main')
        .factory('AuthenticationService', AuthenticationService);

    function AuthenticationService($http, $cookies, $rootScope, $window, $location) {
        var service = {};

        service.authenticate = authenticate;
        service.GetCurrentUser = GetCurrentUser;
        service.ClearUser = ClearUser;
		service.IsAuthenticated = IsAuthenticated;
		service.GoUserHome = GoUserHome;
		service.ReloadUserFromCookie = ReloadUserFromCookie;

        return service;

        function authenticate(credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('/login', { headers: headers }).then(function (response) {
                $rootScope.usuarioAtual = {
                    authenticated: true,
                    username: response.data.name,
                    role: response.data.authorities["0"].authority.toLowerCase()
                };
                var cookieExp = new Date();
                cookieExp.setDate(cookieExp.getDate() + 7);
                $cookies.putObject('usuarioAtual', $rootScope.usuarioAtual, { expires: cookieExp });
                callback && callback({ success: true, path: $rootScope.usuarioAtual.role });
            }, function () {
                $rootScope.usuarioAtual = {};
                callback && callback({});
            });
        };

        function GetCurrentUser(callback) {
            return $http.get('/login', {});
        }

        function ClearUser(callback) {
            if ($cookies.get('usuarioAtual')) {
                $http.post('/logout', {}).finally(function () {
                    $rootScope.usuarioAtual = {};
                    $cookies.remove('usuarioAtual');
					$location.path("/");
					$rootScope.logout = false;
                });
            }
        }
		
		function IsAuthenticated() {
			return $cookies.get('usuarioAtual') && $rootScope.usuarioAtual && $rootScope.usuarioAtual !== {} && $rootScope.usuarioAtual.role;
		}
		
		function ReloadUserFromCookie() {
			if(IsAuthenticated()) {
				$rootScope.usuarioAtual = $cookies.getObject('usuarioAtual');
			}
		}
		
		function GoUserHome() {
			$location.path("/home/" + $rootScope.usuarioAtual.role);
		}
    }

})();