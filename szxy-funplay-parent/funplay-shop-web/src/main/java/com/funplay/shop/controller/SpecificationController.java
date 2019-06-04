package com.funplay.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.funplay.pojo.Specification;
import com.funplay.pojogroup.SpecificationGroup;
import com.funplay.sellergoods.service.SpecificationService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 规格controller
 * @author txzs
 *
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;
    /**返回所有规格
     * @return
     */
    @RequestMapping("/findAll")
    public List<Specification> findAll(){
        return specificationService.findAll();
    }

    /**
     * 查询+分页
     * @param specification
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Specification specification, int page, int rows  ){
        return specificationService.findPage(specification, page, rows);
    }

    /**
     * 增加
     * @param specificationGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationGroup specificationGroup){
        try {
            specificationService.add(specificationGroup);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public SpecificationGroup findOne(Long id){
        return specificationService.findOne(id);
    }

    /**
     * 修改
     * @param specificationGroup
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationGroup specificationGroup){
        try {
            specificationService.update(specificationGroup);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            specificationService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     *查询规格列表加入模板的新增中
     * @return
     */
    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }




}
