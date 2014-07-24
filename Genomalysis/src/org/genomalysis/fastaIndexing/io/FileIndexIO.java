/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.fastaIndexing.io;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.fastaIndexing.*;

/**
 *
 * @author ameyers
 */
public class FileIndexIO {
    private static final char sequenceStart = '>';
    
    public String getFileMD5Hash(File file) throws IOException{
        FileInputStream fin = null;
        try {
            //get md5 message digest
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8196];
            fin = new FileInputStream(file);
            int bytesRead = 0;
            while((bytesRead = fin.read(buffer)) > 0){
                digest.update(buffer, 0, bytesRead);
            }
            byte[] md5 = digest.digest();
            BigInteger bi = new BigInteger(md5);
            return bi.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }finally{
            if(fin != null)
                fin.close();
        }
    }
    
    public void writeBinaryIndex(Index index, File outputFile) throws IOException{
        if(!outputFile.exists())
            outputFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(outputFile);
        ObjectOutputStream oout = new ObjectOutputStream(fout);
        oout.writeObject(index);
        oout.flush();
        fout.flush();
        fout.close();
    }
    
    public Index readBinaryIndex(File inputFile) throws IOException{
        try {
            FileInputStream fin = new FileInputStream(inputFile);
            ObjectInputStream oin = new ObjectInputStream(fin);
            Index index = (Index) oin.readObject();
            return index;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileIndexIO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("Invalid index file");
        }
    }
    
    public Index indexFasta(File inputFile) throws IOException{
        /**
         * Start at beginning
         * set main index to 0
         * set file length
         * 
         * get buffer of 1000000 characters (or the last amount of characters)
         * find every instance of ">" in characters
         * for each instance, get name of sequence (shortened) and
         * start and end index
         * index of last ">" and next ">"
         * if it is the last sequence, test for end of file
         * if end of file, this is the last sequence in the file: add it
         * otherwise, repeat until end of file
         * if this is not the last sequence,
         * add it to the list
         */
        //Index will hold all of the indexes in the file
        //of the sequences that we found
        Index result = new Index();
        result.setIndexedFileName(inputFile.getAbsolutePath());
        result.setLastModified(inputFile.lastModified());
        
        RandomAccessFile rm = null;
        int length = (int)inputFile.length();
        int index = 0;
        //one megabyte chunks
        byte[] buffer = new byte[1024 * 1024];
        //get random access to the file, readonly.
        //we don't need to write anything
        rm = new RandomAccessFile(inputFile, "r");
        //sentinal variable. When I reach the end of the file
        //this should be set to true
        boolean eof = false;
        while(!eof){
            rm.seek(index);
            int last = 0;
            //read the next chunk
            int bytesRead = rm.read(buffer, 0, (int)Math.min(length - index, buffer.length));
            //the buffer should fill up, unless we've reached the end of the file
            if(bytesRead < buffer.length)
                eof = true;
            //read until the end of the buffer
            for(int i = 1; i < bytesRead; i++){
                byte current = buffer[i];
                if(current == sequenceStart || (eof && i == bytesRead - 1)){
                    //get the name of the sequence
                    //it shouldn't, in any case, be longer than 100
                    //I'll just get the name up to the first space or newline
                    //in the case we are at the end of the file, and the name is short,
                    //100 characters could break the program
                    //so check to make sure we don't go to far (Math.min)
                    String name = new String(buffer, last, Math.min(100, bytesRead - 5));
                    int nameEndIndex = name.indexOf(" ");
                    if(nameEndIndex == -1)
                        nameEndIndex = name.indexOf("\n");
                    if(nameEndIndex == -1)
                        throw new IOException("Sequence name " + name + " was longer than 100 characters");
                    name = name.substring(1, nameEndIndex);
                    //i is the end byte
                    //last is the beginning byte
                    IndexEntry entry = new IndexEntry(last + index, i + index, name);
                    result.getIndexEntries().getEntries().add(entry);
                    last = i;
                }
            }
            index = last + index;
            
        }
        return result;
    }
}
