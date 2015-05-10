package myusick.controller.services;

import com.google.gson.Gson;
import myusick.controller.MyusickService;

/**
 * Created by david on 01/05/2015.
 */
public class FollowService {
    public static String follow(int seguidor, int seguido) {
        boolean res = new MyusickService().follow(seguidor,seguido);
        return "{\"res\": "+res+"}";
    }

    public static String unfollow(int seguidor, int seguido) {
        boolean res = new MyusickService().unfollow(seguidor, seguido);
        return "{\"res\": "+res+"}";
    }

    public static String isFollowing(int seguidor, int seguido) {
        boolean res = new MyusickService().isfollow(seguidor, seguido);
        return "{\"res\": "+res+"}";
    }

    //TODO
    public static String getFollowers(int userid) {
        Gson gson = new Gson();
        new MyusickService().getFollowers(userid);
        return null;
    }

    //TODO
    public static String getFollowing(int userid) {
        Gson gson = new Gson();
        new MyusickService().getFollowing(userid);
        return null;
    }
}
