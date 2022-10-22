package il.co.codeguru.corewars8086.utils;

import java.io.*;

public class Disassembler {
	
	public static String disassembler(byte[] bytes) throws Exception {
		
		File root = new File(Disassembler.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
		File tempfile = new File( root + "\\temp_disassemblr");
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempfile));
		bos.write(bytes);
		bos.flush();
		bos.close();
		
		ProcessBuilder pb = new ProcessBuilder("ndisasm", "-b 16","-pintel" , tempfile + "");
		pb.redirectOutput();
		Process p = pb.start();
		
		String error = getStringFromInput(p.getErrorStream());
		if(error == null)
			throw new Exception(error);
		
		String result = getStringFromInput(p.getInputStream());
		
		StringBuilder sb = new StringBuilder();
		String[] lines = result.split("\n");
		for (String line : lines) {
			String[] args = line.split("\\s\\s+");
			StringBuffer opcode = new StringBuffer(args[1]);
			while(opcode.length() < 10) opcode.append(" ");
			sb.append(opcode + args[2] + "\n");
			
		}
		
		return sb.toString();
	}

	private static String getStringFromInput(InputStream s) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(s));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ( (line = br.readLine()) != null) {
		   builder.append(line);
		   builder.append(System.getProperty("line.separator"));
		}
		String result = builder.toString();
		return result;
	}
	
}
