package com.app.master.console.controller;

import com.app.master.base.controller.ConsoleController;
import com.app.master.constant.GlobalConstant;
import com.app.master.model.Json;
import com.app.master.model.SessionInfo;
import com.app.master.vo.V${domainName};
import com.app.master.core.service.${domainName?cap_first}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.app.common.MapUtils;
import com.app.common.Logis;
import java.util.Map;


/**
 * ${description}
 * @time	${date}
 */
@Controller
@RequestMapping("/${domainName}")
public class ${domainName?cap_first}Controller extends ConsoleController {

    @Autowired
    private ${domainName?cap_first}Service ${domainName}Service;

    @RequestMapping("/manager")
    public String manager() {
        return "/${domainName}/${domainName}Index";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public void dataGrid(V${domainName} ${domainName}, PageFilter ph) {
        Grid grid = new Grid();
        try {
            Map map = MapUtils.beanToMapNotNull(${domainName});
            grid.setRows(${domainName}Service.query(ph.getPage(), ph.getRows(),map));
            grid.setTotal(${domainName}Service.count(map));
            this.printJson(grid);
        } catch (Exception e) {
            logger.error(Logis.fail(), e);
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public void get(Long id) {
        try {
            this.printJson(${domainName}Service.get(id));
        } catch (Exception e) {
            logger.error(Logis.fail(), e);
        }
    }

    @RequestMapping("/editPage")
    public String editPage(Long id) {
        try {
            V${domainName} r = ${domainName}Service.get(id);
            request.setAttribute("${domainName}", r);
        } catch (Exception e) {
            logger.error(Logis.fail(), e);
        }
        return "/${domainName}/${domainName}Update";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public void edit(V${domainName} ${domainName}) throws InterruptedException {
        Json json = new Json();
        try {
            ${domainName}Service.update(${domainName});
            json.setSuccess(true);
            json.setMsg("编辑成功！");
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            logger.error(Logis.fail(), e);
        }
        this.printJson(json);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(Long id) {
        Json json = new Json();
        try {
            ${domainName}Service.delete(id);
            json.setMsg("删除成功！");
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            logger.error(Logis.fail(), e);
        }
        this.printJson(json);
    }

    @RequestMapping("/addPage")
    public String addPage() {
        return "/${domainName}/${domainName}Add";
    }

    @RequestMapping("/add")
    @ResponseBody
    public void add(V${domainName} ${domainName}) {
        Json json = new Json();
        try {
            ${domainName}Service.save(${domainName});
            json.setSuccess(true);
            json.setMsg("添加成功！");
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            logger.error(Logis.fail(), e);
        }
        this.printJson(json);
    }

}
