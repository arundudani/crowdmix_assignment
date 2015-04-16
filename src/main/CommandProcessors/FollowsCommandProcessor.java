package CommandProcessors;

import repositories.FollowerRepository;

/**
 * Created by arundudani on 16/04/2015.
 *
 * Implements <user name> follows <user name> command by setting up follower/followee relationships
 */
public class FollowsCommandProcessor implements CommandProcessor<String> {
    @Override
    public String process(String userWhoIsStartingToFollow, String userWhoWillBeFollowed) {
        FollowerRepository.createRelationship(userWhoIsStartingToFollow, userWhoWillBeFollowed);
        return userWhoIsStartingToFollow + " will now see posts of " + userWhoWillBeFollowed + " on their wall";
    }
}
