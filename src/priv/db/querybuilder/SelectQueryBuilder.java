package priv.db.querybuilder;

import java.util.ArrayList;

import priv.db.querybuilder.logicnode.SqlPredicateNode;

public class SelectQueryBuilder {
    private boolean distinct = false;
    private ArrayList<String> queryKeys;
    private ArrayList<String> queryForm;
    private ArrayList<JoinInfo> joinForm = null;
    private SqlPredicateNode predicate = null;
    private String groupKey = null;
    private String orderKey = null;
    private boolean orderAsc = true;

    public SelectQueryBuilder(){
        this.queryForm = new ArrayList<String>();
        this.queryKeys = new ArrayList<String>();
    }

    public SelectQueryBuilder(String form){
        this();
        this.queryForm.add(form);
    }

    public SelectQueryBuilder distinct(){
        this.distinct = true;
        return this;
    }

    public SelectQueryBuilder select(String[] keys){
        for (String k : keys)
            this.queryKeys.add(k);
        return this;
    }

    public SelectQueryBuilder select(String key){
        this.queryKeys.add(key);
        return this;
    }

    public SelectQueryBuilder from(String[] formArr){
        for (String form : formArr)
            this.queryKeys.add(form);
        return this;
    }

    public SelectQueryBuilder from(String form){
        this.queryForm.add(form);
        return this;
    }

    public SelectQueryBuilder join(JoinType type, String form, String key1, String key2){
        if (this.joinForm == null)
            this.joinForm = new ArrayList<JoinInfo>();
        this.joinForm.add(new JoinInfo(type, form, key1, key2));
        return this;
    }

    public SelectQueryBuilder where(SqlPredicateNode node){
        this.predicate = node;
        return this;
    }

    public SelectQueryBuilder order(String key, boolean asc){
        this.orderKey = key;
        this.orderAsc = asc;
        return this;
    }

    public SelectQueryBuilder groupBy(String key){
        this.groupKey = key;
        return this;
    }

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
