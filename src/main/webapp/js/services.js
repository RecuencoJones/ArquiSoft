angular.module('starter')

    .factory('auth', function () {
        var _identity = undefined,
            _authenticated = false;

        return {
            isAuthenticated: function () {
                if (_authenticated) {
                    //console.log('user already authenticated');
                    return _authenticated;
                } else {
                    //console.log('attempting to restore session');
                    var tmp = angular.fromJson(localStorage.userIdentity);
                    //console.log(tmp);
                    if (tmp !== undefined) {
                        //console.log('session restored');
                        this.authenticate(tmp);
                        return _authenticated;
                    }else{
                        //console.log('session unavailable');
                        return false;                        
                    }
                }
            },
            authenticate: function (identity) {
                _identity = identity;
                _authenticated = identity != null && identity != undefined;
                localStorage.userIdentity = angular.toJson(_identity);
            },
            identity: function () {
                return _identity;
            },
            addGroup: function(id) {
                _identity.groupsIds.push(id);
                localStorage.userIdentity = angular.toJson(_identity);
            }
        };
    })

    .factory('sidemenu', function ($location) {
        var hidden = true;

        return {
            isActive: function (viewLocation) {
                return viewLocation === $location.path();
            },
            isHidden: function () {
                return hidden;
            },
            toggleMenu: function () {
                hidden = !hidden;
            },
            hide: function () {
                hidden = true;
            }
        };

    });