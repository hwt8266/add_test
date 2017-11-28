/**
 * 
 */
package com.siemens.ct.pes.powerload.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.siemens.ct.pes.powerload.common.entities.IDDTO;

/**
 * Utilities for power load system application
 * 
 * @author Hao Liu
 *
 */
public class Utilities {

    /**
     * Build an integer id list
     * 
     * @param lst
     * @return
     */
    public static List<Integer> buildIDList(final List<IDDTO> lst) {
        List<Integer> ids = new ArrayList<Integer>();

        for (IDDTO id : lst) {
            ids.add(id.getId());
        }

        return ids;
    }

    /**
     * Get index of day(96)
     * 
     * @param tv
     * @return
     */
    public static int getIndexOfDay(final long tv) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tv * 1000);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        c.set(year, month, day, 0, 0, 0);

        long dayStart = c.getTimeInMillis();

        return (int) (tv - dayStart / 1000) / 900;
    }

    /**
     * Get total data of list
     * 
     * @param lst
     * @return
     */
    public static BigDecimal addAll(final List<BigDecimal> lst) {
        BigDecimal total = new BigDecimal(0);

        for (BigDecimal b : lst) {
            total = total.add(b);
        }

        return total;
    }
}