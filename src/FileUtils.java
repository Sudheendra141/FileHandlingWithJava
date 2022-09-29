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
      for (File f: arr) {
         FileWriter fw = new FileWriter(f);;
         try {
            int i = ((int)(Math.random() * 10)) % keyword.length;
            fw.write("@Key: " + keyword[i] + "\nRandom Text: in File - " + f.getName());
         } catch (Exception e) {
            System.out.println("Exception");
         } finally {
            fw.close();
         }

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
            File newFile = new File("C:\\Users\\msudheendra\\Desktop\\FileHandling\\Output\\" + i.getKey() + "\\" + fName[0] + "." + fName[1]);
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
      int numberOfFilesToBeCreaed = s.nextInt();

      File files[] = createFile(numberOfFilesToBeCreaed, Workspace);
      System.out.println(numberOfFilesToBeCreaed + " files created successfully..");
      System.out.println("Enter no of KeyWords: ");

      String keyWords[] = new String[s.nextInt()];
      for (int i = 0; i < keyWords.length; i++) {
         System.out.print("@key" + (i + 1) + ") ");
         keyWords[i] = s.next();
      }
      writeInFiles(files, keyWords);
      // grouping files with keys
      HashMap < String, ArrayList < File >> fileMap = new HashMap < > ();
      groupFiles(files, fileMap);
      printFiles(fileMap);
      addToFolder(fileMap);
      System.out.println("Execution Done :) ");

   }

}