package gitlet;

import static gitlet.Utils.exitWithMessage;
import static gitlet.Utils.join;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0){
            Utils.exitWithMessage("Please enter a command.");
        }
        String firstArg = args[0];
        if (firstArg.equals("init")) {
            judgeLength(1, args.length);
            Repository.init();
        }
        if (!join(Repository.GITLET_DIR).exists()){
            exitWithMessage("Not in an initialized Gitlet directory.");
        }
        switch (firstArg) {
            case "init":
                break;
            case "merge" :
                judgeLength(2,args.length);
                Repository.merge(args[1]);
            case "branch" :
                judgeLength(2,args.length);
                Repository.branch(args[1]);
                break;
            case "reset" :
                judgeLength(2,args.length);
                Repository.reset(args[1]);
                break;
            case "rm-branch" :
                judgeLength(2,args.length);
                Repository.rmBranch(args[1]);
                break;
            case "status" :
                judgeLength(1,args.length);
                Repository.status();
                break;
            case "find" :
                judgeLength(2,args.length);
                Repository.find(args[1]);
                break;
            case "global-log":
                judgeLength(1,args.length);
                Repository.globalLog();
                break;

            case "add":
                judgeLength(2,args.length);
                Repository.add(args[1]);
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "log":
                judgeLength(1,args.length);
                Repository.log();
                break;
            case "checkout":
                if (args.length == 3) {
                        Repository.checkout(args[2]);
                }else if (args.length == 4) {
                    if (!args[2].equals("--")) {
                        Utils.exitWithMessage("Incorrect operands.");
                    }else{Repository.checkout(args[1], args[3]);}
                } else if (args.length == 2) {
                    Repository.checkoutBranch(args[1]);
                } else{
                    Utils.exitWithMessage("Incorrect operands.");
                }
                break;
            case "rm":
                judgeLength(2,args.length);
                Repository.remove(args[1]);
                break;
            case "commit":
                if (args.length != 2){
                    Utils.exitWithMessage("Incorrect operands.");
                }
                Repository.commit(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }

    static void judgeLength(int correctLength,int argsLength){
        if (correctLength != argsLength){
            Utils.exitWithMessage("Incorrect operands.");
        }
    }
}
