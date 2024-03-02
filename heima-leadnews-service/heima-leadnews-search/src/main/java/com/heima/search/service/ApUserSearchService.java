package com.heima.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;
import com.heima.model.search.pojos.ApUserSearch;

import java.util.List;

public interface ApUserSearchService {

    public void insert(String keyword, Integer userId);

    public ResponseResult<List<ApUserSearch>> findUserSearchHistory();

    /**
     删除搜索历史
     @param historySearchDto
     @return
     */
    ResponseResult<Void> delUserSearch(HistorySearchDto historySearchDto);
}