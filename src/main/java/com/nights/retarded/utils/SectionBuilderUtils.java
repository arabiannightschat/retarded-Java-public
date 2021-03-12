package com.nights.retarded.utils;

import java.io.*;
import java.sql.*;
import java.util.*;

public class SectionBuilderUtils {

	private static final String packageName = "com.nights.retarded";
	private static final String url = "jdbc:mysql://arabiannightschat.top:3306/retarded?useSSL=false";
	private static final String username = "root";
	private static final String password = "032459287886";
	private static final String driverClassName = "com.mysql.jdbc.Driver";

	// 项目路径
	private static String path = SectionBuilderUtils.class.getResource("").getPath();

	static {
		path = path.substring(1).replaceAll("/target/classes", "/src/main/java").replaceAll("common/utils/", "");
	}

	private static void newSection(String sectionName) {
		// 新建模块
		new File(path + sectionName + "/controller").mkdirs();
		new File(path + sectionName + "/dao").mkdirs();
		new File(path + sectionName + "/model/enum").mkdirs();
		new File(path + sectionName + "/service/impl").mkdirs();
	}

	private static void newController(String sectionName, String firstUpper, String secondUpper) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path + sectionName + "/controller/"+firstUpper+"Controller.java"));
		bw.write("package " + packageName + ".baseController;");
		bw.write("\n\n");bw.write("import " + packageName + ".base.baseController.BaseController;");
		bw.write("\n");bw.write("import " + packageName + ".base.baseController.Result;");
		bw.write("\n");bw.write("import org.springframework.beans.factory.annotation.Autowired;");
		bw.write("\n\n");bw.write("import org.springframework.web.bind.annotation.GetMapping;");
		bw.write("\n");bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
		bw.write("\n");bw.write("import org.springframework.web.bind.annotation.RestController;");
		bw.write("\n");bw.write("import " + packageName + ".service." + firstUpper + "Service;");
		bw.write("\n\n");bw.write("@RestController");
		bw.write("\n");bw.write("@RequestMapping(\"api/" + sectionName + "/" + secondUpper + "\")");
		bw.write("\n");bw.write("public class " + firstUpper + "Controller extends BaseController {");
		bw.write("\n\n\t");bw.write("@Autowired");
		bw.write("\n\t");bw.write("private " + firstUpper + "Service " + secondUpper + "Service;");
		bw.write("\n\t");bw.write("@GetMapping(value = \"getAll\")");
		bw.write("\n\t");bw.write("public Result getAll() {");
		bw.write("\n\t\t");bw.write("return Success(" + secondUpper + "Service.getAll());");
		bw.write("\n\t");bw.write("}");
		bw.write("\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/baseController/"+firstUpper+"Controller.java");

	}

	private static void newService(String sectionName, String firstUpper, String secondUpper) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path + sectionName + "/service/"+firstUpper+"Service.java"));
		bw.write("package " + packageName + ".service;");
		bw.write("\n\n");bw.write("import java.util.List;");
		bw.write("\n\n");bw.write("import " + packageName + ".model." + firstUpper + ";");
		bw.write("\n\n");bw.write("public interface " + firstUpper + "Service {");
		bw.write("\n\n\t");bw.write("List<" + firstUpper + "> getAll();");
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/service/"+firstUpper+"Service.java");

		bw = new BufferedWriter(new FileWriter(path + sectionName + "/service/impl/"+firstUpper+"ServiceImpl.java"));
		bw.write("package " + packageName + ".service.impl;");
		bw.write("\n\n");bw.write("import java.util.List;");
		bw.write("\n\n");bw.write("import org.springframework.beans.factory.annotation.Autowired;");
		bw.write("\n\n");bw.write("import org.springframework.stereotype.Service;");
		bw.write("\n");bw.write("import " + packageName + ".model." + firstUpper + ";");
		bw.write("\n");bw.write("import " + packageName + ".dao." + firstUpper + "JpaDao;");
		bw.write("\n");bw.write("import " + packageName + ".service." + firstUpper + "Service;");
		bw.write("\n\n");bw.write("@Service(\"" + secondUpper + "Service\")");
		bw.write("\n");bw.write("public class " + firstUpper + "ServiceImpl implements " + firstUpper + "Service{");
		bw.write("\n\n\t");bw.write("@Autowired");
		bw.write("\n\t");bw.write("private " + firstUpper + "JpaDao " + secondUpper + "JpaDao;");
		bw.write("\n\n\t");bw.write("@Override");
		bw.write("\n\t");bw.write("public List<" + firstUpper + "> getAll() {");
		bw.write("\n\t\t");bw.write("return " + secondUpper + "JpaDao.findAll();");
		bw.write("\n\t");bw.write("}");
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/service/impl/"+firstUpper+"ServiceImpl.java");
	}

	private static void newJpaDao(String sectionName, String firstUpper) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(path + sectionName + "/dao/"+firstUpper+"JpaDao.java"));
		bw.write("package " + packageName + ".dao;");
		bw.write("\n\n");bw.write("import org.springframework.data.jpa.repository.JpaRepository;");
		bw.write("\n\n");bw.write("import " + packageName + ".model." + firstUpper + ";");
		bw.write("\n\n");bw.write("public interface " + firstUpper + "JpaDao extends JpaRepository<" + firstUpper + ", String>{");
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/dao"+firstUpper+"JpaDao.java");
	}

	private static void newModel(String sectionName, String tablesName, String firstUpper) throws Exception {

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
		boolean isExistBigDecimal = false;
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
			if(rsmd.getColumnType(i + 1) == 3){
				isExistBigDecimal = true;
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

		BufferedWriter bw = new BufferedWriter(new FileWriter(path + sectionName + "/model/"+firstUpper+".java"));
		bw.write("package " + packageName + ".model;");
        bw.write("\n\n");
        if(isExistDate) {
            bw.write("import com.fasterxml.jackson.annotation.JsonFormat;");
        }
        if(isExistOpenId) {
            bw.write("import com.fasterxml.jackson.annotation.JsonIgnore;");
        }
        bw.write("\n");bw.write("import lombok.Data;");
        bw.write("\n\n");bw.write("import javax.persistence.Entity;");
        bw.write("\n");bw.write("import javax.persistence.GeneratedValue;");
        bw.write("\n");bw.write("import javax.persistence.Id;");
        bw.write("\n");bw.write("import javax.persistence.Table;");
        bw.write("\n");bw.write("import org.hibernate.annotations.GenericGenerator;");
        if(isExistDate) {
            bw.write("\n");bw.write("import java.util.Date;");
        }
        if(isExistBigDecimal){
            bw.write("\n");bw.write("import java.math.BigDecimal;");
        }
        bw.write("\n\n");bw.write("@Entity(name = \"" + firstUpper + "\")");
		bw.write("\n");bw.write("@Table(name = \"" + tablesName + "\")");
		bw.write("\n");bw.write("@Data");
		bw.write("\n");bw.write("public class " + firstUpper + " {");
		// 开始生成属性
		for (Map<String, Object> map : list) {
			bw.write("\n");
			// 对于主键的处理
			if((boolean)map.get("isPrimaryKey")) {
                if(map.get("columnName").equals("open_id")) {
                    bw.write("\n\n\t");bw.write("@Id");
                    bw.write("\n\t");bw.write("@JsonIgnore");
                    bw.write("\n\t");bw.write("private String " + secondUpperUtil((String)map.get("columnName")) + ";");
                }else {
                    bw.write("\n\n\t");bw.write("@Id");
                    bw.write("\n\t");bw.write("@GeneratedValue(generator=\"system-uuid\")" );
                    bw.write("\n\t");bw.write("@GenericGenerator(name=\"system-uuid\", strategy = \"uuid\")" );
                    bw.write("\n\t");bw.write("private String " + secondUpperUtil((String)map.get("columnName")) + ";");
                }
			} else { // 非主键的处理
                if("open_id".equals(map.get("columnName"))) {
                    bw.write("\n\t");bw.write("@JsonIgnore");
                }
				if((int)map.get("columnType") == 93) {
					bw.write("\n\t");bw.write("@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
				}
				bw.write("\n\t");bw.write("private ");
				bw.write(type2String((int)map.get("columnType")) + " ");
				bw.write(secondUpperUtil((String)map.get("columnName")) + ";");
			}
		}
		bw.write("\n\n");bw.write("}");
		bw.close();
		System.out.println("[info] 成功创建 " + path + sectionName + "/model/"+firstUpper+".java");

	}

	// 类型转字符串 12 VARCHAR 4 INT 93 DATETIME 其他类型没用到没写适配
	private static String type2String(int type) {
		if(type == 12) {
			return "String";
		} else if(type == 4) {
			return "Integer";
		} else if(type == 93) {
			return "Date";
		} else if(type == 8) {
			return "Double";
		} else if(type == 3) {
			return "BigDecimal";
		} else {
			return "Object";
		}
	}

	// 下划线转驼峰命名
	public static String secondUpperUtil(String str) {
		String [] strArray = str.split("_");
		String secondUpper = strArray[0];
		for (int i = 1 ; i < strArray.length ; i++) {
			secondUpper += strArray[i].substring(0, 1).toUpperCase() +  strArray[i].substring(1);
		}
		return secondUpper;
	}

	// 下划线转首字母大写
	public static String firstUpperUtil(String str) {
		String [] strArray = str.split("_");
		String firstUpper = "";
		for (int i = 0 ; i < strArray.length ; i++) {
			firstUpper += strArray[i].substring(0, 1).toUpperCase() +  strArray[i].substring(1);
		}
		return firstUpper;
	}

	private static void newTable(String sectionName, String tablesName, String firstUpper, String secondUpper) {
		try {
			newController(sectionName, firstUpper, secondUpper);
			newService(sectionName, firstUpper, secondUpper);
			newJpaDao(sectionName, firstUpper);
			newModel(sectionName, tablesName, firstUpper);
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

		newTable(sectionName, tablesName, firstUpper, secondUpper);
	}

	public static void main(String[] args) {

		// 创建结构文件夹

		newSection("xx");

		// section、数据库表名、实体名

		newTable("xx","sys_user","user");


	}


}
