package priv.db.querybuilder.logicnode;

public class BetweenNode extends SqlPredicateNode{

    public BetweenNode(String value1, String value2){
        if (value1 == null || value2 == null)
            throw new NullPointerException("COMPARISON node cannot revceive null values.");
        this.leftVal = value1;
        this.rightVal = value2;
    }

    @Override
    public String buildPredicate(){
        StringBuilder builder = new StringBuilder();
        builder
            .append("BETWEEN ")
            .append(this.leftVal)
            .append(" AND ")
            .append(this.rightVal);
        return builder.toString();
    }
}
