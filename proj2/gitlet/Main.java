package gitlet;

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
        switch (firstArg) {
            case "merge" :
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.merge(args[1]);
            case "branch" :
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.branch(args[1]);
                break;
            case "reset" :
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.reset(args[1]);
                break;
            case "rm-branch" :
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.rmBranch(args[1]);
                break;
            case "status" :
                if (!judgeLength(1,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.status();
                break;
            case "find" :
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.find(args[1]);
                break;
            case "global-log":
                if (!judgeLength(1,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.globalLog();
                break;
            case "init":
                if (!judgeLength(1,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.init();
                // TODO: handle the `init` command
                break;
            case "add":
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.add(args[1]);
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "log":
                if (!judgeLength(1,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.log();
                break;
            case "checkout":
                if (judgeLength(2,args.length)||judgeLength(3,args.length)||
                        judgeLength(4,args.length)) {
                    if (args.length == 3) {
                        Repository.checkout(args[2]);
                    } else if (args.length == 4) {
                        if (!args[2].equals("--")) {
                            Utils.exitWithMessage("Incorrect operands.");
                        }else{Repository.checkout(args[1], args[3]);}
                    } else {
                        Repository.checkoutBranch(args[1]);
                    }
                }else{
                    Utils.exitWithMessage("Incorrect operands.");
                }
                break;
            case "rm":
                if (!judgeLength(2,args.length)){Utils.exitWithMessage("Incorrect operands.");}
                Repository.remove(args[1]);
                break;
            case "commit":
                if (args.length != 2){
                    Utils.exitWithMessage("Please enter a commit message.");
                }
                Repository.commit(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }

    static boolean judgeLength(int correctLength,int argsLength){
        return  correctLength == argsLength;
    }
}
