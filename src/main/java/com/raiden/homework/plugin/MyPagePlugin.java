package com.raiden.homework.plugin;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/5/9
 */
@Intercepts({@Signature(type = Executor.class,method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyPagePlugin implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {

        RowBounds rowBounds= (RowBounds) invocation.getArgs()[2];
        if(rowBounds!=null&&rowBounds!=RowBounds.DEFAULT){
            Object[] args=invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            BoundSql boundSql = ms.getBoundSql(args[1]);
            if(!boundSql.getSql().toLowerCase().contains("limit")){
                String limit = String.format("limit %d,%d", rowBounds.getOffset(), rowBounds.getLimit());
                String sql = boundSql.getSql() + " " + limit;
                SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, boundSql.getParameterMappings());
                Field field = MappedStatement.class.getDeclaredField("sqlSource");
                field.setAccessible(true);
                field.set(ms, sqlSource);
            }
        }
        Long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        System.out.println("耗时:" + (System.currentTimeMillis() - startTime) + "ms");
        return result;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
//        return null;
    }

    public void setProperties(Properties properties) {

    }
}
