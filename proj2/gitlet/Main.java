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
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.init();
                // TODO: handle the `init` command
                break;
            case "add":
                Repository.add(args[1]);
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "log":
                Repository.log();
                break;
            case "checkout":
                if (args.length == 3) {
                    Repository.checkout(args[2]);
                }else if (args.length == 4){
                    Repository.checkout(args[1],args[3]);
                }else {
                    ;
                }
                break;
            case "rm":
//                Repository.remove(args[1]);
            case "commit":
                Repository.commit(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }
}
