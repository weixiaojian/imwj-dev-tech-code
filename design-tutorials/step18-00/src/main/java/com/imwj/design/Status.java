package com.imwj.design;

/**
 * 流程状态
 * @author wj
 * @create 2023-06-15 16:24
 */
public enum Status {

    // 1创建编辑、2待审核、3审核通过(任务扫描成活动中)、4审核拒绝(可以撤审到编辑状态)、5活动中、6活动关闭、7活动开启(任务扫描成活动中)
    Editing, Check, Pass, Refuse, Doing, Close, Open
}
