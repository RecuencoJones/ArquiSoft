angular.module('starter', ['ui.router'])

    .config(function($stateProvider, $urlRouterProvider){
        $stateProvider

            .state('starter', {
                url: "/",
                templateUrl: "index.html"
            })

            .state('home', {
                url: "/home",
                templateUrl: "templates/main/home.html",
                controller: "MainCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('login');
                    }
                }
            })
            
            .state('profile', {
                url: "/profile",
                templateUrl: "templates/main/profile.html",
                controller: "ProfileCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('login');
                    }
                }
            })
            
            .state('login', {
                url: "/login",
                templateUrl: "templates/main/login.html",
                controller: "LoginCtrl"
            })
            
            .state('register', {
                url: "/register",
                templateUrl: "templates/main/register.html",
                controller: "RegisterCtrl"
            })

            .state('about', {
                url: "/about",
                templateUrl: "templates/main/about.html",
                controller: "AboutCtrl"
            });

        $urlRouterProvider.otherwise('login');
    });