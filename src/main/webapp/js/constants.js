angular.module('starter')

    .constant("API", {
        "URL": "http://"+window.location.host+"/myusick/api",
        "AUTH_ENDPOINT": "/auth",
        "REGISTER_ENDPOINT": "/register",
        "PROFILE_ENDPOINT": "/profile/",
        "CREATE_BAND_ENDPOINT": "/newgroup"
    })
