angular.module('starter')

    .constant("API", {
        "URL": "http://"+window.location.host+"/myusick/api",
        "WS_URL": "http://"+window.location.host+"/myusick/api/ws/sub/",
        "AUTH_ENDPOINT": "/auth",
        "REGISTER_ENDPOINT": "/register",
        "PROFILE_ENDPOINT": "/profile/",
        "CREATE_BAND_ENDPOINT": "/newgroup",
        "TAG_ENDPOINT": "/newtag",
        "POST_ENDPOINT": "/post"
    });
