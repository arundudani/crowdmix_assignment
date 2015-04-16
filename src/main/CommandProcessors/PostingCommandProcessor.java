package CommandProcessors;

import domain.Post;
import repositories.PostsRepository;

/**
 * Created by arundudani on 16/04/2015.
 *
 * Implements <user name> -> <message> command by posting the message

 */
public class PostingCommandProcessor implements CommandProcessor<Post> {
    @Override
    public Post process(String userDoingPosting, String message) {
        Post p = new Post(userDoingPosting, message);
        PostsRepository.newPost(p);
        return p;
    }
}
