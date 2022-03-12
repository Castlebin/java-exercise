package test;

import java.util.Date;

import org.junit.Test;

import cn.hutool.core.date.DateUtil;

public class DateTest {

    @Test
    public void testDateArea() {
        // timestamp 是不带时区的
        Date _1970_01_01 = new Date(0);
        // 可以看到这里打印出来的是 1970.01.01 08:00 （中国时区，东8）
        System.out.println(_1970_01_01);

        Date date = DateUtil.parse("1970-01-01").toJdkDate();
        // 可以看到，这里打印出来的是负数，原因是 创建 Date 对象时，会以本地时区为基准
        System.out.println(date.getTime());
    }

}
