package priv.db.querybuilder.logicnode;

public class OrNode extends SqlPredicateNode{
    SqlPredicateNode leftVal;
    SqlPredicateNode rightVal;

    public OrNode(SqlPredicateNode value1, SqlPredicateNode value2){
        if (value1 == null || value2 == null)
            throw new NullPointerException("OR node cannot revceive null values.");
        this.leftVal = value1;
        this.rightVal = value2;
    }

    @Override
    public String buildPredicate(){
        StringBuilder builder = new StringBuilder();
            builder
            .append(this.leftVal.buildPredicate())
            .append(" OR ")
            .append(this.rightVal.buildPredicate());

        return builder.toString();
    }
}
