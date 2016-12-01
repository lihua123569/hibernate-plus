package com.app.master.vo;

import java.util.Date;
import com.app.master.entity.AutoPrimaryKey;

/**
 * ${description}
 * @time    ${date}
 */
public class V${domainName} extends AutoPrimaryKey{
/**
 *
 */
    private static final long serialVersionUID = ${serialVId}L;

<#list columns as item>
    <#if item.name != pkName>
        <#if item_index == 1 >
    // ${item.columnComment}
    private ${item.type} ${item.name};
        </#if>
        <#if item_index != 1 >
    // ${item.columnComment}
    private ${item.type} ${item.name};
        </#if>
    </#if>
</#list>

<#list columns as item>
    <#if item.name != pkName>
        <#if item_index == 1 >
    public ${item.type} get${item.name?cap_first}() {
        return ${item.name};
    }

    public void set${item.name?cap_first}(${item.type} ${item.name}) {
        this.${item.name} = ${item.name};
    }
        </#if>
        <#if item_index != 1 >
    public ${item.type} get${item.name?cap_first}() {
        return ${item.name};
    }

    public void set${item.name?cap_first}(${item.type} ${item.name}) {
        this.${item.name} = ${item.name};
    }
        </#if>
    </#if>
</#list>
}