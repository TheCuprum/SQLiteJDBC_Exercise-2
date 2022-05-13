package priv.db.querybuilder;

import java.util.ArrayList;

import priv.db.querybuilder.logicnode.SqlPredicateNode;

/**
 * The builder of SQL SELECT query.
 */
public class SelectQueryBuilder {
    private boolean distinct = false;
    private ArrayList<String> queryKeys;
    private ArrayList<String> queryForm;
    private ArrayList<JoinInfo> joinForm = null;
    private SqlPredicateNode predicate = null;
    private String groupKey = null;
    private String orderKey = null;
    private boolean orderAsc = true;

    /**
     * Parameterless constructor for SelectQueryBuilder.
     */
    public SelectQueryBuilder(){
        this.queryForm = new ArrayList<String>();
        this.queryKeys = new ArrayList<String>();
    }

    /**
     * Constructor for SelectQueryBuilder.
     * @param form The target form(which is behind "FROM").
     */
    public SelectQueryBuilder(String form){
        this();
        this.queryForm.add(form);
    }

    /**
     * Adds "DISTINCT" to this SELECT query.
     * @return Reference to this object. 
     */
    public SelectQueryBuilder distinct(){
        this.distinct = true;
        return this;
    }

    /**
     * Appends keys behind "SELECT (DISTINCT)" word.
     * @param keys The array of keys to add. 
     * @return Reference to this object. 
     */
    public SelectQueryBuilder select(String[] keys){
        for (String k : keys)
            this.queryKeys.add(k);
        return this;
    }

    /**
     * Appends a key behind "SELECT (DISTINCT)" word.
     * @param key The key to add. 
     * @return Reference to this object.
     */
    public SelectQueryBuilder select(String key){
        this.queryKeys.add(key);
        return this;
    }

    /**
     * Appends form names behind "FROM" word.
     * @param formArr The array of form names to add.
     * @return Reference to this object.
     */
    public SelectQueryBuilder from(String[] formArr){
        for (String form : formArr)
            this.queryKeys.add(form);
        return this;
    }

    /**
     * Appends form name behind "FROM" world
     * @param form Form name to add.
     * @return Reference to this object.
     */
    public SelectQueryBuilder from(String form){
        this.queryForm.add(form);
        return this;
    }

    /**
     * Adds a JOIN statement to this query.
     * @param type Type of JOIN.
     * @param form Form name.
     * @param key1 Key to be used in JOIN statement(will not be used if type is NATURAL, CROSS or FULL).
     * @param key2 Key to be used in JOIN statement(will not be used if type is NATURAL, CROSS or FULL).
     * @return Reference to this object.
     */
    public SelectQueryBuilder join(JoinType type, String form, String key1, String key2){
        if (this.joinForm == null)
            this.joinForm = new ArrayList<JoinInfo>();
        this.joinForm.add(new JoinInfo(type, form, key1, key2));
        return this;
    }

    /**
     * Adds WHERE statement to this query.
     * @param node A SqlPredicateNode object.
     * @return Reference to this object.
     */
    public SelectQueryBuilder where(SqlPredicateNode node){
        this.predicate = node;
        return this;
    }

    /**
     * Adds ORDER BY statement to this query.
     * @param key The key to be used in ORDER BY.
     * @param asc Flag to control whether the order is ASC.
     * @return Reference to this object.
     */
    public SelectQueryBuilder order(String key, boolean asc){
        this.orderKey = key;
        this.orderAsc = asc;
        return this;
    }

    /**
     * Adds GROUP BY statement to this query.
     * @param key The key to be used in GROUP BY.
     * @return Reference to this object.
     */
    public SelectQueryBuilder groupBy(String key){
        this.groupKey = key;
        return this;
    }

    /**
     * Builds SELECT statement according to iinformation contained in this instance.
     * @return String-repersented SELECT statement.
     */
    public String buildQuery(){
        if (this.queryKeys == null || this.queryForm == null) 
            return "";
        StringBuilder builder = new StringBuilder("SELECT");

        if (distinct) 
            builder.append(" DISTINCT");

        builder.append(' ');
        for (String key : this.queryKeys) 
            builder.append(key).append(',');
        builder.deleteCharAt(builder.length() - 1);

        builder.append(" FROM ");
        for (String form : this.queryForm)
            builder.append(form).append(',');
        builder.deleteCharAt(builder.length() - 1);


        if (this.joinForm != null)
            for (JoinInfo joinInfo : joinForm)
                builder.append(' ').append(joinInfo.buildString());
        
        if (this.predicate != null)
            builder.append(" WHERE ").append(this.predicate.buildPredicate());

        if (this.groupKey != null)
            builder.append(" GROUP BY ").append(this.groupKey);

        if (this.orderKey != null)
            builder.append(" ORDER BY ").append(this.orderKey)
                .append(this.orderAsc ? " ASC":" DESC");
        
        return builder.toString();
    }
}
