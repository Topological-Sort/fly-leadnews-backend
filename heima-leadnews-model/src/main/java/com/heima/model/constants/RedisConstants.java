package com.heima.model.constants;

public class RedisConstants {
    public static final String ARTICLE_LIKES = "article_likes_";  // 文章点赞集合key
    public static final String ARTICLE_UNLIKES = "article_unlikes_";  // 文章不喜欢集合key
    public static final String ARTICLE_COLLECTIONS = "article_collections_"; // 文章收藏集合key
    public static final String ARTICLE_VIEWS = "article_views_";  // 文章阅读集合key
    public static final String COMMENT_LIKES = "comment_likes_";  // 评论点赞集合key

    public static final String USER_FOLLOWS = "user_follows_";  // 用户关注z-set集合key
    public static final String USER_FANS = "user_fans_";        // 用户粉丝z-set集合key

    public static final String DYNAMIC_LIKES = "dynamic_likes_";  // 动态点赞集合key
}
