import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Error: only provide the path of a directory");
            System.exit(1);
        }
        if (!ProjectUtils.isDirectory(args[0])){
            System.out.println("Error: provide the path of a directory");
            System.exit(2);
        }

        ProjectUtils.walkDirectory(args[0], args[0]);

    }
}