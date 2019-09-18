package com.itheima.dao.impl;

import com.itheima.dao.AccountDao;
import com.itheima.domain.Account;
import com.itheima.tx.TxManger;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private QueryRunner queryRunner;

    @Autowired
    private TxManger txManger;

    @Override
    public Account findByName(String name) {
        try {
            return queryRunner.query(txManger.getConnection(), "select * from account1 where name = ?",
                    new BeanHandler<>(Account.class), name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Account account) {
        try {
            System.out.println(txManger.getConnection());
            queryRunner.update(txManger.getConnection(), "update account1 set balance = ? where name = ?",
                    account.getBalance(), account.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
