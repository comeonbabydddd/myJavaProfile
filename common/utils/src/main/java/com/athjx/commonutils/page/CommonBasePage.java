package com.athjx.commonutils.page;


import com.athjx.commonutils.BaseClassUtil;
import com.athjx.commonutils.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
@NoArgsConstructor
public class CommonBasePage {

    private Integer endIndex;

    @ApiModelProperty("排序 ASC/DESC")
    private String order;

    @ApiModelProperty("排序字段")
    private String orderBy;

    @ApiModelProperty("页数")
    private Integer page = 1;

    @ApiModelProperty("条数")
    private Integer pageSize = 20;

    private Pager pager = new Pager();

    private String q;

    private Integer startIndex;

    public Pager getPager() {

        this.pager.setPageId(this.getPage());
        this.pager.setPageSize(this.getPageSize());
        String orderField = "";
        if (this.orderBy != null && this.orderBy.trim()
                .length() > 0) {
            orderField = this.orderBy;
        }

        if (orderField.trim()
                .length() > 0 && this.order != null && this.order.trim()
                .length() > 0) {
            orderField = orderField + " " + this.order;
        }

        this.pager.setOrderField(orderField);
        return this.pager;
    }

    public void setStartIndex(Integer startIndex) {

        if (Objects.isNull(page) || Objects.isNull(pageSize)) {
            this.startIndex = startIndex;
        } else {
            this.startIndex = (this.page - 1) * this.pageSize + 1;
        }
    }

    public void initConditionDefault() {
        this.likeCondition();
        this.orderByDefault();
    }

    /**
     * 模糊查询赋值，给查询比较符为like的查询内容添加 %
     *
     * @author 刘朋
     * <br/>date 2019-12-18
     */
    public void likeCondition() {
        Class<? extends CommonBasePage> pageClass = this.getClass();

        //获取所有字段
        List<Field> fieldList = BaseClassUtil.getFields(pageClass);
        for (Field field : fieldList) {
            Class type = field.getType();
            //如果不是字符串类型，则不做模糊查询
            if (!type.equals(String.class)) {
                continue;
            }
            //如果不是比较符则继续下一个
            String fieldName = field.getName();
            if (!fieldName.contains("Operator")) {
                continue;
            }
            try {
                //判断比较符是否为like
                Method getOperator =
                        pageClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                String operator = (String) getOperator.invoke(this);
                if (!"like".equals(operator)) {
                    continue;
                }
                //将查询条件加上 % 做模糊查询
                String name = fieldName.substring(0, fieldName.indexOf("Operator"));
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method getVale = pageClass.getMethod("get" + name);
                String value = (String) getVale.invoke(this);

                if (StringUtils.isBlank(value)) {
                    continue;
                }
                if (!value.startsWith("%")) {
                    value = "%" + value;
                }
                if (!value.endsWith("%")) {
                    value = value + "%";
                }
                Method setValue = pageClass.getMethod("set" + name, String.class);
                setValue.invoke(this, value);
            } catch (Exception e) {
                log.warn("模糊查询赋值出错", e);
            }
        }
    }

    /**
     * 设置默认排序条件
     *
     * @return
     * @author 刘朋
     * <br/>date 2020-03-02
     */
    public void orderByDefault() {
        Class<? extends CommonBasePage> pageClass = this.getClass();
        //获取所有字段
        List<Field> fieldList = BaseClassUtil.getFields(pageClass);
        for (Field field : fieldList) {
            String name = field.getName();
            if ("createdTime".equals(name) && StringUtils.isBlank(this.getOrderBy()) && StringUtils.isBlank(this.getOrder())) {
                this.setOrderBy("CREATED_TIME DESC");
            }
        }
    }




}
