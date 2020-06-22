package tech.codingclub.helix.entity;

public class Tweet {
    public Tweet(String message, Long id, Long author_id, Long created_at) {
        this.message = message;
        this.id = id;
        this.author_id = author_id;
        this.created_at = created_at;
    }
    public Tweet(){}
    public String message;
    public Long id;
    public Long author_id;
    public Long created_at;
}
