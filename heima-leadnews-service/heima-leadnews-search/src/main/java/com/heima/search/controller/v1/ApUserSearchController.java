package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;
import com.heima.model.search.pojos.ApUserSearch;
import com.heima.search.service.ApUserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * APP用户搜索信息表 前端控制器
 * </p>
 *
 * @author itheima
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/history")
public class ApUserSearchController{

    @Autowired
    ApUserSearchService apUserSearchService;

    @PostMapping("/load")
    public ResponseResult<List<ApUserSearch>> findUserSearch() {
        return apUserSearchService.findUserSearchHistory();
    }

    @PostMapping("/del")
    public ResponseResult<Void> deleteUserSearch(HistorySearchDto dto) {
        return apUserSearchService.delUserSearch(dto);
    }

}