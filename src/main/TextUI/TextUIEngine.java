package TextUI;

import CommandProcessors.FollowsCommandProcessor;
import CommandProcessors.PostingCommandProcessor;
import CommandProcessors.ReadingCommandProcessor;
import CommandProcessors.WallCommandProcessor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by arundudani on 16/04/2015.
 *
 * This is the GUI engine which reads the console inputs and processes them.
 * Even though, right now I am creating instances of various command processors inside this class itself,
 *  In real world, I will either inject them or use factory pattern
 *
 */
public class TextUIEngine {
    private FollowsCommandProcessor followsCommandProcessor = new FollowsCommandProcessor();
    private PostingCommandProcessor postingCommandProcessor = new PostingCommandProcessor();
    private ReadingCommandProcessor readingCommandProcessor = new ReadingCommandProcessor();
    private WallCommandProcessor wallCommandProcessor = new WallCommandProcessor();

    public void startEngine() throws Exception {
        String nextCommand = "Waiting";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(commandHelp());

            while(!nextCommand.equalsIgnoreCase("quit")) {
                System.out.print("> ");
                nextCommand = bufferedReader.readLine().trim();
                System.out.println(process(nextCommand));
            }

        } catch(Exception e) {
            throw e;
        }
    }

    /**
     * Returns Response of command execution as String
     * @param command
     * @return
     */
    private String process(String command) {
        String returnValue ;

        // we want only maximum of 3 results. The last argument would most likely be message user posts so would
        //  contain spaces
        String[] partsOfCommand = command.split(" ", 3);

        // short circuit quit command
        if ("quit".equalsIgnoreCase(partsOfCommand[0])) {
            return "Good Bye!";
        }

        String user = partsOfCommand[0];                    // first argument is always username unless we are trying to quit

        switch(partsOfCommand.length) {
            case 1:     // this is username, so display wall
                returnValue = readingCommandProcessor.process(user, null);
                break;
            case 2:     // this request is either to display wall or we do not know what it is
                boolean isWallDisplay = "wall".equalsIgnoreCase(partsOfCommand[1]);
                if (isWallDisplay) {
                    returnValue = wallCommandProcessor.process(user, null);
                } else {
                    returnValue = "Unknown Command \n" + commandHelp();
                }
                break;
            case 3:     // this could be posting or instruction to follow
                String secondPartOfCommand = partsOfCommand[1];
                String thirdPartOfCommand = partsOfCommand[2];

                switch(secondPartOfCommand.toUpperCase()) {
                    case "->":      // this is a posting
                        postingCommandProcessor.process(user, thirdPartOfCommand);
                        returnValue = "Message posted on " + user + "'s wall";
                        break;
                    case "FOLLOWS": // this is instruction to follow user
                        returnValue = followsCommandProcessor.process(user, thirdPartOfCommand);
                        break;
                    default:        // don't know what this is
                        returnValue = "Unknown Command \n" + commandHelp();
                        break;
                }
                break;
            default:
                returnValue = "Unknown Command \n" + commandHelp();
                break;
        }
        return returnValue;
    }

    private String commandHelp() {
        StringBuilder help = new StringBuilder("Available commands:\n");
        help.append("For Posting: <user name> -> <message> \n");
        help.append("For Reading user's posts: <username> \n");
        help.append("For Following: <user name> follows <user name> \n");
        help.append("For Reading user's wall: <user name> wall \n");
        help.append("To quit program: quit\n");
        help.append("\n");
        help.append("-> user names are case sensitive. John and JOHN are different users\n");
        help.append("-> keywords 'follows/quit/wall' are NOT case sensitive\n\n");
        return help.toString();
    }
}
