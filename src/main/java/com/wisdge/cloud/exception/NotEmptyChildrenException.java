package com.wisdge.cloud.exception;

public class NotEmptyChildrenException extends Exception {

    public NotEmptyChildrenException() {
        super("不能删除包含子节点的记录");
    }

    public NotEmptyChildrenException(String message) {
        super(message);
    }
}
