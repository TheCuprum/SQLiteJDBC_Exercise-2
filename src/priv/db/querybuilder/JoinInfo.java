package priv.db.querybuilder;

public class JoinInfo {
    JoinType type;
    String formName;
    String leftKey;
    String rightKey;
    
    public JoinInfo(JoinType type, String form, String key1, String key2){
        this.type = type;
        this.formName = form;
        if(type == JoinType.NATURAL || type == JoinType.CROSS){
            this.leftKey = null;
            this.rightKey = null;
        }else{
            this.leftKey = key1;
            this.rightKey = key2;
        }
    }

    public String buildString(){
        StringBuilder builder = new StringBuilder();
        builder
            .append(this.type.toString())
            .append(' ')
            .append(this.formName);
        if(type != JoinType.NATURAL && type != JoinType.CROSS){
            builder
                .append(' ')
                .append(this.leftKey)
                .append(" = ")
                .append(this.rightKey);
        }

        return builder.toString();
    }
}
