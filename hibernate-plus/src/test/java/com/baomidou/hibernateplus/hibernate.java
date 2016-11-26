package com.baomidou.hibernateplus;

import org.junit.Test;

import com.baomidou.hibernateplus.utils.EntityInfoUtils;

/**
 * Created by Caratacus on 2016/11/26 0026.
 */
public class hibernate {

        public static void main(String[] args) {
            EntityInfoUtils.initEntityInfo(TuserBind.class);
        }
}
