package com.itheima.dao;

import com.itheima.domain.Account;

public interface AccountDao {

    //查询,根据账户名
    Account findByName(String name);

    //更新
    void update(Account account);
}
