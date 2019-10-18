package com.nights.retarded.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SectionBuilderUtil {
	
	// 项目路径
	private static String path = SectionBuilderUtil.class.getResource("").getPath(); 
	
	static {
		path = path.substring(1, path.length()).replaceAll("/target/classes", "/src/main/java").replaceAll("common/utils/", "");
	}
	
	private static void newSection(String sectionName) {
		// 新建模块
		new File(path + sectionName + "/controller").mkdirs();
		new File(path + sectionName + "/dao/impl").mkdirs();
		new File(path + sectionName + "/model/enum").mkdirs();
		new File(path + sectionName + "/service/impl").mkdirs();
	}
	
	private static void newController(String sectionName,String tablesName,String firstUpper, String secondUpper) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(path + sectionName + "/controller/"+firstUpper+"Controller.java"));
		bw.write("package com.nights.retarded." + sectionName + ".controller;");
		bw.write("\n");bw.write("import javax.annotation.Resource;");
		bw.write("\n");bw.write("import javax.servlet.http.HttpServletRequest;");
		bw.write("\n\n");bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
		bw.write("\n");bw.write("import org.springframework.web.bind.annotation.RequestMethod;");
		bw.write("\n");bw.write("import org.springframework.web.bind.annotation.RestController;");
		bw.write("\n\n");bw.write("import com.nights.retarded.common.utils.JsonUtils;");
		bw.write("\n");bw.write("import com.nights.retarded." + sectionName + ".service." + firstUpper + "Service;");
		bw.write("\n\n");bw.write("import io.swagger.annotations.ApiOperation;");
		bw.write("\n\n");bw.write("@RestController");
		bw.write("\n");bw.write("@RequestMapping(\"api/" + sectionName + "\")");
		bw.write("\n");bw.write("public class " + firstUpper + "Controller {");
		bw.write("\n\n\t");bw.write("@Resource(name=\"" + secondUpper + "Service\")");
		bw.write("\n\t");bw.write("private " + firstUpper + "Service " + secondUpper + "Service;");
		bw.write("\n\n\t");bw.write("@ApiOperation(value=\"查询所有\")");
		bw.write("\n\t");bw.write("@RequestMapping(value = \"getAll\", method = RequestMethod.GET)");
		bw.write("\n\t");bw.write("public String getAll() {");
		bw.write("\n\t\t");bw.write("// HttpServletRequest request");
		bw.write("\n\t\t");bw.write("//String openId = JsonUtils.requestToOpenId(request);");
		bw.write("\n\t\t");bw.write("return JsonUtils.objectToJson(" + secondUpper + "Service.getAll());");
		bw.write("\n\t");bw.write("}");
		bw.write("\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/controller/"+firstUpper+"Controller.java");
		
	}

	private static void newService(String sectionName, String tablesName, String firstUpper, String secondUpper) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(path + sectionName + "/service/"+firstUpper+"Service.java"));
		bw.write("package com.nights.retarded." + sectionName + ".service;");
		bw.write("\n\n");bw.write("import java.util.List;");
		bw.write("\n\n");bw.write("import com.nights.retarded." + sectionName + ".model." + firstUpper + ";");
		bw.write("\n\n");bw.write("public interface " + firstUpper + "Service {");
		bw.write("\n\n\t");bw.write("public List<" + firstUpper + "> getAll();");
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/service/"+firstUpper+"Service.java");
		
		bw=new BufferedWriter(new FileWriter(path + sectionName + "/service/impl/"+firstUpper+"ServiceImpl.java"));
		bw.write("package com.nights.retarded." + sectionName + ".service.impl;");
		bw.write("\n\n");bw.write("import java.util.List;");
		bw.write("\n\n");bw.write("import javax.annotation.Resource;");
		bw.write("\n\n");bw.write("import org.springframework.stereotype.Service;");
		bw.write("\n");bw.write("import com.nights.retarded." + sectionName + ".model." + firstUpper + ";");
		bw.write("\n");bw.write("import com.nights.retarded." + sectionName + ".dao." + firstUpper + "Dao;");
		bw.write("\n");bw.write("import com.nights.retarded." + sectionName + ".service." + firstUpper + "Service;");
		bw.write("\n\n");bw.write("@Service(\"" + secondUpper + "Service\")");
		bw.write("\n");bw.write("public class " + firstUpper + "ServiceImpl implements " + firstUpper + "Service{");
		bw.write("\n\n\t");bw.write("@Resource(name = \"" + secondUpper + "Dao\")");
		bw.write("\n\t");bw.write("private " + firstUpper + "Dao " + secondUpper + "Dao;");
		bw.write("\n\n\t");bw.write("@Override");
		bw.write("\n\t");bw.write("public List<" + firstUpper + "> getAll() {");
		bw.write("\n\t\t");bw.write("return this." + secondUpper + "Dao.findAll();");
		bw.write("\n\t");bw.write("}");
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/service/impl/"+firstUpper+"ServiceImpl.java");
	}
	
	private static void newDao(String sectionName, String tablesName, String firstUpper, String secondUpper) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(path + sectionName + "/dao/"+firstUpper+"Dao.java"));
		bw.write("package com.nights.retarded." + sectionName + ".dao;");
		bw.write("\n\n");bw.write("import org.springframework.data.jpa.repository.JpaRepository;");
		bw.write("\n\n");bw.write("import com.nights.retarded." + sectionName + ".model." + firstUpper + ";");
		bw.write("\n\n");bw.write("public interface " + firstUpper + "Dao extends JpaRepository<" + firstUpper + ", String>{");
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/dao"+firstUpper+"Dao.java");
	}
	
	private static void newModel(String sectionName, String tablesName, String firstUpper, String secondUpper) throws Exception {
		// 从项目配置文件中读取数据库连接属性
		Properties prop = new Properties();
		String propertiesPath = new File("").getAbsolutePath() + "/src/main/resources/application.properties";
		System.out.println("[info] 从配置文件读取连接数据准备数据库连接 " + propertiesPath + " ...");
		prop.load (new FileInputStream(propertiesPath));
		String url = prop.getProperty("spring.datasource.url").toString();
		String username = prop.getProperty("spring.datasource.username").toString();
		String password = prop.getProperty("spring.datasource.password").toString();
		String driverClassName = prop.getProperty("spring.datasource.driverClassName").toString();
		// 连接数据库
		Class.forName(driverClassName);
		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println("[info] 获取到数据库连接：" + url + "，表：" + tablesName);
		// 查出表中字段
		List<Map<String,Object>> list = new ArrayList<>();
		PreparedStatement stmt = conn.prepareStatement("select * from " + tablesName);
		ResultSetMetaData rsmd = stmt.getMetaData();
		// 是否存在时间类型，辅助决定import
		boolean isExistDate = false;
		// 是否存在openId，辅助决定import
		boolean isExistOpenId = false;
		System.out.print("[info] ");
		for (int i = 0 ; i < rsmd.getColumnCount() ; i++) {
			Map<String,Object> temp = new HashMap<>();
			temp.put("columnName", rsmd.getColumnName(i + 1));
			if("open_id".equals(rsmd.getColumnName(i + 1))) {
				isExistOpenId = true;
			}
			temp.put("columnType", rsmd.getColumnType(i + 1));
			if(rsmd.getColumnType(i + 1) == 93) {
				isExistDate = true;
			}
			System.out.print(rsmd.getColumnName(i + 1) + " : " + rsmd.getColumnType(i + 1) + " : " + rsmd.getColumnTypeName(i + 1) + " ; ");
			temp.put("isPrimaryKey", false);
			list.add(temp);
		}
		System.out.print("\n");
		// 查出主键信息
		DatabaseMetaData db = conn.getMetaData();
		ResultSet rs = db.getPrimaryKeys(conn.getCatalog(), null, tablesName);
		while( rs.next()) {
			for (Map<String, Object> map : list) {
				if(map.get("columnName").equals(rs.getString("COLUMN_NAME"))) {
					map.put("isPrimaryKey", true);
				}
			}
		}
		
		conn.close();
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(path + sectionName + "/model/"+firstUpper+".java"));
		bw.write("package com.nights.retarded." + sectionName + ".model;");
		bw.write("\n\n");bw.write("import java.io.Serializable;");
		if(isExistDate) {
			bw.write("\n");bw.write("import java.util.Date;");
		}
		bw.write("\n\n");bw.write("import javax.persistence.Column;");
		bw.write("\n");bw.write("import javax.persistence.Entity;");
		bw.write("\n");bw.write("import javax.persistence.GeneratedValue;");
		bw.write("\n");bw.write("import javax.persistence.Id;");
		bw.write("\n");bw.write("import javax.persistence.Table;");
		if(isExistDate) {
			bw.write("\n");bw.write("import javax.persistence.Temporal;");
			bw.write("\n");bw.write("import javax.persistence.TemporalType;");
		}
		bw.write("\n\n");bw.write("import org.hibernate.annotations.GenericGenerator;");
		if(isExistDate) {
			bw.write("\n\n");bw.write("import com.fasterxml.jackson.annotation.JsonFormat;");
		}
		if(isExistOpenId) {
			bw.write("\n");bw.write("import com.fasterxml.jackson.annotation.JsonIgnore;");
		}
		bw.write("\n\n");bw.write("@Entity(name = \"" + firstUpper + "\")");
		bw.write("\n");bw.write("@Table(name = \"" + tablesName + "\")");
		bw.write("\n");bw.write("public class " + firstUpper + " implements Serializable {");
		bw.write("\n\n\t");bw.write("private static final long serialVersionUID = 1L;");
		// 开始生成属性
		for (Map<String, Object> map : list) {
			// 对于主键的处理
			if((boolean)map.get("isPrimaryKey")) {
				if(map.get("columnName").equals("open_id")) {
					bw.write("\n\n\t");bw.write("@Id");
					bw.write("\n\t");bw.write("@JsonIgnore");
					bw.write("\n\t");bw.write("private String " + secondUpperUtil((String)map.get("columnName")) + ";");
				} else {
					bw.write("\n\n\t");bw.write("@Id");
					bw.write("\n\t");bw.write("@GeneratedValue(generator=\"system-uuid\")");
					bw.write("\n\t");bw.write("@GenericGenerator(name=\"system-uuid\", strategy = \"uuid\")");
					bw.write("\n\t");bw.write("private String " + secondUpperUtil((String)map.get("columnName")) + ";");
				}
			} else { // 非主键的处理
				bw.write("\n\n\t");bw.write("@Column(name=\"" + map.get("columnName") + "\")");
				if("open_id".equals((String)map.get("columnName"))) {
					bw.write("\n\t");bw.write("@JsonIgnore");
				}
				if((int)map.get("columnType") == 93) {
					bw.write("\n\t");bw.write("@Temporal(TemporalType.DATE)");
					bw.write("\n\t");bw.write("@JsonFormat(pattern = \"yyyy-MM-dd\")");
				}
				bw.write("\n\t");bw.write("private ");
				bw.write(type2String((int)map.get("columnType")) + " ");
				bw.write(secondUpperUtil((String)map.get("columnName")) + ";");
			}
		}
		// 开始生成GETSET方法
		for (Map<String, Object> map : list) {
			String first =  firstUpperUtil((String)map.get("columnName"));
			String second =  secondUpperUtil((String)map.get("columnName"));
			String type = type2String((int)map.get("columnType"));
			bw.write("\n\n\t");bw.write("public " + type + " get" + first + "() {");
			bw.write("\n\t\t");bw.write("return " + second + ";");
			bw.write("\n\t");bw.write("}");
			bw.write("\n\n\t");bw.write("public void set" + first + "(" + type + " " + second + ") {");
			bw.write("\n\t\t");bw.write("this." + second + " = " + second + ";");
			bw.write("\n\t");bw.write("}");
		}
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/model/"+firstUpper+".java");
		
	}
	
	// 类型转字符串 12 CARCHAR 4 INT 93 DATETIME 其他类型没用到没写适配
	private static String type2String(int type) {
		if(type == 12) { 
			return "String";
		} else if(type == 4) {
			return "Integer";
		} else if(type == 93) {
			return "Date";
		} else if(type == 8) {
			return "Double";
		} else {
			return "Object";
		}
	}
	
	// 下划线转驼峰命名
	public static String secondUpperUtil(String str) {
		String [] strs = str.split("_");
		String secondUpper = strs[0];
		for (int i = 1 ; i < strs.length ; i++) {
			secondUpper += strs[i].substring(0, 1).toUpperCase() +  strs[i].substring(1, strs[i].length());
		}
		return secondUpper;
	}
	
	// 下划线转首字母大写
	public static String firstUpperUtil(String str) {
		String [] strs = str.split("_");
		String firstUpper = "";
		for (int i = 0 ; i < strs.length ; i++) {
			firstUpper += strs[i].substring(0, 1).toUpperCase() +  strs[i].substring(1, strs[i].length());
		}
		return firstUpper;
	}
	
	// 生成对应表的 controller、service、dao、bean 结构
	private static void newTable(String sectionName,String tablesName) {
		
		// 首字母大写
		String firstUpper = firstUpperUtil(tablesName);
		String secondUpper = secondUpperUtil(tablesName);
		
		try {
			newController(sectionName,tablesName,firstUpper,secondUpper);
			newService(sectionName,tablesName,firstUpper,secondUpper);
			newDao(sectionName,tablesName,firstUpper,secondUpper);
			newModel(sectionName,tablesName,firstUpper,secondUpper);
			System.out.println("------------------------------------------------------------------");
			System.out.println("[info] 创建成功 " + firstUpper);
			System.out.println("------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void newTable(String sectionName,String tablesName,String className) {
		
		// 首字母大写
		String firstUpper = firstUpperUtil(className);
		String secondUpper = secondUpperUtil(className);
		
		try {
			newController(sectionName,tablesName,firstUpper,secondUpper);
			newService(sectionName,tablesName,firstUpper,secondUpper);
			newDao(sectionName,tablesName,firstUpper,secondUpper);
			newModel(sectionName,tablesName,firstUpper,secondUpper);
			System.out.println("------------------------------------------------------------------");
			System.out.println("[info] 创建成功 " + firstUpper);
			System.out.println("------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
		
		// 1.创建结构文件夹
		
//		newSection("statistics");
		
		// 2.第一个参数标识要放进的section，第二个参数标识数据库表名，第三个参数标识生成的bean名
		
//		newTable("statistics","statistics_crazy_daily","crazy_daily");
//		newTable("sys","sys_feedback","feedback");
		
		// 2.可以用这一种方式标识数据库表名=bean名
		
//		newTable("records","records_user");
		
//	}
	
	
}
