package com.app.hibernate.production.factory;

import com.app.hibernate.production.domain.Table;
import com.app.hibernate.production.tools.AssistTools;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseFactory {

	public boolean create(Table table) throws Exception {

		String fileRoot = AssistTools.ifNull(table.getTemplateDirectory(), table.getTemplateDirectory());
		String fileName = AssistTools.ifNull(table.getTemplateName(), table.getTemplateName());
		String encoding = AssistTools.ifNull(table.getEncoding(), "UTF-8");

		File file = new File(table.getOutFilePath());
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		boolean state = true;
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setDefaultEncoding(encoding);
		File tempFile = null;
		if (fileRoot != null) {
			tempFile = new File(fileRoot);
		} else {
			tempFile = new File(BaseFactory.class.getResource("template").getPath());
		}
		try {
			cfg.setDirectoryForTemplateLoading(tempFile);
			Template t = cfg.getTemplate(fileName);
			Writer out = new OutputStreamWriter(new FileOutputStream(file), encoding);

			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<Object, Object>> list = table.getList();
			if (list == null || list.size() == 0) {
				state = false;
			} else {

				map.put("columns", list);
				map.put("packageName", table.getPackageName());
				map.put("domainName", table.getDomainName());
				map.put("pak", table.getPak());
				map.put("tableName", table.getTableName());
				map.put("pkName", table.getPkName());
				map.put("dbPkName", table.getDbPkName());
				map.put("description", table.getDescription());
				map.put("date", AssistTools.getCurrentDate());
				map.put("serialVId", String.valueOf(Math.abs(table.getSerialVId())));
				map.put("serialTId", String.valueOf(Math.abs(table.getSerialTId())));

				map.put("jl", "${");
				map.put("jld", "}");

				t.process(map, out);
			}
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			state = false;
		} catch (TemplateException e) {
			System.out.println(e.getLocalizedMessage());
			state = false;
		}
		String info = state ? "OK" : "Fail";
		System.out.println("Create file : " + table.getOutFilePath() + "[" + info + "]");
		return state;
	}
}
