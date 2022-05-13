package priv.db.querybuilder.logicnode;

/**
 * The class represents different types of comparison operator.
 */
public enum ComparisonOperator {
    EQUAL("="),
    NOT_EQUAL("<>"),
    LESSER("<"),
    LESSER_EQUAL("<="),
    GEATER(">"),
    GREATER_EQUAL(">=");

    private String toStringName;

    private ComparisonOperator(String name){
        this.toStringName = name;
    }

    public String toString(){
        return this.toStringName;
    }
}
