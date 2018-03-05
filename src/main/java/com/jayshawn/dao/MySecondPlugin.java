package com.jayshawn.dao;

import static org.hamcrest.CoreMatchers.theInstance;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
	@Signature(type=StatementHandler.class,method="parameterize",args={java.sql.Statement.class})
	
})
public class MySecondPlugin implements Interceptor {

	@Override
	// MySecondPlugin.java
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("before MySecondPlugin");
		Object object = invocation.proceed();
		System.out.println("after MySecondPlugin");
		return object;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
