import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageHtmlGen {

    private File path;
    private File fname;

    public ImageHtmlGen(File path, File fname, File[] files2) {
        this.path = path;
        this.fname = fname;
    }

    public static boolean isImage(File file) // checks if given file is an image
    {
        String s = file.toString().toLowerCase();
        return s.endsWith(".jpg") || s.endsWith(".jpeg") || s.endsWith(".gif") || s.endsWith(".png");
    }

    public static void imageHtmlKeszito(String imagePath, String rootPath, String root, File[] files2) {
        File imageFile = new File(imagePath);
        File htmlFile = new File(imageFile.getParent(), imageFile.getName().substring(0, imageFile.getName().lastIndexOf(".")) + ".html");
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head><style> img {width: 30%;}</style></head>\n");
        sb.append("<body>");
        String s = "../".repeat(ProjectUtils.depth(root, rootPath));
        //melyseg alapjan szamolja ki a rootot
        sb.append(String.format("<a href=\"%sindex.html\"><h1>Start Page</h1></a>\n", s));
        sb.append("<hr>\n");
        sb.append("<h2>Image</h2>\n");


        if (files2!= null && ImageHtmlGen.isImage(imageFile)){
            new ImageHtmlGen(imageFile, imageFile, files2).gombKeszito(sb, imageFile, files2);

        }

        sb.append("</body>");
        sb.append("</html>");


        try {
            FileWriter writer = new FileWriter(htmlFile);
            writer.write(sb.toString());
            System.out.println("HTML FILE CREATED: " + htmlFile.getAbsolutePath());

            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void generateImageHtmlLink(StringBuilder sb, File imageFile){
        // Generálja le az egyes képekhez tartozó HTML linket.
        String imageName = imageFile.getName();
        sb.append("<li><a href=\"" + imageName.substring(0, imageName.lastIndexOf(".")) + ".html\">" + imageName + "</a></li>\n");
    }


    // meghívja a gombokat keszito fuggvenyt a kepek html oldalára, amivel lépni tudunk előre/hátra
    public String gombKeszito(StringBuilder sb, File imageFile, File[] files2) {
        List<File> jelenlegi = new ArrayList<>();
        for (int i = 0; i < files2.length; i++){
            if (isImage(files2[i])){
                jelenlegi.add(files2[i]);
            }
        }

        String kovetkezo = "";
        String elozo = "";
        int jelenlegiIndex = jelenlegi.indexOf(this.path);


        if (jelenlegiIndex == jelenlegi.size() - 1) {
            elozo = String.valueOf(jelenlegi.get(jelenlegiIndex - 1));
        } else if (jelenlegiIndex == 0) {
            kovetkezo = String.valueOf(jelenlegi.get(jelenlegiIndex + 1));
        } else {
            kovetkezo = String.valueOf(jelenlegi.get(jelenlegiIndex + 1));
            elozo = String.valueOf(jelenlegi.get(jelenlegiIndex - 1));
        }
        if (!kovetkezo.equals("")) {
            File f1 = new File(kovetkezo);
            kovetkezo = f1.getName().substring(0, f1.getName().lastIndexOf(".")) + ".html";
        }
        if (!elozo.equals("")){
            File f2 = new File(elozo);
            elozo = f2.getName().substring(0, f2.getName().lastIndexOf(".")) + ".html";
        }
        sb.append(fileLinker(imageFile.getName() ,kovetkezo, elozo));



        return sb.toString();

    }


    private String fileLinker(String s, String next, String previous) {
        StringBuilder sb = new StringBuilder();

        if (!next.equals("") && !previous.equals("")) {
            sb.append(String.format("<h3><a href=\"./" + previous + "\"> << </a> &nbsp; %s <a href=\"./" + next + "\"> >> </a></h3>", s));
            sb.append("<a href=\"./" + next + "\"><img src=\"./"+ s + "\"  style=\"width: 20%; height: 20%;\"></a>");
        } else if (previous.equals("")) {
            sb.append(String.format("<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; %s <a href=\"./" + next + "\"> >> </a></h3>", s));
            sb.append("<a href=\"./" + next + "\"><img src=\"./"+ s + "\"  style=\"width: 20%; height: 20%;\"></a>");
        } else if (next.equals("")) {
            sb.append(String.format("<h3> <a href=\"./" + previous + "\"> << </a> %s </h3>", s));
            sb.append("<img src=\"./"+ s + "\"  style=\"width: 20%; height: 20%;\"></a>");
        }

        return sb.toString();
    }

}



