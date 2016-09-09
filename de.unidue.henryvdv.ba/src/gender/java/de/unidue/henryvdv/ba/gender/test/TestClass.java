package de.unidue.henryvdv.ba.gender.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

/**
 * Not finished test class for reading compressed files
 * 
 * @author Henry
 *
 */


public class TestClass {

	public static void main(String[] args){
		
		try{
			BufferedReader reader = getBufferedReaderForCompressedFile("src/gender/resources/test/wikiCoNLL.bz2");
			for(int i = 0; i < 1000; i++){
				String s = reader.readLine();
				System.out.println(s);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static BufferedReader getBufferedReaderForCompressedFile(String fileIn) throws FileNotFoundException, CompressorException {
	    FileInputStream fin = new FileInputStream(fileIn);
	    BufferedInputStream bis = new BufferedInputStream(fin);
	    CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
	    BufferedReader br2 = new BufferedReader(new InputStreamReader(input));
	    return br2;
	}
	
}
