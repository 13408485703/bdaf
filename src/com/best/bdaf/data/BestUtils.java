package com.best.bdaf.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.GZIPInputStream;


public class BestUtils {
	
	public static final int BUFFER = 1024;  
    public static final String EXT_GZ = ".gz";  
    public static final String EXT_LZO = ".of.lzo";  
    
    public enum E_nature_type {INT,LONG,FLOAT,DOUBLE};
	
	public static byte[] intToByte4_Low(int number)
	{
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); 
			temp = temp >> 8; 
		}
		return b;
	}

	public static byte[] intToByte2_Low(int number)
	{
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 0; i < 2; i++)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); 
			temp = temp >> 8; 
		}
		return b;
	}
	
	public static byte[] intToByte4_High(int number)
	{
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 3; i > -1; i--)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); 
			temp = temp >> 8;
		}
		return b;
	}

	public static byte[] intToByte2_High(int number)
	{
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 1; i > -1; i--)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); 
			temp = temp >> 8;
		}
		return b;
	}


	public static int byte4ToInt_High(byte[] b) 
	{
		int s = 0;
		for (int i = 0; i < 3; i++)
		{
			if (b[i] >= 0)
				s = s + b[i];
			else
				s = s + 256 + b[i];
			s = s * 256;
		}
		if (b[3] >= 0) 
			s = s + b[3];
		else
			s = s + 256 + b[3];
		return s;
	}


	public static int byte2ToInt_High(byte[] b)
	{
		int s = 0;
		if (b[0] >= 0)
			s = s + b[0];
		else
			s = s + 256 + b[0];
		s = s * 256;
		if (b[1] >= 0) 
			s = s + b[1];
		else
			s = s + 256 + b[1];
		return s;
	}
	
	
	public static int byte4ToInt_Low(byte[] b) 
	{
		int s = 0;
		for (int i = 3; i > 0; i--)
		{
			if (b[i] >= 0)
				s = s + b[i];
			else
				s = s + 256 + b[i];
			s = s * 256;
		}
		if (b[0] >= 0) 
			s = s + b[0];
		else
			s = s + 256 + b[0];
		return s;
	}


	public static int byte2ToInt_Low(byte[] b)
	{
		int s = 0;
		if (b[1] >= 0)
			s = s + b[1];
		else
			s = s + 256 + b[1];
		s = s * 256;
		if (b[0] >= 0) 
			s = s + b[0];
		else
			s = s + 256 + b[0];
		return s;
	}
	
	public static byte[] intToByte_Low(int number, int bytelength)
	{
		int temp = number;
		byte[] b = new byte[bytelength];
		for (int i = 0; i < bytelength; i++)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); 
			temp = temp >> 8; 
		}
		return b;
	}
	
	public static byte[] intToByte_High(int number, int bytelength)
	{
		int temp = number;
		byte[] b = new byte[bytelength];
		for (int i = (bytelength-1); i > -1; i--)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); 
			temp = temp >> 8;
		}
		return b;
	}
	
    public static byte[] floatToByte4_Low(float f, int bytelength) {  
        int intbits = Float.floatToIntBits(f);//将float里面的二进制串解释为int整数  
        return intToByte_Low(intbits, bytelength);
    }  
    
    public static byte[] floatToByte4_High(float f, int bytelength) {  
        int intbits = Float.floatToIntBits(f);//将float里面的二进制串解释为int整数  
        return intToByte_High(intbits, bytelength);
    }  

    
	public static Object byteToNature(byte[] b, boolean isHigh, E_nature_type nt)
	{
		ByteBuffer buf = ByteBuffer.allocateDirect(b.length); 
		if(isHigh)
			buf = buf.order(ByteOrder.BIG_ENDIAN);
		else 
			buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.put(b);
		buf.rewind();
		switch(nt)
		{
		case LONG:
			return buf.getLong();
		case FLOAT:
			return buf.getFloat();
		case DOUBLE:
			return buf.getDouble();
		case INT:
		default:
			return buf.getInt();		
		}	
	}
	
	public static int byteToInt(byte[] b, boolean isHigh)
	{
		ByteBuffer buf = ByteBuffer.allocateDirect(b.length); 
		if(isHigh)
			buf = buf.order(ByteOrder.BIG_ENDIAN);
		else 
			buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.put(b);
		buf.rewind();

		return buf.getInt();
	}
	
	public static long byteToLong(byte[] b, boolean isHigh)
	{
		ByteBuffer buf = ByteBuffer.allocateDirect(b.length); 
		if(isHigh)
			buf = buf.order(ByteOrder.BIG_ENDIAN);
		else 
			buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.put(b);
		buf.rewind();
		return buf.getLong();
	}
	
	public static float byteToFloat(byte[] b, boolean isHigh)
	{
		ByteBuffer buf = ByteBuffer.allocateDirect(b.length); 
		if(isHigh)
			buf = buf.order(ByteOrder.BIG_ENDIAN);
		else 
			buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.put(b);
		buf.rewind();
		return buf.getFloat();
	}
	
	public static double byteToDouble(byte[] b, boolean isHigh)
	{
		ByteBuffer buf = ByteBuffer.allocateDirect(b.length); 
		if(isHigh)
			buf = buf.order(ByteOrder.BIG_ENDIAN);
		else 
			buf = buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.put(b);
		buf.rewind();
		return buf.getDouble();
	}
	
	/** 
     * 文件解压缩 
     *  
     * @param file 
     * @param delete 
     *            是否删除原始文件 
     * @throws Exception 
     */  
    public static void decompress_gz(File file, boolean delete) throws Exception 
    {  
        FileInputStream fis = new FileInputStream(file);  
        FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT_GZ, ""));  
        decompress_gz(fis, fos);  
        fis.close();  
        fos.flush();  
        fos.close();  

        if (delete) {  
            file.delete();  
        }  
    }  

    /** 
     * 数据解压缩 
     *  
     * @param is 
     * @param os 
     * @throws Exception 
     */  
    public static void decompress_gz(InputStream is, OutputStream os) throws Exception 
    {  
        GZIPInputStream gis = new GZIPInputStream(is);  

        int count;  
        byte data[] = new byte[BUFFER];  
        while ((count = gis.read(data, 0, BUFFER)) != -1) {  
            os.write(data, 0, count);  
        }  

        gis.close();  
    }  
    
    public static byte[] ssrToBytes(String ssr, boolean isHigh)
    {
    	int ssr1 = Integer.parseInt(ssr.substring(0, 1));
		int ssr2 = Integer.parseInt(ssr.substring(1, 2));
		int ssr3 = Integer.parseInt(ssr.substring(2, 3));
		int ssr4 = Integer.parseInt(ssr.substring(3, 4));
		byte[] b = new byte[2];
		b[0] = (new Integer(((ssr1 << 1) | (ssr2 >> 2)))).byteValue();
		b[1] = (new Integer(((ssr2 << 6) | (ssr3 << 3) | (ssr4)))).byteValue();
		if(isHigh == false)
		{
			byte bytetmp = b[0];
			b[0] = b[1];
			b[1] = bytetmp;
		}
		return b;
    }
    
    public static String bytesToSsr(byte[] b, boolean isHigh)
    {
    	int I070_1 = b[0];
		int I070_2 = b[1];
		if(isHigh == false)
		{
			int inttmp = I070_1;
			I070_1 = I070_2;
			I070_2 = inttmp;
		}	
		I070_1 = I070_1 & 0x0F;
		if(I070_2<0)
			I070_2 = 256 + I070_2;
		int ssr = I070_1*256+I070_2;
		int ssr4 = ssr & 0x07;
		ssr = ssr >>> 3;
		int ssr3 = ssr & 0x07;
		ssr = ssr >>> 3;
		int ssr2 = ssr & 0x07;
		ssr = ssr >>> 3;
		int ssr1 = ssr & 0x07;
		
		String ssrcode = ""+ssr1+ssr2+ssr3+ssr4;
		return ssrcode;
    }
   
}
