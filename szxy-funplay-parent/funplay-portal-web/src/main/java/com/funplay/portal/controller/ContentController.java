package com.funplay.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.content.service.ContentService;
import com.funplay.pojo.Content;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Reference
    private ContentService contentService;

    /**
     * 根据广告分类 ID 查询广告列表
     * @param categoryid
     * @return
     */
    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryid) {
        return contentService.findByCategoryId(categoryid);
    }

}
