package priv.db.querybuilder;

public enum JoinType {
    NATURAL("NATURAL JOIN"),
    CROSS("CROSS JOIN"),
    INNER("INNER JOIN"),
    LEFT_OUTER("LEFT OUTER JOIN"),
    RIGHT_OUTER("RIGHT OUTER JOIN"),
    FULL("FULL JOIN");

    private String toStringName;

    private JoinType(String name){
        this.toStringName = name;
    }

    @Override
    public String toString(){
        return this.toStringName;
    }
}
