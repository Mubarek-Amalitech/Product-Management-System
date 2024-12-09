package com.amalitech.productmanagementsystem.Enums;

import lombok.Getter;

@Getter
public enum SortTypeEnums {
    HEAP_SORT,
    MERGE_SORT,
    RADIX_SORT,
    BUCKET_SORT,
    QUICK_SORT;

    public  static SortTypeEnums convertFromString(String sortType){
        for (SortTypeEnums typeEnums:SortTypeEnums.values()){

             if ( sortType.equalsIgnoreCase(String.valueOf(typeEnums))) {
                  return  typeEnums;
             }
        }
          throw new IllegalArgumentException("type not matched");
    }
}

