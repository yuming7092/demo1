package com.yuming.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuming.reggie.common.CustomException;
import com.yuming.reggie.entity.Category;
import com.yuming.reggie.entity.Dish;
import com.yuming.reggie.entity.Setmeal;
import com.yuming.reggie.mapper.CategoryMapper;
import com.yuming.reggie.service.CategoryService;
import com.yuming.reggie.service.DishService;
import com.yuming.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        //添加查询条件，根据分类id进行查询菜品数据
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //如果已经关联，抛出一个业务异常
        if(count1 > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");//已经关联菜品，抛出一个业务异常
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");//已经关联套餐，抛出一个业务异常
        }

        //正常删除分类
        super.removeById(id);
    }

}

