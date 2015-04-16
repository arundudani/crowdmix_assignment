package domain;

import java.util.concurrent.TimeUnit;

/**
 * Created by arundudani on 16/04/2015.
 *
 * This is main domain class Post, which represents User, their message and time of Post creation
 *
 * It also defines how one Post is compared with another by default (chronologically in this implementation).
 * Lastly, this class also gives realtime view of post whenever the post is read (i.e. toString() method
 *   returns how long ago the post was created)
 */
public class Post implements Comparable<Post> {
    private final long postDateTimeInMs;
    private final String message;
    private final String user;

    public Post(String user, String message) {
        postDateTimeInMs = System.currentTimeMillis();
        this.message = message;
        this.user = user;
    }

    public long getPostDateTimeInMs() {
        return postDateTimeInMs;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (postDateTimeInMs != post.postDateTimeInMs) return false;
        if (message != null ? !message.equals(post.message) : post.message != null) return false;
        if (user != null ? !user.equals(post.user) : post.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (postDateTimeInMs ^ (postDateTimeInMs >>> 32));
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        long currentTimeInMillis = System.currentTimeMillis();
        long timeDiff = currentTimeInMillis - postDateTimeInMs;
        return user + " - " + message + " (" + toMinutesAndSeconds(timeDiff) + " ago." + ")";
    }

    private String toMinutesAndSeconds(long durationInMilliSeconds) {
        if (durationInMilliSeconds < 1000) {
            return durationInMilliSeconds + " ms";
        }
        if (durationInMilliSeconds > 60000) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMilliSeconds);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMilliSeconds - (minutes * 60 * 1000));

            return new StringBuilder(Long.toString(minutes)).append(" minutes")
                    .append( (seconds > 0) ? (" " + seconds + " seconds") : "").toString();
        }
        return TimeUnit.MILLISECONDS.toSeconds(durationInMilliSeconds) + " seconds";
    }


    @Override
    public int compareTo(Post o) {
        if (o == null) {
            return 1;
        }

        return (postDateTimeInMs - o.postDateTimeInMs) > 0 ? -1 : 1;
    }
}
