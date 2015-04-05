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
                url: "/profile/:_userid",
                templateUrl: "templates/main/profile.html",
                controller: "ProfileCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('login');
                    }
                }
            })

            .state('group', {
                url: "/group/:_groupid",
                templateUrl: "templates/main/groupProfile.html",
                controller: "GroupProfileCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('login');
                    }
                }
            })
            
            .state('login', {
                url: "/login",
                templateUrl: "templates/main/login.html",
                controller: "LoginCtrl",
                onEnter: function($state,auth){
                    if(auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })
            
            .state('register', {
                url: "/register",
                templateUrl: "templates/main/register.html",
                controller: "RegisterCtrl",
                onEnter: function($state,auth){
                    if(auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })
        
            .state('newgroup', {
                url: "/newGroup",
                templateUrl: "templates/main/newGroup.html",
                controller: "NewGroupCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })

            .state('post', {
                url: "/post/:_postid",
                templateUrl: "templates/main/post.html",
                controller: "PostCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            });
        
        $urlRouterProvider.otherwise('login');
    });