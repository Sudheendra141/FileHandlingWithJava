import java.io.*;
import java.util.*;

public class FileUtils {

	static File[] createFile(int n, File path) throws IOException {
		File[] farr = new File[n];
		for (int i = 0; i < n; i++) {

			farr[i] = new File(path + "\\File-" + (i + 1) + ".txt");
			farr[i].createNewFile();

		}
		return farr;
	}

	static void deleteFiles(File arr[]) {
		for (File f : arr) {
			f.delete();
		}
	}
	static void writeInFiles(File arr[],String keyword[]) throws IOException
	{
	for(File f:arr)
	{FileWriter fw=new FileWriter(f);;
		try
		{
			int i=((int)(Math.random()*10))%keyword.length;
			fw.write("@Key: "+keyword[i]+"\n Random Text: in "+f.getName());
		}catch(Exception e)
		{
			System.out.println("Exception");
		}
		finally
		{
			fw.close();
		}
	
	}
	}

	static void groupFiles(File files[],HashMap<String,ArrayList<File>> fileMap) throws IOException
	{
		for(File f:files)
		{
			String key=getKey(f);
			System.out.println(key);
		}
	}
	private static void fillMap(String key, HashMap<String, ArrayList<File>> fileMap) {
		// TODO Auto-generated method stub
		
	}

	private static String getKey(File f) throws IOException {
		// TODO Auto-generated method stub
		FileReader fr=new FileReader(f);
		BufferedReader br=new BufferedReader(fr);
		String line=br.readLine();
		while(line!=null)
		{
			String temp[]=line.split(" ");
			for(int i=0;i<temp.length;i++)
			{
				if(temp[i].equals("@Key:"))
				{
					return temp[i+1];
				}
			}
			line=br.readLine();
			
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		//prerequisite
		String Folderpath = "C:\\Users\\msudheendra\\Desktop\\FileHandling\\Input";
		File Workspace = new File(Folderpath);
		File files[]=createFile(10, Workspace);
		String KeyWords[] = { "Developer", "Tester", "Analyst", "Support" };
		writeInFiles(files,KeyWords);
		// grouping files with keys
		HashMap<String,ArrayList<File>> fileMap=new HashMap<>();
		groupFiles(files,fileMap);
	}

}
