package com.athjx.commonutils.entity.mpWrapper;


import com.athjx.commonutils.CollectionUtils;
import com.athjx.commonutils.ValidException;
import com.athjx.commonutils.entity.CommonBaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 查询父类
 *
 * @author 刘朋
 * <br/>date 2020-05-14
 */
@Slf4j
@Data
public class BaseQueryWrapper<T extends CommonBaseEntity> {

    /**
     * 查询实体
     */
    @ApiModelProperty("查询实体")
    private T entity;

    @ApiModelProperty("模糊查询字段名")
    private List<String> likeColumn;

    @ApiModelProperty("排序条件（正序）")
    private String orderByAsc;

    @ApiModelProperty("排序条件（倒序）")
    private String orderByDesc;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty("每页条数")
    private long size ;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页数")
    private long current;


    public QueryWrapper<T> getQueryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(orderByAsc) && Objects.nonNull(orderByDesc)) {
            ValidException.throwValidException("排序条件不能同时传两个");
        } else if (Objects.nonNull(orderByAsc)) {
            queryWrapper.orderByAsc(orderByAsc);
        } else if (Objects.nonNull(orderByDesc)) {
            queryWrapper.orderByDesc(orderByDesc);
        }

        //设置模糊查询字段
        initLikeColumn(queryWrapper);

        //设置查询实体
        queryWrapper.setEntity(entity);

        return queryWrapper;
    }

    /**
     * 设置模糊查询字段
     *
     * @param queryWrapper
     * @return
     * @author 刘朋
     * <br/>date 2020-05-15
     */
    private void initLikeColumn(QueryWrapper<T> queryWrapper) {
        if (CollectionUtils.isNotEmpty(likeColumn)) {
            for (String column : likeColumn) {
                Class<? extends CommonBaseEntity> TClass = this.entity.getClass();
                Method method;
                try {
                    //将需要模糊查询的字段内容拿出来
                    column = column.substring(0, 1).toUpperCase() + column.substring(1);
                    method = TClass.getMethod("get" + column);
                    Object value = method.invoke(this.entity);
                    if (Objects.nonNull(value)) {
                        //字符串驼峰转下划线格式
                        queryWrapper.like(StringUtils.camelToUnderline(column), value);
                    }

                    //将需要模糊查询的字段在entity中置空
                    method = TClass.getMethod("set" + column, String.class);
                    String n = null;
                    method.invoke(this.entity, n);
                } catch (Exception e) {
                    log.error("", e);
                    throw new ValidException("模糊查询字段名错误");
                }
            }
        }
    }


    public Page<T> getPage() {
        Page<T> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        return page;
    }

}
