import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IndexHtmlGen {

    private File path; // File tipusu utvonal (sok hasznos metodusa van)


    //11.16 visszatud indexelni nem létező index.html-re a start page gomb
    public void indexHtmlKeszito(String directoryPath, String rootPath, String root) {
        File directory = new File(directoryPath);
        File htmlFile = new File(directory, "index.html");
        StringBuilder sb = new StringBuilder();


        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head><style> img {width: 30%;}</style></head>\n");
        sb.append("<body>");
        String s = "../".repeat(ProjectUtils.depth(root, rootPath));
        //melyseg alapjan szamolja ki a rootot
        sb.append(String.format("<a href=\"%sindex.html\"><h1>Start Page</h1></a>\n", s));
        sb.append("<hr>\n");
        sb.append("<h2>Directories</h2>\n");
        if(ProjectUtils.depth(rootPath, root) != 0) {
            sb.append("<a href=\"../index.html\"><h1>^^</h1></a>\n");
        }
        sb.append("<ul>\n");

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    sb.append("<a href=\"" + file.getName() + "/index.html\"><li>" + file.getName() + "</li></a>\n");
                }
            }
        }

        sb.append("</ul><hr>\n");
        sb.append("<h2>Images</h2>\n");
        File[] files2 = directory.listFiles();
        if (files2 != null) {
            for (File file : files2) {
                 if (ImageHtmlGen.isImage(file)) {
                     //ImageHtmlGen kep = new ImageHtmlGen(file, file.getName());
                    ImageHtmlGen.generateImageHtmlLink(sb, file);
                }
            }
        }
        sb.append("<ul>\n");
        sb.append("</ul>\n");
        sb.append("</body>\n</html>\n");


        try {
            FileWriter writer = new FileWriter(htmlFile);
            writer.write(sb.toString());
            System.out.println("HTML FILE CREATED: " + htmlFile.getAbsolutePath());

            writer.close();
        } catch (IOException e) {
            System.out.println("Hiba!" + e);
        }
    }

    public IndexHtmlGen(int melyseg, File path) {
        //this.melyseg = melyseg;
        this.path = path;

    }

}
