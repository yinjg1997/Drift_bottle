package cn.edu.zust;

import java.sql.*;
import java.util.ArrayList;

/**
 * 辅助写 mapper 的工具
 * Created by Jianguo.Yin on 2021-07-22.
 */
public class BatisUtils {

    public static void getMybatisBoot(String catalog,String user, String password, String tableName) {
        try {
            ArrayList<String> baseColumns = getBaseColumn(catalog, user, password, tableName);
            System.out.println("=============================baseColumns===============================");
            System.out.println(baseColumns);
            propAuxer(baseColumns);
            updateAuxer(baseColumns);
            ifAuxer(baseColumns);
            foreachAuxer(baseColumns);
            getOnDuplicateKeyUpdate(baseColumns);
            resultsAuxer(baseColumns);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出以下形式字符串
     * #{id}, #{name}, #{intro}, #{isDeleted}, #{gmtCreate}, #{gmtModified},
     */
    private static void propAuxer(ArrayList<String> baseColumns) {
        System.out.println("=============================propAuxer===============================");
        String prefix = "#{";
        String suffix = "}, ";
        for (String column: baseColumns) {
            StringBuilder buf = new StringBuilder();
            String prop = toCamelCase(column);
            buf.append(prefix).append(prop).append(suffix);
            System.out.print(buf);
        }
        System.out.println();
    }

    /**
     * 根据base_column 生成下面的mybatis语句
     * id = #{id}, name = #{name}, intro = #{intro}, gmt_create = #{gmtCreate}, gmt_modified = #{gmtModified}
     */
    private static void updateAuxer(ArrayList<String> baseColumns) {
        System.out.println("===================================updateAuxer=========================");
        String prefix = "#{";
        String suffix = "}, ";
        StringBuilder buf = new StringBuilder();
        for (String column: baseColumns) {
            String prop = toCamelCase(column);
            buf.append(column).append(" = ").append(prefix).append(prop).append(suffix);
        }
        String str = buf.substring(0, buf.length() - 2);
        System.out.println(str);
    }

    /**
     * 根据base_column 生成下面的mybatis语句
     *             <if test="realName != null">
     *                 real_name = #{realName}
     *             </if>
     * @param baseColumns base_calumns
     */
    private static void ifAuxer(ArrayList<String> baseColumns) {
        System.out.println("===============================ifAuxer=============================");
        String prefix = "<if test=\"";
        String prefix2 = " != null\">\n    ";    // 对齐
        String midfix = " = #{";
        String midfix2 = "}";
        String suffix = "\n</if>";
        for (String column: baseColumns) {
            StringBuilder buf = new StringBuilder();
            String prop = toCamelCase(column);
            buf.append(prefix).append(prop).append(prefix2)
                    .append(column).append(midfix).append(prop).append(midfix2)
                    .append(suffix);
            System.out.println(buf);
        }
    }

    /**
     * 得到 #{item.property} 这个用于 <foreach> 中,
     * #{item.id}, #{item.name}, #{item.intro}, #{item.isDeleted}, #{item.gmtCreate}, #{item.gmtModified},
     */
    private static void foreachAuxer(ArrayList<String> baseColumns) {
        System.out.println("=============================foreachAuxer===============================");
        String prefix = "#{item.";
        String suffix = "}, ";
        for (String column: baseColumns) {
            StringBuilder buf = new StringBuilder();
            String prop = toCamelCase(column);
            buf.append(prefix).append(prop).append(suffix);
            System.out.print(buf);
        }
        System.out.println();
    }

    /**
     * id = values(id), name = values(name), gmt_create = values(gmt_create), gmt_modified = values(gmt_modified),
     */
    private static void getOnDuplicateKeyUpdate(ArrayList<String> baseColumns) {
        System.out.println("=============================getOnDuplicateKeyUpdate===============================");
        String midFix = " = values(";
        String suffix = "), ";
        for (String column: baseColumns) {
            StringBuilder buf = new StringBuilder();
            String prop = toCamelCase(column);
            buf.append(column).append(midFix).append(column).append(suffix);
            System.out.println(buf);
        }
    }

    /**
     * 根据base_column 生成下面的mybatis语句
     * 如:               @Result(property = "id", column = "id"),
     *                  @Result(property = "name", column = "name"),
     *                  @Result(property = "isDeleted", column = "is_deleted"),
     *                  @Result(property = "gmtCreate", column = "gmt_create"),
     *                  @Result(property = "gmtModified", column = "gmt_modified"),
     */
    private static void resultsAuxer(ArrayList<String> baseColumns) {
        System.out.println("===============================resultsAuxer=============================");
        String prefix = "@Result(property = \"";
        String midfix = "\", column = \"";
        String suffix = "\"), ";
        for (String column: baseColumns) {
            StringBuilder buf = new StringBuilder();
            String prop = toCamelCase(column);
            buf.append(prefix).append(prop).append(midfix).append(column).append(suffix);
            System.out.println(buf);
        }
    }






    /**
     * 他将返回指定表的 column
     * 如: str = "id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified"
     * @param catalog 数据库名
     */
    private static ArrayList<String> getBaseColumn(String catalog,String user, String password, String tableName) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + catalog + "?user="+user+"&password="+password);
        DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();

        ResultSet rs = meta.getColumns(null, "%", tableName, "%");
        ArrayList<String> list = new ArrayList<>();
        System.out.println(tableName);
        while (rs.next())  {
            String columnName = rs.getString("COLUMN_NAME");
            list.add(columnName);
        }
        return list;
    }

    /**
     * 下划线转驼峰
     * user_name  ---->  userName
     * userName   --->  userName
     *
     * @param underlineStr 带有下划线的字符串
     * @return 驼峰字符串
     */
    public static String toCamelCase(String underlineStr) {
        if (underlineStr == null) {
            return null;
        }
        // 分成数组
        char[] charArray = underlineStr.toCharArray();
        // 判断上次循环的字符是否是"_"
        boolean underlineBefore = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0, l = charArray.length; i < l; i++) {
            // 判断当前字符是否是"_",如果跳出本次循环
            if (charArray[i] == 95) {
                underlineBefore = true;
            } else if (underlineBefore) {
                // 如果为true，代表上次的字符是"_",当前字符需要转成大写
                buffer.append(charArray[i] -= 32);
                underlineBefore = false;
            } else {
                // 不是"_"后的字符就直接追加
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }
}
