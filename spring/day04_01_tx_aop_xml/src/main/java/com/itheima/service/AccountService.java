package com.itheima.service;

public interface AccountService {

    /**
     * 转账
     *
     * @param sourceAccountName 原账户
     * @param targetAccountName 目标账户
     * @param amount 转账金额
     */
    void transfer(String sourceAccountName, String targetAccountName, Float amount);
}
