package com.icu.mybill;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.json.JSONArray;
import com.icu.mybill.pojo.AccountType;
import com.icu.mybill.util.TreeDataHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListToTreeTest {

    private static final List<AccountType> list = new ArrayList<>();

    @BeforeAll
    static void init(){
        list.add(new AccountType(15L, "负债账户", "1", 7, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(13L, "信用账户", "2", 6, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(11L, "债权账户", "3", 5, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(8L, "投资账户", "", 4, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(5L, "虚拟账户", "5", 3, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(3L, "储蓄账户", null, 2, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(7L, "微信钱包", null, 2, 5L, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(10L, "股票账户", null, 2, 8L, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(1L, "现金账户", null, 1, null, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(2L, "现金", null, 1, 1L, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(4L, "银行卡", null, 1, 3L, 1L, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(6L, "支付宝余额", null, 1, 5L, null, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(9L, "基金账户", null, 1, 8L, null, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(12L, "应收款项", null, 1, 11L, null, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(14L, "信用卡", "null", 1, 13L, null, LocalDateTime.now(), LocalDateTime.now()));
        list.add(new AccountType(16L, "应付款项", "null", 1, 15L, null, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void testListToTree() {
        List<Tree<Long>> treeList = TreeDataHelper.beanListToTreeList(list, true);

        // 转换为树形结构
        JSONArray jsonArray = new JSONArray(treeList, true);


        System.out.println(jsonArray.toJSONString(4));
    }

}
