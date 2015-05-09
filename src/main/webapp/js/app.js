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

            .state('profile_edit', {
                url: "/profile/:_userid/edit",
                templateUrl: "templates/main/profileEdit.html",
                controller: "ProfileEditCtrl",
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

            .state('group_edit', {
                url: "/group/:_groupid/edit",
                templateUrl: "templates/main/groupProfileEdit.html",
                controller: "GroupProfileEditCtrl",
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
            })

            .state('followers', {
                url: "/followers/:_userid",
                templateUrl: "templates/main/followers.html",
                controller: "FollowersCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })

            .state('following', {
                url: "/following/:_userid",
                templateUrl: "templates/main/followers.html",
                controller: "FollowingCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })

            .state('search', {
                url: "/search/:_term",
                templateUrl: "templates/main/search.html",
                controller: "SearchCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })

            .state('settings', {
                url: "/settings",
                templateUrl: "templates/main/settings.html",
                controller: "SettingsCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            })
        
            .state('error', {
                url: "/error",
                templateUrl: "templates/main/error.html",
                controller: "ErrorCtrl",
                onEnter: function($state,auth){
                    if(!auth.isAuthenticated()){
                        $state.go('home');
                    }
                }
            });
        
        $urlRouterProvider.otherwise('login');
    });