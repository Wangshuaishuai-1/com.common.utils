package com.common.utils.excel.style;

import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

/**
 * @author nanyanqing
 */
public abstract class ExcelStyle {
    /**
     * 获得自定义样式
     *
     * @return 自定义格式
     */
    public abstract HorizontalCellStyleStrategy getStyle();
}
