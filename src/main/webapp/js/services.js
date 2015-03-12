angular.module('starter')

    .factory('auth', function() {
        var _identity = undefined,
            _authenticated = false;

        return {
            isAuthenticated: function() {
                return _authenticated;
            },
            authenticate: function(identity) {
                _identity = identity;
                _authenticated = identity != null && identity != undefined;
            },
            identity: function() {
                return _identity;
            }
        };
    });