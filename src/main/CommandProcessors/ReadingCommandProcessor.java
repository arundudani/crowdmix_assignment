package CommandProcessors;

import repositories.PostsRepository;

import java.util.stream.Collectors;

/**
 * Created by arundudani on 16/04/2015.
 *
 * Implements <user name> command to display all messages posted by user in reverse chronological order
 */
public class ReadingCommandProcessor implements CommandProcessor<String> {
    @Override
    public String process(String user, String commandArgNotUsed) {
        // retrieve posts for this user only
        // since getPosts() returns TreeSet, results are already sorted
        // iterating through those results, generate string representation of posts
        // and join them into single string for display purpose (separated by newline)
        return PostsRepository.getPosts(post -> post.getUser().equals(user))
                .stream()
                .map(post -> post.toString())
                .collect(Collectors.joining("\n"));
    }
}
