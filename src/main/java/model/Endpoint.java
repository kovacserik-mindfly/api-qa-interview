package model;

public enum Endpoint {

    TEMP("temp"),
    CONDITION("condition");

    private String path;

    Endpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
