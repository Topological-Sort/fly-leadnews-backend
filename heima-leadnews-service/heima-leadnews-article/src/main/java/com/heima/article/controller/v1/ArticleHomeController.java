package com.heima.article.controller.v1;


import com.heima.article.service.IApArticleService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.heima.common.constants.ArticleConstants.LOADTYPE_LOAD_MORE;
import static com.heima.common.constants.ArticleConstants.LOADTYPE_LOAD_NEW;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    @Autowired
    private IApArticleService apArticleService;

    @PostMapping("/load")
    public ResponseResult<List<ApArticle>> load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(LOADTYPE_LOAD_MORE,dto);
    }

    @PostMapping("/loadmore")
    public ResponseResult<List<ApArticle>> loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(LOADTYPE_LOAD_MORE,dto);
    }

    @PostMapping("/loadnew")
    public ResponseResult<List<ApArticle>> loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(LOADTYPE_LOAD_NEW,dto);
    }
}
