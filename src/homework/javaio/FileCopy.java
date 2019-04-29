package homework.javaio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
	public static void main(String[] args) {
		try {
			File file_in = new File("d:/D_Other/Tulips.jpg");
			FileInputStream fin = new FileInputStream(file_in);
			BufferedInputStream bin = new BufferedInputStream(fin);
			
			File file_out = new File("d:/D_Other/복사본_Tulips.jpg");
			FileOutputStream fout = new FileOutputStream(file_out);
			BufferedOutputStream bout = new BufferedOutputStream(fout);
			
			int c;
			
			while((c = bin.read()) != -1) {
				bout.write(c);
			}
			
			bout.close();
			bin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}