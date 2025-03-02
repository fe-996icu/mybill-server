package com.icu.mybill;

import com.icu.mybill.enums.BillType;
import com.icu.mybill.util.EnumUtils;
import org.junit.jupiter.api.Test;

public class EnumUtilTest {
    @Test
    public void testEnumUtil(){
        BillType billType = EnumUtils.fromValue("2", BillType.class);

        System.out.println(billType.getDesc());
        System.out.println(billType.getValue());
    }
}
