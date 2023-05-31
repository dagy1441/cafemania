package com.dagy.cafemania.shared.utilities.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtils {
    public static Pageable createPageableBasedOnPageAndSizeAndSorting(
            List<SortItem> sortList,
            Integer page,
            Integer size) {

        List<Sort.Order> orders = new ArrayList<>();

        if(sortList != null) {
            // iterate the SortList to see based on which attributes we are going to Order By the results.
            for(SortItem sortValue : sortList) {
                orders.add(new Sort.Order(sortValue.getDirection(), sortValue.getField()));
            }
        }

        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = 0;
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
