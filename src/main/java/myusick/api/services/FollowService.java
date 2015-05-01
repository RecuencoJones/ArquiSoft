package myusick.api.services;

import myusick.model.services.MyusickService;

/**
 * Created by david on 01/05/2015.
 */
public class FollowService {
    public static String follow(int seguidor, int seguido) {
        boolean res = new MyusickService().follow(seguidor,seguido);
        return "{\"res\": \""+res+"\"}";
    }

    public static String unfollow(int seguidor, int seguido) {
        boolean res = new MyusickService().unfollow(seguidor, seguido);
        return "{\"res\": \""+res+"\"}";
    }

    public static String isFollowing(int seguidor, int seguido) {
        boolean res = new MyusickService().isfollow(seguidor, seguido);
        return "{\"res\": \""+res+"\"}";
    }
}
