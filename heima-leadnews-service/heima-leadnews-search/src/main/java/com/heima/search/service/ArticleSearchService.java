package com.heima.search.service;

import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.common.dtos.ResponseResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleSearchService {

    /**
     ES文章分页搜索
     @return
     */
    ResponseResult<List<Map>> search(UserSearchDto userSearchDto) throws IOException;
}