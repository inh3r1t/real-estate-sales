package com.zx.business.enums;

public enum PhotoType {

    MAIN_PAGE_TOP(0, "首页顶部推荐位照片"),
    THUMBNAIL(1, "缩略图"),
    DETAIL_TOP(2, "详情顶部照片"),
    DETAIL_CONTENT(3, "详情内容照片"),
    ARRIVE_CERTIFY(4, "到访照片"),
    SUBSCRIBE_USE(5, "认购合同照片");

    PhotoType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;
    private String description;

    public int code() {
        return code;
    }

    public String description() {
        return description;
    }
}
