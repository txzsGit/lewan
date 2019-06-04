package com.funplay.page.service;

public interface ItemPageService {
    /**
     * 生成商品详细页
     * @param goodId
     * @return
     */
    public boolean genItemHtml(Long goodId);

    /**
     * 删除商品详细页
     * @param ids
     * @return
     */
    public boolean delItemHtml(Long[] ids);
}
