public enum BroadcastsEnum {
    START("START"),
    UPDATE("UPDATE"),
    END("END"),
    WIN("WIN"),
    TERMINATE("TERMINATE"),
    FROM("FROM");

    private String tag;

    BroadcastsEnum(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
