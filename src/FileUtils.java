import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;

public class FileUtils {

    private static File[] createFile(int n, File path) throws IOException {
        File[] farr = new File[n];
        String types[] = {
            ".doc",
            ".txt",
            ".ppt",
            ".xlsx",
            ".pdf"
        };
        for (int i = 0; i < n; i++) {
            farr[i] = new File(path + "\\File-" + (i + 1) + types[(int)(Math.random() * 10) % types.length]);
            farr[i].createNewFile();

        }
        return farr;
    }

    private static void writeInFiles(File arr[], String keyword[]) throws IOException {
        String str = " ABCDEFGHIJKL MNOPQRSTUVWXYZ abcdefghijklm nopqrstuvxyz 01234 56789 ";
        for (File f: arr) {
            String key = keyword[(int)(Math.random() * keyword.length) % keyword.length];

            FileWriter fw = new FileWriter(f);
            int c = 0;
            int size = (int)(Math.random() * 20) + 5;
            int keyWordAt = (int)(Math.random() * size * 1000);

            while (c < size * 1000) {
                int i = (int)(Math.random() * str.length());
                if (i == str.length()) {
                    i = i - 1;

                }
                if (i < str.length()) {
                    fw.append(str.charAt(i));
                }
                if (c == keyWordAt) {
                    fw.append(" @Key: " + key + " ");

                    c += key.length() + 7;
                    continue;
                }
                if ((c + 1) % 130 == 0) {
                    fw.append("\n");
                }
                c++;

            }
            fw.close();
        }
    }

    private static void groupFiles(File files[], HashMap < String, ArrayList < File >> fileMap) throws IOException {
        for (File f: files) {
            String key = getKey(f);
            fillMap(fileMap, key, f);
        }
    }

    private static void fillMap(HashMap < String, ArrayList < File >> fileMap, String key, File f) {
        if (fileMap.containsKey(key)) {
            ArrayList < File > al = fileMap.get(key);
            al.add(f);
            fileMap.put(key, al);
        } else {
            ArrayList < File > al = new ArrayList < File > ();
            al.add(f);
            fileMap.put(key, al);
        }
    }

    private static String getKey(File f) throws IOException {
        // TODO Auto-generated method stub
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            String temp[] = line.split(" ");
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].equals("@Key:")) {
                    return temp[i + 1];
                }
            }
            line = br.readLine();

        }
        br.close();
        return null;
    }

    private static void printFiles(HashMap < String, ArrayList < File >> fileMap) {
        for (Entry < String, ArrayList < File >> i: fileMap.entrySet()) {
            System.out.println(i.getKey() + "(" + i.getValue().size() + ") : ");
            for (File f: i.getValue()) {
                System.out.print(f.getName() + " ");
            }
            System.out.println();
        }
    }

    private static void addToFolder(HashMap < String, ArrayList < File >> fileMap) throws IOException {
        for (Entry < String, ArrayList < File >> i: fileMap.entrySet()) {
            File f = new File("C:\\Users\\msudheendra\\Desktop\\FileHandling\\Output\\" + i.getKey());
            ArrayList < File > al = i.getValue();
            f.mkdir();

            for (File file: al) {
                String[] fName = file.getName().split("\\.");
                File newFile = new File("C:\\Users\\msudheendra\\Desktop\\FileHandling\\Output\\" + i.getKey() + "\\" +
                    fName[0] + "." + fName[1]);
                Files.copy(file.toPath(), newFile.toPath());
            }

        }
    }

    public static void main(String[] args) throws IOException {
        // prerequisite
        String Folderpath = "C:\\Users\\msudheendra\\Desktop\\FileHandling\\Input";
        File Workspace = new File(Folderpath);
        Scanner s = new Scanner(System.in);
        System.out.println("Enter no of files: ");
        int numberOfFilesToBeCreaed = 15;
        File files[] = createFile(numberOfFilesToBeCreaed, Workspace);
        System.out.println(numberOfFilesToBeCreaed + " files created successfully..");

        String keyWords[] = {
            "Developer",
            "Tester",
            "Analyst",
            "Intern",
            "Admin",
            "Intern"
        };

        writeInFiles(files, keyWords);
        // grouping files with keys
        HashMap < String, ArrayList < File >> fileMap = new HashMap < > ();
        groupFiles(files, fileMap);
        printFiles(fileMap);
        addToFolder(fileMap);
        System.out.println("Execution Done :) ");

    }

}