package com.freight.interfaceBG;

import com.freight.common.R;
import com.freight.entity.Freight_booking_log;
import com.freight.entity.Freight_process_log;
import com.freight.entity.orderBody.orderBody;
import com.freight.entity.orderBody.trackBody;
import com.freight.entity.quoteBody.OrderBody;

public interface OrderInterface {
    public float rates(OrderBody FormData);
    public R orders(orderBody Form);
    public R tracking(trackBody body);
    //保存日志
    public void saveLog(Freight_booking_log log);
    //保存日志
    public void save_process_Log(Freight_process_log log);
}
