package com.nights.retarded;

import com.nights.retarded.utils.DateUtils;
import org.junit.Test;

import java.util.Date;

public class DotTest {

    @Test
    public void sss (){
        System.out.println("Hello World");
    }

    @Test
    public void jj() {
        System.out.println(DateUtils.addMonth(DateUtils.monthBegin(new Date()),1));
    }
}
