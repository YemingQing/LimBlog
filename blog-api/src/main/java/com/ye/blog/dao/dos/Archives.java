package com.ye.blog.dao.dos;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

@Data
public class Archives {
    private Integer year;

    private Integer month;

    private Long count;
}
