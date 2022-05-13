package priv.db.querybuilder.logicnode;

/**
 * Subclass of SqlPredicateNode that repersents BETWEEN logic.
 */
public class BetweenNode extends SqlPredicateNode{

    String key;

    public BetweenNode(String key, String value1, String value2){
        if (key == null || value1 == null || value2 == null)
            throw new NullPointerException("COMPARISON node cannot revceive null values.");
        this.key = key;
        this.leftVal = value1;
        this.rightVal = value2;
    }

    @Override
    public String buildPredicate(){
        StringBuilder builder = new StringBuilder();
        builder
            .append(this.key)
            .append(" BETWEEN ")
            .append(this.leftVal)
            .append(" AND ")
            .append(this.rightVal);
        return builder.toString();
    }
}
