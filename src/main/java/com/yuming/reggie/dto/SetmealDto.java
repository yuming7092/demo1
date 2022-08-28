package com.yuming.reggie.dto;

import com.yuming.reggie.entity.Setmeal;
import com.yuming.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;//套餐关联菜品列表

    private String categoryName;//套餐分类名称
}
