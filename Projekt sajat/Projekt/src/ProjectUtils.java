import java.io.File;

public class ProjectUtils {
    private ProjectUtils() {
        //nem peldanyosithato
    }

    /*
    Ez a metódus létrehoz egy File objektumot a megadott útvonallal, majd meghívja a isDirectory() metódust,
    hogy ellenőrizze, hogy az útvonal egy könyvtár-e vagy sem.
    Az isDirectory() metódus true értéket ad vissza, ha a megadott útvonal egy könyvtár, egyébként false-t.
    Ezt a logikát a fent bemutatott példakódban használhatja.
     */
    public static boolean isDirectory(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    // megnézi hogy hány db \ jel van a két path-ba ha valamelyikbe van . az file-lesz
    public static int depth(String rootPath, String path) {
        if (path.contains(".")) {
            return path.split("\\\\").length - rootPath.split("\\\\").length - 1;
        }
        return path.split("\\\\").length - rootPath.split("\\\\").length;
    }

    /*
    Ebben a módosított példában a walkDirectory metódus kiírja az éppen meglátogatott könyvtár
    abszolút útvonalát a directory.getAbsolutePath() segítségével, majd bejárja a könyvtár tartalmát.
    Az alkönyvtárakban is ugyanezt az abszolút útvonalat használja.
    Így a program közvetlenül a könyvtárban fogja kiírni, hogy éppen melyik könyvtárban tartózkodik.
    Átírhatja a startingDirectory változót a saját könyvtárának megadásához.

    Igazából ez csinál eddig mindent carryzi a projektem DE HOGY MIÉRT???????
     */
    public static void walkDirectory(String rootPath, String path) {
        File directory = new File(path);
        System.out.println("Current dir.: " + directory.getAbsolutePath());
        IndexHtmlGen indexeHtml = new IndexHtmlGen(0, directory);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {

                    if (file.isDirectory()) {
                        //könyvtárak
                        walkDirectory(rootPath, file.getAbsolutePath());
                        indexeHtml.indexHtmlKeszito(file.getAbsolutePath(), directory.getAbsolutePath(), rootPath);
                    } else if (ImageHtmlGen.isImage(file)) {
                        ImageHtmlGen.imageHtmlKeszito(file.getAbsolutePath(), rootPath, rootPath, files);
                    }

                }
            }
        } else {
            System.out.println("Can't find dir. in this path: " + directory.getAbsolutePath());
        }
    }

}


