package com.freight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    /**
     * 请求对象
     */
    private String rSub;

    /**
     * 策略对象
     */
    private String pSub;
}
