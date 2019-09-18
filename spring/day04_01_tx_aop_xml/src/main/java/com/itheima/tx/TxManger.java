package com.itheima.tx;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


//管理事务
@Component
public class TxManger {

    private ThreadLocal<Connection> th = new ThreadLocal<>();

    @Autowired
    private DataSource dataSource;

    //获取连接
    public Connection getConnection() {
        Connection connection = th.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                th.set(connection);
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //开启
    public void begin() {
        try {
            //关闭自动提交,相当于开启了事务
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //提交
    public void commit() {
        try {
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //回滚
    public void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭
    public void close() {
        try {
            getConnection().close();
            //要从集合中移除连接对象
            th.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //管理事务
    public void manageTx(ProceedingJoinPoint pjp){
        try {
            //前置通知 before
            getConnection().setAutoCommit(false);
            //代码
            pjp.proceed();
            //后置通知 after-returning
            getConnection().commit();
        }catch (Throwable e){
            //异常通知  after-throwing
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            //最终通知  after
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
