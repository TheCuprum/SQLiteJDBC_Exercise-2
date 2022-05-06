package priv.db.querybuilder.logicnode;

public abstract class SqlPredicateNode {
    protected String leftVal = null;
    protected String rightVal = null;
    
    public abstract String buildPredicate();

    public static void main(String[] args){
        AndNode node1 = new AndNode(
            new ComparisonNode("form1.key1", ComparisonOperator.GREATER_EQUAL, "form2.key2"),
            new OrNode(
                new BetweenNode("form2.key3","1", "5"),
                new LikeNode("form1.somkey","val%"))
            );
        System.out.println(node1.buildPredicate());
    }
}
