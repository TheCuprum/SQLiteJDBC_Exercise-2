package priv.db.querybuilder.logicnode;

/**
 * Subclass of SqlPredicateNode that repersents LIKE logic.
 */
public class LikeNode extends SqlPredicateNode{
    public LikeNode(String key, String pattern){
        if (pattern == null)
            throw new NullPointerException("LIKE node cannot revceive null values.");
        this.leftVal = key;
        this.rightVal = pattern;
    }

    @Override
    public String buildPredicate(){
        StringBuilder builder = new StringBuilder();
        builder
            .append(this.leftVal)
            .append(" LIKE ")
            .append('"')
            .append(this.rightVal)
            .append('"');

        return builder.toString();
    }
}
