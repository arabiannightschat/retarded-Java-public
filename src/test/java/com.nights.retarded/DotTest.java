package com.nights.retarded;

import com.nights.retarded.common.utils.DateUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;

public class DotTest {

    @Test
    public void sss (){
        System.out.println("Hello World");
    }

    @Test
    public void jj() {
        System.out.println(DateUtils.addMonth(DateUtils.monthFirstDay(new Date()),1));
    }
}
