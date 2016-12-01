package com.app.master.po;

import java.util.Date;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Entity;
import com.app.master.entity.AutoPrimaryKey;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "${tableName}")
@DynamicInsert(true)
@DynamicUpdate(true)
public class T${domainName} extends AutoPrimaryKey{
/**
 *
 */
    private static final long serialVersionUID = ${serialTId}L;

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
            <#if item.type != "Date">
    @Column(name = "${item.dbName}")
    public ${item.type} get${item.name?cap_first}() {
        return ${item.name};
    }

    public void set${item.name?cap_first}(${item.type} ${item.name}) {
        this.${item.name} = ${item.name};
    }
            </#if>
            <#if item.type == "Date">
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "${item.dbName}")
    public ${item.type} get${item.name?cap_first}() {
        return ${item.name};
    }

    public void set${item.name?cap_first}(${item.type} ${item.name}) {
        this.${item.name} = ${item.name};
    }
            </#if>
        </#if>
        <#if item_index != 1 >
            <#if item.type != "Date">
    @Column(name = "${item.dbName}")
    public ${item.type} get${item.name?cap_first}() {
        return ${item.name};
    }

    public void set${item.name?cap_first}(${item.type} ${item.name}) {
        this.${item.name} = ${item.name};
    }
            </#if>
            <#if item.type == "Date">
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "${item.dbName}")
    public ${item.type} get${item.name?cap_first}() {
        return ${item.name};
    }

    public void set${item.name?cap_first}(${item.type} ${item.name}) {
        this.${item.name} = ${item.name};
    }
            </#if>
        </#if>
    </#if>
</#list>
}