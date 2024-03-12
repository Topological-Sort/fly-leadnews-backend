package com.heima.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.heima.apis.wemedia.IWemediaClient;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.HotArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.vos.HotArticleVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.constants.RedisConstants;
import com.heima.model.wemedia.pojos.WmChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HotArticleServiceImpl implements HotArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private IWemediaClient wemediaClient;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 计算热点文章
     */
    @Override
    public void computeHotArticle() {
        //1.查询前n天的文章数据
        LocalDateTime dateTime = LocalDateTime.now().minusDays(100000);
        List<ApArticle> apArticleList = apArticleMapper.getAll(dateTime);
        //2.计算文章的分值
        List<HotArticleVo> hotArticleVoList = computeHotArticle(apArticleList);
        //3.为每个频道缓存30条分值较高的文章
        cacheTagToRedis(hotArticleVoList);
    }

    /**
     * 为每个频道缓存30条分值较高的文章
     *
     * @param hotArticleVoList
     */
    private void cacheTagToRedis(List<HotArticleVo> hotArticleVoList) {
        //每个频道缓存30条分值较高的文章
        ResponseResult<List<WmChannel>> responseResult = wemediaClient.listChannels();
        if (responseResult.getCode().equals(200)) {
            List<WmChannel> wmChannels = responseResult.getData();
            if (wmChannels != null && !wmChannels.isEmpty()) { // 检索出每个频道的文章
                for (WmChannel wmChannel : wmChannels) {
                    System.out.println(wmChannel.getId());
                    // 给文章进行排序，取30条分值较高的文章存入redis
                    List<HotArticleVo> hotArticleVos = hotArticleVoList.stream()
                            .filter(x -> wmChannel.getId().equals(x.getChannelId()))  // 当前频道
                            .sorted((a, b) -> b.getScore() - a.getScore())  // 倒序
                            .limit(30)  // 前30个
                            .collect(Collectors.toList());
                    //key：频道id  value：30条分值较高的文章
                    System.out.println(hotArticleVoList);
                    System.out.println(JSONObject.toJSONString(hotArticleVos));
                    String Key = ArticleConstants.HOT_ARTICLE_FIRST_PAGE + wmChannel.getId();
                    stringRedisTemplate.opsForValue().set(Key, JSONObject.toJSONString(hotArticleVos));
//                    Set<String> st=  new HashSet<>();
                }

            }
        }
        // 设置【推荐】频道数据
        // 所有频道文章进行排序，取30条分值较高的文章存入redis
        List<HotArticleVo> hotArticleVos = hotArticleVoList.stream()
                .sorted((a, b) -> b.getScore() - a.getScore())
                .limit(30).collect(Collectors.toList());
        String Key = ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG;
        System.out.println(hotArticleVoList);
        System.out.println(JSONObject.toJSONString(hotArticleVos));
        stringRedisTemplate.opsForValue().set(Key, JSON.toJSONString(hotArticleVos));
    }

    /**
     * 排序并且缓存数据
     *
     * @param hotArticleVos
     * @param key
     */
    private void sortAndCache(List<HotArticleVo> hotArticleVos, String key) {
        hotArticleVos = hotArticleVos.stream()
                .sorted(Comparator.comparing(HotArticleVo::getScore).reversed())
                .collect(Collectors.toList());
        if (hotArticleVos.size() > 30) {
            hotArticleVos = hotArticleVos.subList(0, 30);
        }
        cacheService.set(key, JSON.toJSONString(hotArticleVos));
    }

    /**
     * 计算文章分值
     *
     * @param apArticleList
     * @return
     */
    private List<HotArticleVo> computeHotArticle(List<ApArticle> apArticleList) {
        return apArticleList.stream()
                .map(article -> {
                    article.setCollection(stringRedisTemplate.opsForSet()
                            .size(RedisConstants.ARTICLE_COLLECTIONS + article.getId()).intValue());
                    article.setLikes(stringRedisTemplate.opsForZSet()
                            .size(RedisConstants.ARTICLE_LIKES + article.getId()).intValue());
                    article.setViews(stringRedisTemplate.opsForSet()
                            .size(RedisConstants.ARTICLE_VIEWS + article.getId()).intValue());
                    int score = computeScore(article);
                    HotArticleVo vo = BeanUtil.copyProperties(article, HotArticleVo.class);
                    vo.setScore(score);
                    return vo;
                }).collect(Collectors.toList());
    }

    /**
     * 计算文章的具体分值
     *
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        int score = 0;
        if (apArticle.getLikes() != null) {
            score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if (apArticle.getViews() != null) {
            score += apArticle.getViews() * ArticleConstants.HOT_ARTICLE_VIEW;
        }
        if (apArticle.getComment() != null) {
            score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if (apArticle.getCollection() != null) {
            score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }
        return score;
    }
}