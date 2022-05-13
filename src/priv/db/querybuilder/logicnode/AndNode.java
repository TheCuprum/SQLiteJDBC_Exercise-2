package priv.db.querybuilder.logicnode;

/**
 * Subclass of SqlPredicateNode that repersents AND logic.
 */
public class AndNode extends SqlPredicateNode{
    SqlPredicateNode leftVal;
    SqlPredicateNode rightVal;

    public AndNode(SqlPredicateNode value1, SqlPredicateNode value2){
        if (value1 == null || value2 == null)
            throw new NullPointerException("AND node cannot revceive null values.");
        this.leftVal = value1;
        this.rightVal = value2;
    }

    @Override
    public String buildPredicate(){
        StringBuilder builder = new StringBuilder();
        if (this.leftVal instanceof OrNode){
            builder.append('(').append(this.leftVal.buildPredicate()).append(')');
        }else{
            builder.append(this.leftVal.buildPredicate());
        }
        builder.append(" AND ");
        if (this.rightVal instanceof OrNode){
            builder.append('(').append(this.rightVal.buildPredicate()).append(')');
        }else{
            builder.append(this.rightVal.buildPredicate());
        }

        return builder.toString();
    }

}
