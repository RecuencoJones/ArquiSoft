angular.module('starter')

    .constant("API", {
        "URL": "http://"+window.location.host+"/myusick/api",
        "AUTH_ENDPOINT": "/auth",
        "REGISTER_ENDPOINT": "/register"
    })
