package com.bnymellon.txnflow.metadata.web.rest.util;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.List;

/**
 * Created by kotwal on 22-08-2018.
 */
public class SQLParser {

    public static void main(String[] args) throws ParseException, JSQLParserException {
        String sql2 = "SELECT my1.a, my2.b " +
            "FROM MY_TABLE1 my1, MY_TABLE1 my2, mdf m3" +
            " where my1.a = my2.b and" +
            " m3.a4 = my1.c and " +
            " my1.b = 7";
        String sql = "SELECT * " +
            "FROM MY_TABLE1 m1 " +
            " where m1.a = 5" ;

        Statement statement = CCJSqlParserUtil.parse(sql2);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        tableList.forEach(System.out::println);
        PlainSelect plainSelect = (PlainSelect)selectStatement.getSelectBody();
        Expression where = plainSelect.getWhere();
        String s = where.toString();
        System.out.println(s);
        FromItem fromItem = plainSelect.getFromItem();
        List<Join> joins = plainSelect.getJoins();
        First first = plainSelect.getFirst();
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        System.out.println();

    }

}
