package com.app.hibernate.production.glocal;

public class GlobalParam {
	public static String getTemplateName(int index) {
		String name = "";
		switch (index) {
		case 1:// beanName
			name = "vo.ftl";
			break;
		case 2:// op action name
			name = "po.ftl";
			break;
		case 3:// dao name
			name = "controller.ftl";
			break;
		case 4:// service name
			name = "service.ftl";
			break;
		case 5:// sql name
			name = "serviceimpl.ftl";
			break;

		default:
			break;
		}
		return name;
	}
}
