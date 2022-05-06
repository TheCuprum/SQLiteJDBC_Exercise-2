package priv.db.querybuilder.logicnode;

public class ComparisonNode extends SqlPredicateNode{
    ComparisonOperator operator;

    public ComparisonNode(String value1, ComparisonOperator operator, String value2){
        if (operator == null || value1 == null || value2 == null)
            throw new NullPointerException("COMPARISON node cannot revceive null values.");
        this.operator = operator;
        this.leftVal = value1;
        this.rightVal = value2;
    }

    @Override
    public String buildPredicate(){
        StringBuilder builder = new StringBuilder();
        builder
            .append(this.leftVal)
            .append(' ')
            .append(this.operator.toString())
            .append(' ')
            .append(this.rightVal);
        return builder.toString();
    }
}
